package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_DECK_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_NO_DECK_SELECTED;
import static seedu.address.commons.core.Messages.MESSAGE_UNKNOWN_COMMAND;
import static seedu.address.testutil.Assert.assertThrows;
import static seedu.address.testutil.TypicalCards.getTypicalMasterDeck;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import seedu.address.commons.core.GuiSettings;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.deckcommands.AddDeckCommand;
import seedu.address.logic.commands.deckcommands.UnselectDeckCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.ReadOnlyMasterDeck;
import seedu.address.model.UserPrefs;
import seedu.address.model.card.Card;
import seedu.address.model.deck.Deck;
import seedu.address.storage.JsonMasterDeckStorage;
import seedu.address.storage.JsonUserPrefsStorage;
import seedu.address.storage.StorageManager;

public class LogicManagerTest {
    private static final IOException DUMMY_IO_EXCEPTION = new IOException("dummy exception");

    @TempDir
    public Path temporaryFolder;

    private Model model = new ModelManager();
    private Logic logic;
    private StorageManager storage;

    @BeforeEach
    public void setUp() {
        JsonMasterDeckStorage masterDeckStorage =
                new JsonMasterDeckStorage(temporaryFolder.resolve("masterdeck.json"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(temporaryFolder.resolve("userPrefs.json"));
        storage = new StorageManager(masterDeckStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);
    }

    @Test
    public void execute_invalidCommandFormat_throwsParseException() {
        String invalidCommand = "uicfhmowqewca";
        assertParseException(invalidCommand, MESSAGE_UNKNOWN_COMMAND);
    }

    @Test
    public void execute_commandExecutionError_throwsCommandException() { // test to delete deck, no deck selected
        String deleteDeckCommand = "deleteDeck 10";
        assertCommandException(deleteDeckCommand, MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidCommandWhenDeckNotSelected_throwsParseException() { // deck needs to be selected
        String invalidCommandWhenDeckNotSelected = UnselectDeckCommand.COMMAND_WORD;
        assertParseException(invalidCommandWhenDeckNotSelected,
                String.format(MESSAGE_NO_DECK_SELECTED, UnselectDeckCommand.COMMAND_WORD));
    }

    @Test
    public void execute_validCommandWhenDeckSelected_success() throws Exception { // select deck when deck selected
        String unselectDeck = UnselectDeckCommand.COMMAND_WORD;
        Model modelWithDeckSelected = new ModelManager(getTypicalMasterDeck(), new UserPrefs());
        modelWithDeckSelected.selectDeck(INDEX_FIRST);
        Logic logic = new LogicManager(modelWithDeckSelected, storage);
        assertLogicSuccess(unselectDeck, UnselectDeckCommand.MESSAGE_SUCCESS, logic);
    }

    @Test
    public void execute_storageThrowsIoException_throwsCommandException() {
        // Setup LogicManager with JsonMasterDeckIoExceptionThrowingStub
        JsonMasterDeckStorage addressBookStorage =
             new JsonMasterDeckIoExceptionThrowingStub(temporaryFolder.resolve("ioExceptionMasterDeck.json"));
        JsonUserPrefsStorage userPrefsStorage =
            new JsonUserPrefsStorage(temporaryFolder.resolve("ioExceptionUserPrefs.json"));
        StorageManager storage = new StorageManager(addressBookStorage, userPrefsStorage);
        logic = new LogicManager(model, storage);

        // Execute add command
        String addDeckCommand = AddDeckCommand.COMMAND_WORD + " " + "Science";
        Deck expectedDeck = new Deck("Science");
        ModelManager expectedModel = new ModelManager();
        expectedModel.addDeck(expectedDeck);
        String expectedMessage = LogicManager.FILE_OPS_ERROR_MESSAGE + DUMMY_IO_EXCEPTION;
        assertCommandFailure(addDeckCommand, CommandException.class, expectedMessage, expectedModel);
    }

    @Test
    public void getFilteredCardList_modifyList_throwsUnsupportedOperationException() {
        assertThrows(UnsupportedOperationException.class, () -> logic.getFilteredCardList().remove(0));
    }

    @Test
    public void getMasterDeck_returnsCorrectDeck() {
        ReadOnlyMasterDeck readOnlyMasterDeck = logic.getMasterDeck();
        assertEquals(model.getMasterDeck(), readOnlyMasterDeck);
    }

    @Test
    public void getFilteredCardList_returnsCorrectList() {
        List<Card> expectedList = new ArrayList<>();
        assertEquals(expectedList, logic.getFilteredCardList());
    }

    @Test
    public void getMasterDeckFilePath_returnsCorrectPath() {
        Path expectedPath = Paths.get("data", "masterdeck.json");
        assertEquals(expectedPath, logic.getMasterDeckFilePath());
    }

    @Test
    public void getGuiSettings_returnsCorrectGuiSettings() {
        assertEquals(model.getGuiSettings(), logic.getGuiSettings());
    }
    @Test
    public void setGuiSettings_setsCorrectGuiSettings() {
        GuiSettings guiSettings = new GuiSettings(100, 200, 1, 2);
        logic.setGuiSettings(guiSettings);
        assertEquals(guiSettings, logic.getGuiSettings());
    }

    /**
     * Executes the command and confirms that
     * - no exceptions are thrown <br>
     * - the feedback message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandSuccess(String inputCommand, String expectedMessage,
                                      Model expectedModel) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
        assertEquals(expectedModel, model);
    }

    private void assertLogicSuccess(String inputCommand, String expectedMessage,
                                    Logic logic) throws CommandException, ParseException {
        CommandResult result = logic.execute(inputCommand);
        assertEquals(expectedMessage, result.getFeedbackToUser());
    }

    /**
     * Executes the command, confirms that a ParseException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertParseException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, ParseException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that a CommandException is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandException(String inputCommand, String expectedMessage) {
        assertCommandFailure(inputCommand, CommandException.class, expectedMessage);
    }

    /**
     * Executes the command, confirms that the exception is thrown and that the result message is correct.
     *
     * @see #assertCommandFailure(String, Class, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage) {
        Model expectedModel = new ModelManager(model.getMasterDeck(), new UserPrefs());
        assertCommandFailure(inputCommand, expectedException, expectedMessage, expectedModel);
    }

    /**
     * Executes the command and confirms that
     * - the {@code expectedException} is thrown <br>
     * - the resulting error message is equal to {@code expectedMessage} <br>
     * - the internal model manager state is the same as that in {@code expectedModel} <br>
     *
     * @see #assertCommandSuccess(String, String, Model)
     */
    private void assertCommandFailure(String inputCommand, Class<? extends Throwable> expectedException,
                                      String expectedMessage, Model expectedModel) {
        assertThrows(expectedException, expectedMessage, () -> logic.execute(inputCommand));
        assertEquals(expectedModel, model);
    }

    /**
     * A stub class to throw an {@code IOException} when the save method is called.
     */
    private static class JsonMasterDeckIoExceptionThrowingStub extends JsonMasterDeckStorage {
        private JsonMasterDeckIoExceptionThrowingStub(Path filePath) {
            super(filePath);
        }

        @Override
        public void saveMasterDeck(ReadOnlyMasterDeck masterDeck, Path filePath) throws IOException {
            throw DUMMY_IO_EXCEPTION;
        }
    }
}
