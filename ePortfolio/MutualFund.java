package ePortfolio;

/**
 * The MutualFund class represents a mutual fund investment in the portfolio.
 * It extends the Investment class and includes specific properties and methods
 * related to mutual funds, such as handling redemption fees and calculating gain/loss.
 * This class also provides methods for buying and selling mutual fund investments.
 */

public class MutualFund extends Investment {

    private static final double REDEMPTION_FEE = 45.00;

    /**
     * Constructor to initialize the mutual fund with its properties.
     * 
     * @param symbol The symbol of the mutual fund.
     * @param name The name of the mutual fund.
     * @param quantity The quantity of the mutual fund shares.
     * @param price The price of the mutual fund share.
     * @param bookValue The book value of the mutual fund.
     * @param payment The payment for the mutual fund investment.
     * @param bookValueSold The value of the mutual fund sold.
     */
    public MutualFund(String symbol, String name, int quantity, double price, double bookValue, double payment, double bookValueSold) {
        super(symbol, name, quantity, price, bookValue, payment, bookValueSold);
    }

    /**
     * Calculates the payment for the mutual fund investment, factoring in the redemption fee.
     * 
     * @param quantity The quantity of the mutual fund shares.
     * @param price The price of the mutual fund share.
     * @return The calculated payment after redemption fee.
     */
    public double calculatePayment(int quantity, double price){
        double payment = (quantity * price) - REDEMPTION_FEE;
        this.setPayment(payment);  // Ensure that payment is set in the Investment class
        return payment;
    }
    
    /**
     * Calculates the gain or loss for the mutual fund investment.
     * The gain is calculated as the new payment minus the book value.
     * 
     * @param price The price of the mutual fund share.
     * @return The calculated gain or loss from the investment.
     */
    public double calculateGain(double price) {
        // Calculate the new payment with the redemption fee
        double newPayment = (this.quantity * price) - REDEMPTION_FEE;

        // Calculate gain as new payment minus book value
        double gain = newPayment - this.bookValue;

        // Update the price and print out gain details
        this.price = price;
        System.out.println("Gain : " + gain);

        // Format gain to 2 decimal places
        gain = Double.parseDouble(String.format("%.2f", gain));
        return gain;
    }

    
    /**
     * Calculates the book value for the mutual fund investment.
     * 
     * @param quantity The quantity of mutual fund shares.
     * @param price The price of the mutual fund share.
     * @return The calculated book value of the mutual fund investment.
     */
    public double calculateBookValue(int quantity, double price){
        double bookValue = (quantity * price);
        return bookValue;
    }


    /**
     * Provides a string representation of the mutual fund investment.
     * 
     * @return A string representation of the mutual fund.
     */
    @Override
    public String toString() {
        return "Mutual Fund -> " + super.toString();
    }

}
