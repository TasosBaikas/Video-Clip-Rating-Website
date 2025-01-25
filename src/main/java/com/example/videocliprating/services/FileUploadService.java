package com.example.videocliprating.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;


@Service
public class FileUploadService {

    @Value("${upload.directory}")
    private String uploadDirectory;

    /**
     * Handles file upload for a given MultipartFile and returns the saved file name.
     *
     * @param file MultipartFile to be uploaded
     * @return The new file name after being saved
     * @throws IOException If any file operation fails
     */
    public String uploadFile(MultipartFile file, String randomString) throws IOException {
        if (file.isEmpty()) {
            throw new IOException("Cannot upload an empty file");
        }

        String pathToSave = System.getProperty("user.dir") + "/" + uploadDirectory;
        File directory = new File(pathToSave);
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String newFileName = randomString + "_" + file.getOriginalFilename();

        File uploadedFile = new File(pathToSave + "/" + newFileName);
        file.transferTo(uploadedFile);

        // Return the relative file path
        return "/uploads/" + newFileName;
    }
}
