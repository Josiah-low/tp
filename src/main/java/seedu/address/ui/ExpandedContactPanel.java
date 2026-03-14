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

    private static final String DEFAULT_MESSAGE = "Select any contact to see full details.";

    // Person whose contact details will be displayed
    private Person selectedContact;

    @FXML
    private Label label;

    /**
     * Creates a {@code PersonListPanel} with the given {@code ObservableList}.
     */
    public ExpandedContactPanel() {
        super(FXML);
        label.setText(DEFAULT_MESSAGE);
    }

    /**
     * Sets the specified person whose contact details will be shown.
     * This method is triggered when the user selects one of their contacts
     * in their contact list using their mouse. Displays default message if
     * the selected contact is null.
     *
     * @param selectedContact
     */
    public void setSelectedContact(Person selectedContact) {
        if (selectedContact == null) {
            this.selectedContact = null;
            label.setText(DEFAULT_MESSAGE);
        } else {
            this.selectedContact = selectedContact;
            label.setText(selectedContact.getName().fullName);
        }

    }
}
