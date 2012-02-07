import processing.core.*;

import java.awt.Color;
import java.util.Vector;

public class Shape {

	private PApplet parent;
	
	Vector<Handle> handles = new Vector<Handle>();
	
	int color = 0xCCccCCcc;
	float r,g,b = 128;
	float a = 128;
	
	/******
	 * TODO
	 * bOver - for hittest
	 */
	
	public Shape(PApplet p){
		parent = p;
	}
	
	public Shape(PApplet p, int c){
		parent = p;
		color = c;
	}
	
	public void add(float x, float y){
		handles.add(new Handle(parent, (int)x, (int)y, handles));
	}
	
	public void update(){
		for (int i = 0; i < handles.size(); i++) {
			handles.elementAt(i).update();
		}
	}
	
	public void draw(){
		parent.fill(color);
		/*****
		 * beginShape();
		 * for(i=0; i<grains.size()) - vertex([i].x, [i].y-([i].th/2))
		 * for(i=grains.size()-1; i<=0) - vertex([i].x, [i].y+([i].th/2))
		 * endShape();
		 */
		parent.beginShape();
		for (int i = 0; i < handles.size(); i++) {
			float x = handles.elementAt(i).getTopX();
			float y = handles.elementAt(i).getTopY();
			parent.vertex(x, y);
		}

		for (int i = handles.size()-1; i >= 0; i--) {
			float x = handles.elementAt(i).getBottomX();
			float y = handles.elementAt(i).getBottomY();
			parent.vertex(x, y);
		}
		parent.endShape();
		

		for (int i = 0; i < handles.size(); i++) {
			handles.elementAt(i).display();
		}
	}
	
	public void mousePressed(){
		
	}
	
	public void mouseReleased(){
		for (int i = 0; i < handles.size(); i++) {
			handles.elementAt(i).release();
		}
	}
	
	public void mouseDragged(){
		for (int i = 0; i < handles.size(); i++) {
			if(handles.elementAt(i).press)
				System.out.println(handles.elementAt(i).getLength());
			else if(handles.elementAt(i).pressC)
				System.out.println(handles.elementAt(i).getX()+" "+handles.elementAt(i).getY());
				
		}
	}
}
