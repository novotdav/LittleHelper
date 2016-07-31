package dave.LitleHelper.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceException;

import dave.LitleHelper.entities.AbstractEntity;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;

abstract public class AbstractDAO<T extends AbstractEntity<?>> {
	protected static EntityManager em;

	protected AbstractDAO() {
		if (em == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("littleHelper");
			em = factory.createEntityManager();
		}
	}

	public T merge(T entity) {
		T persistedEntity;

		try {
			em.getTransaction().begin();
			persistedEntity = em.merge(entity);
			em.getTransaction().commit();
		} catch (PersistenceException e) {
			if (em.getTransaction().isActive()) {
				em.getTransaction().rollback();
			}
			throw new LittleException(Err.ENTITY_SAVE, e);
		}

		return persistedEntity;
	};

	abstract public List<T> findAll();

	abstract public void remove(T item);
}
