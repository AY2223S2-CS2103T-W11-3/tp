package seedu.address.logic.commands.cardcommands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ShowDecksCommand.
 */
public class ShowCardsCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
        expectedModel = new ModelManager(model.getMasterDeck(), new UserPrefs());
    }

    @Test
    public void execute_cardListIsNotFiltered_showsSameCardList() {
        expectedModel.updateFilteredCardList(Model.PREDICATE_SHOW_ALL_CARDS);
        expectedModel.selectDeck(INDEX_FIRST);
        model.selectDeck(INDEX_FIRST);
        assertCommandSuccess(new ShowCardsCommand(), model, ShowCardsCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_cardListIsFiltered_showsEverything() {
        expectedModel.updateFilteredCardList(Model.PREDICATE_SHOW_ALL_CARDS);
        expectedModel.selectDeck(INDEX_FIRST);
        model.selectDeck(INDEX_FIRST);
        assertCommandSuccess(new ShowCardsCommand(), model, ShowCardsCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
