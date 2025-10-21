package bci.user;

import java.io.Serializable;
import bci.DisplayEntity;
import bci.work.Work;
import bci.notifications.Notification;
import bci.notifications.NotificationListener;

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import bci.exceptions.NoSuchRequestException;

public class User implements Serializable, Comparable<User>, NotificationListener {
	private int _id;
	private String _name;
	private String _email;
	private Map<Integer, Request> _requests = new HashMap<>();
	private List<Notification> _notifications = new ArrayList<>();
	private boolean _suspended;
	private int _onTimeStreak = 0;
	private int _lateStreak = 0;
	private Behaviour _behaviour = new NormalBehaviour(this);
	private int _currentFine = 0;


	// Constructor with only id, name, and email
	public User(int id, String name, String email) {
		_id = id;
		_name = name;
		_email = email;
	}

	// Getters and Setters
	public int getId() {
		return _id;
	}

	public void setId(int id) {
		_id = id;
	}

	public int getRequestSize() {
		return _requests.size();
	}

	public String getName() {
		return _name;
	}

	public void setName(String name) {
		_name = name;
	}

	public String getEmail() {
		return _email;
	}

	public void setEmail(String email) {
		_email = email;
	}

	public boolean isSuspended() {
		return _suspended;
	}

	public void setSuspended(boolean suspended) {
		_suspended = suspended;
	}

	public int getOnTimeStreak() {
		return _onTimeStreak;
	}

	public void setOnTimeStreak(int streak) {
		_onTimeStreak = streak;
	}

	public int getLateStreak() {
		return _lateStreak;
	}

	public void setLateStreak(int streak) {
		_lateStreak = streak;
	}

	public Request getRequest(int workId) {
		return _requests.get(workId);
	}

	public int createRequest(Work work, int date) {
		int dueDate = date + _behaviour.calculateDueDate(work.getTotalQuantity());
		Request request = new Request(work, dueDate, date);
		_requests.put(work.getId(), request);
		return dueDate;
	}

	public void removeRequest(int workId) {
		_requests.remove(workId);
	}

	public void setBehaviour(Behaviour behaviour) {
		_behaviour = behaviour;
	}

	public Behaviour getBehaviour() {
		return _behaviour;
	}

	public int getMaxRequests() {
		return _behaviour.getMaxRequests();
	}

	public boolean hasPriceLimitation() {
		return _behaviour.hasPriceLimitation();
	}

	public int calculateFine(Request request, int currentDay) {

		int due = request.getDueDate();
		int daysLate = Math.max(0, currentDay - due);
		return daysLate * 5;
	}

	public void updateStatus(int currentDay) {
		boolean hasOverdueWork = _requests.values().stream()
				.anyMatch(request -> currentDay > request.getDueDate());

		_suspended = hasOverdueWork || _currentFine > 0;
	}

	public int getFine() {
		return _currentFine;
	}

	public void payFine(int currentDay) {
		_currentFine = 0;

		updateStatus(currentDay);
	}

	public List<Notification> getNotifications() {
		return _notifications;
	}

	public void viewNotifications() {
		_notifications.clear();
	}


	public int returnWork(int workId, int currentDay) throws NoSuchRequestException {
		Request request = _requests.get(workId);

		if (request == null)
			throw new NoSuchRequestException();

		boolean returnedOnTime = currentDay <= request.getDueDate();

		int fine = calculateFine(request, currentDay);

		_currentFine += fine;

		_requests.remove(workId);


        _onTimeStreak = returnedOnTime ? _onTimeStreak + 1 : 0;
		_lateStreak = returnedOnTime ? 0 : _lateStreak + 1;

		updateStatus(currentDay);
		_behaviour.update();
		return _currentFine;
	}

	public void payFineByWork(int fine, int currentDay) {
		_currentFine -= fine;
		updateStatus(currentDay);

	}

	public String accept(DisplayEntity display) {
		return display.displayUser(this);
	}

    @Override
    public void updateNotifications(Notification notification) {
        _notifications.add(notification);
    }

	@Override
	public int compareTo(User user) {
		int compareName = _name.compareTo(user._name);
		if (compareName == 0)
			return _id - user._id;

		return compareName;

	}

}
