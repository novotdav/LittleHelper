package dave.LitleHelper.enums;

public enum WorkspaceType {
	CMD("CMD"),
	LEGACY("Legacy"),
	OTHER("Ostatni");

	private String value;

	private WorkspaceType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	@Override
	public String toString() {
		return value;
	}
}
