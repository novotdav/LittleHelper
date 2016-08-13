package dave.LitleHelper.entities;

import java.io.Serializable;

import dave.LitleHelper.dao.WorkspaceDAO;
import dave.LitleHelper.enums.WorkspaceType;

public class Workspace extends AbstractEntity<Workspace> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2402568445137368021L;

	private String name;

	private String path;

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

	@Override
	public WorkspaceDAO getDao() {
		return WorkspaceDAO.getInstance();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Workspace other = (Workspace) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

	@Override
	public int compareTo(Workspace o) {
		return name.compareTo(o.getName());
	}
}
