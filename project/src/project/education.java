
package project;

public class education {
    int educationID;
    String educationLevel;
    education(int education_id,String education_level){
    educationID = education_id;
    educationLevel = education_level;
    }

    public int getEducationID() {
        return educationID;
    }

    public String getEducationLevel() {
        return educationLevel;
    }

    public void setEducationID(int educationID) {
        this.educationID = educationID;
    }

    public void setEducationLevel(String educationLevel) {
        this.educationLevel = educationLevel;
    }
}
