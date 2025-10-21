package bci.user;

import bci.DisplayEntity;

public class NormalBehaviour extends Behaviour{

	public NormalBehaviour(User user) {
		super(user);
	}

	@Override
	public int getMaxRequests() {
		return 3;
	}

	@Override
	public void normal() {
	}

	@Override
	public void wrongful() {
		getUser().setBehaviour(new WrongfulBehaviour(getUser()));
	}

	@Override
	public void abiding() {
		getUser().setBehaviour(new AbidingBehaviour(getUser()));
	}

	@Override
	public int calculateDueDate(int numberOfWorks) {
		if (numberOfWorks == 1)
			return 3;
		else if (numberOfWorks <= 5)
			return 8;
		else
			return 15;
	}

	@Override
	public void update() {
		User user = getUser();
		int onTime = user.getOnTimeStreak();
		int late = user.getLateStreak();

		if (onTime == 5)
			this.abiding();
		else if (late == 3)
			this.wrongful();
	}

    @Override
    public String accept(DisplayEntity display){
        return display.displayNormalBehaviour(this);
    }
}
