/*
*  Author: Tony Scialo
*  Description: Project assignment for UF ADS - Red Black Tree
*/
public class RedBlackTreeApp{
  public static void main (String [] args){

    if(args.length > 0)
    {
      //read in file
      ReadFile rf = new ReadFile();
      Node[] nodeArray = rf.readFileInput(args[0]);

      //if nodes to process, build tree
      if(nodeArray != null){
        
      }
      else{
        System.out.println("Nothing to build tree with, closing program");
      }
    }
    else
      System.out.println("No file name given, closing program");
  }
}
