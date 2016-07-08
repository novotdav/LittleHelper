package dave.LitleHelper.tree;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import dave.LitleHelper.entities.Task;

public class MyNode {

	private final String value;
	private Map<Integer, MyNode> children;
	private Set<Task> tasks;

	public MyNode(String value) {
		this.value = value;
		children = new TreeMap<>();
	}

	public int getChildCount() {
		return children.size();
	}

	public Map<Integer, MyNode> getChildren() {
		return children;
	}

	public MyNode getChild(int key) {
		return children.get(key);
	}

	public MyNode addChild(Integer key, MyNode child) {
		children.put(key, child);
		return child;
	}

	public void removeChild(Integer key) {
		children.remove(key);
	}

	public Set<Task> getTasks() {
		return tasks;
	}

	public void addTask(Task task) {
		if (tasks == null) {
			tasks = new TreeSet<>();
		}

		tasks.add(task);
	}

	public void addAllTasks(Collection<Task> tasks) {
		if (this.tasks == null) {
			this.tasks = new TreeSet<>();
		}

		this.tasks.addAll(tasks);
	}

	@Override
	public String toString() {
		return value;
	}

	public boolean isLeaf() {
		return children == null;
	}

}
