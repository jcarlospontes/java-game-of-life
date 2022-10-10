package main;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.Image;
import java.util.Random;
import java.awt.Graphics;
import java.awt.Graphics2D;

public class GamePanel extends JPanel implements Runnable{
    
    boolean mapeado, despausado, gameState = false;

    final int originalTiteSize = 10; // 12
    final int scale = 3; // 3

    final int tileSize = originalTiteSize * scale;
    final int maxScreenCol = 20; //21
    final int maxScreenRow = 20; //21
    final int screenWidth = maxScreenCol * tileSize+500;
    final int screenHeight = maxScreenRow * tileSize;

    Bloco[][] matrizJogo = new Bloco[maxScreenRow][maxScreenCol]; //10x10
    int[][] matrizVisaoAsp = new int[maxScreenRow][maxScreenCol];

    ClickHandler mouseH = new ClickHandler();
    KeyHandler keyH = new KeyHandler();
    Thread gameThread;

    //imagens
    Image image = new ImageIcon("asp.png").getImage();
    Image image2 = new ImageIcon("energyball.png").getImage();

    JTextField texto = new JTextField();

    JButton botaoquant = new JButton("Espalhar");

    Random rand = new Random();



    public void setGameState(Boolean state){
        this.gameState = state;
    }
    public boolean getGameState(){
        return this.gameState;
    }


    public GamePanel(){

        this.setPreferredSize(new Dimension(screenWidth+((maxScreenCol+1)*2), screenHeight+((maxScreenRow+1)*2)));
        this.setBackground(Color.black);
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

        g2.fillRect(645, 5, 490, 630);

        if(mapeado == false){
            int i = 0;
            int j = 0;

            for(int x = 2; x < (maxScreenRow)*(tileSize+2); x+=tileSize+2){
                for(int y = 2; y< (maxScreenCol)*(tileSize+2); y+=tileSize+2){
                    matrizJogo[i][j] = new Bloco();
                    matrizJogo[i][j].setX(x);
                    matrizJogo[i][j].setY(y);
                    g2.fillRect(x, y, tileSize, tileSize);
                    j++;
                }
                i++;
                j = 0;
            }


            //coloca o robo no ambiente
            matrizJogo[rand.nextInt(20)][rand.nextInt(20)].setRobo();

            // espalha os pontos de sujeira pelo campo
            espalhaRadiacao();




            mapeado = true;
        }
        else{
            if(getGameState()){
                Redor(matrizJogo);
                for(int x = 0; x < maxScreenRow; x++){
                    for(int y = 0; y< maxScreenCol; y++){
                        if(matrizJogo[x][y].isRobo()){
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                            g2.drawImage(image, matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), this);
                        }
                        else if(matrizJogo[x][y].isRadiacao() == true){
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                            g2.drawImage(image2, matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), this);
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
                for(int x = 0; x < maxScreenRow; x++){
                    for(int y = 0; y< maxScreenCol; y++){
                        if(matrizJogo[x][y].isRobo()){
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                            g2.drawImage(image, matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), this);
                        }
                        else if(matrizJogo[x][y].isRadiacao() == true){
                            g2.fillRect(matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), tileSize, tileSize);
                            g2.drawImage(image2, matrizJogo[x][y].getX(), matrizJogo[x][y].getY(), this);
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

    public void espalhaRadiacao(){

        int radiacao = 30;
        while(radiacao > 0){ // espalha pontos
            int x = rand.nextInt(20);
            int y = rand.nextInt(20);

            if(!matrizJogo[x][y].isRadiacao() && !matrizJogo[x][y].isRobo()){
                matrizJogo[x][y].setRadiacao(true);
                radiacao--;
            }
            
        }
    }


    // o robo percebe o ambiente dele
    public void percebeAmbiente(){

    }

    public void tomaAcao(Bloco[][] matriz){

    }


    public void NextRound(Bloco[][] matriz){
        for(int x = 0; x < maxScreenRow; x++){
            for(int y = 0; y< maxScreenCol; y++){
                if(matriz[x][y].getFlag() != 0){
                    if(matriz[x][y].getFlag() == 1){
                        matriz[x][y].setRadiacao(false);
                    }
                    else{
                        matriz[x][y].setRadiacao(true);
                    }
                    matriz[x][y].setFlag(0);
                }
            }
        }
    }

    public void Redor(Bloco[][] matriz){
        for(int x = 0; x < maxScreenRow; x++){
            for(int y = 0; y< maxScreenCol; y++){
                int pt = 0;

                if(x == 0 || y == 0 || x == maxScreenRow-1 || y == maxScreenCol-1){
                    if(y == 0){
                        if(x == 0){
                            if(matriz[x+1][y].isRadiacao()){ // baixo
                                pt+=1;
                            }
                        }
                        else if(x == maxScreenRow-1){
                            if(matriz[x-1][y].isRadiacao()){ // cima
                                pt+=1;
                            }
                        }
                        else{
                            if(matriz[x-1][y].isRadiacao()){ // cima
                                pt+=1;
                            }
                            if(matriz[x+1][y].isRadiacao()){ // baixo
                                pt+=1;
                            }
                        }
                        if(matriz[x][y+1].isRadiacao()){ // direita
                            pt+=1;
                        }
                    }
                }
                else{
                    if(matriz[x-1][y-1].isRadiacao()){ //1
                        pt+=1;
                    }
                    if(matriz[x-1][y].isRadiacao()){ //2
                        pt+=1;
                    }
                    if(matriz[x-1][y+1].isRadiacao()){ //3
                        pt+=1;
                    }
                    if(matriz[x][y-1].isRadiacao()){ //4
                        pt+=1;
                    }
                    if(matriz[x][y+1].isRadiacao()){ //5
                        pt+=1;
                    }
                    if(matriz[x+1][y-1].isRadiacao()){ //6
                        pt+=1;
                    }
                    if(matriz[x+1][y].isRadiacao()){ //7
                        pt+=1;
                    }
                    if(matriz[x+1][y+1].isRadiacao()){ //8
                        pt+=1;
                    }
                }
                
                if(matriz[x][y].isRadiacao()){
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