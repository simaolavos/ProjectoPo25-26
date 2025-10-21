package bci;

import bci.work.DVD;
import bci.work.Book;
import bci.work.Work;

public interface SearchVisitor {
	public boolean search(DVD dvd);

	public boolean search(Book book);

}
