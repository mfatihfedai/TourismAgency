package view;

import business.*;
import core.ComboItem;
import core.Helper;
import entity.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.*;
import org.jfree.data.time.ohlc.OHLCItem;
import org.jfree.data.time.ohlc.OHLCSeries;
import org.jfree.data.time.ohlc.OHLCSeriesCollection;
import org.jfree.data.xy.OHLCDataset;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class EmployeeView extends Layout{
    private final User user;
    private UserManager userManager;
    private final OtelManager otelManager;
    private final PensionManager pensionManager;
    private final SeasonManager seasonManager;
    private RoomManager roomManager;
    private ReservationManager reservationManager;
    private ReportManager reportManager;
    private final DefaultTableModel tmdl_otel = new DefaultTableModel();
    private final DefaultTableModel tmdl_pension = new DefaultTableModel();
    private final DefaultTableModel tmdl_season = new DefaultTableModel();
    private final DefaultTableModel tmdl_room = new DefaultTableModel();
    private final DefaultTableModel tmdl_reservation = new DefaultTableModel();
    private final DefaultTableModel tmdl_report = new DefaultTableModel();
    private JPopupMenu otel_menu;
    private JPopupMenu room_menu;
    private JPopupMenu reservation_menu;
    private JPanel container;
    private JLabel lbl_welcome;
    private JButton btn_exit;
    private JPanel pnl_top;
    private JTabbedPane tab_menu;
    private JPanel pnl_otel;
    private JScrollPane scrl_otel;
    private JTable tbl_otel;
    private JButton btn_otel_add;
    private JScrollPane scrl_pension;
    private JScrollPane scrl_season;
    private JTable tbl_season;
    private JTable tbl_pension;
    private JPanel pnl_room;
    private JLabel lbl_pension;
    private JLabel lbl_season;
    private JTextField fld_srch_city;
    private JTextField fld_srch_otel;
    private JFormattedTextField fld_room_strt_date;
    private JFormattedTextField fld_room_fnsh_date;
    private JComboBox<ComboItem> cmb_room_srch_adult;
    private JComboBox<ComboItem> cmb_room_srch_chld;
    private JButton btn_room_search;
    private JButton btn_room_clear;
    private JButton btn_room_add;
    private JTable tbl_room;
    private JScrollPane scrl_room;
    private JPanel pnl_reservation;
    private JScrollPane scrl_pane;
    private JTable tbl_reservation;
    private JPanel pnl_report;
    private JButton btn_rprt_clear;
    private JTextField fld_srch_rprt_city;
    private JTextField fld_srch_rprt_otel;
    private JFormattedTextField fld_srch_rprt_fnsh;
    private JButton btn_rprt_search;
    private JFormattedTextField fld_srch_rprt_strt;
    private JButton btn_pie_graph;
    private JButton btn_candle_graph;
    private JButton btn_line_graph;
    private JTable tbl_report;
    private JLabel lbl_graph;
    private JLabel lbl_reports;
    private JTextField fld_rprt_ciro;
    private JTextField fld_rprt_sell_room;
    private JTextField fld_rprt_total_guest;
    private Object[] col_otel_list;
    private Object[] col_pension_list;
    private Object[] col_season_list;
    private Object[] col_room_list;
    private Object[] col_reservation_list;
    private Object[] col_report_list;
    private  LineChart lineChart;
    public EmployeeView(User user){
        this.userManager = new UserManager();
        this.otelManager = new OtelManager();
        this.pensionManager = new PensionManager();
        this.seasonManager = new SeasonManager();
        this.roomManager = new RoomManager();
        this.reservationManager = new ReservationManager();
        this.reportManager = new ReportManager();
        this.lineChart = new LineChart();
        this.add(container);
        this.guiInitilaze(1200, 500);
        this.user = user;
        this.lbl_welcome.setText("Welcome : " + this.user.getUsername());
        lbl_graph.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));
        lbl_reports.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.DARK_GRAY));

        //Logout
        loadComponent();

        //Otel Tab Menu
        loadOtelTable(null);
        loadOtelComponent();

        //Pension Menu
        loadPensionTable(null);
        loadPensionComponent();

        //Season Menu
        loadSeasonTable(null);
        loadSeasonComponent();

        //Room Tab Menu
        loadRoomTable(null);
        loadRoomComponent();
        loadRoomFilter();

        //Reservation Tab Menu
        loadReservationTable(null);
        loadReservationComponent();

        //Report Tab Menu
        loadReportTable(null);
        loadReportComponent();
        loadReportFilter();
        loadReportGraphs();
    }

    private void loadReportGraphs(){
        btn_pie_graph.addActionListener(e -> {
            if (!dateFormatControl(fld_srch_rprt_strt,fld_srch_rprt_fnsh)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            DefaultPieDataset pieDataset = new DefaultPieDataset<>();
            Map<String, Integer> cityCountMap = this.reportManager.pieGraphForFilters(
                    this.fld_srch_rprt_city.getText(),
                    this.fld_srch_rprt_otel.getText(),
                    this.fld_srch_rprt_strt.getText(),
                    this.fld_srch_rprt_fnsh.getText());

            for (Map.Entry<String, Integer> entry : cityCountMap.entrySet()){
                pieDataset.setValue(entry.getKey() , entry.getValue());
            }
            JFreeChart chart = ChartFactory.createPieChart("Pie Chart By City", pieDataset);
            ChartFrame frame = new ChartFrame("Pie Chart By City", chart);
            frame.setVisible(true);
            frame.setSize(650,650);
        });

        btn_line_graph.addActionListener(e -> {
            if (!dateFormatControl(fld_srch_rprt_strt,fld_srch_rprt_fnsh)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            ArrayList<Reservation> reservationArrayList = this.reportManager.searchForReservation(
                    this.fld_srch_rprt_city.getText(),
                    this.fld_srch_rprt_otel.getText(),
                    this.fld_srch_rprt_strt.getText(),
                    this.fld_srch_rprt_fnsh.getText()
            );
            Map<String, Integer> lineChartMap = lineChart.calculateMonthlyRevenue(reservationArrayList);

            LineChart lineChartResult = new LineChart(
                    "Line Chart",
                    lineChartMap
            );
            lineChartResult.setSize(800, 600);
            lineChartResult.setLocationRelativeTo(null);
            lineChartResult.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            lineChartResult.setVisible(true);
        });

        btn_candle_graph.addActionListener(e -> {
            if (!dateFormatControl(fld_srch_rprt_strt,fld_srch_rprt_fnsh)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            Map<YearMonth, Integer> guestCountMap = this.reportManager.candleGraphForFilters(
                    this.fld_srch_rprt_city.getText(),
                    this.fld_srch_rprt_otel.getText(),
                    this.fld_srch_rprt_strt.getText(),
                    this.fld_srch_rprt_fnsh.getText());

            OHLCSeries series = new OHLCSeries("Guest Count By Month");

            for (Map.Entry<YearMonth, Integer> entry : guestCountMap.entrySet()) {
                YearMonth yearMonth = entry.getKey();
                RegularTimePeriod period = new Month(Date.from(yearMonth.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant()));
                OHLCItem item = new OHLCItem(period, 0, 0, 0, entry.getValue());
                series.add(item);
            }

            OHLCSeriesCollection dataset = new OHLCSeriesCollection();
            dataset.addSeries(series);

            JFreeChart chart = ChartFactory.createCandlestickChart(
                    "Guest Count By Month",
                    "Month",
                    "Guest Count",
                    dataset,
                    false
            );
            NumberAxis yAxis = (NumberAxis) chart.getXYPlot().getRangeAxis();
            yAxis.setAutoRangeIncludesZero(true);
            yAxis.setRangeWithMargins(0, 5);

            //Üst sınırı belirler.
            double maxValue = guestCountMap.values().stream().mapToDouble(Integer::doubleValue).max().orElse(0.0);
            yAxis.setUpperBound(maxValue + 2);

            // Y ekseni etiketlerini tam sayılarla göstermek için
            yAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

            ChartFrame frame = new ChartFrame("Guest Count By Month", chart);
            frame.setVisible(true);
            frame.setSize(650, 650);
        });

    }
    private void loadReportTable(ArrayList<Object[]> reportList){
        col_report_list = new Object[]{"Hotel Name", "Hotel City", "Execution Date", "Pension Type", "Room Type", "Total Price"};
        if (reportList == null){
            reportList = this.reportManager.getForTable(col_report_list.length, this.reservationManager.findAll());

            //Reports information are filled by default.
            this.fld_rprt_ciro.setText(this.reportManager.total_giro(this.reservationManager.findAll()) + " ₺");
            this.fld_rprt_sell_room.setText(String.valueOf(this.reportManager.total_room(this.reservationManager.findAll())));
            this.fld_rprt_total_guest.setText(String.valueOf(this.reportManager.total_guest(this.reservationManager.findAll())));
        }
        createTable(this.tmdl_report, tbl_report, col_report_list, reportList);
    }

    private void loadReportComponent(){
        this.fld_srch_rprt_strt.setText("01/01/2024");
        this.fld_srch_rprt_fnsh.setText("01/05/2024");
        btn_rprt_search.addActionListener(e -> {
            if (!dateFormatControl(fld_srch_rprt_strt,fld_srch_rprt_fnsh)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            ArrayList<Reservation> reservationArrayList = this.reportManager.searchForReservation(
                    this.fld_srch_rprt_city.getText(),
                    this.fld_srch_rprt_otel.getText(),
                    this.fld_srch_rprt_strt.getText(),
                    this.fld_srch_rprt_fnsh.getText()
            );
            //Reports information are filled after search.
            this.fld_rprt_ciro.setText(this.reportManager.total_giro(reservationArrayList) + " ₺");
            this.fld_rprt_sell_room.setText(String.valueOf(this.reportManager.total_room(reservationArrayList)));
            this.fld_rprt_total_guest.setText(String.valueOf(this.reportManager.total_guest(reservationArrayList)));

            ArrayList<Object[]> reservationList = this.reportManager.getForTable(col_report_list.length, reservationArrayList);

            loadReportTable(reservationList);

        });
    }
    private void loadReportFilter(){
        btn_rprt_clear.addActionListener(e -> {
            this.fld_srch_rprt_otel.setText("");
            this.fld_srch_rprt_city.setText("");
            loadReportTable(null);
        });
    }
    private void loadReservationTable(ArrayList<Object[]> reservationList){
        col_reservation_list = new Object[]{
                "Reservation ID", "Name Surname", "Hotel Name", "Entry Date",
                "Exit Date", "Phone", "Adult", "Child",
                "Pension", "Room Type", "Price"
        };
        if (reservationList == null){
            reservationList = this.reservationManager.getForTable(col_reservation_list.length, this.reservationManager.findAll());
        }
        createTable(this.tmdl_reservation, this.tbl_reservation, col_reservation_list, reservationList);
    }
    private void loadReservationComponent(){
        tableRowSelect(tbl_reservation);
        this.reservation_menu = new JPopupMenu();
        this.reservation_menu.add("Update Reservation").addActionListener(e -> {
            int selectReservationId = this.getTableSelectedRow(tbl_reservation, 0);

            Reservation selectReservation = this.reservationManager.getById(selectReservationId);
            //Seçilen room nesnesi bulunur.
            int selectRoomId = selectReservation.getRoom_id();
            Room selectedRoom = this.roomManager.getById(selectRoomId);

            ReservationView reservationView = new ReservationView(
                    selectReservation,
                    selectedRoom,
                    selectReservation.getEntry_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    selectReservation.getExit_date().format(DateTimeFormatter.ofPattern("dd/MM/yyyy")),
                    selectReservation.getAdultNumber(),
                    selectReservation.getChildNumber());
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadReservationTable(null);
                }
            });
        });

        this.reservation_menu.add("Delete Reservation").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectReservationId = this.getTableSelectedRow(tbl_reservation, 0);
                Reservation selectReservation = this.reservationManager.getById(selectReservationId);
                int selectRoomId = selectReservation.getRoom_id();
                Room selectedRoom = this.roomManager.getById(selectRoomId);
                selectedRoom.setStok(selectedRoom.getStok() + 1);
                if (this.reservationManager.delete(selectReservationId)){
                    this.roomManager.updateStok(selectedRoom);
                    Helper.showMsg("done");
                    loadReservationTable(null);
                    loadRoomTable(null);
                    loadReportTable(null);
                }
            }
        });
        this.tbl_reservation.setComponentPopupMenu(reservation_menu);
    }
    private void loadRoomTable(ArrayList<Object[]> roomList){
        col_room_list = new Object[]{
                "Room ID", "Hotel Name", "Pension Type", "Strt Date",
                "Fnsh Date", "Room Type", "Stock", "Bed", "MeterSqr",
                "Price(Adult)", "Price(Child)", "Aircndtn", "Minibar",
                "TV", "Safe", "Fridge"};
        if (roomList == null){
            roomList = this.roomManager.getForTable(col_room_list.length, this.roomManager.findAll());
        }
        createTable(this.tmdl_room, this.tbl_room, col_room_list, roomList);
    }

    private void loadRoomComponent(){
        tableRowSelect(tbl_room);
        this.room_menu = new JPopupMenu();
        this.room_menu.add("Add Reservation").addActionListener(e -> {
            //Misafir bilgileri comboBoxa eklenir.
            int selectedAdult;
            int selectedChild;
            if (this.cmb_room_srch_adult.getSelectedItem() == null){
                Helper.showMsg("Please enter guest number.");
                return;
            } else {
                selectedAdult = ((ComboItem) this.cmb_room_srch_adult.getSelectedItem()).getKey();
            }
            if (this.cmb_room_srch_chld.getSelectedItem() == null){
                selectedChild = 0;
            } else {
                selectedChild = ((ComboItem) this.cmb_room_srch_chld.getSelectedItem()).getKey();
            }
            if (!dateFormatControl(fld_room_strt_date,fld_room_fnsh_date)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            int selectId = this.getTableSelectedRow(this.tbl_room, 0);
            ReservationView reservationView = new ReservationView(
                    null,
                    this.roomManager.getById(selectId),
                    this.fld_room_strt_date.getText(),
                    this.fld_room_fnsh_date.getText(),
                    selectedAdult,
                    selectedChild
            );
            reservationView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                    loadReservationTable(null);
                    loadRoomFilter();
                    loadReportTable(null);
                }
            });
        });
        this.room_menu.add("Delete Room").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectRoomId = this.getTableSelectedRow(tbl_room, 0);
                if (this.roomManager.delete(selectRoomId)){
                    Helper.showMsg("done");
                    loadRoomTable(null);
                }
            }
        });
        this.tbl_room.setComponentPopupMenu(room_menu);

        addSearchListener("otel_city", this.fld_srch_city);
        addSearchListener("otel_name", this.fld_srch_otel);

        addAdultChildCmb(this.cmb_room_srch_adult, this.cmb_room_srch_chld);

        btn_room_search.addActionListener(e -> {
            //Misafir bilgileri int dönüşümü yapılır. Yetişkin sayısının doluluğu kontrol edilir.
            ComboItem selectAdult= (ComboItem) this.cmb_room_srch_adult.getSelectedItem();
            int selectedAdult = selectAdult.getKey();
            ComboItem selectChild = (ComboItem) this.cmb_room_srch_chld.getSelectedItem();
            int selectedChild = selectChild.getKey();
            if (selectedAdult == 0){
                Helper.showMsg("Please enter guest number.");
                return;
            }
            //Tarih formatı doğruluğu kontrol edilir.
            if (!dateFormatControl(this.fld_room_strt_date, this.fld_room_fnsh_date)){
                Helper.showMsg("Please enter valid date.");
                return;
            }
            ArrayList<Room> roomList = this.roomManager.searchForRoom(
                    this.fld_srch_city.getText(),
                    this.fld_srch_otel.getText(),
                    this.fld_room_strt_date.getText(),
                    this.fld_room_fnsh_date.getText(),
                    selectedAdult,
                    selectedChild
            );
            ArrayList<Object[]> searchedResult = this.roomManager.getForTable(this.col_room_list.length, roomList);
            loadRoomTable(searchedResult);
        });

        btn_room_add.addActionListener(e -> {
            RoomAddView roomAddView = new RoomAddView();
            roomAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadRoomTable(null);
                }
            });
        });
    }
    private void loadRoomFilter(){
        btn_room_clear.addActionListener(e -> {
            this.cmb_room_srch_adult.setSelectedItem(null);
            this.cmb_room_srch_chld.setSelectedItem(null);
            this.fld_srch_otel.setText("");
            this.fld_srch_city.setText("");
            loadRoomTable(null);
        });
    }
    private void loadSeasonComponent(){
        tableRowSelect(tbl_season);
        this.otel_menu = new JPopupMenu();
        this.otel_menu.add("Delete Season").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectSeasonId = this.getTableSelectedRow(tbl_season, 0);
                if (this.seasonManager.delete(selectSeasonId)){
                    Helper.showMsg("done");
                    loadSeasonTable(null);
                }
            }
        });
        this.tbl_season.setComponentPopupMenu(otel_menu);
    }
    private void loadSeasonTable(ArrayList<Object[]> seasonList){
        col_season_list = new Object[]{"Season ID", "Hotel Name", "Start Date", "Finish Date"};
        if (seasonList == null){
            seasonList = this.seasonManager.getForTable(col_season_list.length, this.seasonManager.findAll());
        }
        createTable(this.tmdl_season, this.tbl_season, col_season_list,seasonList);
    }
    private void loadPensionComponent(){
        tableRowSelect(tbl_pension);
        this.otel_menu = new JPopupMenu();
        this.otel_menu.add("Delete Pension").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectPensionId = this.getTableSelectedRow(tbl_pension, 0);
                if (this.pensionManager.delete(selectPensionId)){
                    Helper.showMsg("done");
                    loadPensionTable(null);
                }
            }
        });
        this.tbl_pension.setComponentPopupMenu(otel_menu);
    }
    private void loadPensionTable(ArrayList<Object[]> pensionList){
        col_pension_list = new Object[]{"Pension ID", "Hotel Name", "Pension Type"};
        if (pensionList == null){
            pensionList = this.pensionManager.getForTable(col_pension_list.length, this.pensionManager.findAll());
        }
        createTable(this.tmdl_pension, this.tbl_pension, col_pension_list, pensionList);
    }
    private void loadOtelTable(ArrayList<Object[]> otelList){
        col_otel_list = new Object[]{"Hotel ID", "Hotel Name", "City", "Region",
                "Address", "E-Mail", "Phone", "Star", "Otopark", "Wifi",
                "Pool", "Fitness", "Concierge", "SPA", "7/24 Room Service"};
            otelList = this.otelManager.getForTable(col_otel_list.length, this.otelManager.findAll());
        createTable(this.tmdl_otel, this.tbl_otel, col_otel_list, otelList);
    }
    private void loadOtelComponent(){
        tableRowSelect(tbl_otel);
        this.otel_menu = new JPopupMenu();
        this.otel_menu.add("Add Pension").addActionListener(e -> {
            PensionAddView pensionAddView = new PensionAddView(new Pension());
            pensionAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadPensionTable(null);
                }
            });
        });
        this.otel_menu.add("Add Season").addActionListener(e -> {
            SeasonAddView seasonAddView = new SeasonAddView();
            seasonAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadSeasonTable(null);
                }
            });
        });

        btn_otel_add.addActionListener(e -> {
            OtelAddView otelAddView = new OtelAddView(new Otel());
            otelAddView.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadOtelTable(null);
                }
            });
        });
        this.tbl_otel.setComponentPopupMenu(otel_menu);
    }
    private void loadComponent(){
        btn_exit.addActionListener(e -> {
            dispose();
            LoginView loginView = new LoginView();
        });
    }
    private void createUIComponents() throws ParseException {
        this.fld_room_strt_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_room_fnsh_date = new JFormattedTextField(new MaskFormatter("##/##/####"));
        this.fld_room_strt_date.setText("01/01/2024");
        this.fld_room_fnsh_date.setText("01/05/2024");
    }
    private void addSearchListener(String name, JTextField fld_srch){
        fld_srch.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateSearch();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                updateSearch();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
            }
            private void updateSearch() {
                ArrayList<Room> roomList = roomManager.searchForRoomOtel(
                        name,
                        fld_srch.getText()
                );
                ArrayList<Object[]> searchedResult = roomManager.getForTable(col_room_list.length, roomList);
                loadRoomTable(searchedResult);
            }
        });
    }
    private boolean dateFormatControl(JFormattedTextField strt_date, JFormattedTextField fnsh_date) {
        String startDateText = strt_date.getText();
        String finishDateText = fnsh_date.getText();

        if (startDateText.isEmpty() && finishDateText.isEmpty()) {
            // Her iki tarih de boşsa, geçerli sayılabilir
            return true;
        }

        try {
            if (!startDateText.isEmpty()) {
                LocalDate.parse(startDateText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
            if (!finishDateText.isEmpty()) {
                LocalDate.parse(finishDateText, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
            }
        } catch (Exception exception) {
            return false;
        }
        return true;
    }
    private void addAdultChildCmb(JComboBox<ComboItem> adultNumber, JComboBox<ComboItem> childNumber){
        Object[] adultList = {
                new ComboItem(0, " "),
                new ComboItem(1, "1 Adult"),
                new ComboItem(2, "2 Adult"),
                new ComboItem(3, "3 Adult"),
                new ComboItem(4, "4 Adult"),
                new ComboItem(5, "5 Adult")
        };
        Object[] childList = {
                new ComboItem(0, " "),
                new ComboItem(1, "1 Child"),
                new ComboItem(2, "2 Child"),
                new ComboItem(3, "3 Child"),
                new ComboItem(4, "4 Child"),
                new ComboItem(5 ,"5 Child")
        };

        //Misafir bilgileri comboBoxa eklenir.
        for (Object obj : adultList){
            ComboItem comboItem = (ComboItem) obj;
            adultNumber.addItem(comboItem);
        }

        for (Object obj : childList) {
            ComboItem comboItem = (ComboItem) obj;
            childNumber.addItem(comboItem);
        }
    }
}
