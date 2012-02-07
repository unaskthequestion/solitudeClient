import processing.core.*;

class Handle {
	PApplet parent;
	int x, y;
	int box1x, box1y, box2x, box2y; // box1 = bottom , box2 = top
	int boxCx, boxCy; // central box
	int length;
	int size;
	boolean over1,over2, overC;
	boolean press, pressC;
	boolean locked = false;
	boolean lockedC = false;
	boolean otherslocked = false;
	boolean otherslockedC = false;
	Handle[] others;

	Handle(PApplet p, int ix, int iy, int il, int is, Handle[] o){
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
	}

	void update() {
		boxCx = x - size/2;
		boxCy = y;
		box1x = x - size/2;
		box1y = y+length;
		box2x = box1x;
		box2y = y-length;

		for(int i=0; i<others.length; i++) {
			if(others[i].locked == true) {
				otherslocked = true;
				break;
			} else {
				otherslocked = false;
			}  

			if(others[i].lockedC == true) {
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

	void display() {
		parent.line(x, y, x, y+length);
		parent.line(x, y, x, y-length);
		parent.fill(255);
		parent.stroke(0);
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
				parent.mouseY >= y && parent.mouseY <= y+height) {
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
}
