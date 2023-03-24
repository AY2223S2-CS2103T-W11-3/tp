package seedu.powercards.logic.commands;

import static seedu.powercards.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.powercards.logic.commands.CommandTestUtil.showCardAtIndex;
import static seedu.powercards.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.powercards.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.powercards.model.Model;
import seedu.powercards.model.ModelManager;
import seedu.powercards.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListCommand.
 */
public class ListCommandTest {

    private Model model;
    private Model expectedModel;

    @BeforeEach
    public void setUp() {
        model = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
        expectedModel = new ModelManager(model.getMasterDeck(), new UserPrefs());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showCardAtIndex(model, INDEX_FIRST);
        assertCommandSuccess(new ListCommand(), model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
