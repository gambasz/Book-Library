package frontend.future.newGUILayout;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTreeTableView;
import frontend.data.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class AddCourseViewController {


    @FXML
    private TextField courseNameTF, departmentTF;

    @FXML
    private JFXTextArea descriptionTA;

    @FXML
    private JFXListView<Resource> professorResourcesTable;

    @FXML
    private JFXButton courseTemplateFillBtn, modifyProfessorResourcesBtn, modifyCourseResources, assignProfessorBtn;


    @FXML
    private Label professorTypeLbl, professorLFNameLbl, professorLNameLbl;

    @FXML
    private JFXTreeTableView<?> resourcesTable;

    @FXML
    private void initialize() {
    }
}
