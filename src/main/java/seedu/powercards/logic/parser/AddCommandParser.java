package seedu.powercards.logic.parser;

import static seedu.powercards.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.powercards.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.powercards.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.powercards.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.Set;
import java.util.stream.Stream;

import seedu.powercards.logic.commands.cardcommands.AddCommand;
import seedu.powercards.logic.commands.cardcommands.AddCommand.AddCardDescriptor;
import seedu.powercards.logic.parser.exceptions.ParseException;
import seedu.powercards.model.card.Answer;
import seedu.powercards.model.card.Question;
import seedu.powercards.model.tag.Tag;

/**
 * Parses input arguments and creates a new AddCommand object
 */
public class AddCommandParser implements Parser<AddCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AddCommand
     * and returns an AddCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public AddCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
                ArgumentTokenizer.tokenize(args, PREFIX_QUESTION, PREFIX_ANSWER, PREFIX_TAG);

        if (!arePrefixesPresent(argMultimap, PREFIX_QUESTION, PREFIX_ANSWER)
                || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
        }

        Question question = ParserUtil.parseQuestion(argMultimap.getValue(PREFIX_QUESTION).get());
        Answer answer = ParserUtil.parseAnswer(argMultimap.getValue(PREFIX_ANSWER).get());
        Set<Tag> tagList = ParserUtil.parseTags(argMultimap.getAllValues(PREFIX_TAG)); // always return a Set

        AddCardDescriptor cardDescriptor = new AddCardDescriptor(question, answer, tagList);

        return new AddCommand(cardDescriptor);
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private static boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
