package bci.app.work;

import bci.app.DisplayText;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.WorkUnknownException;
import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

	DoDisplayWork(LibraryManager receiver) {
		super(Label.SHOW_WORK, receiver);
		addIntegerField("id", Prompt.workId());
	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_display.addLine(_receiver.showWork(integerField("id")).accept(new DisplayText()));
		} catch (WorkUnknownException _) {
			throw new NoSuchWorkException(integerField("id"));
		}
	}
}
