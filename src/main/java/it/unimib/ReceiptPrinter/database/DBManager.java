package it.unimib.ReceiptPrinter.database;

import it.unimib.ReceiptPrinter.models.Category;
import it.unimib.ReceiptPrinter.models.Product;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.*;
import java.util.Scanner;

public class DBManager {

    public static void viewTable(Connection conn)  {

        Statement stmt = null;
        String query = "SELECT  name,  price, category\n" +
                        "FROM product\n" +
                        "WHERE is_imported = true\n" +
                        "ORDER BY id_product";
        try {
            stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(query);
            while (rs.next()) {
                String name = rs.getString("name");
                double price = rs.getDouble("price");
                Category category =  Category.valueOf(rs.getString("category"));
                System.out.println(name + "\t" + price + "\t" + category);
            }
        } catch (SQLException e ) {
            System.out.println("ERROR! query NOT successfully completed");
        } finally {
            SQLExceptionHandling(stmt);
        }
    }

    public static Product productFromDB(int id_product)  {
        ConnectionManager connManager = ConnectionManager.getConnectionSingleton();
        Statement stmt = connManager.createStatement();
        Product product = null;
        String query = "SELECT  *\n" +
                "FROM product\n" +
                "WHERE id_product = " + id_product;
        try {
            ResultSet rs = stmt.executeQuery(query);
            rs.next();
            String name = rs.getString("name");
            boolean isImported = rs.getBoolean("is_imported");
            double price = rs.getDouble("price");
            Category category =  Category.valueOf(rs.getString("category"));
            product = new Product(name,isImported,price,category,1);
        } catch (SQLException e ) {
            System.out.println("ERROR! query NOT successfully completed");
        } finally {
            SQLExceptionHandling(stmt);
        }
        return product;
    }

    private static void SQLExceptionHandling(Statement stmt) {
        try {
            if (stmt != null) {
                stmt.close();
            }
        } catch (SQLException e){
            System.err.println("ERROR! query NOT successfully completed");
        }
    }

    public static void inputDBProductFromFileCSV(String fileName) {
        try {
            Scanner inputStream = new Scanner(new File(fileName));
            inputStream.nextLine();
            while (inputStream.hasNextLine()) {
                readSingleProductFromFileCSV(inputStream);
            }
            inputStream.close();
        } catch(FileNotFoundException e) {
            System.out.println("Cannot find file " + fileName);
        }
    }

    private static void readSingleProductFromFileCSV(Scanner inputStream) {
        String line;
        line = inputStream.nextLine();
        String[] productAttributes = line.split(",");
        Product product = parseStringLineToProduct(productAttributes);
        insertSingleProductToDB(product);
    }

    private static Product parseStringLineToProduct(String[] productAttributes) {
        Product product = new Product();
        product.setName(productAttributes[0]);
        product.setImported(Boolean.parseBoolean(productAttributes[1]));
        product.setPrice(Double.parseDouble(productAttributes[2]));
        product.setCategory(Category.valueOf(productAttributes[3]));
        return product;
    }


    public static void insertSingleProductToDB(Product product)  {
        ConnectionManager connManager = ConnectionManager.getConnectionSingleton();
        Statement stmt = connManager.createStatement();

        String query = "INSERT INTO product(\n" +
                "\tname, is_imported, price, category)\n" +
                "\tVALUES ('" + product.getName() + "', " + product.isImported() + ", " + product.getPrice() + ", '" + product.getCategory() + "');";
        try {
            stmt.executeUpdate(query);
            System.out.println("Record has been inserted into product table!");
        } catch (SQLException e ) {
            System.err.println("ERROR! query NOT successfully completed");
            System.out.println(e.getMessage());
        } finally {
            SQLExceptionHandling(stmt);
        }
    }
}
