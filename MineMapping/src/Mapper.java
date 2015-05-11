import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import lejos.nxt.Motor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;


public class Mapper {
	
	static int rotateIncrementL = 90;
	static int rotateIncrementR = 90;
	
	static NXTRegulatedMotor rightMotor = Motor.B;
	static NXTRegulatedMotor leftMotor = Motor.A;
	
	static UltrasonicSensor frontSensor = new UltrasonicSensor(SensorPort.S1);
	static UltrasonicSensor leftSensor = new UltrasonicSensor(SensorPort.S2);
	static UltrasonicSensor rightSensor = new UltrasonicSensor(SensorPort.S4);

	public static void main(String[] args) throws FileNotFoundException {
		
		boolean turnLeft = false;
		
		File data = new File("data.dat");
		FileOutputStream stream = new FileOutputStream(data);
		int counter = 0;
		
		while(true)
		{
			int frontData = frontSensor.getDistance();
			int leftData = leftSensor.getDistance();
			int rightData = rightSensor.getDistance();
			
			rightMotor.rotate(rotateIncrementR, true);
			leftMotor.rotate(rotateIncrementL, true);
			
			if(Math.abs(frontSensor.getDistance()) - frontData < 3)
			{
				leftMotor.rotate(-180, true);
				rightMotor.rotate(180, false);
				turnLeft = true;
			}
			
			if(Math.abs(leftSensor.getDistance() - leftData) > 10)
			{
				turnLeft = true;
				leftMotor.rotate(-180, true);
				rightMotor.rotate(180, false);
			}
			
			String dataReading = ""+counter+","+leftData+","+rightData+","+frontData+","+turnLeft+"\n";
			turnLeft = false;
			try {
				stream.write(dataReading.getBytes());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

}
