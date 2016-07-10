package dave.LitleHelper.tree;

import dave.LitleHelper.entities.Task;

public class TaskNode extends AbstractNode {

	private Task task;

	public TaskNode(Task task) {
		this.task = task;
		value = task.toString();
	}

	public Task getTask() {
		return task;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((task == null) ? 0 : task.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskNode other = (TaskNode) obj;
		if (task == null) {
			if (other.task != null)
				return false;
		} else if (!task.equals(other.task))
			return false;
		return true;
	}

	@Override
	public int compareTo(AbstractNode o) {
		return o.getValue().compareTo(value);
	}

}
