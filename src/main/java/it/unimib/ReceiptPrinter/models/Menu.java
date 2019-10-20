package it.unimib.ReceiptPrinter.models;

import it.unimib.ReceiptPrinter.Menu.IMenuOption;

import java.util.Map;
import java.util.TreeMap;

public class Menu {
    private Map<Integer,IMenuOption> menu;

    public Menu() {
        menu = new TreeMap<Integer, IMenuOption>();
    }

    public void addOption(int optionNumber, IMenuOption option) {
        menu.put(optionNumber,option);
    }

    public IMenuOption getOption(int optionNumber) {
        return menu.get(optionNumber);
    }
}
