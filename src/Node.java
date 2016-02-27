public class Node{
  private int id;
  private int count;

  public Node(){

  }

  public Node(int id, int count){
    this.id = id;
    this.count = count;
  }

  public int getId(){
    return id;
  }

  public void setId(int id){
    this.id = id;
  }

  public int getCount(){
    return count;
  }

  public void setCount(int count){
    this.count = count;
  }
}
