package dave.LitleHelper.tree;

import java.util.TreeSet;

public class RootNode extends AbstractNode {

	public RootNode() {
		children = new TreeSet<>();
		value = "Úkoly";
	}

	@Override
	public int compareTo(AbstractNode o) {
		return 0;
	}

}
