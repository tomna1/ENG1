package io.github.archessmn.eng1.events;
import java.util.Random;


public class EventManager {
    //private static Random randInt = new Random();
    //private static int eventStartTime = randInt.nextInt(1,85);
    private static int eventStartTime = 5;

    private static int[] eventOrder = {1,2,3};
    private static int eventOrderIndex = 0;
    private static boolean eventActive = false;

    public static boolean checkEvents(int elapsedTime){
        if (eventOrderIndex == eventOrder.length){
            return false;
        }
        if(eventActive){
            switch (eventOrder[eventOrderIndex]) {
                case 1 -> eventActive = LectureHallMaintenance.runEvent();
                case 2 -> eventActive = AlienInvasion.runEvent();
                case 3 -> eventActive = GoodBadLecture.runEvent();
                default -> throw new AssertionError();
            }
            if (!eventActive){
                //eventStartTime = randInt.nextInt(elapsedTime+10, 180);
                eventStartTime = elapsedTime + 4;
                eventOrderIndex += 1;
            }


            return true;
        }else{
            eventActive = (eventStartTime == elapsedTime);
            return false;
        }
    }


}
