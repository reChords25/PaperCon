/**
 * Runway for the ufo.
 * @author (Johannes, Ben Eric) 
 * @version (1.4)
 */

import GLOOP.GLTextur;
import GLOOP.GLQuader;

public class Runway {
  //object declaration, cuboids and texture
  GLQuader boden, markierung;
  GLTextur asphalt;
  /**
   * Constructor of the Runway class.
   */
  public Runway(double length, double width, double begin) {
    //texture initialization
    asphalt = new GLTextur("Images/textures/asphalt.jpg");
        
    //ground initialization/generation
    for (double x=-width/2; x<=width/2; x+=width/10){
      for (double z=begin; z<=begin+length; z+=width/10){
        boden = new GLQuader(x, 4, z, width/10, 3, width/10, asphalt);
      }
    }

    //line initialization/generation
    for(double z=begin+length/66; z<begin+length/66+length; z+=length/22){
      markierung = new GLQuader(0, 7, z, width/50, 7, width/50*6);
      markierung.setzeFarbe(1, 0.8, 0);
    }
  }
}