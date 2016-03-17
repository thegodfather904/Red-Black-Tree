import java.util.ArrayList;

/*
*  Author: Tony Scialo
*  Description: Red-black tree; provides tree maintance as well as pointer to root
*/
public class RedBlackTree{

	  private Node root;

	  public RedBlackTree(){

	  }

	  /*Uses the nodeArray from the input file to build the red-black tree*/
	  public void buildRedBlackTree(Node[] nodeArray){
	    System.out.println("Building red black tree");

	    //insert first node at root (so we won't have to check if root == null each time to insert
	    root = nodeArray[0];
	    root.setRed(false);

	    //pointers to nodes we will need to reference (ggp = great grand parent, gp = grand parent, p = parent...)
	    Node gggp = null;
	    Node ggp = null;
	    Node gp = null;
	    Node p = null;
	    Node current = null;

	    //grab it so we won't have to use a ton of get calls
	    int insertId;

	    //true when a nodes been inserted
	    boolean inserted = false;

	    //insert nodes into tree
	    for(int x = 1; x < nodeArray.length; x++){

	    	//go down the tree and find insertion point (start at root)
	    	current = root;
	    	insertId = nodeArray[x].getId();
	    	inserted = false;
	    	gp = null;
	    	p = null;

	    	while(!inserted){
	    		gggp = ggp;
	    		ggp = gp;
	    		gp = p;
	    		p = current;

	    		//check for color flip on the way down (if current == black && both children == red)
	    		if(checkBlackParentRedChildren(current)){

	    			//flip children to black
	    			current.getLeftChild().switchColor();
	    			current.getRightChild().switchColor();

	    			//if root, dont switch, if not root also check for red grandparent (red on red conflict)
	    			if(current != root){

	    				current.switchColor();

	    				//if grandparent = red, red on red conflict, perform rotations
	    				if(gp.isRed()){
	    					if(gp.getRightChild() == p){
	    						//do single rotation
	    						gp.switchColor();
			    				ggp.switchColor();

			    				ggp.setRightChild(gp.getLeftChild());
			    				gp.setLeftChild(ggp);

			    				if(ggp == root)
			    					root = gp;
			    				else
			    					gggp.setRightChild(gp);
	    					}
	    					else{
	    						//do double rotation
	    					}
		    			}
	    			}
	    		}



	    		if(insertId < current.getId()){
	    			current = current.getLeftChild();

	    			//insert null insert into tree
	    			if(current == null){

	    				//if p is black, just insert
	    				if(!p.isRed())
	    					p.setLeftChild(nodeArray[x]);
	    				else{
	    					//if outside grandchild, do single rotation

	    					//else inside grandchild so do double rotation
	    				}



	    				inserted = true;
	    			}
	    		}
	    		else{
	    			current = current.getRightChild();

	    			//if null, insert into tree
	    			if(current == null){

	    				//if p is black, just insert
	    				if(!p.isRed())
	    					p.setRightChild(nodeArray[x]);
	    				else{

	    					if(gp.getRightChild() == p){
	    						//outside grandchild, do flip colors and do single rotation
	    						gp.switchColor();
	    						p.switchColor();

	    						p.setLeftChild(gp);
	    						p.setRightChild(nodeArray[x]);
	    						gp.setRightChild(null);

	    						//check to make sure we still have a pointer to the root
	    						if(root == gp)
	    							root = p;
	    						else
	    							ggp.setRightChild(p);

	    					}
	    					else{
	    						//else inside grandchild so do double rotation
	    					}

	    				}

	    				inserted = true;
	    			}
	    		}


	    	}


	    }

		System.out.println("Finished building red black tree");

	  }

  	/*Checks for a black parent with 2 red children*/
	private boolean checkBlackParentRedChildren(Node current){
		if (current.hasTwoChildren() && !current.isRed() && current.getLeftChild().isRed() && current.getRightChild().isRed())
			return true;
		else
			return false;
	}

	/*Returns the count of the node if found, else returns 0*/
	public int findCount(int searchId){

		Node current = root;

		int currentId;

		while(current != null){

			currentId = current.getId();

			if (searchId < currentId)
				current = current.getLeftChild();
			else if(searchId > currentId)
				current = current.getRightChild();
			else if(searchId == currentId)
				return current.getCount();
		}

		//if it reaches this point, no id was found, return 0;
		return 0;
	}

