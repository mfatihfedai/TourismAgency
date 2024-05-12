package entity;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
    private int id;
    private Room room;
    private int room_id;
    private String book_name;
    private String book_email;
    private String book_phone;
    private String guest_name;
    private String guest_tc;
    private int adultNumber;
    private int childNumber;
    private int total_price;
    private LocalDate entry_date;
    private LocalDate exit_date;
    public Reservation(){
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public String getBook_email() {
        return book_email;
    }

    public void setBook_email(String book_email) {
        this.book_email = book_email;
    }

    public String getBook_phone() {
        return book_phone;
    }

    public void setBook_phone(String book_phone) {
        this.book_phone = book_phone;
    }

    public String getGuest_name() {
        return guest_name;
    }

    public void setGuest_name(String guest_name) {
        this.guest_name = guest_name;
    }

    public String getGuest_tc() {
        return guest_tc;
    }

    public void setGuest_tc(String guest_tc) {
        this.guest_tc = guest_tc;
    }

    public int getTotal_price() {
        return total_price;
    }

    public void setTotal_price(int total_price) {
        this.total_price = total_price;
    }

    public LocalDate getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(LocalDate entry_date) {
        this.entry_date = entry_date;
    }

    public LocalDate getExit_date() {
        return exit_date;
    }

    public void setExit_date(LocalDate exit_date) {
        this.exit_date = exit_date;
    }

    public int getAdultNumber() {
        return adultNumber;
    }

    public void setAdultNumber(int adultNumber) {
        this.adultNumber = adultNumber;
    }

    public int getChildNumber() {
        return childNumber;
    }

    public void setChildNumber(int childNumber) {
        this.childNumber = childNumber;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", room=" + room +
                ", room_id=" + room_id +
                ", book_name='" + book_name + '\'' +
                ", book_email='" + book_email + '\'' +
                ", book_phone='" + book_phone + '\'' +
                ", guest_name='" + guest_name + '\'' +
                ", guest_tc='" + guest_tc + '\'' +
                ", adultNumber=" + adultNumber +
                ", childNumber=" + childNumber +
                ", total_price=" + total_price +
                ", entry_date=" + entry_date +
                ", exit_date=" + exit_date +
                '}';
    }
}
