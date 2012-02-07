import processing.core.*;

import java.awt.Color;
import java.util.Vector;

public class Shape {

	private PApplet parent;
	PGraphics fbo; // an off-screen buffer for mouseOver testing
	
	Vector<Handle> handles = new Vector<Handle>();	// this shapes' handles
	
	float alpha;			// fill color alpha
	int color, colorOver;	// fill color and mouseOver color
	
	boolean bOver;			// is mouse over the shape?
	
	
	/******
	 * TODO
	 * 	- 	add handles in-between existing ones
	 */
	
	public Shape(PApplet p){
		parent = p;
		setup();
		color =  parent.color(0,alpha);
	}
	
	public Shape(PApplet p, int c){
		parent = p;
		color = c;
		setup();
	}
	
	void setup(){
		// set up an off-screen buffer for mouseOver testing
		fbo = parent.createGraphics(parent.width,parent.height, PApplet.P2D);
		bOver = false;
		// color setup
		alpha = 128;
		colorOver = parent.color(255,0,0, alpha);
	}
	
	public void add(float x, float y){
		// add a handle
		if(x < parent.width - SolitudeClient.controlP5Width)
			handles.add(new Handle(parent, (int)x, (int)y, handles));
	}
	
	public void remove(int i){
		// remove a handle
		handles.remove(i);
	}
	
	public void update(){
		for (int i = 0; i < handles.size(); i++) {
			handles.elementAt(i).update();
		}
		
		// check if mouse is over this shape
		isOver();
	}
	
	public void draw(){
		// draw to off-screen buffer for mouseOver testing
		drawToFbo();
		
		// change color if mouse is over this shape
		if(bOver) 	parent.fill(colorOver);
		else		parent.fill(color);
		
		// draw the shape
//		parent.fill(color);		
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
		
		// draw this shapes' handles
		for (int i = 0; i < handles.size(); i++) {
			handles.elementAt(i).draw();
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
		// just some feedback to console
		for (int i = 0; i < handles.size(); i++) {
			if(handles.elementAt(i).press)
				System.out.println(handles.elementAt(i).getLength());
			else if(handles.elementAt(i).pressC)
				System.out.println(handles.elementAt(i).getX()+" "+handles.elementAt(i).getY());
				
		}
	}
	
	public void setColor(int c){
		// change fill color
		color = c;		
	}
	
	void isOver(){
		// check if mouse is over this shape
		if(fbo.get(parent.mouseX, parent.mouseY) == 0){
			bOver = false;
		}else{
			bOver = true;
		}
	}
	
	public void drawToFbo(){
		// draw to off-screen buffer for mouseOver testing
		fbo.beginDraw();
		fbo.fill(0);
		fbo.beginShape();
		for (int i = 0; i < handles.size(); i++) {
			float x = handles.elementAt(i).getTopX();
			float y = handles.elementAt(i).getTopY();
			fbo.vertex(x, y);
		}

		for (int i = handles.size()-1; i >= 0; i--) {
			float x = handles.elementAt(i).getBottomX();
			float y = handles.elementAt(i).getBottomY();
			fbo.vertex(x, y);
		}
		fbo.endShape();
		fbo.endDraw();
	}
	
	public float[][] getInfo(){
		float info[][] = new float[handles.size()][3];
		
		for (int i = 0; i < info.length; i++) {
			info[i][0] = PApplet.norm(handles.elementAt(i).getX(), 0, parent.width);
			info[i][1] = PApplet.norm(handles.elementAt(i).getY(), 0, parent.height);
			info[i][2] = PApplet.norm(handles.elementAt(i).getLength()*2, 0, parent.height);
			
//			System.out.println(i+"- x: "+info[i][0]+" y: "+info[i][1]+" th: "+info[i][2]);
		}
		return info;
	}
}
