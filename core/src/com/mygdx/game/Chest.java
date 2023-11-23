package com.mygdx.game;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Enums.ENTITYTYPE;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;
import com.mygdx.game.items.Bow;
import com.mygdx.game.items.Potion;
import com.mygdx.game.items.Sword;
import com.mygdx.game.items.Wand;
import com.mygdx.game.map.Island;
import java.util.ArrayList;

public class Chest extends Entity {

  Island island;
  ArrayList<Objects> inventaire;
  Player player;
  Vector3 screenPos = new Vector3();
  private boolean isOpen;

  public Chest(Vector3 pos, Box2DWorld box2d, Player player) {
    super();
    type = ENTITYTYPE.CHEST;
    width = 4;
    height = 4;
    this.pos = pos;
    texture = Media.chest;
    this.player = player;
    body =
      Box2DHelper.createBody(
        box2d.world,
        width,
        height,
        0,
        0,
        pos,
        BodyDef.BodyType.StaticBody
      );
    inventaire = new ArrayList<Objects>();
    addItems();
  }

  public void open() {
    if (!isOpen) {
      isOpen = true;
      for (Objects item : inventaire) {
        player.pickObject(item);
      }
      // Clear the chest's inventory
      inventaire.clear();
      texture = Media.chestOpen;
    }
  }

  public void addItems() {
    int rate = MathUtils.random(1, 100);

    if (rate >= 50) { //Common
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Common"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Common"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Common"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Common"));
          break;
        default:
          break;
      }
    } else if (rate >= 25) { //Uncommon
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Uncommon"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Uncommon"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Uncommon"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Uncommon"));
          break;
        default:
          break;
      }
    } else if (rate >= 15) { //Rare
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Rare"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Rare"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Rare"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Rare"));
          break;
        default:
          break;
      }
    } else if (rate >= 7) { //Epic
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Epic"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Epic"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Epic"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Epic"));
          break;
        default:
          break;
      }
    } else if (rate >= 3) { //Legendary
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Legendary"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Legendary"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Legendary"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Legendary"));
          break;
        default:
          break;
      }
    } else { //Mythic
      rate = MathUtils.random(1, 4);

      switch (rate) {
        case 1:
          inventaire.add(new Sword("Sword", 1, 10, "Mythic"));
          break;
        case 2:
          inventaire.add(new Bow("Bow", 1, 10, "Mythic"));
          break;
        case 3:
          inventaire.add(new Wand("Wand", 1, 10, "Mythic"));
          break;
        case 4:
          inventaire.add(new Potion("HP Potion", 1, 10, "Mythic"));
          break;
        default:
          break;
      }
    }
  }

  public void showItems() {}
}
