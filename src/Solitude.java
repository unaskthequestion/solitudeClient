/*
 * Project: 		Solitude
 * File: 			Solitude.java
 * Created by: 		roger, Feb 6, 2012
 */

//package ;

import processing.core.*;

import java.awt.Color;
import java.util.Vector;

public class Solitude extends PApplet {
	/*
	Vector<Shape> shapes = new Vector<Shape>();
	
	int currentShape = 0;
	boolean bAddHandle = false;
	*/
	
	Vector<Player> players = new Vector<Player>();

	/*
	 * Public Methods
	 */

	public void setup() {
		size(1280,480);
		background(255);
		fill(0,128);
		smooth();
		
//		shapes.add( new Shape(this, color(255,238,135,200)) );
		players.add(new Player(this));
	}

	public void draw() {
		background(255);
		/*
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
		}
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).draw();
		}*/
		
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).draw();
		}
	}
	
	public void mousePressed(){
//		if(bAddHandle)	shapes.elementAt(currentShape).add(mouseX, mouseY);
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mousePressed();
		}
	}
	
	public void mouseDragged(){
		/*
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseDragged();
		}*/
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mouseDragged();
		}
		
	}
	
	public void mouseReleased(){
/*
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseReleased();
		}*/
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mouseReleased();
		}
	}
	
	public void keyPressed(){
		/*
		switch (key) {
		case '+':
			bAddHandle = true;
			break;

		default:
			break;
		}
		*/
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).keyPressed();
		}
	}
	
	public void keyReleased(){
		/*
		switch (key) {
		case '+':
			bAddHandle = false;
			break;

		default:
			break;
		}
		*/
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).keyReleased();
		}
	}
	/*
	 * Private Methods
	 */

	

	/*
	static public void main(String args[]) {
	    PApplet.main(new String[] { "Solitude" });
	  }
	  */
	
}

