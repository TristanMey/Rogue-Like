package com.mygdx.game.map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.*;
import com.mygdx.game.Enums.TILETYPE;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Island {

  public Tile centreTile1;
  public Tile centreTile;
  // ONE CHUNK
  public Chunk chunk;
  public ArrayList<Entity> entities = new ArrayList<Entity>();

  public World world;
    Tile clickedTile;
  Player player;
  Chest chest;
  // TRACK CLICK
  int currentTileNo;
  int currentCol;
  int currentRow;
  // Arrays for mapping code to texture
  String[] aGrassLeft = { "001001001", "001001001", "001001000", "000001001" };
  String[] aGrassRight = { "100100100", "100100000", "000100100" };
  String[] aGrassREnd = { "100000000" };
  String[] aGrassLEnd = { "001000000" };
  String[] aGrassTop = { "000000111", "000000011", "000000110" };
  String[] aGrassTopRight = { "000000100" };
  String[] aGrassTopLeft = { "000000001" };
  private final ArrayList<Chest> chests = new ArrayList<Chest>();

  public Island(Box2DWorld box2D, Camera camera, Player player) {
    this.player = player;
    setupTiles();
    codeTiles();
    reset(box2D, camera, player);
  }

  public ArrayList<Chest> getChests() {
    return chests;
  }

  public void reset(Box2DWorld box2D, Camera camera, Player player) {
    this.player = player;
    entities.clear();
    box2D.clearAllBodies();
    setupTiles();
    codeTiles();
    addEntities(box2D);
    generateHitboxes(box2D);
    chests.clear();
    addChests(box2D, player, camera);
  }

  private void generateHitboxes(Box2DWorld box2D) {
    for (int i = 0; i < chunk.tiles.size(); i++) {
      for (int j = 0; j < chunk.tiles.get(i).size(); j++) {
        Tile tile = chunk.tiles.get(i).get(j);
        if (tile.is_not_passable() && tile.notIsAllWater()) {
          // Shift the hitbox one tile to the left and bottom
          Vector3 pos = new Vector3(
            tile.pos.x - chunk.tileSize,
            tile.pos.y - chunk.tileSize,
            0
          );
          // Exclude the top and right tiles
          if (i < chunk.tiles.size() - 1 && j < chunk.tiles.get(i).size() - 1) {
            Box2DHelper.createBody(
              box2D.world,
              chunk.tileSize,
              8,
              8,
              chunk.tileSize,
              pos,
              BodyType.StaticBody
            );
          }
        }
      }
    }
  }

  private void setupTiles() {
    chunk = new Chunk(33, 33, 8);

    int currentRow = 0;
    int rngW = MathUtils.random(5, 8);
    int rngH = MathUtils.random(5, 8);

    int centreTileRow = chunk.numberRows / 2;
    int centreTileCol = chunk.numberCols / 2;
    int firstTileRow = centreTileRow - (rngH);

    int maxRow = centreTileRow + rngH;
    int minRow = centreTileRow - rngH;
    int maxCol = centreTileCol + rngW;
    int minCol = centreTileCol - rngW;

    // CHUNK ROW
    ArrayList<Tile> chunkRow = new ArrayList<Tile>();

    // If number of tiles is needed.
    // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

    for (int row = 0; row < chunk.numberRows; row++) {
      for (int col = 0; col < chunk.numberCols; col++) {
        // Create TILE
        Tile tile = new Tile(
          col,
          row,
          chunk.tileSize,
          TILETYPE.WATER,
          randomWater()
        );

        // Make a small island
        if (row > minRow && row < maxRow && col > minCol && col < maxCol) {
          tile.texture = randomGrass();
          tile.type = TILETYPE.GRASS;

          if (row == firstTileRow + 1) {
            tile.texture = Media.cliff;
            tile.type = TILETYPE.CLIFF;
          } else {
            // Chance to add trees etc
          }
        }

        // ADD TILE TO CHUNK
        if (currentRow == row) {
          // Add tile to current row
          chunkRow.add(tile);

          // Last row and column?
          if (row == chunk.numberRows - 1 && col == chunk.numberCols - 1) {
            chunk.tiles.add(chunkRow);
          }
        } else {
          // New row
          currentRow = row;

          // Add row to chunk
          chunk.tiles.add(chunkRow);

          // Clear chunk row
          chunkRow = new ArrayList<Tile>();

          // Add first tile to the new row
          chunkRow.add(tile);
        }
      }
    }

    // Set centre tile for camera positioning
    centreTile = chunk.getTile(centreTileRow, centreTileCol);
    centreTile1 = chunk.getTile(centreTileRow + 3, centreTileCol+3);
  }

  public Tile randomTile() {
    // Create a list to hold the grass tiles
    List<Tile> grassTiles = new ArrayList<>();

    // Iterate over all tiles
    for (int row = 0; row < chunk.numberRows; row++) {
      for (int col = 0; col < chunk.numberCols; col++) {
        Tile tile = chunk.getTile(row, col);

        // If the tile is a grass tile, add it to the list
        if (tile.type == TILETYPE.GRASS) {
          grassTiles.add(tile);
        }
      }
    }

    // If there are no grass tiles, return null
    if (grassTiles.isEmpty()) {
      return null;
    }

    // Select a random grass tile
    int randomIndex = MathUtils.random(grassTiles.size() - 1);
    return grassTiles.get(randomIndex);
  }

  private void updateImage(Tile tile) {
    // Secondary Texture is to add edges to tiles
    if (Arrays.asList(aGrassLeft).contains(tile.code)) {
      tile.secondary_texture = Media.grassLeft;
    } else if (Arrays.asList(aGrassRight).contains(tile.code)) {
      tile.secondary_texture = Media.grassRight;
    } else if (Arrays.asList(aGrassREnd).contains(tile.code)) {
      tile.secondary_texture = Media.grassLeftUpperEdge;
    } else if (Arrays.asList(aGrassLEnd).contains(tile.code)) {
      tile.secondary_texture = Media.grassRightUpperEdge;
    } else if (Arrays.asList(aGrassTop).contains(tile.code)) {
      tile.secondary_texture = Media.grassTop;
    } else if (Arrays.asList(aGrassTopRight).contains(tile.code)) {
      tile.secondary_texture = Media.grassTopRight;
    } else if (Arrays.asList(aGrassTopLeft).contains(tile.code)) {
      tile.secondary_texture = Media.grassTopLeft;
    }
  }

  private Texture randomGrass() {
    Texture grass;

    int tile = MathUtils.random(20);
    switch (tile) {
      case 1:
        grass = Media.grass01;
        break;
      case 2:
        grass = Media.grass02;
        break;
      case 3:
        grass = Media.grass03;
        break;
      case 4:
        grass = Media.grass04;
        break;
      default:
        grass = Media.grass01;
        break;
    }

    return grass;
  }

  private Texture randomWater() {
    Texture water;

    int tile = MathUtils.random(20);
    switch (tile) {
      case 1:
        water = Media.water01;
        break;
      case 2:
        water = Media.water02;
        break;
      case 3:
        water = Media.water03;
        break;
      case 4:
        water = Media.water04;
        break;
      default:
        water = Media.water01;
        break;
    }

    return water;
  }

  private void codeTiles() {
    // Loop all tiles and set the initial code

    // 1 CHUNK ONLY ATM
    for (ArrayList<Tile> row : chunk.tiles) {
      for (Tile tile : row) {
        // Check all surrounding tiles and set 1 for pass 0 for non pass
        // 0 0 0
        // 0 X 0
        // 0 0 0

        int[] rows = { 1, 0, -1 };
        int[] cols = { -1, 0, 1 };

        for (int r : rows) {
          for (int c : cols) {
            tile.code += chunk.getTileCode(tile.row + r, tile.col + c);
            updateImage(tile);
          }
        }
      }
    }
  }

  private void addEntities(Box2DWorld box2D) {
    // Loop all tiles and add random trees
    for (ArrayList<Tile> row : chunk.tiles) {
      for (Tile tile : row) {
        if (tile.is_grass()) {
          if (MathUtils.random(100) > 90) {
              Tree tree = new Tree(getRandomTile(), box2D);
              if(tree.pos.dst(centreTile.pos) > 10){
                entities.add(tree);
              }
            }
          }
        }
      }
    }
  

  public Vector3 getCentrePosition() {
    return centreTile.pos;
  }

  public Vector3 getRandomTile() {
    return randomTile().pos;
  }

  public void addHitboxHero(Hero hero, Box2DWorld box2d){
    hero.reset(box2d, centreTile1.pos);
  }

  public void dispose() {}

  public List<Chest> addChests(Box2DWorld box2D, Player player, Camera camera) {
    List<Chest> newChests = new ArrayList<>();
    // Loop all tiles and add random trees
    for (ArrayList<Tile> row : chunk.tiles) {
      for (Tile tile : row) {
        if (tile.is_grass()) {
          if (MathUtils.random(100) > 90) {
            if (newChests.isEmpty()) {
              Chest chest = new Chest(getRandomTile(), box2D, player); // Pass player here
              if(chest.pos.dst(centreTile.pos) > 10){
                entities.add(chest);
                newChests.add(chest);
                chests.add(chest); // Add chest to chests
              }
            }
          }
        }
      }
    }
    return newChests;
  }

}
