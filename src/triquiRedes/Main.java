package triquiRedes;

import processing.core.PApplet;

public class Main extends PApplet{
	
	private Logica log;
	
	public static void main(String[] args) {
		System.setProperty("java.net.preferIPv4Stack", "true");
		PApplet.main("triquiRedes.Main");
	}
	
	@Override
	public void settings(){
		size(600,600);
	}
	
	@Override
	public void setup(){
		log = new Logica(this);
	}
	
	@Override
	public void draw(){
		background(0);
		log.pintar();
	}
	
	@Override
	public void mousePressed(){
		log.click();
	}
	
	@Override
	public void mouseReleased(){
		log.release();
	}

}