package bci.work;

import bci.DisplayEntity;
import bci.SearchVisitor;

public class DVD extends Work {
	private final Creator _director;
	private final String _igac;

	public DVD(int id, String title, int price, Category category, int quantity, Creator director, String igac) {
		super(id, title, price, category, quantity);
		_director = director;
		_igac = igac;
	}

	public DVD(DVD other) {
		super(other);
		_igac = other.getIgac();
		_director = other.getDirector();
	}

	public Work copy() {
		return new DVD(this);
	}

	public String getIgac() {
		return _igac;
	}

	public Creator getDirector() {
		return _director;
	}

	public String getDirectorName() {
		return _director.getName();
	}

	@Override
	public String accept(DisplayEntity display) {
		return display.displayDVD(this);
	}

	@Override
	public boolean acceptSearch(SearchVisitor visitor) {
		return visitor.search(this);
	}
}
