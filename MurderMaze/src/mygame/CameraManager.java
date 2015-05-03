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
import com.jme3.input.ChaseCamera;

/**
*
* @author Bob
*/
public class CameraManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager stateManager;
  private AssetManager assetManager;
  public ChaseCamera cam;
  private Player player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player = this.stateManager.getState(PlayerManager.class).player;
    initCamera();
    }
  
  //Creates camera
  public void initCamera() {
      
        cam = new ChaseCamera(this.app.getCamera(), player, this.app.getInputManager());
        cam.setMinDistance(0.5f);
        cam.setMaxDistance(.5f);
        cam.setDefaultDistance(.5f);
        cam.setDragToRotate(false);
        cam.setDownRotateOnCloseViewOnly(false);
        cam.setRotationSpeed(2f);
        cam.setLookAtOffset(player.getLocalTranslation().add(0, 1.25f, 0));
        cam.setDefaultVerticalRotation(3f);
        cam.setMaxVerticalRotation(4f);
        cam.setMinVerticalRotation(2f);
        app.getInputManager().setCursorVisible(false);
        float scale = .5f;
        app.getCamera().setFrustumNear(app.getCamera().getFrustumNear()*scale);
        app.getCamera().setFrustumLeft(app.getCamera().getFrustumLeft()*scale);
        app.getCamera().setFrustumRight(app.getCamera().getFrustumRight()*scale);
        app.getCamera().setFrustumTop(app.getCamera().getFrustumTop()*scale);
        app.getCamera().setFrustumBottom(app.getCamera().getFrustumBottom()*scale);
        
  }
  
  @Override
  public void update(float tpf) {
    cam.setDefaultDistance(.6f);
    cam.setDefaultVerticalRotation(.145f);
  }
  
}
