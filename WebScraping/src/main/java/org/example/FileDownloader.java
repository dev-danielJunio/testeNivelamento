package org.example;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class FileDownloader {
    public void downloadFile(String fileUrl, String destination) throws IOException {
        try (ReadableByteChannel rbc = Channels.newChannel(new URL(fileUrl).openStream());
             FileOutputStream fos = new FileOutputStream(destination)) {
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            System.out.println("Baixado: " + destination);
        }
    }
}
