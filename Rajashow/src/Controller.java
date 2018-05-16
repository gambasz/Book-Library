import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Controller {
    public void exit() {
        System.exit(0);
    }

    public void search() {
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
        Label classLbl = new Label("Class' Name: ");
        Label bookLbl = new Label("Book: ");

        ButtonType addNewClass = new ButtonType("Add The New Class", ButtonBar.ButtonData.OK_DONE);

        labelsPane.getChildren().addAll(crnLbl, profLbl, classLbl, bookLbl);
        textFieldsPane.getChildren().addAll(crnTF, profTF, classTF, bookTF);
        mainAddPane.getChildren().addAll(labelsPane, textFieldsPane);

        dlg.setTitle("Adding A New Class");
        dlg.setHeaderText("Add a New Class");

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
