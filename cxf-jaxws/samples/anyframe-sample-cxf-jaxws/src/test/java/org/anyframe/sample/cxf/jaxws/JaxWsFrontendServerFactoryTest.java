/*
 * Copyright 2008-2011 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.anyframe.sample.cxf.jaxws;

import java.util.List;
import java.util.Map;

import org.anyframe.sample.cxf.jaxws.client.Client;
import org.anyframe.sample.cxf.jaxws.client.ClientInfo;
import org.anyframe.sample.cxf.jaxws.client.JaxWsClient;
import org.anyframe.sample.cxf.jaxws.domain.Movie;
import org.anyframe.sample.cxf.jaxws.moviefinder.service.MovieService;
import org.anyframe.sample.cxf.jaxws.moviefinder.service.impl.MovieServiceImpl;
import org.anyframe.sample.cxf.jaxws.server.JaxWsServer;
import org.anyframe.sample.cxf.jaxws.server.ServerInfo;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;


/**
 * TestCase Name : JaxWsFrontendServerFactoryTest <br>
 * <br>
 * [Description] : Apache CXF의 2가지 frontend model(Simple/JAX-WS) 중 JAX-WS frontend를 사용하여
 *                 JaxWsServerFactoryBean를 통해 구동된 Movie Web Service의 기능을 테스트할 수 있는 시나리오들을 정의한 TestCase이다.
 * <br>
 * [Characteristic]
 * <li>JAX-WS Frontend 사용 - Annotation 설정 필수, Web Services 표준 API인 JAX-WS 사용</li>
 * <li>Annotation 작성 - @WebService()만 필수 사항이며, 대부분 annotation의 경우 디폴트값이 제공되므로 작성 불필요</li>
 * <li>서버 구현: JAX-WS frontend 방식 이용 시 JaxWsServerFactoryBean API를 통해 Movie 서비스의 
 *               interface class, implementation class, URL address 정보 등을 설정하여 손쉽게 Movie 서비스를 
 *               Web Service로 노출시킨다.</li>
 * <br>
 * [Assumption]
 * <li>-서버 : JaxWsServer 사용</li>
 * <li>-클라이언트 : JaxWsClient 사용</li>
 * <br>
 * [Main Flow]
 * <li>#-1  Positive Case : List 형태로 전체 목록을 조회한다.</li>
 * <li>#-2  Positive Case : Map 형태로 전체 목록을 조회한다.</li>
 * <li>#-3  Positive Case : Movie를 조회한다.</li>
 * <li>#-4  Positive Case : 신규 Movie를 생성한다.</li>
 * <li>#-5  Positive Case : Movie 정보를 변경한다.</li>
 * <li>#-6  Positive Case : Movie를 삭제한다.</li> 
 * 
 * @author SooYeon Park
 */
@RunWith(JUnit4.class)
public class JaxWsFrontendServerFactoryTest extends ServerRunner {

    // ==============================================================
    // ====== TestCase 수행에 필요한 사전 작업 정의 ====================
    // ==============================================================
    @Before
    public void setUp() throws Exception {
        this.setServer(new JaxWsServer());
        this.getServer().setServerInfo(
            new ServerInfo(MovieService.class, new MovieServiceImpl(),
                "http://localhost:9002/Movie", false));
        super.setUp();
    }

    @After
    public void tearDown() throws Exception {
    	super.tearDown();	
    }	
    
    // ==============================================================
    // ====== TestCase methods ======================================
    // ==============================================================
        
    /**
     * [Flow #-1] Positive Case : List 형태로 전체 목록을 조회한다.
     * @throws Exception
     *         throws exception which is from service
     */   
    @Test
    public void testFindMovieListAll() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. find movie list all
        List<Movie> movieList = movieService.findMovieListAll();

        // 2. check the movie list count
        Assert.assertEquals(2, movieList.size());
    }

    /**
     * [Flow #-2] Positive Case : Map 형태로 전체 목록을 조회한다.
     * @throws Exception
     *         throws exception which is from service
     */   
    @Test
    public void testFindMovieMapAll() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. find movie map all
        Map<String, Movie> movieMap = movieService.findMovieMapAll();

        // 2. check the movie map count
        Assert.assertEquals(2, movieMap.size());
    }

    /**
     * [Flow #-3] Positive Case : Movie Id가 "001"인 Movie를 조회한다.
     * @throws Exception
     *         throws exception which is from service
     */   
    @Test
    public void testFindMovie() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. find movie
        Movie movie = movieService.findMovie("001");

        // 2. check the movie information
        Assert.assertEquals("The Sound Of Music", movie.getTitle());
        Assert.assertEquals("Robert Wise", movie.getDirector());
    }

    /**
     * [Flow #-4] Positive Case : Movie Id가 "003"인 신규 Movie를 생성한다.
     * @throws Exception
     *         throws exception which is from service
     */  
    @Test
    public void testCreateMovie() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. check the existing movie list count
        Assert.assertEquals(2, movieService.findMovieListAll().size());

        // 2. create new movie
        movieService.createMovie(new Movie("003", "Life Is Beautiful",
            "Roberto Benigni"));

        // 3. check the new movie list count
        Assert.assertEquals(3, movieService.findMovieListAll().size());

        // 4. check the new movie information
        Movie movie = movieService.findMovie("003");
        Assert.assertEquals("Life Is Beautiful", movie.getTitle());
        Assert.assertEquals("Roberto Benigni", movie.getDirector());
    }

    /**
     * [Flow #-5] Positive Case : Movie Id가 "002"인 기존 Movie 정보를 변경한다.
     * @throws Exception
     *         throws exception which is from service
     */     
    @Test
    public void testUpdateMovie() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. update movie
        movieService.updateMovie(new Movie("002", "Life Is Wonderful",
            "Roberto"));

        // 2. find updated movie
        Movie movie = movieService.findMovie("002");

        // 3. check the movie information
        Assert.assertEquals("Life Is Wonderful", movie.getTitle());
        Assert.assertEquals("Roberto", movie.getDirector());
    }

    /**
     * [Flow #-6] Positive Case : Movie Id가 "002"인 기존 Movie를 삭제한다.
     * @throws Exception
     *         throws exception which is from service
     */  
    @Test 
    public void testRemoveMovie() throws Exception {
        Client client = new JaxWsClient();
        MovieService movieService =
            (MovieService) client.getClient(new ClientInfo(MovieService.class,
                "http://localhost:9002/Movie", false));

        // 1. set movie id to remove
        Movie movie = new Movie();
        movie.setMovieId("002");

        // 2. remove the movie
        movieService.removeMovie(movie);

        // 3. check the removed movie info
        Assert.assertNull(movieService.findMovie("002"));
    }

}