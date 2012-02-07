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
		
		num = height/15;
		handles = new Handle[num];
		int hsize = 10;
		for (int i = 0; i < num	; i++) {
			handles[i] = new Handle(this, width/2, 10+i*15, 50-hsize/2, 10, handles);
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
//		println("pressed at "+mouseX+" "+mouseY);
//		mx = mouseX;
//		my = mouseY;
		
		
	}
	
	public void mouseReleased(){
//		println("released at "+mouseX+" "+mouseY+" - thickness: "+abs(my-mouseY));
		/*float dst = dist(mx,my,mouseX,mouseY);
		
		if(dst > 2){
			sh.add(mx, my, dst);
		}else{
			sh.add(mx, my, 10);
		}*/
		
		for (int i = 0; i < num; i++) {
			handles[i].release();
		}
	}

	/*
	 * Private Methods
	 */

}

