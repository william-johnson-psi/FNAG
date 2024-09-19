package com.mygdx.game;

import java.util.ArrayList;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.*;
import com.badlogic.gdx.utils.viewport.*;
import com.badlogic.gdx.audio.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.*; 
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.utils.*;
import com.badlogic.gdx.utils.*;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.*;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.*;


public class Main extends ApplicationAdapter {
	private SpriteBatch batch;
        // Responsiveness https://www.youtube.com/watch?v=Ml-XXQaN0hc -- Really good video that explains it well
        private OrthographicCamera camera;
        private final float GAME_WORLD_WIDTH = 180 ; 
        private final float GAME_WORLD_HEIGHT = 100; // Make our camera, a portion of the actual screen using these ccordinates.
        private Viewport viewport;
        private Viewport uiViewport;
        // Input Processor 
        private GameInputProcessor inputProcessor;
        // Game Settings
        private int screenHeight;
        private int screenWidth;  // To be used, potentially.
        private int mouseX;
        private int mouseY;
        private float timerDelay;
        private OrthographicCamera uiCamera;
        // Game Variables 
        private float currentPower; // Tracks CURRENT power 
        // Organizing Power Outage Sequence
        private boolean musicBoxPlayed; 
        private boolean musicBoxStopped;
        // Managing what they can do in each area
        private boolean isInMainOffice; 
        private boolean isInLeftOffice;  // < ---  Depending on which part of the office we are in, perform different actions
        private boolean isInRightOffice;
        
        private boolean isCameraMode;  //Camera Mode? Display cams
        // Transitions.
        private boolean mainTransitionLeft; 
        private boolean mainTransitionRight;
        private boolean leftTransitionMain;   // Enabling of transitions to different scenes 
        private boolean rightTransitionMain;
        private boolean tabletTransition;
        private boolean tabletOffTransition;
        private boolean jumpscareTransition;
    
        private boolean powerIsOn; // Allows user to perform power on actions
        private byte gameState; //  0 = Main Menu, 1 = Active Game
        private boolean startedMainMenu;
        private boolean displayNightInfo;
        // Times for Animations, etc
        private float mainMenuTimer;
        private float jumpscareTimer;
        private float powerDeductionTimer;
        private float powerOffJumpscareTimer;
        private float gameTimer;
        
        private boolean doorClosed;
        private boolean lightOn;
        private boolean endMainMenuDraw;
        private boolean gameStartTransition;
        private boolean gameStartTransitionTwo;
        // Rooms and Camera Manager
        private CameraMonitor monitor;
        // Audios
        private Music darkAmbience; // Plays during active game 
        private Music officeAmbience; // Plays during active game and power on, and when camera is not up
        private Music lightBuzz; // Plays when user turns on light 
        private Music cameraMove; // Noises when camera mode is active
        private Music mainMenuMusic; // Plays during main menu
        private Sound mainMenuIntroSound; // Plays during startuup
        private Sound windowScare; // Plays when Gully is revealed 
        private Sound doorClose; // Played when user closes door with "spacebar"
        private Sound powerOff; // Plays when user runs out of power 
        private Sound musicBox; // Plays when Gully appears when power runs out
        private Sound phoneGuyOne; // Plays at the start of the night 
        private Sound monitorMove; // Plays when user puts down camera monitor
        private Sound monitorOn; // Plays when user puts up camera monitor
        private Sound blip; // Plays to begin night, and when hitting camera buttons
        // Fonts
        private BitmapFont pixelFont;
        // Main Scene Textures 
        private Sprite activeScene;
	private Sprite sprite_mainOffice;
        private Sprite sprite_mainOfficePowerOff;
        private Sprite sprite_mainOfficeLeft;
        private Sprite sprite_mainOfficeLeftPowerOff;
        private Sprite sprite_mainOfficeLeftGullyPresentPowerOff;
        private Sprite sprite_mainOfficeLeftDoorClosed;
        private Sprite sprite_mainOfficeLeftLightOn;
        private Sprite sprite_mainOfficeLeftGullyPresent;
        private Sprite sprite_mainOfficeRight;
        private Sprite sprite_mainOfficeRightPowerOff;
        // Monitor Button
        private Sprite sprite_monitorButton;
        // Camera Buttons 
        private Sprite sprite_cam1;
        private Sprite sprite_cam1_on;
        private Sprite sprite_cam2;
        private Sprite sprite_cam2_on;
        private Sprite sprite_cam3;
        private Sprite sprite_cam3_on;
        private Sprite sprite_cam4;
        private Sprite sprite_cam4_on;
        private Sprite sprite_cam5;
        private Sprite sprite_cam5_on;
        private Sprite sprite_cam6;
        private Sprite sprite_cam6_on;
        // Camera Lists
        private ArrayList<Sprite> array_cameraSprites;
        private ArrayList<Sprite> array_cameraOnSprites;
        // Map 
        private Sprite sprite_mapFrame;
        // Blue Transition Textures  
        // Left Office <---> Main Office        // All of the below are animation sprites.
        private Sprite sprite_mainLookLeft1;
        private Sprite sprite_mainLookLeft3;
        private Sprite sprite_mainLookLeft5;
        private Sprite sprite_mainLookLeft7;
        private Sprite sprite_mainLookLeft9;
        private Sprite sprite_mainLookLeft11;
        private Sprite sprite_mainLookLeft13;
        private Sprite sprite_mainLookLeft15;
        private Sprite sprite_mainLookLeft17;
        private Sprite sprite_mainLookLeft19;
        private Sprite sprite_mainLookLeft21;
        private Sprite sprite_mainLookLeft23;
        private Sprite sprite_mainLookLeft24;
        private Sprite sprite_mainLookLeft25;
        private Sprite sprite_mainLookLeft26;
        private Sprite sprite_mainLookLeft27;
        private Sprite sprite_mainLookLeft28;
        private Animation mainLookLeftAnimation;
        // Left Office <---> Main Office     POWER OFF
        private Sprite sprite_mainLookLeft1PowerOff;
        private Sprite sprite_mainLookLeft3PowerOff;
        private Sprite sprite_mainLookLeft5PowerOff;
        private Sprite sprite_mainLookLeft7PowerOff;
        private Sprite sprite_mainLookLeft9PowerOff;
        private Sprite sprite_mainLookLeft11PowerOff;
        private Sprite sprite_mainLookLeft13PowerOff;
        private Sprite sprite_mainLookLeft15PowerOff;
        private Sprite sprite_mainLookLeft17PowerOff;
        private Sprite sprite_mainLookLeft19PowerOff;
        private Sprite sprite_mainLookLeft21PowerOff;
        private Sprite sprite_mainLookLeft23PowerOff;
        private Sprite sprite_mainLookLeft24PowerOff;
        private Sprite sprite_mainLookLeft25PowerOff;
        private Sprite sprite_mainLookLeft26PowerOff;
        private Sprite sprite_mainLookLeft27PowerOff;
        private Sprite sprite_mainLookLeft28PowerOff;
        private Animation mainLookLeftAnimationPowerOff;

