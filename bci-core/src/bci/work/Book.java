package bci.work;

import java.util.List;

import bci.SearchVisitor;
import bci.DisplayEntity;

import java.util.ArrayList;

public class Book extends Work {
	private List<Creator> _authors = new ArrayList<>();
	private String _isbn;

	public Book(int id, String title, int price, Category category, int quantity, List<Creator> authors, String isbn) {
		super(id, title, price, category, quantity);
		for (Creator c : authors)
			_authors.add(c);
		_isbn = isbn;
	}

	public Book(Book other) {
		super(other);
		_isbn = other.getIsbn();
		_authors = other.getAuthors();
	}

	public Work copy() {
		return new Book(this);
	}

	public String getIsbn() {
		return _isbn;
	}

	public List<String> getAuthorsNames() {
		List<String> names = new ArrayList<>();
		for (Creator author : _authors) {
			names.add(author.getName());
		}
		return names;
	}

	public List<Creator> getAuthors() {
		return _authors;
	}

	public String accept(DisplayEntity display) {
		return display.displayBook(this);
	}

	@Override
	public boolean acceptSearch(SearchVisitor visitor) {
		return visitor.search(this);
	}
}
