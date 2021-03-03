//--== CS400 File Header Information ==--
//Name: Shupeng Tang
//Email: stang72@wisc.edu
//Team: HG Blue
//Role: Data Wrangler
//TA: Hang
//Lecturer: Gary Dahl
//Notes to Grader: <optional extra notes>
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.zip.DataFormatException;
/**
 * This class implements MovieDataReader Interface and Movie Interface, and enables
 * the backend developer to get a list of movie objects contained in the csv file
 * by passing in a reader object as input;
 * @author shupengtang
 */
public class MovieDataReader implements MovieDataReaderInterface {
  
  /**                                                                                                                                                                                                     
   * Method that reads movie data in CSV format from the Reader provided， and 
   * returns a list of movie objects;                                                                                                                                     
   */
  @Override
  public List<MovieInterface> readDataSet(Reader inputFileReader) {
    //creates a new scanner to read in data in reader
    Scanner  scan = new Scanner(inputFileReader);
    //creates a Array list of movie objects
    ArrayList<MovieInterface> movies = new ArrayList<MovieInterface>();
    scan.nextLine();//ignore the first line of csv file
    //scan in all the data and add per line as an object to movie list
    while(scan.hasNext()) {
    String line= scan.nextLine();
    //split the strings on each line into a String array, with index 0 =
    //the title of the movie, etc;
    String [] splitted = line.split(",(?=([^\"]*\"[^\"]*\")*[^\"]*$)");
    //implement the movie interface for each movie object;
    movies.add(new MovieInterface() {
     /**
      * This method gets the title of the movie object and returns it as a String 
      * @return title of the movie   
      */
      @Override
      public String getTitle() {
        String title = splitted[0];
        return title;
      }
      /**
       * This method gets the year of the movie object and returns it as a Integer
       * @return year of the movie
       */
      @Override
      public Integer getYear() {
        Integer year = Integer.valueOf(splitted[2]);
        return year;
      }
      /**
       * This method gets the genres of the movie, and returns them as a 
       * list of String objects;
       * @return list of Strings that contains genres of the movie
       */
      @Override
      public List<String> getGenres() {
        String genre  = splitted[3];
        //delete the "" mark that's included in some genres
        if(genre.charAt(0)=='\"') genre = genre.substring(1);
        if(genre.charAt(genre.length()-1)=='\"')genre = genre.substring(0, genre.length()-1);
        List genres = Arrays.asList(genre.split(","));
        return genres;
      }
      /**
       * This method gets the director of the movie, and returns it as a String
       * @return the director of the movie
       */
      @Override
      public String getDirector() {
        String director = splitted[7];
        return director;
      }
      /**
       * This method gets the description of the movie, and returns it as a String
       * @return the description of the movie
       */
      @Override
      public String getDescription() {
        String description = splitted[11];
        return description;
      }
      /**
       * This method gets the rating of the movie, and returns it as a Float
       * @return the description of the movie
       */
      @Override
      public Float getAvgVote() {
        String temp = splitted[12];
        //convert from String to Float
        float vote =  Float.parseFloat(temp);
        return vote;
      }
      
     /**
       * This method compares two movies and put them in order;
       * @return int number that indicates the order of movies
       */
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
 
    }
    //return the list of movies
    return movies;
  }

  

}


