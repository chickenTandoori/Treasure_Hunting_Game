package main;

import entity.Player;
import object.SuperObject;
import tile.TileManager;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    //Screen Settings

    final int originalTileSize = 16;
    final int scale = 4;

    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 12;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;

    //WORLD SETTINGS
    public final int maxWorldCol=50;
    public final int maxWorldRow=50;

    //FPS
    int FPS = 60;
    TileManager tileM=new TileManager(this);
    KeyHandler KeyH = new KeyHandler();
    public CollisionChecker cChecker=new CollisionChecker(this);
    public AssetSetter aSetter=new AssetSetter(this);
    public UI ui=new UI(this);
    Thread gameThread;

    //ENTITY AND OBJECT
    Sound music = new Sound();
    Sound se=new Sound();
    public Player player=new Player(this,KeyH);
    public SuperObject obj[]=new SuperObject[10];

    //Set Player Default position.
    int playerX = 100;
    int playerY = 100;
    int playerSpeed = 4;

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(KeyH);
        this.setFocusable(true);

    }

    public void setupGame(){
        aSetter.setObject();
        playMusic(0);
    }
    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }
    public void run(){
        double drawInterval=1000000000/FPS;
        double delta=0;
        long lastTime=System.nanoTime();
        long currentTime;
        while(gameThread!=null){
            currentTime=System.nanoTime();
            delta+=(currentTime-lastTime)/drawInterval;
            lastTime = currentTime;
            if (delta >=1){
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {

        player.update();
    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        //TILE
        tileM.draw(g2);
        //OBJECT
        for (int i=0;i<obj.length;i++){
            if (obj[i]!=null){
                obj[i].draw(g2,this);
            }
        }
        //PLAYER
        player.draw(g2);
        //UI
        ui.draw(g2);
        g2.dispose();
    }
    public void playMusic(int i){
        music.setFile(i);
        music.play();
        music.loop();
    }
    public void stopMusic(){
        music.stop();
    }
    public void playSE(int i){
        se.setFile(i);
        se.play();
    }
}





















