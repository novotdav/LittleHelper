package dave.LitleHelper.panel;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;

import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.table.WorkspaceTable;

public class WorkspacePane extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1020924043708997579L;
	private int verticalIndex = 0;

	public WorkspacePane() {
		initComponents();
	}

	private void initComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 5, 5, 5);

		c.gridy = verticalIndex++;
		c.gridx = 0;

		add(new JLabel(WorkspaceType.LEGACY.toString()), c);
		WorkspaceTable wt = new WorkspaceTable(WorkspaceType.LEGACY);
		c.gridx = 1;
		add(wt, c);
		c.gridx = 2;
		add(new WorkspaceControlButtons(wt, WorkspaceType.LEGACY), c);

		c.gridy = verticalIndex++;
		c.gridx = 0;

		add(new JLabel(WorkspaceType.CMD.toString()), c);
		WorkspaceTable wt1 = new WorkspaceTable(WorkspaceType.CMD);
		c.gridx = 1;
		add(wt1, c);
		c.gridx = 2;
		add(new WorkspaceControlButtons(wt1, WorkspaceType.CMD), c);

		c.gridy = verticalIndex++;
		c.gridx = 0;

		add(new JLabel(WorkspaceType.OTHER.toString()), c);
		WorkspaceTable wt2 = new WorkspaceTable(WorkspaceType.OTHER);
		c.gridx = 1;
		add(wt2, c);
		c.gridx = 2;
		add(new WorkspaceControlButtons(wt2, WorkspaceType.OTHER), c);

	}
}
