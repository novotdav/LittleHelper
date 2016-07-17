package dave.LitleHelper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;
import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.mail.MailSender;
import dave.LitleHelper.utils.TableUtils;

public class IntegrationMailController implements ActionListener {

	private JTable table;
	private Task task;
	private Settings settings = Settings.getInstance();

	public IntegrationMailController(JTable table, Task task) {
		this.table = table;
		this.task = task;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// get files to send by email
		List<AbstractEntity<?>> selectedItems = TableUtils.getSelectedItems(table);
		if (selectedItems == null) {
			throw new LittleException(Err.NULL_SELECTION);
		}

		StringBuilder sb = new StringBuilder(selectedItems.size() * 20);
		selectedItems.forEach(item -> {
			sb.append(item.toString()).append("\n");
		});

		// add signature
		sb.append("--").append("\n").append(settings.getValue(PropertyValues.EMAIL_SIGNATURE));

		String[] sendTo = settings.getValue(PropertyValues.EMAIL_TO).split(";");

		MailSender.send(sendTo, task.getHp(), sb.toString());

		SwingUtilities.invokeLater(() -> {
			JOptionPane.showMessageDialog(null, "Email uspesne odeslan.", "Info", JOptionPane.INFORMATION_MESSAGE);
		});
	}

}
