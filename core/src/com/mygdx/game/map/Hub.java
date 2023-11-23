package com.mygdx.game.map;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Chest;
import com.mygdx.game.Entity;
import com.mygdx.game.Enums.TILETYPE;
import com.mygdx.game.Hero;
import com.mygdx.game.Media;
import com.mygdx.game.Player;
import com.mygdx.game.Tree;
import com.mygdx.game.Npc;
import com.mygdx.game.box2d.Box2DHelper;
import com.mygdx.game.box2d.Box2DWorld;


public class Hub {
    public Tile centreTile;
    Tile clickedTile;
    Player player;
    Hero hero;
    public World world;
    Npc npc;
    private ArrayList<Npc> Npcs = new ArrayList<Npc>();


    public ArrayList<Entity> entities = new ArrayList<Entity>();

    public Chunk chunk;

    String[] aGrassLeft = {"001001001","001001001", "001001000", "000001001"};
    String[] aGrassRight = {"100100100","100100000","000100100"};
    String[] aGrassREnd = {"100000000"};
    String[] aGrassLEnd = {"001000000"};
    String[] aGrassTop = {"000000111", "000000011","000000110"};
    String[] aGrassTopRight = {"000000100"};
    String[] aGrassTopLeft = {"000000001"};


    public Hub(Box2DWorld box2d,Camera camera, Player player, Hero hero){
        this.player = player;
        world = box2d.world;
        setupTiles();
        codeTiles();
        hubInit(box2d, hero, camera, player); 
    }

    public void hubInit(Box2DWorld box2d, Hero hero, Camera camera, Player player) {
        entities.add(hero);
        addEntities(box2d);
        addNpc(box2d, player, camera);
        generateHitboxesHub(box2d);
    }

    public ArrayList<Npc> getNpc() {
        return Npcs;
    }

    public void addHitboxHero(Hero hero, Box2DWorld box2d){
      hero.reset(box2d, getCentrePosition());
    }

    private void updateImage(Tile tile) {
        // Secondary Texture is to add edges to tiles
        if(Arrays.asList(aGrassLeft).contains(tile.code)){
            tile.secondary_texture = Media.grassLeft;
        } else if(Arrays.asList(aGrassRight).contains(tile.code)){
            tile.secondary_texture = Media.grassRight;
        } else if(Arrays.asList(aGrassREnd).contains(tile.code)){
            tile.secondary_texture = Media.grassLeftUpperEdge;
        } else if(Arrays.asList(aGrassLEnd).contains(tile.code)){
            tile.secondary_texture = Media.grassRightUpperEdge;
        } else if(Arrays.asList(aGrassTop).contains(tile.code)){
            tile.secondary_texture = Media.grassTop;
        } else if(Arrays.asList(aGrassTopRight).contains(tile.code)){
            tile.secondary_texture = Media.grassTopRight;
        } else if(Arrays.asList(aGrassTopLeft).contains(tile.code)){
            tile.secondary_texture = Media.grassTopLeft;
        }        
    }


