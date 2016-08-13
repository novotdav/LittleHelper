package dave.LitleHelper.panel;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class FileChooser extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4225484680521061012L;
	private JTextField textField;
	private JButton chooseFileButton;

	public FileChooser(String text, int columns) {
		initComponents(text, columns - 5);
	}

	private void initComponents(String text, int columns) {
		textField = new JTextField(text, columns);
		add(textField);

		chooseFileButton = new JButton("...");
		add(chooseFileButton);

		chooseFileButton.addActionListener(e -> {
			JFileChooser jfc = new JFileChooser();
			jfc.setDialogTitle("Vyberte adresar");
			jfc.setApproveButtonText("Vybrat");
			jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

			int choice = jfc.showOpenDialog(null);
			if (choice == JFileChooser.APPROVE_OPTION) {
				textField.setText(jfc.getSelectedFile().getAbsolutePath());
			}
		});
	}

	public String getText() {
		return textField.getText();
	}

}
