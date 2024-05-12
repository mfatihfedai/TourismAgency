package view;

import business.ReservationManager;
import business.RoomManager;
import core.Helper;
import entity.Pension;
import entity.Reservation;
import entity.Room;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ReservationView extends Layout {
    private JPanel container;
    private JLabel lbl_otel_name;
    private JLabel lbl_room_type;
    private JLabel lbl_pension_type;
    private JLabel lbl_city;
    private JLabel lbl_region;
    private JLabel lbl_address;
    private JLabel lbl_otel1;
    private JLabel lbl_otel2;
    private JLabel lbl_otel3;
    private JLabel lbl_otel4;
    private JLabel lbl_otel5;
    private JLabel lbl_otel6;
    private JLabel lbl_otel7;
    private JLabel lbl_room1;
    private JLabel lbl_room2;
    private JLabel lbl_room3;
    private JLabel lbl_room4;
    private JLabel lbl_room5;
    private JFormattedTextField fld_entry_date;
    private JFormattedTextField fld_exit_date;
    private JTextField fld_nmbr_chld;
    private JTextField fld_nmbr_adlt;
    private JTextField fld_prc_adult;
    private JTextField fld_prc_child;
    private JTextField fld_total_price;
    private JPanel pnl_info;
    private JPanel pnl_reservation;
    private JTextField fld_book_name;
    private JTextField fld_book_email;
    private JTextField fld_book_phone;
    private JTextField fld_guest_name;
    private JTextField fld_guest_tc;
    private JButton btn_reservation_save;
    private JLabel lbl_room_head_type;
    private JLabel lbl_pension_head;
    private JLabel lbl_otel_head;
    private JLabel lbl_room_head_service;
    private JLabel lbl_line;
    private JLabel lbl_total_price;
    private JLabel lbl_night;
    private JLabel lbl_rsrvtn_head;
    //private JList<String> list_otel_service;
    private Room room;
    private Reservation reservation;
    private final ReservationManager reservationManager;
    private final RoomManager roomManager;

    public ReservationView(Reservation reservation, Room selectedRoom, String entry_date, String exit_date, int adult_nmbr, int chld_nmbr){
        this.room = selectedRoom;
        if (reservation == null) {
            this.reservation = new Reservation();
        } else {
            this.reservation = reservation;
        }
        this.reservationManager = new ReservationManager();
        this.roomManager = new RoomManager();
        this.add(container);
        this.guiInitilaze(1072, 700);
        subline();
        this.lbl_otel_name.setText(this.room.getOtel().getName());
        this.lbl_room_type.setText(this.room.getType().toString());
        this.lbl_pension_type.setText(this.room.getPension().getName().toString());
        otelServices();
        roomServices();
        this.lbl_city.setText(this.room.getOtel().getCity());
        this.lbl_region.setText(this.room.getOtel().getRegion());
        this.lbl_address.setText(this.room.getOtel().getAddress());
        this.fld_entry_date.setText(entry_date);
        this.fld_exit_date.setText(exit_date);
        this.fld_nmbr_adlt.setText(String.valueOf(adult_nmbr));
        this.fld_nmbr_chld.setText(String.valueOf(chld_nmbr));
        this.fld_prc_adult.setText(String.valueOf(this.room.getPrc_adult()));
        this.fld_prc_child.setText(String.valueOf(this.room.getPrc_chld()));


        if (this.reservation.getId() != 0){
            this.lbl_rsrvtn_head.setText("Update Reservation");
            this.fld_book_name.setText(this.reservation.getBook_name());
            this.fld_book_email.setText(this.reservation.getBook_email());
            this.fld_book_phone.setText(this.reservation.getBook_phone());
            this.fld_guest_name.setText(this.reservation.getGuest_name());
            this.fld_guest_tc.setText(this.reservation.getGuest_tc());
            this.btn_reservation_save.setText("Update Reservation");
        }


        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate entryDate = LocalDate.parse(entry_date, formatter);
        LocalDate exitDate = LocalDate.parse(exit_date, formatter);

        long days =  ChronoUnit.DAYS.between(entryDate, exitDate);
        this.lbl_night.setText(days + " Night");
        int totalPrice = (int) (days *  ((adult_nmbr * this.room.getPrc_adult()) + (chld_nmbr * this.room.getPrc_chld())));
        this.fld_total_price.setText(String.valueOf(totalPrice));

        btn_reservation_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_book_name,
                    this.fld_book_email,
                    this.fld_book_phone,
                    this.fld_guest_name,
                    this.fld_guest_tc
            };
            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            } else {
                boolean result;
                this.reservation.setRoom_id(this.room.getId());
                this.reservation.setBook_name(this.fld_book_name.getText());
                this.reservation.setBook_email(this.fld_book_email.getText());
                this.reservation.setBook_phone(this.fld_book_phone.getText());
                this.reservation.setGuest_name(this.fld_guest_name.getText());
                this.reservation.setGuest_tc(this.fld_guest_tc.getText());
                this.reservation.setEntry_date(LocalDate.parse(entry_date, formatter));
                this.reservation.setExit_date(LocalDate.parse(exit_date, formatter));
                this.reservation.setTotal_price(totalPrice);
                this.reservation.setRoom(this.room);
                this.reservation.setAdultNumber(adult_nmbr);
                this.reservation.setChildNumber(chld_nmbr);


                if (this.reservation.getId() != 0){
                    result = this.reservationManager.update(this.reservation);
                } else {
                    result = this.reservationManager.save(this.reservation);
                    this.room.setStok(this.room.getStok() - 1);
                    this.roomManager.updateStok(this.room);
                }
                if (result){
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }
    private void subline(){
        lbl_room_head_type.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        lbl_pension_head.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        lbl_otel_head.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        lbl_room_head_service.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.BLACK));
        lbl_otel_name.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.DARK_GRAY));
        lbl_total_price.setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.DARK_GRAY));
        lbl_night.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));
    }

    private void otelServices(){
        ArrayList<String> otelServiceList = new ArrayList<>();
        ArrayList<JLabel> lblOtelList = new ArrayList<>();
        lblOtelList.add(lbl_otel1);
        lblOtelList.add(lbl_otel2);
        lblOtelList.add(lbl_otel3);
        lblOtelList.add(lbl_otel4);
        lblOtelList.add(lbl_otel5);
        lblOtelList.add(lbl_otel6);
        lblOtelList.add(lbl_otel7);

        if (this.room.getOtel().isOtopark()){
            otelServiceList.add("        * Otopark");
        }
        if (this.room.getOtel().isWifi()){
            otelServiceList.add("        * Wifi");
        }
        if (this.room.getOtel().isPool()){
            otelServiceList.add("        * Pool");
        }
        if (this.room.getOtel().isFitness()){
            otelServiceList.add("        * Fitness");
        }
        if (this.room.getOtel().isConcierge()){
            otelServiceList.add("        * Concierge");
        }
        if (this.room.getOtel().isSpa()){
            otelServiceList.add("        * SPA");
        }
        if (this.room.getOtel().isService()){
            otelServiceList.add("        * Service");
        }
        for (int i = 0 ; i < otelServiceList.size(); i++){
            lblOtelList.get(i).setText(otelServiceList.get(i));
            lblOtelList.get(i).setVisible(true);
        }
    }
    private void roomServices(){
        ArrayList<String> roomService = new ArrayList<>();
        ArrayList<JLabel> lblList = new ArrayList<>();
        lblList.add(lbl_room1);
        lblList.add(lbl_room2);
        lblList.add(lbl_room3);
        lblList.add(lbl_room4);
        lblList.add(lbl_room5);
        if (this.room.isAircndtn()){
            roomService.add("        * Aircondition");
        }
        if (this.room.isMinibar()){
            roomService.add("        * Minibar");
        }
        if (this.room.isTv()){
            roomService.add("        * Television");
        }
        if (this.room.isSafe()){
            roomService.add("        * Safe");
        }
        if (this.room.isFridge()){
            roomService.add("        * Fridge");
        }
        for (int i = 0 ; i < roomService.size(); i++){
            lblList.get(i).setText(roomService.get(i));
            lblList.get(i).setVisible(true);
        }
    }
}
