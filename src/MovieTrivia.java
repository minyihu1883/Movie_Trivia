import java.util.ArrayList;

import file.MovieDB;
import movies.Actor;
import movies.Movie;

/**
 * Movie trivia class providing different methods for querying and updating a movie database.
 * @author Ruotong Zhu & Minyi Hu
 */
public class MovieTrivia {
	
	/**
	 * Create instance of movie database
	 */
	MovieDB movieDB = new MovieDB();
	
	
	public static void main(String[] args) {
		
		//create instance of movie trivia class
		MovieTrivia mt = new MovieTrivia();
		
		//setup movie trivia class
		mt.setUp("moviedata.txt", "movieratings.csv");
	}
	
	/**
	 * Sets up the Movie Trivia class
	 * @param movieData .txt file
	 * @param movieRatings .csv file
	 */
	public void setUp(String movieData, String movieRatings) {
		//load movie database files
		movieDB.setUp(movieData, movieRatings);
		
		//print all actors and movies
		this.printAllActors();
		this.printAllMovies();		
	}
	
	/**
	 * Prints a list of all actors and the movies they acted in.
	 */
	public void printAllActors () {
		System.out.println(movieDB.getActorsInfo());
	}
	
	/**
	 * Prints a list of all movies and their ratings.
	 */
	public void printAllMovies () {
		System.out.println(movieDB.getMoviesInfo());
	}
	
	/**
	 * append the new actor and movies to the actorsInfo. If existed, add the movies to the list
	 * filter the white spaces and not case-sensitive
	 * @param actorName: the name of the given actor
	 * @param moviesName: the movies of the actors
	 * @param actorsInfo: the existed actor info list
	 */
	public void insertActor(String actor, String[] movies, ArrayList<Actor> actorsInfo) {
		//Create a new instance
		Actor newActor = null;
		
		//format the actor's name
		String actorName = actor.trim().toLowerCase();
		
		//notExisted is a flag to see if the actor already exists
		boolean notExisted = true;
		
		//for each existing actor in the list
		for (Actor actorExisted : actorsInfo) {
			//search if there is a same name
			if (actorExisted.getName().equals(actorName))  {
				newActor = actorExisted;  //let newActor point to existed actor
				notExisted = false;  //change the flag to false
				break;  //exist the loop
			}
		}
		
		// if the actor not existed, create a new instance with the name
		if (notExisted)  {
			newActor = new Actor (actorName);
		}
		
		//for each movie in the list
		for (int i = 0;i < movies.length; i++)  {
			//find the index of the movie name
			int index = newActor.getMoviesCast().indexOf(movies[i].trim().toLowerCase());
		
			if (index == -1)  {  //if it doesn't exist
				
				//add the movie to the list
				newActor.getMoviesCast().add(movies[i].trim().toLowerCase());
			}
		}
		
		//add the actor to list if the actor is new
		if (notExisted) {
			actorsInfo.add(newActor);
		}
		
	}
	
	/**
	 * Given an actor, returns the list of all movies. 
	 * Given a non-existent actor, this method should return an empty list
	 * @param actor: the name of the actor
	 * @param actorsInfo: the arraylist of actors
	 * @return the list of movies of the actor
	 */
	public ArrayList<String> selectWhereActorIs(String actor, ArrayList<Actor> actorsInfo) {
		
		//format the actor's name
		String actorName = actor.trim().toLowerCase();
		
		//Initialize the movie list
		ArrayList <String> movieList = new ArrayList<String> ();
		
		//for each actor in list
		for (Actor actorInlist : actorsInfo)  {
			if (actorName.equals(actorInlist.getName()))  { //actor found
				
				//update the value of movie list
				movieList = actorInlist.getMoviesCast();
			}
		}

		return movieList;
	}

