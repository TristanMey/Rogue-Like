package com.mygdx.game;

import com.badlogic.gdx.Game;

public class GameTable extends Game {

  public static boolean showMenu = true;

  @Override
  public void create() {
    setScreen(new PixelSwordAdventures(this));
  }

  @Override
  public void dispose() {
    super.dispose();
  }
}
