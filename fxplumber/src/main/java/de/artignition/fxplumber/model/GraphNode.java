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

import de.artignition.fxplumber.event.NodeEvent;
import de.artignition.fxplumber.model.Connector.ConnectorType;
import de.artignition.fxplumber.view.ConnectorFactory;
import de.artignition.fxplumber.view.GraphNodePaneFactory;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;


public class GraphNode {

	private Set<Connector> 		ports;
	private Pane				nodePane;
	private GraphNodePaneFactory factory;
	
	GraphNode(double x, double y, final Pane canvas, GraphNodePaneFactory factory) {

		this.nodePane = factory.createGraphNode();
		this.nodePane.relocate(x, y);
		this.factory = factory;
		
		canvas.getChildren().add(this.nodePane);
		
		this.ports = new LinkedHashSet<Connector>();
		
	
		final GraphNode that = this;
		
		// fire node selected event 
		this.nodePane.setOnMousePressed(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				canvas.fireEvent(new NodeEvent(NodeEvent.NODE_SELECTED, that));
			}
		});
	
		// handle mouse drag on node
		this.nodePane.setOnMouseDragged(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				double x = e.getSceneX();// + e.getX();
				double y = e.getSceneY();// + e.getY();
				nodePane.relocate(x, y);
		
				for (Connector c : ports) {
					if (c.isConnected()) {
						Line l = (Line) c.getConnection().getConnectionNode();
						if (c.getConnection().getSource().getNode() == that) {
					
							l.setStartX(that.getPointByConnector(c).getX());
							l.setStartY(that.getPointByConnector(c).getY());
						
						} else if (c.getConnection().getTarget().getNode() == that) {
					
							l.setEndX(that.getPointByConnector(c).getX());
							l.setEndY(that.getPointByConnector(c).getY());
						}
					}
				}
			}
		});
	}
	

	/**
	 * Adds a new {@link Connector} to this node.
	 * @param type The type of the connector
	 * @param factory The factory that provides the actual graphical representation of the connector
	 * @return The created connector
	 */
	public Connector addConnector(ConnectorType type, ConnectorFactory factory) {
		Connector c = new Connector(this, this.nodePane, type, factory);
		this.ports.add(c);
		for (Connector con : ports) {
			Point2D pos = this.factory.adjustConnector(this.nodePane, this, con);
			c.adjustPosition(pos);
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

	
	void dispose() {
		// search each connector for connections
		for (Connector c : ports) {
			if (c.isConnected())
				c.setConnection(null);
		}
		this.ports = null;
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
}
