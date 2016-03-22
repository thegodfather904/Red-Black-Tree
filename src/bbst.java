/*
*  Author: Tony Scialo
*  Description: Project assignment for UF ADS - Red Black Tree
*/
import java.util.Scanner;
public class bbst{
  public static void main (String [] args){

    if(args.length > 0)
    {
      //read in file CL
      ReadFile rf = new ReadFile();
      Node[] nodeArray = rf.readFileInput(args[0]);

      //Used for testing in Eclipse
      //Node[] nodeArray = rf.readFileInput(RedBlackTreeApp.class.getClassLoader().getResource("record-file-3").getPath());

      //if nodes to process, build tree
      if(nodeArray != null){
        RedBlackTree rbTree = new RedBlackTree();
        rbTree.buildRedBlackTree(nodeArray, rf.getActualNumNodes());


        Scanner scan = new Scanner(System.in);
        String userInput = scan.nextLine();

        String[] inputArray;

        while(!userInput.equals("quit")){
        	try{

        		inputArray = userInput.split(" ");

        		switch(inputArray[0]){
        		case UserCommands.INCREASE:
        			System.out.println(rbTree.increaseNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.REDUCE:
        			System.out.println(rbTree.reduceNodeCount(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		case UserCommands.COUNT:
        			System.out.println(rbTree.findCount(Integer.parseInt(inputArray[1])));
        			break;
        		case UserCommands.NEXT:
        			System.out.println(rbTree.findSuccessor(Integer.parseInt(inputArray[1]), rbTree.getRoot()));
        			break;
        		case UserCommands.PREVIOUS:
        			System.out.println(rbTree.findPredecessor(Integer.parseInt(inputArray[1])));
        			break;
        		case UserCommands.INRANGE:
        			System.out.println(rbTree.numberInRange(Integer.parseInt(inputArray[1]), Integer.parseInt(inputArray[2])));
        			break;
        		default:
        			System.out.println("Unrecognized command");
        			break;
        		}

        		userInput = scan.nextLine();

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
