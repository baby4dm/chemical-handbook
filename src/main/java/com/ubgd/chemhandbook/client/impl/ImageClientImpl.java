package com.ubgd.chemhandbook.client.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ubgd.chemhandbook.client.ImageClient;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

@Component
public class ImageClientImpl implements ImageClient {

    @Value("${image-service.url}")
    private String apiUrl;

    @Override
    public String sendImage(MultipartFile file) {
        try (InputStream imageInputStream = file.getInputStream();
             CloseableHttpClient httpClient = HttpClients.createDefault()) {

            HttpPost post = new HttpPost(apiUrl);

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addBinaryBody("file", imageInputStream, ContentType.DEFAULT_BINARY, file.getOriginalFilename());
            HttpEntity multipart = builder.build();
            post.setEntity(multipart);

            HttpResponse response = httpClient.execute(post);
            int statusCode = response.getStatusLine().getStatusCode();

            if (statusCode != 200) {
                return "Failed to process image. HTTP Status: " + statusCode;
            }

            HttpEntity responseEntity = response.getEntity();
            if (responseEntity == null) {
                return "No response from the server.";
            }

            String responseString = EntityUtils.toString(responseEntity);

            ObjectMapper objectMapper = new ObjectMapper();
            Map<String, String> responseMap = objectMapper.readValue(responseString, new TypeReference<Map<String, String>>() {});

            return responseMap.getOrDefault("text", "No text recognized.");

        } catch (IOException e) {
            e.printStackTrace();
            return "Не вдалося розпізнати текст.";
        }
    }


    @Override
    public Integer parseResponseToIntegers(String response) {
        String[] parts = response.trim().split(" ");

        if (parts.length != 2) {
            throw new IllegalArgumentException("Response must contain exactly two integers separated by space.");
        }

        try {
            return Integer.parseInt(parts[1]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in response: " + response, e);
        }
    }


}
