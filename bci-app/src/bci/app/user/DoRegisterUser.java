package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.UserRegistrationFailedException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

	DoRegisterUser(LibraryManager receiver) {
		super(Label.REGISTER_USER, receiver);
		addStringField("name", Prompt.userName());
		addStringField("email", Prompt.userEMail());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			var id = _receiver.registerUser(stringField("name"), stringField("email"));
			_display.popup(Message.registrationSuccessful(id));
		} catch (bci.exceptions.UserRegistrationFailedException e) {
			throw new bci.app.exceptions.UserRegistrationFailedException(stringField("name"), stringField("email"));
		}

	}

}
