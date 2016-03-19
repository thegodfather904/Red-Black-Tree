/*
*  Author: Tony Scialo
*  Description: Project assignment for UF ADS - Red Black Tree
*/
public class RedBlackTreeApp{
  public static void main (String [] args){

	//change back to args[0] > 0
    if(true)
    {
      //read in file CL
      ReadFile rf = new ReadFile();
      //Node[] nodeArray = rf.readFileInput(args[0]);

      Node[] nodeArray = rf.readFileInput(RedBlackTreeApp.class.getClassLoader().getResource("record-file-3").getPath());

      //if nodes to process, build tree
      if(nodeArray != null){
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.buildRedBlackTree(nodeArray);

        //get count testing
        System.out.println("FIND COUNT:");
        System.out.println(rbTree.findCount(16));
        System.out.println(rbTree.findCount(8));
        System.out.println(rbTree.findCount(24));
        System.out.println(rbTree.findCount(4));
        System.out.println(rbTree.findCount(12));
        System.out.println(rbTree.findCount(20));
        System.out.println(rbTree.findCount(28));
        System.out.println(rbTree.findCount(1));
        System.out.println(rbTree.findCount(3));
        System.out.println(rbTree.findCount(5));
        System.out.println(rbTree.findCount(7));
        System.out.println(rbTree.findCount(35));
        System.out.println(rbTree.findCount(37));
        System.out.println(rbTree.findCount(38));
        System.out.println(rbTree.findCount(0));
        System.out.println(rbTree.findCount(39));

        //get next() testing
        System.out.println("NEXT():");
        Node testNode;
        testNode = rbTree.findSuccessor(7);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findSuccessor(9);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findSuccessor(10);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findSuccessor(11);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findSuccessor(12);
        System.out.println(testNode.getId() + " " + testNode.getCount());

        //get previous() testing
        System.out.println("PREVIOUS()");
        testNode = rbTree.findPredecessor(1);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(4);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(5);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(8);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(10);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(12);
        System.out.println(testNode.getId() + " " + testNode.getCount());
        testNode = rbTree.findPredecessor(13);
        System.out.println(testNode.getId() + " " + testNode.getCount());

        System.out.println("INRANGE()");
        System.out.println(rbTree.numberInRange(4, 58));

      }
      else{
        System.out.println("Nothing to build tree with, closing program");
      }
    }



  }
}
