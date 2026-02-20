
package project;

public class specialisation {
    int specialisationID;
    String specialisationName;
    
    specialisation(int specialisation_id,String specialisation_name){
    specialisationID = specialisation_id;
    specialisationName = specialisation_name;
    }

    public int getSpecialisationID() {
        return specialisationID;
    }

    public String getSpecialisationName() {
        return specialisationName;
    }

    public void setSpecialisationID(int specialisationID) {
        this.specialisationID = specialisationID;
    }
    public void setSpecialisationName(String specialisationName) {
        this.specialisationName = specialisationName;
    }
}
