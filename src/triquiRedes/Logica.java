
package triquiRedes;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {
	private PApplet app;

	private Comunicacion c;
	private int id, idOponente;

	private int x, y;
	private int[][] matriz;
	private int xJ, yJ;
	private int xO, yO;

	private boolean jugar;
	private boolean check;

	public Logica(PApplet app) {
		this.app = app;

		c = new Comunicacion();
		Thread th = new Thread(c);
		th.start();

		c.addObserver(this);

		id = c.getId();

		if (id == 1) {
			idOponente = 2;
			jugar = true;
			System.out.println("Voy primero");
		} else if (id == 2) {
			idOponente = 1;
			jugar = false;
			System.out.println("Voy de segundo");
		}

		matriz = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matriz[i][j] = 0;
			}
		}

		xJ = 4;
		yJ = 4;

	}

	public void pintar() {
		
		app.text("Soy jugador: "+id, 25, 20);
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				app.noFill();
				app.stroke(0,255,0);
				app.strokeWeight(3);
				app.line(25 + (25), 183 + (25), 183*3,  183+ (25));
				app.line(25 + (25), 183*2 + (25), 183*3,  183*2+ (25));
				app.line(183+25, 25 + (25), 183+25,  183*3);
				app.line(183*2+25, 25 + (25), 183*2+25,  183*3);
				app.noStroke();
				x = (116) + (i * 183);
				y = (116) + (j * 183);

				if (matriz[i][j] == 1) {
					app.stroke(200, 0, 50);
					app.strokeWeight(4);
					app.ellipse(x, y, 100, 100);
					app.noStroke();
				}

				if (matriz[i][j] == 2) {
					app.stroke(0, 10, 255);
					app.strokeWeight(4);
					app.line(x - 50, y - 50, x + 50, y + 50);
					app.line(x + 50, y - 50, x - 50, y + 50);
				}
			}
		}

	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String pos = (String) arg;

			String[] posiciones = pos.split(":");
			xO = Integer.parseInt(posiciones[0]);
			yO = Integer.parseInt(posiciones[1]);
			int idJugador = Integer.parseInt(posiciones[2]);

			if (idJugador != id) {
				matriz[xO][yO] = idOponente;
				jugar = true;
			}
		}

	}

	public void click() {
		System.out.println(jugar + ":" + check + ":" + xJ + ":" + yJ + ":" + id);

		if (jugar && !check && xJ == 4 && yJ == 4) {
			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					x = (116) + (i * 183);
					y = (116) + (j * 183);

					if (app.dist(x, y, app.mouseX, app.mouseY) < 150 && id != 0 && matriz[i][j] == 0) {
						matriz[i][j] = id;

						xJ = i;
						yJ = j;

						check = true;
					}
				}
			}
		}
	}

	public void release() {
		if (jugar && check && xJ != 4 && yJ != 4) {
			MensajeID jugada = new MensajeID("posicion;" + xJ + ":" + yJ + ":" + id);
			try {
				c.enviar(c.serialize(jugada), c.getGroupAddress(), 5000);
			} catch (IOException e) {
				e.printStackTrace();
			}

			jugar = false;
			check = false;
			xJ = 4;
			yJ = 4;
		} else {
			System.out.println("Jugada no válida");
		}
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
