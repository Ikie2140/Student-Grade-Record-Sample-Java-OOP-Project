package OopFinalProj;

public class DuplicateStudentException extends RuntimeException {
    public DuplicateStudentException(String studentId) {
        super("Student ID \"" + studentId + "\" already exists in the records.");
    }
}
