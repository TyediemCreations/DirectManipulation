

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

/**
 * A simple demo of how to create a rectangular sprite.
 * 
 * Written by: Michael Terry
 */
public class RectangleSprite extends Sprite {

    private RoundRectangle2D rect = null;

    /**
     * Creates a rectangle based at the origin with the specified
     * width and height
     */
    public RectangleSprite(int width, int height) {
        super(0,0,0);
        this.initialize(width, height);
    }
    /**
     * Creates a rectangle based at the origin with the specified
     * width, height, and parent (not currently used, as only the body
     * sprite is a Rectangle)
     */
    public RectangleSprite(int width, int height, Sprite parentSprite) {
        super(parentSprite,0,0,0);
        this.initialize(width, height);
    }
    
    private void initialize(int width, int height) {
        rect = new RoundRectangle2D.Double(0, 0, width, height,20,20);
    }
    
    /**
     * Test if our rectangle contains the point specified.
     */
    public boolean pointInside(Point2D p) {
        AffineTransform fullTransform = this.getFullTransform();
        AffineTransform inverseTransform = null;
        try {
            inverseTransform = fullTransform.createInverse();
        } catch (NoninvertibleTransformException e) {
            e.printStackTrace();
        }
        Point2D newPoint = (Point2D)p.clone();
        inverseTransform.transform(newPoint, newPoint);
        return rect.contains(newPoint);
    }

    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(rect);
    }
    
    public String toString() {
        return "RectangleSprite: " + rect;
    }
}
