

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D;

/*
	Essentially just the RectangleSprite code with minor alterations
*/
public class EllipseSprite extends Sprite {

    private Ellipse2D rect = null;

    /**
     * Creates an ellipse based at the origin with the specified
     * width and height
     */
    public EllipseSprite(int width, int height, int x, int y, int fullRotation) {
        super(x,y,fullRotation);
        this.initialize(width, height);
    }
    /*
     * Creates an ellipse based at the origin with the specified 
	width and height and initial length; 
	used for the upper and lower legs 
     */
    public EllipseSprite(int width, int height, int x, int y, int fullRotation, int length) {
        super(x,y,fullRotation,length);
        this.initialize(width, height);
    }
    /**
     * Creates an ellipse based at the origin with the specified
     * width, height, and parent
     */
    public EllipseSprite(int width, int height, Sprite parentSprite, int x, int y, int fullRotation) {
        super(parentSprite,x,y,fullRotation);
        this.initialize(width, height);
    }
    
    /**
     * Initializes the ellipse with width and height
     */
    private void initialize(int width, int height) {
        rect = new Ellipse2D.Double(0, 0, width, height);
    }
    
    /**
     * Test if our ellipse contains the point specified.
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

    
    /**
     * Draw the ellipse to canvas
     */
    protected void drawSprite(Graphics2D g) {
        g.setColor(Color.BLACK);
        g.draw(rect);
    }
    
    /**
     * Ellipse's 'toString' method; used to identify in stack-trace if necessary
     */
    public String toString() {
        return "EllipseSprite: " + rect;
    }
}
