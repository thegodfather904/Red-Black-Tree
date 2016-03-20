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
	    					if(isRightOutsideGrandchild(p, gp, ggp)){
	    						//right outside grandchild, do single rotation
	    						gp.switchColor();
			    				ggp.switchColor();

			    				ggp.setRightChild(gp.getLeftChild());
			    				gp.setLeftChild(ggp);

			    				if(ggp == root)
			    					root = gp;
			    				else
			    					gggp.setRightChild(gp);
	    					}
	    					else if(isLeftOutsideGrandchild(p, gp, ggp)){
	    						//left outside grandchild, do single rotation
	    						gp.switchColor();
			    				ggp.switchColor();

			    				ggp.setLeftChild(gp.getRightChild());
			    				gp.setRightChild(ggp);

			    				if(ggp == root)
			    					root = gp;
			    				else
			    					gggp.setLeftChild(gp);
	    					}
	    					else if (isLeftRightInsideGrandchild(p, gp, ggp)){
	    						//LR inside grandchild, do a double rotation

	    						//color flips
	    						ggp.switchColor();
	    						p.switchColor();

	    						//first rotation
	    						ggp.setLeftChild(p);
	    						gp.setRightChild(p.getLeftChild());
	    						p.setLeftChild(gp);

	    						//second rotation

	    						ggp.setLeftChild(p.getRightChild());
	    						p.setRightChild(ggp);

	    						if(root == ggp)
	    							root = p;
	    						else
	    							gggp.setLeftChild(p);

	    					}
	    					else{
	    						//RL inside grandchild, do a double rotation

	    						//color flips
	    						ggp.switchColor();
	    						p.switchColor();

	    						//first rotation
	    						ggp.setRightChild(p);
	    						gp.setLeftChild(p.getRightChild());
	    						p.setRightChild(gp);

	    						//second rotation

	    						ggp.setRightChild(p.getLeftChild());
	    						p.setLeftChild(ggp);

	    						if(root == ggp)
	    							root = p;
	    						else
	    							gggp.setRightChild(p);

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

	    					if(gp.getLeftChild() == p){
	    						//if outside grandchild, do single rotation

	    						gp.switchColor();
	    						p.switchColor();

	    						p.setRightChild(gp);
	    						p.setLeftChild(nodeArray[x]);
	    						gp.setLeftChild(null);

	    						//If root was gp, make sure we still have pointer to root, else determine if connect to ggp left or right child
	    						if(root == gp)
	    							root = p;
	    						else if(ggp.rightChildIs(gp))
	    							ggp.setRightChild(p);
	    						else
	    							ggp.setLeftChild(p);

//	    						if(root == gp)
//	    							root = p;
//	    						else
//	    							ggp.setLeftChild(p);
	    					}
	    					else{
	    						//else inside grandchild so do double rotation

	    						//color swaps
	    						gp.switchColor();
	    						nodeArray[x].switchColor();

	    						//rotation 1
	    						gp.setRightChild(nodeArray[x]);
	    						nodeArray[x].setRightChild(p);

	    						//rotation 2
	    						nodeArray[x].setLeftChild(gp);

	    						//if no ggp, nothing to attach so at root, else determin if attaching to ggp left or right child
	    						if(ggp == null)
	    							root = nodeArray[x];
	    						else if (ggp.rightChildIs(gp))
	    							ggp.setRightChild(nodeArray[x]);
	    						else
	    							ggp.setLeftChild(nodeArray[x]);

//	    						if(ggp != null)
//	    							ggp.setRightChild(nodeArray[x]);
//	    						else
//	    							root = nodeArray[x];

	    						gp.setRightChild(null);

	    					}
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

	    						//If root was gp, make sure we still have pointer to root, else determine if connect to ggp left or right child
	    						if(root == gp)
	    							root = p;
	    						else if(ggp.rightChildIs(gp))
	    							ggp.setRightChild(p);
	    						else
	    							ggp.setLeftChild(p);

	    					}
	    					else{
	    						//else inside grandchild so do double rotation

	    						//color swaps
	    						gp.switchColor();
	    						nodeArray[x].switchColor();

	    						//rotation 1
	    						gp.setLeftChild(nodeArray[x]);
	    						nodeArray[x].setLeftChild(p);

	    						//rotation 2
	    						nodeArray[x].setRightChild(gp);

	    						//if no ggp, nothing to attach so at root, else determin if attaching to ggp left or right child
	    						if(ggp == null)
	    							root = nodeArray[x];
	    						else if (ggp.rightChildIs(gp))
	    							ggp.setRightChild(nodeArray[x]);
	    						else
	    							ggp.setLeftChild(nodeArray[x]);

//	    						if(ggp != null)
//	    							ggp.setLeftChild(nodeArray[x]);
//	    						else
//	    							root = nodeArray[x];

	    						gp.setLeftChild(null);
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
		Node successor = null;
		boolean foundSuccessor = false;

		//search for node until at end of path (current == null) or you found the successor
		while(current != null && !foundSuccessor){

			if(searchId == current.getId()){

				if(current.hasRightChild()){
					successor = findMinNode(current.getRightChild());
					foundSuccessor = true;
				}

				else if(current == p.getLeftChild()){
					successor = p;
					foundSuccessor = true;
				}

				else{
					//successor is either the last time we went right, or there is no successor(if it is the item w/ the highest key value)
					if(rightParent != null)
						successor = rightParent;
					else
						successor = new Node(0,0);

					foundSuccessor = true;
				}
			}
			else if (searchId < current.getId()){

				rightParent = current;
				p = current;
				current = current.getLeftChild();

				//if there are no nodes left or search id is between the child and parent, parent is the successor
				if(current == null || (searchId > current.getId() && !current.hasRightChild())){
					successor = p;
					foundSuccessor = true;
				}
			}
			else{
				p = current;
				current = current.getRightChild();

				//if no more right nodes, parent must be last time we went right on the tree(everything in its left subtree is smaller than it)
				if(current == null)
				{
					if(rightParent != null)
						successor = rightParent;
					else
						successor = new Node(0,0);

					foundSuccessor = true;
				}
			}
		}

		return successor;
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
		Node predecessor = null;
		boolean foundPredecessor = false;

		//first find node
		while(current != null && !foundPredecessor){
			if(searchId == current.getId()){

				if(current.hasLeftChild()){
					predecessor = findMaxNode(current.getLeftChild());
					foundPredecessor = true;
				}
				else if(current == p.getRightChild()){
					predecessor = p;
					foundPredecessor = true;
				}
				else{
					//the last time we went right down the tree is the successor
					if(leftParent != null)
						predecessor = leftParent;
					else
						predecessor = new Node(0,0);

					foundPredecessor = true;
				}
			}
			else if (searchId < current.getId()){
				p = current;
				current = current.getLeftChild();

				//if no more left nodes, parent must be last time we went left on the tree(everything in its right subtree is larger than it)
				if(current == null)
				{
					if(leftParent != null)
						predecessor = leftParent;
					else
						predecessor = new Node(0,0);

					foundPredecessor = true;
				}

			}
			else{
				leftParent = current;
				p = current;
				current = current.getRightChild();

				//if there are no nodes left or search id is between the child and parent, parent is the predecessor
				if(current == null || (searchId < current.getId() && !current.hasLeftChild())){
					predecessor = p;
					foundPredecessor = true;
				}

			}
		}

		return predecessor;
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

	private boolean isLeftRightInsideGrandchild(Node current, Node p, Node gp){
		if(gp.leftChildIs(p) && p.rightChildIs(current))
			return true;
		else
			return false;
	}

	private boolean isRightLeftInsideGrandchild(Node current, Node p, Node gp){
		if (gp.rightChildIs(p) && p.leftChildIs(current))
			return true;
		else
			return false;
	}

	private boolean isLeftOutsideGrandchild(Node current, Node p, Node gp){
		if(gp.leftChildIs(p) && p.leftChildIs(current))
			return true;
		else
			return false;
	}

	private boolean isRightOutsideGrandchild(Node current, Node p, Node gp){
		if(gp.rightChildIs(p) && p.rightChildIs(current))
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


	/*
	 * Increase the count of the node with searchId, if not present, insert the node
	 *
	 * */
	public int increaseNodeCount(int searchId, int countToAdd){

	    //pointers to nodes we will need to reference (ggp = great grand parent, gp = grand parent, p = parent...)
	    Node gggp = null;
	    Node ggp = null;
	    Node gp = null;
	    Node p = null;
	    Node current = null;

	    //true when a nodes been inserted
	    boolean inserted = false;

	    //true when a nodes been found
	    boolean foundNode = false;

    	//go down the tree and find insertion/increase point (start at root)
    	current = root;
    	inserted = false;

    	//if we need to insert, will insert this node
    	Node insertNode = new Node(searchId, countToAdd);

    	//set if a count was increased, not a new node inserted
    	int newCount = 0;

    	while(!inserted && !foundNode){

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
						if(isRightOutsideGrandchild(p, gp, ggp)){
							//right outside grandchild, do single rotation
							gp.switchColor();
		    				ggp.switchColor();

		    				ggp.setRightChild(gp.getLeftChild());
		    				gp.setLeftChild(ggp);

		    				if(ggp == root)
		    					root = gp;
		    				else
		    					gggp.setRightChild(gp);
						}
						else if(isLeftOutsideGrandchild(p, gp, ggp)){
							//left outside grandchild, do single rotation
							gp.switchColor();
		    				ggp.switchColor();

		    				ggp.setLeftChild(gp.getRightChild());
		    				gp.setRightChild(ggp);

		    				if(ggp == root)
		    					root = gp;
		    				else
		    					gggp.setLeftChild(gp);
						}
						else if (isLeftRightInsideGrandchild(p, gp, ggp)){
							//LR inside grandchild, do a double rotation

							//color flips
							ggp.switchColor();
							p.switchColor();

							//first rotation
							ggp.setLeftChild(p);
							gp.setRightChild(p.getLeftChild());
							p.setLeftChild(gp);

							//second rotation

							ggp.setLeftChild(p.getRightChild());
							p.setRightChild(ggp);

							if(root == ggp)
								root = p;
							else
								gggp.setLeftChild(p);

						}
						else{
							//RL inside grandchild, do a double rotation

							//color flips
							ggp.switchColor();
							p.switchColor();

							//first rotation
							ggp.setRightChild(p);
							gp.setLeftChild(p.getRightChild());
							p.setRightChild(gp);

							//second rotation

							ggp.setRightChild(p.getLeftChild());
							p.setLeftChild(ggp);

							if(root == ggp)
								root = p;
							else
								gggp.setRightChild(p);

						}
	    			}
				}
			}

			//if searchId equals current ID, we found the node, increase the count by the specified amount
			if(searchId == current.getId()){
				newCount = current.getCount() + countToAdd;
				current.setCount(newCount);
				foundNode = true;
			}
			else if(searchId < current.getId()){
				current = current.getLeftChild();

				//insert null insert into tree
				if(current == null){

					//if p is black, just insert
					if(!p.isRed())
						p.setLeftChild(insertNode);
					else{

						if(gp.getLeftChild() == p){
							//if outside grandchild, do single rotation

							gp.switchColor();
							p.switchColor();

							p.setRightChild(gp);
							p.setLeftChild(insertNode);
							gp.setLeftChild(null);

							//If root was gp, make sure we still have pointer to root, else determine if connect to ggp left or right child
							if(root == gp)
								root = p;
							else if(ggp.rightChildIs(gp))
								ggp.setRightChild(p);
							else
								ggp.setLeftChild(p);
						}
						else{
							//else inside grandchild so do double rotation

							//color swaps
							gp.switchColor();
							insertNode.switchColor();

							//rotation 1
							gp.setRightChild(insertNode);
							insertNode.setRightChild(p);

							//rotation 2
							insertNode.setLeftChild(gp);

							//if no ggp, nothing to attach so at root, else determin if attaching to ggp left or right child
							if(ggp == null)
								root = insertNode;
							else if (ggp.rightChildIs(gp))
								ggp.setRightChild(insertNode);
							else
								ggp.setLeftChild(insertNode);

							gp.setRightChild(null);

						}
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
						p.setRightChild(insertNode);
					else{

						if(gp.getRightChild() == p){
							//outside grandchild, do flip colors and do single rotation
							gp.switchColor();
							p.switchColor();

							p.setLeftChild(gp);
							p.setRightChild(insertNode);
							gp.setRightChild(null);

							//If root was gp, make sure we still have pointer to root, else determine if connect to ggp left or right child
							if(root == gp)
								root = p;
							else if(ggp.rightChildIs(gp))
								ggp.setRightChild(p);
							else
								ggp.setLeftChild(p);

						}
						else{
							//else inside grandchild so do double rotation

							//color swaps
							gp.switchColor();
							insertNode.switchColor();

							//rotation 1
							gp.setLeftChild(insertNode);
							insertNode.setLeftChild(p);

							//rotation 2
							insertNode.setRightChild(gp);

							//if no ggp, nothing to attach so at root, else determin if attaching to ggp left or right child
							if(ggp == null)
								root = insertNode;
							else if (ggp.rightChildIs(gp))
								ggp.setRightChild(insertNode);
							else
								ggp.setLeftChild(insertNode);

							gp.setLeftChild(null);
						}
					}

					inserted = true;
				}
			}
    	}

		//if the node was already in the tree, return its new count, else return the inserted node's count
		if(foundNode)
			return newCount;
		else
			return countToAdd;
	}





	/* Reduces a specified nodes count by the countToDecrease
	 * If count after decrease is <= 0, delete node and return 0
	 * If node not in tree, return 0
	 * */
	public int reduceNodeCount(int searchId, int countDecrease){

	    Node p = null;
	    Node current = root;

	    boolean deleted = false;
	    boolean found = false;

	    //first, search for node in tree
	    while(current != null && !deleted && !found){

	    	//id found, decrease and test for deletion
	    	if(searchId == current.getId()){
	    		//id found, decrease and test for deletion
	    		found = true;
	    		current.setCount(current.getCount() - countDecrease);
	    		if(current.getCount() <= 0){
	    			deleteNode(current);
	    			deleted = true;
	    		}
	    	}
	    	else if(searchId < current.getId()){
	    		p = current;
	    		current = current.getLeftChild();
	    	}
	    	else{
	    		p = current;
	    		current = current.getRightChild();
	    	}
	    }

	    //if node deleted or not found in tree, return 0, else return its current count
	    if(deleted || !found)
	    	return 0;
	    else
	    	return current.getCount();
	}

	/*deletes a node from the red black tree, performing any needed corrections as well*/
	private void deleteNode(Node deleteNode){

		Node p;

		if(deleteNode.hasLeftChild()){

			//swap nodes, and keep a pointer to parent of deleted node
			p = swapDeleteNodeandPredecessor(deleteNode);

			//if deleting a red child, just delete, no corrections need to be made
			if(p.getRightChild().isRed())
				p.setRightChild(null);
			else{

			}
		}
		else if (deleteNode.hasRightChild()){

		}
		else{

		}



	}



	/*Finds node we will replace the deleted value with (it predecessor)
	 *
	 * Pass in currents left subtree to get this node
	 * */
	private Node swapDeleteNodeandPredecessor(Node deleteNode){

		//need parent pointer so we can perform other deletion steps
		Node p = null;

		Node current = deleteNode.getLeftChild();

		//find node
		while(current.hasRightChild()){
			p = current;
			current = current.getRightChild();
		}

		//swap node values
		int idHolder = deleteNode.getId();
		deleteNode.setId(current.getId());
		current.setId(idHolder);

		return p;
	}



	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