	/**
	 * Inserts given ratings for given movie into database
	 * @param movie: the name of the movie
	 * @param ratings: an int array contains ratings
	 * @param moviesInfo: the ArrayList that is to be inserted into/updated
	 */
	public void insertRating(String movie, int[] ratings, ArrayList<Movie> moviesInfo) {
		
		//format the movie name
		String movieName = movie.trim().toLowerCase();
		
		//some cases to return directly
		//ratings length not equals to 2 or ratings is null
		if ((ratings.length != 2) || (ratings == null)) {
			return;
		}
		
		//any of the ratings not inside [0,100]
		if ((ratings[0] < 0 || ratings[0] > 100) || (ratings[1] < 0 || ratings[1] > 100))  {
			return;
		}
		
		//create a new movie instance
		Movie newMovie = null;
		
		//for each existing movie in the list
		for (Movie movieInlist : moviesInfo) {
			
			//existing movie found
			if (movieInlist.getName().equals(movieName)) {
				
				//make newMovie != null
				newMovie = movieInlist;
				//update ratings
				movieInlist.setCriticRating(ratings[0]);
				movieInlist.setAudienceRating(ratings[1]);
				//exit the loop
				break;
			}
		}
		
		//if it is a new movie
		if (newMovie == null) {
			
			//create a new movie instance
			newMovie = new Movie(movieName, ratings[0], ratings[1]);
			
			//add the new movie to movieInfo
			moviesInfo.add(newMovie);
		}
		
	}

	/**
	 * returns a list of movies that satisfy an inequality or equality, based on the 
	 * comparison argument and the targeted rating argument
	 * @param comparison: ��=��, ��>��, or ��< ��
	 * @param targetRating: an integer for target rating
	 * @param isCritic: true = critic ratings, false = audience ratings
	 * @param moviesInfo: list of movies
	 * @return
	 */
	public ArrayList<String> selectWhereRatingIs(char comparison, int targetRating, boolean isCritic, ArrayList<Movie> moviesInfo) {
		
		//create an empty list
		ArrayList<String> movieList = new ArrayList<String>();
		
		//some invalid cases
		//target rating not inside [0,100]
		if (targetRating < 0 || targetRating > 100) {
			return movieList;
		}
		
		//comparison invalid
		if (comparison != '<' && comparison != '>' && comparison != '=') {
			return movieList;
		}
		
		int rating = 0;
		//for each movie in list
		for (Movie movieInlist : moviesInfo) {
			
			//get rating
			if (isCritic) {  //get critic rating
				rating = movieInlist.getCriticRating();
			}
			else {  //get audience rating
				rating = movieInlist.getAudienceRating();
			}
			
			//three cases
			if (comparison == '<') {  //less than
				if (rating < targetRating) {  //meet the requirement
					movieList.add(movieInlist.getName());  //add to list
				}
			}
			
			//more than
			if (comparison == '>') {  
				if (rating > targetRating) {  //meet the requirement
					movieList.add(movieInlist.getName());  //add to list
				}
			}
			
			//equal to
			if (comparison == '=') {  
				if (rating == targetRating) {  //meet the requirement
					movieList.add(movieInlist.getName());  //add to list
				}
			}
		}
		
		return movieList;
	}

	
	/**
	 * Given a movie, returns the list of all actors in that movie
	 * @param movie: the name of the movie
	 * @param actorsInfo: list of actors' info
	 * @return
	 */
	public ArrayList<String> selectWhereMovieIs(String movie, ArrayList<Actor> actorsInfo) {
		
		//create an empty arraylist
		ArrayList<String> actorList = new ArrayList<String> ();
		
		//format the movie's name
		String movieName = movie.trim().toLowerCase();
		
		//for each actor
		for (Actor actorInlist : actorsInfo)  {
			
			//get casted movies list
			ArrayList<String> movieList = actorInlist.getMoviesCast();
			
			//find index of movie
			int index = movieList.indexOf(movieName);
			
			//index != -1, movie found
			if (index != -1) {
				
				//add this actor to list
				actorList.add(actorInlist.getName());
			}
			
		}
		
		//return the actor list
		return actorList;
	}

	/**
	 * Returns a list of all actors that the given actor has ever worked with in any movie
	 * @param actor
	 * @param actorsInfo
	 * @return
	 */
	public ArrayList <String> getCoActors (String actor, ArrayList <Actor> actorsInfo){
		//first to deal with the actor's names
		String a=actor.trim().toLowerCase();
		//create an empty arraylist
		ArrayList <String> co_actor=new ArrayList<String>();
		ArrayList <String> movies=selectWhereActorIs(actor, actorsInfo);
		for(String m:movies) {
			ArrayList <String> actors=selectWhereMovieIs(m, actorsInfo);
			int i=actors.indexOf(a);
			actors.remove(i);
			for(String act:actors) {
				if(!co_actor.contains(act)) {
					co_actor.add(act);
				}
			}
		}
		return co_actor;
	}

