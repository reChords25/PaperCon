/**
 * Kurzbeschreibung der Klasse Ufo:
 * ...
 * @author (Johannes) 
 * @version (1.0)
 */
import GLOOP.*;
import java.util.ArrayList;
public class Ufo
{
  //Objekte fuer das Flugzeug
  ArrayList<GLObjekt> ufoAll;
  GLKugel[] ufoDots;
  GLTorus ufoBody;
  GLKugel ufoTop;
  GLZylinder ufoMid;
  public GLVektor tmpSPCam, ufoPos;
  private GLVektor directionUfoX, directionUfoY, directionUfoZ;
  private double speed;

  public Ufo()
  {
    ufoAll = new ArrayList<GLObjekt>();
    ufoDots = new GLKugel[10];
    //Objekte werden erstellt und platziert
    ufoBody = new GLTorus(0,0,50,100,40);
    ufoMid = new GLZylinder (0,0,50,100,20);
    ufoTop = new GLKugel(0,60,0,90);

    ufoBody.skaliere(1.0,1.0,0.5);
    ufoBody.drehe(-90,0,0, 0,0,0);
    ufoBody.setzeFarbe(0.3,0.3,0.3);
    ufoBody.setzeGlanz(0.9, 0.9, 0.9, 255);
    ufoBody.setzeQualitaet(36);

    ufoMid.setzeFarbe(0.15,0.15,0.15);
    ufoMid.drehe(-90,0,0, 0,0,0);

    //ufoTop.skaliere(1.0,0.75,1.0);
    ufoTop.setzeMaterial(GLMaterial.RUBIN);
    //-------------------------------------------------------------\\
    directionUfoX = new GLVektor(1,0,0);
    directionUfoY = new GLVektor(0,1,0);
    directionUfoZ = new GLVektor(0,0,-1);
    ufoPos = new GLVektor(ufoBody.gibX(),ufoBody.gibY(),ufoBody.gibZ());

    tmpSPCam = new GLVektor(0,1,0);
    speed = 10;

    addPartsToList();
    generateDots();
  }

  void addPartsToList()
  {
    ufoAll.add(ufoBody);
    ufoAll.add(ufoTop);
  }

  private void generateDots()
  {
    GLVektor dotGenerateTmpVec = new GLVektor(ufoPos.x, 1, ufoPos.z);
    ufoDots = new GLKugel[10];
    for(int i=0; i<10; i++)
    {
      ufoDots[i] = new GLKugel(0,67,118,8);
      ufoDots[i].setzeFarbe(0,0.5,0);
      //ufoDots[i].setzeGlanz(0.9, 0.9, 0.9, 255);
      ufoDots[i].rotiere((i+1)*36, ufoPos, dotGenerateTmpVec);
    }
    /*
    public void fly()
    {
    GLVektor tmpDir = new GLVektor(directionUfoZ);
    tmpDir.skaliereAuf(speed);
    ufoPos.addiere(tmpDir);
    for(int i=0; i<UfoAll.size(); i++)
    {
    UfoAll.get(i).verschiebe(tmpDir);
    }
    tmpSPCam = tmpDir;
    }

    public void right()
    {
    for(int i=0; i<UfoAll.size(); i++)
    {
    UfoAll.get(i).rotiere(-1, UfoPos, directionUfoY);
    }

    directionUfoX.rotiere(-1, directionUfoY);
    directionUfoZ.rotiere(-1, directionUfoY);
    }

    public void left()
    {
    for(int i=0; i<UfoAll.size(); i++)
    {
    UfoAll.get(i).rotiere(1, UfoPos, directionUfoY);
    }

    directionUfoX.rotiere(1, directionUfoY);
    directionUfoZ.rotiere(1, directionUfoY);
    }

    public void up()
    {
    for(int i=0; i<UfoAll.size(); i++)
    {
    UfoAll.get(i).rotiere(1, UfoPos, directionUfoX);
    }

    directionUfoY.rotiere(1, directionUfoX);
    directionUfoZ.rotiere(1, directionUfoX);
    }

    public void down()
    {
    for(int i=0; i<UfoAll.size(); i++)
    {
    UfoAll.get(i).rotiere(-1, UfoPos, directionUfoX);
    }

    directionUfoY.rotiere(-1, directionUfoX);
    directionUfoZ.rotiere(-1, directionUfoX);
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
     */
  }
}