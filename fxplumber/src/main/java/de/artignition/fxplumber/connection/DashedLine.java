package de.artignition.fxplumber.connection;

import de.artignition.fxplumber.view.StartAndEndAwareShape;

import javafx.geometry.Point2D;
import javafx.scene.shape.Line;

public class DashedLine extends StartAndEndAwareShape {

	private Line line;
	
	public DashedLine() {
		this.line = new Line();
		line.getStrokeDashArray().addAll(2d);
		this.getChildren().add(line);
	}
	
	
	@Override
	public Point2D getStart() {
		return new Point2D(line.getStartX(), line.getStartY());
	}

	@Override
	public Point2D getEnd() {
		return new Point2D(line.getEndX(), line.getEndY());
	}

	@Override
	public void setStart(Point2D start) {
		this.line.setStartX(start.getX());
		this.line.setStartY(start.getY());
	}

	@Override
	public void setEnd(Point2D end) {
		this.line.setEndX(end.getX());
		this.line.setEndY(end.getY());
	}
}
