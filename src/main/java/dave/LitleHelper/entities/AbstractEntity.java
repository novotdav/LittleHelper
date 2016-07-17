package dave.LitleHelper.entities;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import dave.LitleHelper.dao.AbstractDAO;

@MappedSuperclass
public abstract class AbstractEntity<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected long id;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	abstract public AbstractDAO<? extends AbstractEntity<?>> getDao();
}
