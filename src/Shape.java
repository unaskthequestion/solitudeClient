import processing.core.*;

import java.awt.Color;
import java.util.Vector;

public class Shape {
	private Vector grains = new Vector();
	private PApplet parent;
	private Color color = Color.gray;
	private float r,g,b;
	private float a = 128;
	
	public Shape(PApplet p){
		parent = p;
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	public Shape(PApplet p, Color c){
		parent = p;
		color = c;
		r = color.getRed();
		g = color.getGreen();
		b = color.getBlue();
	}
	
	public void add(float x, float y){
		grains.add(new Grain(x,y));
	}
	
	public void add(float x, float y, float thickness){
		grains.add(new Grain(x,y,thickness));
	}
	
	public void draw(){
		parent.fill(r,g,b,a);
		
		/*****
		 * beginShape();
		 * for(i=0; i<grains.size()) - vertex([i].x, [i].y-([i].th/2))
		 * for(i=grains.size()-1; i<=0) - vertex([i].x, [i].y+([i].th/2))
		 * endShape();
		 */
		
		/*float px = ((Grain)grains.elementAt(0)).x;
		float py = ((Grain)grains.elementAt(0)).y;
		float pth = ((Grain)grains.elementAt(0)).thickness;
		for (int i = 0; i < grains.size(); i++) {
			Grain g = (Grain)grains.elementAt(i);
			float gx = g.x;
			float gy = g.y;
			float th = g.thickness;
			
			//
			
			px = gx;
			py = gy;
			pth = th;
			
		}*/
	}
}
