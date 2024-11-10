package ePortfolio;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Collections;

/**
 * The Portfolio class manages a collection of stocks and mutual funds.
 * It allows users to buy, sell, update prices, calculate total gain, and search for investments.
 * This class contains the main method and various helper methods to manage the portfolio.
 */

public class Portfolio {

    /** List of investments in the portfolio. */
    private final ArrayList<Investment> investments;

    /** Index of keywords for searching investments by name. */
    private HashMap<String, List<Integer>> keywordIndex;

    /**
     * Constructs a new empty portfolio with no stocks or mutual funds.
     * Initializes the investments list and keyword index.
     */
    public Portfolio() {
        investments = new ArrayList<>();
	    keywordIndex = new HashMap<>();
    } 
    
    /**
     * The main method that runs the portfolio management program.
     * It continuously asks the user for input and performs actions like buying, selling, and updating investments.
     *
     * @param args Command-line arguments, where the first argument is the filename containing investment data.
     */
    public static void main(String [] args){

        if(args.length != 1){
            System.out.println("File name was not provided in command line");
            return;
        }

        String filename = args[0];
        //create new instance
        Portfolio portfolio = new Portfolio();

        // Load existing investments from file
        portfolio.readFile(filename);
        Scanner scanner = new Scanner(System.in);
 
        //command loop
        while (true) {
            System.out.println("Enter a command: buy, sell, update, getGain, search, quit");
            String command = scanner.nextLine().trim().toLowerCase();

            switch (command) {
                case "buy", "b" -> portfolio.buyInvestment(scanner, filename);
                case "sell" -> portfolio.sellInvestment(scanner);
                case "update", "u" -> portfolio.updateAllPrices(scanner);
                case "getGain", "g" -> portfolio.totalGain();
                case "search" -> {
                    System.out.println("Enter investment symbol (leave blank if not searching by symbol):");
                    String symbol = scanner.nextLine().trim();
                
                    System.out.println("Enter keywords for the name (space-separated, leave blank if not searching by name):");
                    String keywordInput = scanner.nextLine().trim();
                    String[] keywords = keywordInput.isEmpty() ? new String[0] : keywordInput.split("\\s+");
                
                    System.out.println("Enter lower bound price (or leave blank for no lower bound):");
                    String lowerBoundInput = scanner.nextLine().trim();
                    double lowerBound = lowerBoundInput.isEmpty() ? Double.NEGATIVE_INFINITY : Double.parseDouble(lowerBoundInput);
                
                    System.out.println("Enter upper bound price (or leave blank for no upper bound):");
                    String upperBoundInput = scanner.nextLine().trim();
                    double upperBound = upperBoundInput.isEmpty() ? Double.POSITIVE_INFINITY : Double.parseDouble(upperBoundInput);
                
                    // Now call the search method with these parameters
                    portfolio.searchInvestments(symbol, keywords, lowerBound, upperBound);
                }                
                case "quit", "q" -> {
                    System.out.println("Exiting program.");
                    // Save all investments to the specified file before exiting
                    portfolio.saveToFile(filename);
                    scanner.close(); // Close the scanner
                    // Exit the method
                    return;
                }
                default -> System.out.println("Invalid command. Please try again.");
            }
        }
    }


    //methods to load and save from/to a file 

    /**
     * Saves all investments in the portfolio to the specified file.
     * 
     * @param filename The name of the file to save the investments.
     */
    public void saveToFile(String fileName){
        PrintWriter outputStream = null;
        try{
            //connect stream to file
            outputStream = new PrintWriter(new FileOutputStream(fileName, false)); 
        } catch (FileNotFoundException e){
            System.out.println("Error writing to file" + fileName);
            System.exit(0);
        }

        //iterate through all investments
        for(Investment investment: investments){
            outputStream.println("Type = " + (investment instanceof Stock ? "stock" : "mutualfund"));
            outputStream.println("Symbol = " + investment.getSymbol());
            outputStream.println("Name = " + investment.getName());
            outputStream.println("Quantity = " + investment.getQuantity());
            outputStream.println("Price = " + String.format("%.2f", investment.getPrice()));
            outputStream.println("BookValue = " + String.format("%.2f", investment.getBookValue()));
            outputStream.println(" ");
        }

        //close the stream and display success
        outputStream.close();
        System.out.println("\nInvestments were successfully saved to " + fileName);
    }

