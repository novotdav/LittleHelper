package dave.LitleHelper.panel;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.event.TreeExpansionEvent;
import javax.swing.event.TreeExpansionListener;

import dave.LitleHelper.dao.TaskDAO;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.tree.AbstractNode;
import dave.LitleHelper.tree.DateNode;
import dave.LitleHelper.tree.MyJTreeModel;
import dave.LitleHelper.tree.TaskNode;

public class OverViewPane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3930054696368288777L;
	private TaskDAO dao = new TaskDAO();

	public OverViewPane() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());

		MyJTreeModel model = new MyJTreeModel();

		JButton refresh_button = new JButton("Refresh");
		refresh_button.addActionListener(event -> {
			model.generateStructure(dao.findAllDays());
		});

		JPanel topPanel = new JPanel();
		topPanel.add(refresh_button);
		add(topPanel, BorderLayout.NORTH);

		final JTree jt = new JTree(model);
		// TODO consider renderer
		// jt.setCellRenderer(new MyTreeCellRenderer());
		jt.setRootVisible(true);
		jt.setRowHeight(0);

		JScrollPane jsp = new JScrollPane(jt);
		add(jsp, BorderLayout.CENTER);

		jt.addTreeExpansionListener(new TreeExpansionListener() {

			@Override
			public void treeExpanded(TreeExpansionEvent event) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AbstractNode node = (AbstractNode) event.getPath().getLastPathComponent();

						if (node.isDayNode()) {
							DateNode dn = (DateNode) node;
							LocalDate date = dn.getDate();
							List<Integer> indices = new ArrayList<>();

							List<Task> tasks = dao.findByDate(date);
							int lastIndex = 0;

							dn.removeAllChildren();

							for (Task task : tasks) {
								dn.addChild(new TaskNode(task));
								indices.add(lastIndex++);
							}

							int[] intArray = new int[indices.size()];
							for (int i = 0; i < indices.size(); i++) {
								intArray[i] = indices.get(i);
							}
							model.nodesWereInserted(node, intArray);
						}
					}
				});
			}

			@Override
			public void treeCollapsed(TreeExpansionEvent event) {
				SwingUtilities.invokeLater(new Runnable() {

					@Override
					public void run() {
						AbstractNode node = (AbstractNode) event.getPath().getLastPathComponent();

						if (node.isDayNode()) {
							DateNode dn = (DateNode) node;
							dn.removeAllChildren();
							model.nodeStructureChanged(dn);
						}
					}
				});
			}
		});

		jt.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
					AbstractNode node = (AbstractNode) jt.getSelectionPath().getLastPathComponent();

					if (node instanceof TaskNode) {
						LocalDate date = ((DateNode) node.getParent()).getDate();
						String dateString = date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
						Task task = ((TaskNode) node).getTask();
						// TODO create viewPane instead of editPane
						TaskEditPane editPane = task.createEditPane(date);
						JOptionPane.showMessageDialog(null, editPane, dateString + ": " + task,
								JOptionPane.PLAIN_MESSAGE);
					}
				}
			}
		});

	}
}
