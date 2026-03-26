package seedu.address.logic;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import org.junit.jupiter.api.Test;

import seedu.address.model.person.Person;
import seedu.address.model.person.TutInfo;
import seedu.address.testutil.PersonBuilder;

public class MessagesTest {

    @Test
    public void format_personWithNameOnly_success() {
        Person person = new PersonBuilder().withPhone("-")
                .withEmail("-")
                .withAddress("-")
                .withTelegram("-")
                .build();

        String expectedString = person.getName()
                + "; Phone: -"
                + "; Email: -"
                + "; Address: -"
                + "; Courses: "
                + "; Tags: ";

        assertEquals(expectedString, Messages.format(person));
    }

    @Test
    public void format_personWithAllFields_success() {
        Person person = new PersonBuilder()
                .withTags("friend")
                .withTutInfos(List.of(new TutInfo("CS2101", "t10"), new TutInfo("CS2103T", "t01")))
                .build();

        String expectedString = person.getName()
            + "; Phone: " + person.getPhone()
            + "; Email: " + person.getEmail()
            + "; Address: " + person.getAddress()
            + "; Courses: [" + person.getTutInfos().get(0).toDisplayString() + "]"
            + "[" + person.getTutInfos().get(1).toDisplayString() + "]"
            + "; Tags: " + person.getTags().toArray()[0];

        assertEquals(expectedString.toString(), Messages.format(person));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_singleDuplicatePrefix_success() {
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/";
        assertEquals(expectedMessage, Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_mulitpleDuplicatePrefix_success() {
        // .collect(Collectors.toSet()) in testing method does not ensure fixed ordering
        String expectedMessageOptionOne = Messages.MESSAGE_DUPLICATE_FIELDS + "n/ " + "e/";
        String expectedMessageOptionTwo = Messages.MESSAGE_DUPLICATE_FIELDS + "e/ " + "n/";
        String errorMessage = Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_EMAIL);

        assertTrue(errorMessage.equals(expectedMessageOptionOne)
                || errorMessage.equals(expectedMessageOptionTwo));
    }

    @Test
    public void getErrorMessageForDuplicatePrefixes_repeatedDuplicateDuplicatePrefix_success() {
        String expectedMessage = Messages.MESSAGE_DUPLICATE_FIELDS + "n/";
        assertEquals(expectedMessage,
                Messages.getErrorMessageForDuplicatePrefixes(PREFIX_NAME, PREFIX_NAME));
    }
}
