package project;


public class courseAssignment {

    static void add(Project.lecturer.CourseAssignment courseAssignment) {
    }

    //static void add(Project.lecturer.CourseAssignment courseAssignment) {
    //}
   int CourseAssignmentID;
   int courseID;
   int lecturerID;
   courseAssignment(int ca_id,int course_id,int lecturer_id){
   CourseAssignmentID = ca_id;
   courseID = course_id;
   lecturerID = lecturer_id;
   }

    public int getCourseAssignmentID() {
        return CourseAssignmentID;
    }

    public int getCourseID() {
        return courseID;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public void setCourseAssignmentID(int CourseAssignmentID) {
        this.CourseAssignmentID = CourseAssignmentID;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }
}
