package seedu.address.logic.commands.deckcommands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;

/**
 * Selects a deck to operate on.
 */
public class UnselectDeckCommand extends Command {

    public static final String COMMAND_WORD = "unselectDeck";

    public static final String MESSAGE_SUCCESS = "The deck has been unselected.";

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.unselectDeck();
        return new CommandResult(MESSAGE_SUCCESS, false, false, false, false,
                false, true);
    }
}
