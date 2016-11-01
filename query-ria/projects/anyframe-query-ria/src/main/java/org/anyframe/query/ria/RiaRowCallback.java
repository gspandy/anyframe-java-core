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
package org.anyframe.query.ria;

import org.anyframe.query.QueryInfo;
import org.anyframe.query.RowMetadataCallbackHandler;
import org.anyframe.query.impl.Pagination;
/**
 * @author JongHoon Kim
 */
public interface RiaRowCallback extends RowMetadataCallbackHandler {

	/**
	 * set paging information for IRiaQueryService
	 * 
	 * @param pagination
	 */
	void setPagination(Pagination pagination);
	
	void setDataSet(Object dataset);
	
	public void setQueryInfo( QueryInfo queryInfo);
	
	public boolean isNeedColumnInfo();

	public void setNeedColumnInfo(boolean needColumnInfo);
}