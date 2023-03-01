/**
 * (Nearly?-) Main class of the project, links all other classes togehter and executes main code.
 * 
 * @author (Johannes, Ben Eric) 
 * @version (6.2)
 */

import GLOOP.GLKamera;
import GLOOP.GLLicht;
import GLOOP.GLBoden;
import GLOOP.GLHimmel;
import GLOOP.GLMaus;
import GLOOP.GLTastatur;
import GLOOP.GLTextur;
import GLOOP.Sys;

public class World
{
  //basic window objects
  private GLKamera camTP; //third person camera
  private GLLicht lightF, lightB, lightL, lightR; //for lights, the ufo needs to be lit up from all sides
  private GLBoden floor; //the floor
  private GLHimmel sky; //the sky

  //environment stuff
  private Runway runwayStart; //takeoff runway
  private Runway runwayEnd; //landing runway
  private Mountain mountain; //terrain fo mountains

  //controls
  private GLMaus mouse; //mouse (what else...)
  private GLTastatur keyboard; //keyboard (what else...)

  //textures
  private GLTextur floorTexture;
  private GLTextur skyTexture;

  //THE UFOOOO
  private Ufo ufo;

  /**
   * Constructor of the World class.
   */
  public World() {
    //camera and lights
    camTP = new GLKamera();
    lightF = new GLLicht(25000,25000,25000);
    lightB = new GLLicht(25000, 25000, -25000);
    lightL = new GLLicht(-25000,25000,25000);
    lightR = new GLLicht(-25000, 50000, -25000);

    //place camera
    camTP.setzePosition(0, 400, 800);

    //initialize/load textures
    floorTexture = new GLTextur("images/textures/meadow.jpg");
    skyTexture = new GLTextur("images/textures/skyblue.jpg");

    //initialize/load sky and ground
    floor = new GLBoden(floorTexture);
    sky = new GLHimmel(skyTexture);

    //initialize/load environment
    runwayStart = new Runway(33000, 5000, -30000);
    runwayEnd = new Runway(33000, 5000, -690000); 
    mountain = new Mountain();

    //initialize keyboard and mouse
    keyboard = new GLTastatur();
    mouse = new GLMaus();

    //create a ufo object and start the simulation loop
    ufo = new Ufo();
    simulation();
  }

  /**
   * Links keyboard keys to ufo methods and executes them, furthermore corrects camera and lights.
   */
  private void simulation() {
    while(!keyboard.esc()) {
      if (keyboard.istGedrueckt('w')) {
        ufo.forwards();
      }
      if (keyboard.istGedrueckt('s')) {
        ufo.backwards();
      }
      if (keyboard.istGedrueckt('a')) {
        ufo.left();
      }
      if (keyboard.istGedrueckt('d')) {
        ufo.right();
      }
      if (keyboard.istGedrueckt(' ')) {
        ufo.up();
      }
      if (keyboard.shift()) {
        ufo.down();
      }
      ufo.strgEnabled = keyboard.strg(); //update boost mode
      ufo.move(); //move ufo
      updateLightPosition(); //...
      updateCamTPPosition(); //...
      updateCamTPPointOfView(); //...
      Sys.warte(5); //wait, without performance and gameplay issues
    }
    Sys.beenden();
  }

  /**
   * Updates the position of the third person camera.
   */
  private void updateCamTPPosition() {
    camTP.setzePosition(ufo.ufoPos.x, ufo.ufoPos.y+420, ufo.ufoPos.z+800);
  }

  /**
   * Updates the point of view of the third person camera.
   */
  private void updateCamTPPointOfView() {
    camTP.setzeBlickpunkt(ufo.ufoPos.x, ufo.ufoPos.y+120, ufo.ufoPos.z);
  }

  /**
   * Updates the position of the lights, they move with the ufo.
   */
  private void updateLightPosition() {
    lightF.setzePosition(ufo.ufoPos.x+25000, ufo.ufoPos.y+25000, ufo.ufoPos.z+25000);
    lightB.setzePosition(ufo.ufoPos.x+25000, ufo.ufoPos.y+25000, ufo.ufoPos.z-25000);
    lightL.setzePosition(ufo.ufoPos.x-25000, ufo.ufoPos.y+25000, ufo.ufoPos.z+25000);
    lightR.setzePosition(ufo.ufoPos.x-25000, ufo.ufoPos.y+25000, ufo.ufoPos.z-25000);
  }
}