/*
 * Project: 		Solitude
 * File: 			Solitude.java
 * Created by: 		roger, Feb 6, 2012
 */

//package ;

import processing.core.*;
import controlP5.*;
import oscP5.*;
import netP5.*;

import java.awt.Color;
import java.util.Vector;

public class SolitudeClient extends PApplet {
	
	ControlP5 controlP5;
	int buttonValue		= 0;
	static int _CONTROLP5WIDTH 	= 100;
	
//	Vector<Player> players = new Vector<Player>();
	int playerID = 0;
	static int _NUM_PLAYERS = 8;
	Vector<Shape> shapes	= new Vector<Shape>();
	boolean bAddHandle	= false;
	String sAddHandle = "Add Handles";
	int currentShape	= 0;
	
	NetAddress remoteLocation;
	static String _HOST = "127.0.0.1";
	static int	_PORT = 12000;

	/*
	 * Public Methods
	 */

	public void setup() {
		size(screen.width,480);
//		background(255);
		fill(0,128);
		smooth();
		
		setGUI();
		
//		players.add(new Player(this));
		shapes.add( new Shape(this, color(255,238,135,200))); // just one shape for now
		
		remoteLocation = new NetAddress(_HOST, _PORT);
	}

	public void draw() {
		
		background(255);
		
		stroke(255-32);
		line(0, height/2, width, height/2);
		line(width/2, 0, width/2, height);
		
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
			shapes.elementAt(i).draw();
		}
		
	}
	
	public void mousePressed(){
		if(bAddHandle) shapes.elementAt(currentShape).add(mouseX, mouseY);
	}
	
	/*public void mouseDragged(){
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseDragged();
		}
		
	}*/
	
	public void mouseReleased(){
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseReleased();
		}
	}
	
	/*public void keyPressed(){
		for (int i = 0; i < players.size(); i++) {
			shapes.elementAt(i).keyPressed();
		}
	}
	
	public void keyReleased(){
		for (int i = 0; i < players.size(); i++) {
			shapes.elementAt(i).keyReleased();
		}
	}*/
	
	public void controlEvent(ControlEvent theEvent){
		String name = theEvent.controller().name();
		println(name);
		
		if(name == "addHandle") bAddHandle = !bAddHandle;
		if(name == "send") send(0);
		// PLAYER N buttons
		for(int i=0; i < SolitudeClient._NUM_PLAYERS; i++){
			if(Integer.parseInt(name.substring(name.length()-1)) == i+1) this.playerID = i+1; // set playerID
		}
	}
	
	public void send(int theValue){
//		println("a button event from send: "+buttonValue);
		println("\nThe following info should be sent:\n");
		float [][] info = shapes.firstElement().getInfo();
		for (int i = 0; i < info.length; i++) {
			println("x: "+info[i][0]+"\ty: "+info[i][1]+"\t\tth: "+info[i][2]);
			//				println("do nothing...");
		}

		OscMessage msg = new OscMessage("/test");
		msg.add(playerID);
		msg.add(info);
		OscP5.flush(msg, remoteLocation);
	}
	/*
	public void addHandle(boolean theFlag){
		println("Add Handle: "+theFlag);
//		bAddHandle = theFlag;
	}*/
	
	/*
	 * Private Methods
	 */

	public void setGUI(){
		controlP5 = new ControlP5(this);
		int buttonW = _CONTROLP5WIDTH;
		int buttonH = 20;
		int vOffset = 2;
		controlP5.addToggle("addHandle", false, width - buttonW, 0, buttonW, buttonH).setMode(ControlP5.SWITCH);
		controlP5.addTextlabel("labelAdd","ADD",width - buttonW+5, 5);
		controlP5.addTextlabel("labelEdit","EDIT",width - 45, 5);
		controlP5.addButton("send",0, width - buttonW, buttonH+vOffset, buttonW, buttonH);
		
		// Player buttons
		for (int i = 0; i < SolitudeClient._NUM_PLAYERS; i++) {
			controlP5.addButton("player "+(i+1),0, width - buttonW, (buttonH+vOffset)*(i+3), buttonW, buttonH);
		}
		
		
		/* IMAGE BUTTONS
		int spriteSize = 64;
		ControllerSprite sprite = new ControllerSprite(controlP5, loadImage("plus-"+spriteSize+"-blue.png"), spriteSize,spriteSize);
		sprite.setMask(loadImage("squareButtonMask-"+spriteSize+".png"));
		sprite.enableMask();
		
		controlP5.Button b = controlP5.addButton("playButton",100, width - spriteSize, 0, 100,100);
		b.setSprite(sprite);
		*/
	}
	


	/*
	static public void main(String args[]) {
	    PApplet.main(new String[] { "Solitude" });
	  }
	  */
	
}

