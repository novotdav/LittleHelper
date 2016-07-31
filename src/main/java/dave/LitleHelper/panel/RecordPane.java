package dave.LitleHelper.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;

import com.github.lgooddatepicker.components.DatePicker;

import dave.LitleHelper.dao.TaskDAO;
import dave.LitleHelper.entities.Task;

public class RecordPane extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2155308492206387679L;
	private JPanel centerPane;
	private Task lastAddedTask;
	TaskDAO taskDao;
	JComboBox<Task> taskSelector;

	public RecordPane() {
		taskDao = new TaskDAO();

		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		JPanel top = new JPanel();
		DatePicker dp = new DatePicker();
		dp.setDateToToday();
		top.add(dp);

		taskSelector = new JComboBox<>();
		taskSelector.addItemListener(e -> {
			CardLayout cl = (CardLayout) (centerPane.getLayout());
			cl.show(centerPane, Long.toString(((Task) e.getItem()).getId()));
		});
		top.add(taskSelector);

		JButton novyUkolButton = new JButton("Novy ukol");
		novyUkolButton.addActionListener(e -> {
			Task t = taskDao.createTask();
			TaskEditPane tep = new TaskEditPane(t, dp.getDate());

			centerPane.add(tep, Long.toString(t.getId()));
			taskSelector.addItem(t);
			taskSelector.setSelectedItem(t);

			lastAddedTask = t;
			novyUkolButton.setEnabled(false);
		});
		top.add(novyUkolButton);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(e -> {
			Task task = ((Task) taskSelector.getSelectedItem());
			task.update();

			Task mergedTask = taskDao.merge(task);
			taskSelector.removeItem(task);
			taskSelector.addItem(mergedTask);
			taskSelector.setSelectedItem(mergedTask);
			// TODO create sorted model for combobox?
		});
		buttons.add(saveButton);

		add(top, BorderLayout.NORTH);
		add(buttons, BorderLayout.SOUTH);

		centerPane = new JPanel(new CardLayout());
		loadTasks(dp.getDate());

		dp.addDateChangeListener(event -> {
			loadTasks(dp.getDate());
		});

		add(centerPane, BorderLayout.CENTER);
	}

	private void loadTasks(LocalDate date) {
		List<Task> list = taskDao.findAllByDate(date);
		taskSelector.removeAllItems();
		centerPane.removeAll();

		list.forEach(task -> {
			taskSelector.addItem(task);
			centerPane.add(task.createEditPane(date), Long.toString(task.getId()));
		});
	}
}
