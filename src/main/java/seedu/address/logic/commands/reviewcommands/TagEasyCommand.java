package seedu.address.logic.commands.reviewcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.tag.Tag.TagName.EASY;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.tag.Tag;

/**
 * The TagEasyCommand class is responsible for tagging the
 * current card being reviewed with the EASY tag.
 */
public class TagEasyCommand extends Command {
    public static final String COMMAND_WORD = "l";

    public static final String MESSAGE_SUCCESS = "Card is tagged with %1$s!";

    private final Tag tag;

    /**
     * Constructs a new TagEasyCommand object with the specified tag name.
     */
    public TagEasyCommand() {
        this.tag = new Tag(EASY);
    }

    /**
     * Executes the tagging operation by calling the tagCurrentCardInReview() method of the Model object.
     *
     * @param model the Model object containing the cards to be tagged
     * @return a CommandResult object with a success message
     * @throws CommandException if an error occurs during the execution of the command
     */
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.tagCurrentCardInReview(this.tag);
        return new CommandResult(String.format(MESSAGE_SUCCESS, this.tag.tagName));
    }
}
