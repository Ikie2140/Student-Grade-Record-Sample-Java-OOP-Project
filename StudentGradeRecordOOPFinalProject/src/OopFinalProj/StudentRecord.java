package OopFinalProj;

import java.util.ArrayList;
public class StudentRecord {

    private final Student student;
    private final ArrayList<SubjectGrade> subjectGrades;

    public StudentRecord(Student student) {
        if (student == null) {
            throw new IllegalArgumentException("Student must not be null.");
        }
        this.student = student;
        this.subjectGrades = new ArrayList<SubjectGrade>();
    }

    // ---------- Getters ----------
    public Student getStudent() { return student; }

    public ArrayList<SubjectGrade> getSubjectGrades() {
        return subjectGrades;
    }

    // ---------- Record transactions ----------
    public boolean addSubjectGrade(SubjectGrade grade) {
        if (grade == null) {
            throw new IllegalArgumentException("Subject grade must not be null.");
        }
        if (findSubject(grade.getSubjectCode()) != null) {
            return false; // subject code already encoded for this student
        }
        return subjectGrades.add(grade);
    }

    public boolean removeSubjectGrade(String subjectCode) {
        SubjectGrade grade = findSubject(subjectCode);
        if (grade == null) {
            return false;
        }
        return subjectGrades.remove(grade);
    }

    public SubjectGrade findSubject(String subjectCode) {
        if (subjectCode == null) {
            return null;
        }
        for (SubjectGrade grade : subjectGrades) {
            if (grade.getSubjectCode().equalsIgnoreCase(subjectCode.trim())) {
                return grade;
            }
        }
        return null;
    }

    // ---------- Computations ----------
    public double getTotalUnits() {
        double total = 0.0;
        for (SubjectGrade grade : subjectGrades) {
            total += grade.getUnits();
        }
        return total;
    }

    public double getPassedUnits() {
        double total = 0.0;
        for (SubjectGrade grade : subjectGrades) {
            if (grade.getRemarks().equals("PASSED")) {
                total += grade.getUnits();
            }
        }
        return total;
    }

    public double getFailedUnits() {
        return getTotalUnits() - getPassedUnits();
    }

    public double computeGWA() {
        double weightedTotal = 0.0;
        double totalUnits = 0.0;
        for (SubjectGrade grade : subjectGrades) {
            weightedTotal += grade.computeFinalGrade() * grade.getUnits();
            totalUnits += grade.getUnits();
        }
        return (totalUnits == 0.0) ? 0.0 : weightedTotal / totalUnits;
    }

    /** GWA converted to the 1.00 - 5.00 scale (the transmuted equivalent of computeGWA()). */
    public double computeTransmutedGWA() {
        return SubjectGrade.transmute(computeGWA());
    }

    public String getAcademicStatus() {
        if (subjectGrades.isEmpty()) {
            return "NO GRADES ENCODED";
        }
        return (getFailedUnits() > 0.0) ? "WITH FAILED SUBJECTS" : "REGULAR / PASSED";
    }

    public ArrayList<SubjectGrade> getFailedSubjects() {
        ArrayList<SubjectGrade> failed = new ArrayList<SubjectGrade>();
        for (SubjectGrade grade : subjectGrades) {
            if (grade.getRemarks().equals("FAILED")) {
                failed.add(grade);
            }
        }
        return failed;
    }
}
