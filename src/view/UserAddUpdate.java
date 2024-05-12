package view;

import business.UserManager;
import core.Helper;
import dao.UserDao;
import entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserAddUpdate extends Layout {
    private JPanel container;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JComboBox<User.Role> cmb_admin_role;
    private JButton btn_user_save;
    private User user;
    private UserManager userManager;
    private UserDao userDao;

    public UserAddUpdate(User user){
        this.add(container);
        this.user = user;

        this.userManager = new UserManager();
        this.guiInitilaze(300,400);

        this.cmb_admin_role.setModel(new DefaultComboBoxModel<>(User.Role.values()));

        if (this.user.getId() != 0){
            this.fld_username.setText(this.user.getUsername());
            this.fld_pass.setText(this.user.getPassword());
            this.cmb_admin_role.getModel().setSelectedItem(this.user.getRole());
        }

        btn_user_save.addActionListener(e -> {
            if (Helper.isFieldListEmpty(new JTextField[]{this.fld_username, this.fld_pass})){
                Helper.showMsg("fill");
            } else {
                boolean result;
                this.user.setUsername(this.fld_username.getText());
                this.user.setPassword(this.fld_pass.getText());
                this.user.setRole(String.valueOf(cmb_admin_role.getSelectedItem()));

                if (this.user.getId() != 0){
                    result = this.userManager.update(this.user);
                } else {
                    result = this.userManager.save(this.user);
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
