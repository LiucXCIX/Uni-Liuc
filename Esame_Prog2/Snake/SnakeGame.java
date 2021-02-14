package Esame_Prog2.Snake;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.*;

//OVERVIEW
/**
* La classe SnakeGame implementa un'istanza di tipo SnakeGame la quale modella il gioco dello Snake.
* Nel gioco è presente un seprente, il quale verrà controllato dal giocatore e una serie di mele che possono
* essere mangiate da quest'ultimo al fine di incrementare la sua grandezza e quindi il suo punteggio.
* Qualora il serpente andasse a toccare i bordi dell'area di gioco o il suo stesso corpo, il giocatore perde.
* Un'istanza di questa classe è mutabile.
*/
public class SnakeGame {
  //CAMPI
  /**
  * La variabile punteggio descrive il valore del punteggio del giocatore
  */
  private int punteggio;
  /**
  * La variabile namePlayer definisce il nome del giocatore
  */
  private String namePlayer = "Assopoldo9";
  /**
  * Le variabili width e height descrivono rispettivamente la grandezza e l'altezza dell'area di gioco
  */
  private final int width, height;
  /**
  * L'istanza nakedSnake di tipo Snake rappresenta il serpente controllato dal giocatore
  */
  private Snake nakedSnake;
  /**
  * La collezione apples contiene tutte le istanze di Apple presenti in uno stesso istante nell'area di gioco
  */
  private List<Apple> apples;
  /**
  * La matrice gameArea definisce i limiti dell'area di gioco e la posizione degli elementi in essa.
  * Essa è una matrice di dimensioni height * width dove ogni casella contiene un valore coordinata,
  * il quale rappresenta uno specifico punto presente in essa.
  */
  // private Coordinate[][] gameArea; // potrebbe non essere assolutamente necessaria //non lo è stata: neat!
  /**
  * La costante minDimensions definisce la dimensione minima che la matrice possa assumere
  */
  private final int minDimensions = 5;
  /**
  * La costante stdDimensionHeight definisce la dimensione standard dell'altezza che la matrice assumerà
  */
  private final int stdDimensionHeight = 25;
  /**
  * La costante stdDimensionWidth definisce la dimensione standard della larghezza che la matrice assumerà
  */
  private final int stdDimensionWidth = 50;

  private String str = "";
  /*TimerTask task = new TimerTask(){ //FUNZIONE DISABILITATA PERCHE" NON LA CAPISCO anCORA BENE AAAAA
        public void run() {
            if( str.equals("") ) {
                System.exit( 0 );
            }
        }    
    };*/

  /**
   *  ABS FUN: AF(width, height, nakedSnake, apples) -> rappresenterà l'area di gioco di dimensione height * width nella quale sono presenti il serpente
   *               controllato dal giocatore e le n mele sparpagliate nell'area di gioco in base alla loro posizione
   *  REP INV: I valori di height e width devono essere >= stdDimension
   *           La singola istanza di nakedSnake e le istanze di tipo Apple contenute in apples, devono essere "allocate"
   *               su Coordinate coerenti con la dimensione di gameArea (non potranno essere all'esterno di essa)
   *  ABS INV: La dimensione dell'area di gioco deve essere height * width
   *           Il serpente e le mele non potranno essere all'esterno dell'area di gioco, e se il primo ne dovesse toccare i bordi
   *               sarebbe game over!
   */

  //COSTRUTTORI
  /**
  * Post-condizioni: inizializza una specifica istanza di this, usando una dimensione standard per la matrice
  */
  public SnakeGame(){
    // inizializza matrice di coordinate con dimensione stdDimension * stdDimension
    this.height = stdDimensionHeight;
    this.width = stdDimensionWidth;
    this.nakedSnake = new Snake();
    apples = new ArrayList<>();
    assert repOk();
  }

