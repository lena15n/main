package seedu.address.logic.commands;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import seedu.address.commons.core.LogsCenter;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

/**
 *  Changes tag color mode in address book
 */
public class ToggleTagColorCommand extends Command {

    public static final String COMMAND_WORD = "tagcolor";
    public static final String MESSAGE_SUCCESS = "TagColor set to ";

    private final Logger logger = LogsCenter.getLogger(ToggleTagColorCommand.class);

    private boolean toSet;
    private String tag;
    private String color;
    private String message;

    public ToggleTagColorCommand(boolean toSet, String tag, String color) {
        this.toSet = toSet;
        this.tag = tag;
        this.color = color;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.setTagColor(toSet, tag, color);
        model.resetData(model.getAddressBook());
        logger.fine("Tag color set to " + (toSet ? "random" : "off"));
        if (!"".equals(tag) && !(containsTag(model.getAddressBook().getTagList(), tag))) {
            message = "No such tag";
        } else if (tagCustomized(tag, color)) {
            message = tag + " tag set to " + color;
        } else {
            message = String.format("%s%s", MESSAGE_SUCCESS, toSet ? "random" : "off");
        }
        return new CommandResult(message);
    }

    @Override
    public boolean equals(Object other) {
        // Check if
        // (a) Object is the same object
        // (b) Object is an instance of the object and that toSet, tag and color are the same
        return other == this // short circuit if same object
                || (other instanceof ToggleTagColorCommand // instanceof handles nulls
                && this.tag.equals(((ToggleTagColorCommand) other).tag))
                && this.color.equals(((ToggleTagColorCommand) other).color)
                && (this.toSet == (((ToggleTagColorCommand) other).toSet)); // state check
    }

    private boolean tagCustomized(String tag, String color) {
        return !"".equals(tag) && !"".equals(color);
    }

    /**
     * Helper method to check if the tagList contains such a tag name
     * @param tagList
     * @param tagString
     */
    private boolean containsTag(ObservableList<Tag> tagList, String tagString) {

        for (Tag tag : tagList) {
            if (tag.tagName.equals(tagString)) {
                return true;
            }
        }
        return false;
    }
}