    /**
     * Reads the investment data from the specified file and populates the portfolio.
     * 
     * @param filename The name of the file to read investments from.
     */
    public void readFile(String fileName) {
    Scanner inputStream = null;
    File file = new File(fileName);

    try {
        // Connect stream to file
        inputStream = new Scanner(new FileInputStream(fileName));

        // Create investment object
        Investment investment = null;
        String type = "", symbol = "", name = "", stringQuantity = "", stringPrice = "", stringBookValue = "";
        int quantity = 0;
        double price = 0;
        double bookValue = 0;
        String token[];

        while (inputStream.hasNextLine()) {

            // Read investment information
            type = inputStream.nextLine();
            symbol = inputStream.nextLine();
            name = inputStream.nextLine();
            stringQuantity = inputStream.nextLine();
            stringPrice = inputStream.nextLine();
            stringBookValue = inputStream.nextLine();

            // Split and parse each field
            token = type.split("=");
            type = token[1].trim();
            
            token = symbol.split("=");
            symbol = token[1].trim();
            
            token = name.split("=");
            name = token[1].trim();
            
            token = stringQuantity.split("=");
            quantity = Integer.parseInt(token[1].trim());
            
            token = stringPrice.split("=");
            price = Double.parseDouble(token[1].trim());
            
            token = stringBookValue.split("=");
            bookValue = Double.parseDouble(token[1].trim());

            // Check if the type is stock or mutual fund and create appropriate investment object
            if (type.equalsIgnoreCase("stock")) {
                investment = new Stock(symbol, name, quantity, price, 0, 0, 0);
                System.out.println("Stock added to investment.");
                investments.add(investment);
                investment.setBookValue(bookValue);
            } else if (type.equalsIgnoreCase("mutualfund")) {
                System.out.println("MutualFund added to investment");
                investment = new MutualFund(symbol, name, quantity, price, 0, 0, 0);
                investments.add(investment);
                investment.setBookValue(bookValue);
            }

            // Update keyword index for the name of the investment
            String[] keywords = name.toLowerCase().split("\\s+");
            for (String keyword : keywords) {
                // If the keyword is already in the index, add the position to the list
                if (keywordIndex.containsKey(keyword)) {
                    keywordIndex.get(keyword).add(investments.size() - 1); // Add the current position
                } else {
                    // Otherwise, create a new list for this keyword
                    List<Integer> positions = new ArrayList<>();
                    positions.add(investments.size() - 1);
                    keywordIndex.put(keyword, positions);
                }
            }

            // Skip any extra blank lines between investments if necessary
            if (inputStream.hasNextLine()) {
                inputStream.nextLine();
            }
        }

        // Print the keyword index for debugging
        System.out.println("Current Keyword Index:");
        for (Map.Entry<String, List<Integer>> entry : keywordIndex.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }

        inputStream.close();

        } catch (FileNotFoundException e) {
	
            // Handle the case where the file is missing by creating it
            System.out.println("File not found. Creating a new file: " + fileName);
            try {
                if (file.createNewFile()) {
                    System.out.println("File created successfully: " + file.getName());
                }
            } catch (IOException ioException) {
                System.out.println("Error creating the file.");
                ioException.printStackTrace();
            }
        }
    }


    //methods to implement tasks

