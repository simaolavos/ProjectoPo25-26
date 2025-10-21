package bci.work;

import bci.DisplayEntity;
import bci.SearchVisitor;
import java.io.Serializable;
import bci.exceptions.InvalidInventoryException;

public abstract class Work implements Serializable, Comparable<Work> {

	private final int _id;
	private final String _title;
	private final int _price;
	private final Category _category;
	private int _totalQuantity;
	private int _currentQuantity;

	public Work(int id, String title, int price, Category category, int totalQuantity) {
		_id = id;
		_title = title;
		_price = price;
		_category = category;
		_totalQuantity = totalQuantity;
		_currentQuantity = _totalQuantity;
	}

	public Work(Work other) {
		_id = other.getId();
		_title = other.getTitle();
		_price = other.getPrice();
		_category = other.getCategory();
		_totalQuantity = other.getTotalQuantity();
		_currentQuantity = other.getCurrentQuantity();
	}

	public abstract Work copy();

	public void changeCurrentQuantity(int amount) {
		int finalValue = _currentQuantity + amount;
        _currentQuantity = Math.max(0, finalValue);
	}

	public boolean isAvailable() {
		return _totalQuantity != 0;
	}

	public int getTotalQuantity() {
		return _totalQuantity;
	}

	public void changeTotalQuantity(int quantity) throws InvalidInventoryException {
		if (_currentQuantity + quantity < 0) {
			throw new InvalidInventoryException();
		}
		_totalQuantity += quantity;
	}

	public int getCurrentQuantity() {
		return _currentQuantity;
	}

	public void borrowCopy() {
		changeCurrentQuantity(-1);
	}

	public void returnCopy() {
		changeCurrentQuantity(1);
	}

	public int getId() {
		return _id;
	}

	public int getPrice() {
		return _price;
	}

	public String getCategoryName() {
		return _category.toString();
	}

	public Category getCategory() {
		return _category;
	}

	public String getTitle() {
		return _title;
	}

	public abstract String accept(DisplayEntity display);

	@Override
	public int compareTo(Work work) {
		return _id - work._id;
	}

	public abstract boolean acceptSearch(SearchVisitor visitor);

}
