package main;

public class Bloco {
    private int x;
    private int y;
    private boolean vivo;
    private int flag; //0 sem flag, 1 vai morrer, 2 vai viver

    Bloco(){
        this.x = 0;
        this.y = 0;
        vivo = false;
        flag = 0;
    }

    public int getFlag() {
        return this.flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getX() {
        return this.x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isVivo() {
        return this.vivo;
    }

    public boolean getVivo() {
        return this.vivo;
    }

    public void setVivo(boolean vivo) {
        this.vivo = vivo;
    }

}
