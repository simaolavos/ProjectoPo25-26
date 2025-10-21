package bci.app.user;

import bci.app.DisplayText;
import bci.exceptions.UserUnknownException;
import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchUserException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

	DoShowUser(LibraryManager receiver) {
		super(Label.SHOW_USER, receiver);
		addIntegerField("id", Prompt.userId());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.addLine(_receiver.showUser(integerField("id")).accept(new DisplayText()));
		} catch (UserUnknownException e) {
			throw new NoSuchUserException(integerField("id"));
		}

	}
}
