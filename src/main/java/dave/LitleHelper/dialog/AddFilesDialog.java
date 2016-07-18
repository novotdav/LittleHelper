package dave.LitleHelper.dialog;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import dave.LitleHelper.entities.AffectedFile;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.listener.ClipBoardListener;
import dave.LitleHelper.listener.ProcessFileInput;

public class AddFilesDialog extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3052353763568225254L;
	private JTextArea filesTextArea;
	private JPanel parent;
	private Task task;
	private List<AffectedFile> resultList;
	private ClipBoardListener cl;

	public AddFilesDialog(JPanel parent, Task task) {
		this.parent = parent;
		this.task = task;
		resultList = new ArrayList<>();
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setModalityType(DEFAULT_MODALITY_TYPE);

		JPanel center = new JPanel();

		filesTextArea = new JTextArea(10, 50);
		JScrollPane jsc = new JScrollPane(filesTextArea);
		center.add(jsc);

		JPanel buttons = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ProcessFileInput(filesTextArea, resultList, this, task));

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});

		buttons.add(okButton);
		buttons.add(cancelButton);

		add(center, BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);

		cl = new ClipBoardListener(filesTextArea);
		cl.start();
	}

	public List<AffectedFile> showDialog() {
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);

		cl.stopMonitor();
		return resultList;
	}
}
