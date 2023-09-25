package com.techelevator;

import java.math.BigDecimal;
import java.util.*;

public class App {

    // The regex string to split the Strings in the dataset.
    private static final String FIELD_DELIMITER = "\\|";

    private static final int TITLE_FIELD = 0;
    private static final int AUTHOR_FIELD = 1;
    private static final int PUBLISHED_YEAR_FIELD = 2;
    private static final int PRICE_FIELD = 3;

    private final Scanner keyboard = new Scanner(System.in);

    private List<String> titles = new ArrayList<>();
    private List<String> authors = new ArrayList<>();
    private List<Integer> publishedYears = new ArrayList<>();
    private List<BigDecimal> prices = new ArrayList<>();

    public static void main(String[] args) {

        App app = new App();
        app.loadData();
        app.run();

    }

    private void loadData() {

        String[] dataset = Dataset.load();
        System.out.println(Arrays.toString(dataset));

        for (String i: dataset ){
            String[] fields = i.split(FIELD_DELIMITER);
            titles.add(fields[TITLE_FIELD]);
            publishedYears.add(Integer.parseInt(fields[PUBLISHED_YEAR_FIELD]));
            prices.add(new BigDecimal(fields[PRICE_FIELD]));


            System.out.println(i);
        }

        System.out.println("*");


        /*
        for (String input: dataset){
            String[] fields = input.split(FIELD_DELIMITER);
            titles.add(fields[TITLE_FIELD]);
            authors.add(fields[AUTHOR_FIELD]);
            publishedYears.add(Integer.valueOf(fields[PUBLISHED_YEAR_FIELD]));
            prices.add(new BigDecimal(fields[PRICE_FIELD]));
            System.out.println(input);
        }

         */


        /*
         Requirement: 1
         Populate the instance variables `titles`, `authors`, `publishedYears`,
         and `prices` by splitting each string in the `dataset` array and adding
         the individual fields to the appropriate list.
         See README for additional details.
         */

    }

