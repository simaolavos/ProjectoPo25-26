package bci.user;

import bci. DisplayEntity;

public class WrongfulBehaviour extends Behaviour {

	public WrongfulBehaviour(User user) {
		super(user);
	}

	@Override
	public int getMaxRequests() {
		return 1;
	}

	@Override
	public void normal() {
		getUser().setBehaviour(new NormalBehaviour(getUser()));
	}

	@Override
	public void wrongful() {
	}

	@Override
	public void abiding() {
	}

	@Override
	public int calculateDueDate(int numberOfWorks) {
		return 2;
	}

	@Override
	public void update() {
		User user = getUser();
		int onTime = user.getOnTimeStreak();

		if (onTime == 3)
			this.normal();
	}

    @Override
    public String accept(DisplayEntity display){
        return display.displayWrongfulBehaviour(this);
    }

}