    private void setupTiles(){
        chunk = new Chunk(33,33, 8);
        
        int currentRow = 0;
        int rngW = 5;
        int rngH = 5;
        
        int centreTileRow = chunk.numberRows / 2;
        int centreTileCol = chunk.numberCols /2;
        int firstTileRow = centreTileRow - (rngH);
        
        int maxRow = centreTileRow + rngH;
        int minRow = centreTileRow - rngH;
        int maxCol = centreTileCol + rngW;
        int minCol = centreTileCol - rngW;
        
        // CHUNK ROW
        ArrayList<Tile> chunkRow = new ArrayList<Tile>();
        
        // If number of tiles is needed.
        // int num_tiles = ((max_col - min_col)-1) * ((max_row - min_row)-1);

        for(int row = 0; row < chunk.numberRows; row ++){
            for(int col = 0; col < chunk.numberCols; col ++){
                // Create TILE
                Tile tile = new Tile(col, row, chunk.tileSize, TILETYPE.WATER, randomWater());
                
                // Make a small island
                if(row > minRow && row < maxRow && col > minCol && col < maxCol){
                    tile.texture = randomGrass();
                    tile.type = TILETYPE.GRASS;
                    
                    if(row == firstTileRow + 1){
                        tile.texture = Media.cliff;
                        tile.type = TILETYPE.CLIFF;
                    } else {
                        // Chance to add trees etc
                    }
                } 
                
                // ADD TILE TO CHUNK
                if(currentRow == row){
                    // Add tile to current row
                    chunkRow.add(tile);
                    
                    // Last row and column?
                    if (row == chunk.numberRows - 1 && col == chunk.numberCols - 1){
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
    }


    private void codeTiles() {
        // Loop all tiles and set the initial code
    
        // 1 CHUNK ONLY ATM
        for(ArrayList<Tile> row : chunk.tiles){
            for(Tile tile : row){ 
                // Check all surrounding tiles and set 1 for pass 0 for non pass
                // 0 0 0
                // 0 X 0
                // 0 0 0
                
                int[] rows = {1,0,-1};
                int[] cols = {-1,0,1};
                
                for(int r: rows){
                    for(int c: cols){
                        tile.code += chunk.getTileCode(tile.row + r, tile.col + c);
                        updateImage(tile);
                    }
                }    
            }
        }
    }

    private Texture randomWater(){
        Texture water;

        water = Media.water01;
        
        return water;
    }

    private Texture randomGrass(){
        Texture grass;
        grass = Media.grass01;

        return grass;
    }

    private void generateHitboxesHub(Box2DWorld box2d) {
        for(int i = 0; i < chunk.tiles.size(); i++){
            for(int j = 0; j < chunk.tiles.get(i).size(); j++){ 
                Tile tile = chunk.tiles.get(i).get(j);
                if(tile.is_not_passable() && tile.notIsAllWater()){
                    // Shift the hitbox one tile to the left and bottom
                    Vector3 pos = new Vector3(tile.pos.x - chunk.tileSize, tile.pos.y - chunk.tileSize, 0);
                    // Exclude the top and right tiles
                    if(i < chunk.tiles.size() - 1 && j < chunk.tiles.get(i).size() - 1) {
                        Box2DHelper.createBody(box2d.world, chunk.tileSize,8,8, chunk.tileSize, pos, BodyType.StaticBody);
                    }
                }
            }
        }
    }

    private void addEntities(Box2DWorld box2d) {

        Vector3 pos1 = new Vector3(136.0f,128.0f,0.0f);
        Vector3 pos2 = new Vector3(152.0f,128.0f,0.0f);
        Vector3 pos3 = new Vector3(160.0f,128.0f,0.0f);
        Vector3 pos4 = new Vector3(120.0f,136.0f,0.0f);
        Vector3 pos5 = new Vector3(104.0f,144.0f,0.0f);
        Vector3 pos6 = new Vector3(160.0f,152.0f,0.0f);
        Vector3 pos7 = new Vector3(112.0f,112.0f,0.0f);
        Vector3 pos8 = new Vector3(120.0f,120.0f,0.0f);
        Vector3 pos9 = new Vector3(160.0f,112.0f,0.0f);


        // Loop all tiles and add random trees
                    entities.add(new Tree(pos1, box2d));
                    entities.add(new Tree(pos2, box2d));
                    entities.add(new Tree(pos3, box2d));
                    entities.add(new Tree(pos4, box2d));
                    entities.add(new Tree(pos5, box2d));
                    entities.add(new Tree(pos6, box2d));
                    entities.add(new Tree(pos7, box2d));
                    entities.add(new Tree(pos8, box2d));
                    entities.add(new Tree(pos9, box2d));
                }   

    public Vector3 getCentrePosition(){
        return centreTile.pos;
    }

    public List<Npc> addNpc(Box2DWorld box2d,  Player player, Camera camera){
        List<Npc> newNpc = new ArrayList<>();
        Vector3 pos1 = new Vector3(142.0f,128.0f,0.0f);

        for(ArrayList<Tile> row : chunk.tiles){
            for(Tile tile : row){ 
                if (tile.is_grass()){
                    Npc npc = new Npc(pos1, box2d);
                    entities.add(npc);
                    newNpc.add(npc);
                    Npcs.add(npc);
                }   
            }
        }
        return newNpc;
    }
}



