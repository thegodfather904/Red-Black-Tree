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

	public Node getRoot() {
		return root;
	}

	public void setRoot(Node root) {
		this.root = root;
	}

}
