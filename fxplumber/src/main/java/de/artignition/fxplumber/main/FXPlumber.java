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

package de.artignition.fxplumber.main;

import de.artignition.fxplumber.event.ConnectionEvent;
import de.artignition.fxplumber.model.Connection;
import de.artignition.fxplumber.model.Connector;
import de.artignition.fxplumber.model.Connector.ConnectorType;
import de.artignition.fxplumber.model.Graph;
import de.artignition.fxplumber.model.GraphNode;
import de.artignition.fxplumber.view.DefaultConnectorFactory;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class FXPlumber extends Application {

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {

		Pane root = new Pane();
		root.setPrefSize(800.d, 600.d);
	
		Scene scene = new Scene(root, 800.d, 600.d);
		stage.setScene(scene);
		stage.show();
	
		
		Graph g = new Graph(root);
		GraphNode n = g.createNode(200, 200);
		DefaultConnectorFactory cFact = new DefaultConnectorFactory();
		
		n.addConnector(ConnectorType.INPUT, cFact);
		n.addConnector(ConnectorType.OUTPUT, cFact);
	
		GraphNode n2 = g.createNode(400, 200);
		n2.addConnector(ConnectorType.INPUT, cFact);
		n2.addConnector(ConnectorType.OUTPUT, cFact);
		n2.addConnector(ConnectorType.OUTPUT, cFact);
		
		GraphNode n3 = g.createNode(500, 400);
		n3.addConnector(ConnectorType.INPUT, cFact);
		
		root.addEventHandler(ConnectionEvent.CONNECTION_CREATED, new EventHandler<ConnectionEvent>() {
			@Override
			public void handle(ConnectionEvent e) {
				Connection c = e.getConnection();
				Connector con = c.getSource();
			}
		});
	}
}
