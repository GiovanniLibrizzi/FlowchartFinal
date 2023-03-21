import java.awt.*;

public class RectangleToolMethod extends RectangleTool{

    public RectangleToolMethod(int x1, int y1, Color color, String label) {
        super(x1, y1, color, label);
        xPoints = new int[]{x1-50, x1+50, x1+50, x1-50};
        yPoints = new int[]{y1-50, y1-50, y1+50, y1+50};
        shape = new Polygon(xPoints, yPoints, numPoints);
    }

    @Override
    public void relocate(int x, int y) {
        this.xPoints = new int[]{x-50, x+50, x+50, x-50};
        this.yPoints = new int[]{y-50, y-50, y+50, y+50};
        this.x1 = x;
        this.y1 = y;
    }

    public boolean checkClick(int x, int y) {
        Point p = new Point(x, y);
        shape = new Polygon(this.xPoints, this.yPoints, this.numPoints);
        return shape.contains(p);
    }


    @Override
    public void draw(Graphics g) {

        g.setColor(color);
        g.fillRect(x1-50, y1-50, 100, 100);

        g.setColor(Color.BLACK);

        if(label != null){
            g.drawString(label, x1+((100)/8),y1);
        }

        g.drawLine(x1-40, y1-50, x1-40, y1+50);
        g.drawLine(x1+40, y1-50, x1+40, y1+50);

    }
}
