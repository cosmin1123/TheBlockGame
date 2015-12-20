package com.robo.game.extended;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.robo.game.RoboGame;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;

import java.util.LinkedList;

/**
 * Created by cosmin on 11/25/2015.
 */
public class Enemy extends MovableObject{
    private float mEndX;
    private float mEndY;

    public void setStep(float step) {
        float diffX = mEndX - mX;
        float diffY = mEndY - mY;

        mStepX = (float) -Math.sqrt(step / (1 + diffY / diffX));
        mStepY = mStepX * diffY / diffX;
    }

    public Enemy(float x, float y, SpriteBatch batch, float scale, float endX, float endY, float step) {
        super(x, y, batch, scale);
        mAnimation = LoadAssets.getAsteroidAnimation();
        mDieAnimation = LoadAssets.getBulleDietAnimation();

        mEndX = endX;
        mEndY = endY;
        setStep(step);
        performAnimation(mAnimation, false);
    }

    public static void generateEnemy(SpriteBatch batch, LinkedList movableObjectLinkedList) {
        float x = RoboGame.screenWidth / 2 + (RoboGame.screenWidth / 2) *(float) Math.random();
        float y = RoboGame.screenHeight / 2 + (RoboGame.screenHeight / 2) *(float) Math.random();

        if(x < y) {
            x = RoboGame.screenWidth;
        } else {
            y = RoboGame.screenHeight;
        }

        movableObjectLinkedList.add(new Enemy(
                x,
                y,
                batch,
                1,//10 - (float)Math.random() * 9,
                RoboGame.player.mX,
                RoboGame.player.mY,
                20 + (float) Math.random() * 40));
    }


    @Override
    public void doActionOnAnimationEnd() {
        RoboGame.enemyLinkedList.remove(this);
        Enemy.generateEnemy(this.mBatch, RoboGame.enemyLinkedList);
    }
}
