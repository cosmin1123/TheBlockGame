package com.robo.game.basic;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by cosmin on 12/20/2015.
 */
public abstract class MenuElement {
    private float mX;
    private float mY;
    private float mWidth;
    private float mHeight;
    private SpriteBatch mBatch;
    private BitmapFont mFont;
    private GlyphLayout layout;
    Rectangle objectBorder;

    private String mResText;
    private Texture mResTexture;
    // TODO FIX THE WIDTH AND HEIGHT TO THE CAMERA
    public MenuElement(float x, float y, SpriteBatch batch, BitmapFont font, String resText) {
        this(x, y, batch, font);
        mResText = resText;
        layout.setText(mFont, resText);
        mWidth = layout.width;
        mHeight = layout.height;
        objectBorder = new Rectangle(mX, mY, mWidth, mHeight);
    }
    // TODO FIX THE WIDTH AND HEIGHT TO THE CAMERA
    public MenuElement(float x, float y, SpriteBatch batch, BitmapFont font, Texture resTexture) {
        this(x, y, batch, font);
        mResTexture = resTexture;
        mWidth = mResTexture.getWidth();
        mHeight = mResTexture.getHeight();
    }

    public MenuElement(float x, float y, SpriteBatch batch, BitmapFont font) {
        mX = x;
        mY = y;
        mFont = font;
        layout = new GlyphLayout();
        mBatch = batch;
    }

    public void draw() {
        if(mResTexture != null) {
            mBatch.draw(mResTexture, mX, mY);
        }
        if(mResText != null) {
            mFont.draw(mBatch, mResText, mX, mY);
        }
    }

    public abstract void doOnClick();

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println(screenX + " " + screenY + " " +
            mX + " " + mY + " " + mHeight + " " + mWidth);

        if(objectBorder.contains(screenX, screenY)) {
            doOnClick();
        }

        return false;
    }
}
