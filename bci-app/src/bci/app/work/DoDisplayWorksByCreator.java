package bci.app.work;

import bci.app.DisplayText;
import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import bci.exceptions.CreatorUnknownException;
import bci.exceptions.WorkUnknownException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

	DoDisplayWorksByCreator(LibraryManager receiver) {
		super(Label.SHOW_WORKS_BY_CREATOR, receiver);
		addStringField("name", Prompt.creatorId());

	}

	@Override
	protected final void execute() throws CommandException {
		try {
			_receiver.showCreatorWorks(stringField("name")).forEach(a -> _display.addLine(a.accept(new DisplayText())));
		} catch (CreatorUnknownException | WorkUnknownException e) {
			throw new NoSuchCreatorException(stringField("name"));
		}
	}

}
