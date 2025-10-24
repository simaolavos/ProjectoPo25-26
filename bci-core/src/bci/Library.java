package bci;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

import bci.notifications.NotificationService;
import bci.notifications.Notification;
import bci.notifications.AvailabilityNotification;
import bci.notifications.RequestNotification;
import bci.notifications.NotificationListener;

import java.util.Comparator;

import bci.user.User;

import bci.work.Work;
import bci.work.Creator;
import bci.work.DVD;
import bci.work.Book;
import bci.work.Category;

import bci.exceptions.NoSuchRequestException;
import bci.exceptions.InvalidIsbnException;
import bci.exceptions.RuleNotMetException;
import bci.exceptions.UnrecognizedEntryException;
import bci.exceptions.UserRegistrationFailedException;
import bci.exceptions.CreatorUnknownException;
import bci.exceptions.InvalidInventoryException;
import bci.exceptions.WorkUnknownException;
import bci.exceptions.UserUnknownException;
import bci.exceptions.NoFineException;

import bci.rules.Rule;

import java.io.IOException;
import java.io.Serializable;
import java.io.FileReader;
import java.io.BufferedReader;

/** Class that represents the library as a whole. */
class Library implements Serializable {

	/**
	 * Serialization identifier.
	 */

	@java.io.Serial
	private static final long serialVersionUID = 202507171003L;

	/**
	 * The current system date.
	 */

	private int _date = 1;

	/**
	 * Current filename.
	 */

	private String _filename = null;

	/**
	 * All works mapped by ID.
	 */

	private final Map<Integer, Work> _works = new HashMap<>();

	/**
	 * Id counter to find the id of the next work.
	 */

	private int _currentWorkId = 1;

	/**
	 * All creators mapped by name.
	 */

	private final Map<String, Creator> _creators = new HashMap<>();

	/**
	 * All users mapped by ID.
	 */

	private final Map<Integer, User> _users = new HashMap<>();

	/**
	 * Notification Service for RequestNotification.
	 */

	private final NotificationService _requestNotificationService = new NotificationService();

	/**
	 * Notification Service for AvailabilityNotification
	 */

	private final NotificationService _availabilityNotificationService = new NotificationService();

	/**
	 * Id counter to find the id of the next User.
	 */

	private int _currentUserId = 1;

	/**
	 * Boolean to verify if the file as changed
	 */

	private boolean _changed = false;

	private List<Rule> _defaultRules;

	public Library(List<Rule> defaulRules) {
		_defaultRules = defaulRules;
	}

	/**
	 * Gets the current date of the system.
	 *
	 * @return current date of the system
	 *
	 */

	public int getDate() {
		return _date;
	}

	/**
	 * Advances the date of the system.
	 * 
	 * @param days the number of days we want to advance
	 *
	 */

	public void advanceDate(int days) {
		if (days <= 0)
			return;
		_changed = true;
		_date += days;
		_users.values().forEach(user -> user.updateStatus(_date));
	}

	/**
	 * Gets the name of the current file.
	 *
	 * @return name of the current file
	 *
	 */

	public String getFilename() {
		return _filename;
	}

	/**
	 * Sets the name of the current file.
	 *
	 * @param filename name we want to set as the current file name
	 *
	 */

	public void setFilename(String filename) {
		_filename = filename;
		_changed = true;
	}

	/**
	 * Checks if the file has changed.
	 *
	 * @return file changed boolean
	 *
	 */

	public boolean changed() {
		return _changed;
	}

	/**
	 * Sets the changed variable.
	 *
	 * @param changed a boolean to set if the file as changed
	 *
	 */

	public void setChanged(boolean changed) {
		_changed = changed;
	}

	/**
	 * Read the text input file at the beginning of the program and populates the
	 * instances of the various possible types (books, DVDs, users).
	 *
	 * @param filename name of the file to load
	 * @throws UnrecognizedEntryException
	 * @throws IOException
	 * 
	 */

	void importFile(String filename)
			throws UnrecognizedEntryException, IOException, UserRegistrationFailedException, InvalidIsbnException {
		var reader = new BufferedReader(new FileReader(filename));
		String line;
		while ((line = reader.readLine()) != null) {
			var fields = line.split("\\:");
			process(fields);
		}
		_changed = true;
		reader.close();
	}

	/**
	 * Processes a line from the input file and creates the corresponding instance
	 * based on the entry type.
	 *
	 * @param fields array containing the data fields of the entry
	 * @throws UnrecognizedEntryException
	 * @throws UserRegistrationFailedException
	 *
	 */

