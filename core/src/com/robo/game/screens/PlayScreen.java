package com.robo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.robo.game.RoboGame;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;
import com.robo.game.basic.ScreenInstance;
import com.robo.game.basic.StaticObject;
import com.robo.game.extended.Bullet;
import com.robo.game.extended.Enemy;
import com.robo.game.extended.Player;

import java.util.LinkedList;

/**
 * Created by cosmin on 12/20/2015.
 */
public class PlayScreen extends ScreenInstance {
    public Player player;
    public LinkedList<Enemy> enemyLinkedList;
    public StaticObject ground;
    public StaticObject background;
    private static PlayScreen instance;
    ////////
//    private static ShapeRenderer shapeRenderer;

    public static Player getPlayer() {
        return instance.player;
    }

    public static PlayScreen getInstance(SpriteBatch batch) {
        if(instance == null) {
            instance = new PlayScreen();
            instance.setBatch(batch);
            instance.setBackground();
            instance.setPlayer();
            instance.setEnemyLinkedList();
            instance.setGround();

//            shapeRenderer = new ShapeRenderer();
        }

        return instance;
    }

    private void setPlayer() {
        player = new Player(0, 0, mBatch, 3);
    }

    private void setEnemyLinkedList() {
        enemyLinkedList = new LinkedList<Enemy>();
        Enemy.generateEnemy(mBatch, enemyLinkedList);
    }

    private void setGround() {
        ground = new StaticObject(LoadAssets.getGround(), mBatch, RoboGame.screenWidth, RoboGame.screenHeight / 5);
    }

    private void setBackground() {
        background = new StaticObject(LoadAssets.getBackground(1), mBatch, RoboGame.screenWidth, RoboGame.screenHeight);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        player.fire(screenX, screenY);
        return false;
    }

    @Override
    public void draw() {
        background.drawAtCoords(0, 0);

        float decal = (float)Math.random() * 4;
        player.mY = decal;
        ground.drawAtCoords(10, -RoboGame.screenHeight / 15 + decal);
        player.drawAnimation();

        for (int i = 0; i < enemyLinkedList.size(); i++) {
            MovableObject movableObject = enemyLinkedList.get(i);
            movableObject.drawAnimation();
            if(movableObject.outOfScene()) {
                enemyLinkedList.remove(movableObject);
            }
            if(movableObject.isDying()) {
                continue;
            }

            if(movableObject.overlaps(player)) {
                player.die();
            }

            for(int j = 0; j < player.getBulletList().size(); j++) {
                Bullet bullet = player.getBulletList().get(j);
                if(movableObject.overlaps(bullet)) {
                    movableObject.die();
                    bullet.die();
                }
            }
        }
    }

//    public void drawCollisionCircles() {
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.circle(player.getRectangle().x, player.getRectangle().y, player.getRectangle().radius);
//
//        for (int i = 0; i < enemyLinkedList.size(); i++) {
//            Circle circle = enemyLinkedList.get(i).getRectangle();
//            shapeRenderer.circle(circle.x,circle.y, circle.radius);
//        }
//        for(int j = 0; j < player.getBulletList().size(); j++) {
//            Circle circle = player.getBulletList().get(j).getRectangle();
//            shapeRenderer.circle(circle.x,circle.y, circle.radius);
//        }
//        shapeRenderer.end();
//    }
}
