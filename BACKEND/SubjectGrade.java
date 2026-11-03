package OopFinalProj;

public class SubjectGrade {
    public static final double PASSING_GRADE   = 75.0;
    public static final double WEIGHT_PRELIM   = 0.30;
    public static final double WEIGHT_MIDTERM  = 0.30;
    public static final double WEIGHT_FINALS   = 0.40;

    private String subjectCode;
    private String subjectName;
    private double units;
    private double prelimGrade;
    private double midtermGrade;
    private double finalsGrade;

    public SubjectGrade(String subjectCode, String subjectName, double units,
                        double prelimGrade, double midtermGrade, double finalsGrade) {
        setSubjectCode(subjectCode);
        setSubjectName(subjectName);
        setUnits(units);
        setGrades(prelimGrade, midtermGrade, finalsGrade);
    }

    // ---------- Getters ----------
    public String getSubjectCode() { return subjectCode; }
    public String getSubjectName() { return subjectName; }
    public double getUnits()       { return units; }
    public double getPrelimGrade() { return prelimGrade; }
    public double getMidtermGrade(){ return midtermGrade; }
    public double getFinalsGrade() { return finalsGrade; }

    // ---------- Setters (with validation) ----------
    public void setSubjectCode(String subjectCode) {
        this.subjectCode = requireText(subjectCode, "Subject code");
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = requireText(subjectName, "Subject name");
    }

    public void setUnits(double units) {
        if (units <= 0) {
            throw new IllegalArgumentException("Units must be greater than zero.");
        }
        this.units = units;
    }

    public void setGrades(double prelim, double midterm, double finals) {
        validateGrade(prelim, "Prelim grade");
        validateGrade(midterm, "Midterm grade");
        validateGrade(finals, "Finals grade");
        this.prelimGrade  = prelim;
        this.midtermGrade = midterm;
        this.finalsGrade  = finals;
    }

    // ---------- Computations ----------
    public double computeFinalGrade() {
        return (prelimGrade * WEIGHT_PRELIM)
                + (midtermGrade * WEIGHT_MIDTERM)
                + (finalsGrade * WEIGHT_FINALS);
    }

    public String getRemarks() {
        return (computeFinalGrade() >= PASSING_GRADE) ? "PASSED" : "FAILED";
    }

    public static void validateGrade(double grade, String label) {
        if (grade < 0.0 || grade > 100.0) {
            throw new IllegalArgumentException(label + " must be between 0 and 100.");
        }
    }

    private static String requireText(String value, String fieldName) {
        if (value == null || value.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " must not be empty.");
        }
        return value.trim();
    }

    @Override
    public String toString() {
        return String.format("%-12s %-30s %5.1f  %6.2f  %6.2f  %6.2f  %7.2f  %s",
                subjectCode, subjectName, units,
                prelimGrade, midtermGrade, finalsGrade,
                computeFinalGrade(), getRemarks());
    }
}
