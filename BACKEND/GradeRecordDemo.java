package OopFinalProj;

public class GradeRecordDemo {
    public static void main(String[] args) {

        GradeRecordSystem system = new GradeRecordSystem();

        try {
            // --- Create records ---
            Student student1 = new Student("2026-0001", "Dela Cruz", "Juan", "Santos",
                    "BSIT", "2", "F1");
            StudentRecord record1 = new StudentRecord(student1);
            system.addStudentRecord(record1);

            system.encodeGrade("2026-0001",
                    new SubjectGrade("IT203", "Object Oriented Programming", 3.0, 88.0, 90.0, 92.0));
            system.encodeGrade("2026-0001",
                    new SubjectGrade("IT204", "Data Structures", 3.0, 78.0, 80.0, 76.0));

            Student student2 = new Student("2026-0002", "Reyes", "Maria", "Lopez",
                    "BSIT", "2", "F1");
            StudentRecord record2 = new StudentRecord(student2);
            system.addStudentRecord(record2);
            system.encodeGrade("2026-0002",
                    new SubjectGrade("IT203", "Object Oriented Programming", 3.0, 70.0, 68.0, 72.0));

            // --- Update transaction ---
            system.updateGrades("2026-0002", "IT203", 74.0, 76.0, 80.0);

            // --- Retrieve / summarize ---
            System.out.println(system.generateReportCard("2026-0001"));
            System.out.println(system.generateClassSummary());

            // --- Search by criteria ---
            System.out.println("Search result (year level 2, section F1):");
            for (StudentRecord record : system.searchByYearAndSection("2", "F1")) {
                System.out.println("  " + record.getStudent());
            }

        } catch (DuplicateStudentException | StudentNotFoundException | InvalidGradeException ex) {
            System.out.println("Error: " + ex.getMessage());
        } catch (IllegalArgumentException ex) {
            System.out.println("Invalid input: " + ex.getMessage());
        }
    }
}
