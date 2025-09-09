package com.shop.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Bean;

@Configuration
public class PrintConfig {

    @Bean
    public PrinterProperties printerProperties() {
        return new PrinterProperties();
    }

    public static class PrinterProperties {
        private String printerName = "Default Printer";
        private int paperWidth = 80; // mm
        private int paperHeight = 297; // mm (A4 height)

        // Getters and setters
        public String getPrinterName() {
            return printerName;
        }

        public void setPrinterName(String printerName) {
            this.printerName = printerName;
        }

        public int getPaperWidth() {
            return paperWidth;
        }

        public void setPaperWidth(int paperWidth) {
            this.paperWidth = paperWidth;
        }

        public int getPaperHeight() {
            return paperHeight;
        }

        public void setPaperHeight(int paperHeight) {
            this.paperHeight = paperHeight;
        }
    }
}