/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public abstract class Interactable extends Node {
  
  public  AppStateManager stateManager;
  public  Node            model;
  public  String          name;
  public  Player          player;
    
  public Interactable(AppStateManager stateManager, Node interactable) {
      
    this.stateManager = stateManager;
    model             = interactable;
    name              = model.getUserData("Name");
    player            = stateManager.getState(PlayerManager.class).player;
    
    }  
  
  public void act() {
      
    removeFromParent();
    stateManager.getState(SceneManager.class).addPhys();
    
    }  
    
  }
