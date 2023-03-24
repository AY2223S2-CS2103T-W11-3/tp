package seedu.powercards.logic.parser;

import static seedu.powercards.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.powercards.logic.commands.CommandTestUtil.DECK_DESC_SCIENCE;
import static seedu.powercards.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.jupiter.api.Test;

import seedu.powercards.logic.commands.deckcommands.EditDeckCommand;

public class EditDeckCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditDeckCommand.MESSAGE_USAGE);
    private EditDeckCommandParser parser = new EditDeckCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, DECK_DESC_SCIENCE, MESSAGE_INVALID_FORMAT);

    }

}
