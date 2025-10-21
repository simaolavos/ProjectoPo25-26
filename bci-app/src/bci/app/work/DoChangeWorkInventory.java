package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.InvalidInventoryException;
import bci.exceptions.WorkUnknownException;
import bci.app.exceptions.NoSuchWorkException;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
        addIntegerField("workId", Prompt.workId());
        addIntegerField("quantity", Prompt.amountToUpdate());
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.inventoryChange(integerField("workId"), integerField("quantity"));
        } catch(WorkUnknownException e) {
            throw new NoSuchWorkException(integerField("workId"));
        }catch(InvalidInventoryException e) {
            _display.popup(Message.notEnoughInventory(integerField("workId"), integerField("quantity")));
        }
    }

}
