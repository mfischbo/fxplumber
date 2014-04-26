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

import de.artignition.fxplumber.event.ConnectorEvent;
import de.artignition.fxplumber.view.ConnectorFactory;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Represents a port on a {@link GraphNode} where the node is sensitive for establishing {@link Connection}'s to others.
 * 
 * @author M. Fischboeck
 *
 */
public class Connector {

	public enum ConnectorType {
		INPUT,
		OUTPUT
	}

	/* The connectors graph node */
	private GraphNode		graphNode;
	
	/* The actual node displaying the connector */
	private Node   			node;

	private ConnectorFactory factory;
	
	/* The connection this connector holds if any */
	private Connection		connection = null;
	
	/* The type of the connector */
	private ConnectorType	type;

	
	/**
	 * Creates a new connector on the given graph node.
	 * @param graphNode The GraphNode on which to create the connector
	 * @param parent The pane of the graph node on which to paint on
	 * @param type The type of this node
	 * @param factory The view factory used to produce the graphical representation of the connector
	 */
	Connector(GraphNode graphNode, final Pane parent, ConnectorType type, ConnectorFactory factory) {
		this.graphNode = graphNode;
		this.type = type;
		this.factory = factory;
	
		this.node = factory.newConnector(type);
		parent.getChildren().add(node);
		parent.fireEvent(new ConnectorEvent(ConnectorEvent.CONNECTOR_CREATED, this));
		
		
		// attach handler for mouse pressed connection request events
		final Connector that = this;
		this.node.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				parent.fireEvent(new ConnectorEvent(ConnectorEvent.CONNECTOR_SELECTED, that));
			}
		});
		
		this.node.setOnMouseEntered(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				parent.fireEvent(new ConnectorEvent(ConnectorEvent.CONNECTOR_HOVERED, that));
			}
		});
		
		this.node.setOnMouseExited(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				parent.fireEvent(new ConnectorEvent(ConnectorEvent.CONNECTOR_UNHOVERED, that));
			}
		});
	}

	/**
	 * Sets the connection on the connector.
	 * If the connection is set, the connection will return true for {@link Connector#isConnected()}
	 * @param c The connection this connector will hold
	 */
	void setConnection(Connection c) {
		this.connection = c;
	}
	
	/**
	 * Returns the connection this connector holds or null if the connector is not connected.
	 * @return The connection or null
	 */
	Connection getConnection() {
		return this.connection;
	}

	/**
	 * Returns whether or not this connector is connected to another connector
	 * @return True, if the connector is connected, false otherwise
	 */
	public boolean isConnected() {
		return this.connection != null;
	}
	
	/**
	 * Adjusts the position of the connector within the {@link GrapNode}'s graphical representation.
	 * the provided point is considered to be relative to the parents bounding box
	 * @param pos The position where to apply the graph
	 */
	void adjustPosition(Point2D pos) {
		this.node.relocate(pos.getX(), pos.getY());
	}

	/**
	 * Delegates the call to the underlying factory
	 */
	void onConnectionRequestCancelled() {
		if (this.connection == null)
			node = factory.onConnectionRequestCancelled(this.node);
	}

	/**
	 * Delegates the onConnectionRequested event to the underlying factory
	 * @param isSource Whether the connector is the source of the connection
	 * @param acceptorResult Whether or not an acceptor would accept the connection.
	 */
	void onConnectionRequested(boolean isSource, boolean acceptorResult) {
		node = factory.onConnectionRequested(this.node, acceptorResult, isSource);
	}
	
	/**
	 * Delegates to the underlying factory
	 */
	void onConnectionAccepted() {
		node = factory.onConnectionAccepted(this.node);
	}
	
	/**
	 * Returns the relative position of the connector within the parents bounding box
	 * @return The coordinates of the connector within it's parent
	 */
	Point2D getRelativePosition() {
		return new Point2D(node.getLayoutX(), node.getLayoutY());
	}
	
	
	void dispose() {
		if (this.connection != null)
			this.connection.dispose();
	}
	
	/**
	 * Returns the {@link GraphNode} that is parent of this connector
	 * @return The GraphNode
	 */
	public GraphNode getNode() {
		return this.graphNode;
	}

	/**
	 * Returns the type of this connector.
	 * @return The type of the connector
	 */
	public ConnectorType getType() {
		return this.type;
	}
}