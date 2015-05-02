/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.TextureKey;
import com.jme3.font.BitmapFont;
import com.jme3.font.BitmapFont.Align;
import com.jme3.font.BitmapFont.VAlign;
import com.jme3.font.LineWrapMode;
import com.jme3.input.event.MouseButtonEvent;
import com.jme3.math.ColorRGBA;
import com.jme3.math.FastMath;
import com.jme3.math.Vector2f;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.shape.Box;
import com.jme3.texture.Texture;
import java.util.ArrayList;
import tonegod.gui.controls.buttons.ButtonAdapter;
import tonegod.gui.controls.extras.android.Joystick;
import tonegod.gui.controls.text.TextElement;
import tonegod.gui.core.Screen;
import tonegod.gui.effects.Effect;
import tonegod.gui.effects.Effect.EffectEvent;
import tonegod.gui.effects.Effect.EffectType;

/**
 *
 * @author Bob
 */
public class GuiManager extends AbstractAppState {

  private SimpleApplication        app;
  private AppStateManager          stateManager;
  private AssetManager             assetManager;
  private Screen                   screen;
  private int                      stage;
  private ArrayList<ButtonAdapter> buttonList;
  private Player                   player;
  private TextElement              title;
  private Joystick                 stick;
  private BitmapFont               font;
  private boolean                  isAndroid;
  private Geometry                  bg;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    screen            = new Screen(app, "tonegod/gui/style/atlasdef/style_map.gui.xml");
    font              = assetManager.loadFont("Interface/Impact.fnt");
    screen.setUseTextureAtlas(true,"tonegod/gui/style/atlasdef/atlas.png");
    screen.setUseMultiTouch(true);
    this.app.getGuiNode().addControl(screen);
    this.app.getInputManager().setSimulateMouse(true);
    stage      = 1;
    buttonList = new ArrayList();
    isAndroid  = "Dalvik".equals(System.getProperty("java.vm.name"));
    if (isAndroid)
    initJoystick();
    
    initBackground();
    initTitle();
    initMenu();
    
    }
  
  private void initBackground() {
    System.out.println(app.getCamera().getDirection());
    Box b = new Box(6,6,6);  
    bg    = new Geometry("Background", b);
    b.scaleTextureCoordinates(new Vector2f(2,2));
    bg.setMaterial(assetManager.loadMaterial("Materials/Stone.j3m"));
    app.getRootNode().attachChild(bg);
    bg.setLocalTranslation(new Vector3f(17,9,-13));
    }
  
  private void initTitle() {     
    
    BitmapFont titleFont = assetManager.loadFont("Interface/Fonts/InvisibleKiller.fnt");
      
    title = new TextElement(screen, "Title", Vector2f.ZERO, new Vector2f(300,50), titleFont) {
    @Override
    public void onUpdate(float tpf) { }
    @Override
    public void onEffectStart() { }
    @Override
    public void onEffectStop() { }
    };
    
    title.setIsResizable(false);
    title.setIsMovable(false);
    title.setTextWrap(LineWrapMode.NoWrap);
    title.setTextVAlign(VAlign.Center);
    title.setTextAlign(Align.Center);
    title.setFontSize(font.getPreferredSize());
    
    Effect slideIn1 = new Effect(EffectType.SlideIn, EffectEvent.Show, 1f);
    Effect slideOut1 = new Effect(EffectType.SlideOut, EffectEvent.Hide, 1f);
    
    title.addEffect(slideIn1);
    title.addEffect(slideOut1);
    
    title.setFontSize(screen.getHeight()/7);
    title.setText("Prison Escape");
    
    screen.addElement(title);
    title.setPosition(screen.getWidth()/2 - title.getWidth()/2, screen.getHeight() - title.getHeight());
    
    
    }
  
  private void initMenu() {
    
    for (int i = 0; i < 9; i++) {
        
      final int buttonNum  = i + 1;
      
      ButtonAdapter currentButton = new ButtonAdapter(screen, "Button" + i, new Vector2f(12,12)) {
        @Override
        public void onButtonMouseLeftUp(MouseButtonEvent evt, boolean toggle) {

          String mazeNum      = String.valueOf(stage * buttonNum);
          String path         = "Scenes/Maze" + mazeNum + ".j3o";
          player.currentLevel = stage*buttonNum;
          hideMenu();
          stateManager.getState(SceneManager.class).initScene(path);
          }
      
        };
      
      currentButton.setFont("Interface/Impact.fnt");
      currentButton.setDimensions(screen.getWidth()/10, screen.getWidth()/10);
      currentButton.setText(stage + " - " + stage*buttonNum);
      screen.addElement(currentButton);
      buttonList.add(currentButton);
      currentButton.hide();
      
        
      }  
    
    //Centered around button 4
    buttonList.get(4).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(0).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(1).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(2).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 - buttonList.get(4).getHeight()/2 + buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(3).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(5).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - title.getHeight()*1.5f);
    
    buttonList.get(6).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 - buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(7).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    buttonList.get(8).setPosition(screen.getWidth()/2 - buttonList.get(4).getWidth()/2 + buttonList.get(4).getWidth()*2, screen.getHeight()/2 -  buttonList.get(4).getHeight()/2 - buttonList.get(4).getHeight()*1.25f - title.getHeight()*1.5f);
    
    showMenu();
    }
  
  private void initJoystick(){
    stick = new Joystick(screen, Vector2f.ZERO, (int)(screen.getWidth()/6)) {
    
    @Override
    public void onUpdate(float tpf, float deltaX, float deltaY) {
        
        
        float dzVal = .2f; // Dead zone threshold
        
            if (deltaX < -dzVal) {
              stateManager.getState(InteractionManager.class).left  = true;
              stateManager.getState(InteractionManager.class).right = false;
              } 
            
            else if (deltaX > dzVal) {
              stateManager.getState(InteractionManager.class).right = true;
              stateManager.getState(InteractionManager.class).left  = false;
              }
            
            else {
              stateManager.getState(InteractionManager.class).right = false;
              stateManager.getState(InteractionManager.class).left  = false; 
              }
            
        
            if (deltaY < -dzVal) {
              stateManager.getState(InteractionManager.class).down = true;
              stateManager.getState(InteractionManager.class).up   = false;
              } 
            
            else if (deltaY > dzVal) {
              stateManager.getState(InteractionManager.class).down = false;
              stateManager.getState(InteractionManager.class).up   = true;
              }
            
            else {
              stateManager.getState(InteractionManager.class).up   = false;
              stateManager.getState(InteractionManager.class).down = false;    
              }
            
          player.speedMult = FastMath.abs(deltaY);
          
          }
    
        };
    
      // getGUIRegion returns region info “x=0|y=0|w=50|h=50″, etc
      TextureKey key = new TextureKey("Textures/barrel.png", false);
      Texture tex = assetManager.loadTexture(key);
      stick.setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      stick.getThumb().setTextureAtlasImage(tex, "x=20|y=20|w=120|h=35");
      screen.addElement(stick, true);
      stick.setPosition(screen.getWidth()/10 - stick.getWidth()/2, screen.getHeight() / 10f - stick.getHeight()/5);
     
      // Add some fancy effects
      Effect fxIn = new Effect(Effect.EffectType.FadeIn, Effect.EffectEvent.Show,.5f);
      stick.addEffect(fxIn);
      Effect fxOut = new Effect(Effect.EffectType.FadeOut, Effect.EffectEvent.Hide,.5f);
      stick.addEffect(fxOut);
      stick.hide();
      
      }
  
  private void hideMenu() {
    
    for (int i = 0; i < buttonList.size(); i++) {
      if (i ==9) break;
      buttonList.get(i).hide();
      }
    
    title.hideWithEffect();
    app.getRootNode().detachChild(bg);
    
    if (isAndroid)
    stick.show();
    
    }
  
  public void showMenu() {
    
    for (int i = 0; i < player.bestLevel + 1; i++) {
      if (i ==9) break;  
      buttonList.get(i).show();
      }
    
    title.show();
    app.getCamera().setLocation(new Vector3f(17.320509f, 10.0f, 0.0f));
    app.getRootNode().attachChild(bg);
    bg.setLocalTranslation(new Vector3f(17,9,-13));
    app.getCamera().lookAtDirection(new Vector3f(0.0f, 0.0f, -1.0f), new Vector3f(0,1,0));
    
    if (isAndroid)
    stick.hide();
      
    }
  }
