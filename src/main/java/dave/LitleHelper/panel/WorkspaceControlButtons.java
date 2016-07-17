package dave.LitleHelper.panel;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.JPanel;

import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.listener.AddExistingWorkspaceListener;
import dave.LitleHelper.listener.EditExistingWorkspaceListener;
import dave.LitleHelper.table.WorkspaceTable;

public class WorkspaceControlButtons extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2002413599021391079L;
	private WorkspaceTable table;
	private WorkspaceType type;

	public WorkspaceControlButtons(WorkspaceTable table, WorkspaceType type) {
		this.table = table;
		this.type = type;
		initButtonsComponents();
	}

	private void initButtonsComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		JButton addExisting = new JButton("Pridej existujici");
		addExisting.addActionListener(new AddExistingWorkspaceListener(table, type));
		Dimension prefSize = addExisting.getPreferredSize();
		Dimension size = addExisting.getSize();

		JButton addNew = new JButton("Novy");
		addNew.setPreferredSize(prefSize);
		addNew.setSize(size);

		JButton update = new JButton("Aktualizuj");
		update.setPreferredSize(prefSize);
		update.setSize(size);

		JButton edit = new JButton("Uprav");
		edit.setPreferredSize(prefSize);
		edit.setSize(size);
		edit.addActionListener(new EditExistingWorkspaceListener(table));

		add(addExisting, c);

		c.gridy = 1;
		add(addNew, c);

		c.gridy = 2;
		add(update, c);

		c.gridy = 3;
		add(edit, c);
	}
}
