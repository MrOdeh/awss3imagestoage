package com.zain.jo.uploader.awsimageuploader.service.storage;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FileStoreTest {

    @Test
    public void testMap(){
        Optional<Map<String, String>> keys = Optional.ofNullable(null);
        System.out.println(keys.isPresent());

    }

}