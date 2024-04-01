import java.util.Date;

public class Inchiriere extends Entity{
    private Car car;
    private Date startDate;
    private Date endDate;

    public Inchiriere(int ID, Car car, Date startDate, Date endDate) {
        super(ID);
        this.car = car;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Car getCar() {
        return car;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Date getEndDate() {
        return endDate;
    }
}
