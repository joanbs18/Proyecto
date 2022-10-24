package GUI;

import java.util.ArrayList;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
/**
 *
 * @author joans
 */
public class Ottelo {

    private int columnas = 12;
    private int filas = 12;
    private int contePlayer1;
    private int contePlayer2;
    char[][] celdasJuego = new char[filas][columnas];
    int userCont;
    int y, x;
    public int[] array1;
    public int[] array2;

    public void llenarJuego() {

        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < columnas; j++) {
                if (i == 5 && j == 5 || i == 6 && j == 6) {
                    celdasJuego[i][j] = '0';
                } else if (i == 5 && j == 6 || i == 6 && j == 5) {
                    celdasJuego[i][j] = '1';
                } else {
                    celdasJuego[i][j] = '.';
                }

            }
        }

    }

    public void mostrar() {
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j < filas; j++) {
                System.out.print(celdasJuego[i][j] + "\t");
                if (j == 11) {
                    System.out.println("\n");
                }
            }
        }
    }

    public Ottelo() {
        llenarJuego();
        buscar('0', '1');
       
    }

    public void añadir(int x, char charr, int y) {
        celdasJuego[x][y] = charr;
    }

    public void validar(int fila, int colum) {

        if (!(fila >= 0
                && fila <= (filas - 1)
                && colum >= 0
                && colum <= (columnas - 1))) {

        } else {

            int numPosiciones;

            if (fila == 0 || fila == (filas - 1)) {
                if (colum == 0 || colum == (columnas - 1)) {
                    numPosiciones = 3;
                } else {
                    numPosiciones = 5;
                }
            } else {
                if (colum == 0 || colum == (columnas - 1)) {
                    numPosiciones = 5;
                } else {
                    numPosiciones = 8;
                }
            }

            int[] posiciones1 = new int[numPosiciones];
            int[] posiciones2 = new int[numPosiciones];

            int indicePosicion = 0;
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {

                    if ((i != 0 || j != 0)
                            && (fila + i) >= 0
                            && (fila + i) <= (filas - 1)
                            && (colum + j) >= 0
                            && (colum + j) <= (columnas - 1)) {
                        posiciones1[indicePosicion] = fila + i;
                        posiciones2[indicePosicion] = colum + j;
                        indicePosicion++;
                    }

                }
            }
            array1 = posiciones1;
            array2 = posiciones2;
        }

    }
    public void presionarUnP(int i, int j,char char1){
    if (celdasJuego[i][j]=='p'){
    celdasJuego[i][j]=char1;
    }
    }

    public void buscar(char char1, char char2) {

        for (int i = 0; i < celdasJuego.length; i++) {
            for (int j = 0; j < celdasJuego[0].length; j++) {
                if (celdasJuego[i][j] == char1) {
                    validar(i, j);
                    for (int k = 0; k < array1.length; k++) {
                        if (celdasJuego[array1[k]][array2[k]] == char1) {//1
                            //array1[k]=-1;
                            //  array2[k]=-1;
                        } else if (celdasJuego[array1[k]][array2[k]] == char2) {
                            habilitar(array1[k], array2[k], 1, char2, char1);
                        }
                    }
                }

            }
        }
        
    }

    public void habilitar(int fila, int columna, int pos, char char1, char char2) {
        if (((columna + pos) < 12) && ((fila + pos) < 12) && pos >= 1) {
            
            if (comprobar(fila,columna+pos,char1)) {//DERECHO----->
                habilitar(fila, columna, pos += 1, char1, char2);
            } else if (celdasJuego[fila][columna + pos] == '.') {
                celdasJuego[fila][columna + pos] = 'p';
            }
             if (comprobar(fila,columna-pos,char1)) {//IZQUIERDA----->
                habilitar(fila, columna, pos -= 1, char1, char2);
            } else if (celdasJuego[fila][columna - pos] == '.') {
                celdasJuego[fila][columna - pos] = 'p';
            }
             
            if (comprobar(fila-pos,columna,char1)) {///ARRIBA^
               habilitar(fila, columna, pos -= 1, char1, char2);
            }else if (celdasJuego[fila - pos][columna] == '.') {
                celdasJuego[fila - pos][columna] = 'p';
            }
            if (comprobar(fila+pos,columna,char1)) {///ARRIBA^
                habilitar(fila, columna, pos += 1, char1, char2);
            }else if (celdasJuego[fila +pos][columna] == '.') {
                celdasJuego[fila + pos][columna] = 'p';
            }

           
           

            
        }
    
    }