	public void process(String[] fields)
			throws UnrecognizedEntryException, UserRegistrationFailedException, InvalidIsbnException {
		switch (fields[0]) {
			case "DVD" -> createDVD(fields);
			case "BOOK" -> createBook(fields);
			case "USER" -> processUser(fields);
			default -> throw new UnrecognizedEntryException(fields[0]);
		}
	}

	/**
	 * Processes the user data entry and registers a new user with the given name
	 * and email.
	 *
	 * @param fields array containing user data
	 * @throws UserRegistrationFailedException
	 *
	 */

	public void processUser(String... fields) throws UserRegistrationFailedException {
		String name = fields[1], email = fields[2];
		registerUser(name, email);
	}

	/**
	 * Creates a new DVD instance and adds it to the collection of works.
	 *
	 * @param fields array containing DVD data
	 *
	 */

	public void createDVD(String... fields) {
		String title = fields[1].trim(), directorName = fields[2], igac = fields[5];
		Category category = Category.valueOf(fields[4]);
		int price = Integer.parseInt(fields[3]), quantity = Integer.parseInt(fields[6]);

		Creator director = parseCreator(directorName, _currentWorkId);

		_changed = true;
		DVD newDVD = new DVD(_currentWorkId, title, price, category, quantity, director, igac);
		director.addWork(newDVD);
		_works.put(_currentWorkId++, newDVD);
	}

	/**
	 * Parses information about a creator, returning an existing creator if found or
	 * creating a new one otherwise.
	 *
	 * @param name creator’s name
	 * @param id   identifier of the work associated with the creator
	 * @return the Creator instance
	 *
	 */

	public Creator parseCreator(String name, int id) {
		if (_creators.containsKey(name)) {
			Creator creator = _creators.get(name);
			return creator;
		}
		Creator creator = new Creator(name);
		_creators.put(name, creator);
		_changed = true;
		return creator;
	}

	/**
	 * Creates a new book instance and adds it to the collection of works.
	 *
	 * @param fields array containing book data
	 *
	 */

	public void createBook(String... fields) throws InvalidIsbnException {
		String title = fields[1].trim();
		String isbn = fields[5];

		if (isbn.length() != 10 && isbn.length() != 13) {
			throw new InvalidIsbnException("ISBN: " + isbn + " is invalid.");
		}

		String[] authorsInput = fields[2].split(",");
		List<Creator> authors = new ArrayList<>();

		for (String author : authorsInput) {
			authors.add(parseCreator(author.trim(), _currentWorkId));
		}

		Category category = Category.valueOf(fields[4]);
		int price = Integer.parseInt(fields[3]);
		int quantity = Integer.parseInt(fields[6]);

		_changed = true;
		Book newBook = new Book(_currentWorkId, title, price, category, quantity, authors, isbn);

		for (Creator author : authors) {
			author.addWork(newBook);
		}

		_works.put(_currentWorkId++, newBook);
	}

	/**
	 * Registers a new user in the library system.
	 *
	 * @param name  the name of the user to register
	 * @param email the email of the user to register
	 * @return the newly created {@link User}
	 * @throws UserRegistrationFailedException if the name or email is invalid
	 *
	 */

	public int registerUser(String name, String email) throws UserRegistrationFailedException {
		if (name == null || name.isEmpty() || email == null || email.isEmpty()) {
			throw new UserRegistrationFailedException("Invalid user data");
		}

		User user = new User(_currentUserId, name, email);
		_users.put(_currentUserId++, user);

		_changed = true;
		return user.getId();
	}

	/**
	 * Returns the {@link User} corresponding to the given ID.
	 *
	 * @param id the unique identifier of the user
	 * @return the {@link User} object with the given ID
	 * @throws UserUnknownException if no user with the given ID exists
	 *
	 */

	public User requireUser(int id) throws UserUnknownException {
		User user = _users.get(id);
		if (user == null) {
			throw new UserUnknownException("User with id " + id + " not found.");
		}
		return user;
	}

	/**
	 * Returns the {@link Work} corresponding to the given ID.
	 *
	 * @param id the unique identifier of the work
	 * @return the {@link Work} object with the given ID
	 * @throws WorkUnknownException
	 */

	public Work requireWork(int id) throws WorkUnknownException {
		Work work = _works.get(id);
		if (work == null)
			throw new WorkUnknownException("Work with id " + id + " not found.");
		return work;
	}

	/**
	 * Returns a list of all works created by the specified creator.
	 *
	 * @param name the name of the creator
	 * @return a sorted list of {@link Work} objects associated with the creator
	 * @throws CreatorUnknownException
	 * @throws WorkUnknownException
	 *
	 */

