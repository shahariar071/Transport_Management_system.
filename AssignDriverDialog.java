import javax.swing.*;
import java.awt.*;

public class AssignDriverDialog extends JDialog {
    public AssignDriverDialog(JFrame parent) {
        super(parent, "Assign Driver to Bus", true);
        setSize(520, 360);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new GridLayout(4,2,8,8));
        top.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
        top.add(new JLabel("Choose Bus:"));
        JComboBox<String> busCombo = new JComboBox<>();
        for (DataStore.Bus b : DataStore.buses) busCombo.addItem(b.busNumber + " (" + b.from + "->" + b.to + " @" + b.time + ")");
        top.add(busCombo);
        top.add(new JLabel("Driver Name:"));
        JTextField dname = new JTextField();
        top.add(dname);
        top.add(new JLabel("License Number:"));
        JTextField lic = new JTextField();
        top.add(lic);
        top.add(new JLabel("Phone Number:"));
        JTextField phone = new JTextField();
        top.add(phone);
        add(top, BorderLayout.CENTER);

        JPanel btns = new JPanel();
        JButton assign = new JButton("Assign");
        JButton cancel = new JButton("Cancel");
        btns.add(assign); btns.add(cancel);
        add(btns, BorderLayout.SOUTH);

        assign.addActionListener(e -> {
            if (DataStore.buses.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No buses available. Add a bus first.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            int idx = busCombo.getSelectedIndex();
            if (idx < 0) return;
            DataStore.Bus selected = DataStore.buses.get(idx);
            if (dname.getText().trim().isEmpty() || lic.getText().trim().isEmpty() || phone.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please fill driver details.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            DataStore.Driver driver = new DataStore.Driver(dname.getText().trim(), lic.getText().trim(), phone.getText().trim());
            DataStore.drivers.add(driver);
            selected.assignedDriverName = driver.name;
            JOptionPane.showMessageDialog(this, "Driver assigned to bus " + selected.busNumber);
            dispose();
        });

        cancel.addActionListener(e -> dispose());
    }
}
