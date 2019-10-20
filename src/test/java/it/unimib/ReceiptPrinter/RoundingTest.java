package it.unimib.ReceiptPrinter;

import static it.unimib.ReceiptPrinter.utilities.Rounding.*;
import static org.junit.Assert.*;

import org.junit.Test;

public class RoundingTest {
    @Test
    public void inputNumberOutputTheSameNumberRoundingTest()
    {
        assertEquals(0.05,roundingUpForExcess5Cents(0.05),0.001);
    }

    @Test
    public void inputNumberWith3DecimalPlacesOutputNumberRoundedUpForExcess5CentsTest()
    {
        assertEquals(0.05,roundingUpForExcess5Cents(0.001),0.001);
    }

    @Test
    public void inputNumberWith3DecimalPlacesOutputNumberRoundedUpForExcess5CentsSecondTest()
    {
        assertEquals(0.1,roundingUpForExcess5Cents(0.051),0.001);
    }

    @Test
    public void inputNumberWith2DecimalPlacesOutputNumberRoundedUpForExcess5CentsTest()
    {
        assertEquals(0.15,roundingUpForExcess5Cents(0.11),0.001);
    }

}
