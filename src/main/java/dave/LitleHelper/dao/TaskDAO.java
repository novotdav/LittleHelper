package dave.LitleHelper.dao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.exception.ConstraintViolationException;

import dave.LitleHelper.entities.Task;
import dave.LitleHelper.exception.LittleException;
import dave.LitleHelper.exception.LittleException.Err;
import dave.LitleHelper.exception.ValidationException;
import dave.LitleHelper.exception.ValidationException.ValErr;

public class TaskDAO extends AbstractDAO<Task> {

	@Override
	public List<Task> findAll() {
		return em.createQuery("select t from Task t ", Task.class).getResultList();
	}

	public List<Task> findByDate(LocalDate date) {
		Session session = em.unwrap(Session.class);

		// to remove previous results from cache
		session.clear();
		return em.createQuery("select t from Task t JOIN FETCH t.times i where i.date = :date", Task.class)
				.setParameter("date", date).getResultList();
	}

	public List<Task> findAllByDate(LocalDate date) {
		Session session = em.unwrap(Session.class);

		// to remove previous results from cache
		session.clear();
		session.enableFilter("date").setParameter("date", date);

		List<Task> tasks = new ArrayList<>();
		tasks.addAll(em.createQuery("select t from Task t LEFT JOIN FETCH t.times i", Task.class).getResultList());
		session.disableFilter("date");

		return tasks;
	}

	public List<LocalDate> findAllDays() {
		return em.createQuery("select DISTINCT(t.date) from TimeInterval t", LocalDate.class).getResultList();
	}

	@Override
	public void remove(Task item) {
		throw new LittleException(Err.NOT_SUPPORTED);
		// em.getTransaction().begin();
		// Task entity = em.find(Task.class, item.getId());
		// em.remove(entity);
		// em.getTransaction().commit();
	}

	@Override
	public Task merge(Task entity) {
		try {
			return super.merge(entity);
		} catch (LittleException e) {
			if (e.getCause().getCause().getCause() instanceof ConstraintViolationException) {
				throw new ValidationException(ValErr.TASK_DUPLICITY, e);
			} else {
				throw e;
			}
		}
	}

	public Task createTask() {
		return merge(new Task());
	}
}
