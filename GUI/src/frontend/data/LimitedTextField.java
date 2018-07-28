package frontend.data;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.paint.Color;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class LimitedTextField extends TextField {

    private final IntegerProperty maxLength;
    private Label counter;

    public LimitedTextField() {
        super();
        this.maxLength = new SimpleIntegerProperty(0);
        counter = null;
    }


    public final Integer getMaxLength() {
        return this.maxLength.getValue();
    }

    public final void setMaxLength(Integer maxLength) {
        Objects.requireNonNull(maxLength, "Max length cannot be null, -1 for no limit");
        this.maxLength.setValue(maxLength);
        this.checkMaxLength();
    }

    public void setCounter(Label counter){
        this.counter = counter;
        counter.setTextFill(Color.web("#0076a3"));

    }

    private void maxCharLabel(){
        if (counter != null)
            counter.setTextFill(Color.web("#FF0000"));


    }

    private void checkMaxLength() {

        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
            if (change.isContentChange()) {
//                if (counter !=null)
//                    this.counter.setVisible(true);
                if (change.getControlNewText().length() > this.getMaxLength()) {
                    if(counter!=null)
                        maxCharLabel();
                    this.setStyle("-fx-focus-color: red;");

                    return null;
                }
            }
            if(counter!=null)
            {
//                this.counter.setVisible(false);
                counter.setTextFill(Color.web("#0076a3"));
            }
            this.setStyle("-fx-text-inner-color: black;");

            return change;
        };
        this.setTextFormatter(new TextFormatter(rejectChange));
//        if(counter!=null)
//            this.counter.setVisible(false);


    }


}