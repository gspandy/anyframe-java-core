/*
 * Copyright 2002-2012 the original author or authors.
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
package org.anyframe.query.impl.util;

import org.slf4j.Logger;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

/**
 * Simple <code>org.xml.sax.ErrorHandler</code> implementation:
 * logs warnings using the given Commons Logging logger instance,
 * and rethrows errors to discontinue the XML transformation.
 * <br>
 * We changed
 * org.springframework.util.xml.SimpleSaxErrorHandler Class into
 * org.anyframe.query.impl.util.SimpleSaxErrorHandler
 * <ul>
 * <li>We changed this source for replacing jcl to slf4j.</li>
 * </ul>
 *
 * @author Juergen Hoeller
 * @author modified by Jongpil Park 
 */
public class SimpleSaxErrorHandler implements ErrorHandler {
	
	private final Logger logger;

	/**
	 * Create a new SimpleSaxErrorHandler for the given
	 * Simple Logging Facade for Java logger instance.
	 */
	public SimpleSaxErrorHandler(Logger logger) {
		this.logger = logger;
	}

	public void warning(SAXParseException ex) throws SAXException {
		logger.warn("Ignored XML validation warning", ex);
	}

	public void error(SAXParseException ex) throws SAXException {
		throw ex;
	}

	public void fatalError(SAXParseException ex) throws SAXException {
		throw ex;
	}
}
