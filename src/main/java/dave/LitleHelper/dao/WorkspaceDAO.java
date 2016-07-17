package dave.LitleHelper.dao;

import java.util.List;

import dave.LitleHelper.entities.Workspace;
import dave.LitleHelper.enums.WorkspaceType;

public class WorkspaceDAO extends AbstractDAO<Workspace> {

	@Override
	public List<Workspace> findAll() {
		return em.createQuery("select w from Workspace w ", Workspace.class).getResultList();
	}

	@Override
	public void remove(Workspace item) {
		em.getTransaction().begin();
		Workspace entity = em.find(Workspace.class, item.getId());
		em.remove(entity);
		em.getTransaction().commit();
	}

	public List<Workspace> findByType(WorkspaceType type) {
		return em.createQuery("select w from Workspace w where w.type = :type", Workspace.class)
				.setParameter("type", type).getResultList();
	}
}
