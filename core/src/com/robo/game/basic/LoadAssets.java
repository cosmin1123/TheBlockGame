package com.robo.game.basic;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;

/**
 * Created by cosmin on 11/20/2015.
 */
public class LoadAssets {

    public static Texture getBackground(int num) {
        if(num >= 1 && num <= 5) {
            return new Texture("background/" + num +".png");
        }
        return null;
    }

    public static Texture getGround() {
        return new Texture("ground1.png");
    }

    public static Animation loadAnimation(Texture texture, int frameCols, int frameRows, int row) {
        TextureRegion[] frames;
        TextureRegion[][] tmp = TextureRegion.split(texture, texture.getWidth()/frameCols,
                texture.getHeight()/frameRows);
        frames = new TextureRegion[frameCols];
        int index = 0;
        for (int j = 0; j < frameCols; j++) {
            frames[index++] = tmp[row][j];
        }
        return new Animation(0.050f, frames);
    }

    public static Animation getPlayerRunAnimation() {
        return loadAnimation(new Texture("Enemies/Player/player_run.png"), 8, 1, 0);
    }

    public static Animation getBulletAnimation() {
        return loadAnimation(new Texture("Enemies/Player/player_bullet_projectile.png"), 5, 2, 0);
    }

    public static Animation getBulleDietAnimation() {
        return loadAnimation(new Texture("Enemies/Player/player_bullet_projectile.png"), 5, 2, 1);
    }

    public static Animation getPlayerFireAnimation() {
        return loadAnimation(new Texture("Enemies/Player/player_fire.png"), 9, 1, 0);
    }

    public static Animation getAsteroidAnimation() {
        return loadAnimation(new Texture("Enemies/Air/bigtaroid1_spr_strip8.png"), 8, 1, 0);
    }

    public static Animation getMuzzleFireAnimation() {
        return loadAnimation(new Texture("Enemies/Player/player_fire_muzzle.png"), 5, 1, 0);
    }

    public static FreeTypeFontGenerator getFontGenerator() {
        return new FreeTypeFontGenerator(Gdx.files.internal("fonts/pdark.ttf"));
    }
}
