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

package de.artignition.fxplumber.view;

import de.artignition.fxplumber.model.Connector;
import de.artignition.fxplumber.model.Connector.ConnectorType;

import javafx.scene.Node;

/**
 * Defines a set of methods that are called in order to define the graphical representation of the Connector in different states of the application.
 * @author M. Fischboeck
 *
 */
public interface ConnectorFactory {

	/**
	 * Called when a new connector is requested.
	 * Implementation should return 
	 * @param type The type of the connector
	 * @return The {@link javafx.scene.Node} that is the graphical representation of the connector
	 */
	public Node newConnector(ConnectorType type);
	
	/**
	 * 
	 * @param current
	 * @param acceptorAccepts
	 * @param isSource
	 * @return
	 */
	public Node onConnectionRequested(Node current, boolean acceptorAccepts, boolean isSource);
	
	public Node onConnectionRequestCancelled(Connector connector, Node current);
	
	public Node onConnectionAccepted(Node current);
}
