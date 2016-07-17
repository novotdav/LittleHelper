package dave.LitleHelper.panel;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import dave.LitleHelper.enums.WorkspaceType;

public class WorkspaceEditPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -280625456019890529L;
	private static final int TEXT_SIZE = 40;

	private int verticalIndex = 0;
	private GridBagConstraints c;
	private JPanel center;
	private JTextField nameText, pathText;
	private JComboBox<WorkspaceType> typeBox;

	public WorkspaceEditPane() {
		this(null);
	}

	public WorkspaceEditPane(String name, String path, WorkspaceType type) {
		this(type);
		nameText.setText(name);
		pathText.setText(path);
		typeBox.setEnabled(false);
	}

	public WorkspaceEditPane(WorkspaceType type) {
		initComponents(type);
	}

	private void initComponents(WorkspaceType type) {
		setLayout(new BorderLayout());

		center = new JPanel(new GridBagLayout());

		c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		nameText = new JTextField(TEXT_SIZE);
		JLabel nameLabel = new JLabel("Nazev");
		placeComponent(nameLabel, nameText);

		typeBox = new JComboBox<>(WorkspaceType.values());
		if (type != null) {
			typeBox.setSelectedItem(type);
		}
		JLabel typeLabel = new JLabel("Typ");
		placeComponent(typeLabel, typeBox);

		// TODO change to fileChooser
		pathText = new JTextField(TEXT_SIZE);
		JLabel pathLabel = new JLabel("Cesta");
		placeComponent(pathLabel, pathText);

		add(center, BorderLayout.CENTER);
	}

	private void placeComponent(Component label, Component comp) {
		c.gridy = verticalIndex++;
		c.anchor = GridBagConstraints.WEST;
		center.add(label, c);
		c.anchor = GridBagConstraints.CENTER;

		c.gridx = 1;
		center.add(comp, c);
		c.gridx = 0;
	}

	public String getName() {
		return nameText.getText();
	}

	public String getPath() {
		return pathText.getText();
	}

	public WorkspaceType getType() {
		return (WorkspaceType) typeBox.getSelectedItem();
	}
}
