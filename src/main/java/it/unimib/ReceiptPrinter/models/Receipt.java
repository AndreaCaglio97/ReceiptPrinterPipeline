package it.unimib.ReceiptPrinter.models;

import java.io.*;
import java.util.*;

public class Receipt {
    private List<Product> receipt;

    public Receipt()
    {
        receipt = new ArrayList<Product>();
    }

    public List<Product> getReceipt() {
        return receipt;
    }

    public void addNewProduct(Product product) {
        if(! receipt.contains(product))
            receipt.add(product);
    }

    public double calculationOfTax() {
        double taxAmount=0;
        for(Product product : receipt) {
            taxAmount += product.singleProductTax();
        }
        return taxAmount;
    }

    public double calculationOfTotal() {
        double total=0;
        for(Product product : receipt) {
            total += product.getPrice();
        }
        return total;
    }

    public void printReceipt() {
        double taxAmount = calculationOfTax();
        double total = calculationOfTotal();
        System.out.println("RECEIPT");
        System.out.println();
        for(Product product : receipt) {
            System.out.println(product.toString());
        }
        System.out.println();
        System.out.println("Total taxes: " + String.format( "%.2f", taxAmount ) + "€");
        System.out.println("Total: " + String.format( "%.2f", total ) + "€");
    }

    public void readProductFromFileCSV(String fileName) {
        try {
        Scanner inputStream = new Scanner(new File(fileName));
        inputStream.nextLine();
        while (inputStream.hasNextLine()) {
            addNewProductFromCSV(inputStream);
        }
        inputStream.close();
    } catch(FileNotFoundException e) {
        System.out.println("Cannot find file " + fileName);
    }
    }

    private void addNewProductFromCSV(Scanner inputStream) {
        String line;
        line = inputStream.nextLine();
        String[] productAttributes = line.split(",");
        String name = productAttributes[0];
        boolean isImported = Boolean.parseBoolean(productAttributes[1]);
        double price = Double.parseDouble(productAttributes[2]);
        Category category = Category.valueOf(productAttributes[3]);
        int quantity = Integer.parseInt(productAttributes[4]);
        Product product = new Product(name,isImported,price,category,quantity);
        addNewProduct(product);
    }

    public void writeReceiptOnFile() {
        String fileName = "receipt.txt";
        FileWriter outputStream = null;
        double taxAmount = calculationOfTax();
        double total = calculationOfTotal();
        try {
            outputStream = new FileWriter(fileName);
            receiptOnFile(outputStream, taxAmount, total);
        } catch (IOException e) {
            System.out.println("Error with file " + fileName);
            System.exit(0);
        } finally {
            try {
                if(outputStream!=null) outputStream.close();
            } catch (IOException e) {
                System.out.println("Impossible to close file " + fileName);
            }
        }
    }

    private void receiptOnFile(FileWriter outputStream, double taxAmount, double total) throws IOException {
        outputStream.write("RECEIPT" + "\n\n");
        for(Product product : receipt) {
            outputStream.write(product.toString() + "\n");
        }
        outputStream.write("\n");
        outputStream.write("Total taxes: " + String.format( "%.2f", taxAmount ) + "€" + "\n");
        outputStream.write("Total:" + String.format( "%.2f", total ) + "€" + "\n");
    }

    public void writeReceiptOnFileFormatted() {
        String fileName = "receipt.txt";
        PrintWriter outputStream = null;
        double taxAmount = calculationOfTax();
        double total = calculationOfTotal();
        try {
            outputStream = new PrintWriter(new FileOutputStream(fileName));
        } catch(FileNotFoundException e) {
            System.out.println("Error opening the file " + fileName);
            System.exit(0);
        }
        receiptOnFileFormatted(outputStream, taxAmount, total);
        outputStream.close( );
    }

    private void receiptOnFileFormatted(PrintWriter outputStream, double taxAmount, double total) {
        outputStream.println("RECEIPT");
        outputStream.println();
        for(Product product : receipt) {
            outputStream.println(product.toString());
        }
        outputStream.println();
        outputStream.println( "Total taxes: " + String.format( "%.2f", taxAmount ) + "€" + "\n");
        outputStream.println("Total:" + String.format( "%.2f", total ) + "€" + "\n");
    }
}
