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

import de.artignition.fxplumber.connection.DashedLine;
import de.artignition.fxplumber.connection.StrokeLine;
import de.artignition.fxplumber.model.Connection;
import de.artignition.fxplumber.model.Connector;

import javafx.scene.paint.Color;

public class DefaultConnectionFactory implements ConnectionFactory {

	@Override
	public StartAndEndAwareShape getConnectionRequestNode(Connector source) {
		return new DashedLine();
	}

	
	@Override
	public StartAndEndAwareShape getConnectionNode(Connection c) {
		return new StrokeLine(c);
	}


	@Override
	public void onConnectionSelected(StartAndEndAwareShape current) {
		System.out.println("Connection selected");
		((StrokeLine) current).setStroke(Color.BLUE);
	}


	@Override
	public void onConnectionUnselected(StartAndEndAwareShape current) {
		System.out.println("Connection unselected");
		((StrokeLine) current).setStroke(Color.BLACK);
	}


	@Override
	public void onConnectionHovered(StartAndEndAwareShape current) {
		System.out.println("Connection hovered");
		((StrokeLine) current).setStroke(Color.CHOCOLATE);
	}


	@Override
	public void onConnectionUnhovered(StartAndEndAwareShape current) {
		System.out.println("Connection unhovered");
		((StrokeLine) current).setStroke(Color.BLACK);
	}
}
