package frontend;

import com.mbox.BookAPI.Book;
import com.mbox.Publisher;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

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
        addFakeBook();
    }

    private void addFakeBook() {
        final String programeIconImg = "/frontend/media/icon.png";
        ImageView icon = new ImageView(this.getClass().getResource(programeIconImg).toString());
        Publisher tempPublisher = new Publisher();
        tempPublisher.setTitle("Person");

        Date tempDate = Calendar.getInstance().getTime();
        ArrayList<String> tempAuthors = new ArrayList<>();
        tempAuthors.add("Rajashow");
        tempAuthors.add("MAMALEE");
        tempAuthors.add("DEVIL OF THE WEST");
        Book temp = new Book("Green Eggs and Ham", tempAuthors, null, "BOB", 5);
        temp.setDatePublished(tempDate);
        temp.setPublisherInstance(tempPublisher);
        temp.setIcon(icon);

        tableOfBooks.getItems().add(temp);

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

    class BookViewListCellController {
        @FXML
        private ImageView cellIcon;

        @FXML
        private Label titleLbl;

        @FXML
        private Label publisherLbl;

        @FXML
        private Label isbn13Lbl;

        @FXML
        private Label isbn10Lbl;

        @FXML
        private Label dateLbl;

        @FXML
        private Label authorsLBL;
        @FXML
        GridPane bookSCell;
        private boolean valueSet;

        BookViewListCellController() {
            valueSet = false;
            try {
                FXMLLoader parent = new FXMLLoader((getClass().getResource("/frontend/booksSearchTableCell.fxml")));
                parent.setController(this);
                bookSCell = parent.load();
            } catch (IOException ex) {
                System.err.print(ex.getMessage() + "hello");
            }
        }

        public void setCellInfo(Book book) {
            try {
                cellIcon = book.getIcon();
                titleLbl.setText(book.getTitle());
                publisherLbl.setText(book.getPublisherInstance().getTitle());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                dateLbl.setText(df.format(book.getDatePublished().getTime()));
                authorsLBL.setText(book.getAuthors().toString());
                isbn10Lbl.setText(book.getIsbn().get("ISBN 10"));
                isbn13Lbl.setText(book.getIsbn().get("ISBN 13"));


            } catch (Exception ex) {
                System.out.println("" + ex.getMessage());
            }
            valueSet = true;
        }

        public GridPane getCell() {
            if (valueSet) {
                return bookSCell;
            }
            return null;
        }
    }
}
