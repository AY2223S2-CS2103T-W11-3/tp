package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.Deck;


/**
 * Starts a review session of a Deck.
 */
public class ReviewCommand extends Command {

    public static final String COMMAND_WORD = "review";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Review a deck of cards "
            + "by the index number used in the displayed deck list.\n"
            + "Parameter: INDEX (must be a positive integer).\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "Deck to be reviewed: %1$s"; // %1$s is the first argument in format
    public static final String MESSAGE_INVALID_DECK_DISPLAYED_INDEX = "Deck index provided is invalid";
    private final Index deckIndex;

    /**
     * Creates a ReviewCommand with the specified index of the deck.
     */
    public ReviewCommand(Index idx) {
        requireNonNull(idx);
        this.deckIndex = idx;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        List<Deck> deckList = model.getFilteredDeckList();
        boolean isIndexOutOfBound = deckIndex.getZeroBased() >= deckList.size();
        if (isIndexOutOfBound) {
            throw new CommandException(MESSAGE_INVALID_DECK_DISPLAYED_INDEX);
        }

        model.reviewDeck(deckIndex);
        return new CommandResult(String.format(MESSAGE_SUCCESS, model.getReviewDeckName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReviewCommand // instanceof handles nulls
                && deckIndex.equals(((ReviewCommand) other).deckIndex));
    }
}
