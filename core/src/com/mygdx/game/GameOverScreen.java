package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameOverScreen implements Screen {
    private Stage stage;
    private Game game;

    public GameOverScreen(Game game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        BitmapFont font = new BitmapFont(); // Create a new BitmapFont
        font.getData().setScale(2); // Set the scale of the font to 2
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Set the font of the style
        textButtonStyle.fontColor = Color.WHITE; // Set the font color of the style
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.RED); // Create a new LabelStyle with the font

        Label gameOverLabel = new Label("Game Over", labelStyle); // Use the style to create the Label

        gameOverLabel.setFontScale(4);
        gameOverLabel.setPosition(Gdx.graphics.getWidth() / 2 - gameOverLabel.getWidth() * 2 / 2,
                Gdx.graphics.getHeight() / 2 + gameOverLabel.getHeight() / 2 + 100);

        final Game finalGame = game;

        TextButton restartButton = new TextButton("Restart", textButtonStyle);
        restartButton.setPosition(Gdx.graphics.getWidth() / 2 - restartButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - restartButton.getHeight() / 2);
        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                finalGame.setScreen(new PixelSwordAdventures(finalGame));
            }
        });

        TextButton exitButton = new TextButton("Exit", textButtonStyle);
        exitButton.setPosition(Gdx.graphics.getWidth() / 2 - exitButton.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - exitButton.getHeight() / 2 - 50);
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        stage.addActor(gameOverLabel);
        stage.addActor(restartButton);
        stage.addActor(exitButton);
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void dispose() {
    }
}