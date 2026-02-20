
package project;

public class course {
    int courseID;
    String courseCode;
    String courseName;
    
    course(int course_id,String course_code,String course_name){
    courseID = course_id;
    courseCode = course_code;
    courseName = course_name;
    
    }

    public int getCourseID() {
        return courseID;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseID(int courseID) {
        this.courseID = courseID;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }
    
}