	/*Find the next node that has loweset id where ID > searchID*/
	/*Logic is as follows:
	 *
	 * Find node
	 * if node has right child
	 * 	successor equals min of right subtree
	 * else if node is left child of parent
	 * 	successor equals parent
	 * else
	 * 	if it has a right parent at some point (we store this on the way down the tree)
	 * 		grandparent equals successor
	 * 	else
	 * 		no successor
	 *
	 */
	public Node findSuccessor(int searchId){

		Node current = root;
		Node p = null;
		Node rightParent = null;	//stores the last right parent (stores when we go left)

		//first find node
		while(current != null){
			if(searchId == current.getId()){

				if(current.hasRightChild())
					return findMinNode(current.getRightChild());
				else if(current == p.getLeftChild())
					return(p);
				else{
					//the last time we went right down the tree is the successor
					if(rightParent != null)
						return rightParent;
					else
						return new Node(0,0);
				}
			}
			else if (searchId < current.getId()){

				rightParent = current;
				p = current;
				current = current.getLeftChild();
			}
			else{
				p = current;
				current = current.getRightChild();
			}
		}

		return new Node (0, 0);
	}

	/*Find the next node that has highest id where ID < searchID*/
	/*Logic is as follows:
	 *
	 * Find node
	 * if node has left child
	 * 	successor equals max of right subtree
	 * else if node is right child of parent
	 * 	successor equals parent
	 * else
	 * 	if it has a left parent at some point (we store this on the way down the tree)
	 * 		grandparent equals successor
	 * 	else
	 * 		no successor
	 *
	 */
	public Node findPredecessor(int searchId){

		Node current = root;
		Node p = null;
		Node leftParent = null;	//stores the last left parent (stores when we go right)

		//first find node
		while(current != null){
			if(searchId == current.getId()){

				if(current.hasLeftChild())
					return findMaxNode(current.getLeftChild());
				else if(current == p.getRightChild())
					return(p);
				else{
					//the last time we went right down the tree is the successor
					if(leftParent != null)
						return leftParent;
					else
						return new Node(0,0);
				}
			}
			else if (searchId < current.getId()){
				p = current;
				current = current.getLeftChild();
			}
			else{
				leftParent = current;
				p = current;
				current = current.getRightChild();
			}
		}

		return new Node (0, 0);
	}

	/*Finds the maximum node for a gven subtree*/
	private Node findMaxNode(Node subtree){
		Node current = subtree;

		while(current.hasRightChild())
			current = current.getRightChild();

		return current;
	}

	/*Finds the minimum node for a given subtree*/
	private Node findMinNode(Node subtree){

		Node current = subtree;

		while(current.hasLeftChild())
			current = current.getLeftChild();

		return current;
	}

	/*Returns true if the current node is an inside grandchild of the grandparent*/
	private boolean isInsideGrandchild(Node current, Node p, Node gp){
		if(gp.leftChildIs(p) && p.rightChildIs(current))
			return true;
		else if (gp.rightChildIs(p) && p.leftChildIs(current))
			return true;
		else
			return false;
	}

	/*
	 * Returns the count of the number of items in the range inclusively
	 * Logic is as follows:
	 *
	 * while node not found
	 * 	pop current onto nodeArray stack
	 *
	 *
	 * */
	public int numberInRange(int start, int end){

		NodeStack stack = new NodeStack();

		Node current = root;

		int numInRange = 0;

		int currentId = -1;

		//while searching for node, pop any node traversed onto stack
		while(current != null){

			currentId = current.getId();

			//only push items onto our stack that are in our range
			if(currentId >= start && currentId <= end)
				stack.push(current);

			if(start == currentId)
				current = null;
			else{
				if(start < current.getId())
					current = current.getLeftChild();

				else if (start > current.getId())
					current = current.getRightChild();
				}
		}

		//while still nodes in our stack
		while(stack.isNotEmpty()){

			//pop the item
			current = stack.pop();
			numInRange++;

			//if current has right child, push it and go down its left path
			if(current.hasRightChild()){
				current = current.getRightChild();
				currentId = current.getId();
				if(currentId >= start && currentId <= end)
					stack.push(current);

				while(current.hasLeftChild()){
					current = current.getLeftChild();
					currentId = current.getId();

					//only push if in range
					if(currentId >= start && currentId <= end)
						stack.push(current);
				}
			}
		}

		return numInRange;
	}


	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
