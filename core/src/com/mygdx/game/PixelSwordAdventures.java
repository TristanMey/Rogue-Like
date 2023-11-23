package com.mygdx.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.game.box2d.Box2DWorld;
import com.mygdx.game.enemy.Gobelin;
import com.mygdx.game.heroes.Mage;
import com.mygdx.game.heroes.Rogue;
import com.mygdx.game.heroes.Warrior;
import com.mygdx.game.map.Hub;
import com.mygdx.game.map.Island;
import com.mygdx.game.map.Tile;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import com.badlogic.gdx.Screen;

public class PixelSwordAdventures extends ScreenAdapter {

  private final String ID = getClass().getName();
  Game game;
  SpriteBatch ui;

  Pixmap pm;
  Texture exit, start, options, sound, background, title, border, optionsTitle, optionsExit, input;
  Music menu;
  OrthographicCamera camera;
  Control control;
  Texture img;
  Island island;
  Hub hub;
  Hero hero;
  Gobelin gobelin;
  BitmapFont font;
  Player player;
  Enemy enemy;
  Box2DWorld box2D, box2d;
  SpriteBatch batch;
  SpriteBatch HealthBarBatch;
  Texture redMushroom;
  Boolean newLevel = false;
  Rectangle gobelinBounds;
  Boolean removeEntite = false;
  Boolean boss = false;
  SpriteBatch endDisplay;
  BitmapFont endFont;
  Boolean end = false;
  Warrior warrior;
  Mage mage;
  Rogue rogue;
  GlyphLayout layout;
  List<Hero> heroes = new ArrayList<Hero>();
  HashMap<Enemy, Gobelin> enemies = new HashMap<Enemy, Gobelin>();
  HashMap<Gobelin, Rectangle> hitBoxEnemy = new HashMap<Gobelin, Rectangle>();
  List<Chest> chests = new ArrayList<Chest>();

  

  float interactionDistance = 20.0f;
  int nbEnemies = 1;
  boolean showOptions = false;
  int x, y;
  int direction_x, direction_y;
  int speed = 4;
  boolean heroDied = false;
  float attackDelay = 1.0f;
  float timeSinceLastAttack = 0.0f;
  private int displayW;
  private int displayH;
  float elapsedTime = 0;
  float gobelinTime = 0;

  public PixelSwordAdventures(Game game) {
    this.game = game;

    //UI
    this.exit = new Texture("ressources/UI/Quit.png");
    this.start = new Texture("ressources/UI/Start.png");
    this.background = new Texture("ressources/UI/Background.png");
    this.sound = new Texture("ressources/UI/SoundOn.png");
    this.options = new Texture("ressources/UI/Options.png");
    this.title = new Texture("ressources/UI/Title.png");
    this.optionsTitle = new Texture("ressources/UI/Settings.png");
    this.optionsExit = new Texture("ressources/UI/OptionsExit.png");
    this.menu =
      Gdx.audio.newMusic(Gdx.files.internal("ressources/Music/Menu2.mp3"));
    this.menu.setLooping(true);
    this.menu.setVolume(0.5f);
    menu.play();
    this.border = new Texture("ressources/UI/Border.png");
    this.input = new Texture("ressources/UI/Azerty.png");

    this.HealthBarBatch = new SpriteBatch();
    this.pm = new Pixmap(Gdx.files.internal("assets/Ui/cursor.png"));
    this.ui = new SpriteBatch();
    this.font = new BitmapFont();
    this.batch = new SpriteBatch();

    create();
  }