    /**
     * Buys a new investment or adds shares to an existing one.
     * 
     * @param scanner The scanner for user input.
     * @param filename The name of the file to save after buying the investment.
     */
    public void buyInvestment(Scanner scanner, String fileName) {
        System.out.println("Enter investment type (stock or mutualfund):");
        String type = scanner.nextLine().trim().toLowerCase();
        
        System.out.println("Enter the symbol of the investment:");
        String symbol = scanner.nextLine().trim();
        
        // Check if the investment already exists
        Investment investment = findInvestment(symbol);
        
        if (investment != null) { // Existing investment
            System.out.println("Enter quantity to buy:");
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter a price:");
            double price = Double.parseDouble(scanner.nextLine());
        
            investment.buy(quantity, price);
            
            // Update payment and bookValueSold after buying
            investment.setPayment(investment.calculatePayment(quantity, price));
            investment.setBookValueSold(investment.calculateBookValue(quantity, price));
    
            System.out.println("Investment updated successfully.");
            saveToFile(fileName);

        } else { // New investment
            System.out.println("Enter name of the investment:");
            String name = scanner.nextLine().trim();
            System.out.println("Enter quantity:");
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter price:");
            double price = Double.parseDouble(scanner.nextLine());
            
            Investment newInvestment = null; // Initialize newInvestment
            
            if (type.equals("stock")) {
                // Calculate payment and bookValue for stock by creating an instance
                Stock tempStock = new Stock(symbol, name, quantity, price, 0, 0, 0); // Temporary Stock instance
                double bookValue = tempStock.calculateBookValue(quantity, price);  // Consistent book value calculation
                double payment = tempStock.calculatePayment(quantity, price);  // Initial payment            
                
                newInvestment = new Stock(symbol, name, quantity, price, bookValue, payment, 0);

            } else if (type.equals("mutualfund")) {
                // Calculate payment and bookValue for mutual fund by creating an instance
                MutualFund tempMutualFund = new MutualFund(symbol, name, quantity, price, 0, 0, 0); // Temporary MutualFund instance
                double bookValue = quantity * price; // No additional costs for mutual funds
                double payment = tempMutualFund.calculatePayment(quantity, price);
                double bookValueSold = tempMutualFund.calculateBookValue(quantity, price);
                
                newInvestment = new MutualFund(symbol, name, quantity, price, bookValue, payment, bookValueSold);
            } else {
                System.out.println("Invalid investment type.");
                return;
            }
            //add new investment to the list and update the keyword index
            addInvestment(newInvestment);
            saveToFile(fileName);
            System.out.println("New investment added to portfolio.");
        }
    }
    
    /**
     * Sells an investment and updates the portfolio accordingly.
     * 
     * @param scanner The scanner for user input.
     */
    public void sellInvestment(Scanner scanner) {
        System.out.println("Enter the symbol of the investment to sell:");
        String symbol = scanner.nextLine().trim();
    
        Investment investment = findInvestment(symbol);
        if (investment != null) {
            System.out.println("Enter the quantity to sell:");
            int quantity = Integer.parseInt(scanner.nextLine());
            System.out.println("Enter the selling price:");
            double price = Double.parseDouble(scanner.nextLine());
    
            if (quantity <= investment.getQuantity()) {
                investment.sell(quantity, price);
                System.out.println("Investment sold successfully.");
    
                removeFromIndex(investment.getName(), investments.indexOf(investment));


                // Remove investment if fully sold
                if (investment.getQuantity() == 0) {

		    deleteInvestment(investments.indexOf(investment));

                    System.out.println("Investment fully sold and removed from portfolio.");
                }
            } else {
                System.out.println("Insufficient quantity to sell.");
            }
        } else {
            System.out.println("Investment with the given symbol not found.");
        }
    }
    
    /**
     * Updates the prices of all investments in the portfolio.
     * 
     * @param scanner The scanner for user input.
     */
    public void updateAllPrices(Scanner scanner) {
        for (Investment investment : investments) {
            System.out.println("Enter new price for " + investment.getSymbol() + ": ");
            double newPrice = scanner.nextDouble();
            //investment.setPrice(newPrice);  // Directly set the price without recalculating payment/book value
            investment.calculateGain(newPrice);
        }
    }
   
    
    /**
     * Calculates and displays the total gain of the portfolio by summing the gains of all investments.
     */
    public void totalGain() {
        double totalGain = 0;
        
        // Calculate total gain for all investments
        for (Investment investment : investments) {
            double price = investment.getPrice();
            double gain = investment.calculateGain(price); // Calls the calculateGain() method for each investment
            totalGain += gain; // Add individual gain to total gain
        }
        
        //Print total gain
        System.out.printf("Total gain for all investments: $%.2f%n", totalGain);
    }
    

