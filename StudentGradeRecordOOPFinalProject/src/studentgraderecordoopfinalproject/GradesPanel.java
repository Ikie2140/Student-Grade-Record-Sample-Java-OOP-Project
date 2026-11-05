package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.StudentRecord;
import OopFinalProj.SubjectGrade;

/**
 * "Grades" tab: find a student by ID and enter / update the scores.
 * Layout reference: the "Score" tab of the README screenshots (student ID +
 * Search, then Course/Score rows, then Save / Update / Clear).
 * The reference has five course rows; the same number is used here.
 *
 * This panel is hand-written (it has no .form file), so the Form Editor will
 * not regenerate or overwrite it.
 */
public class GradesPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);
    private static final java.awt.Font LABEL_FONT = new java.awt.Font("Times New Roman", 1, 14);
    private static final int SUBJECT_COUNT = 5;

    private final GradeRecordSystem system;
    private StudentRecord selectedRecord;

    public GradesPanel(GradeRecordSystem system) {
        this.system = system;
        initComponents();
    }

    private void initComponents() {

        pnlForm = new javax.swing.JPanel();
        pnlButtons = new javax.swing.JPanel();
        lbStudentID = new javax.swing.JLabel();
        lbStudentName = new javax.swing.JLabel();
        lbSubject = new javax.swing.JLabel();
        lbScore = new javax.swing.JLabel();
        lbAverage = new javax.swing.JLabel();
        tfStudentID = new javax.swing.JTextField();
        tfStudentName = new javax.swing.JTextField();
        tfAverage = new javax.swing.JTextField();
        butSearch = new javax.swing.JButton();
        butSave = new javax.swing.JButton();
        butUpdate = new javax.swing.JButton();
        butClear = new javax.swing.JButton();
        lbSubjectNo = new javax.swing.JLabel[SUBJECT_COUNT];
        tfSubject = new javax.swing.JTextField[SUBJECT_COUNT];
        tfScore = new javax.swing.JTextField[SUBJECT_COUNT];

        setLayout(new java.awt.BorderLayout(0, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(RED, 2), "Grade Entry",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, LABEL_FONT, RED));
        pnlForm.setLayout(new java.awt.GridBagLayout());

        lbStudentID.setFont(LABEL_FONT);
        lbStudentID.setText("Student's ID");

        lbStudentName.setFont(LABEL_FONT);
        lbStudentName.setText("Student's Name");

        lbSubject.setFont(LABEL_FONT);
        lbSubject.setText("Subject");

        lbScore.setFont(LABEL_FONT);
        lbScore.setText("Score");

        lbAverage.setFont(LABEL_FONT);
        lbAverage.setText("Average");

        tfStudentName.setEditable(false);
        tfStudentName.setBackground(GRAY);

        tfAverage.setEditable(false);
        tfAverage.setBackground(GRAY);
        tfAverage.setColumns(8);
        tfAverage.setMinimumSize(tfAverage.getPreferredSize());

        butSearch.setBackground(GRAY);
        butSearch.setText("Search");
        butSearch.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butSearch.setPreferredSize(new java.awt.Dimension(100, 30));
        butSearch.setMinimumSize(new java.awt.Dimension(100, 30));
        butSearch.addActionListener(this::butSearchActionPerformed);

        // row 0: student ID + Search button
        java.awt.GridBagConstraints gbc = cell(0, 0);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbStudentID, gbc);
        gbc = cell(1, 0);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        pnlForm.add(tfStudentID, gbc);
        gbc = cell(2, 0);
        pnlForm.add(butSearch, gbc);

        // row 1: student name (read-only, filled after a search)
        gbc = cell(0, 1);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbStudentName, gbc);
        gbc = cell(1, 1);
        gbc.gridwidth = 2;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlForm.add(tfStudentName, gbc);

        // row 2: column headers
        gbc = cell(1, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbSubject, gbc);
        gbc = cell(2, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbScore, gbc);

        // rows 3..7: Subject n + score
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            lbSubjectNo[i] = new javax.swing.JLabel();
            lbSubjectNo[i].setFont(LABEL_FONT);
            lbSubjectNo[i].setText("Subject " + (i + 1));
            tfSubject[i] = new javax.swing.JTextField();
            tfScore[i] = new javax.swing.JTextField();
            tfScore[i].setColumns(8);
            tfScore[i].setMinimumSize(tfScore[i].getPreferredSize());

            gbc = cell(0, 3 + i);
            gbc.anchor = java.awt.GridBagConstraints.WEST;
            pnlForm.add(lbSubjectNo[i], gbc);
            gbc = cell(1, 3 + i);
            gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
            pnlForm.add(tfSubject[i], gbc);
            gbc = cell(2, 3 + i);
            pnlForm.add(tfScore[i], gbc);
        }

        // row 8: average (read-only, calculated by the backend)
        gbc = cell(1, 3 + SUBJECT_COUNT);
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        pnlForm.add(lbAverage, gbc);
        gbc = cell(2, 3 + SUBJECT_COUNT);
        pnlForm.add(tfAverage, gbc);

        // filler that keeps the rows packed at the top of the panel
        gbc = cell(0, 4 + SUBJECT_COUNT);
        gbc.weighty = 1.0;
        pnlForm.add(new javax.swing.JPanel(), gbc);

        pnlButtons.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        butSave.setBackground(GRAY);
        butSave.setText("Save");
        butSave.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butSave.setPreferredSize(new java.awt.Dimension(110, 36));
        butSave.addActionListener(this::butSaveActionPerformed);

        butUpdate.setBackground(GRAY);
        butUpdate.setText("Update");
        butUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butUpdate.setPreferredSize(new java.awt.Dimension(110, 36));
        butUpdate.addActionListener(this::butUpdateActionPerformed);

        butClear.setBackground(GRAY);
        butClear.setText("Clear");
        butClear.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butClear.setPreferredSize(new java.awt.Dimension(110, 36));
        butClear.addActionListener(this::butClearActionPerformed);

        pnlButtons.add(butSave);
        pnlButtons.add(butUpdate);
        pnlButtons.add(butClear);

        add(pnlForm, java.awt.BorderLayout.CENTER);
        add(pnlButtons, java.awt.BorderLayout.SOUTH);
    }

    /** Constraints for one cell of the form, with the standard spacing. */
    private java.awt.GridBagConstraints cell(int x, int y) {
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = x;
        gbc.gridy = y;
        gbc.insets = new java.awt.Insets(4, 20, 4, 20);
        return gbc;
    }

    private void butSearchActionPerformed(java.awt.event.ActionEvent evt) {
        selectedRecord = system.findStudentById(tfStudentID.getText());
        if (selectedRecord == null) {
            showError("Student record was not found.");
            return;
        }
        tfStudentName.setText(selectedRecord.getStudent().getFullName());
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            if (i < selectedRecord.getSubjectGrades().size()) {
                SubjectGrade grade = selectedRecord.getSubjectGrades().get(i);
                tfSubject[i].setText(grade.getSubjectCode());
                tfScore[i].setText(String.format("%.2f", grade.computeFinalGrade()));
            } else {
                tfSubject[i].setText("");
                tfScore[i].setText("");
            }
        }
        tfAverage.setText(String.format("%.2f", selectedRecord.computeGWA()));
    }

    private void butSaveActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ensureSelectedRecord();
            for (int i = 0; i < SUBJECT_COUNT; i++) {
                if (!tfSubject[i].getText().trim().isEmpty()) {
                    double score = Double.parseDouble(tfScore[i].getText().trim());
                    system.encodeGrade(tfStudentID.getText(), new SubjectGrade(
                            tfSubject[i].getText(), tfSubject[i].getText(), 1.0, score, score, score));
                }
            }
            butSearchActionPerformed(evt);
            showMessage("Grades saved.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void butUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            ensureSelectedRecord();
            for (int i = 0; i < SUBJECT_COUNT; i++) {
                if (!tfSubject[i].getText().trim().isEmpty()) {
                    double score = Double.parseDouble(tfScore[i].getText().trim());
                    SubjectGrade grade = selectedRecord.findSubject(tfSubject[i].getText());
                    if (grade == null) {
                        system.encodeGrade(tfStudentID.getText(), new SubjectGrade(
                                tfSubject[i].getText(), tfSubject[i].getText(), 1.0, score, score, score));
                    } else {
                        system.updateGrades(tfStudentID.getText(), grade.getSubjectCode(), score, score, score);
                    }
                }
            }
            butSearchActionPerformed(evt);
            showMessage("Grades updated.");
        } catch (RuntimeException ex) {
            showError(ex.getMessage());
        }
    }

    private void ensureSelectedRecord() {
        if (system.findStudentById(tfStudentID.getText()) == null) {
            throw new IllegalArgumentException("Search for an existing student first.");
        }
        selectedRecord = system.findStudentById(tfStudentID.getText());
    }

    private void showMessage(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Grades", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Grades", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void butClearActionPerformed(java.awt.event.ActionEvent evt) {
        tfStudentID.setText("");
        tfStudentName.setText("");
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            tfSubject[i].setText("");
            tfScore[i].setText("");
        }
        tfAverage.setText("");
        tfStudentID.requestFocusInWindow();
    }

    // Variables declaration
    private javax.swing.JButton butClear;
    private javax.swing.JButton butSave;
    private javax.swing.JButton butSearch;
    private javax.swing.JButton butUpdate;
    private javax.swing.JLabel lbAverage;
    private javax.swing.JLabel lbScore;
    private javax.swing.JLabel lbStudentID;
    private javax.swing.JLabel lbStudentName;
    private javax.swing.JLabel lbSubject;
    private javax.swing.JLabel[] lbSubjectNo;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTextField tfAverage;
    private javax.swing.JTextField[] tfScore;
    private javax.swing.JTextField tfStudentID;
    private javax.swing.JTextField tfStudentName;
    private javax.swing.JTextField[] tfSubject;
    // End of variables declaration
}
