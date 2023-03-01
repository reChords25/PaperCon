/**
 * The ufo for the simulation, movement is soooo smooth!
 * 
 * @author (Ben Eric) 
 * @version (3.8)
 */

import GLOOP.GLObjekt;
import GLOOP.GLKugel;
import GLOOP.GLTorus;
import GLOOP.GLZylinder;
import GLOOP.GLMaterial;
import GLOOP.GLVektor;

import java.util.ArrayList;
public class Ufo
{
  //objects of the ufo
  private ArrayList<GLObjekt> ufoAll; //ArrayList used for storing all GLObjekt objects from the ufo (-> used for movement)

  //GLObjekt objects
  private GLKugel[] ufoDots; //colored dots on ufo's edge
  private GLTorus ufoBody; //ring body
  private GLKugel ufoTop; //top capsule
  private GLZylinder ufoMid; //floor 

  //position vector
  public GLVektor ufoPos;

  //speed etc. objects and variables
  private GLVektor momentum; //movement vector (scaled to speed -> direction)
  private double[] speeds; //array containing x ([0]), y ([1]) and z ([2]) speed
  private int vMax; //integer for maximum speed
  private double acceleration, deacceleration, natDeacceleration, strgBoost; //aceleration values + boost value ("sprint")
  public boolean strgBoostOn, strgEnabled; //booleans to perform precise checking and (de)activating of the boost
  
  /**
   * Constructor of the Ufo class.
   */
  public Ufo() {
    //------ initializing variables etc --------\\
    ufoAll = new ArrayList<GLObjekt>(); //look at "addPartsToList();", last code paragraph

    speeds = new double[3]; //3 fields for 3 speeds; x, y and z
    vMax = 100; //initialize the maximum speed to 100

    acceleration = 0.29; //acceleration (if no movement in opposite direction of method)
    deacceleration = 0.39; //acceleration (if  movement in opposite direction of method, for basically slowing down)
    natDeacceleration = 0.09; //ufo stands still after some time when not having touched any keys

    strgBoost = 0.15; //value that is added to accelerations when strg is pressed, meaning it accelerates faster
    strgEnabled = false; //direct check from keyboard
    strgBoostOn = false; //same, but one code cycle delayed

    momentum = new GLVektor(0,0,0);

    //------ initializing all of the GLObjekt objects --------\\
    initUfoObjects(); //look at end of code
    ufoPos = getCoords(); //position of ufo body, look at "getCoords();" (end of code)
    generateDots(); //generating side dots (or lamps or whatever)
    addPartsToList();
  }

  /**
   * Peforms general correction of movement values.
   * 
   * Vector variant
   * 
   * Use the
   * @param v Vector as Input.
   * @param goIntoGround Boolean for anti grounding - If true, ufo is allowed to pass through ground.
   */
  public void moveCalc(GLVektor v, boolean goIntoGround) {
    if(!goIntoGround && this.getCoords().y<=40 && v.y<0){v.y = 0; speeds[1] = 0;} //if it is too far down, prevent it from going even further
    //actual moving
    for(int i=0; i<ufoAll.size(); i++)
    {
      ufoAll.get(i).verschiebe(v);
    }
    //deacceleration, slows down naturally - for automatic stopping while no keys are pressed
    for(int i=0; i<=2; i++){
      if(speeds[i]<0){speeds[i]+=natDeacceleration;}
      if(speeds[i]>0){speeds[i]-=natDeacceleration;}
      //prevents jumping between near zero values that are in between natDeacceleration and -natDeacceleration
      if((speeds[i]<natDeacceleration && speeds[i]>0) || (speeds[i]>-natDeacceleration && speeds[i]<0)){speeds[i]=0;}
    }	
  }

