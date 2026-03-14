package seedu.address.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.model.person.Person;

/**
 * Panel containing the full details of the selected contact.
 */
public class ExpandedContactPanel extends UiPart<Region> {
    private static final String FXML = "ExpandedContactPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(ExpandedContactPanel.class);

    private Person person;

    @FXML
    private Label label;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ExpandedContactPanel() {
        super(FXML);
        // this.person = person;
        label.setText("PLACEHOLDER");
    }

//    /**
//     * Custom {@code ListCell} that displays the graphics of a {@code Person} using a {@code PersonCard}.
//     */
//    class PersonListViewCell extends ListCell<Person> {
//        @Override
//        protected void updateItem(Person person, boolean empty) {
//            super.updateItem(person, empty);
//
//            if (empty || person == null) {
//                setGraphic(null);
//                setText(null);
//            } else {
//                setGraphic(new PersonCard(person, getIndex() + 1).getRoot());
//            }
//        }
//    }

}
