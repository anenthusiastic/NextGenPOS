package com.company.domain;

import com.company.fileOperations.ProductDescriptionFileOperator;
import com.company.customTypes.ItemID;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ProductCatalog {
    private Map<ItemID,ProductDescription> descriptions = new HashMap<>();
    ProductDescriptionFileOperator pfdo = new ProductDescriptionFileOperator("..\\NextGenPOS\\ProductDescriptions.txt");

    public ProductCatalog(){
        loadProdSpecs();
    }

    public ProductDescription getProductDescription(ItemID id){
        return descriptions.get(id);
    }

    public void loadProdSpecs(){
        pfdo.readFromFile();
        descriptions = pfdo.getDescriptions();
    }

    public ArrayList<String> getProductIds() {
        ArrayList<String> ids = new ArrayList<>();
        descriptions.forEach((key, value) -> ids.add(key.toString()));
        return ids;
    }
}
