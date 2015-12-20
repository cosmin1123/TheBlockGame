package com.robo.game.extended;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;

import java.util.BitSet;
import java.util.LinkedList;

/**
 * Created by cosmin on 11/21/2015.
 */
public class Player extends MovableObject {
    LinkedList<Bullet> bulletList;
    Animation mFireAnimation;
    MuzzleFire mMuzzleFire;
    float bulletScreenX;
    float bulletScreenY;

    public Player(float x, float y, SpriteBatch batch, float scale) {
        super(x, y, batch, scale);
        mAnimation = LoadAssets.getPlayerRunAnimation();
        mFireAnimation = LoadAssets.getPlayerFireAnimation();
        mStepX = 0;
        mStepY = 0;

        bulletList = new LinkedList<Bullet>();
        performAnimation(mAnimation, false);
        mMuzzleFire = new MuzzleFire(
                mX + currentAnimation.getKeyFrames()[0].getRegionWidth() / (mScale * 1.05f),
                mY+ currentAnimation.getKeyFrames()[0].getRegionHeight() / (20f * mScale),
                mBatch,
                1.5f);
    }

    public void fire(float screenX, float screenY) {
        super.performAnimation(mFireAnimation, true);

        bulletScreenX = screenX;
        bulletScreenY = screenY;

        mMuzzleFire.start();
    }

    @Override
    public void doActionOnAnimationEnd() {
        bulletList.add(
                new Bullet(
                        mX + currentAnimation.getKeyFrames()[0].getRegionWidth() / (mScale * 1.2f),
                        mY+ currentAnimation.getKeyFrames()[0].getRegionHeight() / (3f * mScale),
                        mBatch,
                        mScale,
                        bulletScreenX,
                        bulletScreenY,
                        5));
    }

    @Override
    public void drawAnimation() {
        super.drawAnimation();

        mMuzzleFire.drawAnimation();

        for(int i = 0; i < bulletList.size(); i++) {
            Bullet bullet = bulletList.get(i);
            bullet.drawAnimation();

            if(bullet.outOfScene()) {
                bulletList.remove(bullet);
                i--;
            }
        }
    }

    public void removeBullet(Bullet target) {
        bulletList.remove(target);
    }

    public LinkedList<Bullet> getBulletList() {
        return bulletList;
    }
}
