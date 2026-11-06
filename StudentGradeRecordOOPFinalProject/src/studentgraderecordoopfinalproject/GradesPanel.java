package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.StudentRecord;
import OopFinalProj.SubjectGrade;

/**
 * "Grades" tab: find a student by ID and enter / update the grades.
 * Layout reference: the "Score" tab of the README screenshots (student ID +
 * Search, then Course/Score rows, then Save / Update / Clear).
 * The reference has five course rows; the same number is used here.
 * Each row has a subject JComboBox (predefined mock subjects) and the three
 * grading components the backend expects: Prelim, Midterm and Finals. The
 * transmuted grade shown beside each row is computed by SubjectGrade.
 *
 */
public class GradesPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);
    private static final java.awt.Font LABEL_FONT = new java.awt.Font("Times New Roman", 1, 14);
    private static final int SUBJECT_COUNT = 5;
    // Units given to every predefined mock subject (same value as the mock data in LoginUI).
    private static final double SUBJECT_UNITS = 3.0;

    // One entry of the subject JComboBox: code + name + units of a predefined subject. 
    private static final class SubjectOption {
        private final String code;
        private final String name;
        private final double units;

        SubjectOption(String code, String name, double units) {
            this.code = code;
            this.name = name;
            this.units = units;
        }

        boolean isPlaceholder() {
            return code == null;
        }

        @Override
        public String toString() {
            return isPlaceholder() ? name : name + " (" + code + ")";
        }
    }

    private static final SubjectOption PLACEHOLDER = new SubjectOption(null, "-- Select subject --", 0.0);

    /// Predefined (mock) subjects offered by the combo boxes
    private static final SubjectOption[] SUBJECTS = {
        new SubjectOption("OOP", "Object-Oriented Programming", SUBJECT_UNITS),
        new SubjectOption("DSA", "Data Structures and Algorithms", SUBJECT_UNITS),
        new SubjectOption("QM", "Quantitative Methods", SUBJECT_UNITS),
        new SubjectOption("SAD", "Systems Analysis and Design", SUBJECT_UNITS),
        new SubjectOption("ITD", "Information Technology Development", SUBJECT_UNITS),
        new SubjectOption("DBMS", "Database Management Systems", SUBJECT_UNITS),
        new SubjectOption("NET", "Networking", SUBJECT_UNITS),
        new SubjectOption("WST", "Web Systems and Technologies", SUBJECT_UNITS),
        new SubjectOption("HCI", "Human-Computer Interaction", SUBJECT_UNITS),
        new SubjectOption("IAS", "Information Assurance and Security", SUBJECT_UNITS)
    };

    // A validated, filled-in row of the form. 
    private static final class GradeEntry {
        private final int row;
        private final SubjectOption subject;
        private final double prelim;
        private final double midterm;
        private final double finals;

        GradeEntry(int row, SubjectOption subject, double prelim, double midterm, double finals) {
            this.row = row;
            this.subject = subject;
            this.prelim = prelim;
            this.midterm = midterm;
            this.finals = finals;
        }

        SubjectGrade toSubjectGrade() {
            return new SubjectGrade(subject.code, subject.name, subject.units, prelim, midterm, finals);
        }

        // True when this row holds exactly the grades already stored in the given SubjectGrade.
        boolean matches(SubjectGrade grade) {
            return grade.getPrelimGrade() == prelim
                    && grade.getMidtermGrade() == midterm
                    && grade.getFinalsGrade() == finals;
        }
    }

    /** Validation problem that remembers which input should receive the focus. */
    private static final class EntryException extends IllegalArgumentException {
        private final java.awt.Component target;

        EntryException(String message, java.awt.Component target) {
            super(message);
            this.target = target;
        }
    }

    private final GradeRecordSystem system;
    private StudentRecord selectedRecord;

    public GradesPanel(GradeRecordSystem system) {
        this.system = system;
        initComponents();
    }

    @SuppressWarnings("unchecked")
    private void initComponents() {

        pnlForm = new javax.swing.JPanel();
        pnlButtons = new javax.swing.JPanel();
        lbStudentID = new javax.swing.JLabel();
        lbStudentName = new javax.swing.JLabel();
        lbSubject = new javax.swing.JLabel();
        lbPrelim = new javax.swing.JLabel();
        lbMidterm = new javax.swing.JLabel();
        lbFinals = new javax.swing.JLabel();
        lbTransmuted = new javax.swing.JLabel();
        lbAverage = new javax.swing.JLabel();
        tfStudentID = new javax.swing.JTextField();
        tfStudentName = new javax.swing.JTextField();
        tfAverage = new javax.swing.JTextField();
        butSearch = new javax.swing.JButton();
        butSave = new javax.swing.JButton();
        butUpdate = new javax.swing.JButton();
        butClear = new javax.swing.JButton();
        lbSubjectNo = new javax.swing.JLabel[SUBJECT_COUNT];
        cbSubject = new javax.swing.JComboBox[SUBJECT_COUNT];
        tfPrelim = new javax.swing.JTextField[SUBJECT_COUNT];
        tfMidterm = new javax.swing.JTextField[SUBJECT_COUNT];
        tfFinals = new javax.swing.JTextField[SUBJECT_COUNT];
        tfTransmuted = new javax.swing.JTextField[SUBJECT_COUNT];

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

        lbPrelim.setFont(LABEL_FONT);
        lbPrelim.setText("Prelim");

        lbMidterm.setFont(LABEL_FONT);
        lbMidterm.setText("Midterm");

        lbFinals.setFont(LABEL_FONT);
        lbFinals.setText("Finals");

        lbTransmuted.setFont(LABEL_FONT);
        lbTransmuted.setText("Transmuted");

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
        gbc.gridwidth = 3;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        pnlForm.add(tfStudentName, gbc);

        // row 2: column headers
        gbc = cell(1, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbSubject, gbc);
        gbc = cell(2, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbPrelim, gbc);
        gbc = cell(3, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbMidterm, gbc);
        gbc = cell(4, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbFinals, gbc);
        gbc = cell(5, 2);
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        pnlForm.add(lbTransmuted, gbc);

        // rows 3..7: Subject n (combo box) + Prelim / Midterm / Finals + transmuted grade
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            final int row = i;
            lbSubjectNo[i] = new javax.swing.JLabel();
            lbSubjectNo[i].setFont(LABEL_FONT);
            lbSubjectNo[i].setText("Subject " + (i + 1));

            cbSubject[i] = new javax.swing.JComboBox<>();
            cbSubject[i].addItem(PLACEHOLDER);
            for (SubjectOption option : SUBJECTS) {
                cbSubject[i].addItem(option);
            }

            tfPrelim[i] = newScoreField();
            tfMidterm[i] = newScoreField();
            tfFinals[i] = newScoreField();
            tfTransmuted[i] = newScoreField();
            tfTransmuted[i].setEditable(false);
            tfTransmuted[i].setBackground(GRAY);

            // a typed-over score makes the old transmuted grade obsolete until it is saved again
            javax.swing.event.DocumentListener staleListener = onChange(() -> tfTransmuted[row].setText(""));
            tfPrelim[i].getDocument().addDocumentListener(staleListener);
            tfMidterm[i].getDocument().addDocumentListener(staleListener);
            tfFinals[i].getDocument().addDocumentListener(staleListener);

            gbc = cell(0, 3 + i);
            gbc.anchor = java.awt.GridBagConstraints.WEST;
            pnlForm.add(lbSubjectNo[i], gbc);
            gbc = cell(1, 3 + i);
            gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
            gbc.weightx = 1.0;
            pnlForm.add(cbSubject[i], gbc);
            gbc = cell(2, 3 + i);
            pnlForm.add(tfPrelim[i], gbc);
            gbc = cell(3, 3 + i);
            pnlForm.add(tfMidterm[i], gbc);
            gbc = cell(4, 3 + i);
            pnlForm.add(tfFinals[i], gbc);
            gbc = cell(5, 3 + i);
            pnlForm.add(tfTransmuted[i], gbc);
        }

        // row 8: average (read-only, calculated by the backend)
        gbc = cell(4, 3 + SUBJECT_COUNT);
        gbc.anchor = java.awt.GridBagConstraints.EAST;
        pnlForm.add(lbAverage, gbc);
        gbc = cell(5, 3 + SUBJECT_COUNT);
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
        gbc.insets = new java.awt.Insets(4, 10, 4, 10);
        return gbc;
    }

    /** Text field sized for a 0 - 100 grade. */
    private javax.swing.JTextField newScoreField() {
        javax.swing.JTextField field = new javax.swing.JTextField();
        field.setColumns(6);
        field.setMinimumSize(field.getPreferredSize());
        return field;
    }

    private static javax.swing.event.DocumentListener onChange(Runnable action) {
        return new javax.swing.event.DocumentListener() {
            @Override
            public void insertUpdate(javax.swing.event.DocumentEvent e) {
                action.run();
            }

            @Override
            public void removeUpdate(javax.swing.event.DocumentEvent e) {
                action.run();
            }

            @Override
            public void changedUpdate(javax.swing.event.DocumentEvent e) {
                action.run();
            }
        };
    }

    private void butSearchActionPerformed(java.awt.event.ActionEvent evt) {
        selectedRecord = system.findStudentById(tfStudentID.getText());
        if (selectedRecord == null) {
            showError("Student record was not found.");
            return;
        }
        loadSelectedRecord();
        if (selectedRecord.getSubjectGrades().size() > SUBJECT_COUNT) {
            showMessage("This student has " + selectedRecord.getSubjectGrades().size()
                    + " subjects; only the first " + SUBJECT_COUNT + " are shown here.\n"
                    + "Every subject is listed in the Summary tab (View).");
        }
    }

    /** Fills the name, the subject rows and the average from the selected student record. */
    private void loadSelectedRecord() {
        tfStudentName.setText(selectedRecord.getStudent().getFullName());
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            if (i < selectedRecord.getSubjectGrades().size()) {
                SubjectGrade grade = selectedRecord.getSubjectGrades().get(i);
                selectSubject(cbSubject[i], grade);
                tfPrelim[i].setText(formatScore(grade.getPrelimGrade()));
                tfMidterm[i].setText(formatScore(grade.getMidtermGrade()));
                tfFinals[i].setText(formatScore(grade.getFinalsGrade()));
                tfTransmuted[i].setText(String.format("%.2f", grade.getTransmutedGrade()));
            } else {
                clearRow(i);
            }
        }
        tfAverage.setText(String.format("%.2f", selectedRecord.computeGWA()));
    }

    /** Selects the combo item that matches the grade's subject code (adds it if it is not predefined). */
    private void selectSubject(javax.swing.JComboBox<SubjectOption> combo, SubjectGrade grade) {
        for (int i = 0; i < combo.getItemCount(); i++) {
            SubjectOption option = combo.getItemAt(i);
            if (!option.isPlaceholder() && option.code.equalsIgnoreCase(grade.getSubjectCode())) {
                combo.setSelectedIndex(i);
                return;
            }
        }
        SubjectOption custom = new SubjectOption(grade.getSubjectCode(), grade.getSubjectName(), grade.getUnits());
        combo.addItem(custom);
        combo.setSelectedItem(custom);
    }

    private void clearRow(int i) {
        cbSubject[i].setSelectedIndex(0);
        tfPrelim[i].setText("");
        tfMidterm[i].setText("");
        tfFinals[i].setText("");
        tfTransmuted[i].setText("");
    }

    /** Shows a grade the way a user would type it (88.0 -> "88", 87.5 -> "87.5"). */
    private static String formatScore(double score) {
        return java.math.BigDecimal.valueOf(score).stripTrailingZeros().toPlainString();
    }

    /**
     * Reads and validates every filled-in row before anything is sent to the
     * backend. Empty rows are skipped. Throws EntryException for the first problem.
     */
    private java.util.ArrayList<GradeEntry> readEntries() {
        java.util.ArrayList<GradeEntry> entries = new java.util.ArrayList<>();
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            SubjectOption subject = (SubjectOption) cbSubject[i].getSelectedItem();
            boolean noSubject = subject == null || subject.isPlaceholder();
            boolean noScores = tfPrelim[i].getText().trim().isEmpty()
                    && tfMidterm[i].getText().trim().isEmpty()
                    && tfFinals[i].getText().trim().isEmpty();
            if (noSubject && noScores) {
                continue; // unused row
            }
            if (noSubject) {
                throw new EntryException("Subject " + (i + 1) + ": select a subject.", cbSubject[i]);
            }
            for (GradeEntry previous : entries) {
                if (previous.subject.code.equalsIgnoreCase(subject.code)) {
                    throw new EntryException(subject.name + " is selected in more than one row.", cbSubject[i]);
                }
            }
            double prelim = readScore(tfPrelim[i], "Subject " + (i + 1) + " Prelim grade");
            double midterm = readScore(tfMidterm[i], "Subject " + (i + 1) + " Midterm grade");
            double finals = readScore(tfFinals[i], "Subject " + (i + 1) + " Finals grade");
            entries.add(new GradeEntry(i, subject, prelim, midterm, finals));
        }
        if (entries.isEmpty()) {
            throw new EntryException("Select a subject and enter its Prelim, Midterm and Finals grades.", cbSubject[0]);
        }
        return entries;
    }

    /** Parses one grade; the 0 - 100 range check is the backend's SubjectGrade.validateGrade. */
    private double readScore(javax.swing.JTextField field, String label) {
        String text = field.getText().trim();
        if (text.isEmpty()) {
            throw new EntryException(label + " is required.", field);
        }
        if (!text.matches("\\d+(\\.\\d+)?")) {
            throw new EntryException(label + " must be a number between 0 and 100.", field);
        }
        double value = Double.parseDouble(text);
        try {
            SubjectGrade.validateGrade(value, label);
        } catch (IllegalArgumentException ex) {
            throw new EntryException(ex.getMessage(), field);
        }
        return value;
    }

    private void butSaveActionPerformed(java.awt.event.ActionEvent evt) {
        int saved = 0;
        try {
            ensureSelectedRecord();
            java.util.ArrayList<GradeEntry> entries = readEntries();
            java.util.ArrayList<GradeEntry> newEntries = new java.util.ArrayList<>();
            for (GradeEntry entry : entries) {
                SubjectGrade existing = selectedRecord.findSubject(entry.subject.code);
                if (existing == null) {
                    newEntries.add(entry);
                } else if (!entry.matches(existing)) {
                    throw new EntryException(entry.subject.name + " already has a grade for this student.\n"
                            + "Use Update to change it.", cbSubject[entry.row]);
                }
            }
            if (newEntries.isEmpty()) {
                showMessage("Nothing new to save: every subject shown already has these grades.\n"
                        + "To change a grade, edit it and press Update.");
                return;
            }
            for (GradeEntry entry : newEntries) {
                if (!system.encodeGrade(selectedRecord.getStudent().getStudentId(), entry.toSubjectGrade())) {
                    throw new EntryException(entry.subject.name + " could not be added (already encoded).",
                            cbSubject[entry.row]);
                }
                saved++;
            }
            loadSelectedRecord();
            showMessage("Grades saved.");
        } catch (EntryException ex) {
            showError(ex);
        } catch (RuntimeException ex) {
            reportBackendFailure(ex, saved);
        }
    }

    private void butUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        int saved = 0;
        try {
            ensureSelectedRecord();
            java.util.ArrayList<GradeEntry> entries = readEntries();
            for (GradeEntry entry : entries) {
                String studentId = selectedRecord.getStudent().getStudentId();
                SubjectGrade grade = selectedRecord.findSubject(entry.subject.code);
                if (grade == null) {
                    system.encodeGrade(studentId, entry.toSubjectGrade());
                } else {
                    system.updateGrades(studentId, grade.getSubjectCode(), entry.prelim, entry.midterm, entry.finals);
                }
                saved++;
            }
            loadSelectedRecord();
            showMessage("Grades updated.");
        } catch (EntryException ex) {
            showError(ex);
        } catch (RuntimeException ex) {
            reportBackendFailure(ex, saved);
        }
    }

    /** A backend rule (e.g. maximum units load) rejected a row; rows before it were already stored. */
    private void reportBackendFailure(RuntimeException ex, int alreadyStored) {
        if (alreadyStored > 0 && selectedRecord != null) {
            loadSelectedRecord();
            showError(alreadyStored + " row(s) were stored before this problem occurred:\n" + ex.getMessage());
        } else {
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

    private void showError(EntryException ex) {
        showError(ex.getMessage());
        ex.target.requestFocusInWindow();
    }

    private void butClearActionPerformed(java.awt.event.ActionEvent evt) {
        tfStudentID.setText("");
        tfStudentName.setText("");
        for (int i = 0; i < SUBJECT_COUNT; i++) {
            clearRow(i);
        }
        tfAverage.setText("");
        tfStudentID.requestFocusInWindow();
    }

    // Variables declaration
    private javax.swing.JButton butClear;
    private javax.swing.JButton butSave;
    private javax.swing.JButton butSearch;
    private javax.swing.JButton butUpdate;
    private javax.swing.JComboBox<SubjectOption>[] cbSubject;
    private javax.swing.JLabel lbAverage;
    private javax.swing.JLabel lbFinals;
    private javax.swing.JLabel lbMidterm;
    private javax.swing.JLabel lbPrelim;
    private javax.swing.JLabel lbStudentID;
    private javax.swing.JLabel lbStudentName;
    private javax.swing.JLabel lbSubject;
    private javax.swing.JLabel[] lbSubjectNo;
    private javax.swing.JLabel lbTransmuted;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTextField tfAverage;
    private javax.swing.JTextField[] tfFinals;
    private javax.swing.JTextField[] tfMidterm;
    private javax.swing.JTextField[] tfPrelim;
    private javax.swing.JTextField tfStudentID;
    private javax.swing.JTextField tfStudentName;
    private javax.swing.JTextField[] tfTransmuted;
    // End of variables declaration
}
