import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class moverment implements MouseMotionListener, MouseListener {
    private int x, y;

    moverment(Component[] screen) {
        for (Component c : screen) {
            c.addMouseListener(this);
            c.addMouseMotionListener(this);
        }
    }
    @Override
    public void mouseDragged(MouseEvent e) {
        e.getComponent().setLocation((e.getX() + e.getComponent().getX()) - x, (e.getY() + e.getComponent().getY()) - y);
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        x = e.getX();
        y = e.getY();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getComponent().getX() > 0) e.getComponent().setLocation(0, (e.getY() + e.getComponent().getY()) - y);
        if (e.getComponent().getX() < -(e.getComponent().getWidth() - 1000))
            e.getComponent().setLocation(-(e.getComponent().getWidth() - 1000), (e.getY() + e.getComponent().getY()) - y);
        if (e.getComponent().getY() > 0) e.getComponent().setLocation((e.getX() + e.getComponent().getX()) - x, 0);
        if (e.getComponent().getY() < -(e.getComponent().getHeight() - 1000))
            e.getComponent().setLocation((e.getX() + e.getComponent().getX()) - x, -(e.getComponent().getHeight() - 1000));




    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }
}
