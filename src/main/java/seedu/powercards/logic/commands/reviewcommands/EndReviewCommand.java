package seedu.powercards.logic.commands.reviewcommands;

import static java.util.Objects.requireNonNull;

import seedu.powercards.logic.commands.Command;
import seedu.powercards.logic.commands.CommandResult;
import seedu.powercards.model.Model;

/**
 * Ends current review session.
 */
public class EndReviewCommand extends Command {

    public static final String COMMAND_WORD = "endReview";

    public static final String MESSAGE_SUCCESS = "Ended the review.";


    @Override
    public CommandResult execute(Model model) {
        requireNonNull(model);
        model.endReview();
        return new CommandResult(MESSAGE_SUCCESS,
                false, false, false, true, false, false);
    }
}
