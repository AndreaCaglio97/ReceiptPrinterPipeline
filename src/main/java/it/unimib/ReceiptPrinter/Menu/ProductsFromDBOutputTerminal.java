package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.CommandLine.FetchProductManager;


public class ProductsFromDBOutputTerminal implements IMenuOption {
    public void menuOptionFunction() {
        FetchProductManager fetchProductManager = new FetchProductManager();
        fetchProductManager.fetchProductFromDB();
    }
}
