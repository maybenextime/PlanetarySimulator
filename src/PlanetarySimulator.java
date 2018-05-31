import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlanetarySimulator extends JFrame {
    private SimulatorScreen screen;
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

    private PlanetarySimulator() {
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(1000, 1000);
        setLocationRelativeTo(this);
        screen = new SimulatorScreen();
        screen.setSize((int) (10000 * screen.zoom), (int) (10000 * screen.zoom));
        screen.setLocation(0, 0);
        screen.addMouseWheelListener(e -> {
            if (e.isControlDown()) {
                double oldZoom = screen.getZoom();
                double amount = 0.05;
                if ((e.getWheelRotation() > 0) && (screen.zoom > 0.1)) {
                    screen.setZoom(oldZoom - amount);

                }
                if ((e.getWheelRotation() < 0) && (screen.zoom < 2)) {
                    screen.setZoom(oldZoom + amount);

                }
                if (screen.planet.size() >= 1)
                    for (int i = 0; i < screen.planet.size(); i++) screen.planet.get(i).zoom = screen.getZoom();

                screen.setSize((int) (10000 * screen.zoom), (int) (10000 * screen.zoom));
                screen.setLocation((int) (-e.getXOnScreen()/2+screen.getLocation().getX()),(int) (-e.getYOnScreen()/2+screen.getLocation().getY()));
               System.out.println(e.getX());
               //System.out.println(oldZoom);
              //  System.out.println(screen.zoom.getZoomNumb());
                System.out.println(screen.getLocation().getX());
                System.out.println(screen.getSize().getWidth());
                if(screen.getLocation().getX()>0) screen.setLocation(0, (int) screen.getLocation().getY());
                if(screen.getLocation().getY()>0) screen.setLocation((int) screen.getLocation().getX() , 0);
                if(screen.getSize().getHeight()+screen.getLocation().getY()<1000) screen.setLocation((int) screen.getLocation().getX(),(int)(1000-screen.getSize().getHeight()));
                if(screen.getSize().getWidth()+screen.getLocation().getX()<1000) screen.setLocation( (int)(1000-screen.getSize().getWidth()), (int) screen.getLocation().getY());


            }
        });

        JPanel back = new JPanel();
        back.setSize(getSize());
        back.setBackground(Color.WHITE);
        back.setLayout(null);
        back.setLocation(0, 0);
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setSize(getSize());
        back.add(screen);
        add(back);


        moverment mv = new moverment(back.getComponents());
        this.addKeyListener(new Hander());
        setVisible(true);
        try {
            PlanetarySimulator.this.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(ImageIO.read(getClass().getResourceAsStream("sunCursor.gif")), new Point(0, 0), "main"));
        } catch (IOException ignored) {
        }
    }

    private void ShowKeyOptions() {
        JOptionPane.showMessageDialog(this, "Press SPACE to pause program\n\n Press F2 to switch Star/Center Planet\n\n Press F3 to select a specific time period\n\n Press F4( or Double Click) to creat a planet\n\n Press F5 to Refresh\n\n Hold Ctrl+ scroll Mouse to zoom in/out ");
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
                double time1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this, "Enter begin time(s)"));
                double time2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this, "Enter end time(s)"));
                for (int i = 0; i < PlanetarySimulator.this.screen.planet.size(); i++) {
                    PlanetarySimulator.this.screen.planet.get(i).setTimeStart((int) Math.round((time1 * 1000) % PlanetarySimulator.this.screen.planet.get(i).cycle));
                    PlanetarySimulator.this.screen.planet.get(i).setTimeEnd((int) Math.round((time2 * 1000) % PlanetarySimulator.this.screen.planet.get(i).cycle));
                    PlanetarySimulator.this.screen.planet.get(i).setTimeSE((int) Math.round(time2 / time1));
                }
                PlanetarySimulator.this.screen.setTime = true;
                screen.isRunning = b;

            }
            if (e.getKeyCode() == 115) {
                boolean b = screen.isRunning;
                screen.isRunning = false;
                String name = JOptionPane.showInputDialog(PlanetarySimulator.this, "Enter Name");
                if (name != null) {
                    if (screen.CenterPlanet) {
                        if (!namePlanet.contains(name.toLowerCase())) name = "sun";
                        screen.x1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this, "Enter x"));
                        screen.y1 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this, "Enter y"));
                        screen.planet.add(new Planet(name, (int) screen.x1, (int) screen.y1, screen.CenterPlanet, screen.x1, screen.y1, screen.x1, screen.y1, screen.getZoom()));

                        screen.setLocation((int) (-screen.x1*screen.zoom+500),(int) (-screen.y1*screen.zoom+500));
                        if(screen.getLocation().getX()>0) screen.setLocation(0, (int) screen.getLocation().getY());
                        if(screen.getLocation().getY()>0) screen.setLocation((int) screen.getLocation().getX() , 0);
                        if(screen.getSize().getHeight()+screen.getLocation().getY()<1000) screen.setLocation((int) screen.getLocation().getX(),(int)(1000-screen.getSize().getHeight()));
                        if(screen.getSize().getWidth()+screen.getLocation().getX()<1000) screen.setLocation( (int)(1000-screen.getSize().getWidth()), (int) screen.getLocation().getY());

                    } else {
                        if (!namePlanet.contains(name.toLowerCase())) name = "earth";
                        double x = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter x"));
                        double y = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter y"));
                        double x2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter x2"));
                        double y2 = Double.parseDouble(JOptionPane.showInputDialog(PlanetarySimulator.this.screen, "Enter y2"));
                        screen.planet.add(new Planet(name, (int) x, (int) y, screen.CenterPlanet, screen.x1, screen.y1, x2, y2, screen.getZoom()));
                        screen.setLocation((int) (-screen.x1*screen.zoom+500),(int) (-screen.y1*screen.zoom+500));
                        if(screen.getLocation().getX()>0) screen.setLocation(0, (int) screen.getLocation().getY());
                        if(screen.getLocation().getY()>0) screen.setLocation((int) screen.getLocation().getX() , 0);
                        if(screen.getSize().getHeight()+screen.getLocation().getY()<1000) screen.setLocation((int) screen.getLocation().getX(),(int)(1000-screen.getSize().getHeight()));
                        if(screen.getSize().getWidth()+screen.getLocation().getX()<1000) screen.setLocation( (int)(1000-screen.getSize().getWidth()), (int) screen.getLocation().getY());
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
