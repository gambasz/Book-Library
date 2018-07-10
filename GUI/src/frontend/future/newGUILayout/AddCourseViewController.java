package frontend.future.newGUILayout;

import com.jfoenix.controls.*;
import frontend.data.Course;
import frontend.data.Person;
import frontend.data.Resource;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.util.ArrayList;

public class AddCourseViewController {

    private Course data;
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
        setCellFactory();
        initButtonButtonActions();

    }

    private void initButtonButtonActions() {
        courseTemplateFillBtn.setOnMouseClicked(e -> {
            openCourseTemplateSelectionView();
        });
        modifyProfessorResourcesBtn.setOnMouseClicked(e -> {
            openResourceManagerView(null, Person.class);

        });
        modifyCourseResources.setOnMouseClicked(e -> {
            openResourceManagerView(null, Course.class);

        });
        assignProfessorBtn.setOnMouseClicked(e->{
            openPersonSelectionView();
            
        });

    }

    private void openPersonSelectionView() {
    }

    private void openResourceManagerView(ArrayList<Resource> resources, Object parent) {
    }

    private void openCourseTemplateSelectionView() {
    }

    private void setCellFactory() {
        professorResourcesTable.setCellFactory(param -> {
            return new JFXListCell<Resource>() {
                @Override
                protected void updateItem(Resource item, boolean empty) {
                    super.updateItem(item, empty);
                    //test
                    if (item == null || empty) {
                        setText(" ");
                        setGraphic(null);
                    } else {
                        setText(item.getTitle());
                    }
                }
            };
        });
    }

    public void setData(Course data) {
        this.data = data;
    }
}
