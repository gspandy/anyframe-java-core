package anyframe.sample.di.qualifier.moviefinder.dao.impl;

import javax.inject.Named;

import anyframe.sample.di.qualifier.DaoQualifier;
import anyframe.sample.di.qualifier.moviefinder.dao.MovieDao;
import anyframe.sample.domain.Movie;

@Named
@DaoQualifier(type = "hibernate")
public class MovieDaoHibernateImpl implements MovieDao {

	public Movie get(String movieId) throws Exception {
		System.out.println("call get() in MovieDaoHibernateImpl");
		Movie movie = new Movie();
		movie.setTitle("Alice in Wonderland");
		movie.setDirector("Tim Burton");

		return movie;
	}

}