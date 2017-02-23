
package triquiRedes;

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

	private int[][] jugadaOponente;
	private int xO, yO;

	private boolean jugar;

	public Logica(PApplet app) {
		this.app = app;

		c = new Comunicacion();
		c.addObserver(this);
		Thread th = new Thread(c);
		th.start();

		id = c.getId();

		if (id == 1) {
			jugar = true;
			idOponente = 2;
		} else if (id == 2) {
			idOponente = 1;
		}

		matriz = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matriz[i][j] = 0;
			}
		}
	}

	public void pintar() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				// CUADR�CULA
				app.noFill();
				app.stroke(255);
				app.strokeWeight(5);
				app.rect((i * 200) + (50), (j * 200) + (50), 200, 200);
				app.noStroke();

				// INTERACCI�N
				x = (150) + (i * 200);
				y = (150) + (j * 200);

				if (matriz[i][j] == 1) {
					app.stroke(150, 0, 200);
					app.strokeWeight(10);
					app.ellipse(x, y, 100, 100);
					app.noStroke();
				}

				if (matriz[i][j] == 2) {
					app.fill(0, 255, 255);
					app.ellipse(x, y, 100, 100);
					app.noFill();
				}
			}
		}

		finalJuego();
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			String pos = (String) arg;

			String[] posiciones = pos.split(":");
			xO = Integer.parseInt(posiciones[0]);
			yO = Integer.parseInt(posiciones[1]);
			System.out.println("envia");

			matriz[xO][yO] = idOponente;
			jugar = true;
		}

	}

	public void click() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				x = (150) + (i * 200);
				y = (150) + (j * 200);

				if (app.dist(x, y, app.mouseX, app.mouseY) < 100 && id != 0 && jugar == true) {
					if (matriz[i][j] == 0) {
						matriz[i][j] = id;
						MensajeID jugada = new MensajeID("posicion;" + i + ":" + j + ";" + c.getId());
						c.mensajePos(jugada);
					}
				}
			}
		}
	}

	public void release() {
		jugar = false;
	}

	public void finalJuego() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if ((matriz[0][0] == id && matriz[0][1] == id && matriz[0][2] == id)
						|| (matriz[1][0] == id && matriz[1][1] == id && matriz[1][2] == id)
						|| (matriz[2][0] == id && matriz[2][1] == id && matriz[2][2] == id)
						|| (matriz[0][0] == id && matriz[1][0] == id && matriz[2][0] == id)
						|| (matriz[0][1] == id && matriz[1][1] == id && matriz[2][1] == id)
						|| (matriz[0][2] == id && matriz[1][2] == id && matriz[2][2] == id)
						|| (matriz[0][0] == id && matriz[1][1] == id && matriz[2][2] == id)
						|| (matriz[0][2] == id && matriz[1][1] == id && matriz[2][0] == id)) {

					app.rectMode(PApplet.CENTER);
					app.fill(255);
					app.rect(app.width / 2, app.height / 2, 500, 200);
					app.textSize(50);
					app.fill(255, 0, 255);
					app.textAlign(PApplet.CENTER, PApplet.CENTER);
					app.text("Ganador:", app.width / 2, (app.height / 2) - 20);
					app.text("Jugador #: " + id, app.width / 2, (app.height / 2) + 20);
					app.rectMode(PApplet.CORNER);
				} else if ((matriz[0][0] == idOponente && matriz[0][1] == idOponente && matriz[0][2] == idOponente)
						|| (matriz[1][0] == idOponente && matriz[1][1] == idOponente && matriz[1][2] == idOponente)
						|| (matriz[2][0] == idOponente && matriz[2][1] == idOponente && matriz[2][2] == idOponente)
						|| (matriz[0][0] == idOponente && matriz[1][0] == idOponente && matriz[2][0] == idOponente)
						|| (matriz[0][1] == idOponente && matriz[1][1] == idOponente && matriz[2][1] == idOponente)
						|| (matriz[0][2] == idOponente && matriz[1][2] == idOponente && matriz[2][2] == idOponente)
						|| (matriz[0][0] == idOponente && matriz[1][1] == idOponente && matriz[2][2] == idOponente)
						|| (matriz[0][2] == idOponente && matriz[1][1] == idOponente && matriz[2][0] == idOponente)) {
					app.rectMode(PApplet.CENTER);
					app.fill(255);
					app.rect(app.width / 2, app.height / 2, 500, 200);
					app.textSize(50);
					app.fill(255, 0, 255);
					app.textAlign(PApplet.CENTER, PApplet.CENTER);
					app.text("Ganador:", app.width / 2, (app.height / 2) - 20);
					app.text("Jugador #: " + idOponente, app.width / 2, (app.height / 2) + 20);
					app.rectMode(PApplet.CORNER);
				} else if (matriz[0][0] != 0 && matriz[0][1] != 0 && matriz[0][2] != 0 && matriz[1][0] != 0
						&& matriz[1][1] != 0 && matriz[1][2] != 0 && matriz[2][0] != 0 && matriz[2][1] != 0
						&& matriz[2][2] != 0) {
					app.rectMode(PApplet.CENTER);
					app.fill(255);
					app.rect(app.width / 2, app.height / 2, 500, 200);
					app.textSize(50);
					app.fill(255, 0, 255);
					app.textAlign(PApplet.CENTER, PApplet.CENTER);
					app.text("Empate", app.width / 2, app.height / 2);
					app.rectMode(PApplet.CORNER);
				}
			}
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
