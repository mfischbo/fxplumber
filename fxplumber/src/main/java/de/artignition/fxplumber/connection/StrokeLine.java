package de.artignition.fxplumber.connection;

import de.artignition.fxplumber.model.Connection;
import de.artignition.fxplumber.view.StartAndEndAwareShape;

import javafx.geometry.Point2D;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.transform.Rotate;

public class StrokeLine extends StartAndEndAwareShape {

	private Line line;
	
	private Connection c;
	
	public StrokeLine(Connection c) {
		this.line = new Line();
		this.c = c;

	
		this.line.setStartX(0);
		this.line.setStartY(7.5);
		this.line.setEndY(7.5);
		this.setPrefHeight(15.d);
		this.getChildren().add(line);
		
		Point2D initStart = c.getSource().getGraphNode().getPointByConnector(c.getSource());
		Point2D initEnd   = c.getTarget().getGraphNode().getPointByConnector(c.getTarget());
		
		setStart(initStart);
		setEnd(initEnd);
	}
	
	public void setStroke(Paint p) {
		this.line.setStroke(p);
	}
	
	@Override
	public Point2D getStart() {
		return new Point2D(this.getLayoutX(), this.getLayoutY() - 7.5d);
	}

	@Override
	public Point2D getEnd() {
		return new Point2D(this.getLayoutX() + this.getWidth(), this.getLayoutY() + this.getHeight() - 7.5d);
	}

	@Override
	public void setStart(Point2D start) {

		Point2D endpoint = c.getTarget().getGraphNode().getPointByConnector(c.getTarget());
		double rot = Math.toDegrees(Math.atan2(endpoint.getY() - start.getY(), endpoint.getX() - start.getX()));
		if (rot < 0)
			rot += 360;
		
		double bw = start.distance(endpoint);
		this.setPrefWidth(bw);
		this.setMaxWidth(bw);
		this.relocate(start.getX(), start.getY() - 7.5d);
		
		if (this.getTransforms().size() > 0)
			this.getTransforms().remove(0);
		
		this.getTransforms().add(new Rotate(rot, 0, 7.5));
		this.line.setEndX(bw);

	}

	
	
	@Override
	public void setEnd(Point2D end) {

		Point2D startpoint = c.getSource().getGraphNode().getPointByConnector(c.getSource());
		
		double rot = Math.toDegrees(Math.atan2(end.getY() - startpoint.getY(), end.getX() - startpoint.getX()));
		if (rot < 0)
			rot += 360;
		
		double bw = end.distance(startpoint);
		this.setPrefWidth(bw);
		this.setMaxWidth(bw);
		
		if (this.getTransforms().size() > 0)
			this.getTransforms().remove(0);
	
		this.getTransforms().add(new Rotate(rot, 0, 7.5));
		this.line.setEndX(bw);

	}
}
