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

import de.artignition.fxplumber.event.ConnectionEvent;
import de.artignition.fxplumber.event.ConnectorEvent;
import de.artignition.fxplumber.event.NodeEvent;
import de.artignition.fxplumber.util.ConnectionAcceptor;
import de.artignition.fxplumber.util.DefaultConnectionAcceptor;
import de.artignition.fxplumber.view.StartAndEndAwareShape;
import de.artignition.fxplumber.view.ViewFactory;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Represents a Graph that can hold nodes and edges.
 * This is the class you should use in order to build up your graph.
 * @author M. Fischboeck
 *
 */
public class Graph {

	/* The root pane we are painting on */
	protected Pane				canvas;
	
	/* The set of nodes that this graph contains */
	protected Set<GraphNode>	nodes;
	
	/* The view factory used to produce the graphs visible elements */
	protected ViewFactory		viewFactory;

	/* Holds the source connector when a new connection is about to be created */
	protected Connector			connRqSource = null;
	
	/* Holds the line when a new connection is about to be craeted */
	protected StartAndEndAwareShape connRqLine   = null;
	
	protected Logger			log = LoggerFactory.getLogger(getClass());

	protected ConnectionAcceptor	acceptor = new DefaultConnectionAcceptor();
	
	/**
	 * Constructor providing the {@link javafx.scene.layout.Pane} to draw on.
	 * Any element created in this graph will have the build in default view.
	 * @param canvas The pane to draw on.
	 */
	public Graph(final Pane canvas) {
		this.canvas = canvas;
		this.nodes  = new LinkedHashSet<GraphNode>();
		this.viewFactory = new ViewFactory();
		initialize();
	}

