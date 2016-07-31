package dave.LitleHelper.table;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import javax.swing.JTable;

import dave.LitleHelper.entities.Task;
import dave.LitleHelper.listener.IntegrationMailController;

public class FilesTableContextMenu extends MouseAdapter {

	private JPopupMenu menu;

	public FilesTableContextMenu(JTable table, Task task) {
		menu = new JPopupMenu();

		JMenuItem integrationMailItem = new JMenuItem("Integracni email");
		integrationMailItem.addActionListener(new IntegrationMailController(table, task));
		menu.add(integrationMailItem);
	}

	@Override
	public void mousePressed(MouseEvent ev) {
		if (ev.isPopupTrigger()) {
			menu.show(ev.getComponent(), ev.getX(), ev.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent ev) {
		if (ev.isPopupTrigger()) {
			menu.show(ev.getComponent(), ev.getX(), ev.getY());
		}
	}

}
