package com.mygdx.game.heroes;

import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Hero;
import com.mygdx.game.Media;
import com.mygdx.game.box2d.Box2DWorld;

public class Rogue extends Hero {

  public Rogue(Vector3 pos, Box2DWorld box2d) {
    super(pos, box2d);
    this.texture = Media.RogueDown;
  }
}
