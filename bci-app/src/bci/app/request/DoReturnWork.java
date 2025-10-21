package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.exceptions.UserUnknownException;
import bci.exceptions.WorkUnknownException;
import bci.exceptions.NoSuchRequestException;
import bci.exceptions.NoFineException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.WorkNotBorrowedByUserException;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

	DoReturnWork(LibraryManager receiver) {
		super(Label.RETURN_WORK, receiver);
		addIntegerField("userId", bci.app.user.Prompt.userId());
		addIntegerField("workId", bci.app.work.Prompt.workId());
	}

	@Override
	protected final void execute() throws CommandException {
		int userId = integerField("userId");
		int workId = integerField("workId");
		try {
			int fine = _receiver.returnWork(userId, workId);
			if (fine != 0) {
				_display.addLine(Message.showFine(userId, fine)).displayText();
				if (Form.confirm(Prompt.finePaymentChoice()))
					_receiver.payFineByWork(userId, fine);
			}
		} catch (UserUnknownException _) {
			throw new NoSuchUserException(userId);
		} catch (WorkUnknownException _) {
			throw new NoSuchWorkException(workId);
		} catch (NoSuchRequestException _) {
			throw new WorkNotBorrowedByUserException(workId, userId);
		}
	}

}