        // Main Office <----> Right Office
        private Sprite sprite_mainLookRight1;
        private Sprite sprite_mainLookRight2;
        private Sprite sprite_mainLookRight3;
        private Sprite sprite_mainLookRight4;
        private Sprite sprite_mainLookRight5;
        private Sprite sprite_mainLookRight6;
        private Sprite sprite_mainLookRight7;
        private Sprite sprite_mainLookRight8;
        private Sprite sprite_mainLookRight9;
        private Sprite sprite_mainLookRight10;
        private Sprite sprite_mainLookRight11;
        private Sprite sprite_mainLookRight12;
        private Sprite sprite_mainLookRight13;
        private Sprite sprite_mainLookRight14;
        private Sprite sprite_mainLookRight15;
        private Sprite sprite_mainLookRight16;
        private Sprite sprite_mainLookRight17;
        private Animation mainLookRightAnimation;
        // Main Office <----> Right Office POWER OFF 
        private Sprite sprite_mainLookRight1PowerOff;
        private Sprite sprite_mainLookRight2PowerOff;
        private Sprite sprite_mainLookRight3PowerOff;
        private Sprite sprite_mainLookRight4PowerOff;
        private Sprite sprite_mainLookRight5PowerOff;
        private Sprite sprite_mainLookRight6PowerOff;
        private Sprite sprite_mainLookRight7PowerOff;
        private Sprite sprite_mainLookRight8PowerOff;
        private Sprite sprite_mainLookRight9PowerOff;
        private Sprite sprite_mainLookRight10PowerOff;
        private Sprite sprite_mainLookRight11PowerOff;
        private Sprite sprite_mainLookRight12PowerOff;
        private Sprite sprite_mainLookRight13PowerOff;
        private Sprite sprite_mainLookRight14PowerOff;
        private Sprite sprite_mainLookRight15PowerOff;
        private Sprite sprite_mainLookRight16PowerOff;
        private Sprite sprite_mainLookRight17PowerOff;
        private Animation mainLookRightAnimationPowerOff;

