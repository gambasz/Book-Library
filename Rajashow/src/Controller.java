import data.Course;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class Controller {
    ArrayList<Course> table_data;
    @FXML
    TextField crnTF;
    @FXML
    TextField profTF;
    @FXML
    TextField departTF;
    @FXML
    TextField courseTF;
    @FXML
    TextField resourceTF;
    @FXML
    Button searchBtn;
    @FXML
    TableView tableTV;
    @FXML
    TableColumn<Course, Integer> crnCol;
    @FXML
    TableColumn<Course, String> profCol;
    @FXML
    TableColumn<Course, String> courseCol;
    @FXML
    TableColumn<Course, String> departCol;
    @FXML
    TableColumn<Course, ArrayList> resourceCol;


    public void exit() {
        System.exit(0);
    }

    public void search() {
        ArrayList<Course> temp_table = new ArrayList<>();
        String crn = crnTF.getText();
        String professor = profTF.getText();
        String department = departTF.getText();
        String course = courseTF.getText();
        String resourse = resourceTF.getText();

        crnCol.setCellValueFactory(
                new PropertyValueFactory<Course, Integer>("CRN"));
        profCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("professor"));
        courseCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("name"));
        departCol.setCellValueFactory(
                new PropertyValueFactory<Course, String>("department"));
        resourceCol.setCellValueFactory(
                new PropertyValueFactory<Course, ArrayList>("books"));

        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS
        System.out.print(crn + "||" + professor + "||" + department + "||" + course + "||" + resourse);

        // make sure the resulting sql query is turned into array list of courses

//        ResultSet rs= st.executeQuery(query1);
//        while(rs.next()) {
//        data.Course temp = new data.Course();
//        tableTV.getItems().add(temp);
        //     }

        ArrayList<String> books =  new ArrayList<>();
        books.add("major");
        Course temp = new Course(10,"bob","bob","bob",books);
        tableTV.getItems().add(temp);

         temp = new Course(10,"cat","bob","bob",books);
        tableTV.getItems().add(temp);

        System.out.print(tableTV.getColumns());

        updateTable(temp_table);

    }

    private void updateTable(ArrayList<Course> temp_table) {

    }

    public void add() {

        HBox mainAddPane = new HBox(2);
        VBox labelsPane = new VBox(4);
        VBox textFieldsPane = new VBox(4);
        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource("/media/icon.png").toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);

        TextField crnTF = new TextField();
        TextField profTF = new TextField();
        TextField classTF = new TextField();
        TextField bookTF = new TextField();

        Label crnLbl = new Label("CRN: ");
        Label profLbl = new Label("Professor's Name: ");
        Label classLbl = new Label("data.Course' Name: ");
        Label bookLbl = new Label("Book: ");

        ButtonType addNewClass = new ButtonType("Add The New data.Course", ButtonBar.ButtonData.OK_DONE);

        labelsPane.getChildren().addAll(crnLbl, profLbl, classLbl, bookLbl);
        textFieldsPane.getChildren().addAll(crnTF, profTF, classTF, bookTF);
        mainAddPane.getChildren().addAll(labelsPane, textFieldsPane);

        dlg.setTitle("Adding A New data.Course");
        dlg.setHeaderText("Add a New data.Course");

        dlg.setGraphic(icon);
        dlg.getDialogPane().setMinWidth(300);
        dlg.getDialogPane().setContent(mainAddPane);
        dlg.getDialogPane().getButtonTypes().addAll(addNewClass, ButtonType.CANCEL);


        dlg.show();
        dlg.setResultConverter(dialogButton -> {
            if (dialogButton == addNewClass) {
                System.out.print("adding the class");
                return null;
            }
            return null;
        });


    }

    public void apply() {
    }
}
