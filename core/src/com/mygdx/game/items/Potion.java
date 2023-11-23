package com.mygdx.game.items;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.Objects;

public class Potion extends Objects {

  public Potion(String nom, int level, int heal, String rarity) {
    super("POTION", level, rarity, new Texture("assets/Weapons/hp_potion.png"));
    super.setNom(nom);
    super.setHeal(heal * level);
  }
}
