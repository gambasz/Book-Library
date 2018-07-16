package frontend;

import com.mbox.BookAPI.Book;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class booksSearchViewController {

    @FXML
    private TextField searchTextF;

    @FXML
    private Button searchBtn;
    @FXML
    ListView<Book> tableOfBooks;
    private ArrayList<Book> bookslist;

    @FXML
    void initialize() {
        bookslist = new ArrayList();
        searchBtn.setOnMouseClicked(e -> {
            search(searchTextF.getText());
        });
        setTableOFBooksCellProperty();
        Book temp = new Book();
        final String programeIconImg = "/frontend/media/icon.png";
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        temp.setIcon(icon);
        tableOfBooks.getItems().add(temp);
    }

    private void setTableOFBooksCellProperty() {
        tableOfBooks.setCellFactory(param -> new ListCell<Book>() {
                    @Override
                    public void updateItem(Book item, boolean empty) {
                        super.updateItem(item,empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            try {
                                setGraphic(item.getIcon());
                                this.getChildren().addAll(new Label("HELLO"));
                            } catch(Exception Ex) {
                                System.out.println (Ex.getMessage());
                                System.err.println(item);
                            }



                        }
                    }
                }
        );
    }

    private void search(String searchQuery) {
//        call the api using this query
    }
}
