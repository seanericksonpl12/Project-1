import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.io.Reader;
import java.util.Collections;

// --== CS400 File Header Information ==--
// Author: CS400 Course Staff
// Email: heimerl@cs.wisc.edu / dahl@cs.wisc.edu
// Notes: This dummy class is part of the starter archive for Project One
//        in spring 2021. You can extend it to work on your Project One Final
//        App.

public class Backend implements BackendInterface {

	private HashTableMap<String, List<MovieInterface>> genreHash;
	private HashTableMap<String, List<MovieInterface>> ratingHash;
	private List<MovieInterface> movieList;
	private List<String> genres;
	private List<String> ratings;

	/**
	 * This constructor instantiates a backend with the command line arguments
	 * passed to the app when started by the user. The arguments are expected to
	 * contain the path to the data file that the backend will read in.
	 * 
	 * @param args list of command line arguments passed to front end
	 */
	public Backend(String[] args) {
		MovieDataReader movieReader = new MovieDataReader();
	
	}

	/**
	 * A constructor that will create a backend from data that can be read in with a
	 * reader object.
	 * 
	 * @param r A reader that contains the data in CSV format.
	 */
	public Backend(Reader r) {

		genres = new ArrayList<String>();
		ratings = new ArrayList<String>();
		MovieDataReader movieReader = new MovieDataReader();
		movieList = movieReader.readDataSet(r);
		genreHash = new HashTableMap<String, List<MovieInterface>>(movieList.size());
		ratingHash = new HashTableMap<String, List<MovieInterface>>(movieList.size());

	}

	/**
	 * Method to add a genre that the user selected. It will output but not store
	 * the genres passed to it.
	 */
	@Override
	public void addGenre(String genre) {
		genres.add(genre);
		for (int i = 0; i < movieList.size(); i++) {
			for (int j = 0; j < movieList.get(i).getGenres().size(); j++) {
				if (movieList.get(i).getGenres().get(j).equals(genre) && !genreHash.containsKey(genre)) {
					List<MovieInterface> newList = new ArrayList<MovieInterface>();
					newList.add(movieList.get(i));
					genreHash.put(genre, newList);
				} else if (movieList.get(i).getGenres().get(j).equals(genre) && genreHash.containsKey(genre)) {
					genreHash.get(genre).add(movieList.get(i));
					genreHash.increaseSize();
				}
			}
		}
	}

	/**
	 * Method to add a genre that the user selected. It will output but not store
	 * the ratings passed to it.
	 */
	@Override
	public void addAvgRating(String rating) {
		// TODO: Fixme! Add dummy implementation similar to addGenre method.
		int i = 0;
		while (i <= 10) {
			String str = String.valueOf(i);
			if (rating.equals(str)) {
				double x = i;
				double ceil = x + .999;
				ratings.add(rating);
				for (int j = 0; j < movieList.size(); j++) {
					if (movieList.get(j).getAvgVote() <= ceil && movieList.get(j).getAvgVote() >= x
							&& !ratingHash.containsKey(rating)) {
						List<MovieInterface> newList = new ArrayList<MovieInterface>();
						newList.add(movieList.get(j));
						ratingHash.put(rating, newList);
						return;
					} else if (movieList.get(j).getAvgVote() <= ceil && movieList.get(j).getAvgVote() >= x
							&& ratingHash.containsKey(rating)) {
						ratingHash.get(rating).add(movieList.get(j));
						ratingHash.increaseSize();
					}
				}
			}
			i++;
		}
		System.out.println("Invalid Rating");
	}

	/**
	 * Method to remove a genre that the user selected. It will output but not
	 * remove the genre passed to it from the backend object.
	 */
	@Override
	public void removeGenre(String genre) {
		// TODO: Fixme! Add dummy implementation similar to addGenre method.
		genres.remove(genre);
		genreHash.remove(genre);
	}

	/**
	 * Method to remove a rating that the user selected. It will output but not
	 * remove the genre passed to it from the backend object.
	 */
	@Override
	public void removeAvgRating(String rating) {
		// TODO: Fixme! Add dummy implementation similar to addGenre method.
		ratings.remove(rating);
		ratingHash.remove(rating);
	}

	/**
	 * Return the genres added to this backend object. The dummy implementation
	 * always returns the same list of genres for testing.
	 */
	@Override
	public List<String> getGenres() {
		return genres;
	}

	/**
	 * Return the ratings added to this backend object. The dummy implementation
	 * always returns the same list of ratings for testing.
	 */
	@Override
	public List<String> getAvgRatings() {
		// TODO: Fixme! Add dummy implementation similar to getGenres.
		return ratings;
	}

	/**
	 * Returns the number of movies. This is a constant for the dummy
	 * implementation.
	 */
	@Override
	public int getNumberOfMovies() {
		int sum = 0;
		boolean swtch1 = false;
		boolean swtch2 = false;
		// Cross reference hash tables to check that there are no duplicates
		for (int i = 0; i < movieList.size(); i++) {
			swtch1 = false;
			swtch2 = false;

			for (int j = 0; j < genres.size(); j++) {
				for (int k = 0; k < movieList.get(i).getGenres().size(); k++) {
					if (movieList.get(i).getGenres().get(k).equals(genres.get(j)))
						swtch1 = true;
				}
			}

			// check that it has the correct rating
			for (int j = 0; j < ratings.size(); j++) {
				int x = Integer.parseInt(ratings.get(j));
				double temp = x;
				double ceil = temp + .999;

				if (movieList.get(i).getAvgVote() >= temp && movieList.get(i).getAvgVote() <= ceil)
					swtch2 = true;
			}

			if (swtch1 && swtch2)
				sum++;

		}
		return sum;
	}

	/**
	 * Returns all genres in the dataset. Will return a list of 5 genres for the
	 * dummy implementation.
	 */
	@Override
	public List<String> getAllGenres() {
		List<String> rtn = new ArrayList<String>();
		for (int i = 0; i < movieList.size(); i++) {
			for (int j = 0; j < movieList.get(i).getGenres().size(); j++) {
				if (!rtn.contains(movieList.get(i).getGenres().get(j))) {
					rtn.add(movieList.get(i).getGenres().get(j));
				}
			}
		}

		return rtn;
	}

	/**
	 * Returns the movies that match the ratings and genres. The dummy
	 * implementation will return the same list of three movies.
	 */
	@Override
	public List<MovieInterface> getThreeMovies(int startingIndex) {
		ArrayList<MovieInterface> movies = new ArrayList<MovieInterface>();
		int count = startingIndex;
		int size = 0;
		while (size < 3 && count >= 0) {
			String rating = String.valueOf(count);
			if (ratingHash.containsKey(rating) && ratingHash.get(rating).size() > 1) {

				int j = 0;
				while (j < ratingHash.get(rating).size() && size < 3) {
					if (!Collections.disjoint(ratingHash.get(rating).get(j).getGenres(), genres)) {
						movies.add(ratingHash.get(rating).get(j));
						j++;
						size++;
						count--;
					} else
						j++;

				}
			} else if (ratingHash.containsKey(rating) && ratingHash.get(rating).size() == 1) {
				if (!Collections.disjoint(ratingHash.get(rating).get(0).getGenres(), genres)) {
					movies.add(ratingHash.get(rating).get(0));
					count--;
					size++;
				}
			} else
				count--;
		}

		return movies;
	}
}
