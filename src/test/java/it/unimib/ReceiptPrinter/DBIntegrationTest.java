package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.database.ConnectionManager;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertNotNull;

public class DBIntegrationTest {

    @Test
    public void connectionToDBTest() {
        assertNotNull(ConnectionManager.getConnectionSingleton());
    }

    @Test
    public void testQueryToDB1() {
        assertTrue(true);
    }

    @Test
    public void testQueryToDB2() {
        assertTrue(true);
    }

}