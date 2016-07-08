package dave.LitleHelper.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dave.LitleHelper.entities.AbstractEntity;

public abstract class MyAbstractTableModel<T extends AbstractEntity> extends AbstractTableModel {

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
			T item = data.get(row);
			item.detach();
			objectsToRemove.add(item);
		}

		data.removeAll(objectsToRemove);

		fireTableDataChanged();
	}

	public List<T> getData() {
		return data;
	}

}
