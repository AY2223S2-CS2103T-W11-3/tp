package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_CARDS_LISTED_OVERVIEW;
import static seedu.address.commons.core.Messages.MESSAGE_DECKS_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.ATOM;
import static seedu.address.testutil.TypicalCards.INDEPENDENCE;
import static seedu.address.testutil.TypicalCards.PRESIDENT;
import static seedu.address.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;
import seedu.address.model.deck.DeckContainsKeywordsPredicate;

/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FindCommandTest {
    private Model model = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
    private Model expectedModel = new ModelManager(getTypicalMasterDeck(), new UserPrefs());

    @Test
    public void equals() {
        QuestionContainsKeywordsPredicate firstPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("first"));
        QuestionContainsKeywordsPredicate secondPredicate =
                new QuestionContainsKeywordsPredicate(Collections.singletonList("second"));

        FindCommand findFirstCommand = new FindCommand(Collections.singletonList("first"));
        FindCommand findSecondCommand = new FindCommand(Collections.singletonList("second"));

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindCommand findFirstCommandCopy = new FindCommand(Collections.singletonList("first"));
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different card -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noDeckFound() {
        String userInput = " ";
        String expectedMessage = String.format(MESSAGE_DECKS_LISTED_OVERVIEW, 0);
        DeckContainsKeywordsPredicate predicate = prepareDeckPredicate(userInput);
        List keywords = prepareKeywords(userInput);
        FindCommand command = new FindCommand(keywords);
        expectedModel.updateFilteredDeckList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Collections.emptyList(), model.getFilteredDeckList());
    }

    @Test
    public void execute_multipleKeywords_multipleCardsFound() {
        String userInput = "atom president independence";
        String expectedMessage = String.format(MESSAGE_CARDS_LISTED_OVERVIEW, 3);
        QuestionContainsKeywordsPredicate predicate = prepareCardPredicate(userInput);
        List keywords = prepareKeywords(userInput);
        FindCommand command = new FindCommand(keywords);
        expectedModel.selectDeck(INDEX_FIRST);
        model.selectDeck(INDEX_FIRST);
        expectedModel.updateFilteredCardList(predicate);
        assertCommandSuccess(command, model, expectedMessage, expectedModel);
        assertEquals(Arrays.asList(ATOM, PRESIDENT, INDEPENDENCE), model.getFilteredCardList());
    }

    /**
     * Parses {@code userInput} into a {@code DeckContainsKeywordsPredicate}.
     */
    private DeckContainsKeywordsPredicate prepareDeckPredicate(String userInput) {
        return new DeckContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code QuestionContainsKeywordsPredicate}.
     */
    private QuestionContainsKeywordsPredicate prepareCardPredicate(String userInput) {
        return new QuestionContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+")));
    }

    /**
     * Parses {@code userInput} into a {@code List}.
     */
    private List prepareKeywords(String userInput) {
        return Arrays.asList(userInput.split("\\s+"));
    }
}
