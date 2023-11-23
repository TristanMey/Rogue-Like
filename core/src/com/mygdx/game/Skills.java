package com.mygdx.game;

public class Skills {

  private String name;
  private int damage;
  private int manaCost;

  public Skills(String name, int damage, int manaCost) {
    this.name = name;
    this.damage = damage;
    this.manaCost = manaCost;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getDamage() {
    return damage;
  }

  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getManaCost() {
    return manaCost;
  }

  public void setManaCost(int manaCost) {
    this.manaCost = manaCost;
  }
}
