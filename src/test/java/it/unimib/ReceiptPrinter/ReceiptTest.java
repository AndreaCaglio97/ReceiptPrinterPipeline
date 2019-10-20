package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.models.Category;
import it.unimib.ReceiptPrinter.models.Product;
import it.unimib.ReceiptPrinter.models.Receipt;
import org.junit.Test;

import static org.junit.Assert.*;

public class ReceiptTest {
    @Test
    public void addNewProductOnReceiptTest()
    {
        Product product = new Product("IPhone X",true, 1400, Category.GENERAL ,1);
        Receipt r = new Receipt();
        r.addNewProduct(product);
        double total=r.calculationOfTotal();
        assertEquals(1400,total,0.001);
        //assertTrue(r.getReceipt().contains(product));
    }

    @Test
    public void addProductContainedOnReceiptTest()
    {
        Product product1 = new Product("IPhone X",true, 1400, Category.GENERAL ,1);
        Product product2 = new Product("IPhone X",true, 1400, Category.GENERAL ,1);
        Receipt r = new Receipt();
        r.addNewProduct(product1);
        r.addNewProduct(product2);
        double total=r.calculationOfTotal();
        assertEquals(1400,total,0.001);
    }

    @Test
    public void calculationOfTaxTest()
    {
        Receipt r = new Receipt();
        Product book = new Product("Book",false,12.49,Category.BOOK,1);
        Product musicCD = new Product("Music CD",false,14.99,Category.GENERAL,1);
        Product chocolateBar = new Product("Chocolate bar",false,0.85,Category.FOOD,1);
        r.addNewProduct(book);
        r.addNewProduct(musicCD);
        r.addNewProduct(chocolateBar);
        double taxAmount = r.calculationOfTax();
        assertEquals(1.50,taxAmount,0.001);
    }

    @Test
    public void calculationOfTotalTest()
    {
        Receipt r = new Receipt();
        Product boxOfChocolate = new Product("Box of chocolate",true,10.00,Category.FOOD,1);
        Product bottleOfPerfume = new Product("Bottle of perfume",true,47.50,Category.GENERAL,1);
        r.addNewProduct(boxOfChocolate);
        r.addNewProduct(bottleOfPerfume);
        r.calculationOfTax();
        double total = r.calculationOfTotal();
        assertEquals(65.15,total,0.001);
    }

    @Test
    public void calculationOfTaxAndTotalTest()
    {
        Receipt r = new Receipt();
        Product iBottleOfPerfume = new Product("Imported bottle of perfume",true,27.99,Category.GENERAL,1);
        Product bottleOfPerfume = new Product("Bottle of perfume",false,18.99,Category.GENERAL,1);
        Product packetOfHeadachePills = new Product("Packet of headache pills",false,9.75,Category.MEDICINE,1);
        Product boxOfChocolate = new Product("Box of chocolate",true,11.25,Category.FOOD,1);
        r.addNewProduct(iBottleOfPerfume);
        r.addNewProduct(bottleOfPerfume);
        r.addNewProduct(packetOfHeadachePills);
        r.addNewProduct(boxOfChocolate);
        double taxAmount = r.calculationOfTax();
        double total = r.calculationOfTotal();
        assertEquals(6.70,taxAmount,0.001);
        assertEquals(74.68,total,0.001);
    }

    @Test
    public void readProductFromFileCSVTest()
    {
        Receipt receipt = new Receipt();
        receipt.readProductFromFileCSV("productInputTest.csv");
        double total = receipt.calculationOfTotal();
        assertEquals(30.80,total,0.001);
    }
}
