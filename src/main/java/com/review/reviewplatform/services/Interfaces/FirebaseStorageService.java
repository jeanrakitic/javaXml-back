package com.review.reviewplatform.services.Interfaces;

import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

public interface FirebaseStorageService {
    String uploadFile(MultipartFile file) throws IOException;
    void deleteFile(String fileUrl) throws IOException;
    String getFileUrl(String fileName) throws IOException;
}