package org.example.creational;

import org.example.creational.factory.Document;
import org.example.creational.factory.DocumentFactory;

public class DocumentEditor {

    public void openDocument(String type) {
        Document doc = DocumentFactory.createDocument(type);
        doc.open();
    }

    public static void main(String[] args) {
        DocumentEditor editor = new DocumentEditor();

        // TODO: Test opening different types of documents (PDF, Word, HTML)
        editor.openDocument("PDF");
        editor.openDocument("Word");
        editor.openDocument("HTML");
    }
}
