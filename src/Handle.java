import processing.core.*;
import java.util.Vector;

class Handle {
	PApplet parent;
	int x, y;
	int box1x, box1y, box2x, box2y; // box1 = bottom , box2 = top
	int boxCx, boxCy; // central box
	int length;		// length of the handle
	int size;		// box size
	boolean over1,over2, overC;	// check if over any box
	boolean press, pressC;		// chek if pressed on any box
	boolean locked = false;		// locked top and bottom boxes
	boolean lockedC = false;	// locked central box
	boolean otherslocked = false;	// are other handles' boxes locked?
	boolean otherslockedC = false;
	Vector<Handle> others;		// the other handles
	int fillColor;				// box color
	int strokeColor;			// line color
	int fillAlpha = 64;			// box alpha
	int strokeAlpha = 128;		// line alpha

	Handle(PApplet p, int ix, int iy, Vector<Handle> o){
		parent = p;
		x = ix;
		y = iy;
		length = 50;
		size = 10;
		boxCx = x - size/2;
		boxCy = y - size/2;
		box1x = x - size/2;
		box1y = y+length - size/2;
		box2x = box1x;
		box2y = y-length - size/2;
		others = o;
		
		fillColor = parent.color(255,64);
		strokeColor = parent.color(0, 128);
	}
	
	Handle(PApplet p, int ix, int iy, int il, int is, Vector<Handle> o){
		parent = p;
		x = ix;
		y = iy;
		length = il;
		size = is;
		boxCx = x - size/2;
		boxCy = y - size/2;
		box1x = x - size/2;
		box1y = y+length - size/2;
		box2x = box1x;
		box2y = y-length - size/2;
		others = o;

		fillColor = parent.color(255, fillAlpha);
		strokeColor = parent.color(0, strokeAlpha);
	}

	void update() {
		// move boxes to place
		boxCx = x - size/2;
		boxCy = y - size/2;
		box1x = x - size/2;
		box1y = y+length - size/2;
		box2x = box1x;
		box2y = y-length - size/2;

		for(int i=0; i<others.size(); i++) {
			if(others.elementAt(i).locked == true) {
				otherslocked = true;
				break;
			} else {
				otherslocked = false;
			}  

			if(others.elementAt(i).lockedC == true) {
				otherslockedC = true;
				break;
			} else {
				otherslockedC = false;
			}
		}

		if(otherslocked == false) {
			over();
			press();
		}
		
		if(otherslockedC == false) {
			over();
			press();
		}

		if(press) {
			length = lock((int)PApplet.dist(parent.mouseX, parent.mouseY, x, y)-size/2, 0, (int)PApplet.dist(parent.mouseX, parent.mouseY, x, y)-size-1);
		}
		
		if(pressC){
			x = parent.mouseX;
			y = parent.mouseY;
		}
	}

	void over(){
		if(!parent.mousePressed){
			if(overRect(boxCx, boxCy, size, size)) {
				overC = true;
			} else {
				overC = false;
			}

			if(overRect(box1x, box1y, size, size)) {
				over1 = true;
			} else {
				over1 = false;
			}

			if(overRect(box2x, box2y, size, size)) {
				over2 = true;
			} else {
				over2 = false;
			}
		}
	}

	void press(){
		if(over1 && parent.mousePressed || locked ||
				over2 && parent.mousePressed) {
			press = true;
			locked = true;
		} else {
			press = false;
		}
		
		if(overC && parent.mousePressed  || lockedC){
			pressC = true;
			lockedC = true;
		}else{
			pressC = false;
		}
	}

	void release(){
		locked = false;
		lockedC = false;
	}

	void draw() {
		parent.stroke(strokeColor, 32);
		parent.line(x, y, x, y+length);
		parent.line(x, y, x, y-length);
		parent.fill(fillColor);
		parent.stroke(strokeColor);
		parent.rect(box1x, box1y, size, size);
		parent.rect(box2x, box2y, size, size);
		parent.rect(boxCx, boxCy, size, size);
		if(over1 || over2 || press) {
			parent.line(box1x, box1y, box1x+size, box1y+size);
			parent.line(box1x, box1y+size, box1x+size, box1y);
			parent.line(box2x, box2y, box2x+size, box2y+size);
			parent.line(box2x, box2y+size, box2x+size, box2y);
		}
		if(overC || pressC){
			parent.line(boxCx, boxCy, boxCx+size, boxCy+size);
			parent.line(boxCx, boxCy+size, boxCx+size, boxCy);
		}

	}


	boolean overRect(int x, int y, int width, int height) {
		if (parent.mouseX >= x && parent.mouseX <= x+width && 
				parent.mouseY >= y && parent.mouseY <= y+height ) {
			return true;
		} else {
			return false;
		}
	}

	int lock(int val, int minv, int maxv) { 
		return  PApplet.min(PApplet.max(val, minv), maxv); 
	} 

	int getLength(){
		return length;
	}
	
	int getX(){
		return x;
	}
	
	int getY(){
		return y;
	}
	
	int getTopX(){
		return x;
	}
	
	int getTopY(){
		return y-length;
	}
	
	int getBottomX(){
		return x;
	}
	
	int getBottomY(){
		return y+length;
	}
	
	/*
	void setColor(int f, int s){
		fillColor = f;
		strokeColor = s;
	}
	
	void fill(int c){
		fillColor = c;
	}
	
	void stroke(int c){
		strokeColor = c;
	}
	*/
}
