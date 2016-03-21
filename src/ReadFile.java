/*
*  Author: Tony Scialo
*  Description: Reads/parses file
*/

import java.io.File;
import java.io.FileReader;
import java.io.BufferedReader;
import java.util.Scanner;

public class ReadFile{

  private int actualNumNodes;

  public ReadFile(){

  }

  /*Reads in file line by line and creates an array of nodes from the input*/
  public Node[] readFileInput(String filename){

    Node[] nodeArray;

    try{
      String currentLine;
      BufferedReader bf = new BufferedReader(new FileReader(new File(filename)));

      //read the first line to get size of array
      currentLine = bf.readLine();
      int numNodes = Integer.parseInt(currentLine);

      nodeArray = new Node[numNodes];

      //use a scanner to parse the ints in the string
      Scanner scan;
      Node newNode;
      int currentIndex = 0;
      while((currentLine = bf.readLine()) != null){
        scan = new Scanner(currentLine);
        newNode = new Node(scan.nextInt(), scan.nextInt());

        //make sure count isn't 0 before inserting
        if(newNode.getCount() > 0)
        	nodeArray[currentIndex++] = newNode;
        else
        	numNodes--;
      }

      //number of nodes = first number in file, actual is that number minus any id's with a count of 0 (we ignore these id's)
      actualNumNodes = numNodes;

      bf.close();

    }
    catch(Exception e){
      //If any error, alert user and return null to avoid exceptions
      System.out.println("Error reading in contents from file");
      nodeArray = null;
    }

    return nodeArray;
  }

  /*Prints the contents of the file to confirm being read in correctly*/
  public void printFileContents(Node[] nodeArray){
	  for(int x = 0; x < nodeArray.length; x++)
		  System.out.println(nodeArray[x].getId() + " - " + nodeArray[x].getCount());
  }

public int getActualNumNodes() {
	return actualNumNodes;
}

public void setActualNumNodes(int actualNumNodes) {
	this.actualNumNodes = actualNumNodes;
}



}
