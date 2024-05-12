package view;

import business.OtelManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import entity.Otel;
import entity.Season;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class SeasonAddView extends Layout {
    private JComboBox<ComboItem> cmb_otel_info;
    private JPanel container;
    private JFormattedTextField fld_strt_date;
    private JFormattedTextField fld_fnsh_date;
    private JButton btn_save;
    private Otel otel;
    private final OtelManager otelManager;
    private final SeasonManager seasonManager;
    public SeasonAddView() {
        this.otelManager = new OtelManager();
        this.seasonManager = new SeasonManager();
        this.add(container);
        this.guiInitilaze(300, 400);
        for (Otel otel : this.otelManager.findAll()) {
            this.cmb_otel_info.addItem(otel.getComboItem());
        }


        btn_save.addActionListener(e -> {
            JTextField[] checkFieldList = {
                    this.fld_strt_date,
                    this.fld_fnsh_date
            };
            if (Helper.isFieldListEmpty(checkFieldList)){
                Helper.showMsg("fill");
            } else {
                Season season = new Season();
                ComboItem selectedOtelInfo = (ComboItem) this.cmb_otel_info.getSelectedItem();
                season.setOtel_id(selectedOtelInfo.getKey());

                String strtDateText = this.fld_strt_date.getText();
                String fnshDateText = this.fld_fnsh_date.getText();

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

                try {
                    season.setStrt_date(LocalDate.parse(strtDateText, formatter));
                    season.setFnsh_date(LocalDate.parse(fnshDateText, formatter));

                    if (this.seasonManager.save(season)) {
                        Helper.showMsg("done");
                        dispose();
                    } else {
                        Helper.showMsg("error");
                    }
                } catch(DateTimeException ex){
                    Helper.showMsg("Invalid Date Format");
                }
            }
        });
    }
    private void createUIComponents() throws ParseException {
        fld_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        fld_strt_date.setText("01/01/2024");
        this.fld_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_fnsh_date.setText("01/05/2024");
    }
}
/*
SELECT *
FROM room r
JOIN otel o ON r.room_otel_id = o.otel_id
LEFT JOIN season s ON r.room_season_id = s.season_id
WHERE
  s.strt_date <= '02/02/2024'
  AND s.fnsh_date >= '05/04/2024'
  AND r.room_bed >= 1 + 0
  AND r.room_stok > 0
  AND LOWER(o.otel_city) LIKE LOWER('%ank%')
  AND LOWER(o.otel_name) LIKE LOWER('%h%')
  */
