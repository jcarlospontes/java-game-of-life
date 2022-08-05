package main;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JPanel;

import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{


    Bloco[][] matrizJogo = new Bloco[30][25]; //20x20
    boolean mapeado, despausado, gameState = false;

    final int originalTiteSize = 12; // 12
    final int scale = 3; // 3

    final int tileSize = originalTiteSize * scale;
    final int maxScreenCol = 31; //21
    final int maxScreenRow = 26; //21
    final int screenWidth = maxScreenCol * tileSize;
    final int screenHeight = maxScreenRow * tileSize;

    ClickHandler mouseH = new ClickHandler();
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;


    public void setGameState(Boolean state){
        this.gameState = state;
    }
    public boolean getGameState(){
        return this.gameState;
    }



    public GamePanel(){
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.blue);
        this.setDoubleBuffered(true);
        this.addMouseListener(mouseH);
        this.addKeyListener(keyH);
        this.setFocusable(true);
    }

    public void startGameThread(){
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run(){

        while(gameThread != null){
            update();
            if(getGameState()){
                try {
                    Thread.sleep(250);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            repaint();
        }

    }

    public void update(){
        if(keyH.pressed){
            if(getGameState()){
                setGameState(false);
                System.out.println("desligado");
            }
            else{
                setGameState(true);
                System.out.println("ligado");
            }
            keyH.pressed = false;
        }

    }


    public void paintComponent(Graphics g){

        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D)g;

        g2.setColor(Color.white);

        if(mapeado == false){
            int i = 0;
            int j = 0;
            for(int x = 2; x < (maxScreenCol-1)*(tileSize+2); x+=tileSize+2){
                for(int y = 2; y< (maxScreenRow-1)*(tileSize+2); y+=tileSize+2){
                    matrizJogo[i][j] = new Bloco();
                    matrizJogo[i][j].setX(x);
                    matrizJogo[i][j].setY(y);
                    g2.fillRect(x, y, tileSize, tileSize);
                    j++;
                }
                i++;
                j = 0;
            }
            mapeado = true;
        }
        else{
            if(getGameState()){
                Redor(matrizJogo);
                for(int x = 0; x < maxScreenCol-1; x++){
                    for(int y = 0; y< maxScreenRow-1; y++){
                        if(matrizJogo[x][y].isVivo() == true){
                            g2.setColor(Color.black);
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                        }
                        else{
                            g2.setColor(Color.white);
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                        }
                    }
                }
                NextRound(matrizJogo);
                g2.dispose();
            }
            else{
                for(int x = 0; x < maxScreenCol-1; x++){
                    for(int y = 0; y< maxScreenRow-1; y++){
                        if(((mouseH.posclicadax >= matrizJogo[x][y].getX()) && (mouseH.posclicadax <= matrizJogo[x][y].getX()+tileSize-1)) && ((mouseH.posclicaday >= matrizJogo[x][y].getY()) && (mouseH.posclicaday <= matrizJogo[x][y].getY()+tileSize-1))){
                            if(matrizJogo[x][y].isVivo() == false){
                                matrizJogo[x][y].setVivo(true);
                            }
                            else{
                                matrizJogo[x][y].setVivo(false);
                            }
                            mouseH.posclicadax = 0;
                            mouseH.posclicaday = 0;
                        }
                        if(matrizJogo[x][y].isVivo() == true){
                            g2.setColor(Color.black);
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                        }
                        else{
                            g2.setColor(Color.white);
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                        }
                    }
                }
            }
        }

        g2.dispose();

    }


    public void NextRound(Bloco[][] matriz){
        for(int x = 0; x < maxScreenCol-1; x++){
            for(int y = 0; y< maxScreenRow-1; y++){
                if(matriz[x][y].getFlag() != 0){
                    if(matriz[x][y].getFlag() == 1){
                        matriz[x][y].setVivo(false);
                    }
                    else{
                        matriz[x][y].setVivo(true);
                    }
                    matriz[x][y].setFlag(0);
                }
            }
        }
    }

    public void Redor(Bloco[][] matriz){
        for(int x = 0; x < maxScreenCol-1; x++){
            for(int y = 0; y< maxScreenRow-1; y++){
                int pt = 0;
                    try{
                        if(matriz[x-1][y-1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x-1][y].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x-1][y+1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x][y-1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x][y+1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x+1][y-1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x+1][y].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    try{
                        if(matriz[x+1][y+1].isVivo()){
                            pt +=1;
                        }
                    }
                    catch(Exception e){
                    }
                    if(matriz[x][y].isVivo()){
                        if((pt >3) || (pt <2)){
                            matriz[x][y].setFlag(1);
                        }
                    }
                    else{
                        if(pt == 3){
                            matriz[x][y].setFlag(2);  
                        }
                    }
            }
        }
    }

}