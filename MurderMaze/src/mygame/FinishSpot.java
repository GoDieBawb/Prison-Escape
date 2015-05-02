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
public class FinishSpot extends Interactable {
    
  public FinishSpot(AppStateManager stateManager, Node interactable) {
    super(stateManager, interactable);
    }
  
  @Override
  public void act() {
      
    if (player.currentLevel > player.bestLevel) {
      player.saveScore(player.currentLevel, stateManager);
      player.bestLevel = player.currentLevel;
      }
    
    stateManager.getState(SceneManager.class).removeScene();
    stateManager.getState(GuiManager.class).showMenu();
      
    }
    
  }