  /**
   * Peforms general correction of movement values.
   * 
   * XYZ-variant
   * 
   * Use the
   * @param v Vector as Input.
   * @param goIntoGround Boolean for anti grounding - If true, ufo is allowed to pass through ground.
   */
  public void moveCalc(double pX, double pY, double pZ, boolean goIntoGround) {
    if(!goIntoGround && this.getCoords().y<=40 && pY<0){pY = 0; speeds[1] = 0;} //if it is too far down, prevent it from going even further
    //actual moving
    for(int i=0; i<ufoAll.size(); i++)
    {
      ufoAll.get(i).verschiebe(pX, pY, pZ);
    }
    //deacceleration, slows down naturally - for automatic stopping while no keys are pressed
    for(int i=0; i<=2; i++){
      if(speeds[i]<0){speeds[i]+=natDeacceleration;}
      if(speeds[i]>0){speeds[i]-=natDeacceleration;}
      //prevents jumping between near zero values that are in between natDeacceleration and -natDeacceleration
      if((speeds[i]<natDeacceleration && speeds[i]>0) || (speeds[i]>-natDeacceleration && speeds[i]<0)){speeds[i]=0;}
    }	
  }

  /**
   * Peforms actual movement of the ufo with values of other methods.
   */
  public void move() {
    GLVektor momentum = new GLVektor(speeds[0], speeds[1], speeds[2]); //new direction vector
    moveCalc(momentum,false); //execute moveCalc() in momentum direction with grounding protection
    ufoPos = getCoords(); //updates ufo position

    //if ufo is to deep down, correct the position upwards (bugfix)
    if(ufoPos.y<=40){
      moveCalc(0,40-ufoPos.y,0,false); 
      ufoPos = getCoords(); //updates ufo position again then
    }

    //controls "boost mode", meaning adds or subtracts boost value to or from acceleration values
    if(strgEnabled ^ strgBoostOn){
      if(!strgEnabled){
        remStrgBoost();
        strgBoostOn = false;
      }
      if(strgEnabled){
        addStrgBoost();
        strgBoostOn = true;
      }
    }
  }

  /**
   * Makes z speed more negative
   */
  public void forwards() {
    if(speeds[2]>=-vMax+acceleration && speeds[2]<0)
    {
      speeds[2]-=acceleration; //if ufo is flying forwards, use accelerate value
    }
    else if(speeds[2]>=0)
    {
      speeds[2]-=deacceleration; //if ufo is flying backwards, use deaccelerate value
    }
  }

  /**
   * Makes z speed more positive
   */
  public void backwards() {
    if(speeds[2]<=vMax-acceleration && speeds[2]>0)
    {
      speeds[2]+=acceleration; //if ufo is flying backwards, use accelerate value
    }
    else if(speeds[2]<=0)
    {
      speeds[2]+=deacceleration; //if ufo is flying forwards, use deaccelerate value
    }
  }

  /**
   * Makes x speed more negative
   */
  public void left() {
    if(speeds[0]>=-vMax+acceleration && speeds[0]<0)
    {
      speeds[0]-=acceleration; //if ufo is flying to the left, use accelerate value
    }
    else if(speeds[0]>=0)
    {
      speeds[0]-=deacceleration; //if ufo is flying to the right, use deaccelerate value
    }
  }

  /**
   * Makes x speed more positive
   */
  public void right() {
    if(speeds[0]<=vMax-acceleration && speeds[0]>0)
    {
      speeds[0]+=acceleration; //if ufo is flying to the right, use accelerate value
    }
    else if(speeds[0]<=0)
    {
      speeds[0]+=deacceleration; //if ufo is flying to the left, use deaccelerate value
    }
  }

  /**
   * Makes y speed more positive
   */
  public void up() {
    if(speeds[1]<=vMax-acceleration && speeds[1]>0)
    {
      speeds[1]+=acceleration; //if ufo is flying upwards, use accelerate value
    }
    else if(speeds[1]<=0)
    {
      speeds[1]+=deacceleration; //if ufo is flying downwards, use deaccelerate value
    }
  }

