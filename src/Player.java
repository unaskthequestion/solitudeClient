/****
 *  A Player has many Shapes
 */

import processing.core.*;

import java.util.Vector;



public class Player {
	
	PApplet parent;	// for accessing processing methods
	Vector<Shape> shapes = new Vector<Shape>();	// shapes in one player
	
	int currentShape = 0;	// keeping track of editable shape (?)
	boolean bAddHandle = false; // to know when to add a handle
	

	/*
	 * Public Methods
	 */

	public Player(PApplet p){
		parent = p;
		setup();
	}
	
	public void setup() {
		// first shape by default
		shapes.add( new Shape(parent, parent.color(255,238,135,200)) );
	}

	public void draw() {		
		// update and draw all shapes for this player
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
		}
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).draw();
		}
		
	}
	
	public void mousePressed(){
		// add a handle
		if(bAddHandle)	shapes.elementAt(currentShape).add(parent.mouseX, parent.mouseY);
	}
	
	public void mouseDragged(){
		// pass mouseEvent to every shape
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseDragged();
		}
		
	}
	
	public void mouseReleased(){
		// pass mouseEvent to every shape
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseReleased();
		}
	}
	
	public void keyPressed(){
		switch (parent.key) {
		case '+':	// start adding handles
			bAddHandle = true;
			break;

		default:
			break;
		}
	}
	
	public void keyReleased(){
		switch (parent.key) {
		case '+':	// stop adding handles
			bAddHandle = false;
			break;

		default:
			break;
		}
	}
	
	public float[][] getInfo(){
		// get the info from a single shape
		float info[][] = shapes.firstElement().getInfo();
		return info;
	}
}
