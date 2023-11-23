package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.Array;

import java.util.Iterator;

public class Player extends Character {

  private final String name;
  private int level, experience;
  private Array<Objects> inventory;
  private int maxHp;

  public Player(
    String name,
    int hp,
    int attack,
    int defense,
    Objects stuff,
    String area,
    int level
  ) {
    super(hp, attack, defense, stuff, area);
    this.name = name;
    this.level = level;
    this.inventory = new Array<Objects>();
    this.maxHp = hp;
  }

  public String getName() {
    return name;
  }

  public int getMaxhp() {
    return maxHp;
  }

  public void setMaxhp(int maxHp) {
    this.maxHp = maxHp;
  }

  public int getHp(Player player) {
    return player.getHp();
  }

  public int getLevel() {
    return level;
  }

  public Array<Objects> getInventory() {
    return inventory;
  }

  public void setInventory(Array<Objects> inventory) {
    this.inventory = inventory;
  }

  public int getExperience() {
    return experience;
  }

  public void setExperience(int experience) {
    this.experience = experience;
  }

  public boolean attack(Enemy oneEnemy) {
    if (oneEnemy.getHp() > 0) {
      int damage = this.getAttack() - oneEnemy.getDefense();
      oneEnemy.setHp(oneEnemy.getHp() - damage);
      return true;
    } else {
      return false;
    }
  }

  public boolean defenses() {
    if (this.defense <= this.getDefense() + 5) {
      this.defense += 1;
      return true;
    } else {
      return false;
    }
  }

  public void pickObject(Objects object) {
    this.inventory.add(object);
    Gdx.app.log("Inventory", "You picked up " + object.getNom());
  }

  public void levelUp() {
    if (this.experience >= 100) {
      this.level += 1;
      this.setAttack(this.getAttack() + 10);
      this.setDefense(this.getDefense() + 5);
      this.setMaxhp(this.getMaxhp() + 20);
      this.experience = 0;
    }
  }

  public void equipWeapon() {
    Iterator<Objects> iterator = this.getInventory().iterator();
    while (iterator.hasNext()) {
      Objects item = iterator.next();
      if (java.util.Objects.equals(item.getType(), "WEAPON") && this.stuff == null) {
        Gdx.app.log("Equipment", "You equip " + item.getNom());
        this.setAttack(this.getAttack() + item.getDamage());
        this.stuff = item;
        iterator.remove(); // Safely remove the equipped weapon from the inventory
        return; // Exit the method after equipping one weapon
      } else if (java.util.Objects.equals(item.getType(), "POTION") && this.hp + item.getHeal() <= this.maxHp) {
        Gdx.app.log("Equipment", "You drink " + item.getNom());
        this.setHp(this.getHp() + item.getHeal());
        iterator.remove(); // Safely remove the potion from the inventory
        return; // Exit the method after drinking one potion
      }
    }
    // If no weapon found, handle it here (optional)
    Gdx.app.log("Equipment", "No weapon found in the inventory");
    // Add logic here if needed when no weapon is found
  }

  public void heal() {
    this.setHp(this.getMaxhp());
  }

  public boolean isDead() {
    return this.hp <= 0;
  }
}
