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


import de.artignition.fxplumber.model.Connector;

import javafx.event.Event;
import javafx.event.EventType;

public class ConnectorEvent extends Event {
	
	private static final long serialVersionUID = 5921189390100419186L;

	public static final EventType<ConnectorEvent> CONNECTOR_CREATED = new EventType<ConnectorEvent>(ANY, "CONNECTOR_CREATED");
	public static final EventType<ConnectorEvent> CONNECTOR_SELECTED = new EventType<ConnectorEvent>(ANY, "CONNECTOR_SELECTED");
	public static final EventType<ConnectorEvent> CONNECTOR_HOVERED  = new EventType<ConnectorEvent>(ANY, "CONNECTOR_HOVERED");
	public static final EventType<ConnectorEvent> CONNECTOR_UNHOVERED = new EventType<ConnectorEvent>(ANY, "CONNECTOR_UNHOVERED");
	
	private Connector 	connector;
	
	public ConnectorEvent(EventType<? extends Event> type, Connector connector) {
		super(type);
		this.connector = connector;
	}
	
	public Connector getConnector() {
		return this.connector;
	}

}