  /**
   * Makes y speed more negative
   */
  public void down() {
    if(speeds[1]>=-vMax+acceleration && speeds[1]<0)
    {
      speeds[1]-=acceleration; //if ufo is flying downwards, use accelerate value
    }
    else if(speeds[1]>=0)
    {
      speeds[1]-=deacceleration; //if ufo is flying upwards, use deaccelerate value
    }
  }

  /**
   * Activates boost mode
   */
  public void addStrgBoost() {
    //allow higher acceleration
    acceleration+=strgBoost;
    deacceleration+=strgBoost;
    //set vMax to super speeeed
    vMax = 180;
  }

  /**
   * Deactivates boost mode
   */
  public void remStrgBoost() {
    //reset acceleration
    acceleration-=strgBoost;
    deacceleration-=strgBoost;
    //set vMax to normal
    vMax = 100;
  }

  /**
   * Initializes and modifies GLObjekt objects, just to keep the standard instructor method nice and clean
   */
  public void initUfoObjects() {
    //initializing
    ufoDots = new GLKugel[10];
    ufoBody = new GLTorus(0,0,50,100,40);
    ufoMid = new GLZylinder (0,0,50,100,20);
    ufoTop = new GLKugel(0,60,0,90);

    //modifying body
    ufoBody.skaliere(1.0,1.0,0.5);
    ufoBody.drehe(-90,0,0, 0,0,0);
    ufoBody.setzeFarbe(0.3,0.3,0.3);
    ufoBody.setzeGlanz(0.9, 0.9, 0.9, 255);
    ufoBody.setzeQualitaet(36);

    //modifying mid part
    ufoMid.setzeFarbe(0.15,0.15,0.15);
    ufoMid.drehe(-90,0,0, 0,0,0);

    //modifying top part
    ufoTop.setzeMaterial(GLMaterial.RUBIN);
  }

  /**
   * Generates all of the small bullets on the edge of the ufo.
   */
  private void generateDots() {
    //new vector, used as rotation axis later on
    GLVektor dotGenerateTmpVec = new GLVektor(ufoPos.x, 1, ufoPos.z);
    //create 10 slot array for the ten bullets
    ufoDots = new GLKugel[10];
    //generation: every one of them is...
    for(int i=0; i<10; i++)
    {
      ufoDots[i] = new GLKugel(0,67,118,8); //...created
      ufoDots[i].setzeFarbe(0,0.5,0); //...colored
      ufoDots[i].setzeGlanz(0.9, 0.9, 0.9, 255); //...made shiny
      ufoDots[i].rotiere((i+1)*36, ufoPos, dotGenerateTmpVec); //turned around the dotGenerateTmpVec axis, by (i+1)*36°
    }
    dotGenerateTmpVec = null; //deletes the vector (basically useless)
  }

  /**
   * Adds all parts of the ufo to the ArrayList, is supposed to make moving them easier.
   */
  private void addPartsToList() {
    //general parts
    ufoAll.add(ufoBody);
    ufoAll.add(ufoMid);
    ufoAll.add(ufoTop);
    //dots
    ufoAll.add(ufoDots[0]);
    ufoAll.add(ufoDots[1]);
    ufoAll.add(ufoDots[2]);
    ufoAll.add(ufoDots[3]);
    ufoAll.add(ufoDots[4]);
    ufoAll.add(ufoDots[5]);
    ufoAll.add(ufoDots[6]);
    ufoAll.add(ufoDots[7]);
    ufoAll.add(ufoDots[8]);
    ufoAll.add(ufoDots[9]);
  }

  /**
   * Returns ufo coordinates as GLVektor
   * @return GLVektor Coordinates of the ufo.
   */
  public GLVektor getCoords() {
    return new GLVektor
    (
      ufoBody.gibX(),
      ufoBody.gibY(),
      ufoBody.gibZ()
    ); 
  }
}