package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.CommandLine.InputProductManager;


public class ProductsOutputTerminal implements IMenuOption {
    public void menuOptionFunction() {
        InputProductManager inputProductManager = new InputProductManager();
        inputProductManager.inputProductToReceiptFromCommandLine();
    }
}
