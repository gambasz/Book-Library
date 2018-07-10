package frontend.future.newGUILayout;

import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableView;
import frontend.data.Course;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;

public class MainPageController {

    @FXML
    private JFXTreeTableView<?> courseTable;

    @FXML
    private TreeTableColumn<Course, String> courseColumn;

    @FXML
    private TreeTableColumn<Course, String> professorColumn;

    @FXML
    private TreeTableColumn<Course, String> resourcesColumn;

    @FXML
    private TreeTableColumn<Course, String> actionColumn;

    @FXML
    private JFXTextField searchBar;

    @FXML
    private ImageView programIcon;

    @FXML
    private Button exportBtn, addBtn, ImportBtn, filterBtn;


    @FXML
    void initialize() {
        initButtonActions();
        makeTheLogoScale();
        TreeItem<?> courseTree = new TreeItem<>();
    }

    private void makeTheLogoScale() {
        double iconWRatio = programIcon.getFitWidth()/programIcon.getScene().getWidth();
        double iconHRatio = programIcon.getFitHeight()/programIcon.getScene().getHeight();

        programIcon.fitWidthProperty().addListener(p->{
            programIcon.setFitWidth(iconWRatio*programIcon.getScene().getWidth());
        });
        programIcon.fitHeightProperty().addListener(p->{
            programIcon.setFitWidth(iconHRatio*programIcon.getScene().getHeight());
        });
    }

    private void initButtonActions() {
        exportBtn.setOnMouseClicked(e -> {
            openExportView();
        });
        addBtn.setOnMouseClicked(e -> {
            openAddCourseView();
        });
        ImportBtn.setOnMouseClicked(e -> {
            openImportView();
        });
        filterBtn.setOnMouseClicked(e -> {
            showFilterOption();
        });

    }

    private void openImportView() {
    }

    private void showFilterOption() {
    }

    private void openAddCourseView() {
    }

    private void openExportView() {

    }

}
