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
import com.jme3.bullet.BulletAppState;
import com.jme3.bullet.control.RigidBodyControl;
import com.jme3.material.Material;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.SceneGraphVisitor;
import com.jme3.scene.Spatial;

/**
 *
 * @author Bob
 */
public class SceneManager extends AbstractAppState {

  private SimpleApplication   app;
  private AppStateManager     stateManager;
  private AssetManager        assetManager;
  private Node                rootNode;
  public  BulletAppState      physics;
  public  Node                scene;
  private InteractableManager interactableManager;
  private Player              player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app            = (SimpleApplication) app;
    this.stateManager   = this.app.getStateManager();
    this.assetManager   = this.app.getAssetManager();
    this.physics        = stateManager.getState(PlayerManager.class).physics;
    this.rootNode       = this.app.getRootNode();
    player              = stateManager.getState(PlayerManager.class).player;
    interactableManager = stateManager.getState(InteractableManager.class);
    scene               = new Node();
    }
  
  public void initScene(String scenePath){
    
    stateManager.attach(physics);
    physics.getPhysicsSpace().add(player.phys);
    this.app.getRootNode().attachChild(player);
    player.keyList.clear();

    rootNode.detachChild(scene);
    physics.getPhysicsSpace().removeAll(scene);

    scene = (Node) assetManager.loadModel(scenePath);
    addPhys();

    Vector3f startSpot = scene.getChild("StartSpot").getLocalTranslation();
    player.phys.warp(startSpot);
    
    rootNode.attachChild(scene);
    
    interactableManager.initInteractables(scene);
    
    makeUnshaded(app.getRootNode());
    
    }
  
  public void removeScene(){
    physics.getPhysicsSpace().removeAll(scene);
    rootNode.detachAllChildren();
    stateManager.detach(physics);
    scene = new Node();
    }
  
  public void addPhys() {
    physics.getPhysicsSpace().removeAll(scene);
    
    RigidBodyControl phys = new RigidBodyControl(0f);
    RigidBodyControl phys1 = new RigidBodyControl(0f);
    
    scene.getChild("SceneNode").removeControl(RigidBodyControl.class);
    scene.getChild("InteractableNode").removeControl(RigidBodyControl.class);
    scene.getChild("SceneNode").addControl(phys);
    scene.getChild("InteractableNode").addControl(phys1);
    
    physics.getPhysicsSpace().add(phys);
    physics.getPhysicsSpace().add(phys1);
    }

  public void makeUnshaded(Node node) {
      
    SceneGraphVisitor sgv = new SceneGraphVisitor() {
 
    public void visit(Spatial spatial) {
 
      if (spatial instanceof Geometry) {
        
        Geometry geom = (Geometry) spatial;
        Material mat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        if (geom.getMaterial().getTextureParam("DiffuseMap") != null) {
          mat.setTexture("ColorMap", geom.getMaterial().getTextureParam("DiffuseMap").getTextureValue());
          geom.setMaterial(mat);
          }
       
        }
      
      }
    
    };
    
  node.depthFirstTraversal(sgv);
    
  }  
  
}