	public List<Work> showCreatorWorks(String name) throws CreatorUnknownException, WorkUnknownException {
		Creator creator = _creators.get(name);
		if (creator == null)
			throw new CreatorUnknownException("Creator with name " + name + " not found");

		List<Work> works = (creator).getWorks();

		return works.stream()
				.sorted(Comparator.comparing(w -> w.getTitle().toLowerCase()))
				.collect(Collectors.toList());

	}

	/**
	 * Returns a collection containing all registered users.
	 *
	 * @return a collection of all {@link User} objects in the system
	 *
	 */

	public List<User> showUsers() {
		return _users.values().stream()
				.sorted()
				.collect(Collectors.toList());
	}

	/**
	 * Returns a collection containing all registered works.
	 *
	 * @return a collection of all {@link Work} objects in the system
	 */

	public List<Work> showWorks() {
		return _works.values().stream()
				.sorted()
				.collect(Collectors.toList());
	}

	public List<Work> search(String term) {

		SearchFilterVisitor visitor = new SearchFilterVisitor(term);

		List<Work> results = _works.values().stream()
				.filter(obra -> obra.acceptSearch(visitor))
				.sorted()
				.collect(Collectors.toList());

		return results;
	}

	public int returnWork(int userId, int workId)
			throws UserUnknownException, WorkUnknownException, NoSuchRequestException {
		User user = requireUser(userId);
		Work work = requireWork(workId);
        int fine = user.returnWork(workId, _date);
		work.returnCopy();
		if (work.getCurrentQuantity() == 1)
			notifyAvailability(work);
        return fine;
	}

	public int parseRequest(int userId, int workId)
			throws RuleNotMetException, WorkUnknownException, UserUnknownException {
		User user = requireUser(userId);
		Work work = requireWork(workId);
		for (Rule rule : _defaultRules)
			rule.check(user, work);
		unsubscribeAvailabilityNotification(user, work);
		return makeRequest(user, work);
	}

	public int makeRequest(User user, Work work) {
		int days = user.createRequest(work, _date);
		work.borrowCopy();
		notifyRequest(work);
		return days;
	}

	public void parseInventoryChange(int workId, int quantity)
			throws InvalidInventoryException, WorkUnknownException {
		Work work = requireWork(workId);
        int oldQuantity = work.getCurrentQuantity();
		int newQuantity = changeInventory(work, quantity);

        if(oldQuantity == 0 && quantity > 0)
            notifyAvailability(work);

		if (newQuantity == 0) {
			_works.remove(workId);
			_creators.values().forEach(c -> c.getWorks().removeIf(w -> w.getTotalQuantity() == 0));
			_creators.entrySet().removeIf(e -> e.getValue().isEmpty());
		}
	}

	public int changeInventory(Work work, int quantity) throws InvalidInventoryException {
		work.changeTotalQuantity(quantity);
		work.changeCurrentQuantity(quantity);
		return work.getTotalQuantity();
	}

	public void payFine(int userId) throws UserUnknownException, NoFineException {
		User user = requireUser(userId);
		if (!user.isSuspended())
			throw new NoFineException();
		user.payFine(_date);
	}

	public void payFineByWork(int userId, int fine) {
		User user = _users.get(userId);

		user.payFineByWork(fine, _date);
	}

	public void subscribeRequestNotification(int userId, int workId) {
		_requestNotificationService.subscribe(_works.get(workId), _users.get(userId));
	}

	public void subscribeAvailabilityNotification(int userId, int workId) {
		_availabilityNotificationService.subscribe(_works.get(workId), _users.get(userId));
	}

	public void unsubscribeRequestNotification(User user, Work work) {
		_requestNotificationService.unsubscribe(work, user);
	}

	public void unsubscribeAvailabilityNotification(User user, Work work) {
		_availabilityNotificationService.unsubscribe(work, user);
	}

	public void notifyRequest(Work work) {
		RequestNotification notification = new RequestNotification(work.copy());
		_requestNotificationService.notify(notification);
	}

	public void notifyAvailability(Work work) {
		AvailabilityNotification notification = new AvailabilityNotification(work.copy());
		_availabilityNotificationService.notify(notification);
	}

	public void clearNotifications(int userId) throws UserUnknownException {
		User user = requireUser(userId);
		user.viewNotifications();
	}

	public List<Notification> showNotifications(int userId) throws UserUnknownException {
		User user = requireUser(userId);
		return user.getNotifications();
	}

}
