/*
*  Author: Tony Scialo
*  Description: Individual node for a red-black tree
*/
public class Node{

  private int id;
  private int count;
  private boolean red = true;
  private Node leftChild;
  private Node rightChild;

  public Node(){

  }

  public Node(int id, int count){
    this.id = id;
    this.count = count;
  }

  /*Switches a nodes color from red to black, and vice versa*/
  public void switchColor(){
	  red = !red;
  }

  /*Returns true if a node has two children (used in color flips on the way down)*/
  public boolean hasTwoChildren(){
	  return !(leftChild == null) && !(rightChild == null);
  }

  public boolean hasLeftChild(){
	  return leftChild != null;
  }

  public boolean hasRightChild(){
	  return rightChild != null;
  }

  /*True if test node is left child of current node*/
  public boolean leftChildIs(Node test){
	  return leftChild == test;
  }

  /*True if test node is right child of current node*/
  public boolean rightChildIs(Node test){
	  return rightChild == test;
  }

  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public int getCount(){
    return count;
  }

  public void setCount(int count){
    this.count = count;
  }


public Node getLeftChild() {
	return leftChild;
}

public void setLeftChild(Node leftChild) {
	this.leftChild = leftChild;
}

public Node getRightChild() {
	return rightChild;
}

public void setRightChild(Node rightChild) {
	this.rightChild = rightChild;
}

public boolean isRed() {
	return red;
}

public void setRed(boolean red) {
	this.red = red;
}
}
