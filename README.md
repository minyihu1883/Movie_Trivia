# Movie_Trivia
This program represents a movie database using classes and ArrayLists, with the goal of answering some simple movie trivia questions. It includes two ArrayLists: one with information about actors and the movies they have acted in, and another with information about movies and their ratings.

# Data Structures
Actors ArrayList
The Actors ArrayList stores instances of the Actor class, with the name of the actor and a list of movies the actor has acted in. The information for this ArrayList is loaded from the "moviedata.txt" file, which contains lines with an actor's name followed by movies the actor has acted in. If an actor has no movies listed, an instance of the Actor class with an empty list of movies is still created.

Movies ArrayList
The Movies ArrayList stores instances of the Movie class, with the name of the movie, the critic rating, and the audience rating. The information for this ArrayList is loaded from the "movieratings.csv" file, which contains lines with a movie followed by the critic rating and the audience rating.

# Programming Languages and Techniques
The code is written in Java and uses object-oriented programming techniques, including class-based programming and ArrayLists. The data files are loaded using file I/O in Java. The program includes static methods for answering trivia questions based on the data in the ArrayLists.

# Trivia Questions
The program includes static methods for answering the following trivia questions:
What is the name of the movie that both Tom Hanks and Leonardo DiCaprio acted in?
What are the movie ratings for Rocky I?
Who is the actor that has acted in the most movies?
What is the movie with the highest audience rating?
What is the average critic rating for all movies?

# Future Improvements
This program could be improved in several ways, such as:
Adding more trivia questions
Allowing users to add and modify data in the ArrayLists
Implementing a GUI for a more user-friendly interface


