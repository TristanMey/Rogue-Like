package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;

public class Objects {

  private String nom;
  private final String type;
  private final String rarity;
  private int damage;
  private final int level;
  private int heal;
  private final Texture texture;

  public Objects(String type, int level, String rarity, Texture texture) {
    this.type = type;
    this.level = level;
    this.rarity = rarity;
    this.texture = texture;
    this.nom = null;
    this.damage = 0;
    this.heal = 0;
  }

  // Getter
  public String getType() {
    return type;
  }

  public int getLevel() {
    return level;
  }

  public String getRarity() {
    return rarity;
  }

  public Texture getTexture() {
    return texture;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public int getDamage() {
    return damage;
  }

  // Setter
  public void setDamage(int damage) {
    this.damage = damage;
  }

  public int getHeal() {
    return heal;
  }

  public void setHeal(int heal) {
    this.heal = heal;
  }

  public boolean equip(Player onePlayer) {
    if (onePlayer.getStuff() == null) {
      onePlayer.setStuff(this);
      return true;
    } else {
      return false;
    }
  }

  public boolean unEquip(Player onePlayer) {
    if (onePlayer.getStuff() != null) {
      onePlayer.getInventory().add(onePlayer.getStuff());
      onePlayer.setStuff(null);
      return true;
    } else {
      return false;
    }
  }
}
