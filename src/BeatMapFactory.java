/**
 * FACTORY PATTERN
 */
import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;
import java.util.Scanner;

public class BeatMapFactory {
	
	
	private float circleSize;
	private float approachRate;
	private float difficultyLevel;
	private Queue<HitObject> hitObjects = new ArrayDeque<HitObject>();
	
	private Color[] colorList = {
			new Color(217,65,116, 128),
			new Color(89,51,204, 128),
			new Color(54,195,217, 128),
			new Color(217,76,205, 128),
			new Color(124,51,204, 128)
	};
	
	
	public float getFloatValue(String line) {
		return Float.parseFloat(line.substring(line.indexOf(":") + 1));
	}
	
	
	/**
	 * Takes in a beatMap file name
	 * Initializes all instance variables of the beatMap class
	 * @param fileName
	 */
	public void readBeatMap(String fileName) {
	
			try {
			
				//scanner reads from file to check if file is empty
				File file = new File(fileName);
				System.out.println(file.exists());
				Scanner scanner = new Scanner(new FileInputStream(file));
				while(scanner.hasNextLine()) {
					String line = scanner.nextLine().strip();
					if(line.startsWith("[Difficulty]")) {
						//gobble up HpDrain
						scanner.nextLine();
						String circleSizeString = scanner.nextLine().strip();
						this.circleSize = getFloatValue(circleSizeString);
						String difficultyLevel = scanner.nextLine().strip();
						this.difficultyLevel = getFloatValue(difficultyLevel);
						String approachRate = scanner.nextLine().strip();
						this.approachRate = getFloatValue( approachRate);
					}
					
					if(line.startsWith("[HitObjects]")) {
						System.out.println(1);
						int circleNumber = 1;
						int colorIndex = 0;
						while(scanner.hasNext()) {
							String[] hitObjectParameters = scanner.nextLine().split(",");
							int x =  Integer.parseInt(hitObjectParameters[0]);
							int y = Integer.parseInt(hitObjectParameters[1]);
							int timeToClick = Integer.parseInt(hitObjectParameters[2]);
							int type = Integer.parseInt(hitObjectParameters[3]);
							if((type & 4) > 0) {
								circleNumber  = 1;
								colorIndex = (colorIndex + 1) % colorList.length;
							}
							HitObject hitObject = 
									new HitObject(x,y,timeToClick, this.circleSize, circleNumber, 
											this.colorList[colorIndex], 
											this.approachRate, this.difficultyLevel);
							this.hitObjects.add(hitObject);
							circleNumber++;
						}
						
					}
				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	
	public HitObject getNextHitObject() {
		return hitObjects.poll();
	}
	
	public float getApproachRate() {
		return approachRate;
	}
	
	public float getDifficultyLevel() {
		return difficultyLevel;
	}
	
	public String toString() {
		return hitObjects.toString();
	}


	public static void main(String[] args) {
		BeatMapFactory beatMap = new BeatMapFactory();
		beatMap.readBeatMap("Songs\\470977 Mili - worldexecute(me);\\Mili - world.execute(me); (Exile-) [mapset.easy(Exile-);].osu");
		System.out.println(beatMap);
	}
	
	
	
}
