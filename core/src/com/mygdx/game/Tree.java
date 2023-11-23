package com.mygdx.game;

import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.mygdx.game.Enums.ENTITYTYPE;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;

public class Tree extends Entity {

  public Tree(Vector3 pos, Box2DWorld box2d) {
    super();
    type = ENTITYTYPE.TREE;
    width = 8;
    height = 8;
    this.pos = pos;
    texture = Media.tree;
    body =
      Box2DHelper.createBody(
        box2d.world,
        width / 2,
        height / 2,
        width / 4,
        0,
        pos,
        BodyDef.BodyType.StaticBody
      );
  }

  
  public void removeRectangel(){
    body.destroyFixture(body.getFixtureList().get(0));
  }
}
