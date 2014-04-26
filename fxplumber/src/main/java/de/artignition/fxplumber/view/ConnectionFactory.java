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

import de.artignition.fxplumber.model.Connection;
import de.artignition.fxplumber.model.Connector;

/**
 * Defines a set of methods that are called in order to define the graphical representation of the connection in different states of the application.
 * 
 * @author M. Fischboeck
 *
 */
public interface ConnectionFactory {

	
	public StartAndEndAwareShape getConnectionRequestNode(Connector source);
	
	
	/**
	 * Returns the {@link javafx.scene.Node} that represents the connection
	 * @param source The source {@link de.artignition.fxplumber.model.Connector} of this new connection.
	 * @param target The target 
	 * @return 
	 */
	public StartAndEndAwareShape getConnectionNode(Connection c);
	
	
	public void onConnectionSelected(StartAndEndAwareShape current);
	
	public void onConnectionUnselected(StartAndEndAwareShape current);
	
	public void onConnectionHovered(StartAndEndAwareShape current);
	
	public void onConnectionUnhovered(StartAndEndAwareShape current);
}
