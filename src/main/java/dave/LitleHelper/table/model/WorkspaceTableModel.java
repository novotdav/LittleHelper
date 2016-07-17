package dave.LitleHelper.table.model;

import java.util.ArrayList;

import dave.LitleHelper.entities.Workspace;

public class WorkspaceTableModel extends MyAbstractTableModel<Workspace> {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1222348987147189833L;

	public WorkspaceTableModel() {
		data = new ArrayList<>();
	}

	@Override
	public String getColumnName(int column) {
		return "Nazev";
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		Workspace workspace = data.get(rowIndex);

		switch (columnIndex) {
			case 0:
				return workspace.getName();
			default:
				return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

}
