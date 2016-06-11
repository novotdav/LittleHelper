package dave.LitleHelper.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

public abstract class MyAbstractTableModel<T> extends AbstractTableModel {

	protected List<T> data;

	@Override
	public int getRowCount() {
		return data.size();
	}

	@Override
	abstract public int getColumnCount();

	@Override
	abstract public Object getValueAt(int rowIndex, int columnIndex);

	public void setData(List<T> newData) {
		data.clear();
		addData(newData);
	}

	public void addData(List<T> newData) {
		data.addAll(newData);

		fireTableDataChanged();
	}

	public void removeRows(int[] rows) {
		List<T> objectsToRemove = new ArrayList<>();
		for (int row : rows) {
			objectsToRemove.add(data.get(row));
		}

		data.removeAll(objectsToRemove);

		fireTableDataChanged();
	}

	public List<T> getData() {
		return data;
	}

}
