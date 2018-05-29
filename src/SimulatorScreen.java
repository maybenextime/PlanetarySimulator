import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class SimulatorScreen extends JPanel implements Runnable {
    double x1;
    double y1;

    zoom zoom = new zoom(5000, 5000, 1);
    boolean setTime = false;

    private List<String> namePlanet = new ArrayList<String>() {
        {
            add("sun");
            add("earth");
            add("jupiter");
            add("mar");
            add("mercury");
            add("venus");
        }
    };

    ArrayList<Planet> planet;
    private BufferedImage space;
    boolean isRunning = true;
    boolean CenterPlanet = true;

    SimulatorScreen() {
        this.addMouseListener(new Mouse());
        planet = new ArrayList<>();
        Thread thread = new Thread(this);
        thread.start();
    }

    public void run() {
        while (true) {
            if (setTime) {
                for (Planet aPlanet : planet) aPlanet.update1();
                setTime = false;
            }
            if (this.planet.size() > 1) {
                if ((this.planet.get(1).getTimeStart() == this.planet.get(1).getTimeEnd()) && (this.planet.get(1).getTimeSE() == this.planet.get(1).getTimeSEReallity())) {
                    isRunning = false;
                    this.planet.get(1).setTimeSE(-1);
                    this.planet.get(1).setTimeEnd(-1);
                }
            }
            if (isRunning)
                for (Planet aPlanet : planet)
                    if (aPlanet.Center) aPlanet.updatePlanetCenter();
                    else aPlanet.updatePlanet();
            removeAll();
            repaint();

            try {
                sleep(20);
            } catch (InterruptedException ignored) {
            }
        }
    }

    private void paintBG(Graphics g) {
        try {
            space = ImageIO.read(getClass().getResourceAsStream("space.GIF"));
        } catch (IOException ignored) {
        }
        g.drawImage(space, 0, 0, (int) (10000 * zoom.getZoomNumb()), (int) (10000 * zoom.getZoomNumb()), null);
    }

    private void paintInfo(Graphics g) {
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 15));
        g.drawString("Press F1 to show the Option", 0, 12);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.setColor(Color.GREEN);
        if (CenterPlanet) g.drawString("Creat Center Planet", 400, 40);
        else g.drawString("Creat A Planet", 400, 40);
        g.setColor(Color.gray);
    }

    private void paintPlanet(Graphics g) {
        if (planet.size() >= 1)
            for (int i = planet.size() - 1; i >= 0; i--) planet.get(i).drawPlanet(g);
    }

    public void paint(Graphics g) {
        paintBG(g);
        paintInfo(g);
        paintPlanet(g);
    }

    private class Mouse implements MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                boolean b = isRunning;
                isRunning = false;
                String name = JOptionPane.showInputDialog(SimulatorScreen.this, "Enter Name");
                if (name != null) {
                    if (CenterPlanet) {
                        if (!namePlanet.contains(name.toLowerCase())) name = "sun";
                        x1 = (int) (e.getX() / zoom.getZoomNumb());
                        y1 = (int) (e.getY() / zoom.getZoomNumb());
                        planet.add(new Planet(name, (int) x1, (int) y1, CenterPlanet, x1, y1, x1, y1, getZoom()));
                        repaint();
                    } else {
                        if (!namePlanet.contains(name.toLowerCase())) name = "earth";
                        double x2 = Double.parseDouble(JOptionPane.showInputDialog(SimulatorScreen.this, "Enter x2"));
                        double y2 = Double.parseDouble(JOptionPane.showInputDialog(SimulatorScreen.this, "Enter y2"));
                        planet.add(new Planet(name, (int) (e.getX() / zoom.getZoomNumb()), (int) (e.getY() / zoom.getZoomNumb()), CenterPlanet, x1, y1, x2, y2, getZoom()));
                        System.out.println(e.getX());
                    }
                }
                isRunning = b;
            }
        }

        @Override
        public void mousePressed(MouseEvent e) {
        }

        @Override
        public void mouseReleased(MouseEvent e) {
        }

        @Override
        public void mouseEntered(MouseEvent e) {
        }

        @Override
        public void mouseExited(MouseEvent e) {
        }
    }
    zoom getZoom() {
        return zoom;   }


    void setZoom(zoom zoom) {
        this.zoom = zoom;
    }
}
