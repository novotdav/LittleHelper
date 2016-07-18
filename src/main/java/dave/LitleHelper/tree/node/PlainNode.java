package dave.LitleHelper.tree.node;

import java.util.TreeSet;

public class PlainNode extends AbstractNode {

	public PlainNode(String value) {
		this.value = value;

		children = new TreeSet<>();
	}

	@Override
	public int compareTo(AbstractNode o) {
		return value.compareTo(o.getValue());
	}

}
