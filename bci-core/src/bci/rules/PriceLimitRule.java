package bci.rules;

import bci.user.User;
import bci.work.Work;
import bci.exceptions.RuleNotMetException;

public class PriceLimitRule implements Rule {
    
    public void check(User user, Work work) throws RuleNotMetException{
        if(!user.hasPriceLimitation() || work.getPrice() <= 25) return;
        throw new RuleNotMetException(6);
    }

}
