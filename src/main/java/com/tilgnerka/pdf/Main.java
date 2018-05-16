package com.tilgnerka.pdf;

import com.tilgnerka.pdf.file_handler.FileFormat;
import com.tilgnerka.pdf.file_handler.FileHandler;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        new Main();
    }

    private Main(){
        File folder = new File("in");
        FileFormat format = new FileFormat("Maturitné otázky 2017/2018 - Vypracovanie", '&', '#', '%', '@');

        FileHandler fileHandler = new FileHandler(folder, format, "out/MaturitneOtazkyInf.pdf");
        fileHandler.closeDocument();
    }
}
