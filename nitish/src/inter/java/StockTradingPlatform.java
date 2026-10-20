package inter.java;

import java.io.*;
import java.util.*;

/*
 * ============================================
 *       STOCK TRADING PLATFORM
 *       CodeAlpha - Java Internship Task 2
 * ============================================
 */

// ---------------- STOCK CLASS ----------------
class Stock {
    private String symbol;
    private String companyName;
    private double price;
    private int availableShares;

    public Stock(String symbol, String companyName,
                 double price, int availableShares) {
        this.symbol = symbol;
        this.companyName = companyName;
        this.price = price;
        this.availableShares = availableShares;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getCompanyName() {
        return companyName;
    }

    public double getPrice() {
        return price;
    }

    public int getAvailableShares() {
        return availableShares;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setAvailableShares(int shares) {
        this.availableShares = shares;
    }

    public void displayStock() {
        System.out.printf(
            "%-10s %-22s ₹%-12.2f %-10d%n",
            symbol,
            companyName,
            price,
            availableShares
        );
    }
}


// ---------------- TRANSACTION CLASS ----------------
class Transaction {
    private String type;
    private String symbol;
    private int quantity;
    private double price;
    private double total;
    private String date;

    public Transaction(String type, String symbol,
                       int quantity, double price) {

        this.type = type;
        this.symbol = symbol;
        this.quantity = quantity;
        this.price = price;
        this.total = quantity * price;

        this.date = new Date().toString();
    }

    public void displayTransaction() {
        System.out.println("---------------------------------------------");
        System.out.println("Type      : " + type);
        System.out.println("Stock     : " + symbol);
        System.out.println("Quantity  : " + quantity);
        System.out.printf("Price     : ₹%.2f%n", price);
        System.out.printf("Total     : ₹%.2f%n", total);
        System.out.println("Date      : " + date);
    }

    public String getData() {
        return type + "|" + symbol + "|" +
               quantity + "|" + price + "|" + date;
    }
}


// ---------------- PORTFOLIO CLASS ----------------
class Portfolio {

    private HashMap<String, Integer> holdings;
    private double cash;
    private ArrayList<Transaction> transactions;

    public Portfolio(double initialCash) {
        holdings = new HashMap<>();
        transactions = new ArrayList<>();
        cash = initialCash;
    }

    // BUY STOCK
    public boolean buyStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("❌ Quantity must be greater than 0.");
            return false;
        }

        if (quantity > stock.getAvailableShares()) {
            System.out.println("❌ Not enough shares available.");
            return false;
        }

        double totalCost = stock.getPrice() * quantity;

        if (totalCost > cash) {
            System.out.println("❌ Insufficient balance.");
            System.out.printf("Required: ₹%.2f%n", totalCost);
            System.out.printf("Balance : ₹%.2f%n", cash);
            return false;
        }

        cash -= totalCost;

        int oldQuantity =
                holdings.getOrDefault(stock.getSymbol(), 0);

        holdings.put(
                stock.getSymbol(),
                oldQuantity + quantity
        );

        stock.setAvailableShares(
                stock.getAvailableShares() - quantity
        );

        Transaction transaction = new Transaction(
                "BUY",
                stock.getSymbol(),
                quantity,
                stock.getPrice()
        );

        transactions.add(transaction);

        System.out.println("\n✅ Stock purchased successfully!");
        System.out.println("Stock    : " + stock.getSymbol());
        System.out.println("Quantity : " + quantity);
        System.out.printf("Amount   : ₹%.2f%n", totalCost);
        System.out.printf("Balance  : ₹%.2f%n", cash);

        return true;
    }


    // SELL STOCK
    public boolean sellStock(Stock stock, int quantity) {

        if (quantity <= 0) {
            System.out.println("❌ Quantity must be greater than 0.");
            return false;
        }

        int owned =
                holdings.getOrDefault(stock.getSymbol(), 0);

        if (owned < quantity) {
            System.out.println("❌ You don't own enough shares.");
            System.out.println("Owned shares: " + owned);
            return false;
        }

        double totalValue =
                stock.getPrice() * quantity;

        cash += totalValue;

        int remaining = owned - quantity;

        if (remaining == 0) {
            holdings.remove(stock.getSymbol());
        } else {
            holdings.put(stock.getSymbol(), remaining);
        }

        stock.setAvailableShares(
                stock.getAvailableShares() + quantity
        );

        Transaction transaction = new Transaction(
                "SELL",
                stock.getSymbol(),
                quantity,
                stock.getPrice()
        );

        transactions.add(transaction);

        System.out.println("\n✅ Stock sold successfully!");
        System.out.println("Stock    : " + stock.getSymbol());
        System.out.println("Quantity : " + quantity);
        System.out.printf("Amount   : ₹%.2f%n", totalValue);
        System.out.printf("Balance  : ₹%.2f%n", cash);

        return true;
    }


