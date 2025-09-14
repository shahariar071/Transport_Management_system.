import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminDashboardFrame extends JFrame {
    DefaultTableModel busModel;
    JTable busTable;

    public AdminDashboardFrame() {
        setTitle("Admin Dashboard - Daffodil TMS");
        setSize(1000, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel top = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton addBusBtn = new JButton("Add Regular Bus Schedule");
        JButton assignDriverBtn = new JButton("Assign Driver");
        JButton viewFeesBtn = new JButton("View Fee Payments");
        JButton viewRequestsBtn = new JButton("View Requests/Complaints");
        JButton logoutBtn = new JButton("Logout");
        top.add(addBusBtn); top.add(assignDriverBtn); top.add(viewFeesBtn); top.add(viewRequestsBtn); top.add(logoutBtn);
        add(top, BorderLayout.NORTH);

        busModel = new DefaultTableModel(new Object[]{"Bus No","From","To","Time","Driver"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        busTable = new JTable(busModel);
        busTable.setRowHeight(28);
        // make driver column wider
        busTable.getColumnModel().getColumn(4).setPreferredWidth(240);

        refreshBusTable();
        add(new JScrollPane(busTable), BorderLayout.CENTER);

        addBusBtn.addActionListener(e -> { new AddBusDialog(this).setVisible(true); refreshBusTable(); });
        assignDriverBtn.addActionListener(e -> { new AssignDriverDialog(this).setVisible(true); refreshBusTable(); });
        viewFeesBtn.addActionListener(e -> { new ViewFeesDialog(this).setVisible(true); });
        viewRequestsBtn.addActionListener(e -> { new ViewRequestsDialog(this).setVisible(true); });
        logoutBtn.addActionListener(e -> dispose());
    }

    public void refreshBusTable() {
        busModel.setRowCount(0);
        for (DataStore.Bus b : DataStore.buses) {
            busModel.addRow(new Object[]{b.busNumber, b.from, b.to, b.time, b.assignedDriverName});
        }
    }
}
