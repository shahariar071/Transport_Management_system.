import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewRequestsDialog extends JDialog {
    public ViewRequestsDialog(JFrame parent) {
        super(parent, "View Requests & Complaints", true);
        setSize(760, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID","Name","Request/Complaint","Date"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        for (DataStore.RequestComplaint r : DataStore.requests) {
            model.addRow(new Object[]{r.studentId, r.studentName, r.text, r.date.toString()});
        }
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());
        add(close, BorderLayout.SOUTH);
    }
}
