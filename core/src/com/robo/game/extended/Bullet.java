package com.robo.game.extended;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.robo.game.RoboGame;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;
import com.robo.game.screens.PlayScreen;

/**
 * Created by cosmin on 11/21/2015.
 */
public class Bullet extends MovableObject {
    private float mEndX;
    private float mEndY;
    private float mRotation;
    private float mCurrentRotation;
    public void setStep(float step) {
        float diffX = mEndX - mX;
        float diffY = mEndY - mY;

        mStepX = (float)Math.sqrt(step / (1 + diffY / diffX));
        mStepY = mStepX * diffY / diffX;
        mRotation = (float) Math.atan(diffY / diffX) * 60;
    }

    public Bullet(float x, float y, SpriteBatch batch, float scale, float endX, float endY, float step) {
        super(x, y, batch, scale);
        mAnimation = LoadAssets.getBulletAnimation();
        mDieAnimation = LoadAssets.getBulleDietAnimation();

        mEndX = endX;
        mEndY = endY;
        setStep(step);
        performAnimation(mAnimation, false);
        mCurrentRotation = 0f;
    }

    public void drawAnimation() {
        boolean remove = this.moveAndUpdateState();
        TextureRegion currentFrame = mAnimation.getKeyFrame(mStateTime, true);  // #16

        mBatch.draw(currentFrame, mX, mY,0, 0,
                currentFrame.getRegionWidth() / mScale,
                currentFrame.getRegionHeight() / mScale,
                1,
                1,
                mCurrentRotation);

        if(mCurrentRotation < mRotation) {
            mCurrentRotation += mRotation / 100;
        }
        if(remove) {
            PlayScreen.getInstance(mBatch).player.removeBullet(this);
        }
    }

    @Override
    public Circle getRectangle() {
        Circle circle = super.getRectangle();
        circle.setPosition(
                circle.x - circle.radius * mCurrentRotation / 40,
                circle.y + circle.radius * mCurrentRotation / 50
        );
        return circle;
    }
}
