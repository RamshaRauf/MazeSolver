import java.util.*;
import java.lang.*;
/**
 *. Class organizes the maze and has methods to solve it
 *. @author Ramsha Rauf
 *. @version CSC 212 Spring 2022
 */
public class Maze implements DisplayableMaze {
  /** Height of the maze stored as int*/
  private int height;
  /** Width of the maze stored as int*/
  private int width;
  /** maze stores the maze contents as a 2d array with the data type MazeContents */
  private MazeContents [][] maze = new MazeContents[height][width];
  /** Row number of the start stored as int*/
  private int startRow;
  
  /** Row number of the finish stored as int*/
  private int finishRow;
  
  /** Column number of the start stored as int*/
  private int startCol;

  /** Column number of the finish stored as int*/
  private int finishCol;
  

  /** 
  *  hard-coded maze builder 
  */ 
  public Maze(){
    height =8;
    width=8;
    this.maze = new MazeContents [][]{{MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL, MazeContents.WALL},
    {MazeContents.WALL, MazeContents.OPEN,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.OPEN,MazeContents.OPEN,MazeContents.WALL},    
    {MazeContents.WALL, MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.WALL,MazeContents.WALL,MazeContents.OPEN,MazeContents.WALL},
    {MazeContents.WALL, MazeContents.OPEN,MazeContents.WALL,MazeContents.OPEN,MazeContents.WALL,MazeContents.WALL,MazeContents.OPEN,MazeContents.WALL},    
    {MazeContents.WALL, MazeContents.OPEN,MazeContents.WALL,MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.WALL},
    {MazeContents.WALL, MazeContents.OPEN,MazeContents.WALL,MazeContents.OPEN,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL},
    {MazeContents.WALL, MazeContents.WALL,MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.OPEN,MazeContents.WALL},
    {MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL,MazeContents.WALL, MazeContents.WALL}};
  };
  
  /** 
    *. Maze builder that builds the maze based on the width, height, and content provided
    *. @param int width, int height, and 2d string array containing the characters for the content of the maze
    */
  public Maze(String[][] mazeReader, int sentWidth, int sentHeight){
    this.height = sentHeight;
    this.width = sentWidth;
    int hCounter = -1;
    int wCounter = -1;
    this.maze=new MazeContents[height][width];
    for (String[]row :mazeReader){
      hCounter+=1;
      wCounter = -1;
      for (String element:row){
        wCounter+=1;
        if (element.equals("#")){
          maze[hCounter][wCounter]= MazeContents.WALL;
        }else if (element.equals(".")){
          maze[hCounter][wCounter]= MazeContents.OPEN;
        }else if (element.equals(" ")){
          maze[hCounter][wCounter]= MazeContents.OPEN;
        }else if (element.equals("F")){
          this.finishRow=hCounter;
          this.finishCol=wCounter;
          maze[hCounter][wCounter]= MazeContents.OPEN;
        }else if (element.equals("S")){
          this.startRow=hCounter;
          this.startCol=wCounter;
          maze[hCounter][wCounter]= MazeContents.OPEN;
        }
      }
    }
  }
      
    
    

  /** @return height of maze grid */
  public int getHeight() {
    return this.height;
  }

  /** @return width of maze grid */
  public int getWidth() {
    return this.width;
  }

  /** 
  *  gets the type of MazeContents at a given square in the maze
  *  @param int i is the row number, int j is the column number of a specific square
  *  @return what is the Mazecontent of a specific square in the maze
  */
  public MazeContents getContents(int i, int j) {
    if (j>=getWidth()||i>=getHeight()||i<0||j<0) {
      return MazeContents.WALL;
    }else{
      return maze[i][j];
    }
  }  
 

  /** @return location of maze start point */
  public MazeLocation getStart() {
    //return new MazeLocation(1,1);
    return new MazeLocation (this.startRow,this.startCol);
  }

  /** @return location of maze finish point */
  public MazeLocation getFinish() {
    return new MazeLocation (this.finishRow,this.finishCol); 
    //return new MazeLocation(6,6);
  }

  /** 
  *  Solves the maze
  *  @param current maze location
  *  @return T/F whether the maze was solved or not 
  */
  public boolean solve(MazeLocation current){
    boolean success = false;
    MazeLocation finish= getFinish();
    boolean solved;
    try { Thread.sleep(20);	} catch (InterruptedException e) {};
    if (current.getRow()==finish.getRow() && current.getCol()== finish.getCol()){
      success = true;
      maze[current.getRow()][current.getCol()]=MazeContents.PATH;
      return success;
    }else if (getContents(current.getRow(),current.getCol())!=MazeContents.VISITED && getContents(current.getRow(),current.getCol())!=MazeContents.WALL) {
      maze[current.getRow()][current.getCol()]=MazeContents.VISITED;
      solved= solve(current.neighbor(MazeDirection.NORTH));
      if (!solved){
        solved = solve(current.neighbor(MazeDirection.SOUTH));
      }if (!solved){
        solved = solve(current.neighbor(MazeDirection.EAST));
      }if (!solved){
        solved = solve(current.neighbor(MazeDirection.WEST));
      }if (solved){
        maze[current.getRow()][current.getCol()]=MazeContents.PATH;
        success=true;
        return success;
      }else{
        maze[current.getRow()][current.getCol()]=MazeContents.DEAD_END;
        success=false;
        return success;
      }
    }
    return success;
  }
}
