package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.DisplayText;
import bci.exceptions.UserUnknownException;
import bci.app.exceptions.NoSuchUserException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

	DoShowUserNotifications(LibraryManager receiver) {
		super(Label.SHOW_USER_NOTIFICATIONS, receiver);
		addIntegerField("id", Prompt.userId());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.showNotifications(integerField("id")).forEach(a -> _display.addLine(a.accept(new DisplayText())));
			_receiver.clearNotifications(integerField("id"));
		} catch (UserUnknownException e) {
			throw new NoSuchUserException(integerField("id"));
		}
	}

}
