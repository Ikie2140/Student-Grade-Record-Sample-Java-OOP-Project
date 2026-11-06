package studentgraderecordoopfinalproject;

import OopFinalProj.Student;
import OopFinalProj.StudentRecord;
import OopFinalProj.SubjectGrade;


public class StudentPerformanceFrame extends javax.swing.JFrame {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);
    private static final java.awt.Color GREEN = new java.awt.Color(0, 120, 0);
    private static final java.awt.Font LABEL_FONT = new java.awt.Font("Times New Roman", 1, 14);
    private static final java.awt.Font VALUE_FONT = new java.awt.Font("Times New Roman", 0, 14);
    /** Placeholder the Student tab stores for fields it does not collect. */
    private static final String UNSPECIFIED = "UNSPECIFIED";
    private static final String NO_VALUE = "N/A";

    private static final String CARD_GRADES = "grades";
    private static final String CARD_EMPTY = "empty";

    private final StudentRecord record;

    public StudentPerformanceFrame(StudentRecord record) {
        this.record = record;
        initComponents();
        loadStudentInformation();
        loadAcademicPerformance();
        pack();
        setMinimumSize(getSize());
    }

    private void initComponents() {

        pnlHeader = new javax.swing.JPanel();
        lbTitle = new javax.swing.JLabel();
        pnlBody = new javax.swing.JPanel();
        pnlInfo = new javax.swing.JPanel();
        pnlPerformance = new javax.swing.JPanel();
        pnlGradesCard = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblPerformance = new javax.swing.JTable();
        lbNoGrades = new javax.swing.JLabel();
        pnlSummary = new javax.swing.JPanel();
        lbNote = new javax.swing.JLabel();
        pnlButtons = new javax.swing.JPanel();
        butClose = new javax.swing.JButton();

        setTitle("Student Academic Performance - " + record.getStudent().getStudentId());
        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        // header (same red banner as the main window)
        pnlHeader.setBackground(RED);
        pnlHeader.setBorder(javax.swing.BorderFactory.createEmptyBorder(14, 20, 14, 20));
        lbTitle.setFont(new java.awt.Font("Times New Roman", 1, 24));
        lbTitle.setForeground(java.awt.Color.WHITE);
        lbTitle.setText("STUDENT ACADEMIC PERFORMANCE");
        pnlHeader.add(lbTitle);

        pnlBody.setLayout(new java.awt.BorderLayout(0, 10));
        pnlBody.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // student information
        pnlInfo.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(RED, 2), "Student Information",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, LABEL_FONT, RED));
        pnlInfo.setLayout(new java.awt.GridBagLayout());

        // academic performance: table card + "no grades" card, summary strip underneath
        pnlPerformance.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(RED, 2), "Academic Performance",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, LABEL_FONT, RED));
        pnlPerformance.setLayout(new java.awt.BorderLayout(0, 8));

        tblPerformance.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Subject", "Units", "Transmuted Grade", "Status"
            }
        ) {
            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return false;
            }
        });
        tblPerformance.setRowHeight(26);
        tblPerformance.setFont(VALUE_FONT);
        tblPerformance.getTableHeader().setFont(LABEL_FONT);
        tblPerformance.getTableHeader().setReorderingAllowed(false);
        tblPerformance.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        tblPerformance.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_ALL_COLUMNS);
        int[] columnWidths = { 340, 60, 130, 90 };
        for (int i = 0; i < columnWidths.length; i++) {
            tblPerformance.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
        javax.swing.table.DefaultTableCellRenderer center = new javax.swing.table.DefaultTableCellRenderer();
        center.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        tblPerformance.getColumnModel().getColumn(1).setCellRenderer(center);
        tblPerformance.getColumnModel().getColumn(2).setCellRenderer(center);
        tblPerformance.getColumnModel().getColumn(3).setCellRenderer(new StatusRenderer());
        tblPerformance.setPreferredScrollableViewportSize(new java.awt.Dimension(640, 5 * 26));
        jScrollPane1.setViewportView(tblPerformance);

        pnlGradesCard.setLayout(new java.awt.BorderLayout());
        pnlGradesCard.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        lbNoGrades.setFont(new java.awt.Font("Times New Roman", 2, 16));
        lbNoGrades.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbNoGrades.setText("No grades have been recorded for this student yet.");
        lbNoGrades.setPreferredSize(new java.awt.Dimension(640, 5 * 26 + 26));

        cardLayout = new java.awt.CardLayout();
        pnlCards = new javax.swing.JPanel(cardLayout);
        pnlCards.add(pnlGradesCard, CARD_GRADES);
        pnlCards.add(lbNoGrades, CARD_EMPTY);

        pnlSummary.setLayout(new java.awt.GridBagLayout());

        lbNote.setFont(new java.awt.Font("Times New Roman", 2, 12));
        lbNote.setText("Grades: 1.00 (highest) to 3.00 are passing; 4.00 = Conditional Passed; 5.00 = Failed. "
                + "Status uses the " + (int) SubjectGrade.PASSING_GRADE + " passing mark.");
        lbNote.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, 10, 4, 10));

        javax.swing.JPanel pnlBottom = new javax.swing.JPanel(new java.awt.BorderLayout());
        pnlBottom.add(pnlSummary, java.awt.BorderLayout.CENTER);
        pnlBottom.add(lbNote, java.awt.BorderLayout.SOUTH);

        pnlPerformance.add(pnlCards, java.awt.BorderLayout.CENTER);
        pnlPerformance.add(pnlBottom, java.awt.BorderLayout.SOUTH);

        // button bar
        pnlButtons.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        butClose.setBackground(GRAY);
        butClose.setText("Close");
        butClose.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butClose.setPreferredSize(new java.awt.Dimension(110, 36));
        butClose.addActionListener(this::butCloseActionPerformed);
        pnlButtons.add(butClose);

        pnlBody.add(pnlInfo, java.awt.BorderLayout.NORTH);
        pnlBody.add(pnlPerformance, java.awt.BorderLayout.CENTER);
        pnlBody.add(pnlButtons, java.awt.BorderLayout.SOUTH);

        getContentPane().setLayout(new java.awt.BorderLayout());
        getContentPane().add(pnlHeader, java.awt.BorderLayout.NORTH);
        getContentPane().add(pnlBody, java.awt.BorderLayout.CENTER);
    }

    // ---------- Student information ----------

    private void loadStudentInformation() {
        Student student = record.getStudent();
        // left column
        addInfo(0, 0, "Student ID", student.getStudentId());
        addInfo(1, 0, "Student Name", student.getFullName());
        addInfo(2, 0, "Program", display(student.getProgram()));
        addInfo(3, 0, "Year / Section", yearAndSection(student));
        addInfo(4, 0, "Date Of Birth", display(student.getDateOfBirth()));
        addInfo(5, 0, "Gender", display(student.getGender()));
        // right column
        addInfo(0, 1, "Email", display(student.getEmail()));
        addInfo(1, 1, "Phone Number", display(student.getPhoneNumber()));
        addInfo(2, 1, "Father's Name", display(student.getFatherName()));
        addInfo(3, 1, "Mother's Name", display(student.getMotherName()));
        addInfo(4, 1, "Address Line 1", display(student.getAddressLine1()));
        addInfo(5, 1, "Address Line 2", display(student.getAddressLine2()));
    }

    /** Places one "label : value" pair. Column 0 is the left pair, column 1 the right pair. */
    private void addInfo(int row, int column, String label, String value) {
        javax.swing.JLabel lbLabel = new javax.swing.JLabel(label);
        lbLabel.setFont(LABEL_FONT);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(4, 20, 4, 10);
        pnlInfo.add(lbLabel, gbc);

        javax.swing.JLabel lbValue = new javax.swing.JLabel(value);
        lbValue.setFont(VALUE_FONT);
        lbValue.setToolTipText(value);
        gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2 + 1;
        gbc.weightx = 1.0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(4, 0, 4, 20);
        pnlInfo.add(lbValue, gbc);
    }

    private static String display(String value) {
        return isUnspecified(value) ? NO_VALUE : value.trim();
    }

    private static boolean isUnspecified(String value) {
        return value == null || value.trim().isEmpty() || UNSPECIFIED.equalsIgnoreCase(value.trim());
    }

    private static String yearAndSection(Student student) {
        if (isUnspecified(student.getYearLevel()) || isUnspecified(student.getSection())) {
            return NO_VALUE;
        }
        return student.getYearLevel().trim() + " - " + student.getSection().trim();
    }

    // ---------- Academic performance ----------

    private void loadAcademicPerformance() {
        javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) tblPerformance.getModel();
        model.setRowCount(0);

        boolean hasGrades = !record.getSubjectGrades().isEmpty();
        for (SubjectGrade grade : record.getSubjectGrades()) {
            model.addRow(new Object[] {
                grade.getSubjectName() + " (" + grade.getSubjectCode() + ")",
                String.format("%.1f", grade.getUnits()),
                String.format("%.2f", grade.getTransmutedGrade()),
                grade.getRemarks()
            });
        }
        cardLayout.show(pnlCards, hasGrades ? CARD_GRADES : CARD_EMPTY);

        String gwa = hasGrades ? String.format("%.2f", record.computeGWA()) : NO_VALUE;
        String transmutedGwa = hasGrades ? String.format("%.2f", record.computeTransmutedGWA()) : NO_VALUE;

        pnlSummary.removeAll();
        addSummary(0, 0, "GWA (percentage)", gwa, null);
        addSummary(0, 1, "GWA (transmuted)", transmutedGwa, null);
        addSummary(0, 2, "Academic Status", record.getAcademicStatus(), statusColor(record.getAcademicStatus()));
        addSummary(1, 0, "Total Units", String.format("%.1f", record.getTotalUnits()), null);
        addSummary(1, 1, "Passed Units", String.format("%.1f", record.getPassedUnits()), null);
        addSummary(1, 2, "Failed Units", String.format("%.1f", record.getFailedUnits()), null);
        pnlSummary.revalidate();
        pnlSummary.repaint();
    }

    private void addSummary(int row, int column, String label, String value, java.awt.Color valueColor) {
        javax.swing.JLabel lbLabel = new javax.swing.JLabel(label + ":");
        lbLabel.setFont(LABEL_FONT);
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(4, 10, 4, 6);
        pnlSummary.add(lbLabel, gbc);

        javax.swing.JLabel lbValue = new javax.swing.JLabel(value);
        lbValue.setFont(LABEL_FONT);
        if (valueColor != null) {
            lbValue.setForeground(valueColor);
        }
        gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2 + 1;
        gbc.weightx = 1.0;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(4, 0, 4, 20);
        pnlSummary.add(lbValue, gbc);
    }

    /** Green for a clean record, red when something failed, default colour when nothing is encoded. */
    private static java.awt.Color statusColor(String status) {
        if (status.contains("FAILED")) {
            return RED;
        }
        if (status.contains("PASSED")) {
            return GREEN;
        }
        return null;
    }

    /** Centers the status column and colours PASSED / FAILED. */
    private static class StatusRenderer extends javax.swing.table.DefaultTableCellRenderer {
        StatusRenderer() {
            setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        }

        @Override
        public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            java.awt.Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            if (!isSelected) {
                java.awt.Color color = statusColor(String.valueOf(value));
                c.setForeground(color != null ? color : table.getForeground());
            }
            return c;
        }
    }

    private void butCloseActionPerformed(java.awt.event.ActionEvent evt) {
        dispose();
    }

    // Variables declaration
    private javax.swing.JButton butClose;
    private java.awt.CardLayout cardLayout;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lbNoGrades;
    private javax.swing.JLabel lbNote;
    private javax.swing.JLabel lbTitle;
    private javax.swing.JPanel pnlBody;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlCards;
    private javax.swing.JPanel pnlGradesCard;
    private javax.swing.JPanel pnlHeader;
    private javax.swing.JPanel pnlInfo;
    private javax.swing.JPanel pnlPerformance;
    private javax.swing.JPanel pnlSummary;
    private javax.swing.JTable tblPerformance;
    // End of variables declaration
}
