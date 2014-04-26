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

import de.artignition.fxplumber.model.Connection;

import javafx.event.Event;
import javafx.event.EventType;

public class ConnectionEvent extends Event {

	private static final long serialVersionUID = 8309301010791024665L;

	public static EventType<ConnectionEvent> CONNECTION_CREATED = new EventType<ConnectionEvent>(ANY, "CONNECTION_CREATED");
	public static EventType<ConnectionEvent> CONNECTION_REMOVED = new EventType<ConnectionEvent>(ANY, "CONNECTION_REMOVED");
	public static EventType<ConnectionEvent> CONNECTION_DENIED  = new EventType<ConnectionEvent>(ANY, "CONNECTION_DENIED");
	public static EventType<ConnectionEvent> CONNECTION_SELECTED = new EventType<ConnectionEvent>(ANY, "CONNECTION_SELECTED");
	public static EventType<ConnectionEvent> CONNECTION_UNSELECTED = new EventType<ConnectionEvent>(ANY, "CONNECTION_UNSELECTED");
	public static EventType<ConnectionEvent> CONNECTION_HOVERED = new EventType<ConnectionEvent>(ANY, "CONNECTION_HOVERED");
	public static EventType<ConnectionEvent> CONNECTION_UNHOVERED = new EventType<ConnectionEvent>(ANY, "CONNECTION_UNHOVERED");

	private Connection conn;
	
	public ConnectionEvent(EventType<? extends Event> type, Connection connection) {
		super(type);
		this.conn = connection;
	}

	public Connection getConnection() {
		return this.conn;
	}
}