    // SHOW PORTFOLIO
    public void showPortfolio(HashMap<String, Stock> market) {

        System.out.println("\n==============================================");
        System.out.println("             MY PORTFOLIO");
        System.out.println("==============================================");

        System.out.printf("Cash Balance : ₹%.2f%n", cash);

        double stockValue = 0;

        if (holdings.isEmpty()) {
            System.out.println("\nNo stocks in portfolio.");
        } else {

            System.out.printf(
                "%-10s %-12s %-15s%n",
                "Symbol",
                "Quantity",
                "Current Value"
            );

            System.out.println("----------------------------------------------");

            for (String symbol : holdings.keySet()) {

                int quantity = holdings.get(symbol);

                Stock stock = market.get(symbol);

                double value =
                        quantity * stock.getPrice();

                stockValue += value;

                System.out.printf(
                    "%-10s %-12d ₹%-15.2f%n",
                    symbol,
                    quantity,
                    value
                );
            }
        }

        double totalPortfolioValue =
                cash + stockValue;

        System.out.println("----------------------------------------------");
        System.out.printf(
            "Stock Value   : ₹%.2f%n",
            stockValue
        );

        System.out.printf(
            "Total Value   : ₹%.2f%n",
            totalPortfolioValue
        );
    }


    // PERFORMANCE
    public void showPerformance(
            HashMap<String, Stock> market,
            double initialInvestment) {

        double stockValue = 0;

        for (String symbol : holdings.keySet()) {

            int quantity = holdings.get(symbol);

            Stock stock = market.get(symbol);

            stockValue += quantity * stock.getPrice();
        }

        double totalValue = cash + stockValue;

        double profitLoss =
                totalValue - initialInvestment;

        double percentage =
                (profitLoss / initialInvestment) * 100;

        System.out.println("\n==============================================");
        System.out.println("          PORTFOLIO PERFORMANCE");
        System.out.println("==============================================");

        System.out.printf(
            "Initial Investment : ₹%.2f%n",
            initialInvestment
        );

        System.out.printf(
            "Current Value      : ₹%.2f%n",
            totalValue
        );

        if (profitLoss >= 0) {
            System.out.printf(
                "Profit             : ₹%.2f%n",
                profitLoss
            );
        } else {
            System.out.printf(
                "Loss               : ₹%.2f%n",
                Math.abs(profitLoss)
            );
        }

        System.out.printf(
            "Return             : %.2f%%%n",
            percentage
        );

        System.out.println("==============================================");
    }


    // TRANSACTION HISTORY
    public void showTransactions() {

        System.out.println("\n==============================================");
        System.out.println("          TRANSACTION HISTORY");
        System.out.println("==============================================");

        if (transactions.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (Transaction transaction : transactions) {
            transaction.displayTransaction();
        }
    }


    // SAVE DATA TO FILE
    public void saveData() {

        try {

            FileWriter writer =
                    new FileWriter("portfolio.txt");

            writer.write("Cash Balance: " + cash + "\n");
            writer.write("\nTransaction History:\n");

            for (Transaction transaction : transactions) {
                writer.write(
                        transaction.getData() + "\n"
                );
            }

            writer.close();

            System.out.println(
                "\n✅ Portfolio saved to portfolio.txt"
            );

        } catch (IOException e) {

            System.out.println(
                "❌ Error saving portfolio."
            );
        }
    }
}


// ---------------- MAIN CLASS ----------------
public class StockTradingPlatform {

    static Scanner scanner = new Scanner(System.in);

    static HashMap<String, Stock> market =
            new HashMap<>();

    static Portfolio portfolio;

    static final double INITIAL_MONEY = 100000.0;


    // CREATE MARKET
    public static void initializeMarket() {

        market.put(
            "TCS",
            new Stock(
                "TCS",
                "Tata Consultancy Services",
                3850.00,
                1000
            )
        );

        market.put(
            "INFY",
            new Stock(
                "INFY",
                "Infosys Limited",
                1850.00,
                1000
            )
        );

        market.put(
            "RELIANCE",
            new Stock(
                "RELIANCE",
                "Reliance Industries",
                2950.00,
                1000
            )
        );

        market.put(
            "HDFC",
            new Stock(
                "HDFC",
                "HDFC Bank",
                1750.00,
                1000
            )
        );

        market.put(
            "ITC",
            new Stock(
                "ITC",
                "ITC Limited",
                520.00,
                2000
            )
        );
    }


    // DISPLAY MARKET
    public static void displayMarket() {

        System.out.println("\n======================================================");
        System.out.println("                  STOCK MARKET");
        System.out.println("======================================================");

        System.out.printf(
            "%-10s %-22s %-13s %-10s%n",
            "Symbol",
            "Company",
            "Price",
            "Shares"
        );

        System.out.println(
            "------------------------------------------------------"
        );

        for (Stock stock : market.values()) {
            stock.displayStock();
        }

        System.out.println(
            "======================================================"
        );
    }


    // FIND STOCK
    public static Stock findStock(String symbol) {

        return market.get(
            symbol.toUpperCase()
        );
    }


