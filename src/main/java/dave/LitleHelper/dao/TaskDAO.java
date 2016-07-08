package dave.LitleHelper.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.criteria.CriteriaBuilder;

import org.hibernate.Session;

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

	public List<Task> findFilter(LocalDate date) {
		// CriteriaQuery<Task> criteria = builder.createQuery(Task.class);
		// Root<Task> root = criteria.from(Task.class);
		// Join<Task, TimeInterval> times = root.join("times");
		// criteria.where(builder.equal(times.get("date"), date));
		//
		// return em.createQuery(criteria).getResultList();

		Session session = em.unwrap(Session.class);

		// to remove previous results from cache
		session.clear();
		session.enableFilter("date").setParameter("date", date);

		List<Task> tasks = new ArrayList<>();

		// tasks.addAll(
		// em.createQuery("select t from Task t LEFT OUTER JOIN FETCH t.times i
		// where i.date = :date", Task.class)
		// .setParameter("date", date).getResultList());

		// tasks.addAll(em.createQuery("select t from Task t LEFT JOIN FETCH
		// t.times i WHERE t.state = :state", Task.class)
		// .setParameter("state", TaskState.IN_PROGRESS).getResultList());

		tasks.addAll(em.createQuery("select t from Task t LEFT JOIN FETCH t.times i", Task.class).getResultList());

		// tasks.addAll(em.createQuery(
		// "select t from Task t JOIN t.times times WHERE NOT EXISTS (SELECT ti
		// FROM TimeInterval ti WHERE ti = times)",
		// Task.class).getResultList());

		return tasks;
	}
}
