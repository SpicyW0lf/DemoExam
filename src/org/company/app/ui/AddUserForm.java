package org.company.app.ui;

import org.company.app.Application;
import org.company.app.database.entity.UserEntity;
import org.company.app.database.manager.UserEntityManager;
import org.company.app.util.BaseForm;

import javax.swing.*;
import java.sql.SQLException;

public class AddUserForm extends BaseForm
{
    private final UserEntityManager userEntityManager = new UserEntityManager(Application.getInstance().getDatabase());
    private final TestForm mainForm;

    private JPanel mainPanel;
    private JTextField loginField;
    private JPasswordField passwordField;
    private JTextField ageField;
    private JTextField jobField;
    private JButton saveButton;
    private JButton backButton;

    public AddUserForm(TestForm mainForm)
    {
        this.mainForm = mainForm;
        setContentPane(mainPanel);

        initButtons();

        setVisible(true);
    }

    private void initButtons()
    {
        backButton.addActionListener(e -> {
            back();
        });

        saveButton.addActionListener(e -> {
            //тут должны проверки на корректность полей, но мне лень
            UserEntity user = new UserEntity(
                    loginField.getText(),
                    new String(passwordField.getPassword()),
                    Integer.parseInt(ageField.getText()),
                    jobField.getText()
            );

            try {
                userEntityManager.add(user);
                mainForm.update();
                back();

            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        });
    }

    private void back()
    {
        dispose();
        mainForm.setVisible(true);
    }

    @Override
    public int getFormWidth() {
        return 400;
    }

    @Override
    public int getFormHeight() {
        return 225;
    }
}
