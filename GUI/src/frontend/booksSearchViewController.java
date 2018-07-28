package frontend;

import com.mbox.BookAPI.Book;
import com.mbox.BookAPI.BookAPI;
import frontend.data.LimitedTextField;
import frontend.data.Publisher;
import javafx.animation.FadeTransition;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;
import org.json.JSONException;

import javax.management.timer.Timer;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Objects;


public class booksSearchViewController {

    @FXML
    private LimitedTextField searchTextF;

    @FXML
    private Button searchBtn;
    @FXML
    ListView<Book> tableOfBooks;
    @FXML
    GridPane scene;
    private Image icon;
    private boolean debugging;


    @FXML
    void initialize() {
        String programIconImf = "/frontend/media/no_cover_thumb.png";
        icon = new Image(this.getClass().getResource(programIconImf).toString());
        debugging = false;
        setTableOFBooksCellProperty();
//        addFakeBook();
        setSearch();
        searchTextF.setMaxLength(60);
    }

    private void setSearch() {
        searchBtn.setOnMouseClicked(e -> search(searchTextF.getText()));
        scene.setOnKeyPressed(e -> {
            if (e.getCode().equals(KeyCode.ENTER)) {
                search(searchTextF.getText());
            }
        });

    }

    private void addFakeBook() {
        Publisher tempPublisher = new Publisher();
        tempPublisher.setName("Person");

        ArrayList<String> tempAuthors = new ArrayList<>();
        tempAuthors.add("Rajashow");
        tempAuthors.add("MAMALEE");
        tempAuthors.add("DEVIL OF THE WEST");
        Book temp = new Book("Green Eggs and Ham", tempAuthors, null, "BOB", 5);
        temp.setDatePublished("March 20199");
        temp.setPublisherInstance(tempPublisher);
        temp.setIcon(icon);
        tableOfBooks.getItems().add(temp);

    }

    private void setTableOFBooksCellProperty() {
        tableOfBooks.setCellFactory(param -> new ListCell<Book>() {
                    {
                        prefWidthProperty().bind(tableOfBooks.widthProperty());
                        setMaxWidth(Double.MAX_VALUE);
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
                            this.runAnimation(cell);
                        }
                    }

                    void runAnimation(GridPane cell) {
                        System.out.println(this.getParent().getParent().getParent());
                        FadeTransition ft = new FadeTransition(Duration.millis(3000), this);
                        ft.setFromValue(0.0);
                        ft.setToValue(1.0);
                        ft.play();


                    }

                }
        );
    }

    private void search(String searchQuery) {
//        call the api using this query
        if (searchQuery != null && !searchQuery.isEmpty()) {
            try {
                tableOfBooks.getItems().clear();
                tableOfBooks.getItems().addAll(Objects.requireNonNull(BookAPI.search(searchQuery)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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

        void setCellInfo(Book book) {
            try {
                cellIcon.setImage(getValueOrDefault(book.getIcon(), icon));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- cellIcon");
            }
            try {
                titleLbl.setText(getValueOrDefault(book.getTitle(), titleLbl.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- titleLbl");
            }
            try {

                publisherLbl.setText(getValueOrDefault(book.getPublisherInstance().getName(), publisherLbl.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- publisherLbl");
            }
            try {
                dateLbl.setText(getValueOrDefault(book.getDatePublished(), dateLbl.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- dateLbl");
            }
            try {
                String authors = book.getAuthors().isEmpty() ? null : book.getAuthors().toString();

                authorsLBL.setText(getValueOrDefault(authors, authorsLBL.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- authorsLBL");
            }
            try {
                isbn10Lbl.setText(getValueOrDefault(book.getIsbn().get("ISBN_10"), isbn10Lbl.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- isbn10Lbl");
            }
            try {
                isbn13Lbl.setText(getValueOrDefault(book.getIsbn().get("ISBN_13"), isbn13Lbl.getText()));
            } catch (Exception e) {
                if (debugging)
                    System.out.println(e.getMessage() + " error -- isbn13Lbl");
            }

            valueSet = true;
        }

        GridPane getCell() {
            if (valueSet) {
                return bookSCell;
            }
            return null;
        }
    }

    private static <T> T getValueOrDefault(T value, T defaultValue) {
        return value == null ? defaultValue : value;
    }
}
