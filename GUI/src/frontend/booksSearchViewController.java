package frontend;

import com.mbox.BookAPI.Book;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

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
    }


    private void setTableOFBooksCellProperty() {
        tableOfBooks.setCellFactory(param -> new ListCell<Book>() {
            {
                prefWidthProperty().bind(tableOfBooks.widthProperty());
                setMaxWidth(Control.USE_PREF_SIZE);
            }

                    @Override
                    public void updateItem(Book item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setText(null);
                            setGraphic(null);
                        } else {
                            BookViewListCellController controller = new BookViewListCellController();
                            controller.setCellInfo(item);
                            GridPane cell = controller.getCell();
                            setGraphic(cell);


                        }
                    }
                }
        );
    }

    private void search(String searchQuery) {
//        call the api using this query
    }


}
