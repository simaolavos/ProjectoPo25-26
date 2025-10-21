package bci.user;

import java.io.Serializable;
import bci.DisplayEntity;

public abstract class Behaviour implements Serializable {
	private User _user;

	public Behaviour(User user) {
		_user = user;
	}

	public abstract int getMaxRequests();

	public abstract void normal();

	public abstract void wrongful();

	public abstract void abiding();

	public abstract int calculateDueDate(int numberOfWorks);

	public abstract void update();

	public boolean hasPriceLimitation() {
		return true;
	}

	public User getUser() {
		return _user;
	}

	public abstract String accept(DisplayEntity display);

}
