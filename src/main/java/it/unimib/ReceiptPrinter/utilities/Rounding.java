package it.unimib.ReceiptPrinter.utilities;

public class Rounding {
    public static double roundingUpForExcess5Cents(double number)
    {
        return Math.ceil(number * 20) / 20;
    }
}
