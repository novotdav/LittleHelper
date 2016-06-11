package dave.LitleHelper.dao;

import java.time.LocalDate;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import dave.LitleHelper.entities.Task;

public class TaskDAO {

	private EntityManager em;
	CriteriaBuilder builder;

	public TaskDAO() {
		EntityManagerFactory factory = Persistence.createEntityManagerFactory("littleHelper");
		em = factory.createEntityManager();
		builder = em.getCriteriaBuilder();
	}

	public List<Task> findAll(LocalDate date) {
		// CriteriaQuery<Task> cq = builder.createQuery(Task.class);

		List<Task> tasks = em.createQuery("select t from Task t ", Task.class).getResultList();

		return tasks;
	}

}
