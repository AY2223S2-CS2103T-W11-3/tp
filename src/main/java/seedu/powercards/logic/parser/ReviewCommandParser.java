package seedu.powercards.logic.parser;

import static java.util.Objects.requireNonNull;
import static seedu.powercards.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import seedu.powercards.commons.core.index.Index;
import seedu.powercards.logic.commands.reviewcommands.ReviewCommand;
import seedu.powercards.logic.parser.exceptions.ParseException;

/**
 * Parses input arguments and creates a new SelectDeck object
 */
public class ReviewCommandParser implements Parser<ReviewCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the ReviewCommand
     * and returns an ReviewCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public ReviewCommand parse(String args) throws ParseException {
        requireNonNull(args);
        ArgumentMultimap argMultimap = ArgumentTokenizer.tokenize(args);

        Index index;
        try {
            index = ParserUtil.parseIndex(argMultimap.getPreamble());
        } catch (ParseException pe) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                    ReviewCommand.MESSAGE_USAGE), pe);
        }

        return new ReviewCommand(index);
    }
}
