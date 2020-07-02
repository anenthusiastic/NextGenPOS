package com.company.fileOperations;

import com.company.customTypes.ItemID;
import com.company.customTypes.Money;
import com.company.domain.ProductDescription;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Currency;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class ProductDescriptionFileOperator {
    private String filePath;
    private Map<ItemID, ProductDescription> descriptions = new HashMap<>();

    public ProductDescriptionFileOperator(String filePath) {
        this.filePath = filePath;
    }

    public Map<ItemID, ProductDescription> getDescriptions() {
        return descriptions;
    }

    public void readFromFile() {// loadProdSpecs olarak adını değiştir çünkü sd de öyle

        try {
            FileInputStream fis = new FileInputStream(filePath);
            Scanner sc = new Scanner(fis);
            //returns true if there is another line to read
            while (sc.hasNextLine()) {
                String line = sc.nextLine();

                String[] splitted = line.split(" "); //id|price|description|category[|priceCurrency]
                if (splitted.length > 4) {
                    addToMap(splitted[0], splitted[1], splitted[2], splitted[3], splitted[4]);
                } else {
                    addToMap(splitted[0], splitted[1], splitted[2], splitted[3] ,null);
                }
            }
            sc.close();
        } catch (IOException e) {
            System.out.println("IOException: File could not be read (catalog could not be created). Please check the file and try again.");
            System.exit(-1);
        }


    }

    private void addToMap(String id, String price, String description, String category, String moneyCurrency) {
        try {
            ItemID itemID = new ItemID(id);
            Money money;
            if (moneyCurrency == null) {
                money = new Money(new BigDecimal(price));
            } else {
                money = new Money(new BigDecimal(price), Currency.getInstance(moneyCurrency));
            }

            descriptions.put(itemID, new ProductDescription(itemID, money, description, category));
        } catch (NumberFormatException nfe) {
            System.out.println("NumberFormatException: File could not be read (catalog could not be created due to wrong number formats).");
            System.exit(-1);
        } catch (IllegalArgumentException iae) {
            System.out.println("IllegalArgumentException: File could not be read (catalog could not be created due to wrong currency formats)");
            System.exit(-1);
        }
    }

}
