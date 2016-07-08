package dave.LitleHelper.tree;

import java.time.LocalDate;
import java.time.temporal.ChronoField;
import java.util.List;

import javax.swing.event.TreeModelListener;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;

public class MyJTreeModel implements TreeModel {

	private MyNode root = new MyNode("root");

	@Override
	public Object getRoot() {
		return root;
	}

	@Override
	public Object getChild(Object parent, int index) {
		return ((MyNode) parent).getChild(index);
	}

	@Override
	public int getChildCount(Object parent) {
		return ((MyNode) parent).getChildCount();
	}

	@Override
	public boolean isLeaf(Object node) {
		return ((MyNode) node).isLeaf();
	}

	@Override
	public void valueForPathChanged(TreePath path, Object newValue) {
	}

	@Override
	public int getIndexOfChild(Object parent, Object child) {
		return 0;
	}

	@Override
	public void addTreeModelListener(TreeModelListener l) {
	}

	@Override
	public void removeTreeModelListener(TreeModelListener l) {
	}

	public void generateStructure(List<LocalDate> dates) {
		dates.forEach(this::addDay);
	}

	public void addDay(LocalDate date) {
		Integer year = date.get(ChronoField.YEAR);
		Integer week = date.get(ChronoField.ALIGNED_WEEK_OF_YEAR);
		Integer day = date.get(ChronoField.DAY_OF_MONTH);

		MyNode yearNode = root.getChild(year);

		if (yearNode == null) {
			yearNode = root.addChild(year, new MyNode(year.toString()));
		}

		MyNode weekNode = yearNode.getChild(week);

		if (weekNode == null) {
			weekNode = yearNode.addChild(week, new MyNode(week.toString()));
		}

		MyNode dayNode = weekNode.getChild(day);

		if (dayNode == null) {
			dayNode = weekNode.addChild(day, new MyNode(day.toString()));
		}
	}
}
