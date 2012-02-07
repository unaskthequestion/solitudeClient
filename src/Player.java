import processing.core.*;
import java.util.Vector;

public class Player {
	
	PApplet parent;
	
	Vector<Shape> shapes = new Vector<Shape>();
	
	int currentShape = 0;
	boolean bAddHandle = false;
	

	/*
	 * Public Methods
	 */

	public Player(PApplet p){
		parent = p;
		
		shapes.add( new Shape(parent, parent.color(255,238,135,200)) );
	}
	
	public void setup() {
	}

	public void draw() {		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
		}
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).draw();
		}
	}
	
	public void mousePressed(){
		if(bAddHandle)	shapes.elementAt(currentShape).add(parent.mouseX, parent.mouseY);
	}
	
	public void mouseDragged(){
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseDragged();
		}
		
	}
	
	public void mouseReleased(){

		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseReleased();
		}
	}
	
	public void keyPressed(){
		switch (parent.key) {
		case '+':
			bAddHandle = true;
			break;

		default:
			break;
		}
	}
	
	public void keyReleased(){
		switch (parent.key) {
		case '+':
			bAddHandle = false;
			break;

		default:
			break;
		}
	}
}