  /**
  * Post-condizioni: inizializza una specifica istanza di this, rendendo la dimensione dell'area di gioco di height * width caselle
  *                  questo solamente se heigth >= minDimensions && width >= minDimensions, altrimenti solleva un'eccezione di tipo
  *                  IllegalArgumentException
  */
  public SnakeGame(int width, int heigth){
    if ((width < minDimensions) || (heigth < minDimensions)) throw new IllegalArgumentException();
    this.height = heigth;
    this.width = width;
    this.nakedSnake = new Snake(width/4, heigth/2);
    apples = new ArrayList<>();
    assert repOk();
  }

  //METODI DI ISTANZA
  /**
  * Effetti collaterali: potrebbe modificare this e System.out in base all'input del giocatore
  * Post-condizioni: inizia il gioco e stampa a terminale l'area di gioco e tutte le istanze di mele e serpente presenti in essa.
  *                  Inoltre il giocatore dovrà poter manipolare da terminale la posizione del serpente, in modo tale da poterlo muovere
  *                  all'interno dei confini dell'area di gioco.
  */
  public void play(InputStream in, PrintStream out) {
    boolean gameOver = false;
    int turns = 0;
    // stampa introduzione del gioco
    printIntroduction(out, in);
    while(!gameOver){
      if (turns + punteggio - 2 % 10 == 0) { // sono andato un po' a braccio con questa espressione, non sono convinto sia molto sensata
        Apple mela = Apple.aNewAppleFalls(height, width);
        apples.add(mela);
      }
      // stampa l'area di gioco e serpente + mele
      printGameScreen(out);
      // scan della string contenente l'input del giocatore (dovrà essere temporizzata, se ci mette troppo snake default move in base alla sua direzione)
      int direction;
      try {
        direction = playerComand(in);
      } catch (Exception e) {
        System.out.println("AAA successa gazada eccezione scansione input");
        direction = 4;
      }
      // muove il serpente in accordo con l'input scansito
      try {
        nakedSnake.move(direction);
      } catch (InvalidMoveException e) {
        System.out.println("AAA successa gazada mossa invaldida");
        nakedSnake.move();
      }
      // se la posizione del serpente fosse quella dove è presente una mela, esso la mangierà, incrementando la sua dimensione o nel caso in cui avesse toccato l'estremità della mappa o il corpo del serpente -> GameOver
      gameOver = checkConditions();
      turns++;
      punteggio++;
      assert repOk();
    }
    printGameScreen(out);
    System.out.println("Bella giocata! Hai ottenuto un punteggio di: " + punteggio + "\nCongratulazioni " + namePlayer +"!\n");
  }

  /**
  * Effetti collaterali: modifica System.out
  * Post-condizoni: stampa a terminale l'introduzione al gioco
  */
  private void printIntroduction(PrintStream out, InputStream in) {
    Scanner s = new Scanner(in);
    System.out.println("Benvenuto, dai, iniziamo a giocare a Snake: io ho portato le cartine, adesso le rolliamo e poi ce le fumiamo tutte!");
    System.out.println("Per muoverti in alto premi 'w', per andare a sinistra 'a', per andare a destra 'd' e inifine, non ci crederai mai, 's' per andare indietro!");
    System.out.println("Incredibile: EVVIVAAAAH! Dai cazzo, sei un epic gamer: queste cose le conoscerai, no?");
    System.out.println("Inserisci il tuo nome o il numero della tua carta di credito + scadenza per continuare");
    namePlayer = s.nextLine();
    s.close();
  }

  /**
  * Effetti collaterali: modifica System.out
  * Post-condizioni: stampa un singolo elemento del gioco a terminale, se presente o uno spazio vuoto
  */
  private void printElement(int x, int y, Set<Coordinate> pointsToPrint){
    Coordinate point = Coordinate.coord(x, y);
    if (pointsToPrint.contains(point)) {
      System.out.print("*"); // per ora tutti gli elementi saranno stampati come un *, però ciò potrebbe cambiare
    } else {
      System.out.print(" ");
    }
  }

