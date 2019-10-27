package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.database.ConnectionManager;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;

public class DBIntegrationTest {

    @Test
    public void connectionToDBTest() {
        assertNotNull(ConnectionManager.getConnectionSingleton());
    }

}