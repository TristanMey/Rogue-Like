package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Objects;

public class Sword extends Objects {

  public Sword(String nom, int level, int damage, String rarity) {
    super("WEAPON", level, rarity, new Texture("assets/Weapons/sword_01.png"));
    super.setNom(nom);
    super.setDamage(damage);
  }
}
