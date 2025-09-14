import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {
    public MainFrame() {
        setTitle("Daffodil International University Transport Management System");
        setSize(800, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());


        JPanel header = new JPanel(new BorderLayout());
        header.setBackground(new Color(12, 80, 153));
        JLabel title = new JLabel("<html><center>Daffodil International University<br/>Transport Management System</center></html>");
        title.setForeground(Color.WHITE);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        title.setHorizontalAlignment(SwingConstants.CENTER);
        header.add(title, BorderLayout.CENTER);
        header.setPreferredSize(new Dimension(0, 100));
        add(header, BorderLayout.NORTH);


        JPanel center = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(12, 12, 12, 12);

        JButton adminBtn = new JButton("Admin Login");
        JButton studentBtn = new JButton("Student Login/Register");
        JButton exitBtn = new JButton("Exit");

        adminBtn.setPreferredSize(new Dimension(240, 50));
        studentBtn.setPreferredSize(new Dimension(240, 50));
        exitBtn.setPreferredSize(new Dimension(120, 40));

        gbc.gridx = 0; gbc.gridy = 0;
        center.add(adminBtn, gbc);
        gbc.gridy = 1;
        center.add(studentBtn, gbc);
        gbc.gridy = 2;
        center.add(exitBtn, gbc);

        add(center, BorderLayout.CENTER);


        adminBtn.addActionListener(e -> {
            AdminLoginDialog dlg = new AdminLoginDialog(this);
            dlg.setVisible(true);
        });

        studentBtn.addActionListener(e -> {
            StudentLoginDialog dlg = new StudentLoginDialog(this);
            dlg.setVisible(true);
        });

        exitBtn.addActionListener(e -> dispose());
    }
}
