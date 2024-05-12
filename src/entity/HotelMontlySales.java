package entity;

public class HotelMontlySales {
    private String hotel_name;
    private String month;
    private double montly_sales;

    public HotelMontlySales(String hotel_name, String month, double montly_sales) {
        this.hotel_name = hotel_name;
        this.month = month;
        this.montly_sales = montly_sales;
    }
    public HotelMontlySales(){
    }

    public String getHotel_name() {
        return hotel_name;
    }

    public void setHotel_name(String hotel_name) {
        this.hotel_name = hotel_name;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }

    public double getMontly_sales() {
        return montly_sales;
    }

    public void setMontly_sales(double montly_sales) {
        this.montly_sales = montly_sales;
    }

    @Override
    public String toString() {
        return "HotelMontlySales{" +
                "hotel_name='" + hotel_name + '\'' +
                ", month='" + month + '\'' +
                ", montly_sales=" + montly_sales +
                '}';
    }
}
