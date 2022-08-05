package main;

import java.awt.event.MouseEvent;

import javax.swing.event.MouseInputListener;

public class ClickHandler implements MouseInputListener{

    int posclicadax, posclicaday = 0;

    @Override
    public void mouseClicked(MouseEvent e) {
        posclicadax = e.getX();
        posclicaday = e.getY();
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // TODO Auto-generated method stub
        
    }


}