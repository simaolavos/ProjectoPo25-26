package bci;

import java.util.List;
import bci.user.User;
import bci.exceptions.*;
import bci.notifications.Notification;

import java.io.*;
import bci.work.*;
import bci.rules.*;

/**
 * The façade class.
 */
public class LibraryManager {

	private List<Rule> _defaultRules = List.of(new NoDuplicateRequestRule(), new NotSuspendedRule(),
			new NoWorkAvailableRule(), new LimitRequestRule(),
			new NoReferenceWorkRule(), new PriceLimitRule());

	/** The object doing all the actual work. */
	private Library _library = new Library(_defaultRules);

	public int getDate() {
		return _library.getDate();
	}

	public void advanceDate(int days) {
		_library.advanceDate(days);
	}

	public void save() throws MissingFileAssociationException, IOException {
		String filename = _library.getFilename();
		if (filename == null) {
			throw new MissingFileAssociationException();
		}

		try (ObjectOutputStream oos = new ObjectOutputStream(
				new BufferedOutputStream(new FileOutputStream(filename)))) {
			oos.writeObject(_library);
			_library.setChanged(false);
		}

	}

	public void saveAs(String filename) throws MissingFileAssociationException, IOException {
		_library.setFilename(filename);
		save();
	}

	public boolean hasChanged() {
		return _library.changed();
	}

	public void load(String filename) throws UnavailableFileException {
		try (ObjectInputStream ois = new ObjectInputStream(new BufferedInputStream(new FileInputStream(filename)))) {
			_library = (Library) ois.readObject();
			_library.setFilename(filename);
			_library.setChanged(false);
		} catch (IOException | ClassNotFoundException _) {
			throw new UnavailableFileException(filename);
		}
	}

	/**
	 * Read text input file and initializes the current library (which should be
	 * empty)
	 * with the domain entities representeed in the import file.
	 *
	 * @param filename name of the text input file
	 * @throws ImportFileException if some error happens during the processing of
	 *                             the
	 *                             import file.
	 */

	public void importFile(String filename) throws ImportFileException {
		try {
			if (filename != null && !filename.isEmpty())
				_library.importFile(filename);
		} catch (IOException | UnrecognizedEntryException | UserRegistrationFailedException | InvalidIsbnException e) {
			throw new ImportFileException(filename, e);
		}
	}

	public int registerUser(String name, String email) throws UserRegistrationFailedException {
		var id = _library.registerUser(name, email);
		return id;
	}

	public User showUser(int id) throws UserUnknownException {
		return _library.requireUser(id);
	}

	public Work showWork(int id) throws WorkUnknownException {
		return _library.requireWork(id);
	}

	public List<Work> showWorks() {
		return _library.showWorks();
	}

	public List<User> showUsers() {
		return _library.showUsers();
	}

	public List<Notification> showNotifications(int id) throws UserUnknownException {
		return _library.showNotifications(id);
	}

	public List<Work> showCreatorWorks(String name) throws CreatorUnknownException, WorkUnknownException {
		return _library.showCreatorWorks(name);
	}

	public int makeRequest(int userId, int workId)
			throws RuleNotMetException, UserUnknownException, WorkUnknownException {
		return _library.parseRequest(userId, workId);
	}

	public void inventoryChange(int workId, int quantity) throws InvalidInventoryException, WorkUnknownException {
		_library.parseInventoryChange(workId, quantity);
	}

	public void payFine(int userId) throws NoFineException, UserUnknownException {
		_library.payFine(userId);
	}

	public int returnWork(int userId, int workId)
			throws UserUnknownException, WorkUnknownException, NoSuchRequestException {
		return _library.returnWork(userId, workId);
	}

	public void payFineByWork(int userId, int fine) {
		_library.payFineByWork(userId, fine);
	}

	public void subscribeAvailabilityNotification(int userId, int workId) {
		_library.subscribeAvailabilityNotification(userId, workId);
	}

	public void clearNotifications(int userId) throws UserUnknownException {
		_library.clearNotifications(userId);
	}

	public List<Work> search(String term) {
		return _library.search(term);
	}
}
