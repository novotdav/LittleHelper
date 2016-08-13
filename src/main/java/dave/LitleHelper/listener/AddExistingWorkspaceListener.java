package dave.LitleHelper.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.JOptionPane;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.panel.WorkspaceEditPane;
import dave.LitleHelper.table.WorkspaceTable;
import dave.LitleHelper.table.model.MyAbstractTableModel;

public class AddExistingWorkspaceListener implements ActionListener {

	private WorkspaceType type;
	private MyAbstractTableModel<Workspace> model;
	private WorkspaceDAO dao;

	public AddExistingWorkspaceListener(WorkspaceTable table, WorkspaceType type) {
		this.type = type;
		model = table.getModel();
		dao = WorkspaceDAO.getInstance();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		WorkspaceEditPane editPane = new WorkspaceEditPane(type);

		int option = JOptionPane.showConfirmDialog(null, editPane, "Existujici worskpace",
				JOptionPane.OK_CANCEL_OPTION);

		if (option == JOptionPane.OK_OPTION) {
			Workspace wrk = new Workspace(editPane.getName(), editPane.getPath(), editPane.getType());
			Workspace persistedWrk = dao.merge(wrk);
			model.addData(Arrays.asList(persistedWrk));
		}
	}

}
