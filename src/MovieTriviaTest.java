import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import file.MovieDB;


class MovieTriviaTest {
	
	//instance of movie trivia object to test
	MovieTrivia mt;
	//instance of movieDB object
	MovieDB movieDB;
	
	@BeforeEach
	void setUp() throws Exception {
		//initialize movie trivia object
		mt = new MovieTrivia ();
		
		//set up movie trivia object
		mt.setUp("moviedata.txt", "movieratings.csv");
		
		//get instance of movieDB object from movie trivia object 
		movieDB = mt.movieDB;
	}

	
	@Test
	void testSetUp() { 
		assertEquals(6, movieDB.getActorsInfo().size());
		assertEquals(7, movieDB.getMoviesInfo().size());
		
		assertEquals("meryl streep", movieDB.getActorsInfo().get(0).getName());
		assertEquals(3, movieDB.getActorsInfo().get(0).getMoviesCast().size());
		assertEquals("doubt", movieDB.getActorsInfo().get(0).getMoviesCast().get(0));
		
		assertEquals("doubt", movieDB.getMoviesInfo().get(0).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(0).getCriticRating());
		assertEquals(78, movieDB.getMoviesInfo().get(0).getAudienceRating());
	}
	
	@Test
	void testInsertActor () {
		
		//try to insert new actor with new movies
		mt.insertActor("test1", new String [] {"testmovie1", "testmovie2"}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());	
		assertEquals("test1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getName());
		assertEquals(2, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().size());
		assertEquals("testmovie1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size() - 1).getMoviesCast().get(0));
		
		//try to insert existing actor with new movies
		mt.insertActor("   Meryl STReep      ", new String [] {"   DOUBT      ", "     Something New     "}, movieDB.getActorsInfo());
		assertEquals(7, movieDB.getActorsInfo().size());
		
		//look up and inspect movies for existing actor
		//note, this requires the use of properly implemented selectWhereActorIs method
		assertEquals(4, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).contains("something new"));
		
		// TODO add additional test case scenarios
		mt.insertActor("Elly  ", new String [] {"   mov1 ", "moV2  ","mOv3","  Mov4"}, movieDB.getActorsInfo());
		assertEquals(8, movieDB.getActorsInfo().size());
		assertEquals("elly", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size()-1).getName());
		assertEquals(4, movieDB.getActorsInfo().get(movieDB.getActorsInfo().size()-1).getMoviesCast().size());
		assertEquals("mov1", movieDB.getActorsInfo().get(movieDB.getActorsInfo().size()-1).getMoviesCast().get(0));

		
	}
	
	@Test
	void testInsertRating () {
		
		//try to insert new ratings for new movie 
		mt.insertRating("testmovie", new int [] {79, 80}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());	
		assertEquals("testmovie", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getName());
		assertEquals(79, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size() - 1).getAudienceRating());
		
		//try to insert new ratings for existing movie 
		mt.insertRating("doubt", new int [] {100, 100}, movieDB.getMoviesInfo());
		assertEquals(8, movieDB.getMoviesInfo().size());
		
