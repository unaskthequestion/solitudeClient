/*
 * Project: 		Solitude
 * File: 			Solitude.java
 * Created by: 		roger, Feb 6, 2012
 */

//package ;

import processing.core.*;
import controlP5.*;

import java.awt.Color;
import java.util.Vector;

public class Solitude extends PApplet {
	
	ControlP5 controlP5;
	int buttonValue = 0;
	int controlP5Width = 100;
	
	Vector<Player> players = new Vector<Player>();
	

	/*
	 * Public Methods
	 */

	public void setup() {
		size(screen.width,480);
//		background(255);
		fill(0,128);
		smooth();
		
		setGUI();
		
		players.add(new Player(this));
		
	}

	public void draw() {
		background(255);
		
		stroke(255-32);
		line(0, height/2, width, height/2);
		
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).draw();
		}
		
	}
	
	public void mousePressed(){
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mousePressed();
		}
	}
	
	public void mouseDragged(){
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mouseDragged();
		}
		
	}
	
	public void mouseReleased(){
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).mouseReleased();
		}
	}
	
	public void keyPressed(){
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).keyPressed();
		}
	}
	
	public void keyReleased(){
		for (int i = 0; i < players.size(); i++) {
			players.elementAt(i).keyReleased();
		}
	}
	
	public void controlEvent(ControlEvent theEvent){
		println(theEvent.controller().name());
	}
	
	public void playButton(int theValue){
		println("a button event from buttonA: "+buttonValue);
		buttonValue = buttonValue+theValue;
	}
	
	/*
	 * Private Methods
	 */

	public void setGUI(){
		controlP5 = new ControlP5(this);
//		controlP5.addButton("buttonA",0, width - controlP5Width, 10, 200, 100);
		
		int spriteSize = 64;
		ControllerSprite sprite = new ControllerSprite(controlP5, loadImage("plus-"+spriteSize+"-blue.png"), spriteSize,spriteSize);
		sprite.setMask(loadImage("squareButtonMask-"+spriteSize+".png"));
		sprite.enableMask();
		
		controlP5.Button b = controlP5.addButton("playButton",100, width - spriteSize, 0, 100,100);
		b.setSprite(sprite);
	}
	


	/*
	static public void main(String args[]) {
	    PApplet.main(new String[] { "Solitude" });
	  }
	  */
	
}

