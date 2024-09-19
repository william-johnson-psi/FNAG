/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;
import com.badlogic.gdx.graphics.g2d.*;
import java.util.ArrayList;
/**
 *
 * @author wjohn
 */
public class Room {
    
    private Sprite roomSprite;
    private Sprite gullyRoomSprite;
    private boolean gullyInside; 
    private boolean garyInside; 
    private String roomName;
    
    public Room(String roomName, Sprite emptyRoomSprite, Sprite gullyRoomSprite, boolean isGullyInside,float GAME_WORLD_WIDTH, float GAME_WORLD_HEIGHT ) {
        this.roomSprite = emptyRoomSprite;
        this.gullyRoomSprite = gullyRoomSprite;
        // Set Room State 
        this.gullyInside = isGullyInside;
        // Resizing
        this.roomSprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        this.gullyRoomSprite.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
        //
        this.roomName = roomName;
    }
    
    public String getRoomName() {
        return this.roomName;
    }
    public Sprite getRoomImage() {
        if (this.gullyInside) {
            return this.gullyRoomSprite;
        }
        else {
            return this.roomSprite;
        }
    }
    public boolean isGullyInside() {
        return this.gullyInside;
    }
    public void removeGully() {
        this.gullyInside = false; 
    }
    
    public void addGully() {
        this.gullyInside = true;
    }
}
