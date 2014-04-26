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

package de.artignition.fxplumber.model;

import de.artignition.fxplumber.view.ConnectionFactory;
import de.artignition.fxplumber.view.StartAndEndAwareShape;

/**
 * Represents a connection between two {@link Connector}'s.
 * The graphical representation of the connection depends on the {@link de.artignition.fxplumber.view.ConnectionFactory} that
 * has been set by the user. It is important to know, that the produces graphical representation will be 
 * added directly to the root of the graph and thus, overlapping all other graphics like {@link GraphNode} or {@link Connector}.
 * 
 * @author M. Fischboeck 
 */
public class Connection {

	private Connector 				source;
	private Connector				target;
	private StartAndEndAwareShape 	node;

	/**
	 * Creates a connection between the provided {@link Connector}'s. 
	 * @param source The source connector of the connection
	 * @param target The target connector of the connection
	 * @param fact The factory used to produce the graphical view of the connection
	 */
	Connection(Connector source, Connector target, ConnectionFactory fact) {
		this.source = source;
		this.target = target;
		this.node = fact.getConnectionNode(source, target);
		
		source.setConnection(this);
		target.setConnection(this);
		source.onConnectionAccepted();
		target.onConnectionAccepted();
	}
	

	/**
	 * Destroys this connection by setting the connectors to null
	 */
	void dispose() {
		this.source = null;
		this.target = null;
	}
	
	/**
	 * Returns the source {@link Connector} of the connection
	 * @return The source connector
	 */
	public Connector getSource() {
		return this.source;
	}

	/**
	 * Returns the target {@link Connector} of the connection.
	 * @return The target connector
	 */
	public Connector getTarget() {
		return this.target;
	}

	/**
	 * Returns the graphical representation of the connection
	 * @return The node the connection is drawn on
	 */
	StartAndEndAwareShape getConnectionNode() {
		return this.node;
	}
}
