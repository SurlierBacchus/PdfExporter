package com.tilgnerka.pdf.file_handler;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.IBlockElement;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileHandler {
    private File folder;
    private FileFormat format;

    private Document document;

    public FileHandler(File folder, FileFormat format, String destination) {
        this.folder = folder;
        this.format = format;

        try {
            createPdf(destination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        queryFolders(folder);
    }

    private void createPdf(String destination) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdf = new PdfDocument(writer);
        document = new Document(pdf);

        try {
            PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
            document.add(new Paragraph(format.getTitle()).setFont(font).setTextAlignment(TextAlignment.CENTER));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void closeDocument() {
        document.close();
    }

    private void queryFolders(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                queryFolders(fileEntry);
            } else {
                processFile(fileEntry);
            }
        }
    }

    private void processFile(File file) {
        Scanner scanner;
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException e) {
            return;
        }

        StringBuilder currentToken = new StringBuilder();
        char tokenID = ' ';
        while (scanner.hasNextLine()){
            char[] line = scanner.nextLine().toCharArray();

            for (char c : line){
                if (format.getIDs().contains(String.valueOf(c))){
                    processToken(currentToken, tokenID);
                    tokenID = c;
                }
                else {
                    currentToken.append(c);
                }
            }
        }
    }

    private void processToken(StringBuilder token, char ID){
        if (ID == format.getTopicID()){
            try {
                PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_BOLD);
                document.add(new Paragraph(token.toString()).setFont(font));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