    private void run() {

        while (true) {
            // Main menu loop
            printMainMenu();
            int mainMenuSelection = promptForMenuSelection("Please choose an option: ");
            if (mainMenuSelection == 1) {
                // Display data and subsets loop
                while (true) {
                    printDataAndSubsetsMenu();
                    int dataAndSubsetsMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (dataAndSubsetsMenuSelection == 1) {
                        displayDataset(Dataset.load());
                    } else if (dataAndSubsetsMenuSelection == 2) {
                        displayTitlesList(titles);
                    } else if (dataAndSubsetsMenuSelection == 3) {
                        displayAuthorsList(authors);
                    } else if (dataAndSubsetsMenuSelection == 4) {
                        displayPublishedYearsList(publishedYears);
                    } else if (dataAndSubsetsMenuSelection == 5) {
                        displayPricesList(prices);
                    } else if (dataAndSubsetsMenuSelection == 0) {
                        break;
                    }
                }
            }
            else if (mainMenuSelection == 2) {
                while (true) {
                    printSearchBooksMenu();
                    int searchBooksMenuSelection = promptForMenuSelection("Please choose an option: ");
                    if (searchBooksMenuSelection == 1) {
                        // Search by title
                        String filterTitle = promptForString("Enter title: ");
                        /*
                         Requirement: 3b
                         Replace `displayTitlesList(titles)` with calls to the
                         `filterByTitle()` and `displaySearchResults()` methods.
                         */

                        List<Integer> titleList = filterByTitle(filterTitle);
                        displaySearchResults(titleList, TITLE_FIELD);
                        //displaySearchResults(indexNum);
                    } else if (searchBooksMenuSelection == 2) {
                        // Search by author
                        String filterAuthor = promptForString("Enter author: ");
                        /*
                         Requirement: 4b
                         Replace `displayAuthorsList(authors)` with calls to the
                         `filterByAuthor()` and `displaySearchResults()` methods.
                         */

                        List<Integer> authorsList = filterByAuthor(filterAuthor);
                        displaySearchResults(authorsList, AUTHOR_FIELD);
                    } else if (searchBooksMenuSelection == 3) {
                        // Search by published year
                        int filterYear = promptForPublishedYear("Enter date (YYYY): ");
                        /*
                         Requirement: 5b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYear()` and `displaySearchResults()` methods.
                         */

                        List<Integer> filterPublishedYear = filterByPublishedYear(filterYear);
                        displaySearchResults(filterPublishedYear, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 4) {
                        // Search by published year range
                        int filterFromYear = promptForPublishedYear("Enter \"from\" date (YYYY): ");
                        int filterToYear = promptForPublishedYear("Enter \"to\" date (YYYY): ");
                        /*
                         Requirement: 6b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `filterByPublishedYearRange()` and `displaySearchResults()` methods.
                         */
                        List<Integer> foundPublishedByYearRange = filterByPublishedYearRange(filterFromYear, filterToYear);
                        displaySearchResults(foundPublishedByYearRange, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 5) {
                        // Find the most recent books
                        /*
                         Requirement: 7b
                         Replace `displayPublishedYearsList(publishedYears)` with calls
                         to the `findMostRecentBooks()` and `displaySearchResults()` methods.
                         */
                        List<Integer> findRecentBooks = findMostRecentBooks();
                        displaySearchResults(findRecentBooks, PUBLISHED_YEAR_FIELD);
                    } else if (searchBooksMenuSelection == 6) {
                        // Search by price
                        double filterPrice = promptForPrice("Enter price: ");

                        /*
                         Requirement: 8b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPrice()` and `displaySearchResults()` methods.
                         */
                        List<Integer> filterByPrice = filterByPrice(filterPrice);
                        displaySearchResults(filterByPrice, PRICE_FIELD);
                    } else if (searchBooksMenuSelection == 7) {
                        // Search by price range
                        double filterFromPrice= promptForPrice("Enter \"from\" price: ");
                        double filterToPrice = promptForPrice("Enter \"to\" price: ");
                        /*
                         Requirement: 9b
                         Replace `displayPricesList(prices)` with calls to the
                         `filterByPriceRange()` and `displaySearchResults()` methods.
                         */
                        List<Integer> foundPriceRange = filterByPriceRange(filterFromPrice, filterToPrice);
                        displaySearchResults(foundPriceRange, PRICE_FIELD);
                    } else if (searchBooksMenuSelection == 8) {
                        // Find the least expensive books
                        /*
                         Requirement: 10b
                         Replace `displayPricesList(prices)` with calls to the
                         `findLeastExpensiveBooks()` and `displaySearchResults()` methods.
                         */
                        List<Integer> leastExpensiveBooks = findLeastExpensiveBooks();
                        displaySearchResults(leastExpensiveBooks, PRICE_FIELD);
                    } else if (searchBooksMenuSelection == 0) {
                        break;
                    }
                }
            } else if (mainMenuSelection == 0) {
                break;
            }
        }

    }

    /*
     Requirement: 2
     Write the displaySearchResults(List<Integer> indexes) method.
     See README for additional details.
     */


    private void displaySearchResults(List<Integer> indexes, int primaryField){

        System.out.println("Results");
        System.out.println("-------");
        for (int index : indexes) {
            String str = titles.get(index) + ": " + authors.get(index) + ": " + publishedYears.get(index) + ": $" + prices.get(index);
            System.out.println(str);
        }
        System.out.println();
        promptForReturn();

        /*
        for (int indexNum: indexes){
            String title = titles.get(indexNum);
            String author = authors.get(indexNum);
            Integer publishedYear = publishedYears.get(indexNum);
            BigDecimal price = prices.get(indexNum);


            if (primaryField == AUTHOR_FIELD){
                System.out.println(String.format("%s: %s: %d: $%.2f", author, title, publishedYear, price));
            }
            else if (primaryField == PUBLISHED_YEAR_FIELD){
                System.out.println(String.format("%d: %s: %s: $%.2f", publishedYear, title, author, price));
            }
            else if (primaryField == PRICE_FIELD){
                System.out.println(String.format("$%.2f: %s: %s: %d", price,  title, author,publishedYear));
            }
            else {
                System.out.println(String.format("%s: %s: %d: $%.2f", title, author,publishedYear, price));
            }

        }

         */
    }


    /*
     Requirement: 3a
     Complete the `filterByTitle()` method.
     See README for additional details.
     */
    private List<Integer> filterByTitle(String filterTitle) {
        List<Integer> titleIndexes = new ArrayList<>();

        //if (titles.get(i).contains().toUpperCase()

        for (int i =0; i < titles.size(); i++){
            if (titles.get(i) != null && filterTitle != null && titles.get(i).toUpperCase().contains(filterTitle.toUpperCase())){
                titleIndexes.add(i);
            }
        }
        return titleIndexes;
    }

    /*
     Requirement: 4a
     Complete the `filterByAuthor()` method.
     See README for additional details.
     */
    private List<Integer> filterByAuthor(String filterAuthor) {
        List<Integer> authorIndexes = new ArrayList<>();

        for (int i=0; i < authors.size(); i++){
            String currentAuthors = filterAuthor.toUpperCase();
            if (authors.get(i).toUpperCase().contains(currentAuthors)){
                authorIndexes.add(i);
            }

        }
        return authorIndexes;
    }

    /*
     Requirement: 5a
     Complete the `filterByPublishedYear()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYear(int filterYear) {
        List<Integer> publishedYearIndexes = new ArrayList<>();

        for (int i =0; i < publishedYears.size(); i++){
            if (filterYear == publishedYears.get(i)){
                publishedYearIndexes.add(i);
            }
        }

        return publishedYearIndexes;
    }

    /*
     Requirement: 6a
     Complete the `filterByPublishedYearRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPublishedYearRange(int filterFromYear, int filterToYear) {
        List<Integer> publishedYearRangeChosen = new ArrayList<>();

        for (int i =0; i < publishedYears.size(); i++ ){
            if (publishedYears.get(i) >= filterFromYear && publishedYears.get(i) <= filterToYear){
                publishedYearRangeChosen.add(i);
            }
        }

        return publishedYearRangeChosen;
    }

    /*
     Requirement: 7a
     Add the `private List<Integer> findMostRecentBooks()` method.
     See README for additional details.
     */
    private List<Integer> findMostRecentBooks(){
        List<Integer> mostRecentBooks = new ArrayList<>();
        /*
        Integer mostRecentYear = 0;
        //Integer mostBookPublished = 0;

        for (int i=0; i < publishedYears.size();i++){
            if (publishedYears.get(i) > mostRecentYear){
                mostRecentYear = publishedYears.get(i);
            }
        }
        for (int i = 0; i < publishedYears.size(); i++){
            if (mostRecentYear == publishedYears.get(i)){
                mostRecentBooks.add(i);
            }
        }

         */
        int mostRecentBook = publishedYears.get(0);
        //System.out.println(mostRecentBook);
        mostRecentBooks.add(0); // I am confused of this code;
        for (int i = 1; i<publishedYears.size(); i++){
            int currentYear = publishedYears.get(i);
            if (currentYear > mostRecentBook){
                mostRecentBook = currentYear;
                mostRecentBooks.clear();
                mostRecentBooks.add(i);

            } else if (currentYear == mostRecentBook ) {
                mostRecentBooks.add(i);
            }
        }
        return mostRecentBooks;
    }

    /*
     Requirement: 8a
     Complete the `filterByPrice()` method.
     See README for additional details.
     */
    private List<Integer> filterByPrice(double filterPrice) {
        List<Integer> priceChosen = new ArrayList<>();
        BigDecimal fixedPrice = prices.get(0);

        for (int i = 0; i < prices.size(); i++){
            if (prices.get(i).compareTo(BigDecimal.valueOf(filterPrice)) <= 0){
                priceChosen.add(i);
            }
        }

        return priceChosen;
    }

    /*
     Requirement: 9a
     Complete the `filterByPriceRange()` method.
     See README for additional details.
     */
    private List<Integer> filterByPriceRange(double filterFromPrice, double filterToPrice) {
        List<Integer> byPriceRange = new ArrayList<>();

        for (int i = 0; i < prices.size(); i++){
            double currentPrice = prices.get(i).doubleValue();
            if (currentPrice >= filterFromPrice && currentPrice <= filterToPrice){
                byPriceRange.add(i);
            }

        }

        return byPriceRange;
    }

    /*
     Requirement: 10a
     Add the `private List<Integer> findLeastExpensiveBooks()` method.
     See README for additional details.
     */
    private List<Integer> findLeastExpensiveBooks(){

        List<Integer> leastExpensiveBooks = new ArrayList<>();
        BigDecimal min = prices.get(0);

        for (int i = 1; i < prices.size(); i++){
            BigDecimal priceBooks1 = prices.get(i);
            if (priceBooks1.compareTo(min) < 0){
                min = priceBooks1;
            }
        }

        for (int i = 0; i < prices.size(); i++){
            BigDecimal priceBooks2 = prices.get(i);
            if (min.compareTo(priceBooks2) == 0){
                leastExpensiveBooks.add(i);
            }
        }


        return leastExpensiveBooks;
    }



    // UI methods

    private void printMainMenu() {
        System.out.println("1: Display data and subsets");
        System.out.println("2: Search books");
        System.out.println("0: Exit");
        System.out.println();
    }

    private void printDataAndSubsetsMenu() {
        System.out.println("1: Display dataset");
        System.out.println("2: Display titles");
        System.out.println("3: Display authors");
        System.out.println("4: Display published years");
        System.out.println("5: Display prices");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void printSearchBooksMenu() {
        System.out.println("1: Search by title");
        System.out.println("2: Search by author");
        System.out.println("3: Search by published year");
        System.out.println("4: Search by published year range");
        System.out.println("5: Find most recent books");
        System.out.println("6: Search by price");
        System.out.println("7: Search by price range");
        System.out.println("8: Find least expensive books");
        System.out.println("0: Return to main menu");
        System.out.println();
    }

    private void displayDataset(String[] dataset) {
        System.out.println("Dataset");
        System.out.println("-------");
        for (String data : dataset) {
            System.out.println(data);
        }
        System.out.println();
        promptForReturn();
    }

    private void displayTitlesList(List<String> titles) {
        System.out.println("Titles");
        System.out.println("-------");
        for (int i = 0; i < titles.size(); i++) {
            System.out.println(i + ": " + titles.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private void displayAuthorsList(List<String> authors) {
        System.out.println("Authors");
        System.out.println("-------");
        for (int i = 0; i < authors.size(); i++) {
            System.out.println(i + ": " + authors.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private List<Integer> displayPublishedYearsList(List<Integer> publishedYears) {
        System.out.println("Published Years");
        System.out.println("---------------");
        for (int i = 0; i < publishedYears.size(); i++) {
            System.out.println(i + ": " + publishedYears.get(i));
        }
        System.out.println();
        promptForReturn();
        return publishedYears;
    }

    private void displayPricesList(List<BigDecimal> prices) {
        System.out.println("Prices");
        System.out.println("------");
        for (int i = 0; i < prices.size(); i++) {
            System.out.println(i + ": " + prices.get(i));
        }
        System.out.println();
        promptForReturn();
    }

    private int promptForMenuSelection(String prompt) {
        System.out.print(prompt);
        int menuSelection;
        try {
            menuSelection = Integer.parseInt(keyboard.nextLine());
        } catch (NumberFormatException e) {
            menuSelection = -1;
        }
        return menuSelection;
    }

    private String promptForString(String prompt) {
        System.out.print(prompt);
        return keyboard.nextLine();
    }

    private int promptForPublishedYear(String prompt) {
        int year;
        while (true) {
            System.out.println(prompt);
            try {
                year = Integer.parseInt(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The year provided is not well-formed. It must be YYYY.");
            }
        }
        return year;
    }

    private double promptForPrice(String prompt) {
        double price;
        while (true) {
            System.out.println(prompt);
            try {
                price = Double.parseDouble(keyboard.nextLine());
                break;
            } catch (NumberFormatException e) {
                System.out.println("The price provided is not a valid monetary value.");
            }
        }
        return price;
    }

    private void promptForReturn() {
        System.out.println("Press RETURN to continue.");
        keyboard.nextLine();
    }
}
