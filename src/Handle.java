import processing.core.*;

class Handle {
	PApplet parent;
  int x, y;
  int boxx, boxy, box2x, box2y;
  int length;
  int size;
  boolean over;
  boolean press;
  boolean locked = false;
  boolean otherslocked = false;
  Handle[] others;
  
  Handle(PApplet p, int ix, int iy, int il, int is, Handle[] o){
	  parent = p;
    x = ix;
    y = iy;
    length = il;
    size = is;
//    boxx = x+length - size/2;
//    boxy = y - size/2;
    boxx = x - size/2;
    boxy = y+length - size/2;
    others = o;
  }
  
  void update() 
  {
//    boxx = x+length;
//    boxy = y - size/2;
	    boxx = x - size/2;
	    boxy = y+length;
    
    for(int i=0; i<others.length; i++) {
      if(others[i].locked == true) {
        otherslocked = true;
        break;
      } else {
        otherslocked = false;
      }  
    }
    
    if(otherslocked == false) {
      over();
      press();
    }
    
    if(press) {
//      length = lock(parent.mouseX-parent.width/2-size/2, 0, parent.width/2-size-1);
    	length = lock(parent.mouseY-parent.height/2-size/2, 0, parent.height/2-size-1);
    }
  }
  
  void over()
  {
    if(overRect(boxx, boxy, size, size)) {
      over = true;
    } else {
      over = false;
    }
  }
  
  void press()
  {
    if(over && parent.mousePressed || locked) {
      press = true;
      locked = true;
    } else {
      press = false;
    }
  }
  
  void release()
  {
    locked = false;
  }
  
  void display() 
  {
    parent.line(x, y, x, y+length);
    parent.fill(255);
    parent.stroke(0);
    parent.rect(boxx, boxy, size, size);
    if(over || press) {
    	parent.line(boxx, boxy, boxx+size, boxy+size);
    	parent.line(boxx, boxy+size, boxx+size, boxy);
    }

  }
  

  boolean overRect(int x, int y, int width, int height) 
  {
    if (parent.mouseX >= x && parent.mouseX <= x+width && 
    		parent.mouseY >= y && parent.mouseY <= y+height) {
      return true;
    } else {
      return false;
    }
  }

  int lock(int val, int minv, int maxv) 
  { 
    return  PApplet.min(PApplet.max(val, minv), maxv); 
  } 
}
