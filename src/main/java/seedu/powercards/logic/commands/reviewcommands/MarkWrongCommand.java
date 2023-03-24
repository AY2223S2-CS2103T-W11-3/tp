package seedu.powercards.logic.commands.reviewcommands;

import static java.util.Objects.requireNonNull;

import seedu.powercards.logic.commands.Command;
import seedu.powercards.logic.commands.CommandResult;
import seedu.powercards.model.Model;

/**
 * Marks the current card under review as wrong.
 */
public class MarkWrongCommand extends Command {

    public static final String COMMAND_WORD = ";";

    public static final String MESSAGE_SUCCESS = "You'll get it next time! Marked current card as incorrect.";

    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.markWrong();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
