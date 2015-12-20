package com.robo.game.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by cosmin on 11/20/2015.
 */
public class StaticObject {
    private Texture mTexture;
    private final SpriteBatch mBatch;
    float mWidth;
    float mHeight;

    public StaticObject(Texture texture, SpriteBatch batch) {
        this(texture, batch, texture.getWidth(), texture.getHeight());
    }


    public StaticObject(Texture texture, SpriteBatch batch, float width, float height) {
        mTexture = texture;
        mBatch = batch;
        mWidth = width;
        mHeight = height;
    }

    public void drawAtCoords(float x, float y) {
        mBatch.draw(mTexture, x, y, mWidth, mHeight);
    }

    public void updateImage(Texture texture) {
        mTexture = texture;
    }
}
