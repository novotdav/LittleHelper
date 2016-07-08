package dave.LitleHelper;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.github.lgooddatepicker.datepicker.DatePicker;
import com.github.lgooddatepicker.datepicker.DatePickerSettings;

import dave.LitleHelper.dao.TaskDAO;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.panel.TaskEditPane;

public class MainWindow extends JFrame {
	private static final Dimension WINDOW_SIZE = new Dimension(1100, 720);
	private JPanel centerPane;
	private EntityManager em;
	private Task lastAddedTask;
	TaskDAO taskDao;
	JComboBox<Task> taskSelector;

	public MainWindow() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("littleHelper");
		taskDao = new TaskDAO();
		em = factory.createEntityManager();

		initComponents();

		addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				try {
					DriverManager.getConnection("jdbc:derby:;shutdown=true");
				} catch (SQLException e1) {
					// sql error is expected -> do nothing
				}
			}

		});
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		setSize(WINDOW_SIZE);
		setPreferredSize(WINDOW_SIZE);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setTitle("LitleHelper");
		setLocationRelativeTo(null);

		JPanel top = new JPanel();
		DatePickerSettings dps = new DatePickerSettings();
		dps.setInitialDateToToday();
		DatePicker dp = new DatePicker(dps);
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

			// TODO move to DAO
			em.getTransaction().begin();
			// em.persist(t);
			em.merge(t);
			em.getTransaction().commit();

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
		// TODO load from DB
		loadTasks(dp.getDate());

		dp.addDateChangeListener(event -> {
			System.out.println(dp.getDate());
			loadTasks(dp.getDate());
		});

		// Task t = em.find(Task.class, 2101L);
		// taskSelector.addItem(t);
		// centerPane.add(t.createEditPane(dp.getDate()), t.toString());

		add(centerPane, BorderLayout.CENTER);
	}

	private void loadTasks(LocalDate date) {
		List<Task> list = taskDao.findFilter(date);
		taskSelector.removeAllItems();
		centerPane.removeAll();

		list.forEach(task -> {
			// System.out.println("times: " + t.getTimes().size());

			taskSelector.addItem(task);
			centerPane.add(task.createEditPane(date), task.toString());
			System.out.println("id: " + task.getId());
		});
	}

}
