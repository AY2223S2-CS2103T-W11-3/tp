package seedu.address.ui;

import java.util.Comparator;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.card.Card;

/**
 * A UI component that displays information of a {@code Card}.
 */
public class PersonCard extends UiPart<Region> {

    private static final String FXML = "PersonListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on Deck level 4</a>
     */

    public final Card card;

    @FXML
    private HBox cardPane;
    @FXML
    private Label question;
    @FXML
    private Label id;
    @FXML
    private Label answer;
    @FXML
    private FlowPane tags;

    /**
     * Creates a {@code PersonCode} with the given {@code Card} and index to display.
     */
    public PersonCard(Card card, int displayedIndex) {
        super(FXML);
        this.card = card;
        id.setText(displayedIndex + ". ");
        question.setText(card.getQuestion().question);
        answer.setText(card.getAnswer().answer);

        card.getTags().stream()
                .sorted(Comparator.comparing(tag -> tag.tagName))
                .forEach(tag -> tags.getChildren().add(new CardTag(tag.tagName)));
    }

    static class CardTag extends Label {
        public CardTag(String name) {
            super(name);
            if (name.equals("easy")) {
                setStyle("-fx-background-color:#00FF00;");
            } else if (name.equals("medium")) {
                setStyle("-fx-background-color:#FFA500;");
            } else if (name.equals("hard")) {
                setStyle("-fx-background-color:#ff0000;");
            }
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PersonCard)) {
            return false;
        }

        // state check
        PersonCard card = (PersonCard) other;
        return id.getText().equals(card.id.getText())
                && this.card.equals(card.card);
    }
}
