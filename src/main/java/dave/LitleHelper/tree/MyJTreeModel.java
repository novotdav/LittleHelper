package dave.LitleHelper.tree;

import java.time.LocalDate;
import java.util.List;

import javax.swing.tree.DefaultTreeModel;

import dave.LitleHelper.utils.DateUtils;

public class MyJTreeModel extends DefaultTreeModel {

	private AbstractNode root;

	public MyJTreeModel() {
		this(new RootNode());
	}

	private MyJTreeModel(AbstractNode root) {
		super(root);
		this.root = root;
	}

	@Override
	public Object getRoot() {
		return root;
	}

	public void reload() {
		super.reload();
	}

	public void generateStructure(List<LocalDate> dates) {
		dates.forEach(this::addDay);
	}

	public void addDay(LocalDate date) {
		AbstractNode yearNode = root.getChildByValue(date.format(DateUtils.getYearFormatter()));

		if (yearNode == null) {
			yearNode = root.addChild(new DateNode(date, DateUtils.getYearFormatter()));
		}

		AbstractNode weekNode = yearNode.getChildByValue(date.format(DateUtils.getWeekFormatter()));

		if (weekNode == null) {
			weekNode = yearNode.addChild(new DateNode(date, DateUtils.getWeekFormatter()));
		}

		AbstractNode dayNode = weekNode.getChildByValue(date.format(DateUtils.getDayFormatter()));

		if (dayNode == null) {
			dayNode = weekNode.addChild(new DateNode(date, DateUtils.getDayFormatter(), true));
		}
	}
}
