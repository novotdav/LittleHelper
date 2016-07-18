package dave.LitleHelper.tree.node;

public class UESBuildNode extends AbstractNode {

	public UESBuildNode(String value) {
		this.value = value;
	}

	@Override
	public int compareTo(AbstractNode o) {
		return value.compareTo(o.getValue());
	}

}
