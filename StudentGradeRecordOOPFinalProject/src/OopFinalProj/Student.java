package OopFinalProj;

public class Student {
    private String studentId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String program;
    private String yearLevel;
    private String section;
    private String displayName;
    private String dateOfBirth;
    private String gender;
    private String email;
    private String phoneNumber;
    private String fatherName;
    private String motherName;
    private String addressLine1;
    private String addressLine2;

    public Student(String studentId, String lastName, String firstName, String middleName,
                   String program, String yearLevel, String section) {
        setStudentId(studentId);
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setProgram(program);
        setYearLevel(yearLevel);
        setSection(section);
        displayName = "";
        dateOfBirth = "";
        gender = "";
        email = "";
        phoneNumber = "";
        fatherName = "";
        motherName = "";
        addressLine1 = "";
        addressLine2 = "";
    }

    // ---------- Getters ----------
    public String getStudentId()  { return studentId; }
    public String getLastName()   { return lastName; }
    public String getFirstName()  { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getProgram()    { return program; }
    public String getYearLevel()  { return yearLevel; }
    public String getSection()    { return section; }
    public String getDisplayName() { return displayName; }
    public String getDateOfBirth() { return dateOfBirth; }
    public String getGender() { return gender; }
    public String getEmail() { return email; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getFatherName() { return fatherName; }
    public String getMotherName() { return motherName; }
    public String getAddressLine1() { return addressLine1; }
    public String getAddressLine2() { return addressLine2; }

    // ---------- Setters (with validation) ----------
    public void setStudentId(String studentId) {
        this.studentId = requireText(studentId, "Student ID");
    }

    public void setLastName(String lastName) {
        this.lastName = requireText(lastName, "Last name");
    }

    public void setFirstName(String firstName) {
        this.firstName = requireText(firstName, "First name");
    }

    public void setMiddleName(String middleName) {
        this.middleName = (middleName == null) ? "" : middleName.trim();
    }

    public void setProgram(String program) {
        this.program = requireText(program, "Program");
    }

    public void setYearLevel(String yearLevel) {
        this.yearLevel = requireText(yearLevel, "Year level");
    }

    public void setSection(String section) {
        this.section = requireText(section, "Section");
    }

    public void setDisplayName(String displayName) {
        this.displayName = requireText(displayName, "Student name");
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = optionalText(dateOfBirth);
    }

    public void setGender(String gender) {
        this.gender = optionalText(gender);
    }

    public void setEmail(String email) {
        this.email = optionalText(email);
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = optionalText(phoneNumber);
    }

    public void setFatherName(String fatherName) {
        this.fatherName = optionalText(fatherName);
    }

    public void setMotherName(String motherName) {
        this.motherName = optionalText(motherName);
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = optionalText(addressLine1);
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = optionalText(addressLine2);
    }

    // ---------- Helper methods ----------
    public String getFullName() {
        if (!displayName.isEmpty()) {
            return displayName;
        }
        return lastName + ", " + firstName + (middleName.isEmpty() ? "" : " " + middleName);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
        return value.trim();
    }

    private static String optionalText(String value) {
        return value == null ? "" : value.trim();
    }

    @Override
    public String toString() {
        return studentId + " - " + getFullName() + " (" + program + " " + yearLevel + "-" + section + ")";
    }
}
