package com.review.reviewplatform.services.Implementations;


import com.google.cloud.storage.Blob;
import com.google.cloud.storage.BlobId;
import com.google.cloud.storage.BlobInfo;
import com.google.cloud.storage.Storage;
import com.google.firebase.cloud.StorageClient;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.review.reviewplatform.services.Interfaces.FirebaseStorageService;
import java.io.IOException;
import java.util.UUID;

@Service
public class FirebaseStorageServiceImpl implements FirebaseStorageService {

    private static final String BUCKET_NAME = "reviewapp-1570b.firebasestorage.app";

    @Override
    public String uploadFile(MultipartFile file) throws IOException {
        System.out.println("Starting file upload");
        String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
        System.out.println("Generated filename: " + fileName);

        try {
            Storage storage = StorageClient.getInstance().bucket().getStorage();
            BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
            BlobInfo blobInfo = BlobInfo.newBuilder(blobId)
                    .setContentType(file.getContentType())
                    .build();

            Blob blob = storage.create(blobInfo, file.getInputStream());
            System.out.println("File uploaded successfully");
            return String.format("https://storage.googleapis.com/%s/%s", BUCKET_NAME, fileName);
        } catch (Exception e) {
            System.out.println("Error during upload: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public void deleteFile(String fileUrl) throws IOException {
        String fileName = extractFileNameFromUrl(fileUrl);
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        storage.delete(blobId);
    }

    @Override
    public String getFileUrl(String fileName) throws IOException {
        Storage storage = StorageClient.getInstance().bucket().getStorage();
        BlobId blobId = BlobId.of(BUCKET_NAME, fileName);
        Blob blob = storage.get(blobId);
        return blob != null ? blob.getMediaLink() : null;
    }

    private String extractFileNameFromUrl(String fileUrl) {
        // Extract filename from the full URL
        return fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
    }
}