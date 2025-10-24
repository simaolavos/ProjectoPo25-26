package bci;

import bci.user.User;
import bci.work.DVD;
import bci.work.Book;
import bci.notifications.RequestNotification;
import bci.notifications.AvailabilityNotification;
import bci.user.AbidingBehaviour;
import bci.user.NormalBehaviour;
import bci.user.WrongfulBehaviour;

public interface DisplayEntity {

	String displayUser(User user);

	String displayDVD(DVD dvd);

	String displayBook(Book book);

	String displayRequestNotification(RequestNotification notification);

	String displayAvailabilityNotification(AvailabilityNotification notification);

    String displayNormalBehaviour(NormalBehaviour behaviour);

    String displayWrongfulBehaviour(WrongfulBehaviour behaviour);

    String displayAbidingBehaviour(AbidingBehaviour behaviour);

}
