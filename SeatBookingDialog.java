import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class SeatBookingDialog extends JDialog {
    DataStore.StudentRecord student;
    JComboBox<String> busCombo;
    DefaultTableModel seatModel;
    JTable seatTable;

    public SeatBookingDialog(JFrame parent, DataStore.StudentRecord student) {
        super(parent, "Seat Booking", true);
        this.student = student;
        setSize(720, 520);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        JPanel top = new JPanel(new FlowLayout());
        top.add(new JLabel("Choose Bus:"));
        busCombo = new JComboBox<>();
        for (DataStore.Bus b : DataStore.buses) busCombo.addItem(b.busNumber + " (" + b.from + "->" + b.to + " @" + b.time + ")");
        top.add(busCombo);
        JButton refresh = new JButton("Refresh Seats");
        top.add(refresh);
        add(top, BorderLayout.NORTH);

        seatModel = new DefaultTableModel(new Object[]{"Seat#","Status","Booked By"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        seatTable = new JTable(seatModel);
        add(new JScrollPane(seatTable), BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        JButton bookBtn = new JButton("Book Selected Seat");
        JButton close = new JButton("Close");
        bottom.add(bookBtn); bottom.add(close);
        add(bottom, BorderLayout.SOUTH);

        refresh.addActionListener(e -> refreshSeats());
        busCombo.addActionListener(e -> refreshSeats());
        bookBtn.addActionListener(e -> bookSelectedSeat());
        close.addActionListener(e -> dispose());

        refreshSeats();
    }

    void refreshSeats() {
        seatModel.setRowCount(0);
        int idx = busCombo.getSelectedIndex();
        if (idx < 0 || DataStore.buses.isEmpty()) return;
        DataStore.Bus bus = DataStore.buses.get(idx);
        for (int i = 1; i <= 36; i++) {
            DataStore.SeatBooking booked = null;
            for (DataStore.SeatBooking sb : DataStore.bookings) {
                if (sb.busNumber.equals(bus.busNumber) && sb.seatNumber == i) { booked = sb; break; }
            }
            if (booked == null) seatModel.addRow(new Object[]{i, "Available", ""});
            else seatModel.addRow(new Object[]{i, "Booked", booked.studentName + " (" + booked.studentId + ")"});
        }
    }

    void bookSelectedSeat() {
        int row = seatTable.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a seat first."); return; }
        int seatNo = (Integer) seatModel.getValueAt(row, 0);
        String status = (String) seatModel.getValueAt(row, 1);
        if ("Booked".equals(status)) { JOptionPane.showMessageDialog(this, "Seat already booked."); return; }
        int idx = busCombo.getSelectedIndex();
        DataStore.Bus bus = DataStore.buses.get(idx);
        for (DataStore.SeatBooking sb : DataStore.bookings) if (sb.busNumber.equals(bus.busNumber) && sb.seatNumber == seatNo) { JOptionPane.showMessageDialog(this, "Seat already booked."); refreshSeats(); return; }
        DataStore.bookings.add(new DataStore.SeatBooking(bus.busNumber, seatNo, student.id, student.name));
        JOptionPane.showMessageDialog(this, "Seat " + seatNo + " on bus " + bus.busNumber + " booked for " + student.name);
        refreshSeats();
    }
}
