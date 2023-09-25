package com.lendingcatalog.util;

import com.lendingcatalog.model.CatalogItem;
import com.lendingcatalog.model.Member;
import com.lendingcatalog.util.exception.FileStorageException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class FileStorageService {

    // Requirement: File I/O
    public static void writeContentsToFile(String contents, String filename, boolean appendFile) throws FileStorageException {
        try(PrintWriter writer = new PrintWriter(new FileOutputStream(filename), appendFile)) {
            writer.println(contents);

        } catch (FileNotFoundException e) {
            throw new FileStorageException("file not found",e);
        }
    }

    public static List<String> readContentsOfFile(String filename) throws FileStorageException {
        List<String> lines = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
               lines.add(scanner.nextLine());

            }
        } catch (FileNotFoundException e) {
            throw new FileStorageException(filename + " - " + e.getMessage());
        }
        return lines;
    }


}
