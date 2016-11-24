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
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

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
  private Node   cameraNode;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player = this.stateManager.getState(PlayerManager.class).player;
    setFrustrum();
    setEnabled(false);  
  }
  
  private void setFrustrum() {
        float scale = .5f;
        app.getCamera().setFrustumNear(app.getCamera().getFrustumNear()*scale);
        app.getCamera().setFrustumLeft(app.getCamera().getFrustumLeft()*scale);
        app.getCamera().setFrustumRight(app.getCamera().getFrustumRight()*scale);
        app.getCamera().setFrustumTop(app.getCamera().getFrustumTop()*scale);
        app.getCamera().setFrustumBottom(app.getCamera().getFrustumBottom()*scale);
  }
  
  //Creates camera
  public void initCamera() {
      
        cameraNode = new Node();
        this.app.getRootNode().attachChild(cameraNode);
        cam = new ChaseCamera(this.app.getCamera(), cameraNode, app.getInputManager());
        cam.setMinDistance(0.5f);
        cam.setMaxDistance(.5f);
        cam.setDefaultDistance(.5f);
        cam.setDragToRotate(false);
        cam.setDownRotateOnCloseViewOnly(false);
        cam.setRotationSpeed(4f);
        //cam.setLookAtOffset(player.getLocalTranslation().add(0, 1.35f, 0));
        cam.setDefaultVerticalRotation(3f);
        cam.setMaxVerticalRotation(4f);
        cam.setMinVerticalRotation(2f);
        app.getInputManager().setCursorVisible(false);
        
  }
  
  @Override
  public void update(float tpf) {
    cam.setDefaultDistance(.6f);
    cam.setDefaultVerticalRotation(.145f);
    cameraNode.setLocalTranslation(player.getLocalTranslation().multLocal(1,0,1).add(0, 1.35f, 0));
    cameraNode.move(app.getCamera().getDirection().normalize().mult(.4f));
  }
  
}
