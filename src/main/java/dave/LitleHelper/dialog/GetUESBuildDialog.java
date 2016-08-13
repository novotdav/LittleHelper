package dave.LitleHelper.dialog;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.net.URL;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.TreePath;

import dave.LitleHelper.exception.ValidationException;
import dave.LitleHelper.exception.ValidationException.ValErr;
import dave.LitleHelper.tree.model.UESBuildTreeModel;
import dave.LitleHelper.tree.node.AbstractNode;
import dave.LitleHelper.tree.node.PlainNode;
import dave.LitleHelper.tree.node.UESBuildNode;
import dave.LitleHelper.utils.UESBuildUtils;

public class GetUESBuildDialog extends JDialog {

	private JPanel parent;
	private URL result;

	public GetUESBuildDialog(JPanel parent) {
		this.parent = parent;
		initComponents();
	}

	private void initComponents() {
		setLayout(new BorderLayout());
		setModalityType(DEFAULT_MODALITY_TYPE);
		setPreferredSize(new Dimension(500, 500));
		setSize(new Dimension(500, 500));

		UESBuildTreeModel model = new UESBuildTreeModel();
		JTree tree = new JTree(model);
		model.generateStructure();

		JPanel buttons = new JPanel();
		JButton okButton = new JButton("OK");
		okButton.addActionListener(e -> {
			TreePath selectedPath = tree.getSelectionPath();
			AbstractNode node = (AbstractNode) selectedPath.getLastPathComponent();
			if (node instanceof UESBuildNode) {
				String mainVersion = ((PlainNode) selectedPath.getPathComponent(selectedPath.getPathCount() - 2))
						.getValue();
				String subVersion = node.getValue();
				String uesBuildType = ((PlainNode) selectedPath.getPathComponent(selectedPath.getPathCount() - 3))
						.getValue();
				result = UESBuildUtils.getBuildUrl(uesBuildType, mainVersion, subVersion);
			} else {
				throw new ValidationException(ValErr.BUILD_WRONG_NODE_SELECTED);
			}

			dispose();
		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(e -> {
			dispose();
		});

		buttons.add(okButton);
		buttons.add(cancelButton);

		add(new JScrollPane(tree), BorderLayout.CENTER);
		add(buttons, BorderLayout.SOUTH);
	}

	public URL showDialog() {
		pack();
		setLocationRelativeTo(parent);
		setVisible(true);

		return result;
	}
}