private boolean comprobar(int i, int j,char xy){
        if (celdasJuego[i][j]==xy){
            return true;
        }
        return false;
}
    public char getMatriz(int x, int y) {
        return celdasJuego[x][y];
    }

    public void findLegalMove(ArrayList<Integer> arr) {
        int status;
        int change, max = 0;
        change = 0;
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                if (celdasJuego[i][j] == '.') {
                    int numberOfMoves[] = new int[1];
                    moverFicha(i, j, change, 'X', 'O', numberOfMoves);
                    if (numberOfMoves[0] != 0) {
                        arr.add(i);
                        arr.add(j);
                    }
                }
            }
        }
    }

    public int play(int xCor, int yCor) // play function for user
    {
        int status;
        int change, max = 0;
        int numberOfMoves[] = new int[1];
        change = 0;
        for (int i = 0; i < 12; ++i) {
            for (int j = 0; j < 12; ++j) {
                if (celdasJuego[i][j] == '.') {
                    moverFicha(i, j, change, 'X', 'O', numberOfMoves);
                    if (max < numberOfMoves[0]) {
                        max = numberOfMoves[0];
                    }
                }
            }
        }
        userCont = max;
        if (userCont == 0) // Hamle kalmazsa computera gecer
        {
            userCont = -1;
            return -1;
        }
        if (userCont != 0) {
            change = 1;
            if (!(celdasJuego[xCor][yCor] == '.')) {
                return 1; // dolu yere basti, gecersiz hamle
            }
            status = moverFicha(xCor, yCor, change, 'X', 'O', numberOfMoves);
            if (status == -1) {
                return 1; // gecersiz hamle
            }
        }
        for (int i = 0; i < 12; i++) {
            for (int j = 0; j < 12; j++) {
                System.out.printf("%c", celdasJuego[i][j]);
            }
            System.out.println("");
        }
        return 0;
    }

    public int moverFicha(int x, int y, int cambio, char char1, char char2, int[] array) {
        int cont = 0, st2 = 0, st = 0; //LOS ST SE UTILIZA PARA COMPROBAR QUE ACCION SE TERMINAR POR REALIZAR
        int status = -1, X, Y, temp;
        int str;
        int ix, x1, y1;
        x1 = x;
        y1 = y;
        array[0] = 0;

        if ((x1 < 12) && (celdasJuego[x1 + 1][y1] == char2)) {
            cont = x1;
            while ((cont < 12) && (st2 != -1) && (st != 2)) {
                cont++;

                if (cont < 12) {
                    if (celdasJuego[cont][y] == char2) {
                        st = 1;
                    }
                    if (celdasJuego[cont][y] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {//EN CASO QUE EN ESA POSICION ESTUVIERA EL CHAR == '1' o '0'
                temp = cont - x - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                for (int i = x; i < cont; i++) {
                    this.añadir(i, char2, y);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x - 1 >= 0) && (celdasJuego[x - 1][y] == char2)) {
            cont = x;
        }
        while ((cont >= 0) && (st2 != -1) && (st != 2)) {
            cont--;
            if (cont >= 0) {
                if (celdasJuego[cont][y] == char2) {
                    st = 1;
                } else if (celdasJuego[cont][y] == char1) {
                    st = 2;
                } else {
                    st2 = -1;
                }
            }
        }
        if (st == 2) {
            temp = x - cont - 1;
            array[0] += temp;
        }
        if (st == 2 && cambio == 1) {
            for (int i = cont; i <= x; ++i) {
                añadir(i, char1, y);
            }
            status = 0;
        }
        st = 0;
        st2 = 0;
        if ((y1 + 1 < 12) && (celdasJuego[x1][y1 + 1] == char2)) {
            cont = y1;
            while ((cont < 12) && (st2 != -1) && (st != 2)) {
                cont++;
                if (cont < 12) {
                    if (celdasJuego[x][cont] == char2) {
                        st = 1;
                    } else if (celdasJuego[x][cont] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = cont - y - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                for (int i = y; i < cont; ++i) {

                    añadir(x, char1, i);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((y1 - 1 >= 0) && (celdasJuego[x][y - 1] == char2)) {
            cont = y1;

            while ((cont >= 0) && (st2 != -1) && (st != 2)) {
                cont--;
                if (cont >= 0) {
                    if (celdasJuego[x][cont] == char2) {
                        st = 1;
                    } else if (celdasJuego[x][cont] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = y - cont - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                for (int i = cont; i <= y; ++i) {

                    añadir(x, char1, i);
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x1 - 1 >= 0) && (y1 + 1 < 12) && (celdasJuego[x1 - 1][y1 + 1] == char2)) {
            X = x1;
            Y = y1;
            while ((X >= 0) && (Y < 12) && (st2 != -1) && (st != 2)) {
                X--;
                Y++;

                if ((X >= 0) && (Y < 12)) {
                    if (celdasJuego[X][Y] == char2) {
                        st = 1;
                    } else if (celdasJuego[X][Y] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = x - X - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                while ((X <= x1) && (y1 < Y)) {
                    X++;
                    Y--;

                    if ((X <= x1) && (y1 <= Y)) {

                        añadir(X, char1, Y);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x1 - 1 >= 0) && (y1 - 1 >= 0) && (celdasJuego[x1 - 1][y1 - 1] == char2)) {

            Y = y;
            X = x;
            while ((X >= 0) && (Y >= 0) && (st2 != -1) && (st != 2)) {
                X--;
                Y--;
                if ((X >= 0) && (Y >= 0)) {
                    if (celdasJuego[X][Y] == char2) {
                        st = 1;
                    } else if (celdasJuego[X][Y] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = x - X - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {

                while ((X <= x1) && (Y <= y1)) {
                    X++;
                    Y++;
                    if ((X <= x1) && (Y <= y1)) {

                        añadir(X, char1, Y);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x1 + 1 < 12) && (y1 + 1 < 12) && (celdasJuego[x + 1][y + 1] == char2)) {

            Y = y1;
            X = x1;
            while ((X < 12) && (Y < 12) && (st2 != -1) && (st != 2)) {
                X++;
                Y++;
                if ((X < 12) && (Y < 12)) {
                    if (celdasJuego[X][Y] == char2) {
                        st = 1;
                    } else if (celdasJuego[X][Y] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = X - x - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                while ((X >= x1) && (Y >= y1)) {
                    X--;
                    Y--;

                    if ((X >= x1) && (Y >= y1)) {
                        añadir(X, char1, Y);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if ((x1 + 1 < 12) && (y1 - 1 >= 0) && (celdasJuego[x1 + 1][y1 - 1] == char2)) {

            Y = y;
            X = x;
            while ((X < 12) && (Y >= 0) && (st2 != -1) && (st != 2)) {
                X++;
                Y--;
                if ((X < 12) && (Y >= 0)) {
                    if (celdasJuego[X][Y] == char2) {
                        st = 1;
                    } else if (celdasJuego[X][Y] == char1) {
                        st = 2;
                    } else {
                        st2 = -1;
                    }
                }
            }
            if (st == 2) {
                temp = X - x - 1;
                array[0] += temp;
            }
            if (st == 2 && cambio == 1) {
                while ((X >= x1) && (Y <= y1)) {
                    X--;
                    Y++;

                    if ((X >= x1) && (Y <= y1)) {
                        añadir(X, char1, Y);
                    }
                }
                status = 0;
            }
            st = 0;
            st2 = 0;
        }
        if (status == 0) {
            return 0;
        }
        return -1;
    }

}
