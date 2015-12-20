package com.robo.game.basic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;
import com.robo.game.RoboGame;

/**
 * Created by cosmin on 11/20/2015.
 */
public class MovableObject {
    protected Animation mAnimation;
    protected Animation currentAnimation;
    protected Animation mDieAnimation;
    public float mX;
    public float mY;
    protected float mStepX;
    protected float mStepY;
    protected boolean mShouldStopAnimation;
    protected Circle polygon;

    protected float mScale;
    protected float mStateTime;

    protected final SpriteBatch mBatch;

    public MovableObject(float x, float y, SpriteBatch batch, float scale) {
        mX = x;
        mY = y;
        mBatch = batch;
        mScale = scale;
        mStateTime = 0f;
        mShouldStopAnimation = false;
        polygon = new Circle();
    }

    public void performAnimation(Animation newAnimation, boolean shouldStopAnimation) {
        mStateTime = 0f;
        mShouldStopAnimation = shouldStopAnimation;
        currentAnimation = newAnimation;
    }

    public boolean isDying() {
        return mDieAnimation == currentAnimation;
    }

    public boolean moveAndUpdateState() {
        float prevStateTime = mStateTime;
        move();
        mStateTime += Gdx.graphics.getDeltaTime();
        return mShouldStopAnimation && currentAnimation.isAnimationFinished(prevStateTime);
    }

    public void drawAnimation() {
        if(this.moveAndUpdateState()) {
            currentAnimation = mAnimation;
            mShouldStopAnimation = false;
            doActionOnAnimationEnd();
            return;
        }
        TextureRegion currentFrame = currentAnimation.getKeyFrame(mStateTime, true);  // #16

        mBatch.draw(currentFrame, mX, mY,
                currentFrame.getRegionWidth() / mScale,
                currentFrame.getRegionHeight() / mScale);
    }

    public void doActionOnAnimationEnd() {

    }

    public void die() {
        mStepX = 0f;
        mStepY = 0f;
        if(mDieAnimation != null) {
            performAnimation(mDieAnimation, true);
        }
    }

    public boolean outOfScene() {
        float x = mX;
        float y = mY;
        return  ((x < 0) || ((x) > RoboGame.screenWidth)) ||
                ((y < 0) || ((y) > RoboGame.screenHeight));
    }

    protected void move() {
        mX += mStepX;
        mY += mStepY;
    }

    public Circle getRectangle() {
        TextureRegion region = currentAnimation.getKeyFrames()[0];
        float width = region.getRegionWidth() / mScale;
        float height = region.getRegionHeight() / mScale;
        float radius = Math.min(height, width);
        polygon.setRadius(radius / 2.5f);
        polygon.setPosition(
                mX + width / 2,
                mY + height / 2
        );

        return polygon;
    }

    public boolean overlaps(MovableObject object) {
        return Intersector.overlaps(this.getRectangle(), object.getRectangle());
    }
}
