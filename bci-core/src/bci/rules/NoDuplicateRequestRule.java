package bci.rules;

import bci.user.User;
import bci.work.Work;
import bci.exceptions.RuleNotMetException;

public class NoDuplicateRequestRule implements Rule {

    public void check(User user, Work work) throws RuleNotMetException{
        if(user.getRequest(work.getId()) == null) return;
        throw new RuleNotMetException(1);
    }

}
