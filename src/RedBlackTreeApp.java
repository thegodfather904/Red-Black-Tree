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

      Node[] nodeArray = rf.readFileInput(RedBlackTreeApp.class.getClassLoader().getResource("record-file-2").getPath());

      //if nodes to process, build tree
      if(nodeArray != null){
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.buildRedBlackTree(nodeArray);

        //get count testing
//        System.out.println("FIND COUNT:");
//        System.out.println(rbTree.findCount(16));

        //get next() testing
//        System.out.println("NEXT():");
//        for(int x = 5; x <= 200; x+=5)
//        	System.out.println(rbTree.findSuccessor(x).toString());

//        System.out.println("PREVIOUS():");
//        for(int x = 5; x <= 205; x+= 5)
//        	System.out.println(rbTree.findPredecessor(x));

//        rbTree.increaseNodeCount(13, 13);
//        System.out.println(rbTree.findPredecessor(14));


        //get previous() testing
//        System.out.println("PREVIOUS()");
//        testNode = rbTree.findPredecessor(1);
//        System.out.println(testNode.getId() + " " + testNode.getCount());
//
//        System.out.println("INRANGE()");
//        System.out.println(rbTree.numberInRange(4, 58));
//
//        System.out.println("INCREASE");
//        System.out.println(rbTree.increaseNodeCount(6, 1));


        //reduce testing
        System.out.println("REDUCE()");
//        for(int x = 5; x <= 200; x+= 5)
//        	System.out.println(rbTree.reduceNodeCount(x, 5));

        System.out.println(rbTree.reduceNodeCount(100, 5));

      }
      else{
        System.out.println("Nothing to build tree with, closing program");
      }
    }



  }
}
