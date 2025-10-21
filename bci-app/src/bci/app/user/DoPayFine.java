package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.exceptions.NoFineException;
import bci.exceptions.UserUnknownException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;

/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);
        addIntegerField("userId", Prompt.userId());
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = integerField("userId");
        try {
            _receiver.payFine(userId);
        } catch (UserUnknownException e){
            throw new NoSuchUserException(userId);
        } catch(NoFineException e) {
            throw new UserIsActiveException(userId);
        }
    }

}
