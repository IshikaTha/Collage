package com.ishika.collageapp.eBook;

public class PdfData {
    private String pdfName, title, uniqueKey, file;

    public PdfData() {
    }

    public PdfData(String pdfName, String title, String uniqueKey, String file) {
        this.pdfName = pdfName;
        this.title = title;
        this.uniqueKey = uniqueKey;
        this.file = file;
    }

    public String getPdfName() {
        return pdfName;
    }

    public void setPdfName(String pdfName) {
        this.pdfName = pdfName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUniqueKey() {
        return uniqueKey;
    }

    public void setUniqueKey(String uniqueKey) {
        this.uniqueKey = uniqueKey;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }
}
