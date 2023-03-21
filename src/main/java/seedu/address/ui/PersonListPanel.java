package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.card.Card;

/**
 * Panel containing the list of persons.
 */
public class PersonListPanel extends UiPart<Region> {
    private static final String FXML = "PersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(PersonListPanel.class);

    @FXML
    private ListView<Card> personListView;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public PersonListPanel(ObservableList<Card> cardList) {
        super(FXML);
        personListView.setItems(cardList);
        personListView.setCellFactory(listView -> new PersonListViewCell());

        personListView.setStyle("-fx-background-color: #ededed; "
                + "-fx-background-radius: 30; -fx-border-radius: 30; -fx-border-width: 5;");
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code ReviewCard} using a {@code PersonCard}.
     */
    static class ReviewCardListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);
            if (empty || card == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(new ReviewCard(card).getRoot());
                setStyle("-fx-border-insets: 10px; -fx-background-insets: 10px; -fx-padding: 10 80 10 80; "
                        + "-fx-background-color:#ededed ");
            }
        }
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code Card} using a {@code PersonCard}.
     */
    static class PersonListViewCell extends ListCell<Card> {
        @Override
        protected void updateItem(Card card, boolean empty) {
            super.updateItem(card, empty);
            if (empty || card == null) {
                setGraphic(null);
                setText(null);

            } else {
                setGraphic(new PersonCard(card, getIndex() + 1).getRoot());
                setStyle("-fx-border-insets: 10px; -fx-background-insets: 10px; -fx-padding: 10 80 10 80; "
                        + "-fx-background-color:#ededed ");
            }
        }
    }

}
