import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import java.util.ArrayList;

public class Controller {
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

    public void exit() {
        System.exit(0);
    }

    public void search() {

        String crn = crnTF.getText();
        String professor = profTF.getText();
        String department = departTF.getText();
        String course = courseTF.getText();
        String resourse = resourceTF.getText();

        // TODO :: BACKEND JOB CREATE A DATA MANAGER AND RETURN THE RESULTS
        System.out.print(crn + "||" + professor + "||" + department + "||" + course + "||" + resourse);

        // make sure the resulting sql query is turned into array list of courses
        ArrayList<Course> result;


        System.out.print(tableTV.getColumns());

    }

    public void add() {

        HBox mainAddPane = new HBox(2);
        VBox labelsPane = new VBox(4);
        VBox textFieldsPane = new VBox(4);
        Dialog dlg = new Dialog();

        ImageView icon = new ImageView(this.getClass().getResource("/Media/icon.png").toString());
        icon.setFitHeight(100);
        icon.setFitWidth(100);

        TextField crnTF = new TextField();
        TextField profTF = new TextField();
        TextField classTF = new TextField();
        TextField bookTF = new TextField();

        Label crnLbl = new Label("CRN: ");
        Label profLbl = new Label("Professor's Name: ");
        Label classLbl = new Label("Course' Name: ");
        Label bookLbl = new Label("Book: ");

        ButtonType addNewClass = new ButtonType("Add The New Course", ButtonBar.ButtonData.OK_DONE);

        labelsPane.getChildren().addAll(crnLbl, profLbl, classLbl, bookLbl);
        textFieldsPane.getChildren().addAll(crnTF, profTF, classTF, bookTF);
        mainAddPane.getChildren().addAll(labelsPane, textFieldsPane);

        dlg.setTitle("Adding A New Course");
        dlg.setHeaderText("Add a New Course");

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