  /**
  * Post-condizioni: restituisce una collezione contenente i punti da stampare
  */
  private Set<Coordinate> whatToPrint() {
    Set<Coordinate> pointsToPrint = new HashSet<>(); // potremmo usare una mappa da Set<Coord> -> int per capire cosa effettivamente si debba stampare e.g. 1 = testa serpente, 2 = corpo, 3 = mela
    for (Coordinate punto : nakedSnake) {
      pointsToPrint.add(punto);
    }
    pointsToPrint.add(nakedSnake.head());
    for (Apple mela : apples) {
      pointsToPrint.add(mela.whereDidItFall());
    }
    return pointsToPrint;
  }

  /**
  * Effetti collaterali: modifica System.out
  * Post-condizioni: stampa l'area di gioco a terminale
  */
  private void printGameScreen(PrintStream out) {
    Set<Coordinate> pointsToPrint = whatToPrint();
    for (int i = 0; i < 10; i++) {
      System.out.println();
    }
    for (int i = 0; i < height + 2 ; i++){
      for (int j = 0; j < width + 2 ; j++){
        if (((i == 0) || (i == height + 1)) && ((j == 0) || (j == width + 1))) {
          if ((i == 0) && (j == 0)) {
            System.out.print("\u231C");
          }
          if ((i == 0) && (j == width + 1)) {
            System.out.print("\u231D");
          }
          if ((i == height + 1) && (j == 0)) {
            System.out.print("\u231E");
          }
          if ((i == height + 1) && (j == width + 1)) {
            System.out.print("\u231F");
          }
        } else if ((i == 0) || (i == height + 1) || (j == 0) || (j == width + 1)) {
          if (i == 0) {
            System.out.print("\u203E");
          }
          if ((j == 0) || (j == width + 1)) System.out.print("|");
          if (i == height + 1) System.out.print("_");
        } else {
          printElement(i - 1, j - 1, pointsToPrint);
        }
      }
      System.out.println();
    }
  }
  /**
  * Post-condizoni: scansisce l'input del giocatore relativo alla direzione verso la quale il seprente deve andare,
  *                 se ci sono errori nell'input o l'utente è stato troppo lento a inserirlo, allora solleverà un'eccezione di tipo Exception
  * @return 0 = UP, 1 = RIGTH, 2 = DOWN e 3 = LEFT o
  */
  private int playerComand(InputStream in) throws Exception {
    int out;
    Scanner s = new Scanner(in);
    //Timer timer = new Timer();
    //timer.schedule( task, 3*1000 );
    str = s.nextLine();
    //timer.cancel(); //da ripensare
    char c = str.charAt(0);
    switch (c) {
      case 'w': out = 0;
                break;
      case 'a': out = 3;
                break;
      case 's': out = 2;
                break;
      case 'd': out = 1;
                break;
      default:  s.close();
                throw new Exception();
    }
    s.close();
    return out;
  }
  /**
  * Effetti collaterali: se la testa del serpente fosse nella stessa posizione di una mela, allora potrebbe modificare this
  * Post-condizioni: controlla se le condizioni di game over sono state rese valide e restituisce corrispettivamente true se sono
  *                  vere o false se non sono ancora state validate, inoltre incrementa la dimensione del serpente se esso avrà mangiato una mela, incrementando di cobseguenza
  *                  il punteggio di gioco
  */
  private boolean checkConditions() {
    boolean gameOver = false;
    Coordinate headSnake = nakedSnake.head();
    Map<Coordinate, Apple> melePos = new HashMap<>();
    for (Apple mela : apples){
      melePos.put(mela.whereDidItFall(), mela);
    }
    if (melePos.containsKey(headSnake)) {
      nakedSnake.increaseSize(); //da cancellare mela in quella posizione
      punteggio += 5;
      Apple melaMangiata = melePos.get(headSnake);
      while (!apples.contains(melaMangiata)) apples.remove(melaMangiata);
    }
    Set<Coordinate> bodySnake = new HashSet<>();
    for (Coordinate p : nakedSnake){
      bodySnake.add(p);
    }
    //controlla che non tocchi bordo o coda
    if (bodySnake.contains(headSnake)) gameOver = true;
    if ((headSnake.xCoord() == 0) || (headSnake.yCoord() == 0) || (headSnake.xCoord() == width) || (headSnake.yCoord() == height)) gameOver = true;
    return gameOver;
  }

