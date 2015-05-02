package mygame;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {

    
    public static void main(String[] args) {
        Main app = new Main();
        app.start();
    }

    @Override
    public void simpleInitApp() {
    this.setShowSettings(false);
    this.setDisplayFps(false);
    this.setDisplayStatView(false);  
    flyCam.setEnabled(false);
    stateManager.attach(new PlayerManager());
    stateManager.attach(new InteractableManager());
    stateManager.attach(new SceneManager());
    stateManager.attach(new CameraManager());
    stateManager.attach(new InteractionManager());
    stateManager.attach(new GuiManager());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
