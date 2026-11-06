package studentgraderecordoopfinalproject;

import OopFinalProj.GradeRecordSystem;
import OopFinalProj.Student;
import OopFinalProj.StudentRecord;

public class StudentPanel extends javax.swing.JPanel {

    private static final java.awt.Color RED = new java.awt.Color(167, 4, 18);
    private static final java.awt.Color GRAY = new java.awt.Color(204, 204, 204);
    private static final java.awt.Font LABEL_FONT = new java.awt.Font("Times New Roman", 1, 14);

    private final GradeRecordSystem system;

    public StudentPanel(GradeRecordSystem system) {
        this.system = system;
        initComponents();
        tfStudentID.setText(system.generateNextStudentId());
    }

    private void initComponents() {

        pnlForm = new javax.swing.JPanel();
        pnlButtons = new javax.swing.JPanel();
        lbStudentID = new javax.swing.JLabel();
        lbStudentName = new javax.swing.JLabel();
        lbDOB = new javax.swing.JLabel();
        lbGender = new javax.swing.JLabel();
        lbEmail = new javax.swing.JLabel();
        lbPhone = new javax.swing.JLabel();
        lbFatherName = new javax.swing.JLabel();
        lbMotherName = new javax.swing.JLabel();
        lbAddress1 = new javax.swing.JLabel();
        lbAddress2 = new javax.swing.JLabel();
        tfStudentID = new javax.swing.JTextField();
        tfStudentName = new javax.swing.JTextField();
        tfDOB = new javax.swing.JTextField();
        cbGender = new javax.swing.JComboBox<>();
        tfEmail = new javax.swing.JTextField();
        tfPhone = new javax.swing.JTextField();
        tfFatherName = new javax.swing.JTextField();
        tfMotherName = new javax.swing.JTextField();
        tfAddress1 = new javax.swing.JTextField();
        tfAddress2 = new javax.swing.JTextField();
        butAddNew = new javax.swing.JButton();
        butUpdate = new javax.swing.JButton();
        butDelete = new javax.swing.JButton();
        butClear = new javax.swing.JButton();

        setLayout(new java.awt.BorderLayout(0, 10));
        setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));

        pnlForm.setBorder(javax.swing.BorderFactory.createTitledBorder(
                javax.swing.BorderFactory.createLineBorder(RED, 2), "Student Information",
                javax.swing.border.TitledBorder.LEFT, javax.swing.border.TitledBorder.TOP, LABEL_FONT, RED));
        pnlForm.setLayout(new java.awt.GridBagLayout());

        lbStudentID.setFont(LABEL_FONT);
        lbStudentID.setText("Student's ID");

        lbStudentName.setFont(LABEL_FONT);
        lbStudentName.setText("Student's Name");

        lbDOB.setFont(LABEL_FONT);
        lbDOB.setText("Date Of Birth (yyyy-mm-dd)");

        lbGender.setFont(LABEL_FONT);
        lbGender.setText("Gender");

        lbEmail.setFont(LABEL_FONT);
        lbEmail.setText("Email");

        lbPhone.setFont(LABEL_FONT);
        lbPhone.setText("Phone Number");

        lbFatherName.setFont(LABEL_FONT);
        lbFatherName.setText("Father's Name");

        lbMotherName.setFont(LABEL_FONT);
        lbMotherName.setText("Mother's Name");

        lbAddress1.setFont(LABEL_FONT);
        lbAddress1.setText("Address Line 1");

        lbAddress2.setFont(LABEL_FONT);
        lbAddress2.setText("Address Line 2");

        cbGender.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Male", "Female" }));

        // left column of the form
        addRow(0, 0, lbStudentID, tfStudentID);
        addRow(1, 0, lbStudentName, tfStudentName);
        addRow(2, 0, lbDOB, tfDOB);
        addRow(3, 0, lbGender, cbGender);
        addRow(4, 0, lbEmail, tfEmail);
        // right column of the form
        addRow(0, 1, lbPhone, tfPhone);
        addRow(1, 1, lbFatherName, tfFatherName);
        addRow(2, 1, lbMotherName, tfMotherName);
        addRow(3, 1, lbAddress1, tfAddress1);
        addRow(4, 1, lbAddress2, tfAddress2);

        // filler that keeps the rows packed at the top of the panel
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        pnlForm.add(new javax.swing.JPanel(), gbc);

        pnlButtons.setBorder(javax.swing.BorderFactory.createLineBorder(RED, 2));
        pnlButtons.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 15, 10));

        butAddNew.setBackground(GRAY);
        butAddNew.setText("Add New");
        butAddNew.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butAddNew.setPreferredSize(new java.awt.Dimension(110, 36));
        butAddNew.addActionListener(this::butAddNewActionPerformed);

        butUpdate.setBackground(GRAY);
        butUpdate.setText("Update");
        butUpdate.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butUpdate.setPreferredSize(new java.awt.Dimension(110, 36));
        butUpdate.addActionListener(this::butUpdateActionPerformed);

        butDelete.setBackground(GRAY);
        butDelete.setText("Delete");
        butDelete.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butDelete.setPreferredSize(new java.awt.Dimension(110, 36));
        butDelete.addActionListener(this::butDeleteActionPerformed);

        butClear.setBackground(GRAY);
        butClear.setText("Clear");
        butClear.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        butClear.setPreferredSize(new java.awt.Dimension(110, 36));
        butClear.addActionListener(this::butClearActionPerformed);

        pnlButtons.add(butAddNew);
        pnlButtons.add(butUpdate);
        pnlButtons.add(butDelete);
        pnlButtons.add(butClear);

        add(pnlForm, java.awt.BorderLayout.CENTER);
        add(pnlButtons, java.awt.BorderLayout.SOUTH);
    }

    /**
     * Places one "label + input" pair in the form. Column 0 is the left pair,
     * column 1 is the right pair.
     */
    private void addRow(int row, int column, javax.swing.JLabel label, javax.swing.JComponent input) {
        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2;
        gbc.anchor = java.awt.GridBagConstraints.WEST;
        gbc.insets = new java.awt.Insets(10, 20, 10, 10);
        pnlForm.add(label, gbc);

        gbc = new java.awt.GridBagConstraints();
        gbc.gridy = row;
        gbc.gridx = column * 2 + 1;
        gbc.weightx = 1.0;
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gbc.insets = new java.awt.Insets(10, 0, 10, 20);
        pnlForm.add(input, gbc);
    }

    private void butAddNewActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            tfStudentID.setText(system.generateNextStudentId());
            Student student = readStudent();
            system.addStudentRecord(new StudentRecord(student));
            showMessage("Student added.");
            butClearActionPerformed(evt);
        } catch (RuntimeException ex) {
            showError(ex);
        }
    }

    private void butUpdateActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            system.updateStudentInfo(tfStudentID.getText(), readStudent());
            showMessage("Student updated.");
        } catch (RuntimeException ex) {
            showError(ex);
        }
    }

    private void butDeleteActionPerformed(java.awt.event.ActionEvent evt) {
        try {
            system.removeStudentRecord(tfStudentID.getText());
            showMessage("Student deleted.");
            butClearActionPerformed(evt);
        } catch (RuntimeException ex) {
            showError(ex);
        }
    }

    private Student readStudent() {
        String id = tfStudentID.getText().trim();
        Student student = new Student(id, id, tfStudentName.getText().trim(), "",
                "UNSPECIFIED", "UNSPECIFIED", "UNSPECIFIED");
        student.setDisplayName(tfStudentName.getText());
        student.setDateOfBirth(tfDOB.getText());
        student.setGender((String) cbGender.getSelectedItem());
        student.setEmail(tfEmail.getText());
        student.setPhoneNumber(tfPhone.getText());
        student.setFatherName(tfFatherName.getText());
        student.setMotherName(tfMotherName.getText());
        student.setAddressLine1(tfAddress1.getText());
        student.setAddressLine2(tfAddress2.getText());
        return student;
    }

    private void showMessage(String message) {
        javax.swing.JOptionPane.showMessageDialog(this, message, "Student", javax.swing.JOptionPane.INFORMATION_MESSAGE);
    }

    private void showError(RuntimeException ex) {
        javax.swing.JOptionPane.showMessageDialog(this, ex.getMessage(), "Student", javax.swing.JOptionPane.ERROR_MESSAGE);
    }

    private void butClearActionPerformed(java.awt.event.ActionEvent evt) {
        tfStudentID.setText(system.generateNextStudentId());
        tfStudentName.setText("");
        tfDOB.setText("");
        cbGender.setSelectedIndex(0);
        tfEmail.setText("");
        tfPhone.setText("");
        tfFatherName.setText("");
        tfMotherName.setText("");
        tfAddress1.setText("");
        tfAddress2.setText("");
        tfStudentID.requestFocusInWindow();
    }

    // Variables declaration
    private javax.swing.JButton butAddNew;
    private javax.swing.JButton butClear;
    private javax.swing.JButton butDelete;
    private javax.swing.JButton butUpdate;
    private javax.swing.JComboBox<String> cbGender;
    private javax.swing.JLabel lbAddress1;
    private javax.swing.JLabel lbAddress2;
    private javax.swing.JLabel lbDOB;
    private javax.swing.JLabel lbEmail;
    private javax.swing.JLabel lbFatherName;
    private javax.swing.JLabel lbGender;
    private javax.swing.JLabel lbMotherName;
    private javax.swing.JLabel lbPhone;
    private javax.swing.JLabel lbStudentID;
    private javax.swing.JLabel lbStudentName;
    private javax.swing.JPanel pnlButtons;
    private javax.swing.JPanel pnlForm;
    private javax.swing.JTextField tfAddress1;
    private javax.swing.JTextField tfAddress2;
    private javax.swing.JTextField tfDOB;
    private javax.swing.JTextField tfEmail;
    private javax.swing.JTextField tfFatherName;
    private javax.swing.JTextField tfMotherName;
    private javax.swing.JTextField tfPhone;
    private javax.swing.JTextField tfStudentID;
    private javax.swing.JTextField tfStudentName;
    // End of variables declaration
}
