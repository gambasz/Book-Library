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
    private Button filterBtn;

    @FXML
    private ImageView programIcon;

    @FXML
    private Button exportBtn;

    @FXML
    private Button addBtn;

    @FXML
    private Button ImportBtn;

    @FXML
    void initialize() {
        TreeItem<?> courseTree = new TreeItem<>();
    }

    }
