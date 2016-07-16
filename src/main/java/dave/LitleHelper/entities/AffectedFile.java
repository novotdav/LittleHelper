package dave.LitleHelper.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "files")
public class AffectedFile extends AbstractEntity {

	@ManyToOne
	private Task task;

	@Column
	private String name;

	@Column
	private String path;

	public AffectedFile() {
	};

	public AffectedFile(String name, String path, Task task) {
		this.name = name;
		this.path = path;
		this.task = task;
	}

	public Task getTaskId() {
		return task;
	}

	public void setTaskId(Task task) {
		this.task = task;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return name + " - " + path;
	}
}
