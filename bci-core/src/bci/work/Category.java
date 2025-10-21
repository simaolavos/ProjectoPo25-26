package bci.work;

public enum Category {
	REFERENCE("Referência"),
	FICTION("Ficção"),
	SCITECH("Técnica e Científica");

	private final String _displayName;

	Category(String displayName) {
		_displayName = displayName;
	}

	public String getDisplayName() {
		return _displayName;
	}

	@Override
	public String toString() {
		return _displayName;
	}
}
