package bci.notifications;

import java.io.Serializable;
import bci.notifications.Notification;
import bci.work.Work;
import bci.DisplayEntity;

public class RequestNotification extends Notification {

	public RequestNotification(Work work) {
		super(work);
	}

	public String accept(DisplayEntity display) {
		return display.displayRequestNotification(this);
	}
}
