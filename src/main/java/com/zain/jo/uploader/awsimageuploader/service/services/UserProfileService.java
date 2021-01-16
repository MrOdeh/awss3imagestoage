package com.zain.jo.uploader.awsimageuploader.service.services;

import com.zain.jo.uploader.awsimageuploader.bucket.BucketName;
import com.zain.jo.uploader.awsimageuploader.domain.pofile.UserProfile;
import com.zain.jo.uploader.awsimageuploader.service.storage.FileStore;
import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.*;

import static org.apache.http.entity.ContentType.*;
import static org.apache.http.entity.ContentType.IMAGE_JPEG;

@Service
public class UserProfileService {

    private final UserProfileDataAccessService userProfileDataAccessService;
    private final FileStore fileStore;
    @Autowired
    public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore store) {
        this.userProfileDataAccessService = userProfileDataAccessService;
        this.fileStore = store;
    }

    public List<UserProfile> getUserPofiles(){
        return userProfileDataAccessService.getUserProfile();
    }

    public void uploadUserProfileIMage(UUID userProfileId, MultipartFile file) {
        // check if file is not empty

        isFileEmpty(file);
        //check if the file is image
        isFileImage(file);

        // check if user exists
        UserProfile user = getUserOrThrow(userProfileId);

        // grab some meta data from image
        Map<String, String> metaDate = getMetaDate(file);

        // store the image in S3
        // we can return this in order to save file path and file name in db
        String userPath = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfile());
        String fileName = String.format("%s-%s", (file.getOriginalFilename().equals("") ? "UNKOWN" : file.getOriginalFilename())
                , UUID.randomUUID());
        try {
            fileStore.save(userPath, fileName, Optional.ofNullable(metaDate), file.getInputStream());
            user.setUserProfileImageLink(fileName); // userprofile + UserProfileImageLink to get access to image
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        // update db s3 with s3 image link
    }

    private Map<String, String> getMetaDate(MultipartFile file) {
        Map<String, String> metaDate = new HashMap<>();
        metaDate.put("Content-Type", file.getContentType());
        metaDate.put("Content-Lenght", String.valueOf(file.getSize()));
        return metaDate;
    }

    private UserProfile getUserOrThrow(UUID userProfileId) {
        UserProfile user = userProfileDataAccessService
                .getUserProfile()
                .stream()
                .filter(profile -> profile.getUserProfile().equals(userProfileId))
                .findFirst()
                .orElseThrow(() -> new IllegalStateException(String.format("user profile %s not found ", userProfileId)));
        return user;
    }

    private void isFileImage(MultipartFile file) {
        if(!Arrays.asList(IMAGE_JPEG.getMimeType(), IMAGE_BMP.getMimeType(), IMAGE_GIF.getMimeType(),IMAGE_PNG.getMimeType()
                , IMAGE_SVG.getMimeType(), IMAGE_TIFF.getMimeType(), IMAGE_TIFF.getMimeType())
                .contains(file.getContentType())){
            throw new IllegalStateException("the file is not an image, and it content# " + file.getContentType());
        }
    }

    private void isFileEmpty(MultipartFile file) {
        if(file.isEmpty()){
            throw new IllegalStateException("falid to load image with size [ " + file.getSize() + " ].");
        }
    }

    public byte[] downloadPhoto(UUID userProfileId) {
        UserProfile userProfile = getUserOrThrow(userProfileId);
        String imagePath = String.format("%s/%s/%s", BucketName.PROFILE_IMAGE.getBucketName(),
                userProfile.getUserProfile(), userProfile.getUserProfileImageLink());
        // pass the path alongs with the image as how we did save it before "image name  + UUID.randomlly"
        return userProfile.getUserProfileImageLink().map(fileNameWithUuid ->
           fileStore.download(imagePath, fileNameWithUuid))
                .orElse(new byte[0]);
    }
}
