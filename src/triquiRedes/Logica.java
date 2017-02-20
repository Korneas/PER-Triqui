package triquiRedes;

import processing.core.PApplet;

public class Logica {
	private PApplet app;
	private int x, y;
	private int [][] matriz;
	
	public Logica(PApplet app) {
		this.app = app;
		matriz = new int[3][3];
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				matriz[i][j]=0;
			}
		}
	}

	public void pintar() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				
				// CUADRÍCULA
				app.noFill();
				app.stroke(255);
				app.strokeWeight(5);
				app.rect((i*200)+(50), (j*200)+(50), 200, 200);
				app.noStroke();
				
				// INTERACCIÓN
				x = (150)+(i*200);
				y = (150)+(j*200);
				
				if (matriz[i][j]==1) {
					app.stroke(150,0,200);
					app.strokeWeight(10);
					app.ellipse(x, y, 100, 100);
					app.noStroke();
				}
				
				if (matriz[i][j]== 2) {
					app.fill(0,255,255);
					app.ellipse(x, y, 100, 100);
					app.noFill();
				}
			}
		}
		
	}

	public void click() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				x = (150)+(i*200);
				y = (150)+(j*200);
				
				if (app.dist(x, y, app.mouseX, app.mouseY)<150) {
					matriz[i][j] = 1;
				}
			}
		}
	}

	public void release() {
		
	}

	private void codigoProDeTiempo() {
		// public class Cronometro {
		// //Objeto para contabilizar tiempo y que no se vea afectado por un
		// frame rate
		// //DeclaraciÃ³n de las variables que ejecutaran el tiempo
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