	/**
	 * Returns a list of movie names where both actors were cast.
	 * @param actor1
	 * @param actor2
	 * @param actorsInfo
	 * @return array list
	 */
	public ArrayList <String> getCommonMovie (String actor1, String actor2, ArrayList <Actor> actorsInfo){
		//create the final result
		ArrayList <String> common_movies=new ArrayList<String>();
		//first to deal with the actors' names
		String a1=actor1.trim().toLowerCase();
		String a2=actor2.trim().toLowerCase();
		ArrayList <String> m1=selectWhereActorIs(a1, actorsInfo);
		ArrayList <String> m2=selectWhereActorIs(a2, actorsInfo);
		
		for(String movie:m1) {
			for(int i=0; i<m2.size(); i++) {
				if(movie.equals(m2.get(i))) {
					common_movies.add(movie);
				}
			}
		}
		return common_movies;
	}

	/**
	 * Get the high grades movies
	 * @param moviesInfo
	 * @return
	 */
	public ArrayList <String> goodMovies (ArrayList <Movie> moviesInfo){
		//the rates are from Critic
		ArrayList <String> movies_c1=selectWhereRatingIs('>', 85, true, moviesInfo);
		ArrayList <String> movies_c2=selectWhereRatingIs('=', 85, true, moviesInfo);
		//the rates are not from Critic
		ArrayList <String> movies_a1=selectWhereRatingIs('>', 85, false, moviesInfo);
		ArrayList <String> movies_a2=selectWhereRatingIs('=', 85, false, moviesInfo);
		//merge the list
		movies_c1.addAll(movies_c2);
		movies_a1.addAll(movies_a2);
		movies_c1.retainAll(movies_a1);
		//Returns a list of movie names that both critics and the audience have rated above 85
		return movies_c1;		
	}

	/**
	 * Given a pair of movies, this method returns a list of actors that acted in both
	 * @param movie1
	 * @param movie2
	 * @param actorsInfo
	 * @return String array
	 */
	public ArrayList <String> getCommonActors (String movie1, String movie2, ArrayList <Actor> actorsInfo){
		//create the final result
		ArrayList <String> common_actors=new ArrayList<String>();
		//first to deal with the movies' names
		String m1=movie1.trim().toLowerCase();
		String m2=movie2.trim().toLowerCase();
		//create the arraylist for actors
		ArrayList <String> actors_m1=selectWhereMovieIs(m1, actorsInfo);
		ArrayList <String> actors_m2=selectWhereMovieIs(m2, actorsInfo);

		for(String act:actors_m1) {
			for(int i=0; i<actors_m2.size(); i++) {
				if(act.equals(actors_m2.get(i))) {
					if(!common_actors.contains(act)) {
					     common_actors.add(act);
					}
				}
			}
		}
		
		return common_actors;
	}
	
	
	/**
	 * Given the moviesInfo DB, this static method returns the mean value of the critics' ratings and the audience ratings.
	 * @param moviesInfo
	 * @return double array
	 */
	public double [] getMean (ArrayList <Movie> moviesInfo) {
		//create a double type arraylist, witch the size is 2
		double[] c_a_ratings=new double[2];
		double sum_c=0;
		double sum_a=0;
		int L=moviesInfo.size();
		for(int i=0;i<L; i++) {
			sum_c=sum_c+moviesInfo.get(i).getCriticRating();
			sum_a=sum_a+moviesInfo.get(i).getAudienceRating();
		}
		//Returns the mean values as a double array
		//The 1st item (index 0) is the mean of all critics' ratings
		//The 2nd item (index 1) is the mean of all audience' ratings
		c_a_ratings[0]=sum_c/L;
		c_a_ratings[1]=sum_a/L;
		return c_a_ratings;
	}
	
	
}
