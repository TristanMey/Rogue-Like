package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Entity;
import com.mygdx.game.Enums.TILETYPE;

public class Tile extends Entity {

  public int size;
  public int row;
  public int col;
  public String code;
  public Texture secondary_texture;
  public Texture texture;
  public TILETYPE type;

  public Tile(float x, float y, int size, TILETYPE type, Texture texture) {
    super();
    pos.x = x * size;
    pos.y = y * size;
    this.size = size;
    this.texture = texture;
    this.col = (int) x;
    this.row = (int) y;
    this.type = type;
    this.code = "";
  }

  public String details() {
    return (
      "x :" +
      pos.x +
      " y: " +
      pos.y +
      " row: " +
      row +
      " col: " +
      col +
      " code: " +
      code +
      " type: " +
      type.toString()
    );
  }

  public boolean is_grass() {
    return type == TILETYPE.GRASS;
  }

  public boolean is_Tree() {
    return type == TILETYPE.TREES;
  }

  public boolean is_Water() {
    return type == TILETYPE.WATER;
  }

  public boolean is_Cliff() {
    return type == TILETYPE.CLIFF;
  }

  public boolean is_Mushroom() {
    return type == TILETYPE.REDMUSHROOM;
  }

  public boolean is_passable() {
    return !is_Tree() && !is_Water() && !is_Cliff() && !is_Mushroom();
  }

  public boolean is_not_passable() {
    return !is_passable();
  }

  public boolean isAllWater() {
    return code.equals("000000000");
  }

  public boolean notIsAllWater() {
    return !isAllWater();
  }
}
