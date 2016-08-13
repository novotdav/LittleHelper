package dave.LitleHelper.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Arrays;
import java.util.EnumMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.io.IOUtils;

import dave.LitleHelper.Settings;
import dave.LitleHelper.Settings.PropertyValues;
import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.exception.ValidationException;
import dave.LitleHelper.exception.ValidationException.ValErr;

public class WorkspaceDAO extends AbstractDAO<Workspace> {

	public static WorkspaceDAO instance;

	private EnumMap<WorkspaceType, Set<Workspace>> workspaces;

	private Settings settings = Settings.getInstance();

	@SuppressWarnings("unchecked")
	private WorkspaceDAO() {
		File wrkData = new File(settings.getValue(PropertyValues.WRK_DB_FILE_NAME));
		if (wrkData.exists()) {
			ObjectInputStream ois = null;
			FileInputStream fis = null;
			try {
				fis = new FileInputStream(wrkData);
				ois = new ObjectInputStream(fis);
				workspaces = (EnumMap<WorkspaceType, Set<Workspace>>) ois.readObject();
			} catch (IOException e) {
				throw new LittleException(Err.DB_LOAD, e);
			} catch (ClassNotFoundException e) {
				throw new LittleException(Err.DEFAULT, e);
			} finally {
				IOUtils.closeQuietly(fis);
				IOUtils.closeQuietly(ois);
			}
		} else {
			workspaces = new EnumMap<>(WorkspaceType.class);
			Arrays.asList(WorkspaceType.values()).forEach(type -> {
				workspaces.put(type, new TreeSet<>());
			});
		}
	}

	public void saveData() {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;

		try {
			fos = new FileOutputStream(settings.getValue(PropertyValues.WRK_DB_FILE_NAME));
			oos = new ObjectOutputStream(fos);
			oos.writeObject(workspaces);
		} catch (IOException e) {
			throw new LittleException(Err.DB_WRITE, e);
		} finally {
			IOUtils.closeQuietly(fos);
			IOUtils.closeQuietly(oos);
		}
	}

	public static WorkspaceDAO getInstance() {
		if (instance == null) {
			instance = new WorkspaceDAO();
		}

		return instance;
	}

	@Override
	public Workspace merge(Workspace workspace) {
		if (workspace == null) {
			return workspace;
		}

		if (workspace.getType() == null) {
			throw new ValidationException(ValErr.WRK_NULL_TYPE);
		}

		Set<Workspace> wrkSet = workspaces.get(workspace.getType());
		if (wrkSet.contains(workspace)) {
			throw new ValidationException(ValErr.WRK_DUPLICTY);
		}

		wrkSet.add(workspace);
		return workspace;
	}

	@Override
	public List<Workspace> findAll() {
		throw new LittleException(Err.NOT_SUPPORTED);
	}

	@Override
	public void remove(Workspace workspace) {
		if (workspace == null) {
			return;
		}

		if (workspace.getType() == null) {
			throw new ValidationException(ValErr.WRK_NULL_TYPE);
		}

		workspaces.get(workspace.getType()).remove(workspace);
	}

	public Set<Workspace> findByType(WorkspaceType type) {
		return workspaces.get(type);
	}
}