    /**
     * Searches for investments in the portfolio based on symbol, name keywords, and price range.
     * hashmap index
     * @param symbol The investment symbol to search for (can be empty for no symbol search).
     * @param keywords The list of keywords to search for in the investment name (can be empty for no name search).
     * @param lowerBound The lower bound of the price range (use Double.NEGATIVE_INFINITY for no lower bound).
     * @param upperBound The upper bound of the price range (use Double.POSITIVE_INFINITY for no upper bound).
     */
    public void searchInvestments(String symbol, String[] keywords, double lowerBound, double upperBound) {
        List<Integer> positionsToCheck = null;
        for (String keyword : keywords) {
            List<Integer> keywordPositions = keywordIndex.getOrDefault(keyword.toLowerCase(), Collections.emptyList());
            if (positionsToCheck == null) {
                positionsToCheck = new ArrayList<>(keywordPositions);
            } else {
                positionsToCheck.retainAll(keywordPositions); // Keep only intersecting positions
            }
        }       
    
        // Check the reduced list of positions
        boolean found = false;
        for (int pos : positionsToCheck) {
            Investment investment = investments.get(pos);
            if (matchesSymbolAndPriceRange(investment, symbol, lowerBound, upperBound)) {
                System.out.println("Found investment at position " + pos + ": " + investment);
                found = true;
            }
        }

        // If no investment found, notify the user
        if (!found) {
            System.out.println("No matching investments found.");
        }
    
        //print current keyword after index
        System.out.println("\nCurrent Keyword Index after search:");
        for (Map.Entry<String, List<Integer>> entry : keywordIndex.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    /**
     * Adds an investment to the keyword index based on the investment's name.
     * The name is split into keywords (lowercased) and each keyword is indexed with the position of the investment.
     * 
     * @param name The name of the investment to index.
     * @param position The position of the investment in the investments list.
     */
    public void addToIndex(String name, int position) {
        String[] keywords = name.toLowerCase().split("\\s+");
        for (String keyword : keywords) {
        keywordIndex.computeIfAbsent(keyword, k -> new ArrayList<>()).add(position);
        }
    }

    /**
     * Adds a new investment to the portfolio and updates the keyword index.
     * The investment is added to the investments list, and its name is indexed.
     * 
     * @param investment The investment to be added.
     */
	public void addInvestment(Investment investment) {
        investments.add(investment);
        addToIndex(investment.getName(), investments.size() - 1);
    }

	/**
     * Deletes an investment from the portfolio and removes it from the keyword index.
     * The specified investment is removed from the investments list, and its name is unindexed.
     * After removal, positions in the index are adjusted to account for the change in list size.
     * 
     * @param position The position of the investment to be deleted in the investments list.
     */
	public void deleteInvestment(int position) {
        Investment investment = investments.get(position);
        investments.remove(position);
        removeFromIndex(investment.getName(), position);
        for (List<Integer> positions : keywordIndex.values()) {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i) > position) {
                    positions.set(i, positions.get(i) - 1);
                }
            }
        }
    }

    /**
     * Removes an investment from the keyword index based on the investment's name and position.
     * The name is split into keywords (lowercased), and the specific position is removed from each keyword's index.
     * If a keyword has no more positions, it is removed from the index.
     * 
     * @param name The name of the investment to remove from the index.
     * @param position The position of the investment to be removed from the index.
     */
    public void removeFromIndex(String name, int position) {
        String[] keywords = name.toLowerCase().split("\\s+");
        for (String keyword : keywords) {
            List<Integer> positions = keywordIndex.get(keyword);
            positions.remove((Integer) position); // Remove the specific position
            if (positions.isEmpty()) {
                keywordIndex.remove(keyword);
            }
        }
    }


    //helper methods

    /**
     * Helper method to check if an investment matches the specified symbol and falls within the given price range.
     * 
     * @param investment The investment to check.
     * @param symbol The symbol of the investment (can be empty to ignore symbol matching).
     * @param lowerBound The lower price bound (inclusive). Use Double.NEGATIVE_INFINITY for no lower bound.
     * @param upperBound The upper price bound (inclusive). Use Double.POSITIVE_INFINITY for no upper bound.
     * @return True if the investment matches the symbol and falls within the price range, otherwise false.
     */
    private boolean matchesSymbolAndPriceRange(Investment investment, String symbol, double lowerBound, double upperBound) {
        return (symbol.isEmpty() || investment.getSymbol().equalsIgnoreCase(symbol)) &&
        investment.getPrice() >= lowerBound &&
        investment.getPrice() <= upperBound;
    }

    /**
     * Finds an investment by its symbol from the investments list.
     * This method checks both stocks and mutual funds.
     * 
     * @param symbol The symbol of the investment to search for.
     * @return The investment with the given symbol, or null if not found.
     */
    private Investment findInvestment(String symbol) {
        for (Investment investment : investments) { // Loop through the unified list
            if (investment.getSymbol().equalsIgnoreCase(symbol)) {
                return investment;
            }
        }
        return null; // Return null if not found
    }    
        

}
