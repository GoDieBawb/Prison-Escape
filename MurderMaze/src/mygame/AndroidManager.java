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

/**
 *
 * @author Bob
 */
public class AndroidManager extends AbstractAppState {

  private SimpleApplication app;
  private AppStateManager   stateManager;
  private AssetManager      assetManager;
  public  String            filePath;
  
  @Override
  public void initialize(AppStateManager stateManager, Application app){
    super.initialize(stateManager, app);
    this.app          = (SimpleApplication) app;
    this.stateManager = this.app.getStateManager();
    this.assetManager = this.app.getAssetManager();
    System.out.println("Android Manager Initialized");
    }
  
  public void setFilePath(String filePath) {
    this.filePath = filePath;
    }
  
  }
