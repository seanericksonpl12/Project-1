// --== CS400 File Header Information ==--
// Author: Sean Erickson
// Email: smerickson4@cs.wisc.edu
// Notes: This is the backend implementation of the BackendInterface

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;
import java.io.Reader;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;


public class Backend implements BackendInterface {

	private HashTableMap<String, List<MovieInterface>> genreHash;
	private HashTableMap<String, List<MovieInterface>> ratingHash;
	private List<MovieInterface> movieList;
	private List<MovieInterface> sortedMovies;
	private List<String> genres;
	private List<String> ratings;

	/**
	 * This constructor instantiates a backend with the command line arguments
	 * passed to the app when started by the user. The arguments are expected to
	 * contain the path to the data file that the backend will read in.
	 * 
	 * @param args list of command line arguments passed to front end
	 */
	public Backend(String[] args) throws DataFormatException, FileNotFoundException, IOException {
		String arg = args[0];

		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		sortedMovies = new ArrayList<MovieInterface>();

		MovieDataReader movieReader = new MovieDataReader();
		FileReader file = new FileReader(arg);

		movieList = movieReader.readDataSet(file);
		genreHash = new HashTableMap<String, List<MovieInterface>>(movieList.size());
		ratingHash = new HashTableMap<String, List<MovieInterface>>(movieList.size());
		hash();
	}

	/**
	 * A constructor that will create a backend from data that can be read in with a
	 * reader object.
	 * 
	 * @param r A reader that contains the data in CSV format.
	 */
	public Backend(Reader r) throws IOException, DataFormatException {

		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		sortedMovies = new ArrayList<MovieInterface>();
		MovieDataReader movieReader = new MovieDataReader();
		movieList = movieReader.readDataSet(r);
		// System.out.println(movieList.get(2).getGenres());
		genreHash = new HashTableMap<String, List<MovieInterface>>(movieList.size() * 2);
		ratingHash = new HashTableMap<String, List<MovieInterface>>(movieList.size() * 2);
		hash();

	}

	public List<MovieInterface> getMovieList() {
		return movieList;
	}

	/**
	 * Private helper that puts all movies into a hash table
	 */
	private void hash() {
		// Puts all movies into a hashtable from the movieList based off genre
		// Loop through all movies
		for (int i = 0; i < movieList.size(); i++) {
			// make temporary movie object for each i
			MovieInterface movie = movieList.get(i);
			// Loop through genres for each movie
			for (int j = 0; j < movie.getGenres().size(); j++) {
				// if the table does not contain the key, then make new list and add list to
				// table
				if (!genreHash.containsKey(movie.getGenres().get(j))) {
					List<MovieInterface> newList = new ArrayList<MovieInterface>();
					newList.add(movie);
					genreHash.put(movie.getGenres().get(j), newList);
				}
				// otherwise if the table does contain the key, add to list and increase size
				else if (genreHash.containsKey(movie.getGenres().get(j))) {
					genreHash.get(movie.getGenres().get(j)).add(movie);
					genreHash.increaseSize();
				}
			}

		}

		// put all movies into hashtable from movielist based off rating

		// Loop through genres for each movie
		for (int i = 0; i < movieList.size(); i++) {
			// Make new temporary movie object and store rating as rounded down double
			MovieInterface movie = movieList.get(i);
			double movieRating = Math.floor(movie.getAvgVote());
			int temp = (int) movieRating;
			// Change rating to rounded down string to store in table
			String rating = String.valueOf(temp);
			// if the table does not contain the key, then make new list and add list to
			// table
			if (!ratingHash.containsKey(rating)) {
				List<MovieInterface> newList = new ArrayList<MovieInterface>();
				newList.add(movie);
				ratingHash.put(rating, newList);
			}
			// otherwise if the table does contain the key, add to list and increase size
			else if (ratingHash.containsKey(rating)) {
				ratingHash.get(rating).add(movie);
				ratingHash.increaseSize();
			}
		}

	}

