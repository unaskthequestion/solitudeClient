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
	
	Vector<Shape> shapes = new Vector<Shape>();
	boolean bAddHandle = false;
	

	/*
	 * Public Methods
	 */

	public void setup() {
		size(1280,480);
		background(255);
		fill(0,128);
		smooth();
		
		shapes.add( new Shape(this) );
	}

	public void draw() {
		background(255);
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
		}
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).draw();
		}
	}
	
	public void mousePressed(){
		if(bAddHandle)
			shapes.elementAt(0).add(mouseX, mouseY);
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
		switch (key) {
		case '+':
			bAddHandle = true;
			break;

		default:
			break;
		}
	}
	
	public void keyReleased(){
		switch (key) {
		case '+':
			bAddHandle = false;
			break;

		default:
			break;
		}
	}

	/*
	 * Private Methods
	 */

}

