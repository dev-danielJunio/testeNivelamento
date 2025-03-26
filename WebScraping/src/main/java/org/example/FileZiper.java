package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileZiper {
    public void zipFiles(String folderPath, String zipFileName) throws IOException {
        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFileName))) {
            Files.walk(Paths.get(folderPath))
                    .filter(Files::isRegularFile)
                    .forEach(file -> {
                        try {
                            ZipEntry zipEntry = new ZipEntry(file.getFileName().toString());
                            zos.putNextEntry(zipEntry);
                            Files.copy(file, zos);
                            zos.closeEntry();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
        System.out.println("Arquivos compactados em: " + zipFileName);
    }
}
