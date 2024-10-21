package com.ubgd.chemhandbook.client;

import org.springframework.web.multipart.MultipartFile;


public interface ImageClient {
    String sendImage(MultipartFile file);
    Integer parseResponseToIntegers(String response);
}
