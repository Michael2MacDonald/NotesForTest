package Donnager;
import robocode.*;
import static robocode.util.Utils.normalRelativeAngleDegrees;
// import java.util.Date;
import java.awt.Color;

// API help : https://robocode.sourceforge.io/docs/robocode/robocode/Robot.html

/**
 * Donnager - a robot by Michael MacDonald
 */

// public class Donnager extends AdvancedRobot {
	public class Donnager extends TeamRobot {

	// Date date = new Date();

	String TargetName; // Name of the robot we're currently targeting
	double TargetEnergy; // Remaining energy of the robot we're currently targeting
	
	// switch target to null of we have not hit our target in <TargetTimeout> millis
	// long TargetTimeout = 5000; // 5 seconds
	// long sTargetTimeout;

	public void run() {

		setAdjustGunForRobotTurn(true); // Keep the gun still when we turn
		setColors(Color.red,Color.red,Color.red); // body,gun,radar

		TargetName = null; // Initialize to not targeting anyone
		TargetEnergy = 1000; // start at 1000 to ensure the target will be updated to a real robot

		// Robot main loop
		while(true) {
			// setDebugProperty("Targetname", TargetName);
			// setDebugProperty("TargetEnergy", ""+TargetEnergy);
			System.out.println("Targetname: " + TargetName + "   TargetEnergy: " + TargetEnergy);

			if (TargetEnergy <= 0) {
				TargetName = null;
				TargetEnergy = 1000;
			}

			// Move in a circle if we are not already doing a movement
			// setMaxVelocity(5);
			if (getTurnRemaining()==0) {
				setTurnRight(100000); // turn
			}
			if (getDistanceRemaining()==0) {
				setAhead(100000); // drive
			}
			if (getGunTurnRemaining()==0){
				setTurnGunRight(10000); // sweep radar
			}
			execute(); // run all the things we set to do
		}
	}

	// when you scan another robot
	public void onScannedRobot(ScannedRobotEvent e) {
		if (getTurnRemaining()==0) { // just to ensure they run even if onScannedRobot() is being called repetitively
			setTurnRight(100000); // turn
		}
		if (getDistanceRemaining()==0) {
			setAhead(100000); // drive
		}

		if ( !isTeammate(e.getName()) ) { // if it is not a teamate
			double absoluteBearing = getHeading() + e.getBearing();
			double bearingFromGun = normalRelativeAngleDegrees(absoluteBearing - getGunHeading());

			System.out.println("scanning: " + e.getName());

			if (TargetName == null) { // if we are not targeting anyone then target it
				TargetName = e.getName();
				TargetEnergy = e.getEnergy();
				setTurnGunRight(bearingFromGun);
				execute();
			} else if (e.getEnergy() < TargetEnergy) { // if robot is weaker than current target then switch target
				TargetName = e.getName();
				TargetEnergy = e.getEnergy();
				setTurnGunRight(bearingFromGun);
				execute();
			} else if ( TargetName.equals(e.getName()) ) { // turn gun to opponent only if it is our target
				TargetEnergy = e.getEnergy();
				// setTurnGunRight(getHeading() + e.getBearing() - getGunHeading()); // turn gun to opponent
				setTurnGunRight(bearingFromGun);
				execute();
			}

			// fire even if it is not the target
			// if (Math.abs(bearingFromGun) <= 3) {
				if (getGunHeat() == 0) { // don't waste turns firing when we can't
					if (getEnergy()<20) { // save energy when low
						fire(1);
					} else if (e.getDistance() <= 200) { // use more energy when you are closer and have a better chance of hitting
						fire(3);
					} else { fire(2); }
				}
			// }
		}
	}

	// when we hit someone
	public void onBulletHit(BulletHitEvent e) {
		if ( !isTeammate(e.getName()) ) { // if it is not a teamate
			if (TargetName == null) { // if we are not targeting anyone then target it
				TargetName = e.getName();
				TargetEnergy = e.getEnergy();
			} else if (e.getEnergy() < TargetEnergy) { // if robot is weaker than current target then switch target
				TargetName = e.getName();
				TargetEnergy = e.getEnergy();
			} else if ( TargetName.equals(e.getName()) ) { // if it is our target
				TargetEnergy = e.getEnergy(); // update energy
			}
		}
	}

	// when we are hit by someone
	// public void onHitByBullet(HitByBulletEvent e) {
	// 	if ( !isTeammate(e.getName()) ) { // if it is not a teamate
	// 		if (e.getBearing() > -10 && e.getBearing() < 10) {
	// 			fire(2);
	// 		}
	// 		if (e.getName().equals(TargetName)) { // if it is our target
	// 			turnGunRight(getHeading() - getGunHeading() + e.getBearing());
	// 			fire(1);
	// 		}
	// 	}
	// }


	// when we hit a wall
	public void onHitWall(HitWallEvent e) {
		setBack(60);
		setTurnRight(180);
		execute();
	}	


	// when we collide with another robot
	public void onHitRobot(HitRobotEvent e) {
		// if ( !isTeammate(e.getName()) ) { // if it is not a teamate
		// 	turnGunRight(getHeading() - getGunHeading() + e.getBearing()); // turn to opponent
		// 	fire(3);
		// }
		if (e.isMyFault()) { // if we ran into them then we backup and adjust corse before continuing
			setBack(60);
			setTurnRight(30);
			execute();
		}
	}


	// when a robot dies
	public void onRobotDeath(RobotDeathEvent e) {
		System.out.println("Died: " + e.getName());
		if ( TargetName!=null && TargetName.equals(e.getName()) ) { // if it is our target then set target to null
			System.out.println("Detected the death of the target: " + e.getName());
			TargetName = null;
			TargetEnergy = 1000;
		}
	}


}
