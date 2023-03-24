package seedu.powercards.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.powercards.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.powercards.logic.parser.CliSyntax.PREFIX_DECK_NAME;

import java.util.stream.Stream;

import seedu.powercards.commons.core.index.Index;
import seedu.powercards.logic.commands.deckcommands.EditDeckCommand;
import seedu.powercards.logic.parser.exceptions.ParseException;
import seedu.powercards.model.deck.Deck;

/**
 * Parses input arguments and creates a new EditDeckCommand object
 */
public class EditDeckCommandParser implements Parser<EditDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the EditDeckCommand
     * and returns an EditDeckCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public EditDeckCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args, PREFIX_DECK_NAME);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    EditDeckCommand.MESSAGE_USAGE), pe);
        }

        if (!arePrefixesPresent(argMultimap, PREFIX_DECK_NAME)) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE));
        }

        String deckName = argMultimap.getValue(PREFIX_DECK_NAME).get();
        Deck editedDeck = ParserUtil.parseDeck(deckName);

        return new EditDeckCommand(index, editedDeck);
    }

    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
