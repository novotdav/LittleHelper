package dave.LitleHelper.tree.model;

import java.util.List;
import java.util.Map;

import javax.swing.tree.DefaultTreeModel;

import dave.LitleHelper.tree.node.AbstractNode;
import dave.LitleHelper.tree.node.PlainNode;
import dave.LitleHelper.tree.node.RootNode;
import dave.LitleHelper.tree.node.UESBuildNode;
import dave.LitleHelper.utils.UESBuildUtils;
import dave.LitleHelper.utils.UESBuildUtils.UESBuildType;

public class UESBuildTreeModel extends DefaultTreeModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = -913603060223425588L;
	private AbstractNode root;
	private PlainNode mNode, rNode;

	public UESBuildTreeModel() {
		this(new RootNode());
	}

	private UESBuildTreeModel(AbstractNode root) {
		super(root);
		this.root = root;

		mNode = new PlainNode("M");
		rNode = new PlainNode("R");
		root.addChild(mNode);
		root.addChild(rNode);
	}

	@Override
	public Object getRoot() {
		return root;
	}

	public void reload() {
		super.reload();
	}

	public void generateStructure() {
		mNode.removeAllChildren();
		rNode.removeAllChildren();

		addVersions(mNode, UESBuildUtils.getVersions(UESBuildType.M));
		addVersions(rNode, UESBuildUtils.getVersions(UESBuildType.R));

		fireTreeStructureChanged(this, null, null, null);
	}

	private void addVersions(AbstractNode targetNode, Map<String, List<String>> versions) {
		versions.forEach((mainVersion, subVersions) -> {
			AbstractNode versionNode = new PlainNode(mainVersion);
			subVersions.forEach(subVersion -> {
				versionNode.addChild(new UESBuildNode(subVersion));
			});

			targetNode.addChild(versionNode);
		});
	}

	public PlainNode getmNode() {
		return mNode;
	}

	public PlainNode getrNode() {
		return rNode;
	}
}
