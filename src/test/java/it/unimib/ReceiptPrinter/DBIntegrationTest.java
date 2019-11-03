package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.database.ConnectionManager;
import it.unimib.ReceiptPrinter.database.DBManager;
import org.junit.Test;

import static org.junit.Assert.*;

public class DBIntegrationTest {

    @Test
    public void connectionToDBTest() {
        assertNotNull(ConnectionManager.getConnectionSingleton());
    }

    @Test
    public void getProductsFromDBTest() {
        assertNotEquals(DBManager.viewTable(), -1);
    }

    @Test
    public void getSpecificProductFromDBTest() {
        int numberOfProducts = DBManager.viewTable();
        if(numberOfProducts > 0) {
            assertNotNull(DBManager.productFromDB(1));
        }
    }

}