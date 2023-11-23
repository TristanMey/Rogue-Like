package com.mygdx.game;

public class Enemy extends Character {

  private String name;
  private int experience;
  private final int maxhp;

  public Enemy(
    String name,
    int hp,
    int attack,
    int defense,
    Objects stuff,
    String area,
    int experience
  ) {
    super(hp, attack, defense, stuff, area);
    this.name = name;
    this.maxhp = hp;
    this.experience = experience;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public boolean attack(Player onePlayer) {
    if (onePlayer.getHp() > 0) {
      int damage = this.getAttack() - onePlayer.getDefense();
      if (damage <= 0) {
        damage = 0;
      }
      onePlayer.setHp(onePlayer.getHp() - damage);
      return true;
    } else {
      return false;
    }
  }

  public boolean isDead(Player onePlayer) {
    if (this.getHp() <= 0) {
      onePlayer.setExperience(onePlayer.getExperience() + this.getExperience());
      onePlayer.levelUp();
      return true;
    } else {
      return false;
    }
  }

  public boolean defense() {
    if (this.defense <= this.getDefense() + 5) {
      this.defense += 1;
      return true;
    } else {
      return false;
    }
  }

  public int getMaxhp() {
    return this.maxhp;
  }
}
