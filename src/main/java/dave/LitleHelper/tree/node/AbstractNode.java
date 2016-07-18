package dave.LitleHelper.tree.node;

import java.util.Enumeration;
import java.util.TreeSet;

import javax.swing.tree.TreeNode;

public abstract class AbstractNode implements TreeNode, Comparable<AbstractNode> {

	protected String value;
	protected TreeSet<AbstractNode> children;
	protected AbstractNode parent;

	public int getChildCount() {
		return children.size();
	}

	public TreeSet<AbstractNode> getChildren() {
		return children;
	}

	public AbstractNode getChildAt(int index) {
		int i = 0;
		for (AbstractNode child : children) {
			if (i++ == index) {
				return child;
			}
		}

		return null;
	}

	public AbstractNode getChildByValue(String value) {
		for (AbstractNode child : children) {
			if (child.getValue().equals(value)) {
				return child;
			}
		}

		return null;
	}

	public AbstractNode addChild(AbstractNode child) {
		children.add(child);
		child.setParent(this);
		return child;
	}

	public void removeAllChildren() {
		children.clear();
	}

	@Override
	public int getIndex(TreeNode node) {
		for (int i = 0; i < node.getChildCount(); i++) {
			if (node.getChildAt(i).equals(this)) {
				return i;
			}
		}

		return -1;
	}

	@Override
	public boolean getAllowsChildren() {
		return !(this instanceof TaskNode);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Enumeration children() {
		return null;
	}

	public AbstractNode getParent() {
		return parent;
	}

	public void setParent(AbstractNode parent) {
		this.parent = parent;
	}

	public String getValue() {
		return value;
	}

	public boolean isDayNode() {
		return false;
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean isLeaf() {
		return children == null;
	}
}
