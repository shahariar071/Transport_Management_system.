import javax.swing.*;
import java.awt.*;

public class AddBusDialog extends JDialog {
    public AddBusDialog(JFrame parent) {
        super(parent, "Add Regular Bus Schedule", true);
        setSize(420, 300);
        setLocationRelativeTo(parent);
        setLayout(new GridLayout(5,2,8,8));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setResizable(false);

        add(new JLabel("Bus Number:"));
        JTextField busNo = new JTextField();
        add(busNo);
        add(new JLabel("From:"));
        JTextField from = new JTextField();
        add(from);
        add(new JLabel("To:"));
        JTextField to = new JTextField();
        add(to);
        add(new JLabel("Time (e.g. 08:00 AM):"));
        JTextField time = new JTextField();
        add(time);

        JButton addBtn = new JButton("Add Bus");
        JButton cancel = new JButton("Cancel");
        add(addBtn); add(cancel);

        addBtn.addActionListener(e -> {
            String bno = busNo.getText().trim();
            if (bno.isEmpty() || from.getText().trim().isEmpty() || to.getText().trim().isEmpty() || time.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (DataStore.findBusByNumber(bno) != null) {
                JOptionPane.showMessageDialog(this, "Bus number already exists.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataStore.buses.add(new DataStore.Bus(bno, from.getText().trim(), to.getText().trim(), time.getText().trim()));
            JOptionPane.showMessageDialog(this, "Bus added.");
            dispose();
        });

        cancel.addActionListener(e -> dispose());
    }
}
