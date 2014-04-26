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

import java.util.ArrayList;
import java.util.List;

import de.artignition.fxplumber.model.Connector;
import de.artignition.fxplumber.model.Connector.ConnectorType;
import de.artignition.fxplumber.model.GraphNode;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DefaultGraphNodeFactory implements GraphNodeFactory {

	@Override
	public Pane createGraphNode() {
	
		Pane retval = new Pane();
		retval.setPrefSize(100.0d, 100.0d);
		
		Rectangle r = new Rectangle();
		r.setHeight(100.d);
		r.setWidth(100.d);
		r.setX(0);
		r.setY(0);
		r.setFill(Color.WHITE);
		r.setStroke(Color.BLACK);
		r.setStrokeWidth(1.0d);
		retval.getChildren().add(r);
		return retval;
	}

	@Override
	public Point2D adjustConnector(Pane nodePane, GraphNode g, Connector c) {
		// split by input / output connectors
		List<Connector> ins = new ArrayList<Connector>();
		List<Connector> outs = new ArrayList<Connector>();
		
		for (Connector con : g.getConnectors()) {
			if (con.getType() == ConnectorType.INPUT)
				ins.add(con);
			else
				outs.add(con);
		}
		
		if (c.getType() == ConnectorType.INPUT) {
			double x = -8.d;
			double y = (nodePane.getPrefHeight() / (ins.size() + 1)) * (ins.indexOf(c)+1) - 8.d;
			return new Point2D(x,y);
		} else {
			double x = nodePane.getPrefWidth() - 8.d;
			double f = (nodePane.getPrefHeight() / (outs.size() + 1));
			double y = f * (outs.indexOf(c) + 1);
			return new Point2D(x, y - 8.d);
		}
	}
}
