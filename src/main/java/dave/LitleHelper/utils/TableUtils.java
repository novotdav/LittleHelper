package dave.LitleHelper.utils;

import java.util.Arrays;
import java.util.List;

import javax.swing.JTable;

import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.table.model.MyAbstractTableModel;

public class TableUtils {

	/**
	 * Return immutable list of selected items from specified table or NULL if
	 * no items are selected.
	 * 
	 * @param table
	 *            to select from
	 * @return immutable list of items
	 */
	public static List<AbstractEntity<?>> getSelectedItems(JTable table) {
		if (table.getSelectedRowCount() == 0) {
			return null;
		}

		int[] selectedRows = table.getSelectedRows();
		int[] modelRows = new int[selectedRows.length];

		for (int i = 0; i < selectedRows.length; i++) {
			modelRows[i] = table.convertRowIndexToModel(selectedRows[i]);
		}

		AbstractEntity<?>[] items = new AbstractEntity[modelRows.length];
		@SuppressWarnings("unchecked")
		MyAbstractTableModel<AbstractEntity<?>> model = (MyAbstractTableModel<AbstractEntity<?>>) table.getModel();
		for (int i = 0; i < modelRows.length; i++) {
			items[i] = model.getEntity(modelRows[i]);
		}

		return Arrays.<AbstractEntity<?>>asList(items);
	}

}
