import javax.swing.*;
import java.awt.*;

public class AdminLoginDialog extends JDialog {
    public AdminLoginDialog(JFrame parent) {
        super(parent, "Admin Login", true);
        setSize(380, 220);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Username:"));
        JTextField userField = new JTextField();
        form.add(userField);
        form.add(new JLabel("Password:"));
        JPasswordField passField = new JPasswordField();
        form.add(passField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton login = new JButton("Login");
        JButton cancel = new JButton("Cancel");
        buttons.add(login); buttons.add(cancel);
        add(buttons, BorderLayout.SOUTH);


        login.addActionListener(e -> {
            String user = userField.getText().trim();
            String pass = new String(passField.getPassword()).trim();
            if (user.equals("admin") && pass.equals("1234")) {
                dispose();
                AdminDashboardFrame admin = new AdminDashboardFrame();
                admin.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Invalid credentials. (demo: admin / 1234)", "Login failed", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancel.addActionListener(e -> dispose());


        StudentStore.load();
    }
}
