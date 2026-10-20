import java.io.*;
import java.util.*;

public class
HotelReservationSystem {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Room> rooms = new ArrayList<>();
    static ArrayList<Booking> bookings = new ArrayList<>();

    public static void main(String[] args) {

        createRooms();
        loadBookings();

        int choice;

        do {
            System.out.println("\n======================================");
            System.out.println("       HOTEL RESERVATION SYSTEM");
            System.out.println("======================================");
            System.out.println("1. Search Rooms");
            System.out.println("2. Book Room");
            System.out.println("3. Cancel Reservation");
            System.out.println("4. View Booking Details");
            System.out.println("5. View All Rooms");
            System.out.println("6. Exit");
            System.out.println("======================================");

            System.out.print("Enter your choice: ");
            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    searchRooms();
                    break;

                case 2:
                    bookRoom();
                    break;

                case 3:
                    cancelBooking();
                    break;

                case 4:
                    viewBookings();
                    break;

                case 5:
                    viewAllRooms();
                    break;

                case 6:
                    System.out.println("\nThank you for using Hotel Reservation System!");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }

        } while (choice != 6);
    }


    // Create hotel rooms
    static void createRooms() {

        rooms.add(new Room(101, "Standard", 1500));
        rooms.add(new Room(102, "Standard", 1500));
        rooms.add(new Room(103, "Standard", 1500));

        rooms.add(new Room(201, "Deluxe", 2500));
        rooms.add(new Room(202, "Deluxe", 2500));
        rooms.add(new Room(203, "Deluxe", 2500));

        rooms.add(new Room(301, "Suite", 4000));
        rooms.add(new Room(302, "Suite", 4000));
    }


    // Search available rooms
    static void searchRooms() {

        System.out.println("\n----------- SEARCH ROOMS -----------");

        System.out.print("Enter room category (Standard/Deluxe/Suite): ");
        String category = sc.nextLine();

        boolean found = false;

        for (Room room : rooms) {

            if (room.category.equalsIgnoreCase(category)
                    && room.available) {

                System.out.println(
                        "Room No: " + room.roomNumber +
                                " | Category: " + room.category +
                                " | Price: Rs." + room.price
                );

                found = true;
            }
        }

        if (!found) {
            System.out.println("No available room found.");
        }
    }


    // Book a room
    static void bookRoom() {

        System.out.println("\n------------- BOOK ROOM -------------");

        System.out.print("Enter your name: ");
        String name = sc.nextLine();

        System.out.print("Enter room number: ");
        int roomNumber = sc.nextInt();
        sc.nextLine();

        Room selectedRoom = null;

        for (Room room : rooms) {

            if (room.roomNumber == roomNumber) {
                selectedRoom = room;
                break;
            }
        }

        if (selectedRoom == null) {
            System.out.println("Room not found!");
            return;
        }

        if (!selectedRoom.available) {
            System.out.println("Sorry! Room is already booked.");
            return;
        }

        System.out.print("Enter number of nights: ");
        int nights = sc.nextInt();
        sc.nextLine();

        if (nights <= 0) {
            System.out.println("Invalid number of nights!");
            return;
        }

        double totalAmount = selectedRoom.price * nights;

        System.out.println("\n----------- BOOKING SUMMARY -----------");
        System.out.println("Guest Name : " + name);
        System.out.println("Room No    : " + selectedRoom.roomNumber);
        System.out.println("Category   : " + selectedRoom.category);
        System.out.println("Nights     : " + nights);
        System.out.println("Total Bill : Rs." + totalAmount);

        // Payment simulation
        System.out.println("\n----------- PAYMENT -----------");
        System.out.println("1. Cash");
        System.out.println("2. UPI");
        System.out.println("3. Card");

        System.out.print("Select payment method: ");
        int paymentChoice = sc.nextInt();
        sc.nextLine();

        String paymentMethod;

        switch (paymentChoice) {

            case 1:
                paymentMethod = "Cash";
                break;

            case 2:
                paymentMethod = "UPI";
                break;

            case 3:
                paymentMethod = "Card";
                break;

            default:
                System.out.println("Invalid payment method!");
                return;
        }

        System.out.println("Processing payment...");

        Booking booking = new Booking(
                name,
                selectedRoom.roomNumber,
                selectedRoom.category,
                nights,
                totalAmount,
                paymentMethod
        );

        bookings.add(booking);
        selectedRoom.available = false;

        saveBookings();

        System.out.println("\nBooking successful!");
        System.out.println("Booking ID: " + booking.bookingId);
        System.out.println("Payment: " + paymentMethod);
    }


    // Cancel booking
    static void cancelBooking() {

        System.out.println("\n----------- CANCEL RESERVATION -----------");

        System.out.print("Enter Booking ID: ");
        int id = sc.nextInt();
        sc.nextLine();

        Booking foundBooking = null;

        for (Booking booking : bookings) {

            if (booking.bookingId == id) {
                foundBooking = booking;
                break;
            }
        }

        if (foundBooking == null) {
            System.out.println("Booking not found!");
            return;
        }

        for (Room room : rooms) {

            if (room.roomNumber == foundBooking.roomNumber) {
                room.available = true;
                break;
            }
        }

        bookings.remove(foundBooking);

        saveBookings();

        System.out.println("Reservation cancelled successfully!");
    }


    // View booking details
    static void viewBookings() {

        System.out.println("\n=========== BOOKING DETAILS ===========");

        if (bookings.isEmpty()) {
            System.out.println("No bookings available.");
            return;
        }

        for (Booking booking : bookings) {

            System.out.println("---------------------------------------");
            System.out.println("Booking ID : " + booking.bookingId);
            System.out.println("Name       : " + booking.name);
            System.out.println("Room No    : " + booking.roomNumber);
            System.out.println("Category   : " + booking.category);
            System.out.println("Nights     : " + booking.nights);
            System.out.println("Amount     : Rs." + booking.totalAmount);
            System.out.println("Payment    : " + booking.paymentMethod);
        }
    }


    // View all rooms
    static void viewAllRooms() {

        System.out.println("\n============= ALL ROOMS =============");

        for (Room room : rooms) {

            System.out.println(
                    "Room: " + room.roomNumber +
                            " | Category: " + room.category +
                            " | Price: Rs." + room.price +
                            " | Status: " +
                            (room.available ? "Available" : "Booked")
            );
        }
    }


    // Save bookings to file
    static void saveBookings() {

        try {

            FileWriter writer = new FileWriter("bookings.txt");

            for (Booking booking : bookings) {

                writer.write(
                        booking.bookingId + "," +
                                booking.name + "," +
                                booking.roomNumber + "," +
                                booking.category + "," +
                                booking.nights + "," +
                                booking.totalAmount + "," +
                                booking.paymentMethod + "\n"
                );
            }

            writer.close();

        } catch (IOException e) {

            System.out.println("Error saving bookings.");
        }
    }


    // Load bookings from file
    static void loadBookings() {

        File file = new File("bookings.txt");

        if (!file.exists()) {
            return;
        }

        try {

            Scanner fileScanner = new Scanner(file);

            while (fileScanner.hasNextLine()) {

                String line = fileScanner.nextLine();

                String[] data = line.split(",");

                if (data.length == 7) {

                    Booking booking = new Booking(
                            Integer.parseInt(data[0]),
                            data[1],
                            Integer.parseInt(data[2]),
                            data[3],
                            Integer.parseInt(data[4]),
                            Double.parseDouble(data[5]),
                            data[6]
                    );

                    bookings.add(booking);

                    for (Room room : rooms) {

                        if (room.roomNumber == booking.roomNumber) {
                            room.available = false;
                        }
                    }
                }
            }

            fileScanner.close();

        } catch (Exception e) {

            System.out.println("Error loading booking data.");
        }
    }
}


// ================= ROOM CLASS =================

class Room {

    int roomNumber;
    String category;
    double price;
    boolean available;

    Room(int roomNumber, String category, double price) {

        this.roomNumber = roomNumber;
        this.category = category;
        this.price = price;
        this.available = true;
    }
}


// ================= BOOKING CLASS =================

class Booking {

    static int nextId = 1001;

    int bookingId;
    String name;
    int roomNumber;
    String category;
    int nights;
    double totalAmount;
    String paymentMethod;


    // New booking
    Booking(String name, int roomNumber, String category,
            int nights, double totalAmount, String paymentMethod) {

        this.bookingId = nextId++;
        this.name = name;
        this.roomNumber = roomNumber;
        this.category = category;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;
    }

    // Load existing booking
    Booking(int bookingId, String name, int roomNumber,
            String category, int nights,
            double totalAmount, String paymentMethod) {

        this.bookingId = bookingId;
        this.name = name;
        this.roomNumber = roomNumber;
        this.category = category;
        this.nights = nights;
        this.totalAmount = totalAmount;
        this.paymentMethod = paymentMethod;

        if (bookingId >= nextId) {
            nextId = bookingId + 1;
        }
    }
}