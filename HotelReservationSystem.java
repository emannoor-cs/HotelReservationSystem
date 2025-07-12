import java.io.FileWriter;
import java.io.IOException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class HotelReservationSystem {
    static Room[] rooms = new Room[100];
    static Booking[] bookings = new Booking[100];

    static int roomCount = 0;
    static int bookingCount = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("--- Welcome to Hotel Reservation System ---");
        while (true) {
            showMenu();
            int choice = sc.nextInt();
            switch (choice) {
                case 0:
                    addRooms(sc);
                    break;
                case 1:
                    viewAvailable();
                    break;
                case 2:
                    bookRoom(sc);
                    break;
                case 3:
                    cancelBooking(sc);
                    break;
                case 4:
                    viewBookings("booking.txt");
                    break;
                case 5:
                    System.out.println("Exiting System!");
                    System.exit(0);
                default:
                    System.out.println("Invalid choice!");
                    System.out.println("Select among 0 to 5");
            }
            System.out.println("----------------------------");
        }

    }
    static void showMenu(){
        System.out.println("Select a choice ");
        System.out.print("0.Add a Room\n" +
                "1. View Available Rooms\n" +
                "2. Book a Room\n" +
                "3. Cancel Reservation\n" +
                "4. View Bookings\n" +
                "5. Exit\n");
    }
    static void viewAvailable(){
        System.out.println("Available rooms: ");
        for (int i=0; i<roomCount;i++){
            if (rooms[i].getStatus().equalsIgnoreCase("available"))
                rooms[i].display();
        }
    }

    static void bookRoom(Scanner sc){
        boolean roomFound = false;
        int roomId = -1;

        sc.nextLine();

        System.out.println("Enter your name ");
        String name = sc.nextLine();

        System.out.println("Enter desired room category (Standard / Deluxe / Suite)");
        String cat = sc.next();
        sc.nextLine(); // clears buffer

        for(int i=0;i<roomCount;i++){
            if(rooms[i].getCategory().equalsIgnoreCase(cat) && rooms[i].getStatus().equalsIgnoreCase("available")){
                roomFound = true;
                roomId = rooms[i].getNum();
                rooms[i].setStatus("booked");
                break;
            }
        }

        if(roomFound){
            int price = calculatePrice(cat);
            System.out.println("Room Confirmed Pay RS"+price);
            bookings[bookingCount]= new Booking(roomId,name,cat);
            bookingCount++;
            try{
                PrintWriter pw = new PrintWriter(new FileWriter("booking.txt",true));
                pw.println("Room ID "+roomId+" | Category "+cat+" | Booked By "+ name+" | Payment Rs"+price);
                pw.close();
            } catch (IOException e) {
                System.out.println("Unable to store data");
            }
        }
        else {
            System.out.println("No available rooms in this category");
        }

    }

    static int calculatePrice(String category){
        int price = 0;
        if (category.equalsIgnoreCase("Standard"))
            price = 5000;
        else if(category.equalsIgnoreCase("Deluxe"))
            price = 8000;
        else if(category.equalsIgnoreCase("Suite"))
            price = 12000;
        else
            System.out.println("Invalid category!");

        return price;
    }

    static void viewBookings(String filepath){
        System.out.println("Booking list contents: ");
        try{
            Scanner reader = new Scanner(new FileReader(filepath));
            while(reader.hasNextLine()){
                System.out.println(reader.nextLine());
            }
            reader.close();
        }
        catch(IOException e){
            System.out.println("Unable to load contents of booking file");
        }
    }
    static void addRooms(Scanner sc){
        System.out.println("Enter room category");
        String cat = sc.next();

        rooms[roomCount] = new Room(roomCount+1,cat,"available");
        roomCount++;
        System.out.println("Room added successfully.");

        try{
            PrintWriter pw = new PrintWriter(new FileWriter("hotel_data.txt",true));
            pw.println("RoomId "+roomCount+" | Category "+cat+" | Status "+rooms[roomCount-1].getStatus());
            pw.close();
        }
        catch (IOException e){
            System.out.println("Exception occured!");
        }
    }

    static void cancelBooking(Scanner sc){
        System.out.println("Enter room ID to cancel reservation: ");
        int roomID = sc.nextInt();

        boolean found = false;

        for (int i = 0; i < bookingCount; i++) {
            if (bookings[i].getRoomId()== roomID) {
                found = true;

                for (int j = 0; j < roomCount; j++) {
                    if (rooms[j].getNum() == roomID) {
                        rooms[j].setStatus("available");
                        reWritingBookingFile("booking.txt",bookings[i].getName());
                        break;
                    }
                }

                for (int k = i; k < bookingCount - 1; k++) {
                    bookings[k] = bookings[k + 1];
                }
                bookings[--bookingCount] = null;
                System.out.println("Reservation for Room ID " + roomID + " has been canceled.");
                return;
            }
        }
        if (!found) {
            System.out.println("No reservation found for Room ID " + roomID);
        }
    }

    static void reWritingBookingFile(String filepath, String name){
        String[] arr = new String[bookingCount];
        try{
            Scanner reader = new Scanner(new FileReader(filepath));
            int counter = 0;
            while(reader.hasNextLine()){
                String content = reader.nextLine();
                if(!content.contains(name)) {
                    arr[counter] = content;
                    counter++;
                }
            }
            reader.close();

            PrintWriter pw = new PrintWriter(new FileWriter("booking.txt"));
            for(String content: arr) {
                if (content != null) {
                    pw.println(content);
                }
            }
            pw.close();
        }
        catch (IOException e){
            System.out.println("Exception occured!");
        }

    }


}