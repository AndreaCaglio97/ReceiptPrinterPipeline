package it.unimib.ReceiptPrinter;

import it.unimib.ReceiptPrinter.models.Category;
import it.unimib.ReceiptPrinter.models.Product;
import org.junit.Test;

import static org.junit.Assert.*;

public class ProductTest {
    @Test
    public void createNewProductTest()
    {
        Product product = new Product("IPhone X",true, 1400, Category.GENERAL ,1);
        assertEquals("IPhone X",product.getName());
        assertEquals(true,product.isImported());
        assertEquals(1400,product.getPrice(),0.001);
        assertEquals(Category.GENERAL,product.getCategory());
        assertEquals(1,product.getQuantity());
    }

    private void calculationOfTaxTest(Product product,double expectedPrice,double expectedTaxAmount)
    {
        double taxAmount=product.singleProductTax();
        double price=product.getPrice();
        assertEquals(expectedPrice,price,0.001);
        assertEquals(expectedTaxAmount,taxAmount,0.001);
    }

    @Test
    public void calculationOfTaxOfImportedGeneralProductTest()
    {
        Product productBeforeTax = new Product("Bottle of perfume",true, 47.50, Category.GENERAL ,1);
        calculationOfTaxTest(productBeforeTax,54.65,7.15);
    }

    @Test
    public void calculationOfTaxOfNotImportedGeneralProductTest()
    {
        Product productBeforeTax = new Product("Music CD",false, 14.99, Category.GENERAL ,1);
        calculationOfTaxTest(productBeforeTax,16.49,1.5);
    }

    @Test
    public void calculationOfTaxOfImportedNotGeneralProductTest()
    {
        Product productBeforeTax = new Product("Box of chocolates",true, 10.00, Category.FOOD ,1);
        calculationOfTaxTest(productBeforeTax,10.50,0.50);
    }

    @Test
    public void calculationOfTaxOfNotImportedNotGeneralProductTest()
    {
        Product productBeforeTax = new Product("Book",false, 12.49, Category.BOOK,1);
        calculationOfTaxTest(productBeforeTax,12.49,0);
    }
}
