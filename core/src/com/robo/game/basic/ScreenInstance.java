package com.robo.game.basic;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by cosmin on 12/20/2015.
 */
public class ScreenInstance {
    protected SpriteBatch mBatch;

    public void setBatch(SpriteBatch batch) {
        mBatch = batch;
    }

    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    public void draw() {

    }
}
