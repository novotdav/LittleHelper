package dave.LitleHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;

public class Settings {

	private static Settings instance;
	private Properties prop;
	private static final File SOURCE_FILE = new File("settings.conf");
	private static final String NO_LABEL = "no_label";

	private Settings() {
		init();
	}

	public static Settings getInstance() {
		if (instance == null) {
			instance = new Settings();
		}

		return instance;
	}

	private void init() {
		prop = new Properties();
		try {
			if (SOURCE_FILE.exists()) {
				prop.load(new FileInputStream(SOURCE_FILE));

				if (getValue(PropertyValues.WRK_DB_FILE_NAME) == null
						|| getValue(PropertyValues.WRK_DB_FILE_NAME).isEmpty()) {
					prop.put(PropertyValues.WRK_DB_FILE_NAME, "workspaces.data");
				}
			}
		} catch (IOException e) {
			throw new LittleException(Err.PROPERTY_LOAD, e);
		}
	}

	public void save() {
		try {
			prop.store(new FileOutputStream(SOURCE_FILE), null);
		} catch (IOException e) {
			throw new LittleException(Err.PROPERTY_SAVE, e);
		}
	}

	public String getValue(PropertyValues key) {
		return prop.getProperty(key.getKey());
	}

	public void setValue(PropertyValues key, String value) {
		prop.setProperty(key.getKey(), value);
	}

	public enum PropertyValues {
		EMAIL("email", "Email odesilatele"),
		EMAIL_SMTP("emailSmtp", "SMTP server"),
		EMAIL_SMTP_PORT("emailSmtpPort", "Port odchozi posty"),
		EMAIL_IMAP("emailImap", "IMAP server"),
		EMAIL_IMAP_PORT("emailImapPort", "Port prichozi posty"),
		EMAIL_LOGIN("emailLogin", "Login"),
		EMAIL_PASS("emailPass", "Heslo"),
		EMAIL_TO("sendTo", "Adresati"),
		EMAIL_SIGNATURE("emailSign", "Podpis"),
		WRK_DB_FILE_NAME("wrkDbFileName"),
		WRK_LEGACY_PATH("legacyPath", "Legacy - umisteni"),
		WRK_CMD_PATH("cmdPath", "CMD - umisteni"),
		WRK_OTHER_PATH("otherPath", "Ostatni - umisteni");

		private String key;
		private String label;

		private PropertyValues(String key) {
			this(key, NO_LABEL);
		}

		private PropertyValues(String key, String label) {
			this.key = key;
			this.label = label;
		}

		public String getKey() {
			return key;
		}

		public String getLabel() {
			return label;
		}
	}
}
