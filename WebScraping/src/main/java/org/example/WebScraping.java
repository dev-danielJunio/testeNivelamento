package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.*;
import java.nio.file.*;

public class WebScraping {
    private static final String BASE_URL = "https://www.gov.br/ans/pt-br/acesso-a-informacao/participacao-da-sociedade/atualizacao-do-rol-de-procedimentos";
    private static final String OUTPUT_FOLDER = "downloads/";

    public static void main(String[] args) {
        try {
            Files.createDirectories(Paths.get(OUTPUT_FOLDER));

            FileDownloader fileDownloader = new FileDownloader();

            FileZiper fileZiper = new FileZiper();

            Document doc = Jsoup.connect(BASE_URL).get();

            //Buscando links no pdf...
            Elements pdfLinks = doc.select("a[href$=.pdf]");
            for (Element link : pdfLinks) {
                String pdfUrl = link.absUrl("href");
                if(pdfUrl.toLowerCase().contains("anexo_i") || pdfUrl.toLowerCase().contains("anexo_ii")) {
                    String fileName = pdfUrl.substring(pdfUrl.lastIndexOf("/") + 1);
                    fileDownloader.downloadFile(pdfUrl, OUTPUT_FOLDER + fileName);
                }
            }

            fileZiper.zipFiles(OUTPUT_FOLDER, "anexos.zip");
            System.out.println("Download e compactação concluídos!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
