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

      Node[] nodeArray = rf.readFileInput(RedBlackTreeApp.class.getClassLoader().getResource("record-file").getPath());
      rf.printFileContents(nodeArray);

      //if nodes to process, build tree
      if(nodeArray != null){
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.buildRedBlackTree(nodeArray);
      }
      else{
        System.out.println("Nothing to build tree with, closing program");
      }
    }



  }
}
