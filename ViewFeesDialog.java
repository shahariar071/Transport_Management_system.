import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class ViewFeesDialog extends JDialog {
    public ViewFeesDialog(JFrame parent) {
        super(parent, "View Fee Payments", true);
        setSize(760, 420);
        setLocationRelativeTo(parent);
        setLayout(new BorderLayout());

        DefaultTableModel model = new DefaultTableModel(new Object[]{"Student ID","Name","Months","Amount","Date"},0) {
            public boolean isCellEditable(int r, int c){ return false; }
        };
        for (DataStore.FeePayment f : DataStore.feePayments) {
            model.addRow(new Object[]{f.studentId, f.studentName, f.months, f.amount, f.date.toString()});
        }
        JTable table = new JTable(model);
        add(new JScrollPane(table), BorderLayout.CENTER);

        JButton close = new JButton("Close");
        close.addActionListener(e -> dispose());
        add(close, BorderLayout.SOUTH);
    }
}
