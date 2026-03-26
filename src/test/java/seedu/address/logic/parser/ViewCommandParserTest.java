package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.jupiter.api.Test;

import seedu.address.logic.InvalidIndexMessages;
import seedu.address.logic.commands.ViewCommand;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the ViewCommandParser code. For example, inputs "1" and "1 abc" take the
 * same path through the ViewCommandParser.
 *
 * Therefore, only one representative invalid input is tested here. Path variation of all specific
 * invalid indices (empty string, multiple indices, non-integer, non-numeric, zero, negative,
 * overflow indices) occur inside ParserUtil, and are tested in {@link ParserUtilTest}.
 */
public class ViewCommandParserTest {
    private ViewCommandParser parser = new ViewCommandParser();

    @Test
    public void parse_validArgs_returnsViewCommand() {
        assertParseSuccess(parser, " 1 ", new ViewCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String expectedMessages = String.format(ViewCommand.MESSAGE_VIEW_INDEX_ERROR
                + InvalidIndexMessages.MESSAGE_INDEX_NEGATIVE + "\n%s",
                ViewCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "-3", expectedMessages);
    }

}
