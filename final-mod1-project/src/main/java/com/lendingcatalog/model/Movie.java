package com.lendingcatalog.model;

import com.lendingcatalog.util.FileStorageService;
import com.lendingcatalog.util.exception.FileStorageException;

import java.security.InvalidParameterException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Movie implements CatalogItem{

    private String id;
    private String name;
    private String director;
    private LocalDate releaseDate;

    public Movie(String name, String director, LocalDate releaseDate){
        if (name == null && director == null || releaseDate == null){
            throw new InvalidParameterException("One of those parameters is invalid: " + name + " : " + director + " : " + releaseDate);
        }

        this.name = name;
        this.director = director;
        this.releaseDate = releaseDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDirector() {
        return director;
    }

    public void setDirector(String director) {
        this.director = director;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public boolean matchesName(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");

        }
        return name.toUpperCase().contains(searchStr.toUpperCase());
    }

    @Override
    public boolean matchesCreator(String searchStr) {
        if (searchStr == null){
            throw new InvalidParameterException("Search String cannot be null.");
        }
        return director.toUpperCase().contains(searchStr.toUpperCase());
    }

    @Override
    public boolean matchesYear(int searchYear) {
        return searchYear == releaseDate.getYear();
    }

    @Override
    public void registerItem() {
        if (id == null) {
            id = UUID.randomUUID().toString();
        }
        String filename = "src/main/resources/logs/javamovie" + LocalDate.now()+ ".txt";
        String movie = "movie was registered" + LocalDate.now() + LocalTime.now() + toString();

        try {
            FileStorageService.writeContentsToFile(movie, filename, true );
        } catch (FileStorageException e) {
            throw new RuntimeException(e);
        }


    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", director='" + director + '\'' +
                ", releaseDate=" + releaseDate +
                '}';
    }
}
