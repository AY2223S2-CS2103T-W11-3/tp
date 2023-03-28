package seedu.address.logic.commands.deckcommands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.deck.DeckContainsKeywordsPredicate;

/**
 * Finds and list all decks in the master deck that contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindDecksCommand extends Command {
    public static final String COMMAND_WORD = "findDecks";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all decks that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " science";

    private final DeckContainsKeywordsPredicate deckPredicate;

    /**
     * Constructs a FindDecksCommand with the given list of keywords.
     * The command will search for decks that contain any of the keywords.
     * @param keywords the list of keywords to search for
     */
    public FindDecksCommand(List<String> keywords) {
        this.deckPredicate = new DeckContainsKeywordsPredicate(keywords);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        model.updateFilteredDeckList(deckPredicate);
        return new CommandResult(
                String.format(Messages.MESSAGE_DECKS_LISTED_OVERVIEW, model.getFilteredDeckList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindDecksCommand // instanceof handles nulls
                && deckPredicate.equals(((FindDecksCommand) other).deckPredicate)); // state check
    }
}
