package com.zain.jo.uploader.awsimageuploader.bucket;

public enum BucketName {

    PROFILE_IMAGE("springbootwebbucket-1212");

    private final String bucketName;

    BucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return bucketName;
    }
}
