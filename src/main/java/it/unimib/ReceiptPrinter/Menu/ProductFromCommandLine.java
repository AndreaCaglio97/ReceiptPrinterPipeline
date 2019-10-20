package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.CommandLine.InputProductManager;


public class ProductFromCommandLine implements IMenuOption {
    public void menuOptionFunction() {
        InputProductManager inputProductManager = new InputProductManager();
        inputProductManager.inputProductToDBFromCommandLine();
    }
}
