package dave.LitleHelper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Scanner;

import javax.swing.JDialog;
import javax.swing.JTextArea;

import dave.LitleHelper.entities.AffectedFile;
import dave.LitleHelper.entities.Task;

public class ProcessFileInput implements ActionListener {

	private JTextArea input;
	private List<AffectedFile> resultList;
	private JDialog dialog;
	private Task task;

	public ProcessFileInput(JTextArea input, List<AffectedFile> resultList, JDialog dialog, Task task) {
		this.input = input;
		this.resultList = resultList;
		this.dialog = dialog;
		this.task = task;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Scanner s = new Scanner(input.getText());

		try {
			while (s.hasNextLine()) {
				String textLine = s.nextLine();
				AffectedFile file;

				if (textLine.contains(" - ")) {
					String[] split = textLine.split(" - ");
					file = new AffectedFile(split[0], split[1], task);
				} else {
					String fileName = textLine.substring(textLine.lastIndexOf('/') + 1);
					file = new AffectedFile(fileName, textLine, task);
				}

				resultList.add(file);
			}
		} finally {
			s.close();
		}

		dialog.dispose();
	}

}
