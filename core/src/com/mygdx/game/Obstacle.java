package com.mygdx.game;

import com.badlogic.gdx.math.Rectangle;

public class Obstacle {

  private final Rectangle rectangle;

  public Obstacle(float x, float y, float width, float height) {
    rectangle = new Rectangle(x, y, width, height);
  }

  public Rectangle getRectangle() {
    return rectangle;
  }
}