	/*
	 * Private helper that sorts all values that fit criteria in a list
	 */
	private void sort() {
		// Initialize two booleans

		boolean swtch1;
		boolean swtch2;
		// Checks hash tables against lists to count all movies that fit criteria
		for (int i = 0; i < movieList.size(); i++) {
			swtch1 = true;
			swtch2 = true;
			MovieInterface movie = movieList.get(i);
			// System.out.println(movie.getGenres());
			if (!genres.isEmpty()) {
				for (int j = 0; j < genres.size(); j++) {
					if (!movie.getGenres().contains(genres.get(j))) {
						if (sortedMovies.contains(movie))
							sortedMovies.remove(movie);
						swtch1 = false;
						break;
					}
				}
			} else if (genres.isEmpty() && ratings.isEmpty())
				swtch1 = false;
			else
				swtch1 = true;

			if (!ratings.isEmpty()) {
				// Check that the rating is within search criteria
				double movieRating = Math.floor(movie.getAvgVote());
				int temp = (int) movieRating;
				// Change rating to rounded down string to store in table
				String rating = String.valueOf(temp);
				if (!ratings.contains(rating)) {
					swtch2 = false;
					if (sortedMovies.contains(movie))
						sortedMovies.remove(movie);
				}
			} else
				swtch2 = true;
			// If rating and genres match, add to list

			if (swtch1 && swtch2) {
				sortedMovies.add(movie);
			}
		}

		Set<MovieInterface> set = new HashSet<>(sortedMovies);
		sortedMovies.clear();
		sortedMovies.addAll(set);
	}

	/**
	 * Method to add a genre that the user selected. It will output but not store
	 * the genres passed to it.
	 */
	@Override
	public void addGenre(String genre) {
		genres.add(genre);
		sort();
	}

	/**
	 * Method to add a genre that the user selected. It will output but not store
	 * the ratings passed to it.
	 */
	@Override
	public void addAvgRating(String rating) {
		// Make sure rating is a string between 0-10
		if (rating.equals("0") || rating.equals("1") || rating.equals("2") || rating.equals("3") || rating.equals("4")
				|| rating.equals("5") || rating.equals("6") || rating.equals("7") || rating.equals("8")
				|| rating.equals("9") || rating.equals("10")) {
			ratings.add(rating);
			sort();
		} else
			System.out.println("invalid rating");
	}

	/**
	 * Method to remove a genre that the user selected. It will output but not
	 * remove the genre passed to it from the backend object.
	 */
	@Override
	public void removeGenre(String genre) {
		genres.remove(genre);
		sort();
	}

	/**
	 * Method to remove a rating that the user selected. It will output but not
	 * remove the genre passed to it from the backend object.
	 */
	@Override
	public void removeAvgRating(String rating) {
		// TODO: Fixme! Add dummy implementation similar to addGenre method.
		ratings.remove(rating);
		sort();
	}

	/**
	 * Return the genres added to this backend object.
	 */
	@Override
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * Return the ratings added to this backend object.
	 */
	@Override
	public List<String> getAvgRatings() {

		return ratings;
	}

	/**
	 * Returns the number of movies.
	 */
	@Override
	public int getNumberOfMovies() {
		return sortedMovies.size();
	}

	/**
	 * Returns all genres in the dataset.
	 */
	@Override
	public List<String> getAllGenres() {
		List<String> rtn = new ArrayList<String>();
		// loop through all movies
		for (int i = 0; i < movieList.size(); i++) {
			// loop through all genres of the movie at i
			for (int j = 0; j < movieList.get(i).getGenres().size(); j++) {
				// if list doesn't contain the genre already, remove it
				if (!rtn.contains(movieList.get(i).getGenres().get(j))) {
					rtn.add(movieList.get(i).getGenres().get(j));
				}
			}
		}

		return rtn;
	}

	/**
	 * Returns the movies that match the ratings and genres.
	 */
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		ArrayList<MovieInterface> movies = new ArrayList<MovieInterface>();
		if (sortedMovies.isEmpty())
			return movies;
		try {
			String str = String.valueOf(startingIndex);
			while (!ratingHash.containsKey(str) && startingIndex != 0) {
				startingIndex--;
				str = String.valueOf(startingIndex);
			}
			for (int j = startingIndex; j > startingIndex - 3; j--) {

				List<MovieInterface> temp = ratingHash.get(str);
				if (temp.isEmpty())
					j--;
				else if (temp.size() == 1)
					movies.add(temp.get(0));
				else if (temp.size() > 1) {
					for (int i = 0; i < temp.size(); i++) {
						movies.add(temp.get(i));
						if (j >= 0)
							break;
						j--;
					}
				}
			}
			Collections.sort(movies);
			Collections.reverse(movies);
			return movies;
		} catch (IndexOutOfBoundsException e) {
			return movies;
		}
	}

}
