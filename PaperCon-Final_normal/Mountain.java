/**
 * Class for mountains in the ufo simulation
 * 
 * @author (Johannes, Ben Eric) 
 * @version (1.5)
 */
import GLOOP.GLTerrain;
import GLOOP.GLTextur;
import java.util.Random;

public class Mountain {
  //declaration of new GLTerrain for a mountain
  GLTerrain mountain;
  //declaration and initialization of the mountains' texture
  GLTextur mountainTexture = new GLTextur("Images/textures/mountain.jpg");
  //new array for storing mountain x positions
  int[] mountainList = {35000, 30000, 25000, 20000, 15000, 10000, 0, -10000, -15000, -20000, -25000, -30000, -35000};
  /**
   * Constructor of the Mountain class.
   */
  public Mountain() {
    //new randomizer for choosing x position value
    Random randomizer = new Random();
    
    //generation
    for(int z = -45000; z >= -660000; z-=15000) {
      //first generation
      int xCoord1 = mountainList[randomizer.nextInt(mountainList.length)];
      generateMountain(xCoord1, -100, z);
      //second generation
      int xCoord2 = mountainList[randomizer.nextInt(mountainList.length)];
      generateMountain(xCoord2, -100, z);
      //third generation
      int xCoord3 = mountainList[randomizer.nextInt(mountainList.length)];
      generateMountain(xCoord3, -100, z);
    }
    
    //manual generation of mountains around the takeoff runway
    generateMountain(-18000, -100, -15000);
    generateMountain(-17000, -100, -30000);
    generateMountain(18000, -100, -15000);
    generateMountain(17000, -100, -30000);
    generateMountain(-14000, -100, 15000);
    generateMountain(0, -100, 15000);
    generateMountain(14000, -100, 15000);
    
    //manual generation of mountains around the landing runway
    generateMountain(-18000, -100, -690000);
    generateMountain(-17000, -100, -675000);
    generateMountain(18000, -100, -690000);
    generateMountain(17000, -100, -675000);
    generateMountain(-14000, -100, -705000);
    generateMountain(0, -100, -710000);
    generateMountain(14000, -100, -705000);
  }
  
  /**
   * Creates a new mountain (size 15000, 9000, 15000) from the GLTerrain, no Mountain object needed.#
   * @param x X coordinate
   * @param y Y coordinate
   * @param z Z coordinate
   */
  void generateMountain(int x, int y, int z) {
    //applies heightmap
    mountain = new GLTerrain(x, y, z, "Images/heightmaps/heightmapMountain.png");
    //sets size
    mountain.setzeAbmessungen(15000, 9000, 15000);
    //sets texture
    mountain.setzeTextur(mountainTexture);
  }
}