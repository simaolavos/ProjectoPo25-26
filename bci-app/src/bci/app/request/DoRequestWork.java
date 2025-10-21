package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.BorrowingRuleFailedException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.exceptions.RuleNotMetException;
import bci.exceptions.UserUnknownException;
import bci.exceptions.WorkUnknownException;

/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

	DoRequestWork(LibraryManager receiver) {
		super(Label.REQUEST_WORK, receiver);
		addIntegerField("userId", bci.app.user.Prompt.userId());
		addIntegerField("workId", bci.app.work.Prompt.workId());
	}

	@Override
	protected final void execute() throws CommandException {
		int userId = integerField("userId");
		int workId = integerField("workId");
		try {
			var returnDay = _receiver.makeRequest(userId, workId);
			_display.popup(Message.workReturnDay(workId, returnDay));
		} catch (UserUnknownException e) {
			throw new NoSuchUserException(userId);
		} catch (WorkUnknownException e) {
			throw new NoSuchWorkException(workId);
		} catch (RuleNotMetException e) {
			if (e.getRuleID() == 3) {
				if (Form.confirm(Prompt.returnNotificationPreference())) {
					_receiver.subscribeAvailabilityNotification(userId, workId);
				}
			} else {
				throw new BorrowingRuleFailedException(userId, workId, e.getRuleID());
			}
		}
	}

}