        // DISTORT ANIMATION
        private Sprite sprite_distort1;
        private Sprite sprite_distort2;
        private Sprite sprite_distort3;
        private Sprite sprite_distort4; 
        private Sprite sprite_distort5;
        private Sprite sprite_distort6;
        private Sprite sprite_distort7;
        private Sprite sprite_distort8;
        private Animation distortAnimation;
        private final Sprite[] distortAnimationList = {sprite_distort1, sprite_distort2, sprite_distort3, sprite_distort4, sprite_distort5, sprite_distort6, sprite_distort7, sprite_distort8};
        // TABLET PULL UP/ DOWN
        private Sprite sprite_tablet1;
        private Sprite sprite_tablet2;
        private Sprite sprite_tablet3;
        private Sprite sprite_tablet4;
        private Animation tabletOpenCloseAnimation;
        // GULLY JUMPSCARE 
        private Sprite sprite_gullyJumpscare1;
        private Sprite sprite_gullyJumpscare2;
        private Sprite sprite_gullyJumpscare3;
        private Sprite sprite_gullyJumpscare4;
        private Sprite sprite_gullyJumpscare5;
        private Sprite sprite_gullyJumpscare6;
        private Sprite sprite_gullyJumpscare7;
        private Sprite sprite_gullyJumpscare8;
        private Sprite sprite_gullyJumpscare9;
        private Animation gullyJumpscareAnimation;
        // MAIN MENU SPRITES
        private Sprite sprite_mainMenu1;
        private Sprite sprite_mainMenu2;
        private Sprite sprite_mainMenu3;
        private Sprite sprite_mainMenu4;
        private Sprite sprite_mainMenu5;
        private Sprite sprite_acceptanceLetter;
        private Sprite sprite_nightOneInfo;
        private Animation mainMenuAnimation;
        // CORE GAME FUNCTIONALITY
        private GullyAnimatronic gully;
	@Override
	public void create () {
            // Input Processor // 
            // Create our Class of Animations to play 
            //   LEFT OFFICE <--> MAIN OFFICE // LOOK LEFT BLUR ANIMATION ASSETS AND SETUP // More Animation Sprites.
            Sprite[] mainLookLeftAnimationList = {sprite_mainLookLeft5, sprite_mainLookLeft7, sprite_mainLookLeft9, sprite_mainLookLeft11, sprite_mainLookLeft13, sprite_mainLookLeft15, sprite_mainLookLeft17, sprite_mainLookLeft19, sprite_mainLookLeft21, sprite_mainLookLeft23, sprite_mainLookLeft24, sprite_mainLookLeft25, sprite_mainLookLeft26, sprite_mainLookLeft27, sprite_mainLookLeft28};
            for (int i = 0; i < mainLookLeftAnimationList.length; i++ ) {
                mainLookLeftAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("mainOfficeLookLeftAnimation/lookLeft" + (i+2) + ".jpg")));
                mainLookLeftAnimationList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            mainLookLeftAnimation = new Animation(mainLookLeftAnimationList, 0.008f); // Create our animation.
            //   LEFT OFFICE <--> MAIN OFFICE // LOOK LEFT BLUR ANIMATION ASSETS AND SETUP // More Animation Sprites.
            Sprite[] mainLookLeftAnimationPowerOffList = {sprite_mainLookLeft5PowerOff, sprite_mainLookLeft7PowerOff, sprite_mainLookLeft9PowerOff, sprite_mainLookLeft11PowerOff, sprite_mainLookLeft13PowerOff, sprite_mainLookLeft15PowerOff, sprite_mainLookLeft17PowerOff, sprite_mainLookLeft19PowerOff, sprite_mainLookLeft21PowerOff, sprite_mainLookLeft23PowerOff, sprite_mainLookLeft24PowerOff, sprite_mainLookLeft25PowerOff, sprite_mainLookLeft26PowerOff, sprite_mainLookLeft27PowerOff, sprite_mainLookLeft28PowerOff};
            for (int i = 0; i <mainLookLeftAnimationPowerOffList.length; i++) {
                mainLookLeftAnimationPowerOffList[i] = new Sprite(new Texture(Gdx.files.internal("mainOfficeLookLeftPowerOffAnimation/lookLeft" + (i+2) + ".jpg")));
                mainLookLeftAnimationPowerOffList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            mainLookLeftAnimationPowerOff = new Animation(mainLookLeftAnimationPowerOffList, 0.008f);
            // MAIN OFFICE <--> RIGHT OFFICE  // LOOK RIGHT BLUR ANIMATION ASSETS AND SETUP
            Sprite[] mainLookRightAnimationList = {sprite_mainLookRight1, sprite_mainLookRight2, sprite_mainLookRight3, sprite_mainLookRight4, sprite_mainLookRight5, sprite_mainLookRight6, sprite_mainLookRight7, sprite_mainLookRight8, sprite_mainLookRight9, sprite_mainLookRight10, sprite_mainLookRight11, sprite_mainLookRight12, sprite_mainLookRight13, sprite_mainLookRight14, sprite_mainLookRight15, sprite_mainLookRight16, sprite_mainLookRight17};
            for (int i = 0; i < mainLookRightAnimationList.length; i++) {
                mainLookRightAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("mainOfficeLookRightAnimation/lookRight" + (i) + ".jpg")));
                mainLookRightAnimationList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            mainLookRightAnimation = new Animation(mainLookRightAnimationList, 0.008f);
            // MAIN OFFICE <--> RIGHT OFFICE  // LOOK RIGHT BLUR ANIMATION ASSETS AND SETUP // POWER OFF 
            Sprite[] mainLookRightAnimationPowerOffList =  {sprite_mainLookRight1PowerOff, sprite_mainLookRight2PowerOff, sprite_mainLookRight3PowerOff, sprite_mainLookRight4PowerOff, sprite_mainLookRight5PowerOff, sprite_mainLookRight6PowerOff, sprite_mainLookRight7PowerOff, sprite_mainLookRight8PowerOff, sprite_mainLookRight9PowerOff, sprite_mainLookRight10PowerOff, sprite_mainLookRight11PowerOff, sprite_mainLookRight12PowerOff, sprite_mainLookRight13PowerOff, sprite_mainLookRight14PowerOff, sprite_mainLookRight15PowerOff, sprite_mainLookRight16PowerOff, sprite_mainLookRight17PowerOff};
            for (int i = 0; i < mainLookRightAnimationPowerOffList.length; i++) {
                mainLookRightAnimationPowerOffList[i] = new Sprite(new Texture(Gdx.files.internal("mainOfficeLookRightPowerOffAnimation/lookRight" + (i) + ".jpg")));
                mainLookRightAnimationPowerOffList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            }
            mainLookRightAnimationPowerOff = new Animation(mainLookRightAnimationPowerOffList, 0.008f);
            // MAIN MENU ANIMATION 
            sprite_mainMenu1 = new Sprite(new Texture(Gdx.files.internal("mainMenuAnimation/mainMenu0.png")));
            sprite_mainMenu2 = new Sprite(new Texture(Gdx.files.internal("mainMenuAnimation/mainMenu1.png")));
            sprite_mainMenu3 = new Sprite(new Texture(Gdx.files.internal("mainMenuAnimation/mainMenu2.png")));
            sprite_mainMenu4 = new Sprite(new Texture(Gdx.files.internal("mainMenuAnimation/mainMenu3.png")));
            sprite_mainMenu5 = new Sprite(new Texture(Gdx.files.internal("mainMenuAnimation/mainMenu4.png")));
            sprite_acceptanceLetter = new Sprite(new Texture(Gdx.files.internal("acceptanceLetter.png")));
            sprite_acceptanceLetter.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT+30);
            sprite_acceptanceLetter.setX(15);
            sprite_acceptanceLetter.setY(-30);
            sprite_nightOneInfo = new Sprite(new Texture(Gdx.files.internal("nightOneInfo.png")));
            sprite_nightOneInfo.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            Sprite[] mainMenuAnimationList = {sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1, sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu1,sprite_mainMenu3,sprite_mainMenu3,sprite_mainMenu3,sprite_mainMenu3,sprite_mainMenu3, sprite_mainMenu4, sprite_mainMenu2,sprite_mainMenu2,sprite_mainMenu2,sprite_mainMenu2,sprite_mainMenu2,sprite_mainMenu2,sprite_mainMenu2, sprite_mainMenu5};
            for (int i = 0; i < mainMenuAnimationList.length; i++) {
                mainMenuAnimationList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
                mainMenuAnimationList[i].setX(15);
            } 
            mainMenuAnimation = new Animation(mainMenuAnimationList, 0.2f);
            // DISTORT ANIMATION 
