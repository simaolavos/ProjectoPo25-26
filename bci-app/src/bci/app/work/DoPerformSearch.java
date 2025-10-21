package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.app.DisplayText;
import pt.tecnico.uilib.menus.Command;

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

	DoPerformSearch(LibraryManager receiver) {
		super(Label.PERFORM_SEARCH, receiver);
		addStringField("term", Prompt.searchTerm());
	}

	@Override
	protected final void execute() {
		_receiver.search(stringField("term")).forEach(a -> _display.addLine(a.accept(new DisplayText())));
	}

}