    // BUY MENU
    public static void buyStock() {

        displayMarket();

        System.out.print(
            "\nEnter stock symbol to BUY: "
        );

        String symbol =
                scanner.next().toUpperCase();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println(
                "❌ Stock not found."
            );
            return;
        }

        System.out.print(
            "Enter quantity: "
        );

        int quantity;

        try {
            quantity = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(
                "❌ Please enter a valid number."
            );
            scanner.nextLine();
            return;
        }

        portfolio.buyStock(
                stock,
                quantity
        );
    }


    // SELL MENU
    public static void sellStock() {

        if (market.isEmpty()) {
            return;
        }

        displayMarket();

        System.out.print(
            "\nEnter stock symbol to SELL: "
        );

        String symbol =
                scanner.next().toUpperCase();

        Stock stock = findStock(symbol);

        if (stock == null) {
            System.out.println(
                "❌ Stock not found."
            );
            return;
        }

        System.out.print(
            "Enter quantity: "
        );

        int quantity;

        try {
            quantity = scanner.nextInt();
        } catch (InputMismatchException e) {
            System.out.println(
                "❌ Please enter a valid number."
            );
            scanner.nextLine();
            return;
        }

        portfolio.sellStock(
                stock,
                quantity
        );
    }


    // UPDATE MARKET PRICE
    public static void updateMarketPrices() {

        Random random = new Random();

        for (Stock stock : market.values()) {

            double oldPrice = stock.getPrice();

            // Price change between -5% and +5%
            double change =
                    (random.nextDouble() * 0.10) - 0.05;

            double newPrice =
                    oldPrice + (oldPrice * change);

            stock.setPrice(
                Math.round(newPrice * 100.0) / 100.0
            );
        }

        System.out.println(
            "\n📈 Market prices updated!"
        );
    }


    // SEARCH STOCK
    public static void searchStock() {

        System.out.print(
            "\nEnter stock symbol: "
        );

        String symbol =
                scanner.next().toUpperCase();

        Stock stock = findStock(symbol);

        if (stock == null) {

            System.out.println(
                "❌ Stock not found."
            );

            return;
        }

        System.out.println(
            "\n========== STOCK DETAILS =========="
        );

        System.out.println(
            "Symbol           : " +
            stock.getSymbol()
        );

        System.out.println(
            "Company          : " +
            stock.getCompanyName()
        );

        System.out.printf(
            "Current Price    : ₹%.2f%n",
            stock.getPrice()
        );

        System.out.println(
            "Available Shares : " +
            stock.getAvailableShares()
        );
    }


    // MAIN METHOD
    public static void main(String[] args) {

        initializeMarket();

        portfolio =
                new Portfolio(INITIAL_MONEY);

        System.out.println(
            "\n=============================================="
        );

        System.out.println(
            "       WELCOME TO STOCK TRADING PLATFORM"
        );

        System.out.println(
            "       CodeAlpha Java Internship - Task 2"
        );

        System.out.println(
            "=============================================="
        );

        boolean running = true;

        while (running) {

            System.out.println(
                "\n\n============== MAIN MENU =============="
            );

            System.out.println(
                "1. View Market"
            );

            System.out.println(
                "2. Search Stock"
            );

            System.out.println(
                "3. Buy Stock"
            );

            System.out.println(
                "4. Sell Stock"
            );

            System.out.println(
                "5. View Portfolio"
            );

            System.out.println(
                "6. View Performance"
            );

            System.out.println(
                "7. Transaction History"
            );

            System.out.println(
                "8. Update Market Prices"
            );

            System.out.println(
                "9. Save Portfolio"
            );

            System.out.println(
                "10. Exit"
            );

            System.out.println(
                "========================================"
            );

            System.out.print(
                "Enter your choice: "
            );

            int choice;

            try {

                choice = scanner.nextInt();

            } catch (InputMismatchException e) {

                System.out.println(
                    "❌ Invalid choice. Please enter a number."
                );

                scanner.nextLine();
                continue;
            }


            switch (choice) {

                case 1:
                    displayMarket();
                    break;

                case 2:
                    searchStock();
                    break;

                case 3:
                    buyStock();
                    break;

                case 4:
                    sellStock();
                    break;

                case 5:
                    portfolio.showPortfolio(market);
                    break;

                case 6:
                    portfolio.showPerformance(
                        market,
                        INITIAL_MONEY
                    );
                    break;

                case 7:
                    portfolio.showTransactions();
                    break;

                case 8:
                    updateMarketPrices();
                    break;

                case 9:
                    portfolio.saveData();
                    break;

                case 10:

                    System.out.println(
                        "\nThank you for using Stock Trading Platform!"
                    );

                    System.out.println(
                        "Goodbye 👋"
                    );

                    running = false;
                    break;

                default:

                    System.out.println(
                        "❌ Invalid choice. Please select 1-10."
                    );
            }
        }

        scanner.close();
    }
}