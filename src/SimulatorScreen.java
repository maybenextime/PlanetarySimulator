import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;


public class SimulatorScreen extends JPanel implements Runnable {
    double x1;
    double y1;
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
        g.drawImage(space, 0, 0, 1000, 1000, null);
        g.setColor(Color.white);
        g.setFont(new Font("Serif", Font.BOLD, 15));
        g.drawString("Press F1 to show the Option", 0, 12);
        g.setFont(new Font("Serif", Font.BOLD, 25));
        g.setColor(Color.GREEN);
        if (CenterPlanet) g.drawString("Creat Center Planet", 400, 40);
        else g.drawString("Creat A Planet", 400, 40);
        g.setColor(Color.gray);

    }

    public void paint(Graphics g) {
        paintBG(g);
        if (planet.size() >= 1)
            for (int i = planet.size() - 1; i >= 0; i--) planet.get(i).DrawPlanet(g);
    }

    private class Mouse implements MouseListener, MouseMotionListener {


        @Override
        public void mouseClicked(MouseEvent e) {
            if (e.getClickCount() == 2) {
                boolean b = isRunning;
                isRunning = false;
                String name = JOptionPane.showInputDialog(SimulatorScreen.this, "Enter Name");
                if (name != null) {
                    if (CenterPlanet) {
                        if (!namePlanet.contains(name.toLowerCase())) name = "sun";
                        x1 = e.getX();
                        y1 = e.getY();
                        planet.add(new Planet(name, e.getX(), e.getY(), CenterPlanet, x1, y1, x1, y1, 0.0));
                    } else {
                        if (!namePlanet.contains(name.toLowerCase())) name = "earth";
                        double x2 = Double.parseDouble(JOptionPane.showInputDialog(SimulatorScreen.this, "Enter x2"));
                        double y2 = Double.parseDouble(JOptionPane.showInputDialog(SimulatorScreen.this, "Enter y2"));
                        double cycle = Double.parseDouble(JOptionPane.showInputDialog(SimulatorScreen.this, "Enter cycle"));
                        planet.add(new Planet(name, e.getX(), e.getY(), CenterPlanet, x1, y1, x2, y2, cycle * 1000));
                    }

                }
                isRunning = b;

            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {

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

}
