package bci.app.work;

import bci.app.DisplayText;
import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.3.2. Display all works.
 */
class DoDisplayWorks extends Command<LibraryManager> {

	DoDisplayWorks(LibraryManager receiver) {
		super(Label.SHOW_WORKS, receiver);
		// FIXME maybe define fields
	}

	@Override
	protected final void execute() {
		_receiver.showWorks().forEach(a -> _display.addLine(a.accept(new DisplayText())));
	}
}
