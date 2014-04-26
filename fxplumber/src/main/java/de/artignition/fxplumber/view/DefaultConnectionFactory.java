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

import de.artignition.fxplumber.model.GraphNode;
import de.artignition.fxplumber.model.Connector;

import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.shape.Line;

public class DefaultConnectionFactory implements ConnectionFactory {

	public Node getConnectionNode(Connector source, Connector target) {
		GraphNode sourceNode = ((GraphNode) source.getNode());
		GraphNode targetNode = ((GraphNode) target.getNode());
		
		Point2D s = sourceNode.getPointByConnector(source);
		Point2D e = targetNode.getPointByConnector(target);
		
		Line l = new Line();
		l.setStartX(s.getX());
		l.setStartY(s.getY());
		l.setEndX(e.getX());
		l.setEndY(e.getY());
		return l;
	}

}
