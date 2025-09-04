package com.back.jsb.domain.user;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Base64;

@Service
public class ImageService {

    public String getBase64UserImage(User user) throws IOException {
        byte[] imageBytes;

        if(user.getProfileImage() != null) {
            imageBytes = user.getProfileImage();
        } else {
            ClassPathResource imgFile = new ClassPathResource("static/images/defaultProfile.png");
            imageBytes = imgFile.getInputStream().readAllBytes();
        }
        return Base64.getEncoder().encodeToString(imageBytes);
    }

    public String getBase64EditingImage(User user, MultipartFile newFile) throws IOException {
        if (newFile != null && !newFile.isEmpty()) {
            return Base64.getEncoder().encodeToString(newFile.getBytes());
        }
        return getBase64UserImage(user);
    }
}
