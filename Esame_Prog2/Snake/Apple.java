package Esame_Prog2.Snake;


//OVERVIEW
/**
* La classe Apple implementa un'entità che rappresenta una delle mele dello SnakeGame, questa sarà un'entità immutabile.
* [Potrebbe essere implementato anche un campo relativo al punteggio guadagnato mangiando questa specifica istanza di mela]
*/
public final class Apple {
  //CAMPI
  /**
  * L'entità point di tipo Coordinate definisce il punto dove sarà presente questa specifica istanza di Apple
  */
  private final Coordinate point;
  //private final int punteggio;

  /**
  * Post-condizioni: inizializza una specifica istanza di Apple
  */
  private Apple(int x, int y){
    point = Coordinate.coord(x, y);
  }

  /**
  * Post-condizioni: restituisce un intero tra min e max
  */
  private static int getRandomNumber(int min, int max) {
    return (int) ((Math.random() * (max - min)) + min);
  }

  /**
  * Post-condizioni: inizializza e restituisce una nuova istanza di Apple, la quale sarà posizionata in una coord(i, j)
  *                  con i e j compresi rispettivamente tra 1 e width -1 e tra 1 e heigth -1
  */
  public static Apple aNewAppleFalls (int heigth, int width) {
    int x = getRandomNumber(1, width - 1);
    int y = getRandomNumber(1, heigth - 1);
    return new Apple(x, y);
  }

  /**
  * Post-condizioni: restituisce le coordinate dove la mela è caduta
  */
  public Coordinate whereDidItFall(){
    return point;
  }

  @Override
  public int hashCode(){
    return 31 * Integer.hashCode(this.point.xCoord()) + Integer.hashCode(this.point.yCoord());
  }

  @Override
  public boolean equals(Object obj){
    if (obj == this) return true;
    if (!(obj instanceof Apple)) return false;
    Apple o = (Apple) obj;
    return (this.point.equals(o.point));
  }
}