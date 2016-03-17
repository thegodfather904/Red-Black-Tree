
/*Stack used for our inorder traversal (the inRange() method for this project
 *
 * Using a linked list for implementation because in a balanced tree, about log base 2 records could be placed in the stack, but as a
 * RB tree isn't fully guarunteed, we can't rely on this number.
 *
 *
 * */
public class NodeStack {

	private Link top;

	public NodeStack(){

	}

	/*push a new node onto the stack*/
	public void push(Node node){
		Link link = new Link(node);
		link.setChild(top);
		top = link;
	}

	/*pop a node off the stack*/
	public Node pop(){
		Node popNode = top.getNode();
		top = top.getChild();
		return popNode;
	}

	public boolean isNotEmpty(){
		return top != null;
	}

}
