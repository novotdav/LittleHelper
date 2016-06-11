package dave.LitleHelper.table.model;

import java.util.ArrayList;

import dave.LitleHelper.entities.AffectedFile;

public class FileTableModel extends MyAbstractTableModel<AffectedFile> {

	public FileTableModel() {
		data = new ArrayList<>();
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Nazev";
		} else {
			return "Cesta";
		}
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		AffectedFile file = data.get(rowIndex);

		switch (columnIndex) {
			case 0:
				return file.getName();
			case 1:
				return file.getPath();
			default:
				return null;
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return String.class;
	}

}
