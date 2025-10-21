package bci.app;

import bci.DisplayEntity;
import bci.work.DVD;
import bci.work.Book;
import bci.user.User;
import bci.notifications.AvailabilityNotification;
import bci.notifications.RequestNotification;
import bci.user.NormalBehaviour;
import bci.user.AbidingBehaviour;
import bci.user.WrongfulBehaviour;

public class DisplayText implements DisplayEntity {

	@Override
	public String displayUser(User user) {
		return "" + user.getId() + " - " + user.getName() + " - " + user.getEmail() + " - "
				+ user.getBehaviour().accept(this) + " - "
				+ (user.isSuspended() ? "SUSPENSO" : "ACTIVO") + (user.isSuspended() ? " - EUR " + user.getFine() : "");
	}

	@Override
	public String displayDVD(DVD dvd) {

		return "" + dvd.getId() + " - " + dvd.getCurrentQuantity() + " de " + dvd.getTotalQuantity() + " - " + "DVD - "
				+ dvd.getTitle() + " - " + dvd.getPrice() + " - "
				+ dvd.getCategoryName() + " - " + dvd.getDirectorName() + " - " + dvd.getIgac();
	}

	@Override
	public String displayBook(Book book) {

		String authorString = String.join("; ", book.getAuthorsNames());

		return book.getId() + " - " + book.getCurrentQuantity() + " de " + book.getTotalQuantity() + " - Livro - "
				+ book.getTitle() + " - " + book.getPrice() + " - "
				+ book.getCategoryName() + " - " + authorString + " - " + book.getIsbn();
	}

	@Override
	public String displayRequestNotification(RequestNotification notification) {
		return "REQUISIÇÃO: " + notification.getWork().accept(this);
	}

	@Override
	public String displayAvailabilityNotification(AvailabilityNotification notification) {
		return "DISPONIBILIDADE: " + notification.getWork().accept(this);
	}

	@Override
	public String displayNormalBehaviour(NormalBehaviour behaviour) {
		return "NORMAL";
	}

	@Override
	public String displayAbidingBehaviour(AbidingBehaviour behaviour) {
		return "CUMPRIDOR";
	}

	@Override
	public String displayWrongfulBehaviour(WrongfulBehaviour behaviour) {
		return "FALTOSO";
	}

}
