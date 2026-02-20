
package project;

public class lecturer {
    int lecturerID;
    String FirstName;
    String LastName;
    String Email;
    String Education;
    String Specialisation;
    //String Education;
    lecturer(int lecturer_id,String firstname,String lastname,String email,String education, String specialisation,int education_id){
    lecturerID = lecturer_id;
    FirstName = firstname;
    LastName = lastname;
    Email = email;
    Specialisation = specialisation;
    Education = education;
    }

    public int getLecturerID() {
        return lecturerID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public String getLastName() {
        return LastName;
    }
    
    public String getEmail() {
        return Email;
    }
    
    public String getEducation() {
        return Education;
    }
    public String getSpecialisation() {
        return Specialisation;
    }

    //public int getEducationID() {
       // return educationID;
    //}

   public void setLecturerID(int lecturerID) {
        this.lecturerID = lecturerID;
    }

    public void setFirstName(String FirstName) {
        this.FirstName = FirstName;
    }

    public void setLastName(String LastName) {
        this.LastName = LastName;
    }

    public void setSpecialisation(String specialisationID) {
        this.Specialisation = Specialisation;
    }

    public void setEducation(int educationID) {
        this.Education = Education;
    }

    public void setEmail(String Email) {
        this.Email = Email;
    }

    public void setEducation(String Education) {
        this.Education = Education;
    }
    
}
