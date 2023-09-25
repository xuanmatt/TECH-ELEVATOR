package com.lendingcatalog.model;

import com.lendingcatalog.util.exception.FileStorageException;

public interface CatalogItem {
    boolean matchesName(String searchStr);
    boolean matchesCreator(String searchStr);
    boolean matchesYear(int searchYear);
    void registerItem();






}
