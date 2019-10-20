package it.unimib.ReceiptPrinter.Menu;

import it.unimib.ReceiptPrinter.database.ConnectionManager;
import it.unimib.ReceiptPrinter.models.Menu;

import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuManager {

    static Scanner keyboard = new Scanner(System.in);
    private int choice;

    private void setChoice(int choice) {
        this.choice = choice;
    }

    private void printList() {
        System.out.println("Menu':\n\t" +
                           "1)Insert products to DB from the command line\n\t" +
                           "2)Create a receipt and print it in the command line\n\t" +
                           "3)Create a receipt from a .csv file and print it on a .txt file\n\t" +
                           "4)Get products from DB and print a receipt in the command line\n\t" +
                           "5)Insert products to DB from a .csv file\n\t" +
                           "6)Exit");
    }

    private void insertChoice() {
        boolean isInputValid;
        do {
            printList();
            try{
                setChoice(keyboard.nextInt());
            }catch(InputMismatchException e) {
                keyboard.nextLine();
                setChoice(-1);
            }finally{
                isInputValid = checkValidChoice();
            }
        }while(!isInputValid);
    }

    private boolean checkValidChoice() {
        boolean check;
        if((choice < 1)||(choice > 6)) {
            System.out.println("ERROR! Invalid input! You must insert a number between 1 and 6");
            check = false;
        } else {
            check = true;
        }
        return check;
    }


    private void selectFunctionAccordingToChoice(Menu menu) {
        menu.getOption(choice).menuOptionFunction();
    }

    private Menu createMenuOptions() {
        Menu menu = new Menu();
        menu.addOption(1,new ProductFromCommandLine());
        menu.addOption(2,new ProductsOutputTerminal());
        menu.addOption(3,new CSVFileOutputTXTFile());
        menu.addOption(4,new ProductsFromDBOutputTerminal());
        menu.addOption(5,new ProductToDBFromCSVFile());
        menu.addOption(6,new CloseConnectionToDB());
        return menu;
    }

    public void printMenu() {
        ConnectionManager.getConnectionSingleton();
        Menu menu = createMenuOptions();
        do {
            insertChoice();
            selectFunctionAccordingToChoice(menu);
        }while(choice != 6);
    }
}
