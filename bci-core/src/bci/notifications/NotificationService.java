package bci.notifications;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.io.Serializable;

import bci.work.Work;

public class NotificationService implements Serializable {

	private final Map<Integer, List<NotificationListener>> _listeners = new HashMap<>();

	public void subscribe(Work work, NotificationListener listener) {
		int workId = work.getId();

		List<NotificationListener> listeners = _listeners.get(workId);

		if (listeners == null) {
			listeners = new ArrayList<>();
			_listeners.put(workId, listeners);
		}

		listeners.add(listener);
	}

	public void unsubscribe(Work work, NotificationListener listener) {
		List<NotificationListener> listeners = _listeners.get(work.getId());

		if (listeners != null) {
			listeners.remove(listener);

			if (listeners.isEmpty()) {
				_listeners.remove(work.getId());
			}
		}
	}

	public void notify(Notification notification) {

		List<NotificationListener> NotificationListeners = _listeners.get(notification.getWork().getId());

		if (NotificationListeners != null) {
			for (NotificationListener listener : NotificationListeners) {
				if (listener != null)
					listener.updateNotifications(notification);
			}
		}
	}
}
