package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;
import javafx.util.Pair;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The Main Window. Provides the basic application layout containing
 * a menu bar and space where other JavaFX elements can be placed.
 */
public class MainWindow extends UiPart<Stage> {

    private static Text title = new Text("PowerCard");
    private static final String FXML = "MainWindow.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;

    // Independent Ui parts residing in this Ui container
    private PersonListPanel personListPanel;
    //PLACEHOLDER FOR DECKLIST
    private UiPart<Region> leftPanel;
    private UiPart<Region> rightTitle;
    private ResultDisplay resultDisplay;
    private HelpWindow helpWindow;
    private ObservableList<Pair<String, String>> deckTitlePlaceholder = FXCollections.observableArrayList();

    @FXML
    private StackPane commandBoxPlaceholder;

    @FXML
    private MenuItem helpMenuItem;

    @FXML
    private StackPane rightPanelPlaceholder;

    @FXML
    private StackPane rightPanelTitle;

    @FXML
    private StackPane leftPanelPlaceholder;

    @FXML
    private StackPane resultDisplayPlaceholder;

    @FXML
    private StackPane statusbarPlaceholder;

    @FXML
    private TextFlow titlePanel;



    /**
     * Creates a {@code MainWindow} with the given {@code Stage} and {@code Logic}.
     */
    public MainWindow(Stage primaryStage, Logic logic) {
        super(FXML, primaryStage);

        // Set dependencies
        this.primaryStage = primaryStage;
        this.logic = logic;

        // Configure the UI
        setWindowDefaultSize(logic.getGuiSettings());

        setAccelerators();

        helpWindow = new HelpWindow();
    }

    public Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setAccelerators() {
        setAccelerator(helpMenuItem, KeyCombination.valueOf("F1"));
    }

