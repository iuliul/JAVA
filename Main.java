public class Main {
    public static void main(String[] args) {
        Repository<Car> carRepository = new Repository<>();
        Repository<Inchiriere> inchiriereRepository = new Repository<>();
        Service service = new Service(carRepository, inchiriereRepository);
        UI ui = new UI(service);
        ui.start();
    }
}