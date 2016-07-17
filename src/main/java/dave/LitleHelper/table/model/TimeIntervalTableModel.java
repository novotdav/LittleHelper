package dave.LitleHelper.table.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import dave.LitleHelper.entities.EmptyTimeInterval;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.entities.TimeInterval;

public class TimeIntervalTableModel extends MyAbstractTableModel<TimeInterval> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2504807731649780791L;
	private LocalDate date;
	private Task task;

	public TimeIntervalTableModel(LocalDate date, Task task) {
		data = new ArrayList<>();
		data.add(new EmptyTimeInterval());
		this.date = date;
		this.task = task;
	}

	@Override
	public void setData(List<TimeInterval> newData) {
		super.setData(newData);
		data.add(new EmptyTimeInterval());
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return data.get(rowIndex).getFrom();
			case 1:
				return data.get(rowIndex).getTo();
			default:
				return null;
		}
	}

	@Override
	public String getColumnName(int column) {
		if (column == 0) {
			return "Od";
		} else {
			return "Do";
		}
	}

	@Override
	public Class<?> getColumnClass(int columnIndex) {
		return LocalTime.class;
	}

	@Override
	public boolean isCellEditable(int rowIndex, int columnIndex) {
		return true;
	}

	@Override
	public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
		TimeInterval time = data.get(rowIndex);

		if (time instanceof EmptyTimeInterval && aValue != null) {
			time = new TimeInterval(date, task);
			data.add(data.size() - 1, time);
		}

		switch (columnIndex) {
			case 0:
				time.setFrom((LocalTime) aValue);
			case 1:
				time.setTo((LocalTime) aValue);
		}
		fireTableDataChanged();
	}

	@Override
	public List<TimeInterval> getData() {
		List<TimeInterval> newData = new ArrayList<>();

		data.forEach(time -> {
			if (!(time instanceof EmptyTimeInterval)) {
				newData.add(time);
			}
		});

		return newData;
	}

}
