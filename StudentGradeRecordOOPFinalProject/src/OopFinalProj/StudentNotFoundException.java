package OopFinalProj;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(String studentId) {
        super("Student record with ID \"" + studentId + "\" was not found.");
    }
}
