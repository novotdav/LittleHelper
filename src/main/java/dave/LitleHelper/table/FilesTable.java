package dave.LitleHelper.table;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;
import dave.LitleHelper.entities.AffectedFile;
import dave.LitleHelper.entities.Task;
import dave.LitleHelper.listener.DeleteTableRow;
import dave.LitleHelper.mail.MailSender;
import dave.LitleHelper.table.model.FileTableModel;
import dave.LitleHelper.table.model.MyAbstractTableModel;

public class FilesTable extends JScrollPane {

	private MyAbstractTableModel<AffectedFile> model;

	public FilesTable(Task task, LocalDate date) {
		super();
		model = new FileTableModel();
		model.setData(task.getFiles());
		JTable table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}
		};
		table.setPreferredScrollableViewportSize(new Dimension(600, 100));
		table.setModel(model);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.addKeyListener(new DeleteTableRow(table, model));

		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) {
					MailSender.send(Settings.getInstance().getValue(PropertyValues.EMAIL_TO).split(";"), "Test mail",
							"Test text");
				}
			}
		});

		table.setTransferHandler(new CopyHandler());
		setViewportView(table);
	}

	public TableModel getModel() {
		return model;
	}

}
