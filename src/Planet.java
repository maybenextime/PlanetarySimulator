import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import static java.lang.Math.*;

class Planet {
    private double x0;
    private double x1;
    private double x2;
    private double y0;
    private double y1;
    private double y2;
    private double a;
    private double b;
    double cycle;
    boolean Center;
    int x;
    private double angle = 0;
    private double apha;
    int y;
    private int firstx;
    private int firsty;
    private int timeStart = 0;
    private int timeEnd = -1;
    private int timeSE = -1;
    private int timeSEReallity = 0;
    private String name;
    zoom zoom;


    Planet(String name, int x, int y, boolean Center, double x1, double y1, double x2, double y2, zoom zoom) {
        this.zoom = zoom;
        this.firstx = x;
        this.firsty = y;
        this.name = name;
        this.Center = Center;
        this.x = x;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.x1 = x1;
        this.y1 = y1;
        this.a = (Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2)) + Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2))) / 2;
        this.cycle = sqrt(a * a * a);
        this.x0 = (x1 + x2) / 2;
        this.y0 = (y1 + y2) / 2;
        double c = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
        this.b = Math.sqrt(a * a - c * c);
        this.apha = atan((y1 - y2) / (x1 - x2));
        if (-(firstx - x0) * sin(apha) + (firsty - y0) * cos(apha) > 0)
            this.angle = (Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a));
        else
            this.angle = -(Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a));
    }

    int getTimeStart() {
        return timeStart;
    }

    int getTimeEnd() {
        return timeEnd;
    }

    int getTimeSE() {
        return timeSE;
    }

    int getTimeSEReallity() {
        return timeSEReallity;
    }

    void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }

    void setTimeSE(int timeSE) {
        this.timeSE = timeSE;
    }

    void update1() {
        if (-(firstx - x0) * sin(apha) + (firsty - y0) * cos(apha) > 0)
            this.angle = ((Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a)) + this.timeStart + 40 * PI / cycle);
        else
            this.angle = (-(Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a)) + this.timeStart * 40 * PI / cycle);
    }

    void updatePlanet() {
        if (timeStart >= cycle) {
            timeStart = (int) (timeStart % cycle);
            if (timeSE < 0) timeSEReallity = 0;
            else timeSEReallity += 1;

        }
        timeStart += 20;
        this.angle = this.angle + 40 * PI / cycle;
        x = (int) (x0 + a * cos(angle) * cos(apha) - b * sin(angle) * sin(apha));
        y = (int) (y0 + a * cos(angle) * sin(apha) + b * sin(angle) * cos(apha));
    }

    void updatePlanetCenter() {
        x = (int) x1;
        y = (int) y1;
    }

    void drawPlanet(Graphics g) {
        BufferedImage image = null;
        try {
            image = ImageIO.read(getClass().getResourceAsStream(name + ".png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (!Center) {

            Graphics2D g2d = (Graphics2D) g;
            AffineTransform old = g2d.getTransform();
            g2d.rotate(apha, x0 * zoom.getZoomNumb(), y0 * zoom.getZoomNumb());
            g2d.drawArc((int) (((x0 - a)) * zoom.getZoomNumb()), (int) ((y0 - b) * zoom.getZoomNumb()), (int) (a * 2 * zoom.getZoomNumb()), (int) (b * 2 * zoom.getZoomNumb()), 0, 360);
            g2d.setTransform(old);
            g.fillArc((int) ((x2 - 5) * zoom.getZoomNumb()), (int) ((y2 - 5) * zoom.getZoomNumb()), (int) (10 * zoom.getZoomNumb()), (int) (10 * zoom.getZoomNumb()), 0, 360);
            g.drawArc((int) ((x0 - 5) * zoom.getZoomNumb()), (int) ((y0 - 5) * zoom.getZoomNumb()), (int) (10 * zoom.getZoomNumb()), (int) (10 * zoom.getZoomNumb()), 0, 360);
            g.drawLine((int) (x * zoom.getZoomNumb()), (int) (y * zoom.getZoomNumb()), (int) (x1 * zoom.getZoomNumb()), (int) (y1 * zoom.getZoomNumb()));
            g.drawLine((int) (x * zoom.getZoomNumb()), (int) (y * zoom.getZoomNumb()), (int) (x2 * zoom.getZoomNumb()), (int) (y2 * zoom.getZoomNumb()));
            g.drawImage(image, (int) ((x - 20) * zoom.getZoomNumb()), (int) ((y - 20) * zoom.getZoomNumb()), (int) (40 * zoom.getZoomNumb()), (int) (40 * zoom.getZoomNumb()), null);

        } else {
            g.drawImage(image, (int) ((x - 40) * zoom.getZoomNumb()), (int) ((y - 40) * zoom.getZoomNumb()), (int) (80 * zoom.getZoomNumb()), (int) (80 * zoom.getZoomNumb()), null);
            ((Graphics2D) g).scale(1, 1);

        }
    }

}
