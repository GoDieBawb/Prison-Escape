/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.app.state.AppStateManager;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.scene.Node;

/**
 *
 * @author Bob
 */
public class Door extends Interactable {
  
  public Door(AppStateManager stateManager, Node interactable) {
    super(stateManager, interactable);
    
    Material mat = new Material( 
       stateManager.getApplication().getAssetManager(), "Common/MatDefs/Misc/Unshaded.j3md");
    
    if (name.equals("Green")) {
      mat.setColor("Color", ColorRGBA.Green);
      }
    
    else if (name.equals("Blue")) {
      mat.setColor("Color", ColorRGBA.Blue);  
      }
    
    else if (name.equals("Red")) {
      mat.setColor("Color", ColorRGBA.Red);  
      }
    
    model.setMaterial(mat);
    
    }
  
  @Override
  public void act() {
  
    if (player.keyList.contains(name)) {
      super.act();
      } else {
        
      }
    
    }
    
  }
