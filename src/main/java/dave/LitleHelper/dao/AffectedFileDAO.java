package dave.LitleHelper.dao;

import java.util.List;

import dave.LitleHelper.entities.AffectedFile;
import dave.LitleHelper.entities.Task;

public class AffectedFileDAO extends AbstractDAO<AffectedFile> {

	@Override
	public List<AffectedFile> findAll() {
		return em.createQuery("select f from AffectedFile f ", AffectedFile.class).getResultList();
	}

	@Override
	public void remove(AffectedFile item) {
		em.getTransaction().begin();
		AffectedFile entity = em.find(AffectedFile.class, item.getId());
		Task task = entity.getTaskId();
		task.getFiles().remove(entity);
		entity.setTaskId(null);
		em.remove(entity);
		em.getTransaction().commit();
	}

}
