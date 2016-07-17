package dave.LitleHelper.table;

import java.awt.Dimension;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.listener.DeleteTableRow;
import dave.LitleHelper.listener.EditExistingWorkspaceListener;
import dave.LitleHelper.table.model.MyAbstractTableModel;
import dave.LitleHelper.table.model.WorkspaceTableModel;
import dave.LitleHelper.utils.TableUtils;

public class WorkspaceTable extends JScrollPane {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1267098428117819734L;
	private MyAbstractTableModel<Workspace> model;
	private WorkspaceDAO dao;
	private JTable table;

	public WorkspaceTable(WorkspaceType type) {
		super();
		model = new WorkspaceTableModel();
		dao = new WorkspaceDAO();

		model.setData(dao.findByType(type));
		// model.addData(Arrays.asList(new Workspace("8-19", "C://neco",
		// WorkspaceType.LEGACY)));
		table = new JTable(model);
		table.setAutoCreateRowSorter(true);

		table.setPreferredScrollableViewportSize(new Dimension(300, 150));
		// table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addKeyListener(new DeleteTableRow(table, model));
		// table.addMouseListener(new FilesTableContextMenu(table, task));
		// table.setTransferHandler(new CopyHandler());
		table.addMouseListener(new EditExistingWorkspaceListener(this));
		setViewportView(table);
	}

	public MyAbstractTableModel<Workspace> getModel() {
		return model;
	}

	public List<AbstractEntity<?>> getSelectedItems() {
		if (table.getSelectedRowCount() == 0) {
			throw new LittleException(Err.NULL_SELECTION);
		}

		return TableUtils.getSelectedItems(table);
	}

}
