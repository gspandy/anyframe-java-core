/*
 * Copyright 2002-2008 the original author or authors.
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
package org.anyframe.query.impl;

import java.sql.Date;
import java.util.Collection;

import org.anyframe.query.QueryService;
import org.anyframe.query.vo.Reservation;
import org.anyframe.util.DateUtil;
import org.springframework.test.AbstractDependencyInjectionSpringContextTests;

/**
 * TestCase Name : QueryServiceReservationTest <br>
 * <br>
 * [Description] : Without additional definition of  query statement, data is entered, modified, 
 * deleted and searched with only object entering and then its result is verified.<br>
 * [Main Flow]
 * <ul>
 * <li>#-1 Positive Case : By calling for create() method of QueryService, 
 * without additional definition of query statement, new data is entered with object.
 * QueryService creates and implements INSERT query statement based on table mapping information.</li>
 * <li>#-2 Positive Case : By calling for update() method of QueryService, 
 * without additional definition of query statement, new data is modified with object.
 * QueryService creates and implements UPDATE query statement based on table mapping information.</li>
 * <li>#-3 Positive Case : By calling for remove() method of QueryService, 
 * without additional definition of query statement, new data is deleted with object.
 * QueryService creates and implements UPDATE query statement based on table mapping information.</li>
 * </ul>
 * @author SoYon Lim
 */
public class QueryServiceReservationTest extends
        AbstractDependencyInjectionSpringContextTests {
    private QueryService queryService = null;

    public void setQueryService(QueryService queryService) {
        this.queryService = queryService;
    }

    protected String[] getConfigLocations() {
        setAutowireMode(AbstractDependencyInjectionSpringContextTests.AUTOWIRE_BY_NAME);
        return new String[] {"classpath*:/spring/context-*.xml" };
    }

    /**
     * Table TB_RESERVATION is created for test.
     */
    public void onSetUp() throws Exception {
        System.out.println("Attempting to drop old table");
        try {
            queryService.updateBySQL("DROP TABLE TB_RESERVATION",
                new String[] {}, new Object[] {});
        } catch (Exception e) {
            System.out.println("Fail to DROP Table.");
        }

        queryService.updateBySQL("CREATE TABLE TB_RESERVATION ( "
            + "RESERVE_ID varchar2(4) NOT NULL, "
            + "RESERVE_SSNO varchar2(13) NOT NULL, "
            + "RESERVE_NM varchar2(20), " + "RESERVE_DATE DATE, "
            + "PRIMARY KEY (RESERVE_ID, RESERVE_SSNO))", new String[] {},
            new Object[] {});
    }

    /**
     * [Flow #-1] Positive Case : By calling for create() method of QueryService, 
     * without additional definition of query statement, new data is entered with object. 
     * QueryService creates and implements INSERT query statement based on table mapping information.
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testInsertReservationByObject() throws Exception {
        // 1. set object value for test
        String reserveId = "0001";
        String reserveSsno = "1234567890123";
        String reserveNm = "Test";
        java.util.Date currentDate = new java.util.Date();
        Date reserveDate = new Date(currentDate.getTime());

        Reservation reservation =
            new Reservation(reserveId, reserveSsno, reserveNm, reserveDate);

        // 2. execute query
        int results = queryService.create(reservation);

        // 3. assert
        assertEquals("Fail to insert object.", results, 1);
        findReservation(reserveId, reserveSsno);
    }

    /**
     * [Flow #-2] Positive Case : By calling for update() method of QueryService, 
     * without additional definition of query statement, new data is modified with object. 
     * QueryService creates and implements UPDATE query statement based on table mapping information. 
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testUpdateReservationByObject() throws Exception {
        // 1. set object value for test
        Reservation reservation =
            insertReservation("0001", "1234567890123", "Anyframe", "2007-06-06");

        // 2. execute query
        int results = queryService.update(reservation);

        // 3. assert
        assertEquals("Fail to update object.", results, 1);
        findReservation(reservation.getReserveId(), reservation
            .getReserveSsno());
    }

    /**
     * [Flow #-3] Positive Case : By calling for remove() method of QueryService, 
     * without additional definition of query statement, new data is deleted with object. 
     * QueryService creates and implements UPDATE query statement based on table mapping information.
     * @throws Exception
     *         throws exception which is from
     *         QueryService
     */
    public void testRemoveReservationByObject() throws Exception {
        // 1. set object value for test
        Reservation reservation =
            insertReservation("0001", "1234567890123", "Anyframe", "2007-06-06");

        // 2. execute query
        int results = queryService.remove(reservation);

        // 3. assert
        assertEquals("Fail to update object.", results, 1);
        Collection rtCollection = (Collection) queryService.find(reservation);
        assertEquals("Fail to remove reservation.", 0, rtCollection.size());
    }

    /**
     * Without additional definition of query statement, 
     * one piece of data is searched and its result is verified.
     * @param reserveId
     * @param reserveSsno
     * @return Search result Reservation object
     * @throws Exception
     *         fail to find reservation
     */
    private Reservation findReservation(String reserveId, String reserveSsno)
            throws Exception {
        // 1. set object value for test
        Reservation reservation =
            new Reservation(reserveId, reserveSsno, null, null);

        // 2. execute query
        Collection rtCollection = (Collection) queryService.find(reservation);

        // 3. assert
        assertNotNull("Fail to find object.", rtCollection);
        assertTrue("Fail to find object.", rtCollection.size() == 1);

        // 4. assert in detail
        Reservation result = ((Reservation) rtCollection.iterator().next());
        assertEquals("Fail to compare reserveSsno.", reserveSsno, result
            .getReserveSsno());

        // 5. return for another test
        return result;
    }

    /**
     * Without additional definition of query statement, new data is entered with object.
     * @param reserveId
     * @param reserveSsno
     * @param reserveNm
     * @param reserveDate
     * @return Reservation object including newly entered data
     * @throws Exception
     *         fail to insert reservation
     */
    private Reservation insertReservation(String reserveId, String reserveSsno,
            String reserveNm, String reserveDate) throws Exception {
        // set object value for test
        Reservation reservation =
            new Reservation(reserveId, reserveSsno, reserveNm, DateUtil
                .string2SQLDate(reserveDate));

        // 2. execute query
        int results = queryService.create(reservation);
        assertEquals("Fail to insert object.", results, 1);

        // 3. return for another test
        return reservation;
    }
}