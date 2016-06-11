package com.robo.game;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;
import com.robo.game.basic.ScreenInstance;
import com.robo.game.basic.StaticObject;
import com.robo.game.extended.Bullet;
import com.robo.game.extended.Enemy;
import com.robo.game.extended.Player;
import com.robo.game.screens.MainMenu;
import com.robo.game.screens.PlayScreen;

import java.util.LinkedList;

public class RoboGame extends ApplicationAdapter implements InputProcessor{
	public static final float screenHeight = 640;
	public static final float screenWidth = 960;
	private static OrthographicCamera camera;
    static SpriteBatch batch;

    static ScreenInstance currentScreen;

	public void setCamera() {
		camera = new OrthographicCamera();
		camera.viewportHeight = screenHeight;
		camera.viewportWidth = screenWidth;

		camera.position.set(camera.viewportWidth * .5f,
				camera.viewportHeight * .5f, 0f);
		camera.update();
	}

	@Override
	public void create () {
		setCamera();

		batch = new SpriteBatch();

        currentScreen = MainMenu.getInstance(batch);
        Gdx.input.setCatchBackKey(true);
        Gdx.input.setInputProcessor(this);
	}

    public static void doAction(String screen) {
        if(screen.equals(MainMenu.START_GAME)) {
            currentScreen = PlayScreen.getInstance(batch);
        }

        if(screen.equals(MainMenu.MAIN_MENU)) {
            currentScreen = MainMenu.getInstance(batch);
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();
		batch.setProjectionMatrix(camera.combined);

		batch.begin();

        currentScreen.draw();

        batch.end();
    }

    @Override
    public boolean keyDown(int keycode) {

        if(keycode == Input.Keys.BACK) {
            doAction(MainMenu.MAIN_MENU);
        }

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        currentScreen.touchDown(screenX, -screenY + Gdx.graphics.getHeight(), pointer, button);
        return false;
	}

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }


}