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

import javafx.scene.Node;

public class Connection {

	private Connector 			source;
	private Connector			target;
	private Node				node;
	
	Connection(Connector source, Connector target, ConnectionFactory fact) {
		this.source = source;
		this.target = target;
		this.node = fact.getConnectionNode(source, target);
		
		source.setConnection(this);
		target.setConnection(this);
		source.onConnectionAccepted();
		target.onConnectionAccepted();
	}
	
	public Connector getSource() {
		return this.source;
	}

	public Connector getTarget() {
		return this.target;
	}

	Node getConnectionNode() {
		return this.node;
	}
}
