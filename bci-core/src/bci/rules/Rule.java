package bci.rules;

import bci.user.User;
import bci.work.Work;
import bci.exceptions.RuleNotMetException;
import java.io.Serializable;

public interface Rule extends Serializable{

    void check(User user, Work work) throws RuleNotMetException;
}
