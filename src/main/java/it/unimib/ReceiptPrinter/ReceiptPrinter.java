package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.Menu.MenuManager;
import it.unimib.ReceiptPrinter.models.Receipt;

import static it.unimib.ReceiptPrinter.database.DBManager.*;

public class ReceiptPrinter {
    public void inputProductsFromCSVFileOutputTXTFile() {
        Receipt receipt = new Receipt();
        receipt.readProductFromFileCSV("ProductList.csv");
        receipt.writeReceiptOnFileFormatted();
        System.out.println("The location of receipt.text is C:\\i3\\Projects\\ReceiptPrinter\\ReceiptPrinter");
    }


    public void inputProductToDBFromCSVFile() {
        inputDBProductFromFileCSV("insertQueryProductList.csv");
    }

    public static void main( String[] args ) {
        MenuManager menuManager = new MenuManager();
        menuManager.printMenu();
    }
}
