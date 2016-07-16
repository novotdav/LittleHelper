package dave.LitleHelper.exception;

public class LittleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7975737427215274816L;

	public LittleException(Err error, Throwable exception) {
		super(error.getMessage(), exception);
	}

	public enum Err {

		DEFAULT("Default exception."),
		PROPERTY_LOAD("Error while loading settings properties."),
		PROPERTY_SAVE("Error while saving settings properties."),
		DB_LOAD("Error while loading database."),
		MAIL_SEND_FAILURE("Error while sending e-mail."),
		MAIL_SAVE_FAILURE("Error while saving e-mail. Email was sent but not saved.");

		private String message;

		private Err(String message) {
			this.message = message;
		}

		public String getMessage() {
			return message;
		}
	}

}
