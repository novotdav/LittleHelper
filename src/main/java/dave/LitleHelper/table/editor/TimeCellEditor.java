package dave.LitleHelper.table.editor;

import java.awt.Component;
import java.time.LocalTime;

import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

import com.github.lgooddatepicker.optionalusertools.TimeChangeListener;
import com.github.lgooddatepicker.timepicker.TimePicker;
import com.github.lgooddatepicker.timepicker.TimePickerSettings;
import com.github.lgooddatepicker.timepicker.TimePickerSettings.TimeIncrement;
import com.github.lgooddatepicker.zinternaltools.TimeChangeEvent;

public class TimeCellEditor extends AbstractCellEditor implements TableCellEditor {

	TimePicker picker;

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
		picker.addTimeChangeListener(new TimeChangeListener() {

			@Override
			public void timeChanged(TimeChangeEvent event) {
				stopCellEditing();
			}
		});
		return picker;
	}

}
