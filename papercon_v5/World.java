/**
 * Kurzbeschreibung der Klasse Ananaswelt:
 * ...
 * @author (Johannes) 
 * @version (1.0)
 */

import GLOOP.*;

public class World
{
  //Grundlagen fuer offenes Fenster
  private GLEntwicklerkamera camSP;
  private GLLicht lightF, lightB, lightL, lightR;
  private GLBoden floor;
  //private GLTerrain floors;
  private GLHimmel sky;
  private Ufo ufo1;

  //Steering
  private GLMaus mouse;
  private GLTastatur keyboard;

  //Texturen
  GLTextur floorTexture;
  GLTextur skyTexture;

  public World()
  {
    //Cameras and lights
    camSP = new GLEntwicklerkamera();
    lightF = new GLLicht(50000,50000,50000);
    lightB = new GLLicht(50000, 50000, -50000);
    lightL = new GLLicht(-50000,50000,50000);
    lightR = new GLLicht(-50000, 50000, -50000);

    //placing cameras
    camSP.setzePosition(0, 400, 800);

    //loading textures
    floorTexture = new GLTextur("images/stone.png");
    skyTexture = new GLTextur("images/skyblue.jpg");

    //loading sky and ground
    floor = new GLBoden(floorTexture);
    sky = new GLHimmel(skyTexture);

    //initializing keyboard and mouse
    keyboard = new GLTastatur();
    mouse = new GLMaus();

    //creates a ufo object and starts the simulation loop
    ufo1 = new Ufo();
    //simulation();
  }

  /*
  private void simulation()
  {
  while(!keyboard.esc())
  {
  if (keyboard.istGedrueckt(' '))
  {
  ufo1.accelerate();
  }
  if (keyboard.shift())
  {
  ufo1.brake();
  }
  if (keyboard.istGedrueckt('a'))
  {
  ufo1.left();
  }
  if (keyboard.istGedrueckt('d'))
  {
  ufo1.right();
  }
  if (keyboard.istGedrueckt('w'))
  {
  ufo1.up();
  }
  if (keyboard.istGedrueckt('s'))
  {
  ufo1.down();
  }
  ufo1.fly();
  alignCamSP();
  Sys.warte(5);
  }
  }
   */
  public void alignCamSP()
  {
    camSP.verschiebe(ufo1.tmpSPCam);
    camSP.setzeBlickpunkt(ufo1.ufoBody.gibX(), ufo1.ufoBody.gibY() + 50, ufo1.ufoBody.gibZ());
  }
}