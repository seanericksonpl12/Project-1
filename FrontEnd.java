// --== CS400 File Header Information ==--
// Name: Erhan Wang
// Email: ewang43@wisc.edu
// Team: blue
// Role: Front end developer
// TA: hang
// Lecturer: <name of the team mate's lecturer>
// Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FrontEnd {
  public FrontEnd() {}
  static Backend B;
  public static void main(String[] args) {
    System.out.println("Please type in the path and press enter");
    Scanner sc = new Scanner (System.in);
    String n = sc.next();
    String[] path = new String[1];
    path[0] = n;
    outer: for(int i=1;i>0;i++) {
      try {
        B = new Backend(path);
        break outer;
      }catch(FileNotFoundException e) {
        System.out.println("File not found, please type in the correct path.");
        sc = new Scanner (System.in);
        n = sc.next();
        path = new String[1];
        path[0] = n;
      }
    }
    base();
  }
  
  public static void base() {
    System.out.println("Welcome to the Base Mode.");
    System.out.println("Here are the top three movies in average rating.");
    System.out.println(B.getThreeMovies(0)); 
    for(int i=1;i>0;i++) {
      System.out.println("You can type from 0 to " + B.getNumberOfMovies() + " or \"g\" to enter genre mode or \"r\" to enter rating mode or \"x\" to exit the programe");
      Scanner sc = new Scanner (System.in);
      String n = sc.next();
      if(n.equals("g")) {
        genre();
      }
      if(n.equals("r")) {
        rating();
      }
      if(n.equals("x")) {
        System.exit(0);
      }
      if(Integer.parseInt(n)>0 && Integer.parseInt(n)<B.getNumberOfMovies()-3) {
        System.out.println(B.getThreeMovies(Integer.parseInt(n)));
      }
      else {
        System.out.println("index not in range");
      }
    }
  }
  
  public static void genre() {
    for(int i=1;i>0;i++) {
    
      System.out.println("Here are all the genre listed");
      System.out.println(B.getAllGenres());
      for(i=0;i<B.getAllGenres().size();i++) {
        System.out.print(i+" ");
      }
      System.out.println("Type in \"select\" or \"deselect\" to change the wanted genre");
      System.out.println("Type in \"x\" to exit select, deselect mode or genre mode");
      System.out.println("or type in \"current genre\" to see sellected genre");
      System.out.println("type in \"finish\" to see sellected movie");
      Scanner sc = new Scanner (System.in);
      String n = sc.next();
      //exit to base mode if type in "x"
      if(n.equals("x")) {
        base();
      }
      //select mode
      outer1: if(n.equals("select")) {
        for(i=1;i>0;i++) {
          System.out.println("Type in the number you want to select and press enter");
          Scanner ge = new Scanner (System.in);
          String g = ge.next();
          if(g.equals("x")) {
            break outer1;
          }
          int p = Integer.parseInt(g);
          B.addGenre(B.getAllGenres().get(p));
        }
      }
      //deselect mode
      outer2: if(n.equals("deselect")) {
        for(i=1;i>0;i++) {
          System.out.println("Type in the number you want to deselect and press enter");
          Scanner ge = new Scanner (System.in);
          String g = ge.next();
          if(g.equals("x")) {
            break outer2;
          }
          int p = Integer.parseInt(g);
          B.removeGenre(B.getAllGenres().get(p));
        }
      }
      //see current selected genre
      if(n.equals("current genre")) {
        System.out.println(B.getGenres());
      
      }
      //finish selection and show movies
      List<MovieInterface> newList = B.getMovieList();
      if(n.equals("finish")) {
        for(int k=B.getMovieList().size()-1;k>=0;k--) {
          outer3:for(i=0;i<B.getGenres().size();i++) {
            if(!B.getMovieList().get(k).getGenres().contains(B.getGenres().get(i))) {
              newList.remove(newList.get(k));
              break outer3;
            }
          }
        }
        for(int j=0;j<newList.size();j++) {
          System.out.print(newList.get(j).getTitle());
        }
        Scanner cc = new Scanner (System.in);
        String c = cc.next();
        if(n.equals("x")) {
          base();
        }
      }
    }
  }
  
  public static void rating() {
    for(int j=1;j>0;j++) {
      for(int i=0;i<=10;i++) {
        if(!B.getAvgRatings().contains(i+"")) {
          B.addAvgRating(i+"");
        }
      }
      System.out.println("Here are all the rating listed");
      System.out.println(B.getAvgRatings());
      System.out.println("Type in \"select\" or \"deselect\" to change the wanted rating");
      System.out.println("Type in \"x\" to exit select, deselect mode or rating mode");
      System.out.println("or type in \"current rating\" to see sellected rating");
      System.out.println("type in \"finish\" to see sellected movie");
      Scanner sc = new Scanner (System.in);
      String n = sc.next();
      //exit to base mode if type in "x"
      if(n.equals("x")) {
        base();
      }
      //select mode
      outer1: if(n.equals("select")) {
        for(int i=1;i>0;i++) {
          System.out.println("Type in the number you want to select and press enter");
          Scanner ge = new Scanner (System.in);
          String g = ge.next();
          if(g.equals("x")) {
            break outer1;
          }
          B.addAvgRating(g);
        }
      }   
      //deselect mode
      outer2: if(n.equals("deselect")) {
        for(int i=1;i>0;i++) {
          System.out.println("Type in the number you want to deselect and press enter");
          Scanner ge = new Scanner (System.in);
          String g = ge.next();
          if(g.equals("x")) {
            break outer2;
          }
          B.removeAvgRating(g);
        }
      }
      //see current selected genre
      if(n.equals("current rating")) {
        System.out.println(B.getAvgRatings());
      }
      //finish selection and show movies
      ArrayList<MovieInterface> newList = new ArrayList<MovieInterface>();
      if(n.equals("finish")) {
        for(int k=0;k<B.getMovieList().size();k++) {
          outer3:for(int i=0;i<B.getAvgRatings().size();i++) {
            if(Integer.parseInt(B.getAvgRatings().get(i)) < B.getMovieList().get(k).getAvgVote() && Integer.parseInt(B.getAvgRatings().get(i))+1 > B.getMovieList().get(k).getAvgVote() ) {
              newList.add(B.getMovieList().get(k));
              break outer3;
            }
          }   
        }
        for(j=0;j<newList.size();j++) {
          System.out.print(newList.get(j).getTitle());
        }
        Scanner cc = new Scanner (System.in);
        String c = cc.next();
        if(n.equals("x")) {
          base();
        }
      }
    }
  }
  
  
  
}
