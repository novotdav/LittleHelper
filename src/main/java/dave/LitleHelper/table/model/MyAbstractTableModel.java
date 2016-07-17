package dave.LitleHelper.table.model;

import java.util.ArrayList;
import java.util.List;

import javax.swing.table.AbstractTableModel;

import dave.LitleHelper.entities.AbstractEntity;

public abstract class MyAbstractTableModel<T extends AbstractEntity> extends AbstractTableModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4059929262977267329L;
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

	@SuppressWarnings("unchecked")
	public void removeRows(int[] rows) {
		List<T> objectsToRemove = new ArrayList<>();
		for (int row : rows) {
			T item = data.get(row);
			item.getDao().remove(item);
			objectsToRemove.add(item);
		}

		data.removeAll(objectsToRemove);

		fireTableDataChanged();
	}

	public List<T> getData() {
		return data;
	}

	public AbstractEntity<?> getEntity(int index) {
		return data.get(index);
	}

	public void refreshData() {
		fireTableDataChanged();
	}

}
