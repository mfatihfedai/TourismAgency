package view;

import business.OtelManager;
import core.Helper;
import dao.OtelDao;
import entity.Otel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class OtelAddView extends Layout {
    private JPanel container;
    private JTextField fld_name;
    private JTextField fld_city;
    private JTextField fld_region;
    private JTextField fld_address;
    private JTextField fld_email;
    private JTextField fld_phone;
    private JComboBox<Otel.Rate> cmb_rate;
    private JRadioButton radio_otopark;
    private JRadioButton radio_wifi;
    private JRadioButton radio_pool;
    private JRadioButton radio_fitness;
    private JRadioButton radio_concierge;
    private JRadioButton radio_spa;
    private JRadioButton radio_service;
    private JButton btn_save;
    private Otel otel;
    private final OtelManager otelManager;
    private OtelDao otelDao;

    public OtelAddView(Otel otel){
        this.add(container);
        this.otelManager = new OtelManager();
        this.otel = otel;
        this.guiInitilaze(600,400);

        this.cmb_rate.setModel(new DefaultComboBoxModel<>(Otel.Rate.values()));

        if (this.otel.getId() != 0){
            this.fld_name.setText(this.otel.getName());
            this.fld_city.setText(this.otel.getCity());
            this.fld_region.setText(this.otel.getRegion());
            this.fld_address.setText(this.otel.getAddress());
            this.fld_email.setText(this.otel.getEmail());
            this.fld_phone.setText(this.otel.getPhone());
            this.cmb_rate.setSelectedItem(this.otel.getRate());
            this.radio_otopark.setSelected(this.otel.isOtopark());
            this.radio_wifi.setSelected(this.otel.isWifi());
            this.radio_pool.setSelected(this.otel.isPool());
            this.radio_fitness.setSelected(this.otel.isFitness());
            this.radio_concierge.setSelected(this.otel.isConcierge());
            this.radio_spa.setSelected(this.otel.isSpa());
            this.radio_service.setSelected(this.otel.isService());
        }

        btn_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{
                    this.fld_name,
                    this.fld_city,
                    this.fld_region,
                    this.fld_address,
                    this.fld_email,
                    this.fld_phone})){
                Helper.showMsg("fill");
            } else {
                boolean result = false;
                this.otel.setName(this.fld_name.getText()); //lower olarak yaz
                this.otel.setCity(this.fld_city.getText());
                this.otel.setRegion(this.fld_region.getText());
                this.otel.setAddress(this.fld_address.getText());
                this.otel.setEmail(this.fld_email.getText());
                this.otel.setPhone(this.fld_phone.getText());
                this.otel.setRate((Otel.Rate) this.cmb_rate.getSelectedItem());
                this.otel.setOtopark(this.radio_otopark.getModel().isSelected());
                this.otel.setWifi(this.radio_wifi.getModel().isSelected());
                this.otel.setPool(this.radio_pool.getModel().isSelected());
                this.otel.setFitness(this.radio_fitness.getModel().isSelected());
                this.otel.setConcierge(this.radio_concierge.getModel().isSelected());
                this.otel.setSpa(this.radio_spa.getModel().isSelected());
                this.otel.setService(this.radio_service.getModel().isSelected());


                //Update
                if (this.otel.getId() != 0){
                    //result = this.otelManager.update(this.otel);
                } else {
                    result = this.otelManager.save(this.otel);
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
}
