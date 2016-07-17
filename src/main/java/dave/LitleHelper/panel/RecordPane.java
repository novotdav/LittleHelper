package dave.LitleHelper.panel;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.time.LocalDate;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
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

		taskSelector.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				CardLayout cl = (CardLayout) (centerPane.getLayout());
				cl.show(centerPane, e.getItem().toString());
			}
		});
		top.add(taskSelector);

		JButton novyUkolButton = new JButton("Novy ukol");
		novyUkolButton.addActionListener(e -> {
			Task t = new Task();
			TaskEditPane tep = new TaskEditPane(t, dp.getDate());

			centerPane.add(tep, t.toString());
			taskSelector.addItem(t);
			taskSelector.setSelectedItem(t);

			lastAddedTask = t;
			novyUkolButton.setEnabled(false);
		});
		top.add(novyUkolButton);

		JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT));

		JButton saveButton = new JButton("Save");
		saveButton.addActionListener(e -> {
			Task t = ((Task) taskSelector.getSelectedItem());
			String beforeUpdate = t.toString();
			t.update();
			String afterUpdate = t.toString();

			// HP or Description was changed
			boolean changed = !beforeUpdate.equals(afterUpdate);

			taskDao.merge(t);

			// if persisted item is new task or task with changed HP or
			// Description
			if ((lastAddedTask != null && lastAddedTask.equals(t)) || changed) {
				if (t.getHp() != null && !t.getHp().isEmpty()) {
					novyUkolButton.setEnabled(true);
					taskSelector.repaint();
					CardLayout cl = (CardLayout) (centerPane.getLayout());
					cl.removeLayoutComponent(t.getEditPane());
					cl.addLayoutComponent(t.getEditPane(), t.toString());
					cl.show(centerPane, t.toString());
					lastAddedTask = null;
				} else {
					JOptionPane.showMessageDialog(null, "HP musi byt vyplneno!", "Error", JOptionPane.ERROR_MESSAGE);
				}
			}
		});
		buttons.add(saveButton);

		add(top, BorderLayout.NORTH);
		add(buttons, BorderLayout.SOUTH);

		centerPane = new JPanel(new CardLayout());
		loadTasks(dp.getDate());

		dp.addDateChangeListener(event -> {
			loadTasks(dp.getDate());
		});

		// Task t = em.find(Task.class, 2101L);
		// taskSelector.addItem(t);
		// centerPane.add(t.createEditPane(dp.getDate()), t.toString());

		add(centerPane, BorderLayout.CENTER);
	}

	private void loadTasks(LocalDate date) {
		List<Task> list = taskDao.findAllByDate(date);
		taskSelector.removeAllItems();
		centerPane.removeAll();

		list.forEach(task -> {
			taskSelector.addItem(task);
			centerPane.add(task.createEditPane(date), task.toString());
		});
	}
}
