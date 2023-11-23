package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.items.*;

public class ShopScreen implements Screen {
    private Stage stage;
    private Game game;
    private Screen previousScreen;
    private PixelSwordAdventures pixelSwordAdventures;
    private Player onePlayer;
    private Hero oneHero;

    public ShopScreen(Game game,Player player,Screen previousScreen,PixelSwordAdventures pixelSwordAdventures,Hero hero) {
        this.game = game;
        this.previousScreen = previousScreen;
        this.pixelSwordAdventures = pixelSwordAdventures;
        this.onePlayer = player;
        this.oneHero = hero;
        stage = new Stage(new ScreenViewport());
        BitmapFont font = new BitmapFont(); // Create a new BitmapFont
        font.getData().setScale(2); // Set the scale of the font to 2
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = font; // Set the font of the style
        textButtonStyle.fontColor = Color.WHITE; // Set the font color of the style
        Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE); // Create a new LabelStyle with the font

        Label shopLabel = new Label("Welcome to the Shop", labelStyle); // Use the style to create the Label

        shopLabel.setFontScale(3);
        shopLabel.setPosition(Gdx.graphics.getWidth() / 2 - shopLabel.getWidth() + 100,
                Gdx.graphics.getHeight() / 2 + shopLabel.getHeight() / 2 + 300);

        final Game finalGame = game;

        // Ajoutez vos images dans le dossier "assets" ou le dossier approprié de votre
        // projet
        Image item1Image = new Image(new Texture(Gdx.files.internal("assets/Weapons/bow_01.png")));
        item1Image.setSize(100, 100); // Ajustez la taille selon vos besoins

        Image item2Image = new Image(new Texture(Gdx.files.internal("assets/Weapons/sword_01.png")));
        item2Image.setSize(100, 100);

        Image item3Image = new Image(new Texture(Gdx.files.internal("assets/Weapons/wand_01.png")));
        item3Image.setSize(100, 100);

        TextButton buyItem1Button = new TextButton("Price : 10", textButtonStyle);
        buyItem1Button.setPosition(Gdx.graphics.getWidth() / 3 - buyItem1Button.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - buyItem1Button.getHeight() / 2 - 100);
        buyItem1Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Logique d'achat pour l'article 1 ici
                if(oneHero.getCoins() >= 200){
                    oneHero.setCoins(oneHero.getCoins() - 200);
                    onePlayer.pickObject(new Bow("Bow", 1, 10, "Common"));
                }
            }
        });
        item1Image.setPosition(buyItem1Button.getX()  , buyItem1Button.getY()+ 30);

        TextButton buyItem2Button = new TextButton("Price : 10", textButtonStyle);
        buyItem2Button.setPosition(Gdx.graphics.getWidth() /  2 - buyItem2Button.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - buyItem2Button.getHeight() / 2 - 100);
        buyItem2Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Logique d'achat pour l'article 2 ici
                if(oneHero.getCoins() >= 200){
                    oneHero.setCoins(oneHero.getCoins() - 200);
                    onePlayer.pickObject(new Sword("Sword", 1, 10, "Common"));
                }
            }
        });

        item2Image.setPosition(buyItem2Button.getX()  , buyItem2Button.getY() + 30);

        TextButton buyItem3Button = new TextButton("Price : 10", textButtonStyle);
        buyItem3Button.setPosition(Gdx.graphics.getWidth() / 3 * 2 - buyItem3Button.getWidth() / 2,
                Gdx.graphics.getHeight() / 2 - buyItem3Button.getHeight() / 2 - 100);
        buyItem3Button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Logique d'achat pour l'article 3 ici
                if(oneHero.getCoins() >= 200){
                    oneHero.setCoins(oneHero.getCoins() - 200);
                    onePlayer.pickObject(new Wand("Wand", 1, 10, "Common"));
                }
            }
        });

        item3Image.setPosition(buyItem3Button.getX()  , buyItem3Button.getY() + 30);

        // Ajoutez les images et les boutons à la scène
        stage.addActor(shopLabel);
        stage.addActor(item1Image);
        stage.addActor(buyItem1Button);
        stage.addActor(item2Image);
        stage.addActor(buyItem2Button);
        stage.addActor(item3Image);
        stage.addActor(buyItem3Button);

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

        if (Gdx.input.isKeyPressed(com.badlogic.gdx.Input.Keys.TAB )) {
            pixelSwordAdventures.setControl();
            game.setScreen(previousScreen);
            this.dispose();
        }
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
