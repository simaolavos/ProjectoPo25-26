package bci.notifications;

import java.io.Serializable;
import bci.notifications.Notification;
import bci.work.Work;
import bci.DisplayEntity;

public class AvailabilityNotification extends Notification {

	public AvailabilityNotification(Work work) {
		super(work);
	}

	public String accept(DisplayEntity display) {
		return display.displayAvailabilityNotification(this);
	}
}
