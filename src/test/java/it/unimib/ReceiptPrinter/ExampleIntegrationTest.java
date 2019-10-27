package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.database.ConnectionManager;
import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class ExampleIntegrationTest {

    @Test
    public void connectionToDBTest() {
        assertNull(ConnectionManager.getConnectionSingleton());
    }

}