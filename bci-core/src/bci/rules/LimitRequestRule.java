package bci.rules;

import bci.user.User;
import bci.work.Work;
import bci.exceptions.RuleNotMetException;

public class LimitRequestRule implements Rule {
    
    public void check(User user, Work work) throws RuleNotMetException{
        if(user.getMaxRequests() != user.getRequestSize()) return;
        throw new RuleNotMetException(4);
    }

}
