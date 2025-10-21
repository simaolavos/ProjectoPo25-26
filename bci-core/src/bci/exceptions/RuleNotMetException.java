package bci.exceptions;

/**
 * Exception thrown when a rule is not met.
 */
public class RuleNotMetException extends Exception {

    private int _ruleID;

	public RuleNotMetException(int ruleID) {
		_ruleID = ruleID;
	}

    public int getRuleID(){
        return _ruleID;
    }
}
