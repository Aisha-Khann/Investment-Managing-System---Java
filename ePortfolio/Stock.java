package ePortfolio;

/**
 * The Stock class represents a stock investment in the portfolio.
 * It extends the Investment class and includes specific properties and methods
 * related to stocks, such as handling commissions and calculating gain/loss.
 */
 
public class Stock extends Investment {

    private static final double COMMISSION = 9.99;

    /**
     * Constructor to initialize the stock with its properties.
     * 
     * @param symbol The symbol of the stock.
     * @param name The name of the stock.
     * @param quantity The quantity of stock shares.
     * @param price The price of the stock share.
     * @param bookValue The book value of the stock.
     * @param payment The payment for the stock investment.
     * @param bookValueSold The value of the stock sold.
     */
    public Stock(String symbol, String name, int quantity, double price, double bookValue, double payment, double bookValueSold) {
        super(symbol, name, quantity, price, bookValue, payment, bookValueSold);  // Call to parent constructor
    }

    /**
     * Returns the commission fee applied to stock transactions.
     * 
     * @return The commission fee for stock transactions.
     */
    public static double getCommission() {
        return COMMISSION;
    }

    /**
     * Provides a string representation of the stock investment.
     * 
     * @return A string representation of the stock.
     */
    @Override
    public String toString() {
        return "Stock -> " + super.toString();
    }

    /**
     * Calculates the book value for the stock investment, including any commissions.
     * 
     * @param quantity The quantity of stock shares.
     * @param price The price of the stock share.
     * @return The calculated book value for the stock.
     */
    public double calculateBookValue(int quantity, double price) {
        // Book value for stocks includes the quantity, price, and any associated commission
       return (quantity * price) + COMMISSION;
    }

    /**
     * Calculates the payment for the stock investment, including the commission.
     * 
     * @param quantity The quantity of stock shares.
     * @param price The price of the stock share.
     * @return The calculated payment for the stock.
     */
    public double calculatePayment(int quantity, double price) {
        // Payment is the amount paid for the stock, including any commission or purchase cost
        return quantity * price + COMMISSION;
    }

    /**
     * Calculates the gain or loss for the stock investment, including the commission.
     * The gain is calculated as the new payment minus the book value, adjusted by the commission.
     * 
     * @param price The price of the stock share.
     * @return The calculated gain or loss from the investment.
     */
    public double calculateGain(double price){

        super.calculateGain(price);
        newPayment -= 9.99;
        gain -= 9.99;

        return gain;
    
    }
    
}

