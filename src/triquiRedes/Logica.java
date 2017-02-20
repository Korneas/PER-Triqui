package triquiRedes;

import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer{
	private PApplet app;
	
	private Comunicacion c;
	
	private int[][] jugadaOponente;
	private int xO,yO;
	
	private boolean jugar;

	public Logica(PApplet app) {
		this.app = app;
		
		c= new Comunicacion();
	}

	public void pintar() {

	}
	
	@Override
	public void update(Observable o, Object arg) {
		if(arg instanceof String){
			String pos = (String) arg;
			
			String[] posiciones = pos.split(":");
			xO = Integer.parseInt(posiciones[0]);
			yO = Integer.parseInt(posiciones[1]);
			
			//matriz[xO][yO]==2;
			jugar=true;
		}
		
	}
	

	public void click() {

	}

	public void release() {
		jugar=false;
		
//		MensajeID jugada = new MensajeID()
	}
	
	private void codigoProDeTiempo() {
		// public class Cronometro {
		// //Objeto para contabilizar tiempo y que no se vea afectado por un
		// frame rate
		// //Declaración de las variables que ejecutaran el tiempo
		// int comenzar = 0, parar = 0;
		// boolean reproducir = false;
		// //Si el reloj comienza
		// void empezar() {
		// comenzar = millis();
		// reproducir = true;
		// }
		// //Si el reloj se detiene
		// void detener() {
		// parar = millis();
		// reproducir = false;
		// }
		// //Reproducira el tiempo que se este pasando
		// int timepoReproducido() {
		// int tiempo;
		// if (reproducir) {
		// tiempo = (millis() - comenzar);
		// } else {
		// tiempo = (parar - comenzar);
		// }
		// return tiempo;
		// }
		// //Retorna los segundos reproducidos
		// int second() {
		// return (timepoReproducido() / 1000) % 60;
		// }
		// //Retorna los minutos reproducidos
		// int minute() {
		// return (timepoReproducido() / (1000*60)) % 60;
		// }
		// }
	}

	

}
