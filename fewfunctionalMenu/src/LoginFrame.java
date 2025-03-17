import javax.swing.*;
import java.awt.*;

public class LoginFrame extends JFrame {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginFrame() {
        setTitle("Вход в систему");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        JLabel titleLabel = new JLabel("Добро пожаловать!");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 20, 5);
        mainPanel.add(titleLabel, gbc);

        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(5, 5, 5, 5);
        mainPanel.add(new JLabel("Логин:"), gbc);

        usernameField = new JTextField(20);
        gbc.gridx = 1;
        mainPanel.add(usernameField, gbc);

        gbc.gridx = 0;
        gbc.gridy = 2;
        mainPanel.add(new JLabel("Пароль:"), gbc);

        passwordField = new JPasswordField(20);
        gbc.gridx = 1;
        mainPanel.add(passwordField, gbc);

        JButton loginButton = new JButton("Войти");
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(20, 5, 5, 5);
        mainPanel.add(loginButton, gbc);

        JButton registerButton = new JButton("Зарегистрироваться");
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 5, 20, 5);
        mainPanel.add(registerButton, gbc);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (validateLogin(username, password)) {
                openMainApplication();
            } else {
                JOptionPane.showMessageDialog(this,
                    "Неверный логин или пароль",
                    "Ошибка входа",
                    JOptionPane.ERROR_MESSAGE);
            }
        });

        registerButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,
                "Функция регистрации в разработке",
                "Информация",
                JOptionPane.INFORMATION_MESSAGE);
        });

        add(mainPanel);
    }

    private boolean validateLogin(String username, String password) {
        return true;
    }

    private void openMainApplication() {
        this.dispose();
        new MainApplicationFrame().setVisible(true);
    }
} 