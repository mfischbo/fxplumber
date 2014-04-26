/*
 *     Copyright 2014 Markus Fischböck 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.artignition.fxplumber.util;

import de.artignition.fxplumber.model.Connector;

/**
 * This interface defines a method that will be called before a connection is being created.
 * Implementing classes must state if the connection should be created or not.
 * @author M. Fischboeck
 *
 */
public interface ConnectionAcceptor {

	/**
	 * Called before a connection is created between the given connectors.
	 * If the implementor returns true, the connection will be created, otherwise
	 * the connection will be ommited.
	 * @param source The source connector
	 * @param target The target connector
	 * @return True, when the connection should be created, false otherwise
	 */
	public boolean accepts(Connector source, Connector target);
}
