package dave.LitleHelper.table;

import java.awt.Dimension;
import java.time.LocalDate;
import java.time.LocalTime;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;

import dave.LitleHelper.entities.Task;
import dave.LitleHelper.entities.TimeInterval;
import dave.LitleHelper.listener.DeleteTableRow;
import dave.LitleHelper.table.editor.TimeCellEditor;
import dave.LitleHelper.table.model.MyAbstractTableModel;
import dave.LitleHelper.table.model.TimeIntervalTableModel;

public class TimesTable extends JScrollPane {

	private MyAbstractTableModel<TimeInterval> model;

	public TimesTable(Task task, LocalDate date) {
		super();
		model = new TimeIntervalTableModel(date, task);
		model.setData(task.getTimes());
		JTable table = new JTable(model);
		table.setDefaultEditor(LocalTime.class, new TimeCellEditor());
		table.addKeyListener(new DeleteTableRow(table, model));
		table.setPreferredScrollableViewportSize(new Dimension(300, 100));
		setViewportView(table);
	}

	public TableModel getModel() {
		return model;
	}

}
