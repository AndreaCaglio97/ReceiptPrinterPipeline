package it.unimib.ReceiptPrinter.models;

import static it.unimib.ReceiptPrinter.utilities.Rounding.*;

public class Product {
    private String name;
    private boolean isImported;
    private double price;
    private Category category;
    private int quantity;


    public Product(String name,boolean isImported,double price,Category category,int quantity) {
        this.name=name;
        this.isImported=isImported;
        this.price=price;
        this.category=category;
        this.quantity=quantity;
    }

    public Product() {
        this(null,false,0,Category.GENERAL,1);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImported(boolean imported) {
        isImported = imported;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public boolean isImported() {
        return isImported;
    }

    public double getPrice() {
        return price;
    }

    public Category getCategory() {
        return category;
    }

    public int getQuantity() {
        return quantity;
    }

    public double singleProductTax() {
        int taxPercentage = 0;
        double taxAmount = 0;
        if(getCategory() == Category.GENERAL)
        {
            taxPercentage += 10;
        }
        if(isImported())
        {
            taxPercentage += 5;
        }
        taxAmount = calculationOfPriceAndTaxAmount(taxPercentage);
        return taxAmount;
    }

    private double calculationOfPriceAndTaxAmount(int taxPercentage) {
        double price;
        double taxAmount;
        price=getPrice();
        taxAmount = (taxPercentage * price) / 100;
        taxAmount = roundingUpForExcess5Cents(taxAmount);
        price = (price + taxAmount) * getQuantity();
        taxAmount *= getQuantity();
        setPrice(price);
        return taxAmount;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (isImported != product.isImported) return false;
        if (Double.compare(product.price, price) != 0) return false;
        if (quantity != product.quantity) return false;
        if (name != null ? !name.equals(product.name) : product.name != null) return false;
        return category == product.category;
    }



    @Override
    public String toString() {
        return quantity + " " + name + " " + String.format( "%.2f", price ) +"€";
    }
}
