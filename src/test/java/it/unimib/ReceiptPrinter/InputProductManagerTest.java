package it.unimib.ReceiptPrinter;

import org.junit.Test;

import static it.unimib.ReceiptPrinter.CommandLine.InputProductManager.*;
import static org.junit.Assert.*;

public class InputProductManagerTest {

    @Test
    public void inputNegativePriceReturnFalseTest() {
        assertFalse(checkValidInputPrice(-10));
    }

    @Test
    public void inputPositivePriceReturnTrueTest() {
        assertTrue(checkValidInputPrice(10));
    }

    @Test
    public void inputTooSmallPriceReturnFalseTest() {
        assertFalse(checkValidInputPrice(0.009));
    }

    @Test
    public void inputExceptionPriceReturnFalseTest() {
        assertFalse(checkValidInputPrice(-1));
    }

    @Test
    public void inputCategoryNumberBetween1And5ReturnTrueTest() {
        assertTrue(checkValidInputCategory(3));
    }

    @Test
    public void inputCategoryNumberGreaterThan5ReturnFalseTest() {
        assertFalse(checkValidInputCategory(6));
    }

    @Test
    public void inputCategoryNumberLessThan1ReturnFalseTest() {
        assertFalse(checkValidInputCategory(0));
    }

    @Test
    public void inputExceptionCategoryNumberReturnFalseTest() {
        assertFalse(checkValidInputCategory(-1));
    }

    @Test
    public void inputLessThan1QuantityReturnFalseTest() {
        assertFalse(checkValidInputQuantity(0));
    }

    @Test
    public void inputPositiveQuantityReturnTrueTest() {
        assertTrue(checkValidInputQuantity(10));
    }

    @Test
    public void inputExceptionQuantityReturnFalseTest() {
        assertFalse(checkValidInputQuantity(-1));
    }
}