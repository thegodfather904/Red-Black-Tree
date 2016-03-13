/*
*  Author: Tony Scialo
*  Description: Reads/parses file
*/

import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.io.BufferedReader;
import java.util.Scanner;

public class ReadFile{

  public ReadFile(){

  }

  /*Reads in file line by line and creates an array of nodes from the input*/
  public Node[] readFileInput(String filename){

    System.out.println("Getting nodes from file");

    Node[] nodeArray;

    try{
      String currentLine;
      BufferedReader bf = new BufferedReader(new FileReader(new File(filename)));

      //read the first line to get size of array
      currentLine = bf.readLine();
      int numNodes = Integer.parseInt(currentLine);

      nodeArray = new Node[numNodes];

      //use a scanner to parse the ints in the strink
      Scanner scan;
      Node newNode;
      int currentIndex = 0;
      while((currentLine = bf.readLine()) != null){
        scan = new Scanner(currentLine);
        newNode = new Node(scan.nextInt(), scan.nextInt());
        nodeArray[currentIndex++] = newNode;
      }

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



  /*Reads file in Eclipse (different from reading from command line)*/
  public Node[] readFileInputEclipse(){
	  Node[] nodeArray;

	  try{
		  URL url = ReadFile.class.getClassLoader().getResource("record-file");
		  System.out.println(url.getPath());

		  nodeArray = new Node[10];
	  }
	  catch(Exception e){
		  System.out.println("Error reading in contents from file");
		  nodeArray = null;
	  }

	  return nodeArray;
  }




}
