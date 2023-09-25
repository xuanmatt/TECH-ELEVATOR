package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.io.PrintWriter;
import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Book implements CatalogItem{

    private String id;
    private String title;
    private String author;
    private LocalDate publishDate;

    public Book(String title, String author, LocalDate publishDate){
        if (title == null && author == null || publishDate == null){
            throw new InvalidParameterException("One of those parameters is invalid: " + title + " : " + author + " : " + publishDate);
        }

        this.title = title;
        this.author = author;
        this.publishDate = publishDate;
    }



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public LocalDate getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(LocalDate publishDate) {
        this.publishDate = publishDate;
    }

    @Override
    public boolean matchesName(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");
        }
        return title.toUpperCase().contains(searchStr.toUpperCase());

    }

    @Override
    public boolean matchesCreator(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");
        }
        return author.toUpperCase().contains(searchStr.toUpperCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return searchYear == publishDate.getYear();
    }

    @Override
    public void registerItem() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        String filename = "src/main/resources/logs/javabook" + LocalDate.now()+ ".txt";
        String book = "book was registered " + LocalDate.now() + " " + LocalTime.now() + "\n" + toString();

        try {
            FileStorageService.writeContentsToFile(book, filename, true );
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String toString() {
        return "Book{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", publishDate=" + publishDate +
                '}';
    }
}
