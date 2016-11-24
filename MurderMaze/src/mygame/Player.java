/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package mygame;

import com.jme3.animation.AnimChannel;
import com.jme3.animation.AnimControl;
import com.jme3.animation.LoopMode;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.asset.AssetNotFoundException;
import com.jme3.asset.plugins.FileLocator;
import com.jme3.collision.CollisionResults;
import com.jme3.export.binary.BinaryExporter;
import com.jme3.math.Ray;
import com.jme3.math.Vector3f;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Bob
 */
public class Player extends Node {
    
  public Node                   model;
  public AnimControl            animControl;
  public AnimChannel            armChannel;
  public AnimChannel            legChannel;
  public float                  speedMult;
  public long                   lastSwung;
  public boolean                hasSwung;
  public ArrayList              keyList;
  public int                    bestLevel;
  public int                    currentLevel;
  
  public Player(AppStateManager stateManager) {
      
    model        = (Node) stateManager.getApplication().getAssetManager().loadModel("Models/Person/Person.j3o");
    animControl  =  model.getChild("Person").getControl(AnimControl.class);
    armChannel   = animControl.createChannel();
    legChannel   = animControl.createChannel();
    keyList      = new ArrayList();
    bestLevel    = readScore(stateManager);
    model.scale(.3f);
    //attachChild(model);
    model.setLocalTranslation(0,0,0);
    model.setMaterial(stateManager.getApplication().getAssetManager().loadMaterial("Materials/Person.j3m"));
    armChannel.addFromRootBone("TopSpine");
    legChannel.addFromRootBone("BottomSpine");
    armChannel.setAnim("ArmIdle");
    legChannel.setAnim("LegsIdle");
    }
  
  public void saveScore(int newScore, AppStateManager stateManager) {
    
    String filePath;
      
    if("Dalvik".equals(System.getProperty("java.vm.name"))) {
        filePath = stateManager.getState(AndroidManager.class).filePath;
    }
    else {
        filePath = System.getProperty("user.home")+ "/";

    }
    
    BinaryExporter exporter = BinaryExporter.getInstance();
    Node score              = new Node();
    score.setUserData("Name", "Hope");
    score.setUserData("Score", newScore);
    File file               = new File(filePath + "/score.j3o");
    
      System.out.println("Saving Score");
    
    try {
        
      exporter.save(score, file);  
      System.out.println("Score saved to: " + filePath);
        
      }
    
    catch (IOException e) {
        
      Logger.getLogger(Main.class.getName()).log(Level.SEVERE, "Error: Failed to save game!", e);  
        System.out.println("Failure");
      }
    
      System.out.println("score completion");
    
    }
  
  public int readScore(AppStateManager stateManager) {
    
    String filePath;
      
    if("Dalvik".equals(System.getProperty("java.vm.name"))) {
        filePath = stateManager.getState(AndroidManager.class).filePath;
    }
    else {
        filePath = System.getProperty("user.home")+ "/";

    }
     AssetManager assetManager = stateManager.getApplication().getAssetManager();
     
     assetManager.registerLocator(filePath, FileLocator.class);
     
     Node newNode;
     int  score;
     
     try {
       newNode = (Node) assetManager.loadModel("score.j3o");
       score = newNode.getUserData("Score");
       }
     
     catch (AssetNotFoundException ex) {
       saveScore(0, stateManager);
       score = 0;    
       }
     
     catch (IllegalArgumentException e) {
       saveScore(0, stateManager);
       score = 0;
       }
     
     
     System.out.println("You've loaded: " + score);
     return score;
     }

  public void swing(AppStateManager stateManager) {
    lastSwung = System.currentTimeMillis() / 1000;
    hasSwung = true;
    armChannel.setAnim("ArmSwing");
    armChannel.setSpeed(2);
    armChannel.setLoopMode(LoopMode.DontLoop);
    }
  
  public boolean moveCheck(Vector3f moveDir, Node collisionNode) {

      Ray              ray     = new Ray(getLocalTranslation().multLocal(1,0,1).add(0,1,0), moveDir);
      CollisionResults results = new CollisionResults();
      collisionNode.collideWith(ray, results);
      
      for (int i = 0; i < results.size(); i++) {
          
          float dist = results.getCollision(i).getContactPoint().distance(this.getLocalTranslation());
          
          if (results.getCollision(i).getContactPoint().distance(this.getLocalTranslation()) < 1.3f) {
              return false;
          }
          
          else {
              return true;
          }
          
      }
      
      return true;
      
  }
  
  public void run() {
 
    if (!armChannel.getAnimationName().equals("ArmRun") && !hasSwung){
      armChannel.setAnim("ArmRun");
      }
    
    if (!legChannel.getAnimationName().equals("LegRun")){
      legChannel.setAnim("LegRun");
      }
    
    }
    
  public void idle(){

    if (!armChannel.getAnimationName().equals("ArmIdle") && !hasSwung){
      armChannel.setAnim("ArmIdle");
      }
    
    if (!legChannel.getAnimationName().equals("LegsIdle")){
      legChannel.setAnim("LegsIdle");
      }
    
    }
    
  }
