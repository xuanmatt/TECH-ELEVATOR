package com.lendingcatalog.model;

import java.security.InvalidParameterException;

public abstract class AbstractCataloItem implements CatalogItem{

    public boolean matchesName(String searchStr, String itemIdentifier) {
        if (searchStr == null) {
            throw new InvalidParameterException("Search String cannot be null.");
        }
        return itemIdentifier.toUpperCase().contains(searchStr.toUpperCase());
    }

    boolean matchesYear(int searchYear, int itemYear){
        return searchYear == itemYear;

    }




}
