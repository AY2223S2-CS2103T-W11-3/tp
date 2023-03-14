package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
//import static seedu.address.logic.parser.CliSyntax.PREFIX_DECK;

import java.util.stream.Stream;

import seedu.address.logic.commands.AddDeckCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Deck;

/**
 * Parses input arguments and creates a new AddDeckCommand object
 */
public class AddDeckCommandParser implements Parser<AddDeckCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddDeckCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args);

        Deck deck = ParserUtil.parseDeck(args);

        return new AddDeckCommand(deck);
    }

}
