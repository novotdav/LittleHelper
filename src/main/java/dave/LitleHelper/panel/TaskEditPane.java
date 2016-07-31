package dave.LitleHelper.panel;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.text.AbstractDocument;

import dave.LitleHelper.LimitDocumentFilter;
import dave.LitleHelper.dialog.AddFilesDialog;
import dave.LitleHelper.entities.AffectedFile;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.entities.TimeInterval;
import dave.LitleHelper.exception.ValidationException;
import dave.LitleHelper.exception.ValidationException.ValErr;
import dave.LitleHelper.listener.DeleteTableRow;
import dave.LitleHelper.table.FilesTable;
import dave.LitleHelper.table.editor.TimeCellEditor;
import dave.LitleHelper.table.model.MyAbstractTableModel;
import dave.LitleHelper.table.model.TimeIntervalTableModel;

public class TaskEditPane extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5634027815933545573L;
	private Task task;
	private LocalDate date;
	private MyAbstractTableModel<TimeInterval> timesModel;
	private JTextField hpTextField;
	private JTextField descriptionTextField;
	private JTextArea notesTextArea;
	private MyAbstractTableModel<AffectedFile> filesModel;

	public TaskEditPane(Task task, LocalDate date) {
		this.task = task;
		this.date = date;
		task.setEditPane(this);
		initComponents();
	}

	@SuppressWarnings("unchecked")
	private void initComponents() {
		JPanel masterPane = new JPanel();
		masterPane.setLayout(new BoxLayout(masterPane, BoxLayout.Y_AXIS));

		// TODO add state features
		// JPanel controlPane = new JPanel();
		// controlPane.add(new JButton("Uzavrit"));
		// controlPane.add(new JButton("Commit"));
		// controlPane.add(new JButton("Otevrit"));
		//
		// masterPane.add(controlPane);

		JPanel timesPane = new JPanel();
		timesPane.add(new JLabel("Casy"));
		timesModel = new TimeIntervalTableModel(date, task);
		timesModel.setData(task.getTimes());
		JTable timesTable = new JTable(timesModel);
		timesTable.setDefaultEditor(LocalTime.class, new TimeCellEditor());
		timesTable.addKeyListener(new DeleteTableRow(timesTable, timesModel));
		timesTable.setPreferredScrollableViewportSize(new Dimension(300, 100));
		JScrollPane timesScroll = new JScrollPane(timesTable);
		timesPane.add(timesScroll);

		masterPane.add(timesPane);

		JPanel hpPane = new JPanel();
		hpTextField = new JTextField(10);
		// limit text length
		((AbstractDocument) hpTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(64));
		hpTextField.setText(task.getHp() == null ? "" : task.getHp());

		descriptionTextField = new JTextField(25);
		// limit text length
		((AbstractDocument) descriptionTextField.getDocument()).setDocumentFilter(new LimitDocumentFilter(256));
		descriptionTextField.setText(task.getDescription() == null ? "" : task.getDescription());

		hpPane.add(new JLabel("HP/WI"));
		hpPane.add(hpTextField);
		hpPane.add(new JLabel("Popis"));
		hpPane.add(descriptionTextField);

		masterPane.add(hpPane);

		JPanel notesPane = new JPanel();
		notesTextArea = new JTextArea(15, 50);
		notesTextArea.setText(task.getNotes() == null ? "" : task.getNotes());

		JScrollPane notesScroll = new JScrollPane(notesTextArea);
		notesPane.add(new JLabel("Poznamky"));
		notesPane.add(notesScroll);

		masterPane.add(notesPane);

		JPanel filesPane = new JPanel();
		filesPane.add(new JLabel("Soubory"));

		FilesTable filesTable = new FilesTable(task, date);
		filesModel = (MyAbstractTableModel<AffectedFile>) filesTable.getModel();
		filesPane.add(filesTable);

		JButton addFilesButton = new JButton("Pridej soubory");
		addFilesButton.addActionListener(e -> {
			AddFilesDialog dialog = new AddFilesDialog(masterPane, task);
			List<AffectedFile> result = dialog.showDialog();
			filesModel.addData(result);
		});

		filesPane.add(addFilesButton);

		masterPane.add(filesPane);

		super.setViewportView(masterPane);
	}

	public void update() {
		if (hpTextField.getText().isEmpty()) {
			throw new ValidationException(ValErr.EMPTY_HP);
		}

		if (descriptionTextField.getText().isEmpty()) {
			throw new ValidationException(ValErr.EMPTY_DESC);
		}

		task.setTimes(timesModel.getData());
		task.setHp(hpTextField.getText());
		task.setDescription(descriptionTextField.getText());
		task.setNotes(notesTextArea.getText());
		task.setFiles(filesModel.getData());
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

}
