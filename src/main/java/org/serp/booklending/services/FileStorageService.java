package org.serp.booklending.services;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.io.File.separator;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {

    @Value("${application.file.path.images-out-path}")
    private final String fileUploadPath;
    private static final String EMPTY_STRING = "";

    public String saveFile(
            @NotNull MultipartFile file,
            @NotNull Long userId) {
        var fileUploadSubPath = "users" + separator + userId;

        return uploadFile(file, fileUploadSubPath);
    }

    private String uploadFile(
            @NotNull MultipartFile file,
            @NotNull String fileUploadSubPath) {
        var finalFilePath = fileUploadPath + separator + fileUploadSubPath;
        File targetFolder = new File(finalFilePath);
        if (!targetFolder.exists()) {
            var isCreatedDirectory = targetFolder.mkdir();
            if (!isCreatedDirectory) {
                log.warn("Fail to create the Directory!!!");
                return null;
            }
        }
        var fileExtension = getFileExtension(file);
        var targetFilePath= finalFilePath+separator+System.currentTimeMillis()+'.'+fileExtension;
        Path targetPath= Paths.get(targetFilePath);
        try {
            Files.write(targetPath,file.getBytes());
            log.info("File saved in target folder"+targetFilePath);
            return targetFilePath;
        } catch (IOException e) {
            log.error("The File isn't save ",e);
        }
        return null;

    }

    private String getFileExtension(MultipartFile file) {
        String originalFilename = getValidFilename(file);
        if (originalFilename == null) {
            return EMPTY_STRING;
        }

        int lastDotIndex = originalFilename.lastIndexOf('.');
        if (lastDotIndex == -1) {
            log.warn("No file extension found in filename: " + originalFilename);
            return EMPTY_STRING;
        }

        String extension = originalFilename.substring(lastDotIndex + 1).toLowerCase();
        log.info("File extension found: " + extension);
        return extension;
    }

    private String getValidFilename(MultipartFile file) {
        if (file == null || file.getOriginalFilename() == null || file.getOriginalFilename().trim().isEmpty()) {
            log.warn("Invalid file or filename provided.");
            return null;
        }
        return file.getOriginalFilename().trim();
    }
}
