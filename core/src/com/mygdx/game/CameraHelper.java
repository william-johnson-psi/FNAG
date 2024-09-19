/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.*;

/**
 *
 * @author wjohn
 */
public class CameraHelper {
    // Allows Easier Translating.
    public static void updateTranslationOffice(int mouseX, float cameraX, int screenWidth, OrthographicCamera camera, float dt, boolean movingLeft) {
        if (movingLeft) {
            if ((mouseX < (screenWidth * 0.35)) && (mouseX > (screenWidth * 0.25))) {
                camera.translate(-15f * Gdx.graphics.getDeltaTime(), 0);
            }
            if ((mouseX < (screenWidth * 0.25)) && (mouseX > (screenWidth * 0.10))) {
                camera.translate(-80f * Gdx.graphics.getDeltaTime(), 0);
            }
            if ((mouseX < (screenWidth * 0.10))) {
                camera.translate(-120f * Gdx.graphics.getDeltaTime(), 0);
            }
        }
        else {
            if ((mouseX > (screenWidth * 0.65)) && mouseX < (screenWidth * 0.75)) {
                camera.translate(15f * Gdx.graphics.getDeltaTime(), 0);
            }
            if ((mouseX > (screenWidth * 0.75)) && (mouseX < (screenWidth * 0.9))) {
                camera.translate(80f * Gdx.graphics.getDeltaTime(), 0);
            }
            if (mouseX > (screenWidth * 0.90)) {
                camera.translate(120f * Gdx.graphics.getDeltaTime(), 0);
            }
        }
    }
    
    public static boolean autoTranslate(OrthographicCamera camera, boolean movingLeft, boolean paused) {
        if (!paused) {
            if (movingLeft) {
                camera.translate(-10f * Gdx.graphics.getDeltaTime(), 0);
            }
            else {
                camera.translate(10f * Gdx.graphics.getDeltaTime(), 0);
            }
        }
        else {
            return false;
        }
        return false;
    }
    
    public static void drawToScreenCoordinates(Sprite s, OrthographicCamera camera) {
        Vector3 pos = new Vector3(camera.position.x, camera.position.y, 0);
        camera.project(pos);
    }

}
