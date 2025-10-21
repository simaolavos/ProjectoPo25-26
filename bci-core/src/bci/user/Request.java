package bci.user;

import bci.work.Work;
import java.io.Serializable;

public class Request implements Serializable {
	private Work _work;
	private int _dueDate;

	public Request(Work work, int dueDate, int lastUpdate) {
		_work = work;
		_dueDate = dueDate;
	}

	public int getDueDate() {
		return _dueDate;
	}

}
