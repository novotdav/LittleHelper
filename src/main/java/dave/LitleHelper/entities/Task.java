package dave.LitleHelper.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;

import dave.LitleHelper.enums.TaskState;
import dave.LitleHelper.panel.TaskEditPane;

@Entity
@Table(name = "tasks")
public class Task extends AbstractEntity {

	@Transient
	private TaskEditPane editPane;

	@OneToMany(mappedBy = "task")
	@Cascade({ CascadeType.MERGE })
	private List<TimeInterval> times;

	@Column(name = "hp")
	private String hp;

	@Column(name = "description")
	private String description;

	@ManyToOne
	// @Column(name = "workspace")
	private Workspace workspace;

	@Column
	@Lob
	private String notes;

	@OneToMany(mappedBy = "task")
	@Cascade({ CascadeType.MERGE })
	private List<AffectedFile> files;

	@Column(name = "state")
	@Enumerated(EnumType.STRING)
	private TaskState state;

	public Task() {
		times = new ArrayList<>();
		files = new ArrayList<>();
	}

	public TaskEditPane createEditPane(LocalDate date) {
		editPane = new TaskEditPane(this, date);
		return editPane;
	}

	public void addTimeInterval(TimeInterval t) {
		if (times == null) {
			times = new ArrayList<>();
		}

		times.add(t);
		t.setTask(this);
	}

	public List<TimeInterval> getTimes() {
		return times;
	}

	public void setTimes(List<TimeInterval> times) {
		this.times = times;
	}

	public String getHp() {
		return hp;
	}

	public void setHp(String hp) {
		this.hp = hp;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Workspace getWorkspace() {
		return workspace;
	}

	public void setWorkspace(Workspace workspace) {
		this.workspace = workspace;
	}

	public String getNotes() {
		return notes;
	}

	public void setNotes(String notes) {
		this.notes = notes;
	}

	public List<AffectedFile> getFiles() {
		return files;
	}

	public void setFiles(List<AffectedFile> files) {
		this.files = files;
	}

	public TaskState getState() {
		return state;
	}

	public void setState(TaskState state) {
		this.state = state;
	}

	public TaskEditPane getEditPane() {
		return editPane;
	}

	public void setEditPane(TaskEditPane editPane) {
		this.editPane = editPane;
	}

	public Task update() {
		editPane.update();
		return this;
	}

	@Override
	public String toString() {
		return hp + " - " + description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((hp == null) ? 0 : hp.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Task other = (Task) obj;
		if (hp == null) {
			if (other.getHp() != null)
				return false;
		} else if (!hp.equals(other.getHp()))
			return false;
		return true;
	}

}