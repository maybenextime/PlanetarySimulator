import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class PlanetarySimulator extends JFrame {
    private SimulatorScreen screen;

    private PlanetarySimulator() {
        setSize(1000, 1000);
        this.setDefaultCloseOperation(3);
        screen = new SimulatorScreen();
        add(screen);
        this.addKeyListener(new Hander());
        setVisible(true);
        try {
            PlanetarySimulator.this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(getClass().getResourceAsStream("sunCursor.gif")), new Point(0, 0), "main"));
        } catch (IOException ignored) {
        }
    }

    private void ShowKeyOptions() {
        JOptionPane.showMessageDialog(this, "Press SPACE to pause program\n\n Press F2 to switch Star/Center Planet\n\n Press F3 to select a specific time period\n\n Press F4( or Double Click) to creat a planet\n\n Press F5 to Refresh ");
    }

    private class Hander implements KeyListener {
        BufferedImage image;

        @Override
        public void keyTyped(KeyEvent e) {

        }

        @Override
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == 112) {
                PlanetarySimulator.this.ShowKeyOptions();
            }
            if (e.getKeyCode() == 32) {
                screen.isRunning = !screen.isRunning;
            }
            if (e.getKeyCode() == 113) {
                screen.CenterPlanet = !screen.CenterPlanet;
                try {
                    if (screen.CenterPlanet) image = ImageIO.read(getClass().getResourceAsStream("sunCursor.gif"));
                    else image = ImageIO.read(getClass().getResourceAsStream("planetCursor.GIF"));
                } catch (IOException ignored) {
                }
                PlanetarySimulator.this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(image, new Point(0, 0), "main"));
            }
            if (e.getKeyCode() == 114) {
                boolean b = screen.isRunning;
                screen.isRunning = false;
                double time1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter begin time"));
                double time2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter end time"));
                for (int i = 0; i < PlanetarySimulator.this.screen.planet.size(); i++) {
                    PlanetarySimulator.this.screen.planet.get(i).timeStart = time1 * 1000;
                    PlanetarySimulator.this.screen.planet.get(i).timeEnd = time2 * 1000;
                }
                PlanetarySimulator.this.screen.setTime = true;
                screen.isRunning = b;

            }
            if (e.getKeyCode() == 115) {
                boolean b = screen.isRunning;
                screen.isRunning = false;
                String name = JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter Name");
                if (name != null) {
                    if (screen.CenterPlanet) {
                        screen.x1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter x")) - 20;
                        screen.y1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter y")) - 20;
                        screen.planet.add(new Planet(name, (int) screen.x1, (int) screen.y1, screen.CenterPlanet, screen.x1, screen.y1, screen.x1, screen.y1, 0.0));
                    } else {
                        double x = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter x")) - 40;
                        double y = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter y")) - 40;
                        double x2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter x2"));
                        double y2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter y2"));
                        double cycle = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter cycle"));

                        screen.planet.add(new Planet(name, (int) x, (int) y, screen.CenterPlanet, screen.x1, screen.y1, x2, y2, cycle * 1000));
                    }
                }
                screen.isRunning = b;
            }
            if (e.getKeyCode() == 116) {
                screen.planet = new ArrayList<>();
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
        }
    }


    public static void main(String[] args) {
        PlanetarySimulator f = new PlanetarySimulator();
    }
}
