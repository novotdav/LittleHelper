package dave.LitleHelper.entities;

import static java.time.temporal.ChronoUnit.MINUTES;

import java.time.LocalDate;
import java.time.LocalTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

@Entity
@Table(name = "time_interval")
@FilterDef(name = "date", parameters = @ParamDef(name = "date", type = "java.time.LocalDate"))
public class TimeInterval extends AbstractEntity {

	@Column
	private LocalDate date;

	@ManyToOne
	private Task task;

	@Column(name = "time_from")
	private LocalTime from;

	@Column(name = "time_to")
	private LocalTime to;

	@Column
	private float duration = 0;

	public TimeInterval() {
	}

	public TimeInterval(LocalDate date, Task task) {
		this.date = date;
		this.task = task;
	}

	public TimeInterval(LocalTime from, LocalTime to) {
		this.from = from;
		this.to = to;
	}

	public TimeInterval(String from, String to) {
		this.from = LocalTime.parse(from);
		this.to = LocalTime.parse(to);
	}

	public float getDuration() {
		if (duration == 0) {
			countDuration();
		}
		return duration;
	}

	@Override
	public void detach() {
		task = null;
	}

	public LocalTime getFrom() {
		return from;
	}

	public LocalTime getTo() {
		return to;
	}

	public void setFrom(LocalTime from) {
		this.from = from;
		if (to != null) {
			countDuration();
		}
	}

	public void setTo(LocalTime to) {
		this.to = to;
		if (from != null) {
			countDuration();
		}
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Task getTask() {
		return task;
	}

	public void setTask(Task task) {
		this.task = task;
	}

	public void setDuration(float duration) {
		this.duration = duration;
	}

	@PreUpdate
	@PrePersist
	private void countDuration() {
		duration = (float) MINUTES.between(from, to) / 60;
	}
}
