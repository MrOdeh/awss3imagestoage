package com.zain.jo.uploader.awsimageuploader.bootstrap;

import com.zain.jo.uploader.awsimageuploader.domain.pofile.UserProfile;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Repository
public class FakeUserProfileDateStore {

    private final static List<UserProfile> USER_PROFILES = new ArrayList<>();

    static {
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "JanJava", null));
        USER_PROFILES.add(new UserProfile(UUID.randomUUID(), "FebJava", null));
    }

    public List<UserProfile> getUserProfiles(){
        return this.USER_PROFILES;
    }


}
