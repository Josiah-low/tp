package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_COURSE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIAL;

import java.util.ArrayList;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.Person;
import seedu.address.model.person.TutInfo;

/**
 * Enrolls a person into a tutorial.
 */
public class EnrollCommand extends Command {

    public static final String COMMAND_WORD = "enroll";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Enrolls a person into a tutorial.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_COURSE + "COURSE_CODE "
            + PREFIX_TUTORIAL + "TUTORIAL_CODE\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_COURSE + "CS2103T " + PREFIX_TUTORIAL + "T01";

    public static final String MESSAGE_SUCCESS = "Enrolled Person into: %1$s %2$s";
    public static final String MESSAGE_DUPLICATE_COURSE = "This person is already enrolled in %1$s!";

    private final Index index;
    private final TutInfo tutInfoToAdd;

    /**
     * Creates an EnrollCommand to enroll the person at {@code index} into {@code tutInfoToAdd}.
     */
    public EnrollCommand(Index index, TutInfo tutInfoToAdd) {
        requireNonNull(index);
        requireNonNull(tutInfoToAdd);
        this.index = index;
        this.tutInfoToAdd = tutInfoToAdd;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);
        List<Person> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INDEX_OUT_OF_BOUNDS);
        }

        Person personToEdit = lastShownList.get(index.getZeroBased());

        boolean isAlreadyEnrolled = personToEdit.getTutInfos().stream()
                .anyMatch(tut -> tut.getCourseCode().equals(tutInfoToAdd.getCourseCode()));
        if (isAlreadyEnrolled) {
            throw new CommandException(String.format(MESSAGE_DUPLICATE_COURSE, tutInfoToAdd.getCourseCode()));
        }

        List<TutInfo> updatedTutInfos = new ArrayList<>(personToEdit.getTutInfos());
        updatedTutInfos.add(tutInfoToAdd);

        Person editedPerson = new Person(
                personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTelegram(), personToEdit.getTags(),
                updatedTutInfos
        );

        model.setPerson(personToEdit, editedPerson);
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
        model.setPersonToShow(editedPerson); // Always show the enrolled person

        return new CommandResult(String.format(MESSAGE_SUCCESS, tutInfoToAdd.getCourseCode(),
                tutInfoToAdd.getTutorialCode()));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof EnrollCommand)) {
            return false;
        }
        EnrollCommand e = (EnrollCommand) other;
        return index.equals(e.index) && tutInfoToAdd.equals(e.tutInfoToAdd);
    }
}
