package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Tool implements CatalogItem{

    private String id;
    private String type;
    private String manufacturer;
    private int count;

    public Tool(String type, String manufacturer, int count){
        if (type == null && manufacturer == null || count < 0){
            throw new InvalidParameterException("One of those parameters is invalid: " + type + " : " + manufacturer + " : " + count);
        }
        this.count = count;
        this.manufacturer = manufacturer;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public boolean matchesName(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");

        }
        return type.toUpperCase().contains(searchStr.toUpperCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");
        }
        return manufacturer.toUpperCase().contains(searchStr.toUpperCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return false;
    }

    @Override
    public void registerItem() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }

        String filename = "src/main/resources/logs/javatool" + LocalDate.now()+ ".txt";
        String tool = "tool was registered" + LocalDate.now() + LocalTime.now() + toString();

        try {
            FileStorageService.writeContentsToFile(tool, filename, true );
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public String toString() {
        return "Tool{" +
                "id='" + id + '\'' +
                ", type='" + type + '\'' +
                ", manufacturer='" + manufacturer + '\'' +
                ", count=" + count +
                '}';
    }
}
