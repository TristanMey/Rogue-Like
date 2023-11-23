package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;

public class Media {

  //Hero
  public static Texture WarriorDown, WarriorUp, WarriorLeft, WarriorRight;
  public static Array<Texture> WarriorDownAnimation, WarriorUpAnimation, WarriorLeftAnimation, WarriorRightAnimation;
  public static Texture MageDown, MageUp, MageLeft, MageRight;
  public static Array<Texture> MageDownAnimation, MageUpAnimation, MageLeftAnimation, MageRightAnimation;
  public static Texture RogueDown, RogueUp, RogueLeft, RogueRight;
  public static Array<Texture> RogueDownAnimation, RogueUpAnimation, RogueLeftAnimation, RogueRightAnimation;
  public static Texture gobelin, skeleton, slime;
  public static Texture npc;

  //Tiles
  public static Texture grass01, grass02, grass03, grass04;
  public static Texture grassLeft, grassRight;
  public static Texture grassLeftUpperEdge, grassRightUpperEdge;
  public static Texture grassTop, grassTopRight, grassTopLeft;
  public static Texture water01, water02, water03, water04;
  public static Texture cliff, water;

  //Entity
  public static Texture tree;
  public static Texture chest, chestOpen;

  public static void load_assets() {
    WarriorDown = new Texture("assets/Heroes/WarriorDown.png");
    WarriorUp = new Texture("assets/Heroes/WarriorUp.png");
    WarriorLeft = new Texture("assets/Heroes/WarriorLeft.png");
    WarriorRight = new Texture("assets/Heroes/WarriorRight.png");
    npc = new Texture("assets/Heroes/Npc.png");

    WarriorDownAnimation = new Array<Texture>();
    WarriorDownAnimation.add(new Texture("assets/Heroes/WarriorDown2.png"));
    WarriorDownAnimation.add(WarriorDown);
    WarriorDownAnimation.add(new Texture("assets/Heroes/WarriorDown3.png"));

    WarriorUpAnimation = new Array<Texture>();
    WarriorUpAnimation.add(new Texture("assets/Heroes/WarriorUp2.png"));
    WarriorUpAnimation.add(WarriorUp);
    WarriorUpAnimation.add(new Texture("assets/Heroes/WarriorUp3.png"));

    WarriorLeftAnimation = new Array<Texture>();
    WarriorLeftAnimation.add(new Texture("assets/Heroes/WarriorLeft2.png"));
    WarriorLeftAnimation.add(WarriorLeft);
    WarriorLeftAnimation.add(new Texture("assets/Heroes/WarriorLeft3.png"));

    WarriorRightAnimation = new Array<Texture>();
    WarriorRightAnimation.add(new Texture("assets/Heroes/WarriorRight2.png"));
    WarriorRightAnimation.add(WarriorRight);
    WarriorRightAnimation.add(new Texture("assets/Heroes/WarriorRight3.png"));

    MageDown = new Texture("assets/Heroes/MageDown.png");
    MageUp = new Texture("assets/Heroes/MageUp.png");
    MageLeft = new Texture("assets/Heroes/MageLeft.png");
    MageRight = new Texture("assets/Heroes/MageRight.png");

    MageDownAnimation = new Array<Texture>();
    MageDownAnimation.add(new Texture("assets/Heroes/MageDown2.png"));
    MageDownAnimation.add(MageDown);
    MageDownAnimation.add(new Texture("assets/Heroes/MageDown3.png"));

    MageUpAnimation = new Array<Texture>();
    MageUpAnimation.add(new Texture("assets/Heroes/MageUp2.png"));
    MageUpAnimation.add(MageUp);
    MageUpAnimation.add(new Texture("assets/Heroes/MageUp3.png"));

    MageLeftAnimation = new Array<Texture>();
    MageLeftAnimation.add(new Texture("assets/Heroes/MageLeft2.png"));
    MageLeftAnimation.add(MageLeft);
    MageLeftAnimation.add(new Texture("assets/Heroes/MageLeft3.png"));

    MageRightAnimation = new Array<Texture>();
    MageRightAnimation.add(new Texture("assets/Heroes/MageRight2.png"));
    MageRightAnimation.add(MageRight);
    MageRightAnimation.add(new Texture("assets/Heroes/MageRight3.png"));

    RogueDown = new Texture("assets/Heroes/RogueDown.png");
    RogueUp = new Texture("assets/Heroes/RogueUp.png");
    RogueLeft = new Texture("assets/Heroes/RogueLeft.png");
    RogueRight = new Texture("assets/Heroes/RogueRight.png");

    RogueDownAnimation = new Array<Texture>();
    RogueDownAnimation.add(new Texture("assets/Heroes/RogueDown2.png"));
    RogueDownAnimation.add(RogueDown);
    RogueDownAnimation.add(new Texture("assets/Heroes/RogueDown3.png"));

    RogueUpAnimation = new Array<Texture>();
    RogueUpAnimation.add(new Texture("assets/Heroes/RogueUp2.png"));
    RogueUpAnimation.add(RogueUp);
    RogueUpAnimation.add(new Texture("assets/Heroes/RogueUp3.png"));

    RogueLeftAnimation = new Array<Texture>();
    RogueLeftAnimation.add(new Texture("assets/Heroes/RogueLeft2.png"));
    RogueLeftAnimation.add(RogueLeft);
    RogueLeftAnimation.add(new Texture("assets/Heroes/RogueLeft3.png"));

    RogueRightAnimation = new Array<Texture>();
    RogueRightAnimation.add(new Texture("assets/Heroes/RogueRight2.png"));
    RogueRightAnimation.add(RogueRight);
    RogueRightAnimation.add(new Texture("assets/Heroes/RogueRight3.png"));

    grass01 = new Texture("assets/grass/grass_01.png");
    grass02 = new Texture("assets/grass/grass_02.png");
    grass03 = new Texture("assets/grass/grass_03.png");
    grass04 = new Texture("assets/grass/grass_04.png");

    grassLeft = new Texture("assets/grass/right_grass_edge.png");
    grassRight = new Texture("assets/grass/left_grass_edge.png");

    grassLeftUpperEdge = new Texture("assets/grass/left_upper_edge.png");
    grassRightUpperEdge = new Texture("assets/grass/right_upper_edge.png");

    grassTop = new Texture("assets/grass/top.png");
    grassTopRight = new Texture("assets/grass/top_right.png");
    grassTopLeft = new Texture("assets/grass/top_left.png");

    water01 = new Texture("assets/water/water_01.png");
    water02 = new Texture("assets/water/water_02.png");
    water03 = new Texture("assets/water/water_03.png");
    water04 = new Texture("assets/water/water_04.png");
    cliff = new Texture("assets/grass/cliff.png");

    tree = new Texture("assets/Trees/tree.png");
    chest = new Texture("assets/Chests/chest.png");
    chestOpen = new Texture("assets/Chests/chestOpen.png");
    gobelin = new Texture("assets/enemy/Gobelin.png");
    skeleton = new Texture("assets/enemy/Skeleton.png");
    slime = new Texture("assets/enemy/Slime.png");
  }

  public void dispose() {
    grass01.dispose();
    grass02.dispose();
    grass03.dispose();
    grass04.dispose();
    grassLeft.dispose();
    grassRight.dispose();
    grassLeftUpperEdge.dispose();
    grassRightUpperEdge.dispose();
    grassTop.dispose();
    grassTopRight.dispose();
    grassTopLeft.dispose();
    water01.dispose();
    water02.dispose();
    water03.dispose();
    water04.dispose();
    cliff.dispose();
    tree.dispose();
    chest.dispose();
  }
}
