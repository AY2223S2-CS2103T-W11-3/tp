package seedu.address.logic.commands.cardcommands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.cardcommands.AddCommand.MESSAGE_NO_SELECTED_DECK;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.commandresult.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.card.CardInDeckPredicate;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;
import seedu.address.model.deck.Deck;

/**
 * Finds and lists all cards in the selected deck that contain any of the argument keywords.
 * Keyword matching is case-insensitive.
 */
public class FindCardCommand extends Command {

    public static final String COMMAND_WORD = "findCard";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds all cards that contain any of "
            + "the specified keywords (case-insensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " gravity constant";

    private final QuestionContainsKeywordsPredicate cardPredicate;

    /**
     * Constructs a FindCardCommand with the given list of keywords.
     * The command will search for cards in the selected deck that contain any of the keywords.
     * @param keywords the list of keywords to search for
     */
    public FindCardCommand(List<String> keywords) {
        this.cardPredicate = new QuestionContainsKeywordsPredicate(keywords);
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        Optional<Deck> selectedDeck = model.getSelectedDeck();
        assert selectedDeck.isPresent() : MESSAGE_NO_SELECTED_DECK;
        model.updateFilteredCardList(cardPredicate.and(new CardInDeckPredicate(selectedDeck.get())));
        return new CommandResult(
                String.format(Messages.MESSAGE_CARDS_LISTED_OVERVIEW, model.getFilteredCardList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FindCardCommand // instanceof handles nulls
                && cardPredicate.equals(((FindCardCommand) other).cardPredicate)); // state check
    }
}
