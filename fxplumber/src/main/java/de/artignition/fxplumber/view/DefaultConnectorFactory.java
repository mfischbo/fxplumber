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

import de.artignition.fxplumber.model.Connector.ConnectorType;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * This class implements some defaults for a connector.
 * This implementation provides a simple red circle as connector. The circle will change it's color based on the
 * current state of the application. For instance upon a connection requests that is accepted it will change it's
 * color to lightgreen.
 * 
 * @author M. Fischboeck
 *
 */
public class DefaultConnectorFactory implements ConnectorFactory {


	/*
	 * (non-Javadoc)
	 * @see de.artignition.fxplumber.view.ConnectorFactory#newConnector(de.artignition.fxplumber.model.Connector.ConnectorType)
	 */
	@Override
	public Node newConnector(ConnectorType type) {
	
		Circle c = new Circle();
		c.setRadius(12.5d);
		c.setFill(Color.RED);
		c.setStroke(Color.BLACK);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * @see de.artignition.fxplumber.view.ConnectorFactory#onConnectionAccepted(javafx.scene.Node)
	 */
	@Override
	public Node onConnectionAccepted(Node current) {
	
		Circle c = (Circle) current;
		c.setFill(Color.GREEN);
		return c;
	}

	/*
	 * (non-Javadoc)
	 * @see de.artignition.fxplumber.view.ConnectorFactory#onConnectionRequested(javafx.scene.Node, boolean, boolean)
	 */
	@Override
	public Node onConnectionRequested(Node current, boolean acceptorAccepts, boolean isSource) {
		if (acceptorAccepts) {
			Circle c = (Circle) current;
			c.setFill(Color.LIGHTGREEN);
			return c;
		} else {
			Circle c = (Circle) current;
			c.setFill(Color.DARKRED);
			return c;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see de.artignition.fxplumber.view.ConnectorFactory#onConnectionRequestCancelled(javafx.scene.Node)
	 */
	@Override
	public Node onConnectionRequestCancelled(Node current) {
		Circle c = (Circle) current;
		c.setFill(Color.RED);
		return c;
	}
}
