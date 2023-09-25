package com.lendingcatalog.model;

import com.lendingcatalog.model.exception.InvalidParameterException;
import com.lendingcatalog.util.exception.FileStorageException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ItemFactory {

    public static final String PIPE_DELIMITER = "\\|";
    public static final String BOOK = "book";
    public static final String TOOL = "tool";
    public static final String MOVIE = "movie";

    public CatalogItem parseItem(String line) {
        String[] values = line.split(PIPE_DELIMITER);
        String type = values[0];

        if (BOOK.equalsIgnoreCase(type)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(values[3], formatter);
            Book book = new Book(values[1], values[2],date);
            book.registerItem();
            return book;
        }

        if (TOOL.equalsIgnoreCase(type)){
            int count = Integer.parseInt(values[3]);
            Tool tool =  new Tool(values[1], values[2], count);
            tool.registerItem();
            return tool;
        }

        if (MOVIE.equalsIgnoreCase(type)){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate date = LocalDate.parse(values[3], formatter);
            Movie movie = new Movie(values[1], values[2],date);
            movie.registerItem();
            return movie;
        }
        throw new InvalidParameterException(type + " is an unknown type.");


    }

}
