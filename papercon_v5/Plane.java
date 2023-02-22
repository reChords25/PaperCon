/**
 * Kurzbeschreibung der Klasse Plane:
 * ...
 * @author (Johannes) 
 * @version (1.0)
 */

import GLOOP.*;
import java.util.ArrayList;
public class Plane
{
  //Objekte fuer das Flugzeug
  ArrayList<GLObjekt> planeAll;
  GLQuader wingL, wingR;
  GLZylinder body; 
  GLKegel tip;
  GLPrismoid steeringTriangle;
  private GLVektor directionPlaneX, directionPlaneY, directionPlaneZ;
  public GLVektor tmpSPCam, planePos;
  private double speed;

  public Plane()
  {
    planeAll = new ArrayList<GLObjekt>();
    //Objekte werden erstellt und platziert

    body = new GLZylinder(0, 400, 0, 150, 900);
    body.setzeFarbe(0.6, 0.6, 0.6);

    //Die beiden Fluegel
    wingL = new GLQuader(-400, 400, 0, 500, 10, 200);
    wingR = new GLQuader(400, 400, 0, 500, 10, 200);
    wingL.setzeFarbe(0, 0.75, 1);
    wingR.setzeFarbe(0, 0.75, 1);

    //Spitze des Flugzeugs
    tip = new GLKegel(0, 400, -550, 150, 200);
    tip.setzeFarbe(0, 0.75, 1);

    //Dreieck welches oben drauf ist
    steeringTriangle = new GLPrismoid(0, 590, 360, 100, 100, 3, 10);
    steeringTriangle.drehe(0, 90, 0);
    steeringTriangle.setzeFarbe(1, 0, 0);
    //-------------------------------------------------------------\\
    directionPlaneX = new GLVektor(1,0,0);
    directionPlaneY = new GLVektor(0,1,0);
    directionPlaneZ = new GLVektor(0,0,-1);
    planePos = new GLVektor(body.gibX(),body.gibY(),body.gibZ());

    tmpSPCam = new GLVektor(0,1,0);
    speed = 10;
    
    addPartsToList();
  }

  void addPartsToList()
  {
    planeAll.add(body);
    planeAll.add(wingL);
    planeAll.add(wingR);
    planeAll.add(tip);
    planeAll.add(steeringTriangle);
  }

  public void accelerate()
  {
    if(speed<=120)
    {
      speed += 0.1;
    }
  }

  public void brake()
  {
    if(speed>=0.11)
    {
      speed -= 0.1;
    }
  }

  public void fly()
  {
    GLVektor tmpDir = new GLVektor(directionPlaneZ);
    tmpDir.skaliereAuf(speed);
    planePos.addiere(tmpDir);
    for(int i=0; i<planeAll.size(); i++)
    {
      planeAll.get(i).verschiebe(tmpDir);
    }
    tmpSPCam = tmpDir;
  }

  public void right()
  {
    for(int i=0; i<planeAll.size(); i++)
    {
      planeAll.get(i).rotiere(-1, planePos, directionPlaneY);
    }

    directionPlaneX.rotiere(-1, directionPlaneY);
    directionPlaneZ.rotiere(-1, directionPlaneY);
  }

  public void left()
  {
    for(int i=0; i<planeAll.size(); i++)
    {
      planeAll.get(i).rotiere(1, planePos, directionPlaneY);
    }

    directionPlaneX.rotiere(1, directionPlaneY);
    directionPlaneZ.rotiere(1, directionPlaneY);
  }

  public void up()
  {
    for(int i=0; i<planeAll.size(); i++)
    {
      planeAll.get(i).rotiere(1, planePos, directionPlaneX);
    }
    
    directionPlaneY.rotiere(1, directionPlaneX);
    directionPlaneZ.rotiere(1, directionPlaneX);
  }

  public void down()
  {
    for(int i=0; i<planeAll.size(); i++)
    {
      planeAll.get(i).rotiere(-1, planePos, directionPlaneX);
    }

    directionPlaneY.rotiere(-1, directionPlaneX);
    directionPlaneZ.rotiere(-1, directionPlaneX);
  }
}