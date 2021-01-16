package com.zain.jo.uploader.awsimageuploader.service.services;

import com.zain.jo.uploader.awsimageuploader.bootstrap.FakeUserProfileDateStore;
import com.zain.jo.uploader.awsimageuploader.domain.pofile.UserProfile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserProfileDataAccessService {

    private final FakeUserProfileDateStore fakeUserProfileDateStore;

    @Autowired
    public UserProfileDataAccessService(FakeUserProfileDateStore fakeUserProfileDateStore) {
        this.fakeUserProfileDateStore = fakeUserProfileDateStore;
    }

    List<UserProfile> getUserProfile(){
        return fakeUserProfileDateStore.getUserProfiles();
    }


}
