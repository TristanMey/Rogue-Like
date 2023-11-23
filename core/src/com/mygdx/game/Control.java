package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;

public class Control extends InputAdapter {

  public static boolean azerty = true;
  // DIRECTIONS
  public boolean up;
  public boolean down;
  public boolean left;
  public boolean right;
  // MOUSE
  public boolean leftMouseBtn;
  public boolean rightMouseBtn;
  public boolean processedClick;
  public Vector2 mouseClickPos = new Vector2();
  public Vector2 mapClickPos = new Vector2();
  // DEBUG
  public boolean debug;
  public boolean reset;
  // CAMERA
  OrthographicCamera camera;
  // SCREEN
  int screenWidth;
  int screenHeight;

  public Control(int screenWidth, int screenHeight, OrthographicCamera camera) {
    this.camera = camera;
    this.screenWidth = screenWidth;
    this.screenHeight = screenHeight;
  }

  private void setMouseClickedPos(int screenX, int screenY) {
    // Set mouse position (flip screen Y)
    mouseClickPos.set(screenX, screenHeight - screenY);
    mapClickPos.set(get_map_coords(mouseClickPos));
  }

  public Vector2 get_map_coords(Vector2 mouseCoords) {
    Vector3 v3 = new Vector3(mouseCoords.x, screenHeight - mouseCoords.y, 0);
    this.camera.unproject(v3);
    return new Vector2(v3.x, v3.y);
  }

  @Override
  public boolean keyDown(int keyCode) {
    if (azerty) {
      switch (keyCode) {
        case Keys.DOWN:
          down = true;
          break;
        case Keys.UP:
          up = true;
          break;
        case Keys.LEFT:
          left = true;
          break;
        case Keys.RIGHT:
          right = true;
          break;
        case Keys.W:
          up = true;
          break;
        case Keys.A:
          left = true;
          break;
        case Keys.S:
          down = true;
          break;
        case Keys.D:
          right = true;
          break;
      }
    } else if (!azerty) {
      switch (keyCode) {
        case Keys.DOWN:
          down = true;
          break;
        case Keys.UP:
          up = true;
          break;
        case Keys.LEFT:
          left = true;
          break;
        case Keys.RIGHT:
          right = true;
          break;
        case Keys.Z:
          up = true;
          break;
        case Keys.Q:
          left = true;
          break;
        case Keys.S:
          down = true;
          break;
        case Keys.D:
          right = true;
          break;
        case Keys.ENTER:
          Gdx.app.exit();
          break;
      }
    }
    return false;
  }

  @Override
  public boolean keyUp(int keycode) {
    if (azerty) {
      switch (keycode) {
        case Keys.DOWN:
          down = false;
          break;
        case Keys.UP:
          up = false;
          break;
        case Keys.LEFT:
          left = false;
          break;
        case Keys.RIGHT:
          right = false;
          break;
        case Keys.W:
          up = false;
          break;
        case Keys.A:
          left = false;
          break;
        case Keys.S:
          down = false;
          break;
        case Keys.D:
          right = false;
          break;
        case Keys.ESCAPE:
          GameTable.showMenu = true;
          break;
        case Keys.BACKSPACE:
          debug = !debug;
          break;
        case Keys.R:
          reset = true;
          break;
        case Keys.ENTER:
          Gdx.app.exit();
          break;
      }
    } else if (!azerty) {
      switch (keycode) {
        case Keys.DOWN:
          down = false;
          break;
        case Keys.UP:
          up = false;
          break;
        case Keys.LEFT:
          left = false;
          break;
        case Keys.RIGHT:
          right = false;
          break;
        case Keys.Z:
          up = false;
          break;
        case Keys.Q:
          left = false;
          break;
        case Keys.S:
          down = false;
          break;
        case Keys.D:
          right = false;
          break;
        case Keys.ESCAPE:
          GameTable.showMenu = true;
          break;
        case Keys.BACKSPACE:
          debug = !debug;
          break;
        case Keys.R:
          reset = true;
          break;
      }
    }

    return false;
  }

  @Override
  public boolean touchDown(int screenX, int screenY, int pointer, int button) {
    if (pointer == 0 && button == 0) {
      leftMouseBtn = true;
      processedClick = false;
    } else if (button == 1) {
      rightMouseBtn = true;
      processedClick = false;
    }

    setMouseClickedPos(screenX, screenY);
    return false;
  }

  @Override
  public boolean touchUp(int screenX, int screenY, int pointer, int button) {
    if (pointer == 0 && button == 0) {
      leftMouseBtn = false;
    } else if (button == 1) {
      rightMouseBtn = false;
    }

    setMouseClickedPos(screenX, screenY);
    return false;
  }

  @Override
  public boolean touchDragged(int screenX, int screenY, int pointer) {
    setMouseClickedPos(screenX, screenY);
    return false;
  }

  public boolean scrolled(int amount) {
    return false;
  }

  public boolean isLeftMouseBtn() {
    return leftMouseBtn;
  }

  public boolean isProcessedClick() {
    return processedClick;
  }

  public void setProcessedClick(boolean processedClick) {
    this.processedClick = processedClick;
  }
}
