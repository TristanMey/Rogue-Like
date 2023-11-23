package com.mygdx.game;

public abstract class Character {

  protected int hp;
  protected int attack;
  protected int defense;
  protected Objects stuff;
  protected String area;

  public Character(
    int hp,
    int attack,
    int defense,
    Objects stuff,
    String area
  ) {
    this.hp = hp;
    this.attack = attack;
    this.defense = defense;
    this.stuff = stuff;
    this.area = area;
  }

  public int getHp() {
    return hp;
  }

  public void setHp(int hp) {
    this.hp = hp;
  }

  public int getAttack() {
    return attack;
  }

  public void setAttack(int attack) {
    this.attack = attack;
  }

  public int getDefense() {
    return defense;
  }

  public void setDefense(int defense) {
    this.defense = defense;
  }

  public Objects getStuff() {
    return stuff;
  }

  public void setStuff(Objects stuff) {
    this.stuff = stuff;
  }

  public String getArea() {
    return area;
  }

  public void setArea(String area) {
    this.area = area;
  }
}
