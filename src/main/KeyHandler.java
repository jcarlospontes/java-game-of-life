package main;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


public class KeyHandler implements KeyListener{

    boolean pressed = false;

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        int cd = e.getKeyCode();
        if(cd == KeyEvent.VK_SPACE){
            pressed = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    
    }
    
}
