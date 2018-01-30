package com.jc.software;

/**
 * Created by jonataschagas on 21/01/18.
 */
public interface GameConfiguration {

    int SCREEN_WIDTH = 1024;
    int SCREEN_HEIGHT = 768;

    // Snake node size
    int TILE_SIZE = 32;

    int SCREEN_WIDTH_IN_TILES = SCREEN_WIDTH / TILE_SIZE;
    int SCREEN_HEIGHT_IN_TILES = SCREEN_HEIGHT / TILE_SIZE;

    // time = 100ms
    float TIME_PER_TILE = 100.0f / 1000.0f;

}
