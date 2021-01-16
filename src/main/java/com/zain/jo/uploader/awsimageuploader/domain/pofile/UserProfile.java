package com.zain.jo.uploader.awsimageuploader.domain.pofile;

import lombok.*;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Setter
public class UserProfile {

    private UUID userProfile;
    private String userName;
    @Getter(AccessLevel.MODULE)
    private String userProfileImageLink; //S3 link


    public Optional<String> getUserProfileImageLink() {
        return Optional.ofNullable(userProfileImageLink);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserProfile that = (UserProfile) o;
        return Objects.equals(userProfile, that.userProfile)
                && Objects.equals(userName, that.userName)
                && Objects.equals(userProfileImageLink, that.userProfileImageLink);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userProfile, userName, userProfileImageLink);
    }
}
