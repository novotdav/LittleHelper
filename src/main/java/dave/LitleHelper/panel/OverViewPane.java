package dave.LitleHelper.panel;

import javax.swing.JPanel;
import javax.swing.JTree;

import dave.LitleHelper.dao.TaskDAO;
import dave.LitleHelper.tree.MyJTreeModel;

public class OverViewPane extends JPanel {

	private TaskDAO dao = new TaskDAO();

	public OverViewPane() {
		initComponents();
	}

	private void initComponents() {

		MyJTreeModel model = new MyJTreeModel();
		model.generateStructure(dao.findAllDays());

		JTree jt = new JTree(model);
		// TODO consider renderer
		// jt.setCellRenderer(new MyTreeCellRenderer());
		jt.setRootVisible(false);
		jt.setSelectionModel(null);
		jt.setRowHeight(0);

		add(jt);

	}
}
