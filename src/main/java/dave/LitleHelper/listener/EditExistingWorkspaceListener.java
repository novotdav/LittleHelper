package dave.LitleHelper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JOptionPane;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.panel.WorkspaceEditPane;
import dave.LitleHelper.table.WorkspaceTable;
import dave.LitleHelper.table.model.MyAbstractTableModel;

public class EditExistingWorkspaceListener extends MouseAdapter implements ActionListener {

	private WorkspaceTable table;
	private MyAbstractTableModel<Workspace> model;
	private WorkspaceDAO dao;

	public EditExistingWorkspaceListener(WorkspaceTable table) {
		this.table = table;
		model = table.getModel();
		dao = new WorkspaceDAO();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		edit();
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		if (e.getClickCount() == 2) {
			edit();
		}
	}

	private void edit() {
		List<AbstractEntity<?>> entities = table.getSelectedItems();
		if (entities.size() > 1) {
			throw new LittleException(Err.MULTIPLE_SELECTION);
		}

		Workspace oldWrk = (Workspace) entities.get(0);
		WorkspaceEditPane editPane = new WorkspaceEditPane(oldWrk.getName(), oldWrk.getPath(), oldWrk.getType());

		int option = JOptionPane.showConfirmDialog(null, editPane, "Uprav worskpace", JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			oldWrk.setName(editPane.getName());
			oldWrk.setPath(editPane.getPath());
			dao.merge(oldWrk);
			model.refreshData();
		}
	}

}
