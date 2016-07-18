package dave.LitleHelper.listener;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTree;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.table.WorkspaceTable;
import dave.LitleHelper.table.model.MyAbstractTableModel;
import dave.LitleHelper.tree.model.UESBuildTreeModel;

// TODO complete, now only to test functioning
public class AddNewWorkspaceListener implements ActionListener {

	private WorkspaceType type;
	private MyAbstractTableModel<Workspace> model;
	private WorkspaceDAO dao;

	public AddNewWorkspaceListener(WorkspaceTable table, WorkspaceType type) {
		this.type = type;
		model = table.getModel();
		dao = new WorkspaceDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// WorkspaceEditPane editPane = new WorkspaceEditPane(type);
		//
		// int option = JOptionPane.showConfirmDialog(null, editPane,
		// "Existujici worskpace",
		// JOptionPane.OK_CANCEL_OPTION);
		//
		// if (option == JOptionPane.OK_OPTION) {
		// Workspace wrk = new Workspace(editPane.getName(), editPane.getPath(),
		// editPane.getType());
		// Workspace persistedWrk = dao.merge(wrk);
		// model.addData(Arrays.asList(persistedWrk));
		// }

		UESBuildTreeModel model = new UESBuildTreeModel();
		JTree tree = new JTree(model);
		model.generateStructure();

		JPanel content = new JPanel();
		content.setPreferredSize(new Dimension(500, 500));
		content.setSize(new Dimension(500, 500));
		content.add(tree);

		JOptionPane.showMessageDialog(null, content);
	}

}
