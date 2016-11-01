package org.anyframe.hibernate.paging;

import java.util.List;
import java.util.Set;

import org.anyframe.hibernate.AbstractConfigurationalTransactionalTest;
import org.anyframe.hibernate.SetUpInitData;
import org.anyframe.sample.hibernate.model.bidirection.Movie;
import org.hibernate.Query;


/**
 * TestCase Name : HibernateHQLPagingTest<br>
 * <br>
 * [Description] : Hibernate Query Language 수행시 페이징 처리된 조회 결과를 얻기 위한 방법에 대해
 * 알아본다.<br>
 * [Main Flow]
 * <ul>
 * <li>#-1] Positive Case : MOVIE 테이블을 대상으로 HQL을 이용한 조회 작업을 수행한다. 이때, 조회를 시작해야
 * 하는 Row의 Number(FirstResult)와 조회 목록의 개수(MaxResult)를 정의함으로써, 페이징 처리가 가능해진다.</li>
 * </ul>
 * 
 * @author SoYon Lim
 */
public class HibernateHQLPagingTest extends
		AbstractConfigurationalTransactionalTest {
	protected String getHibernateConfigLocation() {
		return "hibernateconfig/hibernate.cfg.xml";
	}

	/**
	 * [Flow #-1] Positive Case : MOVIE 테이블을 대상으로 HQL을 이용한 조회 작업을 수행한다. 이때, 조회를
	 * 시작해야 하는 Row의 Number(FirstResult)와 조회 목록의 개수(MaxResult)를 정의함으로써, 페이징 처리가
	 * 가능해진다.
	 * 
	 * @throws Exception
	 */
	public void testMovieListWithPaging() throws Exception {
		// 1. insert init data
		SetUpInitData.initializeData(session);

		// 2. execute hql
		StringBuffer hqlBuf = new StringBuffer();
		hqlBuf.append("FROM Movie movie ");
		Query hqlQuery = session.createQuery(hqlBuf.toString());
		hqlQuery.setFirstResult(1);
		hqlQuery.setMaxResults(2);
		List movieList = hqlQuery.list();

		// 3. assert result - movie
		assertEquals("fail to match the size of movie list.", 2, movieList
				.size());

		for (int i = 0; i < movieList.size(); i++) {
			Movie movie = (Movie) movieList.get(i);

			if (i == 0) {
				assertEquals("fail to match a movie title.", "My Little Bride",
						movie.getTitle());
				assertEquals("fail to match a movie director.", "Hojun Kim",
						movie.getDirector());

				Set categories = movie.getCategories();
				assertEquals("fail to match the size of category list.", 2,
						categories.size());
			} else if (i == 1) {
				assertEquals("fail to match a movie title.", "Ring 2", movie
						.getTitle());
				assertEquals("fail to match a movie director.", "Hideo Nakata",
						movie.getDirector());

				Set categories = movie.getCategories();
				assertEquals("fail to match the size of category list.", 1,
						categories.size());
			}
		}
	}
}