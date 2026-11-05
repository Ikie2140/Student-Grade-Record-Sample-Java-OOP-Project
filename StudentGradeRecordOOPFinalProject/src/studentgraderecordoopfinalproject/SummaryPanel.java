package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.StudentRecord;
import OopFinalProj.SubjectGrade;

/**
 * "Summary" tab: summary of student grades (the README lists "view summary of
 * student grades" as a system function; the reference's equivalent is its
 * "Marks Sheet" tab).
 *
 * This panel is hand-written (it has no .form file), so the Form Editor will
 * not regenerate or overwrite it.
 */
public class SummaryPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);

    private final GradeRecordSystem system;

    public SummaryPanel(GradeRecordSystem system) {
        this.system = system;
        initComponents();
        loadRows();
    }

    private void initComponents() {

        pnlButtons = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblSummary = new javax.swing.JTable();
        butRefresh = new javax.swing.JButton();
        butPrint = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout(0, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        tblSummary.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Subject 1", "Subject 2", "Subject 3", "Subject 4", "Subject 5", "Average"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSummary.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblSummary.getTableHeader().setReorderingAllowed(false);
        tblSummary.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jScrollPane1.setViewportView(tblSummary);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(RED, 2), "Summary of Student Grades",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP,
                new java.awt.Font("Times New Roman", 1, 14), RED));

        pnlButtons.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        butRefresh.setBackground(GRAY);
        butRefresh.setText("Refresh");
        butRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butRefresh.setPreferredSize(new java.awt.Dimension(110, 36));
        butRefresh.addActionListener(this::butRefreshActionPerformed);

        butPrint.setBackground(GRAY);
        butPrint.setText("Print");
        butPrint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butPrint.setPreferredSize(new java.awt.Dimension(110, 36));
        butPrint.addActionListener(this::butPrintActionPerformed);

        pnlButtons.add(butRefresh);
        pnlButtons.add(butPrint);

        add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(pnlButtons, java.awt.BorderLayout.SOUTH);
    }

    private void butRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadRows();
    }

    private void loadRows() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblSummary.getModel();
        model.setRowCount(0);
        for (StudentRecord record : system.getAllRecords()) {
            Object[] row = new Object[8];
            row[0] = record.getStudent().getStudentId();
            row[1] = record.getStudent().getFullName();
            for (int i = 0; i < 5; i++) {
                row[i + 2] = i < record.getSubjectGrades().size()
                        ? String.format("%.2f", record.getSubjectGrades().get(i).computeFinalGrade()) : "";
            }
            row[7] = String.format("%.2f", record.computeGWA());
            model.addRow(row);
        }
    }

    private void butPrintActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            tblSummary.print();
        } catch (java.awt.print.PrinterException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Unable to print: " + ex.getMessage(),
                    "Print", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration
    private javax.swing.JButton butPrint;
    private javax.swing.JButton butRefresh;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JTable tblSummary;
    // End of variables declaration
}
