package Models.frontend;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;

import java.util.Objects;
import java.util.function.UnaryOperator;

public class LimitedTextField extends TextField {

    private final IntegerProperty maxLength;

    public LimitedTextField() {
        super();
        this.maxLength = new SimpleIntegerProperty(0);
    }


    public final Integer getMaxLength() {
        return this.maxLength.getValue();
    }

    public final void setMaxLength(Integer maxLength) {
        Objects.requireNonNull(maxLength, "Max length cannot be null, -1 for no limit");
        this.maxLength.setValue(maxLength);
        this.checkMaxLength();
    }


    private void checkMaxLength() {

        UnaryOperator<TextFormatter.Change> rejectChange = change -> {
                if (change.isContentChange()) {

                    if (change.getControlNewText().length() > this.getMaxLength()) {

                        this.setStyle("-fx-focus-color: red;");

                        return null;
                    }
                }

            this.setStyle("-fx-text-inner-color: black;");

            return change;

        };
        this.setTextFormatter(new TextFormatter(rejectChange));



    }


}