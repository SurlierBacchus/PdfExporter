package com.tilgnerka.pdf;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.tilgnerka.pdf.file_handler.FileFormat;
import com.tilgnerka.pdf.file_handler.FileHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    private Main(){
        File folder = new File("C:/Users/Robo/Desktop/Informatika");
        FileFormat format = new FileFormat("Maturitné otázky 2017/2018 - Vypracovanie", '&', '#', '%');

        FileHandler fileHandler = new FileHandler(folder, format, "out/test.pdf");
        fileHandler.closeDocument();

        /*
        try {
            createPdf("out/test.pdf");
        } catch (IOException e){
            e.printStackTrace();
        }*/
    }

    public void createPdf(String dest) throws IOException {
        //Initialize PDF writer
        PdfWriter writer = new PdfWriter(dest);

        //Initialize PDF document
        PdfDocument pdf = new PdfDocument(writer);

        // Initialize document
        Document document = new Document(pdf);

        // Create a PdfFont
        PdfFont font = PdfFontFactory.createFont(StandardFonts.TIMES_ROMAN);
        // Add a Paragraph
        document.add(new Paragraph("iText is:").setFont(font));
        // Create a List
        List list = new List()
                .setSymbolIndent(12)
                .setListSymbol("\u2022")
                .setFont(font);
        // Add ListItem objects
        list.add(new ListItem("Never gonna give you up"))
                .add(new ListItem("Never gonna let you down"))
                .add(new ListItem("Never gonna run around and desert you"))
                .add(new ListItem("Never gonna make you cry"))
                .add(new ListItem("Never gonna say goodbye"))
                .add(new ListItem("Never gonna tell a lie and hurt you"));
        // Add the list
        document.add(list);

        //Close document
        document.close();
    }
}
