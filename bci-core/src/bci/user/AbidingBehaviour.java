package bci.user;

import bci.DisplayEntity;

public class AbidingBehaviour extends Behaviour {

	public AbidingBehaviour(User user) {
		super(user);
	}

	@Override
	public int getMaxRequests() {
		return 5;
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
	public boolean hasPriceLimitation() {
		return false;
	}

	@Override
	public int calculateDueDate(int numberOfWorks) {
		if (numberOfWorks == 1)
			return 8;
		else if (numberOfWorks <= 5)
			return 15;
		else
			return 30;
	}

	@Override
	public void update() {
		User user = getUser();
		int onTime = user.getOnTimeStreak();

		if (onTime == 0)
			this.normal();
	}

    @Override
    public String accept(DisplayEntity display){
        return display.displayAbidingBehaviour(this);
    }
}
