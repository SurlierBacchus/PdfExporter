package com.tilgnerka.pdf.file_handler;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
import com.itextpdf.kernel.color.Color;
import com.itextpdf.kernel.color.DeviceRgb;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.property.HorizontalAlignment;
import com.itextpdf.layout.property.TextAlignment;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class FileHandler {
    private File folder;
    private FileFormat format;

    private Document document;
    private PdfFont font;
    private PdfFont fontBold;

    private Map<Character, Integer> counts;

    public FileHandler(File folder, FileFormat format, String destination) {
        this.folder = folder;
        this.format = format;

        createFonts();

        counts = new HashMap<Character, Integer>();
        for (char c : format.getIDs().toCharArray()) counts.put(c, 1);

        try {
            createPdf(destination);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        queryFolders(folder);
    }

    private void createFonts(){
        try {
            FontProgram normalProgram = FontProgramFactory.createFont("data/font/Open_Sans/OpenSans-Regular.ttf");
            font = PdfFontFactory.createFont(normalProgram, PdfEncodings.CP1250);

            FontProgram boldProgram = FontProgramFactory.createFont("data/font/Open_Sans/OpenSans-ExtraBold.ttf");
            fontBold = PdfFontFactory.createFont(boldProgram, PdfEncodings.CP1250);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void createPdf(String destination) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(destination);
        PdfDocument pdf = new PdfDocument(writer);
        document = new Document(pdf);

        document.add(new Paragraph(format.getTitle())
                .setFont(fontBold)
                .setBold()
                .setFontSize(23f)
                .setTextAlignment(TextAlignment.CENTER)
        );

    }

    public void closeDocument() {
        document.close();
    }

    private void queryFolders(final File folder) {
        for (final File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                document.add(new Paragraph("Vypracovanie podľa: " + fileEntry.getName())
                        .setFont(font)
                        .setFontSize(12)
                        .setFontColor(new DeviceRgb(16, 208, 229))
                        .setMarginBottom(-15f)
                );

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
            e.printStackTrace();
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
                    currentToken = new StringBuilder();
                }
                else {
                    currentToken.append(c);
                }
            }
            currentToken.append("\n");
        }
        processToken(currentToken, tokenID);
    }

    private void processToken(StringBuilder token, char ID){
        if (ID == format.getTopicID()){
            document.add(
                    new Paragraph(String.format("Úloha %d: %s", counts.get(format.getTopicID()), token.toString()))
                            .setFont(fontBold)
                            .setFontSize(16f)
                            .setBold()
                            .setMultipliedLeading(0f)
                            .setMarginTop(20f)
            );

            counts.put(format.getTopicID(), counts.get(format.getTopicID()) + 1);
            for (char c : counts.keySet()) if (c != format.getTopicID()) counts.put(c, 1);
        }
        else if (ID == format.getQuestionID()){
            document.add(
                    new Paragraph(String.format("%d. %s", counts.get(format.getQuestionID()), token.toString()))
                            .setFont(fontBold)
                            .setFontColor(new DeviceRgb(52, 173, 0))
                            .setMultipliedLeading(1f)
                            .setMarginTop(10f)
            );

            counts.put(format.getQuestionID(), counts.get(format.getQuestionID()) + 1);
        }
        else if (ID == format.getAnswerID()){
            document.add(
                    new Paragraph(token.toString())
                            .setFont(font)
                            .setMarginTop(-2f)
                            .setMarginLeft(20f)
                            .setTextAlignment(TextAlignment.JUSTIFIED)
                            .setMultipliedLeading(.95f)
            );
        }
        else if (ID == format.getSubQuestionID()){
            document.add(
                    new Paragraph(String.format("%s) %s", String.valueOf((char) ((int) 'a' + counts.get(format.getSubQuestionID()) - 1)), token.toString()))
                            .setFont(fontBold)
                            .setMarginLeft(10f)
                            .setFontColor(new DeviceRgb(104, 196, 136))
            );

            counts.put(format.getSubQuestionID(), counts.get(format.getSubQuestionID()) + 1);
        }
    }
}
