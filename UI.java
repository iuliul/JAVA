import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;

public class UI {
    private Service service;
    private Scanner scanner;

    public UI(Service service) {
        this.service = service;
        this.scanner = new Scanner(System.in);
    }

    public void start() {
        while (true) {
            int option;
            menu();
            option = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (option) {
                case 1:
                    addCar();
                    break;
                case 2:
                    addInchiriere();
                    break;
                case 3:
                    listAllCars();
                    break;
                case 4:
                    listAllInchirieri();
                    break;
                case 5:
                    updateCar();
                    break;
                case 6:
                    updateInchiriere();
                    break;
                case 7:
                    deleteCar();
                    break;
                case 8:
                    deleteInchiriere();
                    break;
                case 0:
                    System.out.println("Exiting the Car Management App.");
                    scanner.close();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }
    }

    private void menu() {
        System.out.println("Car Management Menu:");
        System.out.println("1. Add Car");
        System.out.println("2. Add Inchiriere");
        System.out.println("3. List all Cars");
        System.out.println("4. List all Inchirieri");
        System.out.println("5. Update Car");
        System.out.println("6. Update Inchiriere");
        System.out.println("7. Delete Car");
        System.out.println("8. Delete Inchiriere");
        System.out.println("0. Exit");
        System.out.print("Enter your choice: ");
    }

    private void addCar() {
        System.out.println("Add a new car:");
        System.out.print("Enter ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Enter Model: ");
        String model = scanner.nextLine();
        service.addCar(ID, marca, model);
        System.out.println("Car added successfully!");
    }



    private void addInchiriere() {
        System.out.println("Add a new Inchiriere:");
        System.out.print("Enter ID: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Car ID: ");
        int carID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter Start Date (dd/MM/yyyy): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Enter End Date (dd/MM/yyyy): ");
        String endDateStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            service.addInchiriere(ID, carID, startDate, endDate);
            System.out.println("Inchiriere added successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void listAllCars() {
        Iterator<Car> cars = service.carIterator();
        System.out.println("List of Cars:");
        while (cars.hasNext()) {
            Car car = cars.next();
            System.out.println(car.getID() + ". " + car.getMarca() + " " + car.getModel());
        }
    }

    private void listAllInchirieri() {
        Iterator<Inchiriere> inchirieri = service.inchiriereIterator();
        System.out.println("List of Inchirieri:");
        while (inchirieri.hasNext()) {
            Inchiriere inchiriere = inchirieri.next();
            System.out.println(
                    inchiriere.getID() + ". Car ID: " + inchiriere.getCar().getID() +
                            ", Start Date: " + inchiriere.getStartDate() +
                            ", End Date: " + inchiriere.getEndDate()
            );
        }
    }

    private void updateCar() {
        System.out.print("Enter Car ID to update: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Marca: ");
        String marca = scanner.nextLine();
        System.out.print("Enter New Model: ");
        String model = scanner.nextLine();

        try {
            service.updateCar(ID, marca, model);
            System.out.println("Car updated successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void updateInchiriere() {
        System.out.print("Enter Inchiriere ID to update: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Car ID: ");
        int carID = scanner.nextInt();
        scanner.nextLine();
        System.out.print("Enter New Start Date (dd/MM/yyyy): ");
        String startDateStr = scanner.nextLine();
        System.out.print("Enter New End Date (dd/MM/yyyy): ");
        String endDateStr = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date startDate = dateFormat.parse(startDateStr);
            Date endDate = dateFormat.parse(endDateStr);

            service.updateInchiriere(ID, carID, startDate, endDate);
            System.out.println("Inchiriere updated successfully!");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy.");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteCar() {
        System.out.print("Enter Car ID to delete: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        try {
            service.deleteCar(ID);
            System.out.println("Car deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void deleteInchiriere() {
        System.out.print("Enter Inchiriere ID to delete: ");
        int ID = scanner.nextInt();
        scanner.nextLine();
        try {
            service.deleteInchiriere(ID);
            System.out.println("Inchiriere deleted successfully!");
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}