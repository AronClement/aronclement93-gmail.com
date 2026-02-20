/*              BSc. ITS GROUP NUMBER 2
GROUP MEMBERS NAMES               REGISTRATION NUMBER
1. CLEMENT ANDREW ARON              14322045/T.24
2. EMMANUEL RENATUS LALIKA          14315054/T.24
3. FLOMENA KRISTOFA MTEWELE         14322078/T.24
4. HELENA TOGOLANI RASHIDI          14322008/T.24
5. FELISTA F NGAIZA                 14322096/T.24
6. FRANSIS NADA PAULO               14322086/T.24
7. YONA AUDIFONCE ELIZEUS           14322070/T.24
8. BRUNO ENHARD MLELWA              14322092/T.24*/

package project;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.ArrayList;
import java.util.List;

public class Project extends Application {

    //DATABASE CONNECTION
    static final String URL = "jdbc:mysql://localhost:3306/course_assignment";
    static final String USNM = "root";
    static final String PSWD = "";
    
    Connection conn = null;
    
    private BorderPane groupAssignment;
    private VBox contentBox;
    
    //LABLES AVAILABLE
    Label title;
    Label nameLabel;
    Label firstLabel;
    Label lastLabel;
    Label codeLabel;
    Label emailLabel;
    Label lecturerLabel;
    Label currentSpecialisationLabel;
    Label newSpecialisationLabel;

    //TEXT FIELD AVAILABLE
    TextField nameField;
    TextField firstField;
    TextField lastField;
    TextField codeField;
    TextField emailField;
    TextField newSpecialisationField;
    //BUTTONS
    Button addCourse;
    Button addEducation;
    Button addLecturer;
    Button addSpecialisation;
    Button assignCourse;
    Button viewLecturer;
    Button viewCourse;
    Button editSpecialisation;
    Button updateLecturer;
    Button updateEducation;
    Button fullView;
 
    //LIST OF EDUCATION LEVELS
    public List<String> educationLevels = new ArrayList<>();
    public List<lecturer> lecturer = new ArrayList<>();

    //LIST OF COURSES
    public List<course> courses = new ArrayList<>();
    int courseCounter = 1;
//Helper for filling specialisation in each lecturer
   public void loadSpecialisationsFromDB() {
    try {
        conn = openConnection();
        String sql = "SELECT lecturer_id, specialisation_name FROM specialisation";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int lecturerId = rs.getInt("lecturer_id");
            String spec = rs.getString("specialisation_name");

            // find lecturer by ID and add specialisation
            for (lecturer l : lecturer) {
                String fullName = l.getName();
                if (fullName.equals(getLecturerNameById(lecturerId))) {
                    l.getSpecialisations().add(spec);
                    break;
                }
            }
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
    //LECTURER CLASS
    public static class lecturer {
        String firstName;
        String lastName;
        String EMAIL;
        String Education;
        List<CourseAssignment> assignments = new ArrayList<>();
        List<String> specialisations = new ArrayList<>();
        public List<String> getSpecialisations() {
        return specialisations;
}
        public lecturer(String fst,String lst, String email, String education) {
            firstName = fst;
            lastName = lst;
            EMAIL = email;
            Education = education;
        }
        public String getName(){
            return firstName + " " + lastName;
        }
        public String getfirstName(){
            return firstName;
        }
        public String getlastName(){
            return lastName;
        }
        public String getEmail(){
            return EMAIL;
        }
        public String getEducation(){
            return Education;
        }
    //COURSE ASSIGNMENT
        public static class CourseAssignment{
            String Lecturer;
            String Course;
            public CourseAssignment(String lecturer, String course){
                Lecturer = lecturer;
                Course = course;
            }
            public String getLecturer(){
                return Lecturer;
            }
            public String getCourse(){
                return Course;
            }
        }
    }
    
    @Override
    public void start(Stage stage) {
     
        groupAssignment = new BorderPane();

        //LEFT BUTTONS
        VBox buttonBox = createButtons();

        //CONTENT AREA
        contentBox = new VBox();
        contentBox.setAlignment(Pos.TOP_LEFT);
        contentBox.setStyle("-fx-padding: 30;");/*It adds space on all four sides (top, right, bottom, left) inside the VBox*/

        //SEPARATOR LINE OF BUTTONS AND CONTENT AREA
        Separator separator = new Separator(Orientation.VERTICAL);
        HBox leftSide = new HBox(buttonBox, separator);
        //HEADER
        groupAssignment.setTop(headerUI());
        //LAYOUT
        groupAssignment.setLeft(leftSide);
        groupAssignment.setCenter(contentBox);
        //DEFAULT VIEW
        setContent(homeUI());

        Scene scene = new Scene(groupAssignment, 1385, 710);
        stage.setTitle("COURSE ASSIGNMENT PLATFORM");
        stage.setScene(scene);
        stage.show();
    }

    //HEADER
    public VBox headerUI() {
        title = new Label("COURSE ASSIGNMENT PLATFORM");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 28));

        VBox header = new VBox(title);
        header.setAlignment(Pos.CENTER);
        header.setStyle("-fx-padding: 20;");
        return header;
    }
    //BUTTONS
    public VBox createButtons() {
        VBox vbox = new VBox(15);
        vbox.setStyle("-fx-padding: 20;");
        vbox.setAlignment(Pos.CENTER_LEFT);
        
        addCourse = new Button("Add Course");
        addEducation = new Button("Add Education");
        addLecturer = new Button("Add Lecturer");
        addSpecialisation = new Button("Add Specialisation");
        assignCourse = new Button("Assign Course");
        viewLecturer = new Button("View Lecturer");
        viewCourse = new Button("View Course");
        editSpecialisation = new Button("Edit Specialisation");
        updateLecturer = new Button("Update Lecturer");
        updateEducation = new Button("Update Education");
        fullView = new Button("Full View");
        
        Button[] buttons = {addCourse, addEducation, addLecturer, addSpecialisation,assignCourse,
            viewLecturer, viewCourse,editSpecialisation, updateLecturer, updateEducation, fullView};
        //BUTTON SIZE
        for (Button b : buttons){
            setButtonSize(b);
        }
        //BUTTON ACTION
        addCourse.setOnAction(e -> setContent(addCourseUI()));
        addEducation.setOnAction(e -> setContent(addEducationUI()));
        addLecturer.setOnAction(e -> setContent(addLecturerUI()));
        addSpecialisation.setOnAction(e -> setContent(addSpecialisationUI()));
        assignCourse.setOnAction(e -> setContent(assignCourseUI()));
        viewLecturer.setOnAction(e -> setContent(viewLecturerUI()));
        viewCourse.setOnAction(e -> setContent(viewCourseUI()));
        editSpecialisation.setOnAction(e -> setContent(editSpecialisationUI()));
        updateLecturer.setOnAction(e -> setContent(updateLecturerUI()));
        updateEducation.setOnAction(e -> setContent(updateEducationUI()));
        fullView.setOnAction(e -> setContent(FullViewUI()));

        vbox.getChildren().addAll(buttons);
        return vbox;
    }
    
