/*Link in the linked list used in the stack*/
public class Link {

	private Node node;
	private Link child;

	public Link(){

	}

	public Link(Node node){
		this.node = node;
	}

	public Node getNode() {
		return node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public Link getChild() {
		return child;
	}

	public void setChild(Link child) {
		this.child = child;
	}


}
