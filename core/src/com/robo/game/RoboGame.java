package com.robo.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MovableObject;
import com.robo.game.basic.StaticObject;
import com.robo.game.extended.Bullet;
import com.robo.game.extended.Enemy;
import com.robo.game.extended.Player;

import java.util.LinkedList;

public class RoboGame extends ApplicationAdapter implements InputProcessor{
	public static final float screenHeight = 640;
	public static final float screenWidth = 960;
	private static OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
	SpriteBatch batch;

	StaticObject background;
    StaticObject ground;
	public static Player player;

    public static LinkedList<Enemy> enemyLinkedList;

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
        ground = new StaticObject(LoadAssets.getGround(), batch, screenWidth, screenHeight / 5);
		background = new StaticObject(LoadAssets.getBackground(1), batch, screenWidth, screenHeight);


        enemyLinkedList = new LinkedList<Enemy>();
        player = new Player(0, 0, batch, 3);

        Enemy.generateEnemy(batch, enemyLinkedList);
        Gdx.input.setInputProcessor(this);

        shapeRenderer = new ShapeRenderer();
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

		background.drawAtCoords(0, 0);
        float decal = (float)Math.random() * 4;
        player.mY = decal;
        ground.drawAtCoords(10, -screenHeight / 15 + decal);
		player.drawAnimation();

        for (int i = 0; i < enemyLinkedList.size(); i++) {
            MovableObject movableObject = enemyLinkedList.get(i);
            movableObject.drawAnimation();
            if(movableObject.isDying()) {
                continue;
            }

            if(movableObject.overlaps(player)) {
                player.die();
            }

            for(int j = 0; j < player.getBulletList().size(); j++) {
                Bullet bullet = player.getBulletList().get(j);
                if(movableObject.overlaps(bullet)) {
                    movableObject.die();
                    bullet.die();
                }
            }

        }

		batch.end();

        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.circle(player.getRectangle().x, player.getRectangle().y, player.getRectangle().radius);

        for (int i = 0; i < enemyLinkedList.size(); i++) {
            Circle circle = enemyLinkedList.get(i).getRectangle();
            shapeRenderer.circle(circle.x,circle.y, circle.radius);
        }
        for(int j = 0; j < player.getBulletList().size(); j++) {
            Circle circle = player.getBulletList().get(j).getRectangle();
            shapeRenderer.circle(circle.x,circle.y, circle.radius);
        }
        shapeRenderer.end();
    }

    @Override
    public boolean keyDown(int keycode) {
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
		player.fire(screenX, -screenY + Gdx.graphics.getHeight());
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