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
	
	Vector shapes = new Vector();
	Shape sh = new Shape(this, Color.red);
	float mx, my;
	
	Handle [] handles;
	int num;

	/*
	 * Public Methods
	 */

	public void setup() {
		size(1280,480);
		background(255);
		fill(0,128);
		smooth();
		
		num = width/15;
		handles = new Handle[num];
		int hsize = 10;
		int vsize = 10;
		for (int i = 0; i < num	; i++) {
//			handles[i] = new Handle(this, width/2, 10+i*15, 50-hsize/2, 10, handles);
			handles[i] = new Handle(this, 10+i*15, height/2, 50-vsize/2, 10, handles);
		}
	}

	public void draw() {
		background(255);
		sh.draw();
		
		for (int i = 0; i < num; i++) {
			handles[i].update();
			handles[i].display();
		}
		
		if(mousePressed){
			float dy = dist(mx,my,mouseX,mouseY)/2;
			line(mx,my-dy, mx, my+dy);
		}
	}
	
	public void mousePressed(){
	}
	
	public void mouseDragged(){
		for (int i = 0; i < num; i++) {
			if(handles[i].press)
				println(handles[i].getLength());
			else if(handles[i].pressC)
				println(handles[i].getX()+" "+handles[i].getY());
				
		}
		
	}
	
	public void mouseReleased(){
		for (int i = 0; i < num; i++) {
			handles[i].release();
		}
	}

	/*
	 * Private Methods
	 */

}

