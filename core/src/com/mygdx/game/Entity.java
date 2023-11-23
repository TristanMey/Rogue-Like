package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.mygdx.game.Enums.ENTITYTYPE;

// Entity class represents a game object
public class Entity implements Comparable<Entity> {

  // Position of the entity in 3D space
  public Vector3 pos;
  // Texture of the entity
  public Texture texture;
  // Width of the entity
  public float width;
  // Height of the entity
  public float height;
  // Type of the entity
  public ENTITYTYPE type;
  // Speed of the entity
  public float speed;
  public Body body;

  // Direction of the entity on the x-axis
  float dir_x = 0;
  // Direction of the entity on the y-axis
  float dir_y = 0;

  // Constructor for the Entity class
  public Entity() {
    // Initialize the position in 2D and 3D space
    pos = new Vector3();
  }

  public float getWidth() {
    return width;
  }

  public float getHeight() {
    return height;
  }

  public float getX() {
    return pos.x;
  }

  public float getY() {
    return pos.y;
  }

  // Method to draw the entity
  public void draw(SpriteBatch batch) {
    // Draw the entity texture at its position with its width and height
    batch.draw(texture, pos.x, pos.y, width, height);
  }

  public int compareTo(Entity e) {
    float tempY = e.pos.y;
    float compareY = pos.y;

    return (tempY < compareY) ? -1 : (tempY > compareY) ? 1 : 0;
  }

  
  public void removeRectangel(){
    body.destroyFixture(body.getFixtureList().get(0));
  }
}
