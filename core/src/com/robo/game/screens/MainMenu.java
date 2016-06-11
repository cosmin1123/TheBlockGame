package com.robo.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.robo.game.RoboGame;
import com.robo.game.basic.LoadAssets;
import com.robo.game.basic.MenuElement;
import com.robo.game.basic.ScreenInstance;
import com.robo.game.basic.StaticObject;

import java.util.LinkedList;

/**
 * Created by cosmin on 12/20/2015.
 */
public class MainMenu extends ScreenInstance {
    public static final String START_GAME = "Start Game";
    public static final String SHOP = "Shop";
    public static final String OPTIONS = "Options";
    public static final String HELP = "EXIT";
    public static final String MAIN_MENU = "Main Menu";

    private static final float TOP_SPACE = 300;
    private static final float ELEMENT_SPACING  = 100;

    private static MainMenu mainMenuInstance;
    private BitmapFont mFont;
    private StaticObject background;
    private LinkedList<MenuElement> menuElementLinkedList;

    private SpriteBatch mBatch;

    public static MainMenu getInstance(SpriteBatch batch) {
        if (mainMenuInstance == null) {
            mainMenuInstance = new MainMenu(batch);
        }

        return mainMenuInstance;
    }

    private MainMenu(SpriteBatch batch) {
        mBatch = batch;

        FreeTypeFontGenerator generator = LoadAssets.getFontGenerator();
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 50;
        mFont = generator.generateFont(parameter); // font size 12 pixels
        generator.dispose(); // don't forget to dispose to avoid memory leaks!

        background = new StaticObject(LoadAssets.getBackground(1), mBatch, RoboGame.screenWidth, RoboGame.screenHeight);
        menuElementLinkedList = new LinkedList<MenuElement>();

        addMenuElement(300, RoboGame.screenHeight - TOP_SPACE, START_GAME);
        addMenuElement(350, RoboGame.screenHeight - TOP_SPACE - ELEMENT_SPACING,
                OPTIONS);
        addMenuElement(390, RoboGame.screenHeight - TOP_SPACE - 2 * ELEMENT_SPACING,
                SHOP);
        addMenuElement(390, RoboGame.screenHeight - TOP_SPACE - 3 * ELEMENT_SPACING,
                HELP);
    }

    private void addMenuElement(float x, float y, final String text) {
        menuElementLinkedList.add(new MenuElement(x, y, mBatch, mFont, text) {

            @Override
            public void doOnClick() {
                RoboGame.doAction(text);
            }
        });
    }

    @Override
    public void draw() {
        mFont.setColor(Color.WHITE);

        for(MenuElement element : menuElementLinkedList) {
            element.draw();
        }
        background.drawAtCoords(0,0);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        for(MenuElement element : menuElementLinkedList) {
            element.touchDown(screenX, screenY, pointer, button);
        }
        return false;
    }
}
