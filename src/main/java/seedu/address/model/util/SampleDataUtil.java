package seedu.address.model.util;

import seedu.address.model.MasterDeck;
import seedu.address.model.ReadOnlyMasterDeck;
import seedu.address.model.card.Answer;
import seedu.address.model.card.Card;
import seedu.address.model.card.Question;
import seedu.address.model.deck.Deck;
import seedu.address.model.tag.Tag;

/**
 * Contains utility methods for populating {@code Deck} with sample data.
 */
public class SampleDataUtil {
    private static final Deck DEFAULT_DECK = new Deck("DEFAULT DECK");

    public static Card[] getSampleCards() { // Todo: initilize new cards?
        return new Card[]{
            new Card(new Question("What is a loop"),
                    new Answer("A construct that repeats instructions until a condition is met"),
                    new Tag("easy"), DEFAULT_DECK),
            new Card(new Question("What is a variable"),
                    new Answer("A named memory location that stores a value"),
                    new Tag("easy"), DEFAULT_DECK),
            new Card(new Question("What is the structure of an atom"),
                    new Answer("Atoms consist of a nucleus containing protons and neutrons, "
                            + "surrounded by electrons in shells or energy levels"),
                    new Tag("medium"), DEFAULT_DECK),
            new Card(new Question("What is the basic unit of life"),
                    new Answer("The cell is the basic unit of life"),
                    new Tag("easy"), DEFAULT_DECK),
            new Card(new Question("Who was the first president of the United States"),
                    new Answer("George Washington"),
                    new Tag("medium"), DEFAULT_DECK),
            new Card(new Question("When did Singapore gain independence"),
                    new Answer("9 August 1965"),
                    new Tag("hard"), DEFAULT_DECK),
        };
    }

    public static ReadOnlyMasterDeck getSampleMasterDeck() {
        MasterDeck sampleMasterDeck = new MasterDeck();
        for (Card sampleCard : getSampleCards()) {
            sampleMasterDeck.addCard(sampleCard);
        }
        sampleMasterDeck.initDecks();
        return sampleMasterDeck;
    }

}
