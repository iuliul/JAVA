import java.util.Date;
import java.util.Iterator;

public class Service {
    private Repository<Car> repoCar;
    private Repository<Inchiriere> repoInchiriere;

    public Service(Repository<Car> carRepository, Repository<Inchiriere> inchiriereRepository) {
        this.repoCar = carRepository;
        this.repoInchiriere = inchiriereRepository;
        initializeCars(); // Initialize some cars (if needed)
    }

    private void initializeCars() {
        // Add initial cars if needed
        addCar(1, "BMW", "E60");
        addCar(2, "BMW", "E46");
        addCar(3, "Porsche", "Macan");
        addCar(4, "Mercedes", "AMG GT 63");
        addCar(5, "Dacia", "Logan");
    }

    public Iterator<Car> carIterator() {
        return repoCar.iterator();
    }

    public Iterator<Inchiriere> inchiriereIterator() {
        return repoInchiriere.iterator();
    }

    public void addCar(int ID, String marca, String model) {
        Car car = new Car(ID, marca, model);
        if (carExists(car))
            throw new IllegalArgumentException("Car already exists!");
        repoCar.addEntity(car);
    }

    private Boolean carExists(Car car) {
        return repoCar.findEntity(car.getID()) != null;
    }

    public void addInchiriere(int ID, int carID, Date startDate, Date endDate) {
        Car car = repoCar.findEntity(carID);
        if (car == null)
            throw new IllegalArgumentException("The specified car doesn't exist!");

        Inchiriere inchiriere = new Inchiriere(ID, car, startDate, endDate);
        validateInchiriere(inchiriere);
        repoInchiriere.addEntity(inchiriere);
    }

    private void validateInchiriere(Inchiriere inchiriere) {
        if (repoInchiriere.findEntity(inchiriere.getID()) != null)
            throw new IllegalArgumentException("There exists another rental with this ID!");
        if (inchiriere.getStartDate().compareTo(inchiriere.getEndDate()) > 0)
            throw new IllegalArgumentException("Start date cannot be after end date!");
        if (!carIsFree(inchiriere.getStartDate(), inchiriere.getEndDate(), inchiriere.getCar()))
            throw new IllegalArgumentException("Car is not free!");
    }

    private Boolean carIsFree(Date startDate, Date endDate, Car car) {
        for (Inchiriere inchiriere : repoInchiriere.getAll()) {
            if (inchiriere.getCar().equals(car)) {
                if (inchiriere.getEndDate().compareTo(startDate) > 0)
                    return false;
                if (inchiriere.getStartDate().compareTo(endDate) < 0)
                    return false;
            }
        }
        return true;
    }

    public Car readCar(int ID) {
        Car car = repoCar.findEntity(ID);
        if (car == null)
            throw new IllegalArgumentException("Specified ID doesn't exist!");
        return car;
    }

    public Inchiriere readInchiriere(int ID) {
        Inchiriere inchiriere = repoInchiriere.findEntity(ID);
        if (inchiriere == null)
            throw new IllegalArgumentException("Specified ID doesn't exist!");
        return inchiriere;
    }

    public void updateCar(int ID, String marca, String model) {
        Car car = new Car(ID, marca, model);
        if (!carExists(car))
            throw new IllegalArgumentException("Car with specified ID doesn't exist!");
        repoCar.update(car);
    }

    public void updateInchiriere(int ID, int carID, Date startDate, Date endDate) {
        Car car = repoCar.findEntity(carID);
        if (car == null)
            throw new IllegalArgumentException("The specified car doesn't exist!");

        Inchiriere thisInchiriere = new Inchiriere(ID, car, startDate, endDate);
        Inchiriere old = repoInchiriere.findEntity(ID);
        if (old == null)
            throw new IllegalArgumentException("Rental with specified ID doesn't exist!");
        repoInchiriere.deleteEntity(ID);

        try {
            validateInchiriere(thisInchiriere);
            repoInchiriere.addEntity(thisInchiriere);
        } catch (IllegalArgumentException exception) {
            repoInchiriere.addEntity(old);
            throw exception;
        }
    }

    public void deleteCar(int ID) {
        if (repoCar.findEntity(ID) == null)
            throw new IllegalArgumentException("Car with specified ID doesn't exist!");
        repoCar.deleteEntity(ID);
    }

    public void deleteInchiriere(int ID) {
        if (repoInchiriere.findEntity(ID) == null)
            throw new IllegalArgumentException("Rental with specified ID doesn't exist!");
        repoInchiriere.deleteEntity(ID);
    }
}