    /**
     * Sets the accelerator of a MenuItem.
     * @param keyCombination the KeyCombination value of the accelerator
     */
    private void setAccelerator(MenuItem menuItem, KeyCombination keyCombination) {
        menuItem.setAccelerator(keyCombination);

        /*
         * TODO: the code below can be removed once the bug reported here
         * https://bugs.openjdk.java.net/browse/JDK-8131666
         * is fixed in later version of SDK.
         *
         * According to the bug report, TextInputControl (TextField, TextArea) will
         * consume function-key events. Because CommandBox contains a TextField, and
         * ResultDisplay contains a TextArea, thus some accelerators (e.g F1) will
         * not work when the focus is in them because the key event is consumed by
         * the TextInputControl(s).
         *
         * For now, we add following event filter to capture such key events and open
         * help window purposely so to support accelerators even when focus is
         * in CommandBox or ResultDisplay.
         */
        getRoot().addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            if (event.getTarget() instanceof TextInputControl && keyCombination.match(event)) {
                menuItem.getOnAction().handle(new ActionEvent());
                event.consume();
            }
        });
    }

    /**
     * Fills up all the placeholders of this window.
     */
    void fillInnerParts() {
        titlePanel.getChildren().add(title);

        Pair<String, String> header = new Pair("Current Deck:", "No deck selected!");
        deckTitlePlaceholder.add(header);
        rightTitle = new DeckNamePanel(deckTitlePlaceholder);
        rightPanelTitle.getChildren().add(rightTitle.getRoot());

        personListPanel = new PersonListPanel(logic.getFilteredCardList());
        rightPanelPlaceholder.getChildren().add(personListPanel.getRoot());

        leftPanel = new DeckListPanel(logic.getFilteredDeckList(), false);
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        resultDisplay = new ResultDisplay();
        resultDisplayPlaceholder.getChildren().add(resultDisplay.getRoot());

        StatusBarFooter statusBarFooter = new StatusBarFooter(logic.getMasterDeckFilePath());
        statusbarPlaceholder.getChildren().add(statusBarFooter.getRoot());

        CommandBox commandBox = new CommandBox(this::executeCommand);
        commandBoxPlaceholder.getChildren().add(commandBox.getRoot());
    }

    /**
     * Sets the default size based on {@code guiSettings}.
     */
    private void setWindowDefaultSize(GuiSettings guiSettings) {
        primaryStage.setHeight(guiSettings.getWindowHeight());
        primaryStage.setWidth(guiSettings.getWindowWidth());
        if (guiSettings.getWindowCoordinates() != null) {
            primaryStage.setX(guiSettings.getWindowCoordinates().getX());
            primaryStage.setY(guiSettings.getWindowCoordinates().getY());
        }
    }

    /**
     * Opens the help window or focuses on it if it's already opened.
     */
    @FXML
    public void handleHelp() {
        if (!helpWindow.isShowing()) {
            helpWindow.show();
        } else {
            helpWindow.focus();
        }
    }

    void show() {
        primaryStage.show();
    }

    /**
     * Closes the application.
     */
    @FXML
    private void handleExit() {
        GuiSettings guiSettings = new GuiSettings(primaryStage.getWidth(), primaryStage.getHeight(),
                (int) primaryStage.getX(), (int) primaryStage.getY());
        logic.setGuiSettings(guiSettings);
        helpWindow.hide();
        primaryStage.hide();
    }

    /**
     * Shows the review stats panel.
     */
    public void handleStartReview() {
        rightTitle = new DeckNamePanel(logic.getReviewDeckNameList());
        rightPanelTitle.getChildren().add(rightTitle.getRoot());

        leftPanel = new ReviewStatsPanel(logic.getReviewStatsList());
        leftPanelPlaceholder.getChildren().removeAll();
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        personListPanel.toggleReview();
    }

    /**
     * Shows the deck list panel.
     */
    public void handleEndReview() {
        leftPanel = new DeckListPanel(logic.getFilteredDeckList(), false);
        leftPanelPlaceholder.getChildren().removeAll();
        leftPanelPlaceholder.getChildren().add(leftPanel.getRoot());

        rightPanelTitle.getChildren().removeAll();
        rightTitle = new DeckNamePanel(logic.getDeckNameList());
        rightPanelTitle.getChildren().add(rightTitle.getRoot());

        personListPanel.endReview();
    }

    /**
     * Shows the deck Title.
     */
    public void handleSelectDeck() {
        rightPanelTitle.getChildren().removeAll();
        rightTitle = new DeckNamePanel(logic.getDeckNameList());
        rightPanelTitle.getChildren().add(rightTitle.getRoot());

    }

    /**
     * Hides the deck Title.
     */
    public void handleUnSelectDeck() {
        rightPanelTitle.getChildren().removeAll();
        rightTitle = new DeckNamePanel(logic.getDeckNameList());
        rightPanelTitle.getChildren().add(rightTitle.getRoot());

    }

    public PersonListPanel getPersonListPanel() {
        return personListPanel;
    }

    public UiPart<Region> getLeftPanel() {
        return leftPanel;
    }

    /**
     * Executes the command and returns the result.
     *
     * @see seedu.address.logic.Logic#execute(String)
     */
    private CommandResult executeCommand(String commandText) throws CommandException, ParseException {
        try {
            CommandResult commandResult = logic.execute(commandText);
            logger.info("Result: " + commandResult.getFeedbackToUser());
            resultDisplay.setFeedbackToUser(commandResult.getFeedbackToUser());

            if (commandResult.isShowHelp()) {
                handleHelp();
            }

            if (commandResult.isExit()) {
                handleExit();
            }

            if (commandResult.isStartReview()) {
                handleStartReview();
            }

            if (commandResult.isEndReview()) {
                handleEndReview();
            }

            if (commandResult.isSelectDeck()) {
                handleSelectDeck();
            }

            if (commandResult.isUnselectDeck()) {
                handleUnSelectDeck();
            }

            return commandResult;
        } catch (CommandException | ParseException e) {
            logger.info("Invalid command: " + commandText);
            resultDisplay.setFeedbackToUser(e.getMessage());
            throw e;
        }
    }
}
