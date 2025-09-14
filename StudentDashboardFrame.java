import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class StudentDashboardFrame extends JFrame {
    private DataStore.StudentRecord student;

    public StudentDashboardFrame(DataStore.StudentRecord s) {
        this.student = s;
        setTitle("Student Dashboard - " + s.name + " (" + s.id + ")");
        setSize(920, 600);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton showSchedule = new JButton("Show Bus Schedule");
        JButton payFee = new JButton("Pay Transport Fee");
        JButton seatBooking = new JButton("Seat Booking");
        JButton requestBtn = new JButton("Request / Complaint");
        JButton logout = new JButton("Logout");
        top.add(showSchedule); top.add(payFee); top.add(seatBooking); top.add(requestBtn); top.add(logout);
        add(top, BorderLayout.NORTH);

        JTextArea area = new JTextArea();
        area.setEditable(false);
        add(new JScrollPane(area), BorderLayout.CENTER);

        showSchedule.addActionListener(e -> showScheduleDialog());
        payFee.addActionListener(e -> payFeeDialog());
        seatBooking.addActionListener(e -> new SeatBookingDialog(this, student).setVisible(true));
        requestBtn.addActionListener(e -> requestDialog());
        logout.addActionListener(e -> dispose());
    }

    void showScheduleDialog() {
        JDialog dlg = new JDialog(this, "Available Bus Schedule", true);
        dlg.setSize(780, 420);
        dlg.setLocationRelativeTo(this);
        DefaultTableModel model = new DefaultTableModel(new Object[]{"Bus No","From","To","Time","Driver"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        for (DataStore.Bus b : DataStore.buses) model.addRow(new Object[]{b.busNumber, b.from, b.to, b.time, b.assignedDriverName});
        JTable table = new JTable(model);
        table.getColumnModel().getColumn(4).setPreferredWidth(220);
        dlg.add(new JScrollPane(table), BorderLayout.CENTER);
        JButton close = new JButton("Close");
        close.addActionListener(e -> dlg.dispose());
        dlg.add(close, BorderLayout.SOUTH);
        dlg.setVisible(true);
    }

    void payFeeDialog() {
        JPanel p = new JPanel(new GridLayout(3,2,8,8));
        p.add(new JLabel("Student ID:")); p.add(new JLabel(student.id));
        p.add(new JLabel("Student Name:")); p.add(new JLabel(student.name));
        p.add(new JLabel("Months to pay (1-4):"));
        SpinnerNumberModel model = new SpinnerNumberModel(4,1,4,1);
        JSpinner monthsSpinner = new JSpinner(model);
        p.add(monthsSpinner);

        int option = JOptionPane.showConfirmDialog(this, p, "Pay Transport Fee (3500 per semester)", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (option == JOptionPane.OK_OPTION) {
            int months = (int) monthsSpinner.getValue();
            int amount = Math.round(3500f * months / 4f);
            DataStore.feePayments.add(new DataStore.FeePayment(student.id, student.name, months, amount));
            JOptionPane.showMessageDialog(this, "Payment recorded: Tk " + amount);
        }
    }

    void requestDialog() {
        JTextArea txt = new JTextArea(6, 30);
        int res = JOptionPane.showConfirmDialog(this, new JScrollPane(txt), "Write Request/Complaint", JOptionPane.OK_CANCEL_OPTION);
        if (res == JOptionPane.OK_OPTION) {
            String text = txt.getText().trim();
            if (text.isEmpty()) { JOptionPane.showMessageDialog(this, "Please write something.", "Error", JOptionPane.ERROR_MESSAGE); return; }
            DataStore.requests.add(new DataStore.RequestComplaint(student.id, student.name, text));
            JOptionPane.showMessageDialog(this, "Request/Complaint submitted.");
        }
    }
}
