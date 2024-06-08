package UI.DrawingUtils;

import java.awt.geom.*;
import java.awt.*;

public class ANDShape implements Shape{
    double startX, startY, input_size;
    Path2D.Double path;
    double DISTANCE_BETWEEN_ROW = 25;
    double SIZE = 50;

    public ANDShape(double startX, double startY, double input_size) {
        this.startX = startX;
        this.startY = startY;
        this.input_size = input_size;
        path = new Path2D.Double();
        path.moveTo(startX+SIZE/2, startY);
        path.lineTo(startX, startY);
        path.lineTo(startX, startY+input_size*DISTANCE_BETWEEN_ROW);
        path.lineTo(startX+SIZE/2, startY+input_size*DISTANCE_BETWEEN_ROW);
        path.curveTo(startX+SIZE*1.167d, startY+input_size*DISTANCE_BETWEEN_ROW, startX+SIZE*1.167d, startY, startX+SIZE/2, startY);
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