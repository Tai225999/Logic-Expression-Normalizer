package ui.DrawingUtils;

import java.awt.geom.*;
import java.awt.*;

public class ORShape implements Shape{
    double startX, startY, input_size;
    Path2D.Double path;
    double DISTANCE_BETWEEN_ROW = 25;
    double SIZE = 50;

    public ORShape(double startX, double startY, double input_size) {
        this.startX = startX;
        this.startY = startY;
        this.input_size = input_size;
        path = new Path2D.Double();
        path.moveTo(startX+SIZE, startY+input_size*DISTANCE_BETWEEN_ROW/2);
        path.curveTo(startX+SIZE*0.75, startY+input_size*DISTANCE_BETWEEN_ROW*0.25, startX+SIZE/2, startY+input_size*DISTANCE_BETWEEN_ROW*0.1, startX-SIZE*0.2, startY);
        path.curveTo(startX+SIZE*0.067, startY, startX+SIZE*0.067, startY+input_size*DISTANCE_BETWEEN_ROW, startX-SIZE*0.2, startY+input_size*DISTANCE_BETWEEN_ROW);
        path.curveTo(startX+SIZE/2, startY+input_size*DISTANCE_BETWEEN_ROW*0.9, startX+SIZE*0.75, startY+input_size*DISTANCE_BETWEEN_ROW*0.75, startX+SIZE, startY+input_size*DISTANCE_BETWEEN_ROW/2);

    }

    @Override
    public Rectangle getBounds() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Rectangle2D getBounds2D() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean contains(double x, double y) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Point2D p) {
        return this.contains(p.getX(), p.getY());
    }

    @Override
    public boolean intersects(double x, double y, double w, double h) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean intersects(Rectangle2D r) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(double x, double y, double w, double h) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Rectangle2D r) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at) {
        return path.getPathIterator(at);
    }

    @Override
    public PathIterator getPathIterator(AffineTransform at, double flatness) {
        return path.getPathIterator(at, flatness);
    }

}