import java.util.*;
import java.io.*;
/**
 *  Reads a maze file from the console and solves the maze
 *  @author  Ramsha Rauf
 *  @version CSC 212 Spring 2022
 */
public class MazeSolver{
   /** 
  *  gets file name from console whether the command-line argument is provided or not
  *  @param string[] args whatever the is input on the console 
  */
  public static void main(String[] args) {
    int height = 0;
    int width = 0;
    //when command line argument is not provided
    if (args.length == 0) {
      Scanner input = new Scanner(System.in);
      //creates an arraylist that stores each line in the maze file 
      ArrayList<String[]> mazeReader = new ArrayList<String[]>();
      
      while(input.hasNextLine()){
        height+=1;
        width=0;
        String[] row=input.nextLine().split("");
        mazeReader.add(row);
        for(String symbol:row){
          width+=1;
        }
      }
      //converts the arraylist of string arrays to a 2d string array
      String[][] objects = mazeReader.toArray(new String[][] {});

      Maze maze = new Maze(objects,width,height);
      MazeViewer viewer = new MazeViewer(maze);
      System.out.println(maze.solve(maze.getStart()));
      
      input.close();

    //when command line is provided
    }else{
      Scanner file = null;
      ArrayList<String[]> mazeReader = new ArrayList<String[]>();
      try {
        file = new Scanner(new File(args[0]));
      } catch (FileNotFoundException e) {
        System.err.println("Cannot locate file.");
        System.exit(-1);
      }
      while(file.hasNextLine()){
        height+=1;
        width = 0;
        String[] row=file.nextLine().split("");
        mazeReader.add(row);
        for(String symbol:row){
          width+=1;
        }
      }
      String[][] objects = mazeReader.toArray(new String[][] {});
      Maze maze = new Maze(objects,width,height);
      MazeViewer viewer = new MazeViewer(maze);
      System.out.println(maze.solve(maze.getStart()));
      file.close();
    }
  }
}