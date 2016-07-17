package dave.LitleHelper.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import dave.LitleHelper.entities.AbstractEntity;

abstract public class AbstractDAO<T extends AbstractEntity<?>> {
	protected static EntityManager em;

	protected AbstractDAO() {
		if (em == null) {
			EntityManagerFactory factory = Persistence.createEntityManagerFactory("littleHelper");
			em = factory.createEntityManager();
		}
	}

	public T merge(T entity) {
		em.getTransaction().begin();
		T persistedEntity = em.merge(entity);
		em.getTransaction().commit();
		return persistedEntity;
	};

	abstract public List<T> findAll();

	abstract public void remove(T item);
}