		//look up and inspect movies based on newly inserted ratings
		//note, this requires the use of properly implemented selectWhereRatingIs method
		assertEquals(1, mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).size());
		assertTrue(mt.selectWhereRatingIs('>', 99, true, movieDB.getMoviesInfo()).contains("doubt"));
		
		// TODO add additional test case scenarios
		mt.insertRating("Rate", new int [] {80, 80}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());	
		assertEquals("rate", movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size()-1).getName());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size()-1).getCriticRating());
		assertEquals(80, movieDB.getMoviesInfo().get(movieDB.getMoviesInfo().size()-1).getAudienceRating());
		
		mt.insertRating("Rate", new int [] {200, 80}, movieDB.getMoviesInfo());
		assertEquals(9, movieDB.getMoviesInfo().size());
		assertEquals(4, mt.selectWhereRatingIs('>', 90, true, movieDB.getMoviesInfo()).size());
		assertTrue(mt.selectWhereRatingIs('>', 90, true, movieDB.getMoviesInfo()).contains("doubt"));
		
	}
	
	@Test
	void testSelectWhereActorIs () {
		assertEquals(3, mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).size());
		assertEquals("doubt", mt.selectWhereActorIs("meryl streep", movieDB.getActorsInfo()).get(0));
		
		// TODO add additional test case scenarios
		assertEquals(1, mt.selectWhereActorIs("robin williams", movieDB.getActorsInfo()).size());
		assertEquals("popeye", mt.selectWhereActorIs("robin williams", movieDB.getActorsInfo()).get(0));
		assertEquals(2, mt.selectWhereActorIs("brad pitt", movieDB.getActorsInfo()).size());
		assertEquals("fight club", mt.selectWhereActorIs("brad pitt", movieDB.getActorsInfo()).get(1));
		
		
	}
	
	@Test
	void testSelectWhereMovieIs () {
		assertEquals(2, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("amy adams"));
		assertEquals(2, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).size());
		assertEquals(true, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(false, mt.selectWhereMovieIs("doubt", movieDB.getActorsInfo()).contains("tom hanks"));
		
		// TODO add additional test case scenarios
		assertEquals(true, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("meryl streep"));
		assertEquals(true, mt.selectWhereMovieIs("the post", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(1, mt.selectWhereMovieIs("popeye", movieDB.getActorsInfo()).size());
		assertEquals(false, mt.selectWhereMovieIs("popeye", movieDB.getActorsInfo()).contains("meryl streep"));
		
	}
	
	@Test
	void testSelectWhereRatingIs () {
		assertEquals(6, mt.selectWhereRatingIs('>', 0, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('=', 65, false, movieDB.getMoviesInfo()).size());
		assertEquals(2, mt.selectWhereRatingIs('<', 30, true, movieDB.getMoviesInfo()).size());
		
		// TODO add additional test case scenarios
		assertEquals(1, mt.selectWhereRatingIs('=', 0, false, movieDB.getMoviesInfo()).size());
		assertEquals(6, mt.selectWhereRatingIs('>', 0, false, movieDB.getMoviesInfo()).size());
		assertEquals(4, mt.selectWhereRatingIs('<', 90, true, movieDB.getMoviesInfo()).size());
		assertEquals(0, mt.selectWhereRatingIs('*', 100, true, movieDB.getMoviesInfo()).size());
	}
	
	
	@Test
	void testGetCoActors () {
		assertEquals(2, mt.getCoActors("meryl streep", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("tom hanks"));
		assertTrue(mt.getCoActors("meryl streep", movieDB.getActorsInfo()).contains("amy adams"));
		
		// TODO add additional test case scenarios
		assertEquals(0, mt.getCoActors("brandon krakowsky", movieDB.getActorsInfo()).size());
		assertEquals(1, mt.getCoActors("tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("meryl streep"));
		assertFalse(mt.getCoActors("tom hanks", movieDB.getActorsInfo()).contains("amy adams"));
		
	}
	
	@Test
	void testGetCommonMovie () {
		assertEquals(1, mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "tom hanks", movieDB.getActorsInfo()).contains("the post"));
		
		// TODO add additional test case scenarios
		assertEquals(0, mt.getCommonMovie("tom hanks", "amy adams", movieDB.getActorsInfo()).size());
		assertEquals(1, mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonMovie("meryl streep", "amy adams", movieDB.getActorsInfo()).contains("doubt"));
		assertEquals(0, mt.getCommonMovie("brandon krakowsky", "amy adams", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testGoodMovies () {
		assertEquals(3, mt.goodMovies(movieDB.getMoviesInfo()).size());
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("jaws"));
		
		// TODO add additional test case scenarios
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("doubt"));
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("rocky ii"));
		assertTrue(mt.goodMovies(movieDB.getMoviesInfo()).contains("et"));
		assertFalse(mt.goodMovies(movieDB.getMoviesInfo()).contains("arrival"));
	}
	
	@Test
	void testGetCommonActors () {
		assertEquals(1, mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("doubt", "the post", movieDB.getActorsInfo()).contains("meryl streep"));
		
		// TODO add additional test case scenarios
		assertEquals(1, mt.getCommonActors("cast away", "the post", movieDB.getActorsInfo()).size());
		assertEquals(1, mt.getCommonActors("doubt", "arrival", movieDB.getActorsInfo()).size());
		assertTrue(mt.getCommonActors("cast away", "the post", movieDB.getActorsInfo()).contains("tom hanks"));
		assertEquals(0, mt.getCommonActors("popeye", "arrival", movieDB.getActorsInfo()).size());
	}
	
	@Test
	void testGetMean () {
		// TODO add ALL test case scenarios!
		double[] mean_1=mt.getMean(movieDB.getMoviesInfo());
		assertEquals(67.857, mean_1[0], 0.01);
		assertEquals(65.714, mean_1[1], 0.01);
		
		int[] rating={80, 80};
		mt.insertRating("hello world", rating, movieDB.getMoviesInfo());
		double[] mean_2=mt.getMean(movieDB.getMoviesInfo());
		assertEquals(69.375, mean_2[0], 0.01);
		assertEquals(67.5, mean_2[1], 0.01);
		
		int[] rating_1={20, 20};
		mt.insertRating("hi world", rating_1, movieDB.getMoviesInfo());
		double[] mean_3=mt.getMean(movieDB.getMoviesInfo());
		assertEquals(63.889, mean_3[0], 0.01);
		assertEquals(62.222, mean_3[1], 0.01);
		
	}
}
