import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;

// --== CS400 File Header Information ==--
// Author: CS400 Course Staff
// Email: heimerl@cs.wisc.edu / dahl@cs.wisc.edu
// Notes: This dummy class is part of the starter archive for Project One
// in spring 2021. You can extend it to work on your Project One Final
// App.
public class MovieDataReader implements MovieDataReaderInterface {
  
  /**                                                                                                                                                                                                     
   * Method that reads movie data in CSV format from the Reader provided. The dummy implementations                                                                                                      
   * will always return the same 3 sets of movies.                                                                                                                                                        
   */
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader) {
    Scanner  scan = new Scanner(inputFileReader);
    ArrayList<MovieInterface> movies = new ArrayList<MovieInterface>();
    scan.nextLine();//ignore the first line of csv file
    while(scan.hasNext()) {
    String line= scan.nextLine();
//    try {
//      while(inputFileReader.read()  !=  -1) {
//        int data = inputFileReader.read();
//       // line += Character.toString(data); 
//      }
//     // data = inputFileReader.read(char[]);
//      
//    } catch (IOException e) {
//      
//      e.printStackTrace();
//    }    
    String [] splitted = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    
    movies.add(new MovieInterface() {
         
      @Override
      public String getTitle() {
        String title = splitted[0];
        return title;
      }

      @Override
      public Integer getYear() {
        Integer year = Integer.valueOf(splitted[2]);
        return year;
      }

      @Override
      public List<String> getGenres() {
        String genre  = splitted[3];
        if(genre.charAt(0)=='\"') genre = genre.substring(1);
        if(genre.charAt(genre.length()-1)=='\"')genre = genre.substring(0, genre.length()-1);
        List genres = Arrays.asList(genre.split(","));
        System.out.println(splitted[3]);
        return genres;
      }

      @Override
      public String getDirector() {
        String director = splitted[7];
        return director;
      }

      @Override
      public String getDescription() {
        String description = splitted[11];
        return description;
      }

      @Override
      public Float getAvgVote() {
        String temp = splitted[12];
        float vote =  Float.parseFloat(temp);
        return vote;
      }

      @Override
      public int compareTo(MovieInterface otherMovie) {
        if (this.getTitle().equals(otherMovie.getTitle())) {
          return 0;
          // sort by rating
        } else if (this.getAvgVote() < otherMovie.getAvgVote()) {
          return 0;
          // sort by rating
        } else if (this.getAvgVote() < otherMovie.getAvgVote()) {
          return +1;
        } else {
          return -1;
        }
      }

    });
    // TODO: Fixme! Add two more example movies to the list before returning it (could be
    // ficticious ones).
    }
    return movies;
  }

  

}


