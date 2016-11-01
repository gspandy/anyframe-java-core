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
package org.anyframe.plugin.iam.director.service.impl;

import java.util.List;

import javax.inject.Inject;

import org.anyframe.datatype.SearchVO;
import org.anyframe.plugin.iam.domain.Director;
import org.anyframe.query.QueryService;
import org.anyframe.query.dao.AbstractDao;
import org.springframework.stereotype.Repository;

/**
 * The DirectorDao class is a DAO class to provide CRUD functions about
 * Director domain.
 * 
 * @author Youngmin Jo
 */
@Repository("directorDao")
public class DirectorDao extends AbstractDao{
	
	@Inject
	public void setQueryService(QueryService queryService) {
		super.setQueryService(queryService);
	}
	
	public void create(Director director) throws Exception{
		create("Director", director);
	}
	
	public void remove(String directorId) throws Exception{
		Director director = new Director();
		director.setDirectorId(directorId);
		remove("Director", director);
	}
	
	public void update(Director director) throws Exception{
		update("Director", director);
	}
	
	public Director get(String directorId) throws Exception{
		Director director = new Director();
		director.setDirectorId(directorId);
		return (Director) findByPk("Director", director);
	}
	
	@SuppressWarnings("unchecked")
	public List<Director> getList(SearchVO searchVO) throws Exception{
		return (List<Director>) getQueryService().find("findDirectorList", new Object[] { new Object[] {"vo", searchVO} });
	}
}