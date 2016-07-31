package dave.LitleHelper.exception;

public class ValidationException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5804712023593483661L;

	public ValidationException(ValErr error, Throwable exception) {
		super(error.getMessage(), exception);
	}

	public ValidationException(ValErr error) {
		super(error.getMessage());
	}

	public enum ValErr {

		EMPTY_HP("Empty HP not allowed!"),
		EMPTY_DESC("Empty description not allowed!"),
		TASK_DUPLICITY("Task with this combination of HP and Description allready exists.");

		private String message;

		private ValErr(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}
}
