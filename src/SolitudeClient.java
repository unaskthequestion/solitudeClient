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

	final int CANVAS_WIDTH = screen.width - CONTROLP5_WIDTH;
	final int CANVAS_HEIGHT = 480;

	NetAddress remoteLocation;
	static String HOST = "127.0.0.1";
	static int	PORT = 12000;
	
	ControlP5 gui;
	Textfield tfIp, tfPort;
	static final int CONTROLP5_WIDTH 	= 100;

	//	Vector<Player> players = new Vector<Player>();
	int playerID = 0;
	static final int NUM_PLAYERS = 8;
	Vector<Shape> shapes	= new Vector<Shape>();
	static boolean bEdit	= false;
	String sAddHandle = "Add Handles";
	int currentShape	= 0;// color info for painting shapes
	int r = 228;
	int g = 228;
	int b = 228;
	int a = 128;
	// one color per player
	int p1color = color(r,0,0,a);
	int p2color = color(r,g,0,a);
	int p3color = color(r,0,b,a);
	int p4color = color(0,g,0,a);
	int p5color = color(0,g,b,a);
	int p6color = color(0,0,b,a);
	int p7color = color(r,a,0,a);
	int p8color = color(0,a,b,a);
	// player color array for easy accessibility
	int playerColors[] = {p1color, p2color, p3color, p4color, p5color, p6color, p7color, p8color};

	public void setup() {
		size(CANVAS_WIDTH+CONTROLP5_WIDTH,CANVAS_HEIGHT);
		//		background(255);
		fill(0,128);
		smooth();

		setGUI();

		shapes.add( new Shape(this, color(0,32))); // just one shape for now

		remoteLocation = new NetAddress(HOST, PORT);
	}

	public void draw() {

		background(255 - ((bEdit ? 1:0)*10));

		// guides
		stroke(255-32);
		line(0, height/2, CANVAS_WIDTH, CANVAS_HEIGHT/2);
		line(CANVAS_WIDTH/2, 0, CANVAS_WIDTH/2, CANVAS_HEIGHT);
		line(CANVAS_WIDTH, 0, CANVAS_WIDTH, CANVAS_HEIGHT);

		// shapes
		noStroke();
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).update();
			shapes.elementAt(i).draw();
		}

	}

	public void mousePressed(){
		if(!bEdit && mouseX < CANVAS_WIDTH) shapes.elementAt(currentShape).add(mouseX, mouseY);
		sendOsc();
	}
	
	public void mouseDragged(){
		sendOsc();
	}

	public void mouseReleased(){
		for (int i = 0; i < shapes.size(); i++) {
			shapes.elementAt(i).mouseReleased();
		}
		sendOsc();
	}

	public void keyPressed(){
		// delete last element
		if(key == BACKSPACE){
			if(shapes.size() > 0) {
				shapes.elementAt(0).removeLast();
			}
		}
		
		sendOsc();
	}

	public void keyReleased(){
		if(key == CODED){
			if(keyCode == CONTROL){
				bEdit = false;
				println(bEdit);
			}
		}
	}

	public void sendOsc(){
		println("Sending info");
		// get the nodes info into an array
		float [][] info = shapes.elementAt(0).getInfo();
		for (int i = 0; i < info.length; i++) {
			println("x: "+info[i][0]+"\ty: "+info[i][1]+"\t\tth: "+info[i][2]);
		}

		// check connection settings and update them if needed
		if(!tfIp.getText().equals(HOST)){
			HOST = tfIp.getText();
			remoteLocation = new NetAddress(HOST, PORT);
		}
		if(!tfPort.getText().equals(Integer.toString(PORT))){
			PORT = Integer.parseInt(tfPort.getText());
			remoteLocation = new NetAddress(HOST, PORT);
		}

		// send message
		if(playerID > 0){
			OscMessage msg = new OscMessage("/test");
			msg.add(playerID);
			msg.add(info);
			OscP5.flush(msg, remoteLocation);

			println("Sent OSC to: "+remoteLocation.address()+":"+remoteLocation.port());
		}else{
			println("YOU NEED TO CHOOSE A PLAYER NUMBER!!");
		}
	}

	public void controlEvent(ControlEvent theEvent){
		String name = theEvent.controller().name();
		println(name);
		
		// PLAYER N buttons
		if(name.substring(0,name.length()-2).equals("player")){
			//----------- I THINK THIS LOOP SHOULD GO OUT!!---------------
			for(int i=0; i < SolitudeClient.NUM_PLAYERS; i++){
				if(Integer.parseInt(name.substring(name.length()-1)) == i+1){
					this.playerID = i+1; // set playerID
					shapes.elementAt(0).setColor(playerColors[i]); // change color
				}
			}
		}
	}

	public void setGUI(){
		gui = new ControlP5(this);
		int buttonW = CONTROLP5_WIDTH-5;
		int buttonH = 20;
		int offset = 2;

		// IP input field
		tfIp = gui.addTextfield("ip", width - buttonW, buttonH, buttonW-10, buttonH);
		tfIp.setText(HOST);
		tfIp.setAutoClear(false);
		tfIp.setColorLabel(color(0));
		tfIp.setColorBackground(color(228));
		tfIp.setColorValueLabel(color(128));
		tfIp.captionLabel().style().marginTop = -32;

		// PORT input field
		tfPort = gui.addTextfield("port", width - buttonW, (buttonH+offset)*2+20, buttonW-10, buttonH);
		tfPort.setText(Integer.toString(PORT));
		tfPort.setAutoClear(false);
		tfPort.setColorLabel(color(0));
		tfPort.setColorBackground(color(228));
		tfPort.setColorValueLabel(color(128));
		tfPort.captionLabel().style().marginTop = -32;

		// Player buttons
		for (int i = 0; i < SolitudeClient.NUM_PLAYERS; i++) {
			gui.addButton("player "+(i+1),0, width - buttonW, (buttonH+offset)*(i+5), buttonW, buttonH);
		}
	}




	static public void main(String args[]) {
		PApplet.main(new String[] { "--bgcolor=#FFFFFF", "SolitudeClient" });
	}



}

