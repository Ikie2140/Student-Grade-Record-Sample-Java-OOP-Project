package OopFinalProj;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GradeRecordSystem {
    private static final Pattern NUMERIC_ID = Pattern.compile("^(\\d{4})-(\\d+)$");
    private final ArrayList<StudentRecord> studentRecords;
    private final int maxUnitsLoad;

    public GradeRecordSystem() {
        this(30);
    }

    public GradeRecordSystem(int maxUnitsLoad) {
        if (maxUnitsLoad <= 0) {
            throw new IllegalArgumentException("Maximum units load must be greater than zero.");
        }
        this.maxUnitsLoad = maxUnitsLoad;
        this.studentRecords = new ArrayList<StudentRecord>();
    }

    // =============== CREATE / ADD ===============
    /**
     * Returns the next available student ID without adding a record.
     * Existing IDs such as 2026-0001 are preserved and continued.
     */
    public String generateNextStudentId() {
        int nextNumber = 1;
        String year = String.valueOf(java.time.Year.now().getValue());
        for (StudentRecord record : studentRecords) {
            Matcher matcher = NUMERIC_ID.matcher(record.getStudent().getStudentId());
            if (matcher.matches()) {
                year = matcher.group(1);
                nextNumber = Math.max(nextNumber, Integer.parseInt(matcher.group(2)) + 1);
            }
        }
        return String.format("%s-%04d", year, nextNumber);
    }

    public boolean addStudentRecord(StudentRecord record) throws DuplicateStudentException {
        if (record == null) {
            throw new IllegalArgumentException("Student record must not be null.");
        }
        if (findStudentById(record.getStudent().getStudentId()) != null) {
            throw new DuplicateStudentException(record.getStudent().getStudentId());
        }
        return studentRecords.add(record);
    }

    public boolean encodeGrade(String studentId, SubjectGrade grade)
            throws StudentNotFoundException, InvalidGradeException {
        StudentRecord record = requireStudent(studentId);
        if (grade == null) {
            throw new InvalidGradeException("Subject grade must not be null.");
        }
        if (record.getTotalUnits() + grade.getUnits() > maxUnitsLoad) {
            throw new InvalidGradeException("Maximum units load of " + maxUnitsLoad + " exceeded.");
        }
        return record.addSubjectGrade(grade);
    }

    // =============== UPDATE ===============
    public boolean updateStudentInfo(String studentId, Student updatedInfo) throws StudentNotFoundException {
        if (updatedInfo == null) {
            throw new IllegalArgumentException("Updated student information must not be null.");
        }
        StudentRecord record = requireStudent(studentId);

        Student current = record.getStudent();
        current.setLastName(updatedInfo.getLastName());
        current.setFirstName(updatedInfo.getFirstName());
        current.setMiddleName(updatedInfo.getMiddleName());
        current.setProgram(updatedInfo.getProgram());
        current.setYearLevel(updatedInfo.getYearLevel());
        current.setSection(updatedInfo.getSection());
        current.setDisplayName(updatedInfo.getDisplayName());
        current.setDateOfBirth(updatedInfo.getDateOfBirth());
        current.setGender(updatedInfo.getGender());
        current.setEmail(updatedInfo.getEmail());
        current.setPhoneNumber(updatedInfo.getPhoneNumber());
        current.setFatherName(updatedInfo.getFatherName());
        current.setMotherName(updatedInfo.getMotherName());
        current.setAddressLine1(updatedInfo.getAddressLine1());
        current.setAddressLine2(updatedInfo.getAddressLine2());
        return true;
    }

    public boolean updateGrades(String studentId, String subjectCode,
                                double prelim, double midterm, double finals)
            throws StudentNotFoundException, InvalidGradeException {
        StudentRecord record = requireStudent(studentId);
        SubjectGrade grade = record.findSubject(subjectCode);
        if (grade == null) {
            throw new InvalidGradeException("Subject \"" + subjectCode + "\" is not encoded for this student.");
        }
        try {
            grade.setGrades(prelim, midterm, finals);
        } catch (IllegalArgumentException ex) {
            throw new InvalidGradeException(ex.getMessage());
        }
        return true;
    }

    // =============== DELETE ===============
    public boolean removeStudentRecord(String studentId) throws StudentNotFoundException {
        StudentRecord record = requireStudent(studentId);
        return studentRecords.remove(record);
    }

    public boolean removeSubjectGrade(String studentId, String subjectCode)
            throws StudentNotFoundException, InvalidGradeException {
        StudentRecord record = requireStudent(studentId);
        if (!record.removeSubjectGrade(subjectCode)) {
            throw new InvalidGradeException("Subject \"" + subjectCode + "\" is not encoded for this student.");
        }
        return true;
    }

    // =============== RETRIEVE / SEARCH ===============
    public StudentRecord findStudentById(String studentId) {
        if (studentId == null) {
            return null;
        }
        for (StudentRecord record : studentRecords) {
            if (record.getStudent().getStudentId().equalsIgnoreCase(studentId.trim())) {
                return record;
            }
        }
        return null;
    }

    public ArrayList<StudentRecord> searchByLastName(String lastName) {
        ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
        if (lastName == null || lastName.trim().isEmpty()) {
            return result;
        }
        String key = lastName.trim().toLowerCase();
        for (StudentRecord record : studentRecords) {
            if (record.getStudent().getLastName().toLowerCase().contains(key)) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<StudentRecord> searchByYearAndSection(String yearLevel, String section) {
        ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
        for (StudentRecord record : studentRecords) {
            Student s = record.getStudent();
            boolean yearMatches = (yearLevel == null)
                    || s.getYearLevel().equalsIgnoreCase(yearLevel.trim());
            boolean sectionMatches = (section == null)
                    || s.getSection().equalsIgnoreCase(section.trim());
            if (yearMatches && sectionMatches) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<StudentRecord> searchByAcademicStatus(String status) {
        ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
        for (StudentRecord record : studentRecords) {
            if (record.getAcademicStatus().equalsIgnoreCase(status.trim())) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<StudentRecord> getAllRecords() {
        return studentRecords;
    }

    public int getRecordCount() {
        return studentRecords.size();
    }

    // =============== REPORTS / SUMMARIES ===============
    public String generateReportCard(String studentId) throws StudentNotFoundException {
        StudentRecord record = requireStudent(studentId);
        Student s = record.getStudent();

        StringBuilder report = new StringBuilder();
        report.append("================ STUDENT GRADE RECORD ================\n");
        report.append("Student ID  : ").append(s.getStudentId()).append("\n");
        report.append("Name        : ").append(s.getFullName()).append("\n");
        report.append("Program     : ").append(s.getProgram()).append("\n");
        report.append("Year/Section: ").append(s.getYearLevel()).append("-").append(s.getSection()).append("\n");
        report.append("------------------------------------------------------\n");
        report.append(String.format("%-12s %-30s %5s %6s %6s %6s %7s %s%n",
                "CODE", "SUBJECT", "UNITS", "PRELIM", "MIDTERM", "FINALS", "FINAL", "REMARKS"));

        if (record.getSubjectGrades().isEmpty()) {
            report.append("No subjects encoded yet.\n");
        } else {
            for (SubjectGrade grade : record.getSubjectGrades()) {
                report.append(grade.toString()).append("\n");
            }
        }

        report.append("------------------------------------------------------\n");
        report.append(String.format("Total Units  : %.1f%n", record.getTotalUnits()));
        report.append(String.format("Passed Units : %.1f%n", record.getPassedUnits()));
        report.append(String.format("Failed Units : %.1f%n", record.getFailedUnits()));
        report.append(String.format("GWA          : %.2f%n", record.computeGWA()));
        report.append("Status       : ").append(record.getAcademicStatus()).append("\n");
        report.append("======================================================\n");
        return report.toString();
    }

    public String generateClassSummary() {
        StringBuilder summary = new StringBuilder();
        summary.append("================ CLASS SUMMARY ================\n");
        summary.append("Total Students : ").append(getRecordCount()).append("\n");

        if (studentRecords.isEmpty()) {
            summary.append("No records available.\n");
            summary.append("===============================================\n");
            return summary.toString();
        }

        summary.append(String.format("%-12s %-30s %10s %12s %s%n",
                "ID", "NAME", "GWA", "TOTAL UNITS", "STATUS"));
        for (StudentRecord record : studentRecords) {
            summary.append(String.format("%-12s %-30s %10.2f %12.1f %s%n",
                    record.getStudent().getStudentId(),
                    record.getStudent().getFullName(),
                    record.computeGWA(),
                    record.getTotalUnits(),
                    record.getAcademicStatus()));
        }

        summary.append("--------------------------------------------------\n");
        summary.append(String.format("Class Average GWA : %.2f%n", computeClassAverageGWA()));
        summary.append("===============================================\n");
        return summary.toString();
    }

    public double computeClassAverageGWA() {
        if (studentRecords.isEmpty()) {
            return 0.0;
        }
        double total = 0.0;
        for (StudentRecord record : studentRecords) {
            total += record.computeGWA();
        }
        return total / studentRecords.size();
    }

    public ArrayList<StudentRecord> getStudentsWithFailedSubjects() {
        ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
        for (StudentRecord record : studentRecords) {
            if (record.getFailedUnits() > 0.0) {
                result.add(record);
            }
        }
        return result;
    }

    public ArrayList<StudentRecord> getStudentsWithGWA(double maximumGwa) {
        ArrayList<StudentRecord> result = new ArrayList<StudentRecord>();
        for (StudentRecord record : studentRecords) {
            if (!record.getSubjectGrades().isEmpty() && record.computeGWA() <= maximumGwa) {
                result.add(record);
            }
        }
        return result;
    }

    // =============== VALIDATION / ERROR HANDLING ===============
    private StudentRecord requireStudent(String studentId) throws StudentNotFoundException {
        StudentRecord record = findStudentById(studentId);
        if (record == null) {
            throw new StudentNotFoundException(studentId == null ? "" : studentId.trim());
        }
        return record;
    }

    public boolean hasRecords() {
        return !studentRecords.isEmpty();
    }
}
