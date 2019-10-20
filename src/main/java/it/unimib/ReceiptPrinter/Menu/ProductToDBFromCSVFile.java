package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.ReceiptPrinter;


public class ProductToDBFromCSVFile implements IMenuOption {
    public void menuOptionFunction() {
        ReceiptPrinter receiptPrinter = new ReceiptPrinter();
        receiptPrinter.inputProductToDBFromCSVFile();
    }
}