  /**
   *  ABS FUN: AF(width, height, nakedSnake, apples) -> rappresenterà l'area di gioco di dimensione height * width nella quale sono presenti il serpente
   *               controllato dal giocatore e le n mele sparpagliate nell'area di gioco in base alla loro posizione
   *  REP INV: I valori di height e width devono essere >= stdDimension
   *           La singola istanza di nakedSnake e le istanze di tipo Apple contenute in apples, devono essere "allocate"
   *               su Coordinate coerenti con la dimensione di gameArea (non potranno essere all'esterno di essa)
   *  ABS INV: La dimensione dell'area di gioco deve essere height * width
   *           Il serpente e le mele non potranno essere all'esterno dell'area di gioco, e se il primo ne dovesse toccare i bordi
   *               sarebbe game over!
   */

  /**
  * Post-condizioni = restituisce true se l'invariante di rappresentazione è rispettato, false altrimenti
  */
  private boolean repOk(){
    if ((height < stdDimensionHeight) || (width < stdDimensionWidth)) return false;
    Coordinate headSnake = nakedSnake.head();
    if ((headSnake.xCoord() < 0) || (headSnake.xCoord() > width) || (headSnake.yCoord() < 0) || (headSnake.yCoord() > height)) return false;
    for (Apple a : apples) {
      Coordinate fallenApple = a.whereDidItFall();
      if ((fallenApple.xCoord() < 0) || (fallenApple.xCoord() > width) || (fallenApple.yCoord() < 0) || (fallenApple.yCoord() > height)) return false;
    }
    return true;
  }

  /**
  * Effetti collaterali: modifica StringBuilder
  * Post-condizioni: inserisce un elemento in StringBuilder
  */
  private void StringBuilderAppendElement(int x, int y, Set<Coordinate> pointsToPrint, StringBuilder s){
    Coordinate point = Coordinate.coord(x, y);
    if (pointsToPrint.contains(point)) {
      s.append("*"); // per ora tutti gli elementi saranno stampati come un *, però ciò potrebbe cambiare
    } else {
      s.append(" ");
    }
  }

  @Override
  public String toString(){
    StringBuilder s = new StringBuilder();
    s.append("SnakeGame di " + namePlayer + "con punteggio corrente " + punteggio + ":\n");
    Set<Coordinate> pointsToPrint = whatToPrint();
    for (int i = 0; i < height + 2 ; i++){
      for (int j = 0; j < width + 2 ; j++){
        if (((i == 0) || (i == height + 1)) && ((j == 0) || (j == width + 1))) {
          if ((i == 0) && (j == 0)) {
            s.append("\u231C");
          }
          if ((i == 0) && (j == width + 1)) {
            s.append("\u231D");
          }
          if ((i == height + 1) && (j == 0)) {
            s.append("\u231E");
          }
          if ((i == height + 1) && (j == width + 1)) {
            s.append("\u231F");
          }
        } else if ((i == 0) || (i == height + 1) || (j == 0) || (j == width + 1)) {
          if (i == 0) {
            s.append("\u203E");
          }
          if ((j == 0) || (j == width + 1)) s.append("|");
          if (i == height + 1) s.append("_");
        } else {
          StringBuilderAppendElement(i - 1, j - 1, pointsToPrint, s);
        }
      }
      s.append("\n");
    }
    return s.toString();
  }
}