    public static Connection openConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL,USNM,PSWD);
            return conn;

        } catch (SQLException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //CONTENT HANDLER
    public void setContent(Node node) {
        contentBox.getChildren().clear();
        contentBox.getChildren().add(node);
    }
    //USER INTERFACE SCREENS
    public VBox homeUI() {
        title = new Label("Welcome to Course Assignment Platform");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox vbox = new VBox(title);
        vbox.setAlignment(Pos.CENTER);
        return vbox;
    }
    //ADDING OF COURSE
    public VBox addCourseUI() {
        title = new Label("Add Course");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 30;");

        nameLabel = new Label("Course Name");
        nameField = new TextField();

        codeLabel = new Label("Course Code");
        codeField = new TextField();

        nameField.setMaxWidth(500);
        codeField.setMaxWidth(100);

        Button saveBtn = new Button("Save Course");

    saveBtn.setOnAction(e -> {
    String name = toTitleCase(nameField.getText().trim());
    String code = codeField.getText().trim().toUpperCase();

    //Regex Validation
    if (name.isEmpty() || code.isEmpty()) {
        showAlert("Error","Fill all fields");
        return; // early return no Database saving
    }
    if (!validateCourseCode(code)) {
        showAlert("Error", "Invalid course code! Example: CSS 101");
        return;
    }
    if (!validateCourseName(name)) {
        showAlert("Error", "Course name must be at least 8 letters and valid format");
        return;
    }

    //SAVE TO DB ONLY IF VALID
    try {
        conn = openConnection();
        String DQUERY = "INSERT INTO course (course_code, course_name) VALUES (?,?)";
        PreparedStatement pst = conn.prepareStatement(DQUERY);
        pst.setString(1, code);
        pst.setString(2, name);
        pst.executeUpdate();

        courses.add(new course(courseCounter++, code, name));

        showAlert("Success", "Course saved to database!");
        nameField.clear();
        codeField.clear();
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
});

            vbox.getChildren().addAll(title,nameLabel, nameField,codeLabel, codeField,saveBtn);
            return vbox;
}
  //Regex helper for course restrictions
  private String toTitleCase(String text) {
    String[] words = text.toLowerCase().split(" ");
    StringBuilder result = new StringBuilder();

    for (String w : words) {
        result.append(Character.toUpperCase(w.charAt(0)))
              .append(w.substring(1))
              .append(" ");
    }
    return result.toString().trim();
}
  //ADDING EDUCATION
   public VBox addEducationUI() {
    title = new Label("Add Education");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");
    
    Label levelLabel = new Label("Education Level");

    ComboBox<String> levelCombo = new ComboBox<>();
    levelCombo.getItems().addAll("Certificate", "Diploma", "Bachelor","Masters","PhD");
    levelCombo.setPromptText("Select Education Level. Required:Bachelor,Masters and PhD");

    Button saveBtn = new Button("Save Education");
    saveBtn.setOnAction(e -> {
        String level = levelCombo.getValue();

        if (level == null) {
        showAlert("Error","Please select education level");
        }
        if (!validateEducation(level)) {
        showAlert("Error","Only Bachelor, Masters or PhD allowed");
        }else {
            if(!educationLevels.contains(level)) {
            educationLevels.add(level);
        }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Saved");
            alert.setHeaderText(null);
            alert.setContentText("Education level " + level + " saved successfully!");
            alert.showAndWait();

            levelCombo.getSelectionModel().clearSelection();
        }
    });
            vbox.getChildren().addAll(title,levelLabel,levelCombo,saveBtn);
            return vbox;
}
   //LOADING EDUCATION LEVEL FROM DATABASE
   public void loadEducationFromDB() {
    educationLevels.clear();
    try {
        conn = openConnection();
        String sql = "SELECT education_level FROM education";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();
        
        while (rs.next()) {
            educationLevels.add(rs.getString("education_level"));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
   //ADDING LECTURER
  public VBox addLecturerUI(){
    title = new Label("Add Lecturer");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox();
    vbox.setStyle("-fx-padding: 30;");

    firstLabel = new Label("First Name");
    firstField = new TextField();
    firstField.setMaxWidth(200);

    lastLabel = new Label("Last Name");
    lastField = new TextField();
    lastField.setMaxWidth(200);

    emailLabel = new Label("Email");
    emailField = new TextField();
    emailField.setMaxWidth(200);

    Label eduLabel = new Label("Education Level");
    ComboBox<String> eduCombo = new ComboBox<>();
    loadEducationFromDB();
    eduCombo.getItems().addAll(educationLevels); // load saved levels
    eduCombo.setPromptText("Select Education Level");
    eduCombo.setMaxWidth(200);
       
    Button saveBtn = new Button("Save Lecturer");
    saveBtn.setOnAction(e -> {
    String fst = firstField.getText().trim();
    String lst = lastField.getText().trim();
    String email = emailField.getText().trim();
    String education = eduCombo.getValue();

    //Regex Validation
    if (fst.isEmpty() || lst.isEmpty() || email.isEmpty() || education == null) {
        showAlert("Error","Fill all fields!");
        return;
    }
    if (!validateName(fst)) {
        showAlert("Error","First name must contain letters only!");
        return;
    }
    if (!validateName(lst)) {
        showAlert("Error","Last name must contain letters only!");
        return;
    }
    if (!validateEmail(email)) {
        showAlert("Error","Invalid email address!");
        return;
    }
    if (!validateEducation(education)) {
        showAlert("Error","Education must be Bachelor, Masters, or PhD!");
        return;
    }

    // SAVE TO DATABASEB ONLY IF VALID
    try {
        conn = openConnection();
        String DQUERY = "INSERT INTO lecturer (first_name, last_name, email, education) VALUES (?,?,?,?)";
        PreparedStatement pst = conn.prepareStatement(DQUERY);
        pst.setString(1, fst);
        pst.setString(2, lst);
        pst.setString(3, email);
        pst.setString(4, education);
        pst.executeUpdate();

        lecturer.add(new lecturer(fst, lst, email, education));
        showAlert("Success", "Lecturer saved to database!");

        firstField.clear();
        lastField.clear();
        emailField.clear();
        eduCombo.setValue(null);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
});

    vbox.getChildren().addAll(title,firstLabel, firstField,lastLabel, lastField, emailLabel, emailField, eduLabel, eduCombo, saveBtn);
    return vbox;
    }
  //ADDING SPECIALISATION TO LECTURER
  public VBox addSpecialisationUI() {
    title = new Label("Add Lecturer Specialisation");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    lecturerLabel = new Label("Select Lecturer");
    ComboBox<String> lecturerCombo = new ComboBox<>();

    //Load lecturer from database
    for (lecturer l : lecturer) {
        lecturerCombo.getItems().add(l.getName());
    }

    Label specLabel = new Label("Specialisation");
    TextField specField = new TextField();
    specField.setMaxWidth(400);
    
    Button saveBtn = new Button("Save Specialisation");
    saveBtn.setOnAction(e -> {
    String lecName = lecturerCombo.getValue();
    String spec = toTitleCase(specField.getText().trim());
    //Regex Validation
    if (lecName == null || spec.isEmpty()) {
        showAlert("Error","Please select lecturer and enter specialisation");
        return;
    }
    if (!validateSpecialisationName(spec)) {
        showAlert("Error","Specialisation must have at least 5 letters and valid format");
        return;
    }
    //Saving to database if valid
    try {
        conn = openConnection();
        String DQUERY = "SELECT id FROM lecturer WHERE CONCAT(first_name,' ',last_name)=?";
        PreparedStatement pst1 = conn.prepareStatement(DQUERY);
        pst1.setString(1, lecName);
        ResultSet rs = pst1.executeQuery();
        if (rs.next()) {
            int lecturerId = rs.getInt("id");
            String sql = "INSERT INTO specialisation (lecturer_id, specialisation_name) VALUES (?,?)";
            PreparedStatement pst2 = conn.prepareStatement(sql);
            pst2.setInt(1, lecturerId);
            pst2.setString(2, spec);
            pst2.executeUpdate();
        }
        // Update Java list
        for (lecturer l : lecturer) {
            if (l.getName().equals(lecName)) {
                l.specialisations.add(spec);
                break;
            }
        }
        showAlert("Success","Specialisation saved to database!");
        specField.clear();
        lecturerCombo.setValue(null);
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
});
    vbox.getChildren().addAll(title,lecturerLabel, lecturerCombo,specLabel, specField,saveBtn);
    return vbox;
}
    //ASIGNING COURSE TO LECTURER
    public VBox assignCourseUI() {
        title = new Label("Assign Course to Lecturer");
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox vbox = new VBox(10);
        vbox.setStyle("-fx-padding: 30;");

        lecturerLabel = new Label("Lecturer");
        ComboBox<String> lecturerCombo = new ComboBox<>();
        for(lecturer l : lecturer) {
            lecturerCombo.getItems().add(l.getName());
            //lecturerCombo.getItems().add(l.getlastName());
        }

        Label courseLabel = new Label("Course");
        ComboBox<String> courseCombo = new ComboBox<>();

        for(course c : courses){
          courseCombo.getItems().add(c.getCourseName());
        }

        Button assignBtn = new Button("Assign");
        assignBtn.setOnAction(e -> {
        String lecturerName = lecturerCombo.getValue();
        String course = courseCombo.getValue();
        
        if(lecturerName == null || course == null/* || course.isEmpty()*/) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setContentText("Please select lecturer and course");
        alert.showAndWait();
    }
    try {
        conn = openConnection();
        String getId = "SELECT id FROM lecturer WHERE CONCAT(first_name,' ',last_name)=?";
        PreparedStatement pst1 = conn.prepareStatement(getId);
        pst1.setString(1, lecturerName);
        ResultSet rs = pst1.executeQuery();

        if(rs.next()) {
            int lecturerId = rs.getInt("id");

            // CHECK IF ALREADY ASSIGNED
            String check = "SELECT * FROM assignments WHERE lecturer_id=? AND course_name=?";
            PreparedStatement pstCheck = conn.prepareStatement(check);
            pstCheck.setInt(1, lecturerId);
            pstCheck.setString(2, course);
            ResultSet rsCheck = pstCheck.executeQuery();
            if(rsCheck.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setContentText("This course is already assigned to this lecturer.");
                alert.showAndWait();
            }

            String sql = "INSERT INTO assignments (lecturer_id, course_name) VALUES (?,?)";
            PreparedStatement pst2 = conn.prepareStatement(sql);
            pst2.setInt(1, lecturerId);
            pst2.setString(2, course);
            pst2.executeUpdate();
        }

    } catch(SQLException ex) {
        ex.printStackTrace();
    }
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setContentText("Course assigned successfully!");
    alert.showAndWait();
    courseCombo.setValue(null);
    lecturerCombo.setValue(null);
});
        vbox.getChildren().addAll(title, lecturerLabel, lecturerCombo, courseLabel, courseCombo, assignBtn);
        return vbox;
    }
    public List<String> getCoursesForLecturer(String fullName) {
    List<String> list = new ArrayList<>();
    try{
        conn = openConnection();
        String DQUERY = """
                        SELECT c.course_name
                        FROM courseassignment ca
                        JOIN course c ON ca.course_id = c.course_id
                        JOIN lecturer l ON ca.lecturer_id = l.lecturer_id
                        WHERE CONCAT(l.firstname,' ',l.lastname)=?
                        """;
        PreparedStatement pst = conn.prepareStatement(DQUERY);
        pst.setString(1, fullName);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            list.add(rs.getString("course_name"));
        }

    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return list;
}
    //Loading assignments from database
    public void loadAssignmentsFromDB() {
    try {
        conn = openConnection();
        
                //Clear previous assignments
        for (lecturer l : lecturer) {
            l.assignments.clear();
        }
        String sql = "SELECT l.id AS lecturer_id, c.course_name " +
                     "FROM assignments a " +
                     "JOIN lecturer l ON a.lecturer_id = l.id " +
                     "JOIN course c ON a.course_name = c.course_name"; // assumes course_name unique
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            int lecturerId = rs.getInt("lecturer_id");
            String courseName = rs.getString("course_name");

            //Find lecturer in Java list
            for (lecturer l : lecturer) {
                // Use email or full name or DB id (if you store it in lecturer class)
                if (l.getName().equals(getLecturerNameById(lecturerId))) {
                    l.assignments.add(new lecturer.CourseAssignment(l.getName(), courseName));
                    break;
                }
            }
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
// Helper to get lecturer name from ID
public String getLecturerNameById(int id) {
    try {
        conn = openConnection();
        String sql = "SELECT CONCAT(first_name,' ',last_name) AS fullName FROM lecturer WHERE id=?";
        PreparedStatement pst = conn.prepareStatement(sql);
        pst.setInt(1, id);
        ResultSet rs = pst.executeQuery();
        if (rs.next()) return rs.getString("fullName");
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
    return "";
}
//Retriving Lecturer details.
public VBox viewLecturerUI() {
    title = new Label("Lecturer and Assigned Courses");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    TableView<LecturerTable> table = new TableView<>();
    table.setPrefWidth(900);

    //Define columns
    TableColumn<LecturerTable, Integer> idCol = new TableColumn<>("Lecturer ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));

    TableColumn<LecturerTable, String> firstCol = new TableColumn<>("First Name");
    firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    firstCol.setPrefWidth(120);

    TableColumn<LecturerTable, String> lastCol = new TableColumn<>("Last Name");
    lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    lastCol.setPrefWidth(120);

    TableColumn<LecturerTable, String> emailCol = new TableColumn<>("Email");
    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    emailCol.setPrefWidth(180);

    TableColumn<LecturerTable, String> eduCol = new TableColumn<>("Education");
    eduCol.setCellValueFactory(new PropertyValueFactory<>("education"));
    eduCol.setPrefWidth(100);

    TableColumn<LecturerTable, String> specCol = new TableColumn<>("Specialisations");
    specCol.setCellValueFactory(new PropertyValueFactory<>("specialisations"));
    specCol.setPrefWidth(180);

    TableColumn<LecturerTable, String> courseCol = new TableColumn<>("Assigned Courses");
    courseCol.setCellValueFactory(new PropertyValueFactory<>("courses"));
    courseCol.setPrefWidth(200);

    table.getColumns().addAll(idCol, firstCol, lastCol, emailCol, eduCol, specCol, courseCol);

    //Load lecturers from database
    lecturer.clear();
    try {
        conn = openConnection();
        String query = "SELECT * FROM lecturer";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            lecturer.add(new lecturer(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("education")
            ));
        }

        // Load assignments and specialisations
        loadAssignmentsFromDB();
        loadSpecialisationsFromDB();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    //Populate TableView
    ObservableList<LecturerTable> data = FXCollections.observableArrayList();
    int counter = 1;
    for (lecturer l : lecturer) {
        String coursesList = String.join(", ", l.assignments.stream()
                                        .map(ca -> ca.getCourse())
                                        .toList());

        String specsList = String.join(", ", l.getSpecialisations());

        data.add(new LecturerTable(counter++, l.getfirstName(), l.getlastName(),
                                   l.getEmail(), l.getEducation(), specsList, coursesList));
    }
    table.setItems(data);
    vbox.getChildren().addAll(title, table);
    return vbox;
}
    public static class LecturerTable {Integer lecturerID;
    String firstName;
    String lastName;
    String email;
    String education;
    String specialisations;
    String courses;

    // Constructor
    public LecturerTable(int lecturerID, String firstName, String lastName, String email,
                         String education, String specialisations, String courses) {
        this.lecturerID = lecturerID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.education = education;
        this.specialisations = specialisations;
        this.courses = courses;
    }

    // Getters for TableView binding
    public Integer getLecturerID() {
        return lecturerID;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getEducation() {
        return education;
    }

    public String getSpecialisations() {
        return specialisations;
    }

    public String getCourses() {
        return courses;
    }
}
    //HELPERS
    public VBox simpleForm(String titleText, String field1, String field2, String btnText) {
        title = new Label(titleText);
        title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox box = new VBox(10);
        box.setStyle("-fx-padding: 30;");

        box.getChildren().add(title);
        box.getChildren().add(new Label(field1));
        box.getChildren().add(new TextField());

        if (field2 != null) {
            box.getChildren().add(new Label(field2));
            box.getChildren().add(new TextField());
        }

        box.getChildren().add(new Button(btnText));
        return box;
    }

    private VBox simpleLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

        VBox box = new VBox(label);
        box.setAlignment(Pos.CENTER);
        return box;
    }

    private void setButtonSize(Button btn) {
        btn.setPrefWidth(220);
        btn.setPrefHeight(45);
    }
   //Retriving Courses from database 
   public VBox viewCourseUI(){
    loadCoursesFromDB();  //load courses before displaying

    title = new Label("View Courses");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    TableView<course> table = new TableView<>();
    table.setPrefWidth(600);

    TableColumn<course, Integer> idCol = new TableColumn<>("Course ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("courseID"));
    idCol.setPrefWidth(150);

    TableColumn<course, String> codeCol = new TableColumn<>("Course Code");
    codeCol.setCellValueFactory(new PropertyValueFactory<>("courseCode"));
    codeCol.setPrefWidth(200);

    TableColumn<course, String> nameCol = new TableColumn<>("Course Name");
    nameCol.setCellValueFactory(new PropertyValueFactory<>("courseName"));
    nameCol.setPrefWidth(250);

    table.getColumns().addAll(idCol, codeCol, nameCol);

    ObservableList<course> data = FXCollections.observableArrayList(courses);
    table.setItems(data);

    vbox.getChildren().addAll(title, table);
    return vbox;
}  
//Helper for viewing Course
   public void loadCoursesFromDB() {
    courses.clear();
    try {
        conn = openConnection();
        String sql = "SELECT course_ID, course_code, course_name FROM course";
        PreparedStatement pst = conn.prepareStatement(sql);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            courses.add(new course(rs.getInt("course_ID"),rs.getString("course_code"),rs.getString("course_name")    
            ));
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
    }
}
   //Editing Specialisation for Lecturer
public VBox editSpecialisationUI() {
    Label title = new Label("Edit Lecturer Specialisation");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    //Lecturer selection
    Label lecturerLabel = new Label("Select Lecturer");
    ComboBox<String> lecturerBox = new ComboBox<>();
    for (lecturer l : lecturer) {
        lecturerBox.getItems().add(l.getName());
    }

    //Current Specialisation selection
    Label currentSpecLabel = new Label("Current Specialisation");
    ComboBox<String> currentSpecBox = new ComboBox<>();

    //New Specialisation input
    Label newSpecLabel = new Label("New Specialisation");
    TextField newSpecField = new TextField();
    newSpecField.setMaxWidth(400);

    //Load specialisations when lecturer selected
    lecturerBox.setOnAction(e -> {
        currentSpecBox.getItems().clear();
        String selected = lecturerBox.getValue();
        if (selected != null) {
            for (lecturer l : lecturer) {
                if (l.getName().equals(selected)) {
                    currentSpecBox.getItems().addAll(l.getSpecialisations());
                    break;
                }
            }
        }
    });

    //Update button
    Button updateBtn = new Button("Update Specialisation");
    updateBtn.setOnAction(e -> {
        String selectedLecturer = lecturerBox.getValue();
        String oldSpec = currentSpecBox.getValue();
        String newSpec = newSpecField.getText().trim();

        if (selectedLecturer == null || oldSpec == null || newSpec.isEmpty()) {
            showAlert("Error", "Select lecturer, specialisation, and enter new one!");
            return;
        }
        // Validating new specialisation
        if (!validateSpecialisationName(newSpec)) {
            showAlert("Error", "Specialisation must be at least 5 letters, letters only!");
            return;
        }
        // Update Java List
        for (lecturer l : lecturer) {
            if (l.getName().equals(selectedLecturer)) {
                l.specialisations.remove(oldSpec);
                l.specialisations.add(newSpec);
                break;
            }
        }

        // Update Database
        try {
            conn = openConnection();
            String sql = """
                         UPDATE specialisation
                         SET specialisation_name=?
                         WHERE lecturer_id=(SELECT id FROM lecturer WHERE CONCAT(first_name,' ',last_name)=?)
                         AND specialisation_name=?
                         """;
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newSpec);
            pst.setString(2, selectedLecturer);
            pst.setString(3, oldSpec);
            pst.executeUpdate();

            showAlert("Success", "Specialisation updated successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Database update failed!");
        }
        // Clear fields
        lecturerBox.setValue(null);
        currentSpecBox.getItems().clear();
        newSpecField.clear();
    });

    vbox.getChildren().addAll(
            title,
            lecturerLabel, lecturerBox,
            currentSpecLabel, currentSpecBox,
            newSpecLabel, newSpecField,
            updateBtn
    );
    return vbox;
}
 //Updating Lecturer details
public VBox updateLecturerUI() {

    Label title = new Label("Update Lecturer Details");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    ComboBox<String> lecturerBox = new ComboBox<>();
    for (lecturer l : lecturer) {
        lecturerBox.getItems().add(l.getName());
    }
    firstLabel = new Label("New First Name");
    TextField firstField = new TextField();
    firstField.setPromptText("New First Name");
    firstField.setMaxWidth(200);

    lastLabel = new Label("New Last Name");
    TextField lastField = new TextField();
    lastField.setPromptText("New Last Name");
    lastField.setMaxWidth(200);

    emailLabel = new Label("New Email");
    TextField emailField = new TextField();
    emailField.setPromptText("New Email");
    emailField.setMaxWidth(200);

    Button updateBtn = new Button("Update Lecturer");

    updateBtn.setOnAction(e -> {

        String selected = lecturerBox.getValue();
        String newFirst = firstField.getText();
        String newLast = lastField.getText();
        String newEmail = emailField.getText();

        if (selected == null) {
            showAlert("Error", "Select a lecturer first!");
            return;
        }

        if (newFirst.isEmpty() || newLast.isEmpty() || newEmail.isEmpty()) {
            showAlert("Error", "Fill all fields!");
            return;
        }
        // Update Java List
        for (lecturer l : lecturer) {
            if (l.getName().equals(selected)) {
                l.firstName = newFirst;
                l.lastName = newLast;
                l.EMAIL = newEmail;
                break;
            }
        }
        // Update Database
        try {
            conn = openConnection();
            String sql =
              "UPDATE lecturer SET first_name=?, last_name=?, email=? " +
              "WHERE CONCAT(first_name,' ',last_name)=?";

            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newFirst);
            pst.setString(2, newLast);
            pst.setString(3, newEmail);
            pst.setString(4, selected);

            pst.executeUpdate();

            showAlert("Success", "Lecturer updated successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Database update failed!");
        }
        // Refresh combo box
        lecturerBox.getItems().clear();
        for (lecturer l : lecturer) {
            lecturerBox.getItems().add(l.getName());
        }
    });

    vbox.getChildren().addAll(title,lecturerBox,firstField,lastField,emailField,updateBtn);
    return vbox;
}
    //Updating Education
    public VBox updateEducationUI() {

    Label title = new Label("Update Lecturer Education");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    // Lecturer selection
    ComboBox<String> lecturerBox = new ComboBox<>();
    for (lecturer l : lecturer) {
        lecturerBox.getItems().add(l.getName());
    }
    // Current Education (read-only)
    TextField currentEduField = new TextField();
    currentEduField.setPromptText("Current Education");
    currentEduField.setMaxWidth(100);
    currentEduField.setEditable(false);
    // New Education selection
    ComboBox<String> newEduBox = new ComboBox<>();
    newEduBox.getItems().addAll("Bachelor", "Masters", "PhD");
    // Update button
    Button updateBtn = new Button("Update Education");
    // When lecturer selected → show current education
    lecturerBox.setOnAction(e -> {
        String selected = lecturerBox.getValue();
        if (selected != null) {
            for (lecturer l : lecturer) {
                if (l.getName().equals(selected)) {
                    currentEduField.setText(l.getEducation());
                    break;
                }
            }
        }
    });

    // Button action
    updateBtn.setOnAction(e -> {
        String selected = lecturerBox.getValue();
        String newEdu = newEduBox.getValue();

        if (selected == null || newEdu == null) {
            showAlert("Error", "Select lecturer and new education!");
            return;
        }

        //Update Java List
        for (lecturer l : lecturer) {
            if (l.getName().equals(selected)) {
                l.Education = newEdu;
                break;
            }
        }

        //Update Database
        try {
            conn = openConnection();
            String sql = "UPDATE lecturer SET education=? WHERE CONCAT(first_name,' ',last_name)=?";
            PreparedStatement pst = conn.prepareStatement(sql);
            pst.setString(1, newEdu);
            pst.setString(2, selected);
            pst.executeUpdate();

            showAlert("Success", "Education updated successfully!");

        } catch (SQLException ex) {
            ex.printStackTrace();
            showAlert("Error", "Database update failed!");
        }

        // Refresh lecturer selection
        lecturerBox.getItems().clear();
        for (lecturer l : lecturer) {
            lecturerBox.getItems().add(l.getName());
        }
        // Clear fields
        lecturerBox.setValue(null);
        currentEduField.clear();
        newEduBox.setValue(null);
    });
    vbox.getChildren().addAll(title,lecturerBox,currentEduField,newEduBox,updateBtn);

    return vbox;
}
    //Retriving all details from the database
    public VBox FullViewUI() {
    title = new Label("Full View - All Lecturer Information");
    title.setFont(Font.font("Verdana", FontWeight.BOLD, 20));

    VBox vbox = new VBox(10);
    vbox.setStyle("-fx-padding: 30;");

    TableView<LecturerTable> table = new TableView<>();
    table.setPrefWidth(1100);

    //Define columns
    TableColumn<LecturerTable, Integer> idCol = new TableColumn<>("Lecturer ID");
    idCol.setCellValueFactory(new PropertyValueFactory<>("lecturerID"));
    idCol.setPrefWidth(80);

    TableColumn<LecturerTable, String> firstCol = new TableColumn<>("First Name");
    firstCol.setCellValueFactory(new PropertyValueFactory<>("firstName"));
    firstCol.setPrefWidth(150);

    TableColumn<LecturerTable, String> lastCol = new TableColumn<>("Last Name");
    lastCol.setCellValueFactory(new PropertyValueFactory<>("lastName"));
    lastCol.setPrefWidth(150);

    TableColumn<LecturerTable, String> emailCol = new TableColumn<>("Email");
    emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
    emailCol.setPrefWidth(220);

    TableColumn<LecturerTable, String> eduCol = new TableColumn<>("Education");
    eduCol.setCellValueFactory(new PropertyValueFactory<>("education"));
    eduCol.setPrefWidth(120);

    TableColumn<LecturerTable, String> specCol = new TableColumn<>("Specialisations");
    specCol.setCellValueFactory(new PropertyValueFactory<>("specialisations"));
    specCol.setPrefWidth(200);

    TableColumn<LecturerTable, String> courseCol = new TableColumn<>("Courses");
    courseCol.setCellValueFactory(new PropertyValueFactory<>("courses"));
    courseCol.setPrefWidth(250);

    table.getColumns().addAll(idCol, firstCol, lastCol, emailCol, eduCol, specCol, courseCol);

    //Load lecturers from DB
    lecturer.clear();
    try {
        conn = openConnection();
        String query = "SELECT * FROM lecturer";
        PreparedStatement pst = conn.prepareStatement(query);
        ResultSet rs = pst.executeQuery();

        while (rs.next()) {
            lecturer.add(new lecturer(
                rs.getString("first_name"),
                rs.getString("last_name"),
                rs.getString("email"),
                rs.getString("education")
            ));
        }

        // Load specialisations and assignments
        loadSpecialisationsFromDB();
        loadAssignmentsFromDB();

    } catch (SQLException ex) {
        ex.printStackTrace();
    }

    // --- Populate TableView ---
    ObservableList<LecturerTable> data = FXCollections.observableArrayList();
    int counter = 1;

    for (lecturer l : lecturer) {
        // Specialisations
        String specs = String.join(", ", l.getSpecialisations());

        // Courses (remove duplicates)
        StringBuilder courseList = new StringBuilder();
        for (lecturer.CourseAssignment ca : l.assignments) {
            if (!courseList.toString().contains(ca.getCourse())) {
                courseList.append(ca.getCourse()).append(", ");
            }
        }
        if (courseList.length() > 0)
            courseList.setLength(courseList.length() - 2);

        data.add(new LecturerTable(
            counter++,
            l.getfirstName(),
            l.getlastName(),
            l.getEmail(),
            l.getEducation(),
            specs,
            courseList.toString()
        ));
    }

    table.setItems(data);
    vbox.getChildren().addAll(title, table);
    return vbox;
}   
// VALIDATION METHODS
//Ensures that the course code has **exactly 3 uppercase letters,a single space, and 3 digits that are not 000.
    public boolean validateCourseCode(String code) {
    return code.matches("^[A-Z]{3} (?!000)\\d{3}$");
}
    //Requires at least 8 letters ignoring spaces/hyphens.Ensures the name starts with a letter.
    //Allows letters separated by spaces or hyphens (like "Computer-Science" or "Data Science").Disallows starting/ending with space or hyphen.
    public boolean validateCourseName(String name) {
    String clean = name.replaceAll("[\\s-]", "");
    if (clean.length() < 8) return false;
    return name.matches("^(?![-\\s])[A-Za-z]+(?:[\\s-][A-Za-z]+)*$");
} 
 //At least 5 alphabetical characters,May contain spaces in middle,No space at beginning or end,Store in Title Case
    public boolean validateSpecialisationName(String name) {
    String clean = name.replaceAll("\\s", "");
    if (clean.length() < 5) return false;
    return name.matches("^(?!\\s)[A-Za-z]+(?:\\s[A-Za-z]+)*$");
}
    //The name must consist only of letters.No spaces, no numbers, no special characters.
    public boolean validateName(String name) {
        return name.matches("^[a-zA-Z]+$");
 } 
    public boolean validateEducation(String level) {
    return level.equals("Bachelor") || level.equals("Masters") || level.equals("PhD");
}
    private boolean validateEmail(String email) {
    return email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
}
    public void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
     public static void main(String[] args){
        launch(args);
    }
}