package view;

import business.OtelManager;
import business.PensionManager;
import core.ComboItem;
import core.Helper;
import entity.Otel;
import entity.Pension;

import javax.net.ssl.HostnameVerifier;
import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PensionAddView extends Layout{
    private JPanel container;
    private JComboBox<ComboItem> cmb_otel_info;
    private JComboBox<Pension.Name> cmb_pension_name;
    private JButton btn_save;
    private Pension pension;
    private OtelManager otelManager;
    private PensionManager pensionManager;


    public PensionAddView(Pension pension){

        this.pension = pension;
        this.otelManager = new OtelManager();
        this.pensionManager = new PensionManager();
        this.add(container);
        this.guiInitilaze(300,350);

        for (Otel otel : this.otelManager.findAll()){
            this.cmb_otel_info.addItem(otel.getComboItem());
        }
        this.cmb_pension_name.setModel(new DefaultComboBoxModel<>(Pension.Name.values()));

        btn_save.addActionListener(e -> {
            boolean result;
            ComboItem selectedOtelInfo = (ComboItem) cmb_otel_info.getSelectedItem();
            this.pension.setOtel_id(selectedOtelInfo.getKey());
            this.pension.setName((Pension.Name) cmb_pension_name.getSelectedItem());

            result = this.pensionManager.save(this.pension);

            if (result) {
                Helper.showMsg("done");
                dispose();
            } else {
                Helper.showMsg("error");
            }
        });
    }

}