  public void create() {
    Media.load_assets();

    displayW = Gdx.graphics.getWidth();
    displayH = Gdx.graphics.getHeight();

    ui = new SpriteBatch();
    HealthBarBatch = new SpriteBatch();
    endDisplay = new SpriteBatch();
    Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));

    layout = new GlyphLayout();

    font = new BitmapFont();
    endFont = new BitmapFont();
    endFont.setColor(Color.RED);
    endFont.getData().setScale(12);

    int h = (int) (displayH / Math.floor((double) displayH / 160));
    int w = (int) (
      displayW / (displayH / (displayH / Math.floor((double) displayH / 160)))
    );

    camera = new OrthographicCamera(w, h);
    camera.position.set(1914 / 2f, 1914 / 2f, 0);
    camera.zoom = 0.3f;
    camera.update();

    control = new Control(displayW, displayH, camera);
    Gdx.input.setInputProcessor(control);

    box2D = new Box2DWorld();
    box2d = new Box2DWorld();

    
    
    island = new Island(box2D, camera, player);
    island.addChests(box2D, player, camera);

    player = new Player("Player", 100, 20, 10, null, "area1", 1);
    enemy = new Enemy("Gobelin", 100, 20, 10, null, "area1", 1);

    warrior = new Warrior(island.centreTile.pos, box2D);
    mage = new Mage(island.centreTile.pos, box2D);
    rogue = new Rogue(island.centreTile.pos, box2D);

    hero = warrior;
    Gobelin gobelin = new Gobelin(island.randomTile().pos, box2D);

    heroes.add(hero);
    hero.test(player);
    hero.test();

    enemies.put(enemy, gobelin);
    hitBoxEnemy.put(gobelin, gobelinBounds);
    gobelin.test(enemy);
    gobelin.test();

    island.entities.add(hero);
    island.entities.add(gobelin);

    hub = new Hub(box2d, camera, player, hero);
    hero.currentWorld = island.world;
    hero.setCurrentLocation(island.centreTile);

    redMushroom = new Texture("assets/grass/portal.png");

    island.reset(box2D, camera, player);
    hero.reset(box2D, island.getCentrePosition());
    gobelin.reset(box2D, island.getRandomTile());
  }

  public void render(float delta) {
    // Menu
    if (GameTable.showMenu) {
      batch = new SpriteBatch();
      ScreenUtils.clear(0, 0, 0, 1);
      batch.begin();

      float screenWidth = Gdx.graphics.getWidth();
      float screenHeight = Gdx.graphics.getHeight();

      batch.draw(background, 0, 0, screenWidth, screenHeight);

      // Calculate positions for "Quit" and "Start" textures
      float exitX = (screenWidth - 250) / 2; // Center horizontally
      float exitY = (screenHeight - 300) / 2; // Center vertically
      float startX = (screenWidth - 250) / 2; // Center horizontally
      float startY = (screenHeight) / 2; // Center vertically
      float titleX = (screenWidth - 1020) / 2; // Center horizontally
      float titleY = (screenHeight + 100) / 2 + 200; // Center vertically

      if (showOptions) {
        batch.draw(optionsExit, 1670, 920, 70, 70);
        batch.draw(sound, 1670, 80, 70, 70);
        batch.draw(border, 0, 0, screenWidth, screenHeight);
        batch.draw(optionsTitle, 780, titleY);
        batch.draw(input, startX, startY, 250, 110);
      } else {
        // Draw the textures
        batch.draw(title, titleX, titleY);
        batch.draw(exit, exitX, exitY, 250, 110);
        batch.draw(start, startX, startY, 250, 110);
        batch.draw(options, 1830, 15, 70, 70);
      }

      batch.end();

      // Check if any of the buttons were touched
      if (Gdx.input.justTouched()) {
        float touchX = Gdx.input.getX();
        float touchY = screenHeight - Gdx.input.getY();
        if (
                touchX >= startX &&
                        touchX <= startX + 250 &&
                        touchY >= startY &&
                        touchY <= startY + 100 &&
                        !showOptions
        ) {
          // Start button touched
          GameTable.showMenu = false;
        } else if (
                touchX >= exitX &&
                        touchX <= exitX + 250 &&
                        touchY >= exitY &&
                        touchY <= exitY + 100 &&
                        !showOptions
        ) {
          // Exit button touched
          Gdx.app.exit();
        } else if (
                touchX >= 1670 &&
                        touchX <= 1670 + 70 &&
                        touchY >= 80 &&
                        touchY <= 80 + 70 &&
                        showOptions
        ) {
          // Sound button touched
          if (menu.isPlaying()) {
            sound = new Texture("ressources/UI/SoundOff.png");
            menu.pause();
          } else {
            sound = new Texture("ressources/UI/SoundOn.png");
            menu.play();
          }
        } else if (
                touchX >= 1800 &&
                        touchX <= 1800 + 110 &&
                        touchY >= 10 &&
                        touchY <= 10 + 100 &&
                        !showOptions
        ) {
          // Options button touched
          showOptions = true;
        } else if (
                touchX >= 1670 &&
                        touchX <= 1670 + 70 &&
                        touchY >= 920 &&
                        touchY <= 920 + 70 &&
                        showOptions
        ) {
          // Exit options button touched
          showOptions = false;
        } else if (
                touchX >= startX &&
                        touchX <= startX + 250 &&
                        touchY >= startY &&
                        touchY <= startY + 100 &&
                        showOptions
        ) {
          // Input button touched
          if (Control.azerty) {
            input = new Texture("ressources/UI/Qwerty.png");
            Control.azerty = false;
          } else {
            input = new Texture("ressources/UI/Azerty.png");
            Control.azerty = true;
          }
        }
      }
    } else {
      
      if (hub != null && hero.currentWorld == hub.world) {
        batch.setProjectionMatrix(camera.combined);
        // Set the color for clear
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        for (ArrayList<Tile> row : hub.chunk.tiles) {
          for (Tile tile : row) {
            batch.begin();
            batch.draw(
                    tile.texture,
                    tile.pos.x,
                    tile.pos.y,
                    tile.size,
                    tile.size
            );
            batch.end();
            if (tile.secondary_texture != null) {
              batch.begin();
              batch.draw(
                      tile.secondary_texture,
                      tile.pos.x,
                      tile.pos.y,
                      tile.size,
                      tile.size
              );
              batch.end();
            }
          }
        }
        batch.begin();

        batch.end();
        // Draw the hitboxes for the Hub

        direction_x = 0;
        direction_y = 0;

        if (Gdx.input.isKeyJustPressed(Keys.K)) {
          player.equipWeapon();
        }
        
        if (hero != null) {
          String heroClass = hero.getClass().getSimpleName();
          Animation<Texture> animation = null;

          if (control.down) {
            direction_y = -1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageDownAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueDownAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorDownAnimation);
                break;
            }
          } else if (control.up) {
            direction_y = 1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageUpAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueUpAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorUpAnimation);
                break;
            }
          } else if (control.left) {
            direction_x = -1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageLeftAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueLeftAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorLeftAnimation);
                break;
            }
          } else if (control.right) {
            direction_x = 1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageRightAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueRightAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorRightAnimation);
                break;
            }
          }

          if (animation != null) {
            elapsedTime += Gdx.graphics.getDeltaTime(); // Update the elapsed time
            hero.texture = animation.getKeyFrame(elapsedTime, true);
          }
        }

        // GAME LOGIC
        if (hero != null) {
          hero.update(control);
        }

        // Increase the font size
        font.getData().setScale(2);

        // Draw the text after all other elements have been drawn

        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(touchPos);

        float interactionDistance = 6f;
        List<Npc> Npcs = hub.getNpc();

        Vector3 heroScreenPosition = new Vector3(hero.pos.x, hero.pos.y, 0);
        Vector3 heroWorldPosition = camera.unproject(heroScreenPosition);

        for (Npc npc : Npcs) {
          if (Npcs.isEmpty()) {

          }
          Vector3 npcScreenPosition = new Vector3(
                  npc.pos.x,
                  npc.pos.y,
                  0
          );
          Vector3 npcWorldPosition = camera.unproject(npcScreenPosition);
          float distance = Vector3.dst(
                  npc.pos.x,
                  npc.pos.y,
                  0,
                  hero.pos.x,
                  hero.pos.y,
                  0
          );

          if (distance <= interactionDistance) {
            font.draw(
                    batch,
                    layout,
                    (displayW - layout.width) / 2,
                    (displayH + layout.height) / 2
            );
            if (Gdx.input.isKeyJustPressed(Keys.E)) {
              Screen previousScreen = game.getScreen();
              game.setScreen(new ShopScreen(game, player, previousScreen,this,hero));
            }
          }
        }

        if (hero != null) {
          camera.position.lerp(hero.pos, .1f);
          camera.update();
        }

        // GAME DRAW
        batch.setProjectionMatrix(camera.combined);
        batch.setBlendFunction(
                GL20.GL_SRC_ALPHA,
                GL20.GL_ONE_MINUS_SRC_ALPHA
        );

        if (enemies.isEmpty() && hero != null) {
        if (Gdx.input.isKeyJustPressed(Keys.T) && hero != null) {
          // Vérifier si le héros est actuellement au hub
          if (hero.currentWorld == hub.world) {
            // Téléportation à l'île
            
            island.entities.add(hero);
            hero.removeRectangel();
            island.addHitboxHero(hero, box2D);
            hero.teleportToIsland(island.centreTile1, island.world);
          } else {
            this.controlResetHub();
            hero.teleportToIsland(hub.centreTile, hub.world);
            hub.addHitboxHero(hero, box2d);
          }
        }
      }
        batch.begin();

        for (Entity e : hub.entities) {
          e.draw(batch);
        }

        for (Hero hero : heroes) {
          if (hero != null) {
            hero.drawHealthBar(batch);
          }
        }

        batch.end();
        // End drawing

        // Update the Box2D world
        box2d.tick(camera, control);

        ui.begin();
        // Stats
        font.draw(
                ui,
                "HP: " + player.getHp() + " / " + player.getMaxhp(),
                10,
                120
        );
        font.draw(ui, "Coins: " + hero.getCoins(), 10, 30);
        font.draw(ui, "Level: " + player.getLevel(), 10, 90);
        font.draw(ui, "XP: " + player.getExperience(), 10, 60);
        font.draw(ui, "Damage: " + player.getAttack(), 10, 200);

        if (player.stuff != null) {
          font.draw(ui, "Weapon", 1800, 140);
          ui.draw(player.stuff.getTexture(), 1810, 10, 100, 100);
        }

        // Fps
        font.draw(ui, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 1050);
        int i = 300;
        for (Objects items : player.getInventory()) {
          ui.draw(items.getTexture(), 10, i, 50, 50);
          i += 50;
        }
        ui.end();
      }

      else if(island != null && hero.currentWorld == island.world) {
        batch.setProjectionMatrix(camera.combined);
        // Set the color for clear
        Gdx.gl.glClearColor(0, 0, 0, 0);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // In your update method:
        Vector3 touchPos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);

        touchPos = camera.unproject(touchPos);

        camera.update();

        if (hero != null) {
          camera.position.set(hero.pos.x, hero.pos.y, 0);
        }
        camera.update();

        if (enemies.isEmpty()) {
          island.centreTile.texture = redMushroom;
        }

      if(hero.currentWorld == island.world){
        if (
                enemies.isEmpty() &&
                        hero != null &&
                        hero.pos.dst(island.centreTile.pos) < 4
        ) {
          newLevel = true;
        }
      }

        if (newLevel && !boss) {
          island.reset(box2D, camera, player);
          hero.reset(box2D, touchPos);
          island.entities.add(hero);
          for (int i = 0; i < nbEnemies; i++) {
            Gobelin gobelin = new Gobelin(island.randomTile().pos, box2D);
            String enemyName = "Gobelin" + i;
            int attack = 20 + (i * 5);
            Enemy enemy = new Enemy(enemyName, 100, attack, 10, null, "area1", 1);
            gobelin.test(enemy);
            enemies.put(enemy, gobelin);
            gobelin.reset(box2D, island.getRandomTile());
          }
          if(hero.currentWorld == island.world){
            if (nbEnemies < 3) {
              nbEnemies++;
            } else {
              boss = true;
            }
            newLevel = false;
        }
      }

      if (enemies.isEmpty() && hero != null) {
        if (Gdx.input.isKeyJustPressed(Keys.T) && hero != null) {
          // Vérifier si le héros est actuellement au hub
          if (hero.currentWorld == hub.world) {
            // Téléportation à l'île
            island.entities.add(hero);
            hero.removeRectangel();
            island.addHitboxHero(hero, box2D);
            hero.teleportToIsland(island.centreTile1, island.world);
          } else {
            this.controlResetHub();
            hub.hubInit(box2d, hero, camera, player);
            hero.teleportToIsland(hub.centreTile, hub.world);
            hub.addHitboxHero(hero, box2d);
          }
        }
      }

        if (newLevel && boss) {
          island.reset(box2D, camera, player);
          hero.reset(box2D, touchPos);
          Gobelin boss = new Gobelin(island.randomTile().pos, box2D);
          String enemyName = "Boss";
          Enemy enemy = new Enemy(enemyName, 500, 50, 10, null, "area1", 1);
          boss.test(enemy);
          enemies.put(enemy, boss);
          boss.reset(box2D, island.getRandomTile());
          newLevel = false;
        }

        // Iterate over the entries in the enemies map
        for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
          Enemy enemy = entry.getKey();
          Gobelin gobelin = entry.getValue();

          // Check if the enemy is the boss and if its HP is less than or equal to 0
          if (enemy.getName().equals("Boss") && enemy.getHp() <= 0) {
            end = true; // Set end to true if the boss is dead
            break; // Exit the loop as we've found the boss
          }
        }

        gobelinTime += Gdx.graphics.getDeltaTime();

        if (enemies != null && hero != null) {
          for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
            float distance = Vector3.dst(
                    hero.pos.x,
                    hero.pos.y,
                    0,
                    entry.getValue().pos.x,
                    entry.getValue().pos.y,
                    0
            );
            if (player.getHp() > 0) {
              if (entry.getValue() != null) {
                entry.getValue().follow(hero);
                entry.getValue().test();
              }
            }
            if (distance < 5 && gobelinTime >= attackDelay) {
              if (player.getHp() > 0) {
                entry.getKey().attack(player);
                gobelinTime = 0.0f; // Reset timeSinceLastAttack
              } else {
                heroDied = true;
              }
            }
          }
        }

        if (heroDied && hero != null) {
          // delete hero
          island.entities.remove(hero);
          hero.removeRectangel();
          heroes.remove(hero);
          hero = null;
          heroDied = false;
        }

        HashMap<Enemy, Gobelin> resetEnemy = new HashMap<Enemy, Gobelin>();
        HashMap<Gobelin, Rectangle> resetHitbox = new HashMap<Gobelin, Rectangle>();

        if (control.reset) {
          boolean removeEntiteEnnemies = false;
          boolean removeHitbox = false;

          if (gobelin != null) {
            island.entities.remove(gobelin);
            gobelin.removeRectangel();
            enemies.remove(enemy, gobelin);
          }
          if (hero != null) {
            island.entities.remove(hero);
            hero.removeRectangel();
            heroes.remove(hero);
          }
          if (enemies.isEmpty() == false) {
            for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
              island.entities.remove(entry.getKey());
              resetEnemy.put(entry.getKey(), entry.getValue());
              removeEntiteEnnemies = true;
            }
          }
          if (hitBoxEnemy.isEmpty() == false) {
            for (HashMap.Entry<Gobelin, Rectangle> entry : hitBoxEnemy.entrySet()) {
              resetHitbox.put(entry.getKey(), entry.getValue());
              removeHitbox = true;
            }
          }
          if (removeEntiteEnnemies) {
            for (HashMap.Entry<Enemy, Gobelin> entry : resetEnemy.entrySet()) {
              entry.getValue().removeRectangel();
              island.entities.remove(entry.getValue());
              enemies.remove(entry.getKey(), entry.getValue());
            }
            enemies.clear();
          }
          if (removeHitbox) {
            for (HashMap.Entry<Gobelin, Rectangle> entry : resetHitbox.entrySet()) {
              hitBoxEnemy.remove(entry.getKey(), entry.getValue());
            }
            hitBoxEnemy.clear();
          }
          create();
          control.reset = false;
        }
        

        direction_x = 0;
        direction_y = 0;

        if (Gdx.input.isKeyJustPressed(Keys.K)) {
          player.equipWeapon();
        }

        if (Gdx.input.isKeyJustPressed(Keys.P)) {
          switch (hero.getClass().getSimpleName()) {
            case "Mage":
              System.out.println("Rogue");

              heroes.clear();
              island.entities.remove(hero);
              hero.removeRectangel();

              hero = rogue;
              heroes.add(hero);
              island.entities.add(hero);
              hero.test(player);
              hero.reset(box2D, island.getCentrePosition());
              break;
            case "Rogue":
              System.out.println("Warrior");

              heroes.clear();
              island.entities.remove(hero);
              hero.removeRectangel();

              hero = warrior;
              heroes.add(hero);
              island.entities.add(hero);
              hero.test(player);
              hero.reset(box2D, island.getCentrePosition());
              break;
            case "Warrior":
              System.out.println("Mage");

              heroes.clear();
              island.entities.remove(hero);
              hero.removeRectangel();

              hero = mage;
              heroes.add(hero);
              island.entities.add(hero);
              hero.test(player);
              hero.reset(box2D, island.getCentrePosition());
              break;
          }
        }

        if (hero != null) {
          String heroClass = hero.getClass().getSimpleName();
          Animation<Texture> animation = null;

          if (control.down) {
            direction_y = -1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageDownAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueDownAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorDownAnimation);
                break;
            }
          } else if (control.up) {
            direction_y = 1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageUpAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueUpAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorUpAnimation);
                break;
            }
          } else if (control.left) {
            direction_x = -1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageLeftAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueLeftAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorLeftAnimation);
                break;
            }
          } else if (control.right) {
            direction_x = 1;
            switch (heroClass) {
              case "Mage":
                animation = new Animation<>(0.25f, Media.MageRightAnimation);
                break;
              case "Rogue":
                animation = new Animation<>(0.25f, Media.RogueRightAnimation);
                break;
              case "Warrior":
                animation = new Animation<>(0.25f, Media.WarriorRightAnimation);
                break;
            }
          }

          if (animation != null) {
            elapsedTime += Gdx.graphics.getDeltaTime(); // Update the elapsed time
            hero.texture = animation.getKeyFrame(elapsedTime, true);
          }
        }

        // GAME LOGIC
        if (hero != null) {
          hero.update(control);
        }

        // Increase the font size
        // Increase the font size
        font.getData().setScale(2);

        // Draw the text after all other elements have been drawn
        batch.begin();

        float interactionDistance = 6f;
        List<Chest> chests = island.getChests();

        if (hero != null) {
          Vector3 heroScreenPosition = new Vector3(hero.pos.x, hero.pos.y, 0);
          Vector3 heroWorldPosition = camera.unproject(heroScreenPosition);
        }

        for (Chest chest : chests) {
          if (chests.isEmpty()) {
          }
          Vector3 chestScreenPosition = new Vector3(chest.pos.x, chest.pos.y, 0);
          Vector3 chestWorldPosition = camera.unproject(chestScreenPosition);
          if (hero != null) {
            float distance = Vector3.dst(
                    chest.pos.x,
                    chest.pos.y,
                    0,
                    hero.pos.x,
                    hero.pos.y,
                    0
            );
            if (distance <= interactionDistance) {
              font.draw(
                      batch,
                      layout,
                      (displayW - layout.width) / 2,
                      (displayH + layout.height) / 2
              );
              if (Gdx.input.isKeyJustPressed(Keys.E)) {
                chest.open();
              }
            }
          }
        }

        batch.end();

        if (enemies != null) {
          for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
            entry.getValue().test();
            gobelinBounds = new Rectangle();
            gobelinBounds.set(
                    entry.getValue().getX(),
                    entry.getValue().getY(),
                    entry.getValue().getWidth(),
                    entry.getValue().getHeight()
            );
            hitBoxEnemy.put(entry.getValue(), gobelinBounds);
          }
        }

        if (hero != null) {
          camera.position.lerp(hero.pos, .1f);
          camera.update();
        }

        Collections.sort(island.entities);

        // Draw all tiles in the chunk / chunk rows

        batch.begin();
        for (ArrayList<Tile> row : island.chunk.tiles) {
          for (Tile tile : row) {
            batch.draw(
                    tile.texture,
                    tile.pos.x,
                    tile.pos.y,
                    tile.size,
                    tile.size
            );
            if (tile.secondary_texture != null) {
              batch.draw(
                      tile.secondary_texture,
                      tile.pos.x,
                      tile.pos.y,
                      tile.size,
                      tile.size
              );
            }
          }
        }

        batch.end();

        HashMap<Enemy, Gobelin> test = new HashMap<Enemy, Gobelin>();
        HashMap<Gobelin, Rectangle> test2 = new HashMap<Gobelin, Rectangle>();

        if (hitBoxEnemy != null) {
          for (HashMap.Entry<Gobelin, Rectangle> hitBox : hitBoxEnemy.entrySet()) {
            Rectangle enemyBounds = hitBox.getValue();
            Gobelin gobelin = hitBox.getKey();

            if (
                    control.isLeftMouseBtn() &&
                            !control.isProcessedClick() &&
                            enemyBounds.contains(touchPos.x, touchPos.y)
            ) {
              for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
                if (entry.getKey().getHp() > 0) {
                  if (
                          entry.getValue().getX() == enemyBounds.getX() &&
                                  entry.getValue().getY() == enemyBounds.getY()
                  ) {
                    player.attack(entry.getKey());
                    control.setProcessedClick(true);
                  }
                } else {
                  // delete gobelin
                  test.put(entry.getKey(), entry.getValue());
                  test2.put(entry.getValue(), enemyBounds);
                  entry.getValue().dropCoins(hero);
                  entry.getValue().dropExp(player);
                  player.levelUp();
                  removeEntite = true;
                }
              }
            }
          }
          if (removeEntite) {
            for (HashMap.Entry<Enemy, Gobelin> entry : test.entrySet()) {
              entry.getValue().removeRectangel();
              island.entities.remove(entry.getValue());
              enemies.remove(entry.getKey(), entry.getValue());
            }
            for (HashMap.Entry<Gobelin, Rectangle> entry : test2.entrySet()) {
              hitBoxEnemy.remove(entry.getKey(), entry.getValue());
            }
            removeEntite = false;
          }
        }

        // Begin drawing
        batch.begin();

        for (Entity e : island.entities) {
          // i want verify if the entity is draw on the centered tile
          if (e.pos.dst(island.centreTile.pos) > 10) {
            e.draw(batch);
          }else if (e == hero) {
            e.draw(batch);
          }
        }

        for (Chest chest : chests) {
          chest.draw(batch);
        }

        for (Hero hero : heroes) {
          if (hero != null) {
            hero.draw(batch);
            hero.drawHealthBar(batch);
          }
        }

        if (enemies != null) {
          for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
            if (entry.getValue() != null) {
              entry.getValue().draw(batch);
              entry.getValue().drawHealthBar(batch);
            }
          }
        }

        // End drawing

        batch.end();

        HealthBarBatch.begin();

        if (hero != null) {
          hero.drawHealthBar(HealthBarBatch);
        }

        HealthBarBatch.end();

        if (hero == null) {
          game.setScreen(new GameOverScreen(game));
        }

        if (end) {
          game.setScreen(new WinScreen(game));
        }

        // Update the Box2D world
        box2D.tick(camera, control);

        ui.begin();
        // Stats
        font.draw(
                ui,
                "HP: " + player.getHp() + " / " + player.getMaxhp(),
                10,
                120
        );
        font.draw(ui, "Coins: " + hero.getCoins(), 10, 30);
        font.draw(ui, "Level: " + player.getLevel(), 10, 90);
        font.draw(ui, "XP: " + player.getExperience(), 10, 60);
        font.draw(ui, "Damage: " + player.getAttack(), 10, 200);

        if (player.stuff != null) {
          font.draw(ui, "Weapon", 1800, 140);
          ui.draw(player.stuff.getTexture(), 1810, 10, 100, 100);
        }

        // Fps
        font.draw(ui, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 1050);
        int i = 300;
        for (Objects items : player.getInventory()) {
          ui.draw(items.getTexture(), 10, i, 50, 50);
          i += 50;
        }
        ui.end();
      }
    }
  }

  @Override
  public void dispose() {
    super.dispose();
  }

  public void controlResetHub(){
          boolean removeEntiteEnnemies = false;
          boolean removeHitbox = false;
          HashMap<Enemy, Gobelin> resetEnemy = new HashMap<Enemy, Gobelin>();
          HashMap<Gobelin, Rectangle> resetHitbox = new HashMap<Gobelin, Rectangle>();

          if (gobelin != null) {
            island.entities.remove(gobelin);
            gobelin.removeRectangel();
            enemies.remove(enemy, gobelin);
          }
          if (hero != null) {
            island.entities.remove(hero);
            hero.removeRectangel();
            heroes.remove(hero);
          }
          if (enemies.isEmpty() == false) {
            for (HashMap.Entry<Enemy, Gobelin> entry : enemies.entrySet()) {
              island.entities.remove(entry.getKey());
              resetEnemy.put(entry.getKey(), entry.getValue());
              removeEntiteEnnemies = true;
            }
          }
          if (hitBoxEnemy.isEmpty() == false) {
            for (HashMap.Entry<Gobelin, Rectangle> entry : hitBoxEnemy.entrySet()) {
              resetHitbox.put(entry.getKey(), entry.getValue());
              removeHitbox = true;
            }
          }
          if (removeEntiteEnnemies) {
            for (HashMap.Entry<Enemy, Gobelin> entry : resetEnemy.entrySet()) {
              entry.getValue().removeRectangel();
              island.entities.remove(entry.getValue());
              enemies.remove(entry.getKey(), entry.getValue());
            }
            enemies.clear();
          }
          if (removeHitbox) {
            for (HashMap.Entry<Gobelin, Rectangle> entry : resetHitbox.entrySet()) {
              hitBoxEnemy.remove(entry.getKey(), entry.getValue());
            }
            hitBoxEnemy.clear();
          }
        }

        public void setControl(){
          Gdx.input.setInputProcessor(this.control);
        }
  }

