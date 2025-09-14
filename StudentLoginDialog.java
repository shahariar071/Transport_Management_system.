import javax.swing.*;
import java.awt.*;

public class StudentLoginDialog extends JDialog {
    public StudentLoginDialog(JFrame parent) {
        super(parent, "Student Login / Register", true);
        setSize(420, 260);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());


        StudentStore.load();

        JPanel form = new JPanel(new GridLayout(3,2,8,8));
        form.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        form.add(new JLabel("Student ID:"));
        JTextField idField = new JTextField();
        form.add(idField);
        form.add(new JLabel("Student Name:"));
        JTextField nameField = new JTextField();
        form.add(nameField);

        add(form, BorderLayout.CENTER);

        JPanel buttons = new JPanel();
        JButton loginBtn = new JButton("Login");
        JButton registerBtn = new JButton("Register");
        JButton cancelBtn = new JButton("Cancel");
        buttons.add(loginBtn); buttons.add(registerBtn); buttons.add(cancelBtn);
        add(buttons, BorderLayout.SOUTH);

        loginBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Provide both ID and Name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StudentStore.verify(id, name)) {
                JOptionPane.showMessageDialog(this, "Login successful. Welcome " + name);
                dispose();
                DataStore.StudentRecord sr = StudentStore.findById(id);
                new StudentDashboardFrame(sr).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "No matching student found. If you are new, press Register.", "Not found", JOptionPane.INFORMATION_MESSAGE);
            }
        });

        registerBtn.addActionListener(e -> {
            String id = idField.getText().trim();
            String name = nameField.getText().trim();
            if (id.isEmpty() || name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Provide both ID and Name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (StudentStore.findById(id) != null) {
                JOptionPane.showMessageDialog(this, "ID already exists. If this is you, login with same name.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            boolean ok = StudentStore.addStudent(id, name);
            if (ok) {
                JOptionPane.showMessageDialog(this, "Registration successful. You are logged in.");
                dispose();
                DataStore.StudentRecord sr = StudentStore.findById(id);
                new StudentDashboardFrame(sr).setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Registration failed (IO).", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> dispose());
    }
}
