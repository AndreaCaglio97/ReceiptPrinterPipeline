package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.ReceiptPrinter;


public class CSVFileOutputTXTFile implements IMenuOption {
    public void menuOptionFunction() {
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        receiptPrinter.inputProductsFromCSVFileOutputTXTFile();
    }
}
