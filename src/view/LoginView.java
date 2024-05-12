package view;

import business.UserManager;
import core.Helper;
import entity.User;

import javax.swing.*;
import java.util.Arrays;
import java.util.Objects;

public class LoginView extends Layout {
    private JPanel container;
    private JPanel w_top;
    private JPanel w_bottom;
    private JTextField fld_username;
    private JPasswordField fld_pass;
    private JButton btn_login;
    private final UserManager userManager;

    public LoginView(){
        this.userManager = new UserManager();
        this.add(container);
        this.guiInitilaze(500,600);


        btn_login.addActionListener(e -> {
            JTextField[] checkFieldList = {this.fld_username, this.fld_pass};
            if (Helper.isFieldListEmpty(checkFieldList)) {
                Helper.showMsg("fill");
            }
            else {
                User loginUser = this.userManager.findByLogin(fld_username.getText(), fld_pass.getText());
                if (loginUser == null) {
                    Helper.showMsg("notFound");
                } else {
                    if (loginUser.getRole().equals("ADMIN")){
                        AdminView adminView = new AdminView(loginUser);
                        dispose();
                    } else {
                        EmployeeView employeeView = new EmployeeView(loginUser);
                        dispose();
                    }
                }
            }
        });
    }
}
