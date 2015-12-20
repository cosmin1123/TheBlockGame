package com.robo.game.extended;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;

/**
 * Created by cosmin on 12/12/2015.
 */
public class MuzzleFire extends MovableObject {
    public MuzzleFire(float x, float y, SpriteBatch batch, float scale) {
        super(x, y, batch, scale);
        mAnimation = LoadAssets.getMuzzleFireAnimation();
        performAnimation(mAnimation, true);
    }

    public void start() {
        mStateTime = 0f;
    }
    @Override
    public void doActionOnAnimationEnd() {
        mShouldStopAnimation = true;
    }

    @Override
    public void drawAnimation(){
        if(!currentAnimation.isAnimationFinished(mStateTime)) {
            super.drawAnimation();
        }
    }


}
