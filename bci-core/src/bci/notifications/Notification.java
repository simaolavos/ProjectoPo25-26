package bci.notifications;

import bci.work.Work;
import bci.DisplayEntity;
import bci.user.User;
import java.io.Serializable;

public abstract class Notification implements Serializable {
	private Work _work;

	public Notification(Work work) {
		_work = work;
	}

	public Work getWork() {
		return _work;
	}

	public abstract String accept(DisplayEntity display);
}
