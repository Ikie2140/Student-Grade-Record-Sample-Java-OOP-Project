package OopFinalProj;

public class Student {
    private String studentId;
    private String lastName;
    private String firstName;
    private String middleName;
    private String program;
    private String yearLevel;
    private String section;

    public Student(String studentId, String lastName, String firstName, String middleName,
                   String program, String yearLevel, String section) {
        setStudentId(studentId);
        setLastName(lastName);
        setFirstName(firstName);
        setMiddleName(middleName);
        setProgram(program);
        setYearLevel(yearLevel);
        setSection(section);
    }

    // ---------- Getters ----------
    public String getStudentId()  { return studentId; }
    public String getLastName()   { return lastName; }
    public String getFirstName()  { return firstName; }
    public String getMiddleName() { return middleName; }
    public String getProgram()    { return program; }
    public String getYearLevel()  { return yearLevel; }
    public String getSection()    { return section; }

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

    // ---------- Helper methods ----------
    public String getFullName() {
        return lastName + ", " + firstName + (middleName.isEmpty() ? "" : " " + middleName);
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return studentId + " - " + getFullName() + " (" + program + " " + yearLevel + "-" + section + ")";
    }
}
