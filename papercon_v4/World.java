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
  private GLKamera camSP;
  private GLLicht lightF, lightB, lightL, lightR;
  private GLBoden floor;
  //private GLTerrain floors;
  private GLHimmel sky;
  private Plane plane1;

  //Steering
  private GLMaus mouse;
  private GLTastatur keyboard;

  //Texturen
  GLTextur floorTexture;
  GLTextur skyTexture;

  public World()
  {
    //Cameras and lights
    camSP = new GLKamera();
    lightF = new GLLicht(50000,50000,50000);
    lightB = new GLLicht(50000, 50000, -50000);
    lightL = new GLLicht(-50000,50000,50000);
    lightR = new GLLicht(-50000, 50000, -50000);

    //placing cameras
    camSP.setzePosition(0, 2000, 4000);

    //loading textures
    floorTexture = new GLTextur("images/textures/stone.png");
    skyTexture = new GLTextur("images/textures/skyblue.jpg");

    //loading sky and ground
    floor = new GLBoden(floorTexture);
    sky = new GLHimmel(skyTexture);

    //initializing keyboard and mouse
    keyboard = new GLTastatur();
    mouse = new GLMaus();

    //creates a plane object and starts the simulation loop
    plane1 = new Plane();
    simulation();
  }

  private void simulation()
  {
    while(!keyboard.esc())
    {
      if (keyboard.istGedrueckt(' '))
      {
        plane1.accelerate();
      }
      if (keyboard.shift())
      {
        plane1.brake();
      }
      if (keyboard.istGedrueckt('a'))
      {
        plane1.left();
      }
      if (keyboard.istGedrueckt('d'))
      {
        plane1.right();
      }
      if (keyboard.istGedrueckt('w'))
      {
        plane1.up();
      }
      if (keyboard.istGedrueckt('s'))
      {
        plane1.down();
      }
      plane1.fly();
      alignCamSP();
      Sys.warte(5);
    }
  }

  public void alignCamSP()
  {
    camSP.verschiebe(plane1.tmpSPCam);
    camSP.setzeBlickpunkt(plane1.body.gibX(), plane1.body.gibY() + 50, plane1.body.gibZ());
  }
}