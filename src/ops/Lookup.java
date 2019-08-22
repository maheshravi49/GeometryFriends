package ops;

/**
 * 
 * @author user
 *
 */
public class Lookup{
	
	final static double[] sin = calcSin();
	final static double[] cos = calcCos();
	
/*******************************************************************************************************************************************************************
 * This method will calculate the sine of the given angle which will be used for character's 
 * coordinates
 * @return sine(Angle)
 */
	public static double[] calcSin(){
		double[] sin = new double[360];//there are only 360 degrees in a circle
		for(int i=0; i < 360; i++ ){//i is going to represent each one of the 360 degress
			sin[i] = Math.sin((i * Math.PI)/180);//converting degrees to radians 
			//Math.cos() takes only radians so in order to get radians we have 
			// radians = (degrees*Pi)/180
		}
		return sin;//return the value of sine of the given angle
	}
	
/*******************************************************************************************************************************************************************
 * This method will calculate the cosine of the given angle which will be used for character's 
 * coordinates
 * @return cosine(Angle)
 */
	public static double[] calcCos(){
		double[] cos = new double[360];//there are only 360 degrees in a circle
		for(int i=0; i < 360; i++ ){//i is going to represent each one of the 360 degress
			cos[i] = Math.cos((i*Math.PI)/180);//converting degrees to radians 
			//Math.cos() takes only radians so in order to get radians we have 
			// radians = (degrees*Pi)/180
		}
		return cos;//return the value of cosine of the given angle
	}
}
