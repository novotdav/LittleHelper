package dave.LitleHelper.dao;

import java.util.List;

import dave.LitleHelper.entities.Task;
import dave.LitleHelper.entities.TimeInterval;

public class TimeIntervalDAO extends AbstractDAO<TimeInterval> {

	@Override
	public List<TimeInterval> findAll() {
		return em.createQuery("select t from TimeInterval t ", TimeInterval.class).getResultList();
	}

	@Override
	public void remove(TimeInterval item) {
		em.getTransaction().begin();
		TimeInterval entity = em.find(TimeInterval.class, item.getId());
		Task task = entity.getTask();
		task.getTimes().remove(entity);
		entity.setTask(null);
		em.remove(entity);
		em.getTransaction().commit();
	}

}
