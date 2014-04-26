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

package de.artignition.fxplumber.event;

import de.artignition.fxplumber.model.GraphNode;

import javafx.event.Event;
import javafx.event.EventType;

public class NodeEvent extends Event {

	private static final long serialVersionUID = -7853075712882135492L;
	
	public static final EventType<NodeEvent> NODE_CREATED = new EventType<NodeEvent>(ANY, "NODE_CREATED");
	public static final EventType<NodeEvent> NODE_REMOVED = new EventType<NodeEvent>(ANY, "NODE_REMOVED");
	public static final EventType<NodeEvent> NODE_SELECTED= new EventType<NodeEvent>(ANY, "NODE_SELECTED");
	
	private GraphNode	node;
	
	public NodeEvent(EventType<? extends Event> type, GraphNode node) {
		super(type);
		this.node = node;
	}

	public GraphNode getNode() {
		return this.node;
	}
}
