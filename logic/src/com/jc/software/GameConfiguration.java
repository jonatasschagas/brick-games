package com.jc.software;

/**
 * Created by jonataschagas on 21/01/18.
 */
public interface GameConfiguration {

    int SCREEN_WIDTH = 1024;
    int SCREEN_HEIGHT = 768;

    // Snake node size
    int TILE_SIZE = 32;

    int SCREEN_WIDTH_IN_TILES = SCREEN_WIDTH/ TILE_SIZE;
    int SCREEN_HEIGHT_IN_TILES = SCREEN_HEIGHT/ TILE_SIZE;

    float FPS = 60.0f;
    float FRAME_PERIOD_IN_MILLIS = 1000f/FPS;
    float TIME_TO_SLEEP = FRAME_PERIOD_IN_MILLIS/1000f;
    int MAX_FRAMES_SKIPPED = 5;

    // s = v*t
    // t = s/v
    // s/t
    float SNAKE_VELOCITY = FPS/10;

    // IDs
    int GAME_ID_SNAKE_PIECE = 9000;
    int GAME_ID_SNAKE_SEGMENT_START_RANGE = 3;


}
