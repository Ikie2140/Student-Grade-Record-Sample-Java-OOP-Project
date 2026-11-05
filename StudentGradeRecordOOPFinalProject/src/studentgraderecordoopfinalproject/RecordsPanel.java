package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.StudentRecord;

/**
 * "Records" tab: the table that displays all students, in its own dedicated
 * tab (the reference project shows it beside the form instead).
 * Layout reference: "Search Student" bar on top, table in the middle, button
 * bar at the bottom.
 *
 * This panel is hand-written (it has no .form file), so the Form Editor will
 * not regenerate or overwrite it.
 */
public class RecordsPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);

    private final GradeRecordSystem system;

    public RecordsPanel(GradeRecordSystem system) {
        this.system = system;
        initComponents();
        loadRows(system.getAllRecords());
    }

    private void initComponents() {

        pnlSearch = new javax.swing.JPanel();
        pnlButtons = new javax.swing.JPanel();
        lbSearch = new javax.swing.JLabel();
        tfSearch = new javax.swing.JTextField();
        butSearch = new javax.swing.JButton();
        butRefresh = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblStudents = new javax.swing.JTable();
        butSortByMarks = new javax.swing.JButton();
        butPrint = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout(0, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlSearch.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlSearch.setLayout(new java.awt.GridBagLayout());

        lbSearch.setFont(new java.awt.Font("Times New Roman", 1, 18));
        lbSearch.setText("Search Student");

        butSearch.setBackground(GRAY);
        butSearch.setText("Search");
        butSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butSearch.setPreferredSize(new java.awt.Dimension(100, 30));
        butSearch.setMinimumSize(new java.awt.Dimension(100, 30));
        butSearch.addActionListener(this::butSearchActionPerformed);

        butRefresh.setBackground(GRAY);
        butRefresh.setText("Refresh");
        butRefresh.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butRefresh.setPreferredSize(new java.awt.Dimension(100, 30));
        butRefresh.setMinimumSize(new java.awt.Dimension(100, 30));
        butRefresh.addActionListener(this::butRefreshActionPerformed);

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.insets = new java.awt.Insets(10, 15, 10, 15);
        pnlSearch.add(lbSearch, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 1;
        gbc.weightx = 1.0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(10, 0, 10, 15);
        pnlSearch.add(tfSearch, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 2;
        gbc.insets = new java.awt.Insets(10, 0, 10, 15);
        pnlSearch.add(butSearch, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 3;
        gbc.insets = new java.awt.Insets(10, 0, 10, 15);
        pnlSearch.add(butRefresh, gbc);

        tblStudents.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Student ID", "Student Name", "Average", "Date Of Birth", "Gender", "Email", "Phone Number", "Father's Name", "Mother's Name", "Address Line 1", "Address Line 2"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tblStudents.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tblStudents.getTableHeader().setReorderingAllowed(false);
        tblStudents.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        int[] columnWidths = { 80, 150, 70, 100, 70, 170, 110, 130, 130, 150, 150 };
        for (int i = 0; i < columnWidths.length; i++) {
            tblStudents.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        jScrollPane1.setViewportView(tblStudents);
        jScrollPane1.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));

        pnlButtons.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        butSortByMarks.setBackground(GRAY);
        butSortByMarks.setText("Sort By Marks");
        butSortByMarks.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butSortByMarks.setPreferredSize(new java.awt.Dimension(130, 36));
        butSortByMarks.addActionListener(this::butSortByMarksActionPerformed);

        butPrint.setBackground(GRAY);
        butPrint.setText("Print");
        butPrint.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butPrint.setPreferredSize(new java.awt.Dimension(110, 36));
        butPrint.addActionListener(this::butPrintActionPerformed);

        pnlButtons.add(butSortByMarks);
        pnlButtons.add(butPrint);

        add(pnlSearch, java.awt.BorderLayout.NORTH);
        add(jScrollPane1, java.awt.BorderLayout.CENTER);
        add(pnlButtons, java.awt.BorderLayout.SOUTH);
    }

    private void butSearchActionPerformed(java.awt.event.ActionEvent evt) {
        String query = tfSearch.getText().trim().toLowerCase();
        java.util.ArrayList<StudentRecord> matches = new java.util.ArrayList<>();
        for (StudentRecord record : system.getAllRecords()) {
            if (record.getStudent().getStudentId().toLowerCase().contains(query)
                    || record.getStudent().getFullName().toLowerCase().contains(query)) {
                matches.add(record);
            }
        }
        loadRows(matches);
    }

    private void butRefreshActionPerformed(java.awt.event.ActionEvent evt) {
        loadRows(system.getAllRecords());
    }

    private void butSortByMarksActionPerformed(java.awt.event.ActionEvent evt) {
        java.util.ArrayList<StudentRecord> records = new java.util.ArrayList<>(system.getAllRecords());
        records.sort(java.util.Comparator.comparingDouble((StudentRecord r) -> r.computeGWA()).reversed());
        loadRows(records);
    }

    private void loadRows(java.util.List<StudentRecord> records) {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblStudents.getModel();
        model.setRowCount(0);
        for (StudentRecord record : records) {
            OopFinalProj.Student student = record.getStudent();
            model.addRow(new Object[] {
                student.getStudentId(), student.getFullName(), String.format("%.2f", record.computeGWA()),
                student.getDateOfBirth(), student.getGender(), student.getEmail(), student.getPhoneNumber(),
                student.getFatherName(), student.getMotherName(), student.getAddressLine1(), student.getAddressLine2()
            });
        }
    }

    private void butPrintActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            tblStudents.print();
        } catch (java.awt.print.PrinterException ex) {
            javax.swing.JOptionPane.showMessageDialog(this, "Unable to print: " + ex.getMessage(),
                    "Print", javax.swing.JOptionPane.ERROR_MESSAGE);
        }
    }

    // Variables declaration
    private javax.swing.JButton butPrint;
    private javax.swing.JButton butRefresh;
    private javax.swing.JButton butSearch;
    private javax.swing.JButton butSortByMarks;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbSearch;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlSearch;
    private javax.swing.JTable tblStudents;
    private javax.swing.JTextField tfSearch;
    // End of variables declaration
}