	/**
	 * Constructor providing the {@link javafx.scene.layout.Pane} to draw on and a factory for the graphs elements.
	 * @param canvas The canvas to draw on
	 * @param factory The factory used to create the graphs elements
	 */
	public Graph(final Pane canvas, ViewFactory factory) {
		this.canvas = canvas;
		this.nodes = new LinkedHashSet<GraphNode>();
		this.viewFactory = factory;
		initialize();
	}
	
	
	private void initialize() {
		log.debug("Initializing new Graph");
		
		// register handler for connection events
		this.canvas.addEventHandler(ConnectorEvent.CONNECTOR_SELECTED, new EventHandler<ConnectorEvent>() {
			public void handle(ConnectorEvent e) {

				// if the line is null, we create a new one at the given point
				if (connRqLine == null) {
					log.debug("Requesting connection from source connector: " + e.getConnector());
					connRqSource = e.getConnector();
					
					GraphNode sn = e.getConnector().getGraphNode();
					Point2D  sp = sn.getPointByConnector(e.getConnector());
				
					connRqLine = viewFactory.getConnectionFactory().getConnectionRequestNode(e.getConnector());
					connRqLine.setStart(new Point2D(sp.getX(), sp.getY()));
					connRqLine.setEnd(new Point2D(sp.getX()-4, sp.getY()-4));
					canvas.getChildren().add(connRqLine);
				} else {
					// otherwise we create a new connection between connRqSource and the current connector
					log.debug("Targeting connection on connector : " + e.getConnector());
					
					if (acceptor != null) {
						if (acceptor.accepts(connRqSource, e.getConnector()))
							createConnection(connRqSource, e.getConnector());
						else {
							connRqSource.onConnectionRequestCancelled();
							e.getConnector().onConnectionRequestCancelled();
							canvas.fireEvent(new ConnectionEvent(ConnectionEvent.CONNECTION_DENIED, null));
						}
					} else if (acceptor == null) 
						createConnection(connRqSource, e.getConnector());
					
					// remove the line
					canvas.getChildren().remove(connRqLine);
					connRqSource = null;
					connRqLine   = null;
				}
				e.consume();
			}
		});
		
		this.canvas.addEventHandler(ConnectorEvent.CONNECTOR_HOVERED, new EventHandler<ConnectorEvent>() {
			@Override
			public void handle(ConnectorEvent e) {
				if (connRqLine != null) {
					Connector target = e.getConnector();
					boolean aRes = acceptor.accepts(connRqSource, target);
					connRqSource.onConnectionRequested(true, aRes);
					target.onConnectionRequested(false, aRes);
				}
			}
		});
		
		this.canvas.addEventHandler(ConnectorEvent.CONNECTOR_UNHOVERED, new EventHandler<ConnectorEvent>() {
			@Override
			public void handle(ConnectorEvent arg0) {
				if (connRqSource != null) 
					connRqSource.onConnectionRequestCancelled();
				arg0.getConnector().onConnectionRequestCancelled();
			}
		});
		
	
		// register click handler for right click
		this.canvas.setOnMouseClicked(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent e) {
				if (e.getButton() == MouseButton.SECONDARY && connRqLine != null) {
					log.debug("Cancel creation of connection.");
					canvas.getChildren().remove(connRqLine);
					connRqLine = null;
				}
			}
		});
		
		// register mouse move handler
		this.canvas.setOnMouseMoved(new EventHandler<MouseEvent>() {
			public void handle(MouseEvent arg0) {
				if (connRqLine != null) {
					connRqLine.setEnd(new Point2D(arg0.getX()-4, arg0.getY()-4));
				}
			}
		});
	}

	/**
	 * Returns an unmodifiable set containing all nodes of this graph.
	 * To add and remove nodes, use either {@link #createNode(double, double)} or {@link #removeNode(GraphNode)} 
	 * @return The set of nodes, held by this graph
	 */
	public Set<GraphNode> getNodes() {
		return Collections.unmodifiableSet(this.nodes);
	}

	/**
	 * Sets the {@link de.artignition.fxplumber.util.ConnectionAcceptor} to be queried before a connection attempt.
	 * @param acceptor The acceptor to be used
	 */
	public void setConnectionAcceptor(ConnectionAcceptor acceptor) {
		this.acceptor = acceptor;
	}

	/**
	 * Returns an unmodifiable set of connections hold by this graph 
	 * @return The set of connections 
	 */
	public Set<Connection> getConnections() {
		Set<Connection> retval = new LinkedHashSet<Connection>();
		for (GraphNode g : nodes) {
			retval.addAll(g.getConnections());
		}
		return Collections.unmodifiableSet(retval);
	}

	/**
	 * Creates a new node on the given position
	 * @param x The x coordinate where to create the node
	 * @param y The y coordinate where to create the node
	 * @return The created {@link GraphNode}
	 */
	public GraphNode createNode(double x, double y) {
		final GraphNode n = new GraphNode(new Point2D(x, y), canvas, this.viewFactory.getNodeFactory());
		this.nodes.add(n);
		canvas.fireEvent(new NodeEvent(NodeEvent.NODE_CREATED, n));
		return n;
	}

	/**
	 * Removes the provided node from the graph.
	 * If the node is connected to any other {@link GraphNode}'s, the connections will be removed.
	 * Upon success a {@link de.artignition.fxplumber.event.NodeEvent} with type 'NODE_REMOVED' is fired.
	 * @param n The node to be removed from the graph.
	 */
	public void removeNode(GraphNode n) {
		if (this.nodes.contains(n)) {
			n.dispose();
			this.nodes.remove(n);
			this.canvas.getChildren().remove(n);
			canvas.fireEvent(new NodeEvent(NodeEvent.NODE_REMOVED, n));
		}
	}

	/**
	 * Creates a {@link Connection} between the provided connectors.
	 * If a connection already exists, the given connection will be returned.
	 * @param source The source {@link Connector}
	 * @param target The target {@link Connector}
	 * @return Either a newly created {@link Connection} or, if one already exists, the existing {@link Connection}
	 */
	public Connection connect(Connector source, Connector target) {
		return createConnection(source, target);
	}

	/**
	 * Removes the {@link Connection} from the graph.
	 * @param conn The connection to be removed.
	 */
	public void disconnect(Connection conn) {
		conn.dispose();
		canvas.fireEvent(new ConnectionEvent(ConnectionEvent.CONNECTION_REMOVED, conn));
	}
	
	
	private Connection createConnection(Connector source, Connector target) {
		Connection c = new Connection(source, target, this.viewFactory.getConnectionFactory());
		this.canvas.getChildren().add(c.getConnectionNode());
		canvas.fireEvent(new ConnectionEvent(ConnectionEvent.CONNECTION_CREATED, c));
		return c;
	}
}