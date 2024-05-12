package view;

import business.OtelManager;
import business.PensionManager;
import business.RoomManager;
import business.SeasonManager;
import core.ComboItem;
import core.Helper;
import dao.PensionDao;
import entity.Otel;
import entity.Pension;
import entity.Room;
import entity.Season;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Objects;

public class RoomAddView extends Layout {
    private JPanel container;
    private JComboBox<ComboItem> cmb_otel_info;
    private JComboBox<ComboItem> cmb_season_info;
    private JComboBox<ComboItem> cmb_pension;
    private JComboBox<Room.Type> cmb_room_type;
    private JTextField fld_room_stok;
    private JTextField fld_room_bed;
    private JTextField fld_room_mtrsqr;
    private JTextField fld_room_prc_adult;
    private JTextField fld_room_prc_chld;
    private JRadioButton radio_room_aircntn;
    private JRadioButton radio_room_minibar;
    private JRadioButton radio_room_tv;
    private JRadioButton radio_room_safe;
    private JRadioButton radio_room_fridge;
    private JButton btn_room_save;
    private Room room;
    private RoomManager roomManager;
    private OtelManager otelManager;
    private final SeasonManager seasonManager;
    private PensionManager pensionManager;
    private final PensionDao pensionDao;

    public RoomAddView() {
        this.roomManager = new RoomManager();
        this.otelManager = new OtelManager();
        this.seasonManager = new SeasonManager();
        this.pensionManager = new PensionManager();
        this.pensionDao = new PensionDao();
        this.add(container);
        this.guiInitilaze(600, 500);

        for (Otel otel : this.otelManager.findAll()) {
            this.cmb_otel_info.addItem(otel.getComboItem());
        }
        cmb_otel_info.addActionListener(e -> {

            //Session comboBox'ı güncellenir.
            ArrayList<Season> seasons = this.seasonManager.getSeasonsByOtelId(((ComboItem) cmb_otel_info.getSelectedItem()).getKey());
            updateSeasonComboBox(seasons);

            //Pension comboBox'ı güncellenir.
            ComboItem selectedOtelItem = (ComboItem) cmb_otel_info.getSelectedItem();
            int selectedOtelId = selectedOtelItem.getKey();
            ArrayList<Pension> pensions = getPensionsByOtelId(selectedOtelId);
            updatePensionComboBox(pensions);
        });

        this.cmb_room_type.setModel(new DefaultComboBoxModel<>(Room.Type.values()));

        btn_room_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{
                    this.fld_room_stok,
                    this.fld_room_bed,
                    this.fld_room_mtrsqr,
                    this.fld_room_prc_adult,
                    this.fld_room_prc_chld})){
                Helper.showMsg("fill");
            } else {
                this.room = new Room();
                boolean result;
                ComboItem selectedOtelInfo = (ComboItem) cmb_otel_info.getSelectedItem();
                this.room.setOtel_id(selectedOtelInfo.getKey());

                ComboItem selectedSeasonInfo = (ComboItem) cmb_season_info.getSelectedItem();
                this.room.setSeason_id(selectedSeasonInfo.getKey());

                ComboItem selectedPensionInfo = (ComboItem) cmb_pension.getSelectedItem();
                this.room.setPension_id(selectedPensionInfo.getKey());

                this.room.setType((Room.Type) cmb_room_type.getSelectedItem());
                this.room.setStok(Integer.parseInt(this.fld_room_stok.getText()));
                this.room.setBed(Integer.parseInt(this.fld_room_bed.getText()));
                this.room.setMtrsqr(Integer.parseInt(this.fld_room_mtrsqr.getText()));
                this.room.setPrc_adult(Integer.parseInt(this.fld_room_prc_adult.getText()));
                this.room.setPrc_chld(Integer.parseInt(this.fld_room_prc_chld.getText()));
                this.room.setAircndtn(this.radio_room_aircntn.getModel().isSelected());
                this.room.setMinibar(this.radio_room_minibar.getModel().isSelected());
                this.room.setTv(this.radio_room_tv.getModel().isSelected());
                this.room.setSafe(this.radio_room_safe.getModel().isSelected());
                this.room.setFridge(this.radio_room_fridge.getModel().isSelected());

                result = this.roomManager.save(this.room);
                if (result){
                    Helper.showMsg("done");
                    dispose();
                } else {
                    Helper.showMsg("error");
                }
            }
        });
    }

    private ArrayList<Pension> getPensionsByOtelId(int otelId) {
        return pensionDao.getPensionsByOtelId(otelId);
    }

    private void updatePensionComboBox(ArrayList<Pension> pensions) {
        cmb_pension.removeAllItems(); // Var olan öğeleri temizle

        // Yeni pansiyonları combobox'a ekle
        for (Pension pension : pensions) {
            cmb_pension.addItem(pension.getComboItem());
        }
    }

    private void updateSeasonComboBox(ArrayList<Season> seasons) {
        cmb_season_info.removeAllItems();
        for (Season season : seasons) {
            cmb_season_info.addItem(season.getComboItem());
        }
    }
}
