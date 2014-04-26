package de.artignition.fxplumber.view;

import javafx.geometry.Point2D;
import javafx.scene.layout.Pane;

public abstract class StartAndEndAwareShape extends Pane {

	public abstract Point2D	getStart();
	public abstract Point2D getEnd();
	public abstract void setStart(Point2D start);
	public abstract void setEnd(Point2D end);
}
