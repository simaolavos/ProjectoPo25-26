package bci.rules;

import bci.work.Work;
import bci.user.User;
import bci.exceptions.RuleNotMetException;

public class NoWorkAvailableRule implements Rule {

    public void check(User user, Work work) throws RuleNotMetException{
        if(work.getCurrentQuantity() != 0) return;
        throw new RuleNotMetException(3);
    }
}
