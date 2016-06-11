package dave.LitleHelper.listener;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JTable;

import dave.LitleHelper.table.model.MyAbstractTableModel;

public class DeleteTableRow extends KeyAdapter {

	private JTable table;
	private MyAbstractTableModel<?> model;

	public DeleteTableRow(JTable table, MyAbstractTableModel<?> model) {
		this.table = table;
		this.model = model;
	}

	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_DELETE) {
			int[] rows = table.getSelectedRows();
			int[] modelRows = new int[rows.length];

			for (int i = 0; i < rows.length; i++) {
				modelRows[i] = table.convertRowIndexToModel(rows[i]);
			}

			model.removeRows(modelRows);
		}
	}

}
