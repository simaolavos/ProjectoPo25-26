package bci.rules;

import bci.work.Work;
import bci.work.Category;
import bci.user.User;
import bci.exceptions.RuleNotMetException;

public class NoReferenceWorkRule implements Rule {

    public void check(User user, Work work) throws RuleNotMetException{
        if(work.getCategory() != Category.REFERENCE) return;
        throw new RuleNotMetException(5);
    }
}
