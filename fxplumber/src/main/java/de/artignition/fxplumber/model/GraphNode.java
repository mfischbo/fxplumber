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

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

import de.artignition.fxplumber.event.NodeEvent;
import de.artignition.fxplumber.model.Connector.ConnectorType;
import de.artignition.fxplumber.view.ConnectorFactory;
import de.artignition.fxplumber.view.GraphNodeFactory;
import de.artignition.fxplumber.view.StartAndEndAwareShape;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.Cursor;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

/**
 * Represents a node within the {@link Graph}.
 * @author M. Fischboeck 
 *
 */
public class GraphNode {

	/* uuid for the hash */
	private UUID				id;
	
	/* The connector ports the graph node contains */
	private Set<Connector> 		ports;
	
	/* The pane we are drawing on */
	private Pane				nodePane;

	/* The factory that is used to produce the graphical representation of the node */
	private GraphNodeFactory 	factory;
	
	private double				mouseX;
	private double				mouseY;
	
	/**
	 * Creates a new instance on the given position on the provided canvas using the factory to create the graphical representation
	 * @param pos The coordinates where to create the node.
	 * @param canvas The canvas that holds the actual graph
	 * @param factory The factory that is used to create the graphical representation of the node
	 */
	GraphNode(Point2D pos, final Pane canvas, GraphNodeFactory factory) {
		this.id = UUID.randomUUID();
		this.nodePane = factory.createGraphNode();
		this.nodePane.relocate(pos.getX(), pos.getY());
		this.factory = factory;
		this.ports = new LinkedHashSet<Connector>();
		
		canvas.getChildren().add(this.nodePane);
		
		final GraphNode that = this;
		
		// fire node selected event 
		this.nodePane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				mouseX = arg0.getSceneX();
				mouseY = arg0.getSceneY();
				nodePane.setCursor(Cursor.HAND);
				canvas.fireEvent(new NodeEvent(NodeEvent.NODE_SELECTED, that));
			}
		});
	
		// handle mouse drag on node
		this.nodePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double x = e.getSceneX() - mouseX;// e.getX();
				double y = e.getSceneY() - mouseY;// e.getY();
				nodePane.relocate(nodePane.getLayoutX() + x, nodePane.getLayoutY() + y);
				mouseX = e.getSceneX();
				mouseY = e.getSceneY();
		
				for (Connector c : ports) {
					if (c.isConnected()) {
						StartAndEndAwareShape s = c.getConnection().getConnectionNode();
						
						if (c.getConnection().getSource().getGraphNode() == that) {
							s.setStart(that.getPointByConnector(c));
						
						} else if (c.getConnection().getTarget().getGraphNode() == that) {
							s.setEnd(that.getPointByConnector(c));
						}
					}
				}
			}
		});
		
		this.nodePane.setOnMouseReleased(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent arg0) {
				nodePane.setCursor(Cursor.DEFAULT);
			}
		});
	}
	

	/**
	 * Adds a new {@link Connector} to the node using the default factory for connectors.
	 * @param type The type of the connector
	 * @return The connector
	 */
	public Connector addConnector(ConnectorType type) {
		Connector c = new Connector(this, type);
		this.ports.add(c);
		for (Connector con : ports) {
			Point2D pos = this.factory.adjustConnector(this.nodePane, this, con);
			con.adjustPosition(pos);
		}
		return c;
	}

	
	/**
	 * Adds a new {@link Connector} to this node.
	 * @param type The type of the connector
	 * @param factory The factory that provides the actual graphical representation of the connector
	 * @return The created connector
	 */
	public Connector addConnector(ConnectorType type, ConnectorFactory factory) {
		Connector c = new Connector(this, type, factory);
		this.ports.add(c);
		for (Connector con : ports) {
			Point2D pos = this.factory.adjustConnector(this.nodePane, this, con);
			con.adjustPosition(pos);
		}
		return c;
	}
	

	/**
	 * Returns an unmodifiable set of the attached connectors.
	 * @return The set of connectors 
	 */
	public Set<Connector> getConnectors() {
		return Collections.unmodifiableSet(this.ports);
	}
	

	/**
	 * Returns an unmodifiable set of the connections attached to the connectors
	 * @return The set of connections on this node
	 */
	public Set<Connection> getConnections() {
		Set<Connection> retval = new LinkedHashSet<Connection>();
		for (Connector c : ports) {
			if (c.isConnected())
				retval.add(c.getConnection());
		}
		return Collections.unmodifiableSet(retval);
	}
	
	/**
	 * Returns the x coordinate of the top left corner of the nodes bounding box
	 * @return The x coordinate
	 */
	public double getX() {
		return nodePane.getLayoutX();
	}

	/**
	 * Returns the y coordinate of the top left corner of the nodes bounding box
	 * @return The y coordinate
	 */
	public double getY() {
		return nodePane.getLayoutY();
	}
	
	/**
	 * Destroys the GraphNode.
	 * When the node is destroyed, all related connectors and connections will be also destroyed
	 */
	void dispose() {
		// search each connector for connections
		for (Connector c : ports) {
			c.dispose();
		}
		this.ports = null;
	}
	
	
	Pane getPane() {
		return this.nodePane;
	}
	
	/**
	 * Returns the Point2D of the given connector in root relative coordinates
	 * @param c The connector to return the point 
	 * @return The {@link Point2D} or null if the connector is not a child of this node
	 */
	public Point2D getPointByConnector(Connector c) {
		if (!this.ports.contains(c))
			return null;
	
		Point2D rel = c.getRelativePosition();
		return new Point2D(rel.getX() + this.nodePane.getLayoutX(), rel.getY() + nodePane.getLayoutY());
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		GraphNode other = (GraphNode) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
}
