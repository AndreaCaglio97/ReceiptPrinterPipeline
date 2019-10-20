package it.unimib.ReceiptPrinter.CommandLine;

import it.unimib.ReceiptPrinter.models.*;

import java.util.InputMismatchException;
import java.util.Scanner;

import static it.unimib.ReceiptPrinter.database.DBManager.*;
import static it.unimib.ReceiptPrinter.models.Category.*;

public class InputProductManager {

    public void inputProductToDBFromCommandLine() {
        String other;
        Product product;
        do {
            product = createProduct();
            insertSingleProductToDB(product);
            other = answerYesNo("Do you want to insert another product? (yes/no)");
        }while(other.equalsIgnoreCase("yes"));
    }

    public void inputProductToReceiptFromCommandLine() {
        String other;
        Product product;
        Receipt receipt = new Receipt();
        do {
            product = createProductReceipt();
            receipt.addNewProduct(product);
            other = answerYesNo("Do you want to insert another product in the receipt? (yes/no)");
        }while(other.equalsIgnoreCase("yes"));
        receipt.printReceipt();
    }

    private static Product createProduct() {
        Product product = new Product();
        product.setName(insertProductName());
        product.setImported(isProductImported());
        product.setPrice(insertProductPrice());
        product.setCategory(insertProductCategory());
        return product;
    }

    private static Product createProductReceipt() {
        Product product = createProduct();
        product.setQuantity(insertProductQuantity());
        return product;
    }

    private static String insertProductName() {
        Scanner keyboard = new Scanner(System.in);
        System.out.println("Insert the product name: ");
        return keyboard.nextLine();
    }

    private static boolean isProductImported() {
        boolean isImported = false;
        String answer = answerYesNo("Is the product imported? (yes/no) ");
        if(answer.equalsIgnoreCase("yes"))
            isImported = true;
        return isImported;
    }

    private static double insertProductPrice() {
        Scanner keyboard = new Scanner(System.in);
        double price = 0;
        boolean isInputValid;
        do {
            System.out.println("Insert the product price: ");
            try{
                price = keyboard.nextDouble();
            }catch(InputMismatchException e)
            {
                keyboard.nextLine();
                price = -1;
            }finally{
                isInputValid = checkValidInputPrice(price);
            }
        }while(!isInputValid);
        return price;
    }

    public static boolean checkValidInputPrice(double number) {
        boolean check;
        if(number < 0.01) {
            System.out.println("ERROR! Invalid input! The price must be positive");
            check = false;
        }
        else {
            check = true;
        }
        return check;
    }


    private static Category insertProductCategory() {
        Scanner keyboard = new Scanner(System.in);
        int answer = 0;
        boolean isInputValid;
        do {
            System.out.println("Choose the product Category:\n\t1)FOOD\n\t2)BOOK\n\t3)MEDICINE\n\t4)GENERAL\n\t5)NOT_GENERAL");
            try{
                answer = keyboard.nextInt();
            }catch(InputMismatchException e) {
                keyboard.nextLine();
                answer = -1;
            }finally{
                isInputValid = checkValidInputCategory(answer);
            }
        }while(!isInputValid);
        return getCategory(answer);
    }

    public static boolean checkValidInputCategory(int number) {
        boolean check;
        if((number < 1)||(number > 5)) {
            System.out.println("ERROR! Invalid input! You must insert a number between 1 and 5");
            check = false;
        }
        else {
            check = true;
        }
        return check;
    }

    private static int insertProductQuantity() {
        Scanner keyboard = new Scanner(System.in);
        int quantity = 0;
        boolean isInputValid;
        do {
            System.out.println("Insert the product quantity: ");
            try{
                quantity = keyboard.nextInt();
            }catch(InputMismatchException e)
            {
                keyboard.nextLine();
                quantity = -1;
            }finally{
                isInputValid = checkValidInputQuantity(quantity);
            }
        }while(!isInputValid);
        return quantity;
    }

    public static boolean checkValidInputQuantity(int number) {
        boolean check;
        if(number < 1) {
            System.out.println("ERROR! Invalid input! The quantity must be positive");
            check = false;
        }
        else {
            check = true;
        }
        return check;
    }

    public static String answerYesNo(String message) {
        Scanner keyboard = new Scanner(System.in);
        String answer;
        do {
            System.out.println(message);
            answer = keyboard.nextLine();
            if((!answer.equalsIgnoreCase("yes")) && (!answer.equalsIgnoreCase("no")))
                System.out.println("ERROR! Invalid answer! You must answer 'yes' or 'no' ");
        }while((!answer.equalsIgnoreCase("yes")) && (!answer.equalsIgnoreCase("no")));
        return answer;
    }
}
