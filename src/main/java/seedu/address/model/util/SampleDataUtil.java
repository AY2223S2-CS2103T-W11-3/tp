package seedu.address.model.util;

import java.util.Arrays;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

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
    public static Card[] getSampleCards() {
        return new Card[] {
            new Card(new Question("What is a loop"),
                    new Answer("A construct that repeats instructions until a condition is met"),
                getTagSet("Hard"), Optional.of(new Deck("Sample Deck"))),
            new Card(new Question("What is a variable"),
                new Answer("A named memory location that stores a value"),
                getTagSet("Hard"), Optional.of(new Deck("Sample Deck"))),
            new Card(new Question("What is the structure of an atom"),
                new Answer("Atoms consist of a nucleus containing protons and neutrons"
                        + ", surrounded by electrons in shells or energy levels"),
                getTagSet("Medium"), Optional.of(new Deck("Sample Deck"))),
            new Card(new Question("What is the basic unit of life"),
                new Answer("The cell is the basic unit of life"),
                getTagSet("Medium"), Optional.of(new Deck("Sample Deck"))),
            new Card(new Question("Who was the first president of the United States"),
                new Answer("George Washington"),
                getTagSet("Easy"), Optional.of(new Deck("Sample Deck"))),
            new Card(new Question("When did Singapore gain independence"),
                new Answer("9 August 1965"),
                getTagSet("Easy"), Optional.of(new Deck("Sample Deck")))
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

    /**
     * Returns a tag set containing the list of strings given.
     */
    public static Set<Tag> getTagSet(String... strings) {
        return Arrays.stream(strings)
                .map(Tag::new)
                .collect(Collectors.toSet());
    }

}
