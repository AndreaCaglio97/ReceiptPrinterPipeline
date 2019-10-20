package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.database.ConnectionManager;

public class CloseConnectionToDB implements IMenuOption {
    public void menuOptionFunction() {
        ConnectionManager.getConnectionSingleton().closeConnectionToDB();
    }
}
