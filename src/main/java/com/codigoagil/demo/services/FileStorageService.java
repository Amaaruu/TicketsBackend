package com.codigoagil.demo.services;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    private final Path rootLocation = Paths.get("uploads");

    public FileStorageService() {
        try {
            Files.createDirectories(rootLocation);
        } catch (IOException e) {
            throw new RuntimeException("No se pudo inicializar la carpeta de almacenamiento");
        }
    }

    public String storeFile(byte[] content, String originalFilename) {
        try {
            String extension = "";
            int i = originalFilename.lastIndexOf('.');
            if (i > 0) {
                extension = originalFilename.substring(i);
            }
            String generatedName = UUID.randomUUID().toString() + extension;
            Path destinationFile = this.rootLocation.resolve(Paths.get(generatedName)).normalize().toAbsolutePath();
            
            Files.write(destinationFile, content);
            return destinationFile.toString();
        } catch (IOException e) {
            throw new RuntimeException("Fallo al almacenar el archivo", e);
        }
    }
}