package bci.app.user;

import bci.app.DisplayText;
import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.2.4. Show all users.
 */
class DoShowUsers extends Command<LibraryManager> {

	DoShowUsers(LibraryManager receiver) {
		super(Label.SHOW_USERS, receiver);
	}

	@Override
	protected final void execute() {
		_receiver.showUsers().forEach(a -> _display.addLine(a.accept(new DisplayText())));
	}

}
