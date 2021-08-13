package com.example.pfe.service;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileService {

    private final Path rootLocation = Paths.get("upload");

    public void copy(MultipartFile file) {
        try {
            Files.copy(file.getInputStream(), this.rootLocation.resolve(file.getOriginalFilename()));
        } catch (IOException e) {
        }
    }

    public Path getFile(String filename) {
        return rootLocation.resolve(filename);
    }

    public List<String> getAll() {

        List<String> fileNames = new ArrayList<>();

        try {

            DirectoryStream<Path> directoryStream = Files.newDirectoryStream(rootLocation);

            for (Path path : directoryStream) {
                fileNames.add(path.toString());
            }

        } catch (IOException e) {
        }

        return fileNames;
    }

    public Resource getResource(String filename) {

        Path file = rootLocation.resolve(filename);

        try {
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                return resource;
            }

        } catch (MalformedURLException e) {
        }

        return null;
    }

    public void empty() {
        FileSystemUtils.deleteRecursively(rootLocation.toFile());
    }

    public void init() {
        try {
            Files.createDirectory(rootLocation);
        } catch (IOException e) {
        }
    }
}
