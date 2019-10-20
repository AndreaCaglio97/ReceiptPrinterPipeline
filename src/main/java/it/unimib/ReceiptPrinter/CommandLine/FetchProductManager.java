package it.unimib.ReceiptPrinter.CommandLine;

import it.unimib.ReceiptPrinter.models.Product;
import it.unimib.ReceiptPrinter.models.Receipt;

import java.util.InputMismatchException;
import java.util.Scanner;

import static it.unimib.ReceiptPrinter.CommandLine.InputProductManager.*;
import static it.unimib.ReceiptPrinter.database.DBManager.*;

public class FetchProductManager {

    public void fetchProductFromDB() {
        int idProduct;
        Product product;
        Receipt receipt = new Receipt();
        String other;
        do {
            idProduct=insertProductId();
            product = productFromDB(idProduct);
            receipt.addNewProduct(product);
            other = answerYesNo("Do you want to insert another product in the receipt? (yes/no)");
        }while(other.equalsIgnoreCase("yes"));
        receipt.printReceipt();
    }

    private static int insertProductId() {
        Scanner keyboard = new Scanner(System.in);
        int answer = 0;
        boolean isInputValid;
        do {
            System.out.println("Insert the product Id: ");
            try{
                answer = keyboard.nextInt();
            }catch(InputMismatchException e) {
                keyboard.nextLine();
                answer = -1;
            }finally{
                isInputValid = checkValidInputProductId(answer);
            }
        }while(!isInputValid);
        return answer;
    }

    public static boolean checkValidInputProductId(int idProduct) {
        boolean check;
        if((idProduct < 1)||(productFromDB(idProduct) == null)) {
            System.out.println("ERROR! Invalid input! You must insert an existing product Id");
            check = false;
        }
        else {
            check = true;
        }
        return check;
    }

}
