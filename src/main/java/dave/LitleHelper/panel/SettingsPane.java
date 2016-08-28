package dave.LitleHelper.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.EnumMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.JTextComponent;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;

public class SettingsPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8971921556785580989L;
	private static final int TEXT_SIZE = 20;
	private static final int ROWS_COUNT = 4;

	private int verticalIndex = 0;
	private GridBagConstraints c;
	private Settings settings;
	private Map<PropertyValues, JComponent> entries;
	private JPanel center, buttons;

	public SettingsPane() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		center = new JPanel(new GridBagLayout());
		buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);
		settings = Settings.getInstance();
		entries = new EnumMap<>(PropertyValues.class);

		placeComponent("Integracni emaily");

		placeComponent(PropertyValues.EMAIL_SMTP);
		placeComponent(PropertyValues.EMAIL_SMTP_PORT);
		placeComponent(PropertyValues.EMAIL_IMAP);
		placeComponent(PropertyValues.EMAIL_IMAP_PORT);
		placeComponent(PropertyValues.EMAIL);
		placeComponent(PropertyValues.EMAIL_LOGIN);
		placeComponent(PropertyValues.EMAIL_PASS, ComponentType.PASSWORD);
		placeComponent(PropertyValues.EMAIL_TO);
		placeComponent(PropertyValues.EMAIL_SIGNATURE, ComponentType.AREA);

		placeComponent("Workspaces");
		placeComponent(PropertyValues.WRK_LEGACY_PATH, ComponentType.FILE_PATH);
		placeComponent(PropertyValues.WRK_CMD_PATH, ComponentType.FILE_PATH);
		placeComponent(PropertyValues.WRK_OTHER_PATH, ComponentType.FILE_PATH);

		JButton save = new JButton("Ulozit");
		buttons.add(save);
		save.addActionListener(event -> {
			entries.forEach((key, component) -> {
				String value = "";
				if (component instanceof JPasswordField) {
					value = new String(((JPasswordField) component).getPassword());
				} else if (component instanceof FileChooser) {
					value = ((FileChooser) component).getText().trim();
				} else {
					value = ((JTextComponent) component).getText().trim();
				}

				settings.setValue(key, value);
			});

			settings.save();
			JOptionPane.showMessageDialog(null, "Ulozeni v poradku.", "Info", JOptionPane.INFORMATION_MESSAGE);
		});

		add(center, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

	}

	private void placeComponent(PropertyValues key) {
		placeComponent(key, ComponentType.FIELD);
	}

	private void placeComponent(PropertyValues key, ComponentType compType) {
		JComponent comp = new JTextField(TEXT_SIZE);
		String text = settings.getValue(key);

		if (ComponentType.AREA.equals(compType)) {
			comp = new JTextArea(text, ROWS_COUNT, TEXT_SIZE);
		} else if (ComponentType.PASSWORD.equals(compType)) {
			comp = new JPasswordField(text, TEXT_SIZE);
		} else if (ComponentType.FILE_PATH.equals(compType)) {
			comp = new FileChooser(text, TEXT_SIZE);
		}

		placeComponent(new JLabel(key.getLabel()), comp);
		entries.put(key, comp);
	}

	private void placeComponent(String headLine) {
		placeSeparator();
		placeComponent(new JLabel(headLine), null);
	}

	private void placeComponent(Component label, JComponent comp) {
		if (comp == null) {
			c.gridwidth = 2;
			c.gridy = verticalIndex++;
			center.add(label, c);
			c.gridwidth = 1;
		} else {
			c.gridy = verticalIndex++;
			c.anchor = GridBagConstraints.WEST;
			center.add(label, c);
			c.anchor = GridBagConstraints.CENTER;

			c.gridx = 1;
			center.add(comp, c);
			c.gridx = 0;
		}
	}

	private void placeSeparator() {
		c.gridy = verticalIndex++;
		c.gridwidth = 2;
		c.fill = GridBagConstraints.BOTH;
		JSeparator sep = new JSeparator(SwingConstants.HORIZONTAL);
		// sep.setPreferredSize(new Dimension(5, 1));
		center.add(sep, c);
		c.gridwidth = 1;
		c.fill = GridBagConstraints.NONE;
	}

	private enum ComponentType {
		FIELD,
		AREA,
		PASSWORD,
		FILE_PATH;
	}

}
