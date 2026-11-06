package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.StudentRecord;
import OopFinalProj.SubjectGrade;


public class SummaryPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);

    private static final int VIEW_COLUMN = 3;

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
                "Student ID", "Student Name", "Average", "Action"
            }
        ) {
            // only the "Action" column (the View button) is editable, so the button can be clicked
            boolean[] canEdit = new boolean [] {
                false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblSummary.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        tblSummary.getTableHeader().setReorderingAllowed(false);
        tblSummary.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblSummary.setRowHeight(32);
        int[] columnWidths = { 120, 330, 100, 100 };
        for (int i = 0; i < columnWidths.length; i++) {
            tblSummary.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        tblSummary.getColumnModel().getColumn(VIEW_COLUMN).setCellRenderer(new ViewButtonRenderer());
        tblSummary.getColumnModel().getColumn(VIEW_COLUMN).setCellEditor(new ViewButtonEditor());
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
        if (tblSummary.isEditing()) {
            tblSummary.getCellEditor().cancelCellEditing();
        }
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblSummary.getModel();
        model.setRowCount(0);
        for (StudentRecord record : system.getAllRecords()) {
            model.addRow(new Object[] {
                record.getStudent().getStudentId(),
                record.getStudent().getFullName(),
                String.format("%.2f", record.computeGWA()),
                "View"
            });
        }
    }

    /** Opens the academic performance window of the student with the given ID. */
    private void openStudentPerformance(String studentId) {
        StudentRecord record = system.findStudentById(studentId);
        if (record == null) {
            javax.swing.JOptionPane.showMessageDialog(this, "Student record was not found.",
                    "Summary", javax.swing.JOptionPane.ERROR_MESSAGE);
            return;
        }
        StudentPerformanceFrame frame = new StudentPerformanceFrame(record);
        frame.setLocationRelativeTo(javax.swing.SwingUtilities.getWindowAncestor(this));
        frame.setVisible(true);
    }

    /** Styles a JButton like the other buttons of the application. */
    private static void styleViewButton(javax.swing.JButton button) {
        button.setBackground(GRAY);
        button.setText("View");
        button.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
    }

    /** Draws the "View" button inside the table cells. */
    private static class ViewButtonRenderer extends javax.swing.JButton implements javax.swing.table.TableCellRenderer {
        ViewButtonRenderer() {
            styleViewButton(this);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    /** Makes the "View" button clickable: opens the performance window of that row's student. */
    private class ViewButtonEditor extends javax.swing.AbstractCellEditor implements javax.swing.table.TableCellEditor {
        private final javax.swing.JButton button = new javax.swing.JButton();
        private String studentId;

        ViewButtonEditor() {
            styleViewButton(button);
            button.addActionListener(evt -> {
                String id = studentId;
                fireEditingStopped();
                openStudentPerformance(id);
            });
        }

        @Override
        public java.awt.Component getTableCellEditorComponent(javax.swing.JTable table, Object value,
                boolean isSelected, int row, int column) {
            studentId = String.valueOf(table.getValueAt(row, 0));
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            return "View";
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
