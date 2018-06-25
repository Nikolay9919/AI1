package models;

public class Vertex {
	 float x; 
	 float y; 
	 int id; 
	
	 int kClass = 0; 
	  
	 public Vertex(float x, float y, int id){ 
	  this.x = x; 
	  this.y = y; 
	  this.id = id; 
	 } 
	  
	 public void print(){ 
	  System.out.println(id + " " + x + " " + y + " " + kClass); 
	 } 
	 
	 public void printXY(){ 
	  System.out.println(x + " " + y); 
	 }  
	  
	 public int getKClass(){ 
	  return this.kClass; 
	 } 
	  
	 public void setkClass(int klas){ 
	  this.kClass = klas; 
	 } 
	  
	 public void setOther(Vertex v){ 
	  this.x = v.x; 
	  this.y=v.y; 
	  this.id=v.id; 
	 } 
	  
}
