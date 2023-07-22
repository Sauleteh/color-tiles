import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Tablero extends JPanel  {

	//Aquí irían los atributos necesarios
	private int nFilas, nColumnas, puntuacion, vidas;
	private double probColores;
	private char[][] matrizTablero;
	private Random rand;
	
	//Constructores
	/**
	 * Inicialización del tablero, especificándole el número de filas, de columnas y el porcentaje de casillas con colores.
	 * @param filas es el número de filas que tendrá el tablero
	 * @param columnas es el número de columnas que tendrá el tablero
	 * @param colores es la probabilidad de 0 a 1 de casillas con colores en todo el tablero
	 */
	Tablero(int filas, int columnas, double colores) {
		//El constructor debe tener los parámetros oportunos 
		//para inicializar el tablero y el juego
		this.nFilas = filas;
		this.nColumnas = columnas;
		this.probColores = colores;
		
		this.setPuntuacion(0); // Inicializamos la puntuación a 0
		this.setVidas(3); // Se añaden las vidas correspondientes
		
		this.matrizTablero = new char [this.nFilas][this.nColumnas]; // Matriz de carácteres de nFilas y nColumnas
		this.rand = new Random(); // Objeto de la clase random para utilizar nextInt
		int colorElegido; // Variable para guardar el color resultante cada vez que se llame a nextInt
		
		for (int i = 0; i < this.nFilas; i++)
		{
			for (int j = 0; j < this.nColumnas; j++) // Recorrido de la matriz
			{
				if (Math.random() < this.probColores) // Si el número entre 0 y 1 es menor que la probabilidad deseada de colores...
				{
					colorElegido = this.rand.nextInt(6)+1; // Elegimos un color entre 1 y 6, según el número resultante tendrá un color u otro
					if (colorElegido == 1) this.matrizTablero[i][j] = 'R';
					else if (colorElegido == 2) this.matrizTablero[i][j] = 'G';
					else if (colorElegido == 3) this.matrizTablero[i][j] = 'B';
					else if (colorElegido == 4) this.matrizTablero[i][j] = 'Y';
					else if (colorElegido == 5) this.matrizTablero[i][j] = 'P';
					else this.matrizTablero[i][j] = 'O';
				}
				else this.matrizTablero[i][j] = '-';
			}
		}
		
		/* Mostrar matriz por pantalla
		for (int i = 0; i < this.nFilas; i++)
		{
			for (int j = 0; j < this.nColumnas; j++)
			{
				System.out.printf("%c ",this.matrizTablero[i][j]);
			}
			System.out.println();
		}
		*/

		// Añadimos el 'escuchador' de ratón
		addMouseListener(new MouseHandler());
	}
	
	/**
	 * Inicialización de un tablero por defecto de 8 filas, 12 columnas y un 60% de casillas coloreadas.
	 */
	Tablero() {
		this(8, 12, 0.6);
	}
	
	/**
	 * Inicialización de un tablero con un número de filas y un número de columnas a elección y un porcentaje de 60% de casillas coloreadas.
	 * @param filas es el número de filas que tendrá el tablero
	 * @param columnas es el número de columnas que tendrá el tablero
	 */
	Tablero(int filas, int columnas) {
		this(filas, columnas, 0.6);
	}

	//Métodos de la clase que implementan el juego: básicamente hacer una
	//jugada, dibujar el estado del tablero y comprobar si la partida se acabó

	//Método paint
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		//Aquí iría el código para pintar el estado del tablero
		// Los cuadrados son siempre de 40x40 unidades y existe un margen de 5 unidades a los bordes, además de un margen extra de 1 unidad entre los cuadrados y la rejilla
		g.setColor(Color.gray);
		
		// Inicialización de coordenadas de las líneas a dibujar
		int x1 = 5;
		int y1 = 5;
		int x2 = 5;
		int y2 = y1 + nFilas * 40 + nFilas * 2 + nFilas;
		/* La altura de las líneas verticales es el margen inicial más el número de unidades de los cuadrados totales
		 * más el margen entre los cuadrados más el número de líneas horizontales que hay entre los cuadrados que ocupan 1 unidad cada línea
		 */
		
		// Dibujamos las líneas verticales primero
		for (int i = 0; i < nColumnas + 1; i++)
		{
			g.drawLine(x1,y1,x2,y2);
			x1 += 43; // Sumamos las unidades del margen más las unidades del cuadrado más la unidad de la línea intermedia a la anterior coordenada
			x2 = x1;
		}
		
		x1 = 5;
		y1 = 5;
		x2 = x1 + nColumnas * 40 + nColumnas * 2 + nColumnas;
		y2 = 5;
		
		// Dibujamos las líneas horizontales después
		for (int i = 0; i < nFilas + 1; i++)
		{
			g.drawLine(x1,y1,x2,y2);
			y1 += 43; // Sumamos las unidades del margen más las unidades del cuadrado más la unidad de la línea intermedia a la anterior coordenada
			y2 = y1;
		}
		
		//Dibujamos los cuadrados de la matriz
		y1 = 7;
		
		for (int i = 0; i < this.nFilas; i++)
		{
			x1 = 7;
			for (int j = 0; j < this.nColumnas; j++)
			{
				if (this.matrizTablero[i][j] != '-') // Si es un guión, no dibujar nada
				{
					// Se elige el color según la letra de la casilla actual
					if (this.matrizTablero[i][j] == 'R') g.setColor(Color.red);
					else if (this.matrizTablero[i][j] == 'G') g.setColor(Color.green);
					else if (this.matrizTablero[i][j] == 'B') g.setColor(Color.blue);
					else if (this.matrizTablero[i][j] == 'Y') g.setColor(Color.yellow);
					else if (this.matrizTablero[i][j] == 'P') g.setColor(Color.pink);
					else g.setColor(Color.orange);
					
					g.fillRect(x1,y1,40,40);
				}
				x1 += 43;
			}
			y1 += 43;
		}
		
		// Pintamos la puntuación y las vidas
		String stringPuntuacionYVidas = "Puntos " + this.getPuntuacion() + "   Vidas " + this.getVidas();
		
		g.setColor(Color.black);
		g.setFont(new Font("Arial", Font.PLAIN, 18));
		g.drawString(stringPuntuacionYVidas, 5, 25 + nFilas * 40 + nFilas * 2 + nFilas);
		
	}	

	//Clase privada para capturar los eventos del ratón
	private class MouseHandler extends MouseAdapter {
		public void mouseClicked (MouseEvent e) {
			//Mostramos un diálogo con la posición del ratón 
			//para ver un ejemplo de cómo se obtienen las coordenadas
			//donde se produjo el click
			//JOptionPane.showMessageDialog(null, String.format("Ratón %d,%d \nPosición de la matriz: [%d,%d]",e.getX(),e.getY(),(e.getY()-5)/43,(e.getX()-5)/43));

			//Aquí irían las instrucciones para comprobar si el 
			//click del ratón se produjo en una posición correcta
			//y hacer la jugada correspondiente
			
			//Se pueden llamar a los métodos públicos de la clase

			if (!estaFueraDeRango(e.getX(),e.getY()))
			{
				if (casillaValida(e.getX(),e.getY()))
				{
					if (!hasPerdido())
					{
						hacerJugada(e.getX(),e.getY());
					}
				}
				else JOptionPane.showMessageDialog(null, String.format("Casilla no válida"));
			}
			else JOptionPane.showMessageDialog(null, String.format("Posición de la matriz fuera de rango"));
			//Seguramente habrá que repintar el tablero si se realizó
			//una jugada válida
			repaint();
		}
	}
	
	/**
	 * Función para saber si la casilla que se seleccionó está fuera de rango
	 * @param X es la posición horizontal del ratón cuando se hizo click izquierdo
	 * @param Y es la posición vertical del ratón cuando se hizo click izquierdo
	 * @return true si la casilla seleccionada excede el rango de la matriz y false en caso contrario
	 */
	public boolean estaFueraDeRango(int X, int Y)
	{
		if ((Y-5)/43 > this.nFilas - 1 || (X-5)/43 > this.nColumnas - 1) return true;
		else return false;
	}
	
	/**
	 * Función para saber si la casilla seleccionada es válida o no
	 * @param X es la posición horizontal del ratón cuando se hizo click izquierdo
	 * @param Y es la posición vertical del ratón cuando se hizo click izquierdo
	 * @return true si la casilla seleccionada no tiene un color y false en caso contrario
	 */
	public boolean casillaValida(int X, int Y)
	{
		if (this.matrizTablero[(Y-5)/43][(X-5)/43] == '-') return true;
		else return false;
	}
	
	/**
	 * Realización de una jugada, sumando la puntuación necesaria y restando las vidas si es necesario también
	 * @param X es la posición horizontal del ratón cuando se hizo click izquierdo
	 * @param Y es la posición vertical del ratón cuando se hizo click izquierdo
	 */
	public void hacerJugada(int X, int Y)
	{
		char[] coloresEnCuatroDirecciones = new char [4]; // [Arriba,Abajo,Izquierda,Derecha] Se guarda el carácter que represente al color que se encontró según la dirección
		int[][] posicionesEnCuatroDirecciones = new int [4][2];
		/* Se guarda la posición de la casilla que tiene color, en [X][0] se guarda la fila y en [X][1] la columna,
		 * siendo X la dirección correspondiente (Ejemplo: 1 era "abajo" y 3 "derecha").
		 */
		
		
		// Búsqueda del elemento de arriba
		int k = (Y-5)/43; // El elemento empieza a partir de la casilla seleccionada
		while (!(k == 0) && !(this.matrizTablero[k][(X-5)/43] != '-')) // Mientras la casilla no alcance la primera fila y no sea un guión, se reduce una fila
		{
			k--;
		}
		
		coloresEnCuatroDirecciones[0] = this.matrizTablero[k][(X-5)/43]; // Se guarda el carácter al que se haya llegado	
		posicionesEnCuatroDirecciones[0][0] = k; // Además de la posición de la fila
		posicionesEnCuatroDirecciones[0][1] = (X-5)/43; // Y su posición en la columna
		
		// Búsqueda del elemento de abajo
		k = (Y-5)/43; // El elemento empieza a partir de la casilla seleccionada
		while (!(k == this.nFilas - 1) && !(this.matrizTablero[k][(X-5)/43] != '-')) // Mientras la casilla no alcance la última fila y no sea un guión, se aumenta una fila
		{
			k++;
		}
		
		coloresEnCuatroDirecciones[1] = this.matrizTablero[k][(X-5)/43]; // Se guarda el carácter al que se haya llegado	
		posicionesEnCuatroDirecciones[1][0] = k; // Además de la posición de la fila
		posicionesEnCuatroDirecciones[1][1] = (X-5)/43; // Y su posición en la columna
		
		// Búsqueda del elemento de izquierda
		k = (X-5)/43; // El elemento empieza a partir de la casilla seleccionada
		while (!(k == 0) && !(this.matrizTablero[(Y-5)/43][k] != '-')) // Mientras la casilla no alcance la primera columna y no sea un guión, se disminuye una columna
		{
			k--;
		}
		
		coloresEnCuatroDirecciones[2] = this.matrizTablero[(Y-5)/43][k]; // Se guarda el carácter al que se haya llegado	
		posicionesEnCuatroDirecciones[2][0] = (Y-5)/43; // Además de la posición de la fila
		posicionesEnCuatroDirecciones[2][1] = k; // Y su posición en la columna
		
		// Búsqueda del elemento de derecha
		k = (X-5)/43; // El elemento empieza a partir de la casilla seleccionada
		while (!(k == this.nColumnas - 1) && !(this.matrizTablero[(Y-5)/43][k] != '-')) // Mientras la casilla no alcance la última columna y no sea un guión, se aumenta una columna
		{
			k++;
		}
		
		coloresEnCuatroDirecciones[3] = this.matrizTablero[(Y-5)/43][k]; // Se guarda el carácter al que se haya llegado	
		posicionesEnCuatroDirecciones[3][0] = (Y-5)/43; // Además de la posición de la fila
		posicionesEnCuatroDirecciones[3][1] = k; // Y su posición en la columna
		
		/*
		System.out.printf("Arriba: %c [%d,%d]\nAbajo: %c [%d,%d]\nIzquierda: %c [%d,%d]\nDerecha: %c [%d,%d]\n\n",
					coloresEnCuatroDirecciones[0],posicionesEnCuatroDirecciones[0][0],posicionesEnCuatroDirecciones[0][1],
					coloresEnCuatroDirecciones[1],posicionesEnCuatroDirecciones[1][0],posicionesEnCuatroDirecciones[1][1],
					coloresEnCuatroDirecciones[2],posicionesEnCuatroDirecciones[2][0],posicionesEnCuatroDirecciones[2][1],
					coloresEnCuatroDirecciones[3],posicionesEnCuatroDirecciones[3][0],posicionesEnCuatroDirecciones[3][1]);
		*/
		
		int cont1; // Contador de colores iguales
		int contTotal = 0; // Contador de eliminaciones totales
		int[] posicionesAEliminar = new int [4]; // Vector donde se guarda las posiciones que se eliminarán
		
		// Se recorre el vector coloresEnCuatroDirecciones comparando sus valores entre sí
		for (int i = 0; i < 3; i++) // Se empieza por el primer carácter
		{
			cont1 = 0; // Al inicio de cada comparación se inicia el contador de colores a 0
			for (int l = 0; l < 4; l++) posicionesAEliminar[l] = 0; // El vector de posiciones a eliminar se inicializa a 0 cada iteración
			
			for (int j = 0; j < 4; j++) // Se mira cada carácter del vector comparándolo con el carácter de la posición "i"
			{
				// Si ambas posiciones no son guiones y además tienen el mismo color...
				if (coloresEnCuatroDirecciones[i] != '-' && coloresEnCuatroDirecciones[j] != '-' && coloresEnCuatroDirecciones[i] == coloresEnCuatroDirecciones[j])
				{
					cont1++; // Se suma una unidad al contador de colores
					posicionesAEliminar[j] = 1; // Se guarda un 1 en la posición del vector donde se vaya a querer borrar el color
				}
			}
			
			/* Se cambia a guión si cont1 es mayor que 1 y antes de cambiarlos a guión tanto en el vector coloresEnCuatroDirecciones como en la matriz se añade el número
			 * de eliminaciones de esta iteración al número total de eliminaciones que se hagan después de terminar el bucle anidado entero.
			 */
			
			if (cont1 > 1) // Si se encontró mínimo dos colores iguales...
			{
				contTotal += cont1; // Se añade el número de eliminaciones al total
				
				for (int m = 0; m < 4; m++) // Recorremos el vector posicionesAEliminar buscando si hay un 1
				{
					if (posicionesAEliminar[m] == 1) // En caso de encontrar un 1...
					{
						coloresEnCuatroDirecciones[m] = '-'; // Se actualiza la lista de los colores que se está haciendo la búsqueda actual a un guión
						this.matrizTablero[posicionesEnCuatroDirecciones[m][0]][posicionesEnCuatroDirecciones[m][1]] = '-'; // Y obviamente también se actualiza el tablero de verdad
					}
				}
			}
		}
		
		if (contTotal == 0) // Si no se ha eliminado ningún cuadrado...
		{
			setVidas(getVidas() - 1); // Eliminar una vida
		}
		else if (contTotal == 2) // Si se han eliminado dos cuadrados...
		{
			setPuntuacion(getPuntuacion() + 2); // Sumar dos puntos
		}
		else if (contTotal == 3) // Si se han eliminado tres cuadrados...
		{
			setPuntuacion(getPuntuacion() + 5); // Sumar cinco puntos
		}
		else // Si se han eliminado cuatro cuadrados, en cualquiera de las dos variantes...
		{
			setPuntuacion(getPuntuacion() + 10); // Sumar diez puntos
		}
		
		hasPerdido();
		
	}
	
	/**
	 * Guarda el valor de la puntuación
	 * @param punt es el valor a guardar en la puntuación
	 */
	public void setPuntuacion(int punt)
	{
		this.puntuacion = punt;
	}
	
	/**
	 * Función para obtener el valor de la puntuación
	 * @return el valor de la puntuación
	 */
	public int getPuntuacion()
	{
		return this.puntuacion;
	}
	
	/**
	 * Guarda el valor de las vidas
	 * @param vid es el valor a guardar en las vidas
	 */
	public void setVidas(int vid)
	{
		this.vidas = vid;
	}
	
	/**
	 * Función para obtener el valor de las vidas
	 * @return el valor de las vidas
	 */
	public int getVidas()
	{
		return this.vidas;
	}
	
	/**
	 * Método que comprueba las dos posibles formas de perder el juego, además se mostrar por pantalla que se perdió
	 * @return true si se perdió el juego, false en caso contrario
	 */
	public boolean hasPerdido()
	{
		if (getVidas() == 0)
		{
			JOptionPane.showMessageDialog(null, String.format("Has perdido, lo siento!"));
			return true;
		}
		else if (!existeCasillaEliminar())
		{
			JOptionPane.showMessageDialog(null, String.format("Has ganado, ya no se pueden eliminar más piezas"));
			return true;
		}
		else return false;
	}
	
	/**
	 * Método para comprobar si existe una casilla que pueda ser elegible para eliminar un color
	 * @return true si existe una casilla que pueda eliminar colores, false en caso de no poder eliminar nada
	 */
	public boolean existeCasillaEliminar()
	{
		char[] coloresEnCuatroDirecciones = new char [4]; // [Arriba,Abajo,Izquierda,Derecha] Se guarda el carácter que represente al color que se encontró según la dirección
		int[][] posicionesEnCuatroDirecciones = new int [4][2];
		/* Se guarda la posición de la casilla que tiene color, en [X][0] se guarda la fila y en [X][1] la columna,
		 * siendo X la dirección correspondiente (Ejemplo: 1 era "abajo" y 3 "derecha").
		 */
		
		int cont1; // Contador de colores iguales
		int contTotal; // Contador de eliminaciones totales
		
		for (int i = 0; i < this.nFilas; i++)
		{
			for (int j = 0; j < this.nColumnas; j++)
			{
				if (this.matrizTablero[i][j] == '-') // Solo simular jugada si es una casilla sin color
				{
					
					// Búsqueda del elemento de arriba
					int k = i; // El elemento empieza a partir de la casilla seleccionada
					while (!(k == 0) && !(this.matrizTablero[k][j] != '-')) // Mientras la casilla no alcance la primera fila y no sea un guión, se reduce una fila
					{
						k--;
					}
					
					coloresEnCuatroDirecciones[0] = this.matrizTablero[k][j]; // Se guarda el carácter al que se haya llegado	
					posicionesEnCuatroDirecciones[0][0] = k; // Además de la posición de la fila
					posicionesEnCuatroDirecciones[0][1] = j; // Y su posición en la columna
					
					// Búsqueda del elemento de abajo
					k = i; // El elemento empieza a partir de la casilla seleccionada
					while (!(k == this.nFilas - 1) && !(this.matrizTablero[k][j] != '-')) // Mientras la casilla no alcance la última fila y no sea un guión, se aumenta una fila
					{
						k++;
					}
					
					coloresEnCuatroDirecciones[1] = this.matrizTablero[k][j]; // Se guarda el carácter al que se haya llegado	
					posicionesEnCuatroDirecciones[1][0] = k; // Además de la posición de la fila
					posicionesEnCuatroDirecciones[1][1] = j; // Y su posición en la columna
					
					// Búsqueda del elemento de izquierda
					k = j; // El elemento empieza a partir de la casilla seleccionada
					while (!(k == 0) && !(this.matrizTablero[i][k] != '-')) // Mientras la casilla no alcance la primera columna y no sea un guión, se disminuye una columna
					{
						k--;
					}
					
					coloresEnCuatroDirecciones[2] = this.matrizTablero[i][k]; // Se guarda el carácter al que se haya llegado	
					posicionesEnCuatroDirecciones[2][0] = i; // Además de la posición de la fila
					posicionesEnCuatroDirecciones[2][1] = k; // Y su posición en la columna
					
					// Búsqueda del elemento de derecha
					k = j; // El elemento empieza a partir de la casilla seleccionada
					while (!(k == this.nColumnas - 1) && !(this.matrizTablero[i][k] != '-')) // Mientras la casilla no alcance la última columna y no sea un guión, se aumenta una columna
					{
						k++;
					}
					
					coloresEnCuatroDirecciones[3] = this.matrizTablero[i][k]; // Se guarda el carácter al que se haya llegado	
					posicionesEnCuatroDirecciones[3][0] = i; // Además de la posición de la fila
					posicionesEnCuatroDirecciones[3][1] = k; // Y su posición en la columna
					
					contTotal = 0; // Contador de eliminaciones totales a 0
					
					// Se recorre el vector coloresEnCuatroDirecciones comparando sus valores entre sí
					for (int l = 0; l < 3; l++) // Se empieza por el primer carácter
					{
						cont1 = 0; // Al inicio de cada comparación se inicia el contador de colores a 0
						
						for (int n = 0; n < 4; n++) // Se mira cada carácter del vector comparándolo con el carácter de la posición "l"
						{
							// Si ambas posiciones no son guiones y además tienen el mismo color...
							if (coloresEnCuatroDirecciones[l] != '-' && coloresEnCuatroDirecciones[n] != '-' && coloresEnCuatroDirecciones[l] == coloresEnCuatroDirecciones[n])
							{
								cont1++; // Se suma una unidad al contador de colores
							}
						}
						
						/* Se cambia a guión si cont1 es mayor que 1 y antes de cambiarlos a guión tanto en el vector coloresEnCuatroDirecciones como en la matriz se añade el número
						 * de eliminaciones de esta iteración al número total de eliminaciones que se hagan después de terminar el bucle anidado entero.
						 */
						
						if (cont1 > 1) // Si se encontró mínimo dos colores iguales...
						{
							contTotal += cont1; // Se añade el número de eliminaciones al total
						}
					}
					
					if (contTotal > 1) // Si se puede eliminar colores...
					{
						return true; // Retornamos true y se termina la búsqueda
					}
				}
			}
		}
		return false; // Si se recorrió todo el tablero y no se retornó true, se retorna false
	}
}

