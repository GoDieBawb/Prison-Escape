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
import com.jme3.collision.CollisionResults;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class InteractableManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  private Node              interactableNode;
  private Interactable      finishSpot;
  private Player            player;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app) {
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    player            = stateManager.getState(PlayerManager.class).player;
    setEnabled(false);
    }
  
  public void initInteractables(Node scene) {
      
    setEnabled(true);
      
    interactableNode = (Node) scene.getChild("InteractableNode");
    
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
      
      Node currentInteractable = (Node) interactableNode.getChild(i);
      
      try {
          
        Interactable testInteractable = (Interactable) currentInteractable;
        
        }
      
      catch (ClassCastException e) {
        
        if (currentInteractable.getName().equals("Door")) {
            
          Door door = new Door(stateManager, currentInteractable);
          interactableNode.attachChild(door);
            
          }
        
        else if (currentInteractable.getName().equals("Key")) {  
          Key key = new Key(stateManager, currentInteractable);
          interactableNode.attachChild(key);
          
          }
        
        else if (currentInteractable.getName().equals("FinishSpot")) {
          
          finishSpot = new FinishSpot(stateManager, currentInteractable);
          //finishSpot.setLocalTranslation(currentInteractable.getLocalTranslation());
          interactableNode.attachChild(finishSpot);
            
          }
          
        }
        
      }
    
    interactNodeClean();
      
    }
  
  private void interactNodeClean() {
      
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
        
      Node currentInteractable = (Node) interactableNode.getChild(i);
      
      try {
        Interactable test = (Interactable) currentInteractable;
        test.attachChild(test.model);
        }
      
      catch(ClassCastException e ) {
        currentInteractable.removeFromParent();
        }
        
      }
    
    
    for (int i = 0; i < interactableNode.getQuantity(); i++) {
        
      Interactable bla = (Interactable) interactableNode.getChild(i);
      bla.attachChild(bla.model);
      
      if (bla.equals(finishSpot)) {
          continue;
      }
      
      bla.setLocalTranslation(bla.model.getLocalTranslation());
      bla.model.setLocalTranslation(0,0,0);
      
    }
    
    
      
    }
  
  @Override
  public void update(float tpf) {
    
    float distance = (player.getLocalTranslation().distance(finishSpot.getChild("FinishSpot").getLocalTranslation()));
   
    if (distance < 2f) {
      finishSpot.act();
    }
      
    for (int i = 0; i < interactableNode.getChildren().size(); i++) {
    
        Interactable interactable = (Interactable) interactableNode.getChild(i);
        float dist = player.getLocalTranslation().distance(interactable.getLocalTranslation());
        
        if (dist < 1.5f) {
            interactable.act();
        }
        
    }
      
    }
  
  }
