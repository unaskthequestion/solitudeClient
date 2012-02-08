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

import processing.xml.*; 

import java.applet.*; 
import java.awt.Dimension; 
import java.awt.Frame; 
import java.awt.event.MouseEvent; 
import java.awt.event.KeyEvent; 
import java.awt.event.FocusEvent; 
import java.awt.Image; 
import java.io.*; 
import java.net.*; 
import java.text.*; 
import java.util.*; 
import java.util.zip.*; 
import java.util.regex.*; 

public class SolitudeClient extends PApplet {
	ControlP5 controlP5;
	Textfield ipTextfield, portTextfield;
	int buttonValue		= 0;
	static final int CONTROLP5_WIDTH 	= 100;
	
//	Vector<Player> players = new Vector<Player>();
	int playerID = 0;
	static final int NUM_PLAYERS = 8;
	Vector<Shape> shapes	= new Vector<Shape>();
	boolean bAddHandle	= false;
	String sAddHandle = "Add Handles";
	int currentShape	= 0;
	
	NetAddress remoteLocation;
	static String HOST = "127.0.0.1";
	static int	PORT = 12000;
	
	final int CANVAS_WIDTH = screen.width - CONTROLP5_WIDTH;
	final int CANVAS_HEIGHT = 480;

	/*
	 * Public Methods
	 */

	public void setup() {
		size(CANVAS_WIDTH+CONTROLP5_WIDTH,CANVAS_HEIGHT);
//		background(255);
		fill(0,128);
		smooth();
		
		setGUI();
		
//		players.add(new Player(this));
		shapes.add( new Shape(this, color(255,238,135,200))); // just one shape for now
		
		remoteLocation = new NetAddress(HOST, PORT);
	}

	public void draw() {
		
		background(255);
		
		// guides
		stroke(255-32);
		line(0, height/2, CANVAS_WIDTH, CANVAS_HEIGHT/2);
		line(CANVAS_WIDTH/2, 0, CANVAS_WIDTH/2, CANVAS_HEIGHT);
		line(CANVAS_WIDTH, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
		
		// shapes
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
		if(name == "send") {
			sendOsc((int)theEvent.value());
		}
//		if(name == "ip") {
//			HOST = ipTextfield.getText();
//			remoteLocation = new NetAddress(HOST,PORT);
//			println("Remote Location: "+remoteLocation.address()+":"+remoteLocation.port());
//		}
//		if(name == "port"){
//			PORT = Integer.parseInt(portTextfield.getText());
//			println("PORT: "+this.portTextfield.getText());
//		}
		// PLAYER N buttons
		for(int i=0; i < SolitudeClient.NUM_PLAYERS; i++){
			if(Integer.parseInt(name.substring(name.length()-1)) == i+1) this.playerID = i+1; // set playerID
		}
	}
	
	public void sendOsc(int theValue){
//		println("a button event from send: "+buttonValue);
		println("\nThe following info should be sent:\n");
		float [][] info = shapes.firstElement().getInfo();
		for (int i = 0; i < info.length; i++) {
			println("x: "+info[i][0]+"\ty: "+info[i][1]+"\t\tth: "+info[i][2]);
			//				println("do nothing...");
		}

//		if(!remoteLocation.address().toString().equals( ipTextfield.getText() )){
		if(!ipTextfield.getText().equals(HOST)){
			HOST = ipTextfield.getText();
			remoteLocation = new NetAddress(HOST, PORT);
		}
		if(!portTextfield.getText().equals(Integer.toString(PORT))){
			PORT = Integer.parseInt(portTextfield.getText());
			remoteLocation = new NetAddress(HOST, PORT);
		}

		
		OscMessage msg = new OscMessage("/test");
		msg.add(playerID);
		msg.add(info);
		OscP5.flush(msg, remoteLocation);

		println("Sent OSC to: "+remoteLocation.address()+":"+remoteLocation.port());
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
		int buttonW = CONTROLP5_WIDTH-5;
		int buttonH = 20;
		int offset = 2;
		
		// Add - Edit switch
//		controlP5.addToggle("addHandle", false, width - buttonW, 0, buttonW, buttonH).setMode(ControlP5.SWITCH);
		Toggle t = controlP5.addToggle("addHandle");//, false, width - buttonW, 0, buttonW, buttonH).setMode(ControlP5.SWITCH);
		t.setColorActive(color(0,128,0));
		t.setPosition(width - buttonW, 0);
		t.setSize(buttonW, buttonH);
		t.setMode(ControlP5.SWITCH);
		t.captionLabel().set("   Add       Edit  ");
		t.captionLabel().style().marginTop = -20;

		// Send button
		controlP5.addButton("send",0, width - buttonW, buttonH+offset+10, buttonW, buttonH);
		// IP and PORT
//		Textlabel ipLabel = controlP5.addTextlabel("ipLabel", "Host IP", width - buttonW, (buttonH+offset)*2+20);
//		ipLabel.setColorValueLabel(color(0));
		
		ipTextfield = controlP5.addTextfield("ip", width - buttonW, (buttonH+offset)*3+10, buttonW-10, buttonH);
		ipTextfield.setText(HOST);
		ipTextfield.setAutoClear(false);
		ipTextfield.setColorLabel(color(0));
		ipTextfield.setColorBackground(color(228));
		ipTextfield.setColorValueLabel(color(128));
		ipTextfield.captionLabel().style().marginTop = -32;
		
//		Textlabel portLabel = controlP5.addTextlabel("portLabel", "Port", width - buttonW, (buttonH+offset)*4+20);
//		portLabel.setColorValueLabel(color(0));
		
		portTextfield = controlP5.addTextfield("port", width - buttonW, (buttonH+offset)*4+20, buttonW-10, buttonH);
		portTextfield.setText(Integer.toString(PORT));
		portTextfield.setAutoClear(false);
		portTextfield.setColorLabel(color(0));
		portTextfield.setColorBackground(color(228));
		portTextfield.setColorValueLabel(color(128));
		portTextfield.captionLabel().style().marginTop = -32;
		
		// Player buttons
		for (int i = 0; i < SolitudeClient.NUM_PLAYERS; i++) {
			controlP5.addButton("player "+(i+1),0, width - buttonW, (buttonH+offset)*(i+7), buttonW, buttonH);
		}
		
//		DropdownList playerslist = controlP5.addDropdownList("players", width - buttonW, (buttonH+offset)*3, buttonW, buttonH);
//		playerslist.setHeight(8);
//		playerslist.setItemHeight(buttonH);
//		playerslist.setBarHeight(buttonH);
//		playerslist.captionLabel().set("pulldown");
//		playerslist.captionLabel().style().marginTop = 3;
//		playerslist.captionLabel().style().marginLeft = 3;
//		playerslist.valueLabel().style().marginTop = 3;
//		
//		for(int i=0; i<8; i++){
//			playerslist.addItem("player "+i, i);
//		}
		
		
		/* IMAGE BUTTONS
		int spriteSize = 64;
		ControllerSprite sprite = new ControllerSprite(controlP5, loadImage("plus-"+spriteSize+"-blue.png"), spriteSize,spriteSize);
		sprite.setMask(loadImage("squareButtonMask-"+spriteSize+".png"));
		sprite.enableMask();
		
		controlP5.Button b = controlP5.addButton("playButton",100, width - spriteSize, 0, 100,100);
		b.setSprite(sprite);
		*/
	}
	


	
	static public void main(String args[]) {
		PApplet.main(new String[] { "--bgcolor=#FFFFFF", "SolitudeClient" });
	}
	  
	  
	
}

