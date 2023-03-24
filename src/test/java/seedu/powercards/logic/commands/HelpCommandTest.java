package seedu.powercards.logic.commands;

import static seedu.powercards.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.powercards.logic.commands.HelpCommand.SHOWING_HELP_MESSAGE;

import org.junit.jupiter.api.Test;

import seedu.powercards.model.Model;
import seedu.powercards.model.ModelManager;

public class HelpCommandTest {
    private Model model = new ModelManager();
    private Model expectedModel = new ModelManager();

    @Test
    public void execute_help_success() {
        CommandResult expectedCommandResult = new CommandResult(
                SHOWING_HELP_MESSAGE, true, false, false, false, false, false);
        assertCommandSuccess(new HelpCommand(), model, expectedCommandResult, expectedModel);
    }
}
