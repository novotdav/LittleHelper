package dave.LitleHelper.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Table;

import dave.LitleHelper.enums.WorkspaceType;

@Entity
@Table(name = "workspace")
public class Workspace extends AbstractEntity {

	@Column
	private String name;

	@Column
	private String path;

	@Enumerated(EnumType.STRING)
	@Column
	private WorkspaceType type;

	public Workspace() {
	}

	public Workspace(String name, String path, WorkspaceType type) {
		this.name = name;
		this.path = path;
		this.type = type;
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

	public WorkspaceType getType() {
		return type;
	}
}
