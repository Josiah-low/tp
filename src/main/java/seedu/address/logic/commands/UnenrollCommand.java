package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TutInfo;

/**
 * Unenrolls a person from a course.
 */
public class UnenrollCommand extends Command {

    public static final String COMMAND_WORD = "unenroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Unenrolls a person from a course.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_COURSE + "COURSE_CODE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_COURSE + "CS2103T";

    public static final String MESSAGE_SUCCESS = "Unenrolled Person from: %1$s";
    public static final String MESSAGE_NOT_ENROLLED = "This person is not enrolled in %1$s!";

    private final Index index;
    private final String courseCodeToRemove;

    /**
     * Creates an UnenrollCommand to remove {@code courseCodeToRemove} from the person at {@code index}.
     */
    public UnenrollCommand(Index index, String courseCodeToRemove) {
        requireNonNull(index);
        requireNonNull(courseCodeToRemove);
        this.index = index;
        this.courseCodeToRemove = courseCodeToRemove;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        boolean isEnrolled = personToEdit.getTutInfos().stream()
                .anyMatch(tut -> tut.getCourseCode().equals(courseCodeToRemove));
        if (!isEnrolled) {
            throw new CommandException(String.format(MESSAGE_NOT_ENROLLED, courseCodeToRemove));
        }

        List<TutInfo> updatedTutInfos = new ArrayList<>(personToEdit.getTutInfos());
        updatedTutInfos.removeIf(tut -> tut.getCourseCode().equals(courseCodeToRemove));

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTelegram(), personToEdit.getTags(),
                updatedTutInfos
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setPersonToShow(editedPerson); // Always show the unenrolled person

        return new CommandResult(String.format(MESSAGE_SUCCESS, courseCodeToRemove));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof UnenrollCommand)) {
            return false;
        }
        UnenrollCommand e = (UnenrollCommand) other;
        return index.equals(e.index) && courseCodeToRemove.equals(e.courseCodeToRemove);
    }
}
