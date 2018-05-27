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
    private double c;
    double cycle;
    boolean Center;
    private int x;
    private double angle = 0;
    private double apha;
    private int y;
    private int firstx;
    private int firsty;
    int timeStart = 0;
    int timeEnd = -1;
    int timeSE = -1;
    int timeSEReallity = 0;
    private BufferedImage image;
    private String name;


    Planet(String name, int x, int y, boolean Center, double x1, double y1, double x2, double y2, double cycle) {
        this.firstx = x;
        this.firsty = y;
        this.name = name;
        this.Center = Center;
        this.x = firstx;
        this.y = y;
        this.x2 = x2;
        this.y2 = y2;
        this.x1 = x1;
        this.y1 = y1;
        this.cycle = cycle;
        this.a = (Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y - y1, 2)) + Math.sqrt(Math.pow(x - x2, 2) + Math.pow(y - y2, 2))) / 2;
        this.x0 = (x1 + x2) / 2;
        this.y0 = (y1 + y2) / 2;
        this.c = Math.sqrt(Math.pow(x1 - x0, 2) + Math.pow(y1 - y0, 2));
        this.b = Math.sqrt(a * a - c * c);
        this.apha = atan((y1 - y2) / (x1 - x2));
        if (-(firstx - x0) * sin(apha) + (firsty - y0) * cos(apha) > 0)
            this.angle = (Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a));
        else
            this.angle = -(Math.acos(((firstx - x0) * cos(apha) + (firsty - y0) * sin(apha)) / a));
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

    void DrawPlanet(Graphics g) {
        if (!Center) {
            try {
                image = ImageIO.read(getClass().getResourceAsStream(name + ".png"));
            } catch (IOException ignored) {
            }
            Graphics2D g2d = (Graphics2D) g;
            AffineTransform old = g2d.getTransform();
            g2d.rotate(apha, x0, y0);
            g2d.drawArc((int) (x0 - a), (int) (y0 - b), (int) a * 2, (int) (b * 2), 0, 360);
            g2d.setTransform(old);
            g.fillArc((int) (x2 - 5), (int) (y2 - 5), 10, 10, 0, 360);
            g.drawArc((int) (x0 - 5), (int) (y0 - 5), 10, 10, 0, 360);
            g.drawLine(x, y, (int) x1, (int) y1);
            g.drawLine(x, y, (int) x2, (int) y2);
            g.drawImage(image, x - 20, y - 20, 40, 40, null);

        } else {
            try {
                image = ImageIO.read(getClass().getResourceAsStream(name + ".png"));
            } catch (IOException ignored) {
            }
            g.drawImage(image, x - 40, y - 40, 80, 80, null);
            ((Graphics2D) g).scale(1, 1);

        }
    }

}
