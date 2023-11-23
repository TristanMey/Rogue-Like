package com.mygdx.game;

public class Class {

  private int mana;
  private int intelligence;
  private int dexterity;
  private int luck;
  private Skills skill;

  public Class(
    int mana,
    int intelligence,
    int dexterity,
    int luck,
    Skills skill
  ) {
    this.mana = mana;
    this.intelligence = intelligence;
    this.dexterity = dexterity;
    this.luck = luck;
    this.skill = skill;
  }

  public int getMana() {
    return mana;
  }

  public void setMana(int mana) {
    this.mana = mana;
  }

  public int getIntelligence() {
    return intelligence;
  }

  public void setIntelligence(int intelligence) {
    this.intelligence = intelligence;
  }

  public int getDexterity() {
    return dexterity;
  }

  public void setDexterity(int dexterity) {
    this.dexterity = dexterity;
  }

  public int getLuck() {
    return luck;
  }

  public void setLuck(int luck) {
    this.luck = luck;
  }

  public Skills getSkill() {
    return skill;
  }

  public void setSkill(Skills skill) {
    this.skill = skill;
  }

  public boolean useSkill(Enemy oneEnemy) {
    if (this.mana >= this.getSkill().getManaCost()) {
      int damage = this.getSkill().getDamage() - oneEnemy.getDefense();
      oneEnemy.setHp(oneEnemy.getHp() - damage);
      this.mana -= this.getSkill().getManaCost();
      return true;
    } else {
      return false;
    }
  }
}
