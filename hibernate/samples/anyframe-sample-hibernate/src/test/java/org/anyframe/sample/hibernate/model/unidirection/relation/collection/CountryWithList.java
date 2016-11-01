package org.anyframe.sample.hibernate.model.unidirection.relation.collection;

// Generated 2008. 9. 1 ���� 1:00:42 by Hibernate Tools 3.2.1.GA

import java.util.ArrayList;
import java.util.List;

/**
 * Country generated by hbm2java
 */
@SuppressWarnings("unchecked")
public class CountryWithList implements java.io.Serializable {
	
	private static final long serialVersionUID = 1L;

	private String countryCode;
	private String countryId;
	private String countryName;
	private List movies = new ArrayList(0);

	public CountryWithList() {
	}

	public CountryWithList(String countryCode, String countryId,
			String countryName) {
		this.countryCode = countryCode;
		this.countryId = countryId;
		this.countryName = countryName;
	}

	public CountryWithList(String countryCode, String countryId,
			String countryName, List movies) {
		this.countryCode = countryCode;
		this.countryId = countryId;
		this.countryName = countryName;
		this.movies = movies;
	}

	public String getCountryCode() {
		return this.countryCode;
	}

	public void setCountryCode(String countryCode) {
		this.countryCode = countryCode;
	}

	public String getCountryId() {
		return this.countryId;
	}

	public void setCountryId(String countryId) {
		this.countryId = countryId;
	}

	public String getCountryName() {
		return this.countryName;
	}

	public void setCountryName(String countryName) {
		this.countryName = countryName;
	}

	public List getMovies() {
		return this.movies;
	}

	public void setMovies(List movies) {
		this.movies = movies;
	}

}
