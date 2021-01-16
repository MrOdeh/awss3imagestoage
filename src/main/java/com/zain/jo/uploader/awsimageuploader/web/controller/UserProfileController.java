package com.zain.jo.uploader.awsimageuploader.web.controller;

import com.zain.jo.uploader.awsimageuploader.domain.pofile.UserProfile;
import com.zain.jo.uploader.awsimageuploader.service.services.UserProfileService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/vi/user_profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Autowired
    public UserProfileController(UserProfileService userProfileService) {
        this.userProfileService = userProfileService;
    }

    @GetMapping(value = "/users")
    public ResponseEntity<List<UserProfile>> getUserProfile(){

        List<UserProfile> pofiles = userProfileService.getUserPofiles();
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.add("id", pofiles.get(0).getUserProfile().toString());
        responseHeaders.setBearerAuth("111111111111111111111111111");
        return new ResponseEntity<>(pofiles, responseHeaders,HttpStatus.ACCEPTED);

    }

    @PostMapping(path = "/{userProfileId}/image/upload",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public void uploadPhotp(@PathVariable("userProfileId") UUID userProfileId
                        , @RequestParam("file")MultipartFile file){

        userProfileService.uploadUserProfileIMage(userProfileId, file);


    }

    @GetMapping("/{userProfileId}/image/download")
    public byte[] downloadImage(@PathVariable("userProfileId") UUID userProfileId){
        return userProfileService.downloadPhoto(userProfileId);
    }

}
