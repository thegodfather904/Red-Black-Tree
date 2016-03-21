/*
*  Author: Tony Scialo
*  Description: Project assignment for UF ADS - Red Black Tree
*/
import java.util.Scanner;
public class RedBlackTreeApp{
  public static void main (String [] args){

	//change back to args[0] > 0
    if(true)
    {
      //read in file CL
      ReadFile rf = new ReadFile();
      //Node[] nodeArray = rf.readFileInput(args[0]);

      Node[] nodeArray = rf.readFileInput(RedBlackTreeApp.class.getClassLoader().getResource("record-file").getPath());

      //if nodes to process, build tree
      if(nodeArray != null){
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.buildRedBlackTree(nodeArray, rf.getActualNumNodes());


        Scanner scan = new Scanner(System.in);
        String userInput = "";

        String[] inputArray;

        while(!userInput.equals("quit")){

        	userInput = scan.nextLine();

        	inputArray = userInput.split(" ");

        	try{

        		switch(inputArray[0]){
        		case UserCommands.INCREASE:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.REDUCE:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.COUNT:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.NEXT:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.PREVIOUS:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.INRANGE:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		default:
        			System.out.println("Unrecognized command");
        			break;
        		}


        		if(inputArray[0].equals(UserCommands.INCREASE))
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));

        	}
        	catch(Exception e){
        		System.out.println("Error running command " + userInput);
        	}

        }

        scan.close();

      }
      else{
        System.out.println("Nothing to build tree with, closing program");
      }
    }
  }
}
