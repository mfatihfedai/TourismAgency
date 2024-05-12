package view;

import business.UserManager;
import core.Helper;
import dao.UserDao;
import entity.User;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

public class AdminView extends Layout{
    private User user;
    private UserManager userManager;
    private final DefaultTableModel tmdl_user = new DefaultTableModel();
    private JPanel container;
    private JComboBox<User.Role> cmb_user_filter;
    private JButton btn_clear;
    private JButton btn_search;
    private JButton btn_user_add;
    private JPopupMenu user_menu;
    private JTable tbl_admin;
    private JScrollPane scrl_admin;
    private JButton btn_exit;
    private Object[] col_user_list;


    public AdminView(User user){
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(600, 400);
        this.user = user;

        loadComponent();

        loadUserTable(null);
        loadUserFilter();
        loadUserComponent();

    }
    private void loadComponent(){
        btn_exit.addActionListener(e -> {
            dispose();
            LoginView loginView = new LoginView();
        });
    }
    private void loadUserTable(ArrayList<Object[]> userList){
        col_user_list = new Object[]{"User ID", "User Name", "Role"};
        if (userList == null){
            userList = this.userManager.getForTable(col_user_list.length, this.userManager.findAll());
        }
        createTable(this.tmdl_user, this.tbl_admin, col_user_list, userList);
    }

    private void loadUserFilter(){
        this.cmb_user_filter.setModel(new DefaultComboBoxModel<>(User.Role.values()));
        this.cmb_user_filter.setSelectedItem(null);
    }

    private void loadUserComponent(){
        tableRowSelect(tbl_admin);
        this.user_menu =new JPopupMenu();
        this.user_menu.add("Update").addActionListener(e -> {
            int selectUserId = this.getTableSelectedRow(tbl_admin, 0);
            User updateUser = this.userManager.getById(selectUserId);
            UserAddUpdate userAddUpdate = new UserAddUpdate(updateUser);
            userAddUpdate.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });
        this.user_menu.add("Delete").addActionListener(e -> {
            if (Helper.confirm("sure")){
                int selectedRow = this.getTableSelectedRow(tbl_admin, 0);
                if (this.userManager.delete(selectedRow)){
                    Helper.showMsg("done");
                    loadUserTable(null);
                } else {
                    Helper.showMsg("error");
                }
            }
        });
        this.tbl_admin.setComponentPopupMenu(user_menu);

        btn_user_add.addActionListener(e -> {
            UserAddUpdate userAddUpdate = new UserAddUpdate(new User());
            userAddUpdate.addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosed(WindowEvent e) {
                    loadUserTable(null);
                }
            });
        });

        btn_search.addActionListener(e -> {

            ArrayList<User> userListBySearch =this.userManager.searchForTable((User.Role) cmb_user_filter.getSelectedItem());

            ArrayList<Object[]> userRowListBySearch = this.userManager.getForTable(this.col_user_list.length, userListBySearch);
            loadUserTable(userRowListBySearch);
        });

        btn_clear.addActionListener(e -> {
            this.cmb_user_filter.setSelectedItem(null);
            loadUserTable(null);
        });
    }
}
