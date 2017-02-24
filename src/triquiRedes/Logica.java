
package triquiRedes;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

import processing.core.PApplet;

public class Logica implements Observer {
	private PApplet app;

	private Comunicacion c;
	private int id, idOponente, idGanador;

	private int x, y;
	private int[][] matriz;
	private int xJ, yJ;
	private int xO, yO;

	private boolean jugar, check, winner, empate;

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
		} else if (id >= 3) {
			jugar = false;
			System.out.println("Soy espectador");
		}

		matriz = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matriz[i][j] = 0;
			}
		}

		xJ = 4;
		yJ = 4;

		winner = false;
	}

	public void pintar() {

		app.noStroke();
		app.textSize(12);

		app.textAlign(PApplet.LEFT);
		app.text("Soy jugador: " + id, 25, 20);
		app.textAlign(PApplet.RIGHT);
		if (id <= 2) {
			if (jugar && !winner) {
				app.text("Mi turno", 575, 20);
			} else if (!jugar && !winner) {
				app.text("Turno del oponente", 575, 20);
			}
		} else if (id >= 3) {
			app.text("Espectador", 575, 20);
		}

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {

				app.noFill();
				app.stroke(0, 255, 0);
				app.strokeWeight(3);
				app.line(25 + (25), 183 + (25), 183 * 3, 183 + (25));
				app.line(25 + (25), 183 * 2 + (25), 183 * 3, 183 * 2 + (25));
				app.line(183 + 25, 25 + (25), 183 + 25, 183 * 3);
				app.line(183 * 2 + 25, 25 + (25), 183 * 2 + 25, 183 * 3);
				// app.rect(25+(i*183), 25+(j*183), 183, 183);
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
					app.stroke(0, 50, 255);
					app.strokeWeight(4);
					app.line(x - 50, y - 50, x + 50, y + 50);
					app.line(x + 50, y - 50, x - 50, y + 50);
				}

				// ========HACER 3 EN RAYA RAYA=============
				// Horizontales

				if (matriz[i][j] != 0 && i == 0) {
					if (matriz[i][j] == matriz[i + 1][j]) {
						if (matriz[i][j] == matriz[i + 2][j]) {
							winner = true;
						}
					}
				}

				// Verticales

				if (matriz[i][j] != 0 && j == 0) {
					if (matriz[i][j] == matriz[i][j + 1]) {
						if (matriz[i][j] == matriz[i][j + 2]) {
							winner = true;
						}
					}
				}

				// Diagonales
				if (matriz[i][j] != 0 && i == 2 && j == 0) {
					if (matriz[i][j] == matriz[i - 1][j + 1]) {
						if (matriz[i][j] == matriz[i - 2][j + 2]) {
							winner = true;
						}
					}
				}
				if (matriz[i][j] != 0 && i == 0 && j == 0) {
					if (matriz[i][j] == matriz[i + 1][j + 1]) {
						if (matriz[i][j] == matriz[i + 2][j + 2]) {
							winner = true;
						}
					}
				}
			}
		}

		app.textAlign(PApplet.CENTER);
		app.textSize(20);
		if (id <= 2 && id >= 1) {
			if (jugar && winner) {
				if (idOponente == 1) {
					app.fill(200, 0, 50);
				} else if (idOponente == 2) {
					app.fill(0, 50, 255);
				}
				app.rect(0, 200, app.width, 200);
				app.fill(255);
				app.text("Perdiste\nGano jugador: " + idOponente, 300, 300);
			} else if (!jugar && winner) {
				if (id == 1) {
					app.fill(200, 0, 50);
				} else if (id == 2) {
					app.fill(0, 50, 255);
				}
				app.rect(0, 200, app.width, 200);
				app.fill(255);
				app.text("Ganaste\nPerdio jugador: " + idOponente, 300, 300);
			}
		}

		if (id >= 3 && winner) {
			if (idGanador == 1) {
				app.fill(200, 0, 50);
			} else if (idGanador == 2) {
				app.fill(0, 50, 255);
			}
			app.rect(0, 200, app.width, 200);
			app.fill(255);
			app.text("Gano jugador: " + idGanador, 300, 300);
		}
		app.textSize(30);
		if (empate) {
			app.fill(0, 200, 0);
			app.rect(0, 200, app.width, 200);
			app.fill(255);
			app.text("Empate", 300, 300);
		}

		int contador = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (matriz[i][j] != 0) {
					contador++;
				}
			}
		}

		if (contador >= 9) {
			empate = true;
		}
	}

	@Override
	public void update(Observable o, Object arg) {
		if (arg instanceof String) {
			if (((String) arg).contains("posicion;")) {
				String[] pos = ((String) arg).split(";");

				String[] posiciones = pos[1].split(":");
				xO = Integer.parseInt(posiciones[0]);
				yO = Integer.parseInt(posiciones[1]);
				int idJugador = Integer.parseInt(posiciones[2]);

				if (idJugador != id && id <= 2) {
					jugar = true;
					matriz[xO][yO] = idOponente;
				}

				if (id >= 3) {
					matriz[xO][yO] = idJugador;
				}
			}

			if (((String) arg).contains("Gano")) {
				winner = true;

				if (id >= 3) {
					String[] win = ((String) arg).split(":");
					idGanador = Integer.parseInt(win[1]);
				}
			}
		}

	}

	public void click() {
		System.out.println(jugar + ":" + xJ + ":" + yJ + ":" + id);
		if (!winner) {
			if (jugar && !check && xJ == 4 && yJ == 4) {
				for (int i = 0; i < 3; i++) {
					for (int j = 0; j < 3; j++) {
						x = (116) + (i * 183);
						y = (116) + (j * 183);

						if (PApplet.dist(x, y, app.mouseX, app.mouseY) < 116 && id != 0 && matriz[i][j] == 0) {
							matriz[i][j] = id;

							xJ = i;
							yJ = j;

							check = true;
						}
					}
				}
			}
		}
	}

	public void release() {
		if (!winner) {
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

			if (winner) {
				MensajeID jugada = new MensajeID("Gano el jugador:" + id);
				try {
					c.enviar(c.serialize(jugada), c.getGroupAddress(), 5000);
				} catch (IOException e) {
					e.printStackTrace();
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
