package dave.LitleHelper.table.editor;

import java.awt.Component;
import java.time.LocalTime;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.github.lgooddatepicker.components.TimePicker;
import com.github.lgooddatepicker.components.TimePickerSettings;
import com.github.lgooddatepicker.components.TimePickerSettings.TimeIncrement;

public class TimeCellEditor extends AbstractCellEditor implements TableCellEditor {

	private TimePicker picker;

	private int minimumRowHeightInPixels;

	@Override
	public Object getCellEditorValue() {
		return picker.getTime();
	}

	@Override
	public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
		TimePickerSettings settings = new TimePickerSettings();
		LocalTime startTime = (LocalTime) value;
		if (startTime == null) {
			startTime = LocalTime.of(7, 00);
		}
		settings.generatePotentialMenuTimes(TimeIncrement.FifteenMinutes, startTime, null);

		picker = new TimePicker(settings);
		picker.setTime((LocalTime) value);

		settings.setVetoPolicy(new Time15MinutesVeto());

		minimumRowHeightInPixels = (picker.getPreferredSize().height + 1);
		zAdjustTableRowHeightIfNeeded(table);

		return picker;
	}

	private void zAdjustTableRowHeightIfNeeded(JTable table) {
		if ((table.getRowHeight() < minimumRowHeightInPixels)) {
			table.setRowHeight(minimumRowHeightInPixels);
		}
	}

}
