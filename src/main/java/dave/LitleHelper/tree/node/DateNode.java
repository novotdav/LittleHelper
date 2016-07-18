package dave.LitleHelper.tree.node;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.TreeSet;

public class DateNode extends AbstractNode {

	private LocalDate date;

	private boolean dayNode;

	public DateNode(LocalDate day, DateTimeFormatter formatter) {
		this(day, formatter, false);
	}

	public DateNode(LocalDate day, DateTimeFormatter formatter, boolean dayNode) {
		date = day;
		value = day.format(formatter);
		children = new TreeSet<>();
		this.dayNode = dayNode;
	}

	public LocalDate getDate() {
		return date;
	}

	@Override
	public boolean isDayNode() {
		return dayNode;
	}

	@Override
	public int compareTo(AbstractNode o) {
		if (o instanceof DateNode) {
			DateNode dn = (DateNode) o;
			return date.compareTo(dn.getDate());
		}

		return value.compareTo(o.getValue());
	}
}
