import java.util.*;

public class StockTradingPlatform {

    static Scanner scanner = new Scanner(System.in);

    static double balance = 100000.00;

    static String[] stockNames = {
            "TCS", "INFOSYS", "RELIANCE", "WIPRO", "HDFC"
    };

    static double[] stockPrices = {
            3500.00, 1800.00, 2500.00, 500.00, 1600.00
    };

    static int[] portfolio = new int[stockNames.length];

    static ArrayList<String> history = new ArrayList<>();

    public static void main(String[] args) {

        boolean running = true;

        System.out.println("======================================");
        System.out.println("      STOCK TRADING PLATFORM");
        System.out.println("======================================");
        System.out.println("Welcome to Stock Trading Platform!");

        while (running) {

            System.out.println("\n========== MAIN MENU ==========");
            System.out.println("1. View Stocks");
            System.out.println("2. Buy Stocks");
            System.out.println("3. Sell Stocks");
            System.out.println("4. View Portfolio");
            System.out.println("5. Check Balance");
            System.out.println("6. Transaction History");
            System.out.println("7. Exit");

            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                scanner.nextLine();
                continue;
            }

            switch (choice) {

                case 1:
                    viewStocks();
                    break;

                case 2:
                    buyStocks();
                    break;

                case 3:
                    sellStocks();
                    break;

                case 4:
                    viewPortfolio();
                    break;

                case 5:
                    checkBalance();
                    break;

                case 6:
                    transactionHistory();
                    break;

                case 7:
                    System.out.println("\nThank you for using Stock Trading Platform!");
                    System.out.println("Goodbye!");
                    running = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please select 1-7.");
            }
        }

        scanner.close();
    }

    static void viewStocks() {

        System.out.println("\n========== AVAILABLE STOCKS ==========");

        for (int i = 0; i < stockNames.length; i++) {
            System.out.printf("%d. %-12s Rs. %.2f%n",
                    i + 1, stockNames[i], stockPrices[i]);
        }
    }

    static void buyStocks() {

        viewStocks();

        System.out.print("\nEnter stock number: ");
        int stock = scanner.nextInt();

        if (stock < 1 || stock > stockNames.length) {
            System.out.println("Invalid stock number!");
            return;
        }

        System.out.print("Enter quantity to buy: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0) {
            System.out.println("Quantity must be greater than zero!");
            return;
        }

        int index = stock - 1;
        double total = stockPrices[index] * quantity;

        if (total > balance) {
            System.out.println("Insufficient balance!");
            return;
        }

        balance -= total;
        portfolio[index] += quantity;

        history.add("Bought " + quantity + " shares of "
                + stockNames[index] + " for Rs. " + total);

        System.out.println("\nStock purchased successfully!");
        System.out.printf("Total amount: Rs. %.2f%n", total);
        System.out.printf("Remaining balance: Rs. %.2f%n", balance);
    }

    static void sellStocks() {

        viewPortfolio();

        System.out.print("\nEnter stock number: ");
        int stock = scanner.nextInt();

        if (stock < 1 || stock > stockNames.length) {
            System.out.println("Invalid stock number!");
            return;
        }

        int index = stock - 1;

        if (portfolio[index] == 0) {
            System.out.println("You do not own this stock!");
            return;
        }

        System.out.print("Enter quantity to sell: ");
        int quantity = scanner.nextInt();

        if (quantity <= 0 || quantity > portfolio[index]) {
            System.out.println("Invalid quantity!");
            return;
        }

        double total = stockPrices[index] * quantity;

        portfolio[index] -= quantity;
        balance += total;

        history.add("Sold " + quantity + " shares of "
                + stockNames[index] + " for Rs. " + total);

        System.out.println("\nStock sold successfully!");
        System.out.printf("Amount received: Rs. %.2f%n", total);
        System.out.printf("Available balance: Rs. %.2f%n", balance);
    }

    static void viewPortfolio() {

        System.out.println("\n========== YOUR PORTFOLIO ==========");

        boolean found = false;
        double totalValue = 0;

        for (int i = 0; i < portfolio.length; i++) {

            if (portfolio[i] > 0) {

                double value = portfolio[i] * stockPrices[i];
                totalValue += value;
                found = true;

                System.out.printf("%s | Quantity: %d | Value: Rs. %.2f%n",
                        stockNames[i], portfolio[i], value);
            }
        }

        if (!found) {
            System.out.println("Your portfolio is currently empty.");
        }

        System.out.printf("Total portfolio value: Rs. %.2f%n", totalValue);
    }

    static void checkBalance() {

        System.out.println("\n========== ACCOUNT BALANCE ==========");
        System.out.printf("Available balance: Rs. %.2f%n", balance);
    }

    static void transactionHistory() {

        System.out.println("\n========== TRANSACTION HISTORY ==========");

        if (history.isEmpty()) {
            System.out.println("No transactions yet.");
            return;
        }

        for (int i = 0; i < history.size(); i++) {
            System.out.println((i + 1) + ". " + history.get(i));
        }
    }
}