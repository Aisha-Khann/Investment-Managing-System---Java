package ePortfolio;

/**
 * The Investment class serves as an abstract base class for various types of investments
 * in an investment portfolio. It defines common properties and methods that can be utilized
 * by all derived classes, such as stocks and mutual funds.
 */
 
public abstract class Investment {
    private String symbol;
    private String name;
    protected int quantity;
    protected double price;
    protected double bookValue;
    protected double gain;
    protected double payment;
    private double bookValueSold;
    protected double newPayment;
    public static final double COMMISSION = 9.99;  // Commission for each buy/sell transaction

    /**
     * Constructor to initialize the investment with its properties.
     *
     * @param symbol The symbol of the investment.
     * @param name The name of the investment.
     * @param quantity The quantity of the investment.
     * @param price The price of the investment.
     * @param bookValue The book value of the investment.
     * @param payment The payment for the investment.
     * @param bookValueSold The value of the sold investment.
     */
    public Investment(String symbol, String name, int quantity, double price, double bookValue, double payment, double bookValueSold) {
        this.symbol = symbol;
        this.name = name;
        this.quantity = quantity;
        this.price = price;
        this.bookValue = bookValue;
        this.payment = payment;
        this.bookValueSold = bookValueSold;

    }

    /**
     * Calculates the gain for the investment based on a new price.
     * 
     * @param price The new price of the investment.
     * @return The calculated gain.
     */
    public double calculateGain(double price) {

        newPayment = (this.quantity * price);
        gain = newPayment - this.bookValue;
        this.price = price;

        // Format gain to 2 decimal places
        gain = Double.parseDouble(String.format("%.2f", gain));
        System.out.println("Gain : " + gain);
        
        return gain;
    }
    
    /**
     * Calculates the book value for the investment.
     * 
     * @param quantity The quantity of the investment.
     * @param price The price of the investment.
     * @return The book value.
     */
    public double calculateBookValue(int quantity, double price){
        return bookValue;
    }

    /**
     * Updates the price of the investment and recalculates payment.
     * 
     * @param newPrice The new price of the investment.
     */
    public void updatePrice(double newPrice) {
        this.price = newPrice;
        // Recalculate payment based on the new price, but leave bookValue unchanged
        this.payment = calculatePayment(this.quantity, this.price);
        setPayment(this.payment);
    }
    
    /**
     * Calculates the payment for the investment.
     * 
     * @param quantity The quantity of the investment.
     * @param price The price of the investment.
     * @return The calculated payment.
     */
    public double calculatePayment(int quantity, double price){
        return payment;
    }

    /**
     * Buys additional units of the investment.
     * 
     * @param newQuantity The quantity of the new units to buy.
     * @param newPrice The price of the new units.
     */
    public void buy(int newQuantity, double newPrice) {
        setQuantity(getQuantity() + newQuantity);  // Update total quantity
        double additionalBookValue = newQuantity * newPrice + COMMISSION;
        setBookValue(getBookValue() + additionalBookValue);  // Accumulate book value
        this.price = newPrice;
        setPayment(calculatePayment(newQuantity, newPrice));  // Update payment for new shares
    }
    
    /**
     * Sells a portion of the investment.
     * 
     * @param quantityToSell The quantity of the investment to sell.
     * @param sellPrice The price at which to sell the investment.
     */
    public void sell(int quantityToSell, double sellPrice) {
        if (quantityToSell > getQuantity()) {
            System.out.println("Error: Not enough units to sell.");
            return;
        }

    // Calculate the total value from the sale (includes commission)
    double totalValue = quantityToSell * sellPrice - COMMISSION;
    
    // Calculate the book value of the sold units
    double bookValueSold = (getBookValue() * quantityToSell) / getQuantity();
    
    // Update book value and quantity after the sale
    setBookValue(getBookValue() - bookValueSold);
    setQuantity(getQuantity() - quantityToSell);
    setPrice(sellPrice);
    setBookValueSold(bookValueSold);  // This ensures the book value sold is correctly tracked

    System.out.println("Quantity: " + getQuantity());
    System.out.println("Price: " + getPrice());

    System.out.println("BookValue :" + getBookValue());

    System.out.println("Gain from sale: $" + String.format("%.2f", (totalValue - bookValueSold)));
        if (getQuantity() == 0) {
            System.out.println("Investment fully sold and removed.");
        }
    }

    // Getters and Setters
    public String getSymbol() { return symbol; }
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
    public double getBookValue() { return bookValue; }
    public double getBookValueSold() {return bookValueSold;}
    public double getPayment(){ return payment;}
    public double getGain(){ return this.gain;}
    

    public void setSymbol(String symbol) { this.symbol = symbol; }
    public void setName(String name) { this.name = name; }
    public void setQuantity(int quantity) { this.quantity = quantity; }
    public void setPrice(double price) { this.price = price; }
    public void setBookValue(double bookValue) { this.bookValue = bookValue; }
    public void setPayment(double payment){ this.payment = payment;}
    public void setBookValueSold(double bookValueSold){ this.bookValueSold = bookValueSold;}

    /**
     * Provides a string representation of the investment.
     * 
     * @return A string representation of the investment.
     */
    @Override
    public String toString() {
        return "Symbol: " + symbol + ", Name: " + name + ", Quantity: " + quantity + ", Price: $" + price + ", Book Value: $" + bookValue;
    }
    
}
