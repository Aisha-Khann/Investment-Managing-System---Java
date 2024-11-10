# Investment Managing System
Author: Aisha Khan
Student ID: 1216274

## Project Overview:
This system manages a portfolio of stocks and mutual funds, allowing the user to perform a variety of operations 
like buying, selling, updating prices, calculating total gain, and searching for investments. The key enhancement 
includes implementing a HashMap to index investments by keywords from their names, allowing more 
efficient searching and updating of investments.

## Key Features:
Search Functionality: Investment names are indexed by keywords, allowing efficient multi-keyword search.
Dynamic Keyword Indexing: The HashMap dynamically updates when investments are added or removed, ensuring the search is always up to date.
Search Enhancements: Support for searching by symbol, name (keywords), and price range, with the ability to filter and find intersections of results.
Handling Duplicates: The program checks for duplicate symbols before adding an investment to the portfolio.
Improved User Experience: Handles invalid inputs better, such as incorrect price ranges and symbol mismatches.

## How to Compile:
We begin in the khan61_a1 directory.
To compile the program, use the following command:
javac ePortfolio/*.java
The program expects one command-line argument, which is the file name (e.g., investments.txt) to load and save the portfolio
To run the program, execute: 
java ePortfolio.Portfolio <filename>


# Instructions:
Follow the prompts in the console to enter commands like buy, sell, search, etc.



## Test Cases / Test Plan
# Correct inputs
1. Buying Investments:
- Test case: Buy a stock or mutual fund with symbol, name, quantity, price, and book value.
Expected result: The investment is added to the portfolio and the details are correctly stored.

2. Search Function:
- Test case: Search for an investment using symbol, name, or price range.
Test variations: Ensure that searching works for investments at the start, middle, or end of the list.
Expected result: The program finds and displays the correct investment based on the search criteria (e.g., keyword, symbol, price range).

3. Updating Investment Prices:
- Test case: Use the "update" command to update the price of an investment and reflect getGain.
Expected result: The price of the investment is updated, and the change is reflected when searching or viewing the portfolio.

4. Selling Investments:
- Test case: Sell an investment by specifying quantity.
- Test variations: Selling some units, selling all units, and selling more than available.
Expected result: The program should display the correct messages such as "Error: Not enough units to sell," "Mutual Fund fully sold and removed from portfolio," or confirm the sale successfully.

5.Checking Gain:
- Test case: Use the "getGain" command to check the gain of an investment after selling or updating the price.
Expected result: The gain is calculated correctly, based on updated price and quantity.

6. Re-Opening Program:
- Test case: Close the program and re-open it to load the investments from the saved file.
Expected result: The investments from the file should be reloaded correctly.


## Invalid Inputs:
1. Invalid Symbol in Search:
- Test case: Enter an invalid symbol when searching, such as typing XYZ instead of AAPL.
Expected result: The program displays an appropriate error message like "Stock not found."

2. Invalid Commands:
- Test case: Enter invalid commands such as 'q', 'Q', 'quit', 'QUIT', and any reasonable variations.
Test case: Enter an unreasonable command such as 'bye' or 'exit'.
Expected result: The program should handle reasonable commands like "quit", "Quit", or "Q", and reject unreasonable inputs with an appropriate message, asking the user to try again.

3. Incorrect Price Range in Search:
- Test case: Enter an invalid or impossible price range when searching.
Expected result: The program should return no results or display an error message about the invalid range.

4. Incorrect File Handling:
- Test case: Attempt to load a non-existent file or a corrupted file when the program starts.
Expected result: The program should display a warning message about file handling errors, like "Error: Could not load file."


## Invalid Availability:
1. Selling More Units than Available:
- Test case: Attempt to sell more units than owned for a specific investment.
Expected result: The program should display "Error: Not enough units to sell."

2. Selling All Units:
- Test case: Sell the exact quantity of units owned for a mutual fund or stock.
Expected result: The program should confirm the sale with a message like "Mutual Fund fully sold and removed from portfolio."

3. Search for Non-Existent Investment:
- Test case: Search for an investment that does not exist in the portfolio.
Expected result: The program should return "Stock not found" or an appropriate message.

## Possible improvements
Sometimes when we are actively buying and selling investments, and we go to run the command "search", 
it may not be able to find a given investment based on the keywords or parameters requested, until we save 
all changes by quitting the program. I would work to tackle this issue if given more time.

## Contact Information:
Email: khan61@uoguelph.ca
