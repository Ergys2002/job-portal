package com.exh.jobseeker.service;

import com.exh.jobseeker.exception.FileNotSavedException;
import com.exh.jobseeker.model.entity.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileService {
    private final Path cvPath;

    public FileService(@Value("${file.upload.dir}") String uploadDir) {
        this.cvPath = Paths.get(uploadDir).toAbsolutePath().normalize();

        try {
            Files.createDirectories(this.cvPath);
        } catch (IOException e) {
            throw new RuntimeException("Could not create directory where the uploaded files will be stored.", e);
        }
    }
    public String saveFile(MultipartFile file, User user) {
        try {
            if (file == null || file.isEmpty()) {
                throw new IllegalArgumentException("Cannot save empty file.");
            }

            String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
            String fileExtension = getFileExtension(originalFileName);

            String fileName = user.getId() + "." + fileExtension;

            Path targetLocation = this.cvPath.resolve(fileName);

            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return fileName;
        } catch (IOException ex) {
            throw new FileNotSavedException("Could not save file " + file.getOriginalFilename());
        }
    }

    private String getFileExtension(String filename) {
        if (filename == null || filename.lastIndexOf(".") == -1) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
    }
}
