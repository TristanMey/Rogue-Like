package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Enums.ENTITYTYPE;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;
import com.mygdx.game.map.Hub;
import com.mygdx.game.map.Tile;

// Hero class extends Entity class
public class Hero extends Entity {

  public float health; // Current health
  public float maxHealth; // Maximum health
  public int coins; // Number of coins
  Player player;
  private Tile currentLocation;
  private Hub hub;
  public World currentWorld;
  public Box2DWorld box2D;

  // Constructor for Hero class
  public Hero(Vector3 pos, Box2DWorld box2d) {
    type = ENTITYTYPE.HERO; // Set the type of the entity to HERO
    width = 3; // Set the width of the hero
    height = 4; // Set the height of the hero
    this.pos.x = pos.x; // Set the x position of the hero in 3D space
    this.pos.y = pos.y; // Set the y position of the hero in 3D space
    speed = 10; // Set the speed of the hero
    coins = 100; // Set the number of coins to 0
    this.box2D = box2d;

    reset(box2d, pos);
    body =
      Box2DHelper.createBody(
        box2d.world,
        width / 2,
        height / 2,
        width / 2,
        0,
        pos,
        BodyType.DynamicBody
      );
  }

  public void reset(Box2DWorld box2d, Vector3 pos) {
    this.pos.set(pos);
    body =
      Box2DHelper.createBody(
        box2d.world,
        width,
        height,
        0,
        0,
        pos,
        BodyType.DynamicBody
      );
  }

  public void test(Player player) {
    this.player = player;
    this.health = player.getHp();
    this.maxHealth = player.getMaxhp();
  }

  // Update method for Hero class
  public void update(Control control) {
    dir_x = 0; // Reset the x direction
    dir_y = 0; // Reset the y direction

    // Update the direction based on the control input
    if (control.down) dir_y = -1;
    if (control.up) dir_y = 1;
    if (control.left) dir_x = -1;
    if (control.right) dir_x = 1;

    body.setLinearVelocity(dir_x * speed, dir_y * speed);
    pos.x = body.getPosition().x - width / 2;
    pos.y = body.getPosition().y - height / 4;
  }

  public void update(Texture texture) {
    this.texture = texture;
  }

  public void test() {
    pos.x = body.getPosition().x - width / 2;
    pos.y = body.getPosition().y - height / 4;
  }

  public void follow(Hero target) {
    float distance = Vector3.dst(
      target.pos.x,
      target.pos.y,
      0,
      this.pos.x,
      this.pos.y,
      0
    );
    if (distance > 5) {
      // Calculate the direction towards the target
      float directionX = target.getX() - this.pos.x;
      float directionY = target.getY() - this.pos.y;

      this.body.setLinearVelocity(directionX * 0.2f, directionY * 0.2f);
      this.pos.x = body.getPosition().x - width / 2;
      this.pos.y = body.getPosition().y - height / 4;
      this.setPosition(this.pos.x, this.pos.y);
    } else {
      this.body.setLinearVelocity(0, 0);
    }
  }

  public int getCoins() {
    return coins;
  }

  public void setPosition(float x, float y) {
    this.pos.x = x;
    this.pos.y = y;
  }

  public void removeRectangel() {
    body.destroyFixture(body.getFixtureList().get(0));
  }

  // Method to get the x position of the camera
  public float get_camera_x() {
    return pos.x + width / 2; // Return the x position of the hero plus half the width
  }

  // Method to get the y position of the camera
  public float get_camera_y() {
    return pos.y + height / 2; // Return the y position of the hero plus half the height
  }

  public Vector2 getPosition() {
    return new Vector2(pos.x, pos.y);
  }

  public void drawHealthBar(SpriteBatch batch) {
    Texture healthBarTexture = new Texture("assets/Heroes/HealthBar.png");
    float healthBarHeight = 1;

    this.health = player.getHp();
    this.maxHealth = player.getMaxhp();
    // Calculate the width of the health bar based on the hero's current health
    float healthPercentage = health / maxHealth;
    float healthBarWidth = width * healthPercentage;
    // Draw the health bar
    batch.draw(
      healthBarTexture,
      pos.x,
      pos.y + height,
      healthBarWidth,
      healthBarHeight
    );
  }

  public void teleportToIsland(Tile destinationTile, World currentWorld) {
    this.pos.set(destinationTile.pos);
    this.setCurrentLocation(destinationTile);
    this.currentWorld = currentWorld;
  }

  public Tile getCurrentLocation() {
    return this.currentLocation;
  }

  public void setCurrentLocation(Tile currentLocation) {
    this.currentLocation = currentLocation;
  }

  public void setCoins(int coins) {
      this.coins = coins;
  }
}
