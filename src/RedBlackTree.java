/*
*  Author: Tony Scialo
*  Description: Red-black tree; provides tree maintance as well as pointer to root
*/
public class RedBlackTree{

	  private Node root;

	  public RedBlackTree(){

	  }

	  /*Uses the nodeArray from the input file to build the red-black tree*/
	  public void buildRedBlackTree(Node[] nodeArray, int numNodes){

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
	    for(int x = 1; x < numNodes; x++){

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

	    						gp.setLeftChild(null);
	    					}
	    				}

	    				inserted = true;
	    			}
	    		}


	    	}


	    }

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
	public Node findSuccessor(int searchId, Node subtree){

		Node current = subtree;
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
			numInRange += current.getCount();

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

	    Node current = root;

	    boolean deleted = false;
	    boolean found = false;

	    //keeps track of the parents so we can navigate back up the tree and fix if needed
	    NodeStack parentStack = new NodeStack();

	    //first, search for node in tree
	    while(current != null && !deleted && !found){

	    	//id found, decrease and test for deletion
	    	if(searchId == current.getId()){
	    		//id found, decrease and test for deletion
	    		found = true;
	    		current.setCount(current.getCount() - countDecrease);
	    		if(current.getCount() <= 0){
	    			deleteNode(current.getId(), root, parentStack, current);
	    			deleted = true;
	    		}
	    	}
	    	else if(searchId < current.getId()){
	    		parentStack.push(current);
	    		current = current.getLeftChild();
	    	}
	    	else{
	    		parentStack.push(current);
	    		current = current.getRightChild();
	    	}
	    }

	    //if node deleted or not found in tree, return 0, else return its current count
	    if(deleted || !found)
	    	return 0;
	    else
	    	return current.getCount();
	}

	/*deletes a node from the red black tree, performing any needed corrections as well
	 *
	 * Input params are as follows:
	 * 	deleteId - used when currentNode has 2 children and we must swap
	 * 	subtreeRoot - root of the subtree we are going down
	 * 	parentStack - stack of parents used to navigate back up the tree
	 * 	deleteNode - node currently being deleted
	 *
	 * */
	private void deleteNode(int deleteId, Node subtreeRoot, NodeStack parentStack, Node deleteNode){

		//if deleteNode = null, we must find it to delete, pushing parents on the stack as we go
		if(deleteNode == null){
			Node current = subtreeRoot;
			boolean found = false;

			//first we find the delete node
			while(current != null && !found){
				if(deleteId < current.getId()){
					parentStack.push(current);
					current = current.getLeftChild();
				}
				else if (deleteId > current.getId()){
					parentStack.push(current);
					current = current.getRightChild();
				}
				else{
					//deleteId = current id, found our delete node
					found = true;
					deleteNode = current;
				}
			}
		}

		Node p;			//parent
		Node newChild;	//parents new child (can be null if no children)

		//if current has 0 or 1 child, we can delete, else we must find successor, swap, and delete
		if(!deleteNode.hasTwoChildren()){

			//if current is red, we can just delete it (handle if it has 1 child as well
			if(deleteNode.isRed()){
				p = parentStack.pop();

				//determine if current node has any children, so we can point p to it if necessary
				if(deleteNode.hasNoChildren())
					newChild = null;
				else if (deleteNode.hasLeftChild())
					newChild = deleteNode.getLeftChild();
				else
					newChild = deleteNode.getRightChild();

				//determine if current is left or right child
				if(p.getLeftChild() == deleteNode)
					p.setLeftChild(newChild);
				else
					p.setRightChild(newChild);

				//this is an end case, we can return
				return;
			}
			else if(!deleteNode.isRed() && deleteNode.hasRedChild()){
				//current is black and has 1 red child, delete and move child up
				if(deleteNode.hasLeftChild())
					newChild = deleteNode.getLeftChild();
				else
					newChild = deleteNode.getRightChild();

				//switch new child from red to black to handle black deficiency
				newChild.switchColor();

				if(parentStack.isNotEmpty()){
					p = parentStack.pop();

					//determine if current is left or right child
					if(p.getLeftChild() == deleteNode)
						p.setLeftChild(newChild);
					else
						p.setRightChild(newChild);
				}
				else
					root = newChild;


				//this is an end case, we can return
				return;
			}
			else{

				//deleting a black node, make it double black and then follow the cases, mark as -1 so we can know when to delete a double black node
				deleteNode.setDoubleBlack(true);

				//while there are still parents, fix tree until we reach an end case (1, 4, or 6)
				while(parentStack.isNotEmpty()){

					p = parentStack.pop();

					if(isCase1(p)){
						//root is double black, just set to single black
						p.setDoubleBlack(false);

						//terminal case
						return;
					}
					else if(isCase2(p)){

						if(p.getLeftChild().isDoubleBlack()){
							Node sibling = p.getRightChild();

							//rotations
							p.setRightChild(sibling.getLeftChild());
							sibling.setLeftChild(p);

							if(root == p)
								root = sibling;
							else{
								Node nextParent = parentStack.pop();
								if(nextParent.getRightChild() == p)
									nextParent.setRightChild(sibling);
								else
									nextParent.setLeftChild(sibling);

								parentStack.push(nextParent);
								parentStack.push(sibling);
								parentStack.push(p);
							}

							//color changes
							p.switchColor();
							sibling.switchColor();
						}
						else{
							Node sibling = p.getLeftChild();

							//rotations
							p.setLeftChild(sibling.getRightChild());
							sibling.setRightChild(p);

							if(root == p)
								root = sibling;
							else{
								Node nextParent = parentStack.pop();
								if(nextParent.getRightChild() == p)
									nextParent.setRightChild(sibling);
								else
									nextParent.setLeftChild(sibling);

								parentStack.push(nextParent);
								parentStack.push(sibling);
								parentStack.push(p);
							}

							//color changes
							p.switchColor();
							sibling.switchColor();

						}

					}
					else if(isCase3(p)){

						//change sibling of double black to red, delete double black, make parent double black
						if(p.getLeftChild().isDoubleBlack()){
							p.getRightChild().setRed(true);

							//delete the node or set back to single black
							if(p.getLeftChild() == deleteNode)
								p.setLeftChild(null);
							else
								p.getLeftChild().setDoubleBlack(false);
						}
						else{
							p.getLeftChild().setRed(true);
							if(p.getRightChild() == deleteNode)
								p.setRightChild(null);
							else
								p.getLeftChild().setDoubleBlack(false);
						}

						p.setDoubleBlack(true);

						//if root, push back on stack so we can get to case 1
						if(p == root)
							parentStack.push(p);

					}
					else if(isCase4(p)){

						//switch parent and sibling color
						p.switchColor();

						if(p.getLeftChild().isDoubleBlack()){
							if(p.getLeftChild() == deleteNode)
								p.setLeftChild(null);
							else
								p.getLeftChild().setDoubleBlack(false);
							p.getRightChild().switchColor();
						}
						else{
							if(p.getRightChild() == deleteNode)
								p.setRightChild(null);
							else
								p.getLeftChild().setDoubleBlack(false);

							p.getLeftChild().switchColor();
						}

						//terminating case
						return;
					}
					else if(isCase5(p)){

						if(p.getLeftChild().isDoubleBlack()){

							Node pRightChildLeftChild = p.getRightChild().getLeftChild();

							//perform rotations
							p.getRightChild().setLeftChild(pRightChildLeftChild.getRightChild());
							pRightChildLeftChild.setRightChild(p.getRightChild());
							p.setRightChild(pRightChildLeftChild);

							//perform color changes
							pRightChildLeftChild.setRed(false);
							pRightChildLeftChild.getRightChild().setRed(true);

						}
						else{

							Node pLeftChildRightChild = p.getLeftChild().getRightChild();

							//perform rotations
							p.getLeftChild().setRightChild(pLeftChildRightChild.getLeftChild());
							pLeftChildRightChild.setLeftChild(p.getLeftChild());
							p.setLeftChild(pLeftChildRightChild);

							//perform color changes
							pLeftChildRightChild.setRed(false);
							pLeftChildRightChild.getLeftChild().setRed(true);

						}

						//not terminal, push p back on stack
						parentStack.push(p);

					}
					else{

						//if none of the others, must be case 6
						if(p.getLeftChild().isDoubleBlack()){

							Node pRightChild = p.getRightChild();

							//rotation
							p.setRightChild(pRightChild.getLeftChild());
							pRightChild.setLeftChild(p);
							if(p.getLeftChild() == deleteNode)
								p.setLeftChild(null);
							else
								p.getLeftChild().setDoubleBlack(false);

							//color change
							pRightChild.setRed(p.isRed());
							p.setRed(false);
							pRightChild.getRightChild().setRed(false);

							//make sure we still have root pointer
							if(root == p)
								root = pRightChild;
							else{
								//pop parent from stack, set correct child, push back on
								Node quickPop = parentStack.pop();
								if(quickPop.getLeftChild() == p)
									quickPop.setLeftChild(pRightChild);
								else
									quickPop.setRightChild(pRightChild);

								parentStack.push(quickPop);
							}
						}
						else{

							Node pLeftChild = p.getLeftChild();

							//rotation
							p.setLeftChild(pLeftChild.getRightChild());
							pLeftChild.setRightChild(p);
							if(p.getRightChild() == deleteNode)
								p.setRightChild(null);
							else
								p.getRightChild().setDoubleBlack(false);

							//color change
							pLeftChild.setRed(p.isRed());
							p.setRed(false);
							pLeftChild.getLeftChild().setRed(false);

							//make sure we still have root pointer
							if(root == p)
								root = pLeftChild;
							else{
								//pop parent from stack, set correct child, push back on
								Node quickPop = parentStack.pop();
								if(quickPop.getLeftChild() == p)
									quickPop.setLeftChild(pLeftChild);
								else
									quickPop.setRightChild(pLeftChild);

								parentStack.push(quickPop);
							}
						}

						//terminal case
						return;
					}
				}
			}

		}
		else{
			//find its successor, swap, and run delete node on successor node
			Node successor = findSuccessor(deleteNode.getId(), deleteNode.getRightChild());

			//set delete node to successor's id
			deleteNode.setId(successor.getId());

			//push delete node on the parent stack
			parentStack.push(deleteNode);

			//run delete on successor (passing in id so we can search for it and pop parents on the stack if needed)
			deleteNode(successor.getId(), deleteNode.getRightChild(), parentStack, null);
		}

	}

	//case 1 for deletion
	private boolean isCase1(Node parent){
		boolean case1 = false;

		if(parent == root && parent.isDoubleBlack())
			case1 = true;

		return case1;
	}

	//case 2 for deletion
	private boolean isCase2(Node parent){
		boolean case2 = false;

		Node leftChild = parent.getLeftChild();
		Node rightChild = parent.getRightChild();

		if(!parent.isRed() && (leftChild != null && leftChild.isDoubleBlack()) && (rightChild != null && rightChild.isRed() && rightChild.hasTwoBlackChildren()))
			case2 = true;
		else if(!parent.isRed() && (rightChild != null && rightChild.isDoubleBlack()) && (leftChild != null && leftChild.isRed() && leftChild.hasTwoBlackChildren()))
			case2 = true;

		return case2;
	}

	//case 3 for deletion
	private boolean isCase3(Node parent){

		boolean case3 = false;

		Node leftChild = parent.getLeftChild();
		Node rightChild = parent.getRightChild();

		if(!parent.isRed() && (leftChild != null && leftChild.isDoubleBlack()) && (rightChild != null && rightChild.hasTwoBlackChildren()))
			case3 = true;
		else if(!parent.isRed() && (rightChild != null && rightChild.isDoubleBlack()) && (leftChild != null && leftChild.hasTwoBlackChildren()))
			case3 = true;

		return case3;

	}

	//case 4 for deletion
	private boolean isCase4(Node parent){

		boolean case4 = false;

		Node leftChild = parent.getLeftChild();
		Node rightChild = parent.getRightChild();

		if(parent.isRed() &&  (leftChild != null && leftChild.isDoubleBlack()) && (rightChild != null && rightChild.hasTwoBlackChildren()))
			case4 = true;
		else if(parent.isRed() && (rightChild != null && rightChild.isDoubleBlack() && (leftChild != null && leftChild.hasTwoBlackChildren())))
			case4 = true;

		return case4;
	}

	//case 5 for deletion
	private boolean isCase5(Node parent){
		boolean case5 = false;

		Node leftChild = parent.getLeftChild();
		Node rightChild = parent.getRightChild();

		if(!parent.isRed() && (leftChild != null && leftChild.isDoubleBlack()) && (rightChild != null && !rightChild.isRed())
				&& (rightChild.getLeftChild() != null && rightChild.getLeftChild().isRed()) && (rightChild.getRightChild() != null && !rightChild.getRightChild().isRed()))
			case5 = true;
		else if(!parent.isRed() && (rightChild != null && rightChild.isDoubleBlack()) && (leftChild != null && !leftChild.isRed())
				&& (leftChild.getRightChild() != null && leftChild.getRightChild().isRed()) && (leftChild.getLeftChild() != null && !leftChild.getLeftChild().isRed()))
			case5 = true;

		return case5;
	}


	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
