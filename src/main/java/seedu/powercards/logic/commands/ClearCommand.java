package seedu.powercards.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.powercards.model.MasterDeck;
import seedu.powercards.model.Model;

/**
 * Clears the master deck.
 */
public class ClearCommand extends Command { //todo: this command is dangerous and should warn users in the future

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "All decks have been cleared!";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.setMasterDeck(new MasterDeck());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