//            Sprite[] distortAnimationList = {sprite_distort1, sprite_distort2, sprite_distort3, sprite_distort4, sprite_distort5, sprite_distort6, sprite_distort7, sprite_distort8};
            for (int i = 0; i < distortAnimationList.length; i++) {
                distortAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("distortAnimation/" + (i+1) + ".png")));
                distortAnimationList[i].setSize(GAME_WORLD_WIDTH+200, GAME_WORLD_HEIGHT+200);
                distortAnimationList[i].setAlpha((float)0.65);
            }
            distortAnimation = new Animation(distortAnimationList, 0.002f);
            // TABLET ANIMATION!
            sprite_tablet1 = new Sprite(new Texture(Gdx.files.internal("tabletAnimation/tablet1.png")));
            sprite_tablet2 = new Sprite(new Texture(Gdx.files.internal("tabletAnimation/tablet2.png")));
            sprite_tablet3 = new Sprite(new Texture(Gdx.files.internal("tabletAnimation/tablet3.png")));
            sprite_tablet4 = new Sprite(new Texture(Gdx.files.internal("tabletAnimation/tablet4.png")));
            Sprite[] tabletAnimationList = {sprite_tablet1, sprite_tablet2, sprite_tablet3, sprite_tablet4, sprite_tablet4, sprite_tablet4, sprite_tablet4};
            for (int i = 0; i < tabletAnimationList.length; i++) {
                tabletAnimationList[i].setSize(GAME_WORLD_WIDTH+1, GAME_WORLD_HEIGHT+5);
                tabletAnimationList[i].setX(-94);
                tabletAnimationList[i].setY(-52);
            }
            tabletOpenCloseAnimation = new Animation(tabletAnimationList, 0.0f);
            // GULLY JUMPSCARE ANIMATION 
            Sprite[] gullyJumpscareAnimationList = {sprite_gullyJumpscare1, sprite_gullyJumpscare2, sprite_gullyJumpscare3, sprite_gullyJumpscare4, sprite_gullyJumpscare5, sprite_gullyJumpscare6, sprite_gullyJumpscare7, sprite_gullyJumpscare8, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9, sprite_gullyJumpscare9};
            for (int i = 0; i < gullyJumpscareAnimationList.length; i++ ) {
                if (i > 8) {
                    gullyJumpscareAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("gullyJumpscareAnimation/gullyJumpscare8.png")));
                }
                else {
                    gullyJumpscareAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("gullyJumpscareAnimation/gullyJumpscare" + i + ".png")));
                }
                gullyJumpscareAnimationList[i].setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
                gullyJumpscareAnimationList[i].setX(-90);
                gullyJumpscareAnimationList[i].setY(-50);
            }
            gullyJumpscareAnimation = new Animation(gullyJumpscareAnimationList, 0.018f);
            // Making and MANAGING Audios 
            mainMenuIntroSound = Gdx.audio.newSound(Gdx.files.internal("audio/static.wav"));
            mainMenuMusic = Gdx.audio.newMusic(Gdx.files.internal("audio/mainMenuTheme.mp3"));
            musicBox = Gdx.audio.newSound(Gdx.files.internal("audio/musicBox.wav"));
            monitorMove = Gdx.audio.newSound(Gdx.files.internal("audio/monitorMove.wav"));
            monitorOn = Gdx.audio.newSound(Gdx.files.internal("audio/monitorMoveOn.wav"));
            blip = Gdx.audio.newSound(Gdx.files.internal("audio/blip.wav"));
            lightBuzz = Gdx.audio.newMusic(Gdx.files.internal("audio/lightBuzz.wav"));
            lightBuzz.setLooping(true);
            cameraMove = Gdx.audio.newMusic(Gdx.files.internal("audio/cameraMoving.wav"));
            cameraMove.setLooping(true);
            cameraMove.setVolume((float)1.5);
            officeAmbience = Gdx.audio.newMusic(Gdx.files.internal("audio/officeAmbience.wav"));
            officeAmbience.setVolume((float)0.40);
            officeAmbience.setLooping(true);
            darkAmbience = Gdx.audio.newMusic(Gdx.files.internal("audio/darkAmbience.mp3"));
            darkAmbience.setVolume((float)0.40); 
            darkAmbience.setLooping(true);
            doorClose = Gdx.audio.newSound(Gdx.files.internal("audio/doorClose.wav"));
            windowScare = Gdx.audio.newSound(Gdx.files.internal("audio/windowscare.wav"));
            powerOff = Gdx.audio.newSound(Gdx.files.internal("audio/powerdown.wav"));
            phoneGuyOne = Gdx.audio.newSound(Gdx.files.internal("audio/phoneguy1.mp3"));
            // SPRITE CREATIONS OF VARIOUS SCENES.
            batch = new SpriteBatch();
            sprite_mainOffice = new Sprite(new Texture(Gdx.files.internal("mainOffice.jpg")));
            sprite_mainOffice.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficePowerOff = new Sprite(new Texture(Gdx.files.internal("mainOfficePowerOff.jpg")));
            sprite_mainOfficePowerOff.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            
            sprite_mainOfficeLeft = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftDark.jpg")));
            sprite_mainOfficeLeft.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeLeftPowerOff = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftPowerOff.jpg")));
            sprite_mainOfficeLeftPowerOff.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeLeftGullyPresentPowerOff = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftGullyPresentPowerOff.jpg")));
            sprite_mainOfficeLeftGullyPresentPowerOff.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeLeftDoorClosed = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftDoorClosed.jpg")));
            sprite_mainOfficeLeftDoorClosed.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeLeftLightOn = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftLIGHTON.jpg")));
            sprite_mainOfficeLeftLightOn.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeLeftGullyPresent = new Sprite(new Texture(Gdx.files.internal("mainOfficeLeftGullyPresent.jpg")));
            sprite_mainOfficeLeftGullyPresent.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            
            sprite_mainOfficeRight = new Sprite(new Texture(Gdx.files.internal("mainOfficeRight.jpg")));
            sprite_mainOfficeRight.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            sprite_mainOfficeRightPowerOff = new Sprite(new Texture(Gdx.files.internal("mainOfficeRightPowerOff.jpg")));
            sprite_mainOfficeRightPowerOff.setSize(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            
            activeScene = sprite_mainOffice;
            sprite_mapFrame = new Sprite(new Texture(Gdx.files.internal("fnagmap.png")));
            sprite_mapFrame.setSize(80, 75);
            sprite_mapFrame.setX(-9);
            sprite_mapFrame.setY(-50);
            
            sprite_monitorButton = new Sprite(new Texture(Gdx.files.internal("buttons/monitorButton.png")));
            sprite_monitorButton.setSize(GAME_WORLD_WIDTH-10, GAME_WORLD_HEIGHT-10);
            sprite_monitorButton.setAlpha(0.2f);
            sprite_monitorButton.setY(-51);
            sprite_monitorButton.setX(-86);
            
            
            // The following creates camera buttons, hard-coded for more options
            // on my end, incase I want special sizes or colors. These are all button sprites.
            sprite_cam1 = new Sprite(new Texture(Gdx.files.internal("buttons/cam1.png")));
            sprite_cam1.setSize(9, 6);
            sprite_cam1.setX(30);
            sprite_cam1.setY(5);
            sprite_cam1_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam1ON.png")));
            sprite_cam1_on.setSize(9, 6);
            sprite_cam1_on.setX(30);
            sprite_cam1_on.setY(5);
            
            sprite_cam2 = new Sprite(new Texture(Gdx.files.internal("buttons/cam2.png")));
            sprite_cam2.setSize(9, 6);
            sprite_cam2.setX(41);
            sprite_cam2.setY(-8);
            sprite_cam2_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam2ON.png")));
            sprite_cam2_on.setSize(9, 6);
            sprite_cam2_on.setX(41);
            sprite_cam2_on.setY(-8);
            
            sprite_cam3 = new Sprite(new Texture(Gdx.files.internal("buttons/cam3.png")));
            sprite_cam3.setSize(9, 6);
            sprite_cam3.setX(24);
            sprite_cam3.setY(-15);
            sprite_cam3_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam3ON.png")));
            sprite_cam3_on.setSize(9, 6);
            sprite_cam3_on.setX(24);
            sprite_cam3_on.setY(-15);
            
            sprite_cam4 = new Sprite(new Texture(Gdx.files.internal("buttons/cam4.png")));
            sprite_cam4.setSize(9, 6);
            sprite_cam4.setX(14);
            sprite_cam4.setY(-20);
            sprite_cam4_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam4ON.png")));
            sprite_cam4_on.setSize(9, 6);
            sprite_cam4_on.setX(14);
            sprite_cam4_on.setY(-20);
            
            sprite_cam5 = new Sprite(new Texture(Gdx.files.internal("buttons/cam5.png")));
            sprite_cam5.setSize(9, 6);
            sprite_cam5.setX(25);
            sprite_cam5.setY(-23);
            sprite_cam5_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam5ON.png")));
            sprite_cam5_on.setSize(9, 6);
            sprite_cam5_on.setX(25);
            sprite_cam5_on.setY(-23);
            
            sprite_cam6 = new Sprite(new Texture(Gdx.files.internal("buttons/cam6.png")));
            sprite_cam6.setSize(9, 6);
            sprite_cam6.setX(15);
            sprite_cam6.setY(-33);
            sprite_cam6_on = new Sprite(new Texture(Gdx.files.internal("buttons/cam6ON.png")));
            sprite_cam6_on.setSize(9, 6);
            sprite_cam6_on.setX(15);
            sprite_cam6_on.setY(-33);
            // Fonts
            FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("SFPixelate-Bold.ttf"));
            FreeTypeFontParameter  parameter = new FreeTypeFontParameter();
            parameter.size = 10;
            pixelFont = generator.generateFont(parameter);
            pixelFont.setColor(Color.WHITE);
            pixelFont.getData().setScale(0.5f);
            // Button Lists - We are attatching the buttons to the list 
            array_cameraSprites = new ArrayList<Sprite>();
            array_cameraOnSprites = new ArrayList<Sprite>();
            array_cameraSprites.add(sprite_cam1);
            array_cameraSprites.add(sprite_cam2);
            array_cameraSprites.add(sprite_cam3);
            array_cameraSprites.add(sprite_cam4);
            array_cameraSprites.add(sprite_cam5);
            array_cameraSprites.add(sprite_cam6);
            array_cameraOnSprites.add(sprite_cam1_on);
            array_cameraOnSprites.add(sprite_cam2_on);
            array_cameraOnSprites.add(sprite_cam3_on);
            array_cameraOnSprites.add(sprite_cam4_on);
            array_cameraOnSprites.add(sprite_cam5_on);
            array_cameraOnSprites.add(sprite_cam6_on);
            // Responsive and Screen Settings  
            float aspectRatio = (float)Gdx.graphics.getHeight()*(float)1.1/(float)Gdx.graphics.getWidth(); 
            camera = new OrthographicCamera(); // Width must be relative to the aspect ratio.
            uiCamera = new OrthographicCamera(GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT);
            viewport = new FitViewport(GAME_WORLD_WIDTH * aspectRatio, GAME_WORLD_HEIGHT, camera);// Viewport Extends FitViewport!
            viewport.apply();
            uiViewport = new FitViewport(GAME_WORLD_WIDTH * aspectRatio, GAME_WORLD_HEIGHT, uiCamera);
            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);            
            // Game Variables
            isInMainOffice = true; // Begin the game at the main office, so we set main office to true.
            mainTransitionLeft = false;
            leftTransitionMain = false;
            timerDelay = (float)0.0;
            powerDeductionTimer = 0.0f;
            currentPower = 1000.0f;
            // Creating our Camera Manager , this manages and creates our available rooms.
            monitor = new CameraMonitor();
            monitor.addRoom(new Room("Main Stage",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room0Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room0.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("Stage Audience",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room1Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room1.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("Corridor Door",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room2Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room2.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("Hallway",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room3Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room3.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("Einsteins Backdoor",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room4Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room4.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("Behind CS Lounge Door",new Sprite(new Texture(Gdx.files.internal("cameraLocations/room5Empty.jpg"))),new Sprite(new Texture(Gdx.files.internal("cameraLocations/room5.jpg"))), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            monitor.addRoom(new Room("At CS Door", new Sprite(), new Sprite(), false, GAME_WORLD_WIDTH, GAME_WORLD_HEIGHT));
            // Input Manager
            inputProcessor = new GameInputProcessor(this, monitor);
            Gdx.input.setInputProcessor(inputProcessor);
            // GULLY AI  Creating Gully with an AI level of 5
            gully = new GullyAnimatronic(this, monitor, 5);
            // Core Game
            powerIsOn = true;
            gameState = 0; // 0 -- Main Menu // 1 -- Main Game 
            startedMainMenu = false; 
            mainMenuTimer = 0.0f;
            jumpscareTimer = 0.0f; 
            gameStartTransition = false;
            gameStartTransitionTwo = false;
            endMainMenuDraw = false; 
            displayNightInfo = false;
            doorClosed = false;
            lightOn = false;
            musicBoxPlayed = false;
            musicBoxStopped = false;
            gameTimer = 0.0f;
	}
        
        public boolean isCameraMode() {
            return this.isCameraMode;
        }
        
        public void startGame() {
            darkAmbience.play();
            phoneGuyOne.play();
            gameTimer = 0.0f;
            officeAmbience.play();
            for (int i = 0; i < distortAnimationList.length; i++) {
                distortAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("distortAnimation/" + (i+1) + ".png")));
                distortAnimationList[i].setSize(GAME_WORLD_WIDTH+200, GAME_WORLD_HEIGHT+200);
                distortAnimationList[i].setAlpha(0.2f);
            }
            // Resetting all fields.
            musicBoxStopped = false;
            musicBoxPlayed = false;
            powerOffJumpscareTimer = 0.0f;
            currentPower = 1000.0f;
            doorClosed = false;
            lightOn = false;
            gameState = 1;
            activeScene = sprite_mainOffice;
            isInMainOffice = true;
            isInLeftOffice = false;
            isInRightOffice = false;
            powerIsOn = true;
        }
        
        public void endGame() {
            darkAmbience.stop();
            phoneGuyOne.stop();
            officeAmbience.stop();
            cameraMove.stop();
            lightBuzz.stop();
            for (int i = 0; i < distortAnimationList.length; i++) {
                distortAnimationList[i] = new Sprite(new Texture(Gdx.files.internal("distortAnimation/" + (i+1) + ".png")));
                distortAnimationList[i].setSize(GAME_WORLD_WIDTH+200, GAME_WORLD_HEIGHT+200);
                distortAnimationList[i].setAlpha(.65f);
            }
            // Resetting all fields.
            doorClosed = false;
            currentPower = 1000.0f;
            lightOn = false;
            powerIsOn = true;
            gully.moveGully(0);
            gameState = 0;
            jumpscareTransition = false;
            mainMenuIntroSound.play();
            mainMenuMusic.play();
            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);            
        }
        @Override
        public void resize(int width, int height) {
            viewport.update(width, height); 
            uiViewport.update(width, height);
            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0); // Allows Window Scaling
        }
	@Override
	public void render () {
            timerDelay += Gdx.graphics.getDeltaTime();
            ScreenUtils.clear(Color.BLACK);
            camera.update();
            batch.begin();
            batch.setProjectionMatrix(camera.combined);
            if (gameState == 1) {
                activeScene.draw(batch);
                // Check Jumpscare:// 
                // This checks if we are playing the transition to camera mode, if we are perform all frames and switch variables accordingly
                if (tabletTransition) { 
                    batch.setProjectionMatrix(uiCamera.combined);
                    tabletOpenCloseAnimation.playAnimation(false, Gdx.graphics.getDeltaTime()).draw(batch);
                    if (!tabletOpenCloseAnimation.isPlaying()) {
                        tabletOpenCloseAnimation.stopAnimation();
                        tabletTransition = false; 
                        isCameraMode = true;
                    }
                }
                // Check if we are taking off our tablet, perform all frames and variables
                else if (tabletOffTransition) {
                    batch.setProjectionMatrix(uiCamera.combined);
                    tabletOpenCloseAnimation.playAnimation(true, Gdx.graphics.getDeltaTime()).draw(batch);
                    if (!tabletOpenCloseAnimation.isPlaying()) {
                        tabletOpenCloseAnimation.stopAnimation();
                        tabletOffTransition = false; 
                        isCameraMode = false;
                    }
                }
                // Camera is ON, draw Map and active scene to the room we are looking at.
                if (isCameraMode) {
                    batch.setProjectionMatrix(uiCamera.combined);
                    sprite_mapFrame.draw(batch);
                    int currentCamNumber = monitor.getCameraNumber();
                    for (int i = 0; i < array_cameraSprites.size(); i++){
                        if (currentCamNumber == i) {
                            array_cameraOnSprites.get(i).draw(batch);
                        }
                        else {
                            array_cameraSprites.get(i).draw(batch);
                        }
                    }
                    batch.setProjectionMatrix(camera.combined);
                    activeScene = monitor.getCurrentRoomPicture(camera);
                    distortAnimation.playEndlessAnimation(Gdx.graphics.getDeltaTime()).draw(batch);
                }
                // Updates 
                // Update Gully AI 
                if (powerIsOn){
                    gully.updateMoveTimer(Gdx.graphics.getDeltaTime());
                }
                // Mostly translations, determines which parts of the office we can translate to 
                if (isInMainOffice && !isCameraMode) {
                    if ((camera.position.x > 70)) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), true);
                    }
                    else {
                        mainTransitionLeft = true;
                        isInMainOffice = false;
                    }
                    if (camera.position.x < 112.0) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), false);
                    }
                    else if (camera.position.x > 112.0) {
                        mainTransitionRight = true;
                        isInMainOffice = false;
                    }
                }
                else if (isInLeftOffice && !isCameraMode) {
                    if (camera.position.x > 70) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), true);    
                    }
                    if (camera.position.x < 112.0) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), false);
                    }
                    else if (camera.position.x > 112.0){
                        leftTransitionMain = true;
                        isInLeftOffice = false;

                    }
                    if ((!powerIsOn) && (powerOffJumpscareTimer > 5.0f) && (powerOffJumpscareTimer < 29.0f)) {
                        activeScene = sprite_mainOfficeLeftGullyPresentPowerOff;
                    }
                    else if (!powerIsOn) {
                        activeScene = sprite_mainOfficeLeftPowerOff;
                    }
                    else if (doorClosed) {
                        activeScene = sprite_mainOfficeLeftDoorClosed;
                    }
                    else if (lightOn && monitor.isGullyInside(6)) {
                        activeScene = sprite_mainOfficeLeftGullyPresent;
                    }
                    else if (lightOn) {
                        activeScene = sprite_mainOfficeLeftLightOn;
                    }
                    else{
                        activeScene = sprite_mainOfficeLeft;
                    }

                }
                else if (isInRightOffice && !isCameraMode) {
                    if (camera.position.x > 70) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), true);    
                    }
                    else if (camera.position.x < 70) {
                        rightTransitionMain = true;
                        isInRightOffice = false;
                    }
                    if (camera.position.x < 112.0) {
                        CameraHelper.updateTranslationOffice(Gdx.input.getX(), camera.position.x, Gdx.graphics.getWidth(), camera, Gdx.graphics.getDeltaTime(), false);
                    }
                }
                // END OF TRANSLATIONS ^^
                // User openning camera mode 
                if ((!isCameraMode && timerDelay > 0.40) && (powerIsOn) && (isInMainOffice)) {
                    if (Gdx.input.getY() > Gdx.graphics.getHeight() * 0.9) {
                        tabletTransition = true;
                        monitorOn.play(2.0f);
                        monitorMove.stop();
                        cameraMove.play();
                        officeAmbience.setVolume((float)0);
                        timerDelay = (float)0.0;
                    }
                }
                else if (isCameraMode && timerDelay > 0.40) {
                    if (Gdx.input.getY() > Gdx.graphics.getHeight() * 0.9) {
                        monitorOn.stop();
                        monitorMove.play();
                        cameraMove.stop();
                        officeAmbience.setVolume((float)0.40);
                        if (isInMainOffice) {
                            activeScene = sprite_mainOffice;
                        }
                        else if (isInLeftOffice) {
                            if (doorClosed) {
                                activeScene = sprite_mainOfficeLeftDoorClosed;
                            }
                            else if (lightOn && monitor.isGullyInside(6)) {
                                activeScene = sprite_mainOfficeLeftGullyPresent;
                            }
                            else if (lightOn) {
                                activeScene = sprite_mainOfficeLeftLightOn;
                            }
                            else{
                                activeScene = sprite_mainOfficeLeft;
                            }
                        }
                        else {
                            activeScene = sprite_mainOfficeRight;
                        }
                        camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                        isCameraMode = false;
                        tabletOffTransition = true;
                        monitorMove.play();
                        timerDelay = (float)0.0; 
                    }
                }
                // Monitor Button
                if (isInMainOffice) {
                    batch.setProjectionMatrix(uiCamera.combined);
                    sprite_monitorButton.draw(batch);
                    batch.setProjectionMatrix(camera.combined);
                }
                // These are our transitions, they allow the player to switch scenes without choppiness.
                // Currently aware of repeated code and will change it soon, but im focused on other concepts at the moment.
                
                // TRANSITIONS BETWEEN THE THREE SCENES AND !cameraMode,   mainOfficeLeft <--> mainOffice <--> mainOfficeRight
                if (mainTransitionLeft) {
                    if (powerIsOn) {
                        activeScene = mainLookLeftAnimation.playAnimation(false, Gdx.graphics.getDeltaTime());
                        if (!mainLookLeftAnimation.isPlaying()){ 
                            mainLookLeftAnimation.stopAnimation();
                            mainTransitionLeft = false;
                            camera.position.set(80, GAME_WORLD_HEIGHT/2, 0);
                            isInLeftOffice = true;
                            if (doorClosed) {
                                activeScene = sprite_mainOfficeLeftDoorClosed;
                            }
                            else if (lightOn & monitor.isGullyInside(6)) {
                                windowScare.play();
                                activeScene = sprite_mainOfficeLeftGullyPresent;
                            }
                            else if (lightOn) {
                                activeScene = sprite_mainOfficeLeftLightOn;
                            }
                            else {
                                activeScene = sprite_mainOfficeLeft;
                            }
                        }

                    }
                    else {
                        activeScene = mainLookLeftAnimationPowerOff.playAnimation(false, Gdx.graphics.getDeltaTime());
                        if (!mainLookLeftAnimationPowerOff.isPlaying()) {
                            mainLookLeftAnimationPowerOff.stopAnimation();
                            mainTransitionLeft = false; 
                            camera.position.set(80, GAME_WORLD_HEIGHT/2, 0);
                            isInLeftOffice = true;
                            activeScene = sprite_mainOfficeLeftPowerOff;
                        }
                    }
                }
                else if (leftTransitionMain) {
                    if (powerIsOn) {
                        activeScene = mainLookLeftAnimation.playAnimation(true, Gdx.graphics.getDeltaTime());
                        if (!mainLookLeftAnimation.isPlaying()) {
                            mainLookLeftAnimation.stopAnimation();
                            leftTransitionMain = false;
                            activeScene = sprite_mainOffice;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInMainOffice = true;
                        }
                    }
                    else {
                        activeScene = mainLookLeftAnimationPowerOff.playAnimation(true, Gdx.graphics.getDeltaTime());
                        if (!mainLookLeftAnimationPowerOff.isPlaying()) {
                            mainLookLeftAnimationPowerOff.stopAnimation();
                            leftTransitionMain  = false; 
                            activeScene = sprite_mainOfficePowerOff;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInMainOffice = true;
                        }
                    }
                }
                else if (mainTransitionRight) {
                    if (powerIsOn) {
                        activeScene = mainLookRightAnimation.playAnimation(false, Gdx.graphics.getDeltaTime());
                        if (!mainLookRightAnimation.isPlaying()) {
                            mainLookRightAnimation.stopAnimation();
                            mainTransitionRight = false;
                            activeScene = sprite_mainOfficeRight;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInRightOffice = true;
                        }
                    }
                    else {
                        activeScene = mainLookRightAnimationPowerOff.playAnimation(false, Gdx.graphics.getDeltaTime());
                        if (!mainLookRightAnimationPowerOff.isPlaying()) {
                            mainLookRightAnimationPowerOff.stopAnimation();
                            mainTransitionRight = false;
                            activeScene = sprite_mainOfficeRightPowerOff;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInRightOffice = true;
                        }
                    }
                }
                else if (rightTransitionMain) {
                    if (powerIsOn) {
                        activeScene = mainLookRightAnimation.playAnimation(true, Gdx.graphics.getDeltaTime());
                        if (!mainLookRightAnimation.isPlaying()) {
                            mainLookRightAnimation.stopAnimation();
                            rightTransitionMain = false;
                            activeScene = sprite_mainOffice;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInMainOffice = true;
                        }
                    }
                    else {
                        activeScene = mainLookRightAnimationPowerOff.playAnimation(true, Gdx.graphics.getDeltaTime());
                        if (!mainLookRightAnimationPowerOff.isPlaying()) {
                            mainLookRightAnimationPowerOff.stopAnimation();
                            rightTransitionMain = false;
                            activeScene = sprite_mainOfficePowerOff;
                            camera.position.set(GAME_WORLD_WIDTH/2, GAME_WORLD_HEIGHT/2, 0);
                            isInMainOffice = true;
                        }
                    }
                }
                // If this is enabled, then game is over. Jumpscare begins. Audio must be played from outside of this if-block.
                if (jumpscareTransition) {
                    if (isCameraMode) {
                        isCameraMode = false;
                        if (!isInLeftOffice) {
                            mainTransitionLeft = true;
                        }
                        else {
                            tabletOffTransition = true;
                        }
                    }
                    if (!isInLeftOffice) {
                        mainTransitionLeft = true;
                        isInRightOffice = false;
                    }
                    batch.setProjectionMatrix(uiCamera.combined); 
                    if (jumpscareTimer > .5f) {
                        gullyJumpscareAnimation.playAnimation(false, Gdx.graphics.getDeltaTime()).draw(batch); 
                        if (!gullyJumpscareAnimation.isPlaying()) {
                            gullyJumpscareAnimation.stopAnimation();
                            jumpscareTransition = false;
                            jumpscareTimer = 0.0f;
                            endGame();
                        }
                    }
                    else {
                        jumpscareTimer += Gdx.graphics.getDeltaTime();
                    }
                    
                }
                batch.setProjectionMatrix(uiCamera.combined);
                // Power is OFF, begin Music Box to Jumpscare Sequence 
                if (!powerIsOn) {
                    powerOffJumpscareTimer += Gdx.graphics.getDeltaTime();
                    if ((powerOffJumpscareTimer > 6.0f) && (!musicBoxPlayed)) {
                        musicBox.play(); 
                        musicBoxPlayed = true;
                    }
                    if ((powerOffJumpscareTimer > 29.0f) && (!musicBoxStopped)) {
                        musicBox.stop();
                        musicBoxStopped = true;
                    }
                    if (powerOffJumpscareTimer > 35.0f) {
                        gully.getJumpscareSound().play();  
                        startJumpscare();
                    }
                }
                if (powerIsOn) {
                    pixelFont.draw(batch, "Power left: " + (int)((currentPower/1000.0f) * 100.0f) + "%", -64, -38);
                    deductPower();
                }
                // Keep track of game time.
                gameTimer += Gdx.graphics.getDeltaTime();
                pixelFont.draw(batch, "TIME SURVIVED: " + (int)gameTimer, -64, 46);
            }
            // Main Menu Setup and Settings
            else if (gameState == 0) {
                if (!startedMainMenu) {
                    this.startedMainMenu = true;
                    this.mainMenuMusic.play();
                    this.mainMenuIntroSound.play();
                    this.mainMenuMusic.setLooping(true);
                }
                if (!gameStartTransition & Gdx.input.isTouched()) {
                    gameStartTransition = true; 
                } 
                if (!endMainMenuDraw) {
                    mainMenuAnimation.playEndlessAnimation(Gdx.graphics.getDeltaTime()).draw(batch);
                    distortAnimation.playEndlessAnimation(Gdx.graphics.getDeltaTime()).draw(batch);
                }
                if (gameStartTransition) {
                    mainMenuTimer += Gdx.graphics.getDeltaTime();
                    if (mainMenuTimer <= 2.0f) {
                        sprite_acceptanceLetter.setAlpha((mainMenuTimer/2.0f) * 1.0f);
                    }
                    
                    if (mainMenuTimer >= 20.0f) {
                        endMainMenuDraw = true;
                        sprite_acceptanceLetter.setAlpha((22.0f - mainMenuTimer/2.0f) * 1.0f);
                    }
                    if (mainMenuTimer >= 21.0f) {
                        gameStartTransition = false;
                        gameStartTransitionTwo = true;
                        mainMenuMusic.stop();
                        mainMenuTimer = 0.0f;
                    }
                    sprite_acceptanceLetter.draw(batch);                
                }
                if (gameStartTransitionTwo) {
                    mainMenuTimer += Gdx.graphics.getDeltaTime();
                    if (mainMenuTimer >= 1.0f && !displayNightInfo) {
                        displayNightInfo = true;
                        blip.play();
                    }
                    if (displayNightInfo && mainMenuTimer <= 5.0f) {
                        sprite_nightOneInfo.draw(batch);
                    }
                    if (mainMenuTimer >= 7.5f) {
                        mainMenuTimer = 0.0f;
                        displayNightInfo = false;
                        endMainMenuDraw = false;
                        gameStartTransition = false; 
                        gameStartTransitionTwo = false;
                        startGame();
                    }
                }
            }
            batch.end();
        }
        
        
        public void deductPower() {
            // Deducting Power. 
            powerDeductionTimer += Gdx.graphics.getDeltaTime();
            if (powerDeductionTimer >= 1.0f) {
                powerDeductionTimer = 0.0f;
                currentPower -= 1.0f; 
                if (isCameraMode) {
                    currentPower -= 3.0f;
                }
                if (lightOn) {
                    currentPower -= 3.0f;
                }
                if (doorClosed) {
                    currentPower -= 5.0f;
                }
            }
            if (currentPower <= 0.0f) {
                System.out.println("POWER OFF");
                powerOff.play();
                powerIsOn = false;
                doorClosed = false; 
                officeAmbience.stop();
                lightBuzz.stop();
                phoneGuyOne.stop();
                cameraMove.stop();
                tabletOffTransition = true;
                if (isCameraMode) {
                    isCameraMode = false;
                }
                if (isInMainOffice) {
                    activeScene = sprite_mainOfficePowerOff;
                }
                else if (isInLeftOffice) {
                    activeScene = sprite_mainOfficeLeftPowerOff;
                }
                else if (isInRightOffice) {
                    activeScene = sprite_mainOfficeRightPowerOff;
                }
            }
        }
        public void openDoor() {
            doorClose.play();
            doorClosed = false;
        }
        public void closeDoor() {
            doorClose.play();
            doorClosed = true;
        }
        
        public void enableLight() {
            lightOn = true;
            this.currentPower -= 10f;
            lightBuzz.play();
            if (monitor.isGullyInside(6) && !doorClosed) {
                windowScare.play();
            }
        }
        
        public void disableLight() {
            lightOn = false;
            lightBuzz.stop();
        }
        public boolean isInLeftOffice() {
            return isInLeftOffice;
        }
        
        public boolean isDoorClosed() {
            return doorClosed;
        }
        public boolean isLightOn() {
            return this.lightOn;
        }
        
        public void startJumpscare() {
            jumpscareTransition = true;
            phoneGuyOne.stop();
        }
	@Override
	public void dispose () {
            batch.dispose();
            pixelFont.dispose();
	}
        
}
