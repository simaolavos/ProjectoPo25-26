package bci.rules;

import bci.user.User;
import bci.work.Work;
import bci.exceptions.RuleNotMetException;

public class NotSuspendedRule implements Rule {

    public void check(User user, Work work) throws RuleNotMetException{
        if(!user.isSuspended()) return;
        throw new RuleNotMetException(2);
    }
}
