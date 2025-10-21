package bci;

import bci.work.DVD;
import bci.work.Book;

public class SearchFilterVisitor implements SearchVisitor {

	private final String termLowerCase;

	public SearchFilterVisitor(String searchTerm) {
		this.termLowerCase = searchTerm.toLowerCase();
	}

	private boolean checkTitle(String title) {
		return title.toLowerCase().contains(termLowerCase);
	}

	@Override
	public boolean search(DVD dvd) {
		return checkTitle(dvd.getTitle()) ||
			   dvd.getDirector().getName().toLowerCase().contains(termLowerCase);
	}

	@Override
	public boolean search(Book book) {
		if (checkTitle(book.getTitle())) return true;
		return book.getAuthors().stream()
				.anyMatch(author -> author.getName().toLowerCase().contains(termLowerCase));
	}
}

