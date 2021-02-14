package Esame_Prog2.Snake;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

//OVERVIEW
/**
* La classe Snake implementa un'entità che rappresenta il serpente dello SnakeGame, questa sarà un'entità mutabile che si
* potrà muovere nei limiti delle coordinate dell'area di gioco. Il serpente dovrà inoltre avere una coda, la quale diventerà sempre più grande
* quante più esso mele ingurgiterà.
*/
public class Snake implements Iterable<Coordinate> {
  //CAMPI
  /**
  * La collezione di coordinate body rappresenta la posizione del corpo del serpente nell'area di gioco,
  * ogni qualvolta esso mangerà una mela, diverrà più grande.
  * Nel corpo non sarà presente la posizione della testa del serpente.
  */
  private List<Coordinate> body;
  /**
  * Una variabile di tipo Directions rappresenterà le direzioni verso le quali il serpente potrà dirigersi.
  * 0 = UP, 1 = RIGTH, 2 = DOWN e 3 = LEFT
  */
  private enum Directions {RIGTH, DOWN, UP, LEFT};
  /**
  * La variabile direction rappresenterà la direzione verso la quale il serpente si sta dirigendo.
  * 0 = UP, 1 = RIGTH, 2 = DOWN e 3 = LEFT
  */
  private Directions direction;
  /**
  * L'istanza head definisce la posizione corrente della testa del serpente
  */
  private Coordinate head;
  /**
  * La variabile size definisce la grandezza del serpente [potrebbe non essere necessaria]
  */
  private int size;

  /**
  * Post-condizioni: inizializza una nuova istanza di Snake in una posizione di default
  */
  public Snake(){
    this.head = Coordinate.coord(3, 5);
    this.body = new ArrayList<>();
    body.add(Coordinate.coord(3, 6));
    body.add(Coordinate.coord(3, 7));
    this.direction = Directions.DOWN;
    this.size = 3;
  }

  /**
  * Post-condizioni: inizializza una nuova istanza di Snake in una posizione specifica in base alla grandezza dell'area di gioco,
  *                  questo se x e y sono maggiori di 0, alternativamente solleva un'eccezione di tipo IllegalArgumentException.
  */
  public Snake(int x, int y){
    this.head = Coordinate.coord(x, y);
    this.body = new ArrayList<>();
    body.add(Coordinate.coord(x, y + 1));
    body.add(Coordinate.coord(x, y + 2));
    this.direction = Directions.DOWN;
    this.size = 3;
  }

  /**
  * Effetti collaterali: potrebbe modificare this se direction è una direzione valida verso la quale orientare il seprente
  * Post-condizoni: se la direzione verso la quale il giocatore ha deciso di orientare il serpente è valida allora muove il serpente di una
  *                 posizione verso quella direzione, altrimenti solleverà al chiamante (reflects) un'eccezione contorollata di tipo InvalidMoveException
  * @param direction deve essere un valore compreso tra 0 e 3 {0 = UP, 1 = RIGTH, 2 = DOWN e 3 = LEFT}
  */
  public void move(int direction) throws InvalidMoveException {
    if ((direction < 0) || (direction > 3)) throw new InvalidMoveException();
    Coordinate newHead;
    switch (direction) {
      case 0: if (this.direction == Directions.DOWN) throw new InvalidMoveException(); // la direzione non è corretta perché il serpente non può voltarsi di 180°
              body.add(0, head); // inserisce la testa come primo elemento del corpo e shifta tutti gli altri
              body.remove(size - 1); // rimuove l'ultimo elemento dalla lista dove sono contenute le coordinate del corpo
              newHead = Coordinate.coord(head.xCoord() - 1, head.yCoord());
              break;
      case 1: if (this.direction == Directions.LEFT) throw new InvalidMoveException();
              body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord(), head.yCoord() + 1);
              break;
      case 2: if (this.direction == Directions.UP) throw new InvalidMoveException();
              body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord() + 1, head.yCoord());
              break;
      case 3: if (this.direction == Directions.RIGTH) throw new InvalidMoveException();
              body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord(), head.yCoord() - 1);
              break;
      default: newHead = head;
    }
    this.head = newHead;
  }

  /**
  * Effetti collaterali: modifica this
  * Post-condizoni: la posizione del serpente viene modificata in base alla direzione che esso attualmente ha
  */
  public void move(){
    Coordinate newHead;
    switch(this.direction){
      case UP: body.add(0, head); // inserisce la testa come primo elemento del corpo e shifta tutti gli altri
              body.remove(size - 1); // rimuove l'ultimo elemento dalla lista dove sono contenute le coordinate del corpo
              newHead = Coordinate.coord(head.xCoord() - 1, head.yCoord());
              break;
      case RIGTH: body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord(), head.yCoord() + 1);
              break;
      case DOWN: body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord() + 1, head.yCoord());
              break;
      case LEFT: body.add(0, head);
              body.remove(size - 1);
              newHead = Coordinate.coord(head.xCoord(), head.yCoord() - 1);
              break;
       default: 
              newHead = head;
    }
    this.head = newHead;
  }

  /**
  * Effetti collaterali: modifica this
  * Post-condizioni: incrementa la grandezza del serpente e inserisce un elemento aggiuntivo nel suo corpo
  */
  public void increaseSize() {
    body.add(0, head); // dovrebbe poter funzionare, inserisci un elemento in più nella testa e incrementi di uno la grandezza,
    size++;           //  per n (= size) mosse il serpente non cresce fino all'ultima iterazione quando il cambiamento sarà chiaramente visibile
  }

  /**
  * Post-condizioni: restituisce una copia della coordinata dove è presente la testa del serpente
  */
  public Coordinate head() {
    return Coordinate.coord(head.xCoord(), head.yCoord());
  }

  /**
  * Post-condizioni: un iteratore (Liskov) che restituisce un generatore (Liskov) che restituirà singolarmente ogni coordinata presente nel corpo del serpente
  */
  public Iterator<Coordinate> iterator(){
    return new Iterator<Coordinate>(){
      private int i = 0;

      public boolean hasNext(){
        return (i < body.size());
      }

      public Coordinate next(){
        if (!hasNext()) throw new NoSuchElementException();
        return body.get(i++);
      }
    };
  }
}