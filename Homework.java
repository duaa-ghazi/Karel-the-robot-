import stanford.karel.SuperKarel;

/**
 * Level 1: Put beepers only in the odd outside spots (e.g. 1x2, 2x1 are considered odd), then print the number how many you've put, and then
 * collect them all (do not duplicate beepers)
 * Level 2: Put beepers on all even spots, then print the number how many you've put, and finally collect them all
 * Level 3: Divide the map (using beepers) into 2 or 4 equal chambers (rectangles surrounded by walls or beepers),
 * depending on the map; see solution in 7x7; please note that you cannot put duplicate beepers.
 * Make sure to clean the map and print how many beepers you've put, then collect them all.
 *
 * 
 */
public class Homework extends SuperKarel {

    int putOutsidebeepersNum = 0;
    int putbeepersEvenspot = 0;
    int putbeepersDivide=0;
    int maxStreet = 1, maxAvenue = 1;
    int currentStreet = 1;
    int currentAvenue = 1;
    String action_level2 = "";
    String acttion_level3 = "";
    int level = 1;

    public void run() {
        setBeepersInBag(1000);

        //level#1
        //level1 putting the beepers
        MovesAllCorners();
        System.out.println("PutOutsidebeepersNum : "+putOutsidebeepersNum);
       // System.out.println(maxAvenue);
        //System.out.println(maxStreet);

        //level 1 pick the putting beepers
        // I dont want to moves to all corners to pick the beepers I want to move on outside corners just
        PickOutsideSpots();
        //level1done

        // level2
        //level2 putting the beepers
        level = 2;
        action_level2 = "putlevel2";
        MovesAllCorners();
        System.out.println("PutbeepersEvenspot : "+putbeepersEvenspot);
        //level2 pick the putting beepers
        action_level2 = "picklevel2";
        MovesAllCorners();
        //level2done

        //level3
        //level3 putting the beepers
        acttion_level3 = "putlevel3";
        DivideMap();
        System.out.println("PutbeepersDivide : "+putbeepersDivide);
        //level3 putting the beepers
        acttion_level3 = "picklevel3";
        DivideMap();

        //to use a nother world in same run we must to reinitilise the data
        putOutsidebeepersNum = 0;
        putbeepersEvenspot = 0;
        putbeepersDivide=0;
        maxStreet = 1;
        maxAvenue = 1;
        currentStreet = 1;
        currentAvenue = 1;
        action_level2 = "";
        acttion_level3 = "";
        level = 1;
    }

    private void MovesAllCorners() {
        boolean finish = false;
        while (!finish) {
            if (frontIsClear()) {
                    //Check each Corner in street which karel move forward until finish current street street
                    Check();
                    move();
                    currentAvenue++;
            } else {
                Check();//for the last corner in current Street in right side
                maxAvenue = currentAvenue;
                //Check if it finish all street from right side
                turnLeft();
                if (frontIsBlocked()) {
                    maxStreet = currentStreet;
                    turnRight();
                    //go position (1,1) since it finish all Corners in all Street and now karel in right side
                    turnRight();
                    for (int i = 0; i < maxStreet - 1; i++) {
                        move();
                    }
                    turnRight();
                    for (int i = 0; i < maxAvenue - 1; i++) {
                        move();
                    }
                    turnRight();
                    turnRight();
                    currentStreet = 1;
                    currentAvenue = 1;
                    break;
                }
                // if not finish
                move();
                currentStreet++;
                if(frontIsBlocked()){maxStreet=currentStreet;}
                turnLeft();

                while (frontIsClear()) {
                    //Check each Corner in street which karel move backward until finish current street
                    Check();
                    move();
                    currentAvenue--;
                }
                Check(); // for the last corner in current Street in left side
                //Check if it finish all street from right side
                turnRight();
                if (frontIsBlocked()) {
                    maxStreet = currentStreet;
                    turnRight();
                    ////go position (1,1) since it finish all Corners in all Street and now karel in left side
                    turnRight();
                    for (int i = 0; i < maxStreet - 1; i++) {
                        move();
                    }
                    turnLeft();
                    currentStreet = 1;
                    currentAvenue = 1;
                    break;

                }
                // if not finish
                move();
                currentStreet++;
                if(frontIsBlocked()){maxStreet=currentStreet;}
                turnRight();
            }
        }
    }

    //Functions For Level 1 and Level 2
    private void Check() {
        if (level == 1) {
            ChechOutSideSpots();
        } else if (level == 2) {
            CheckEvenSpots();
        }
    }

    private void ChechOutSideSpots() { // for put beepers only
        if (FulfillTheConditions()) { // Check each spot if it in outside or not
                if (!beepersPresent()) {
                    putBeeper();
                    putOutsidebeepersNum++;
                } else {
                    //notihng
                }
        }
        else {
                if (beepersPresent()) {
                    pickBeeper();
                } else {
                    //nothing
                }
        }
    }

    private boolean FulfillTheConditions() {
        boolean condition;
        if (currentStreet == 1 && currentAvenue % 2 != 0) {
            condition = true; //Check if it in bottom street with odd avenues
        } else if (currentAvenue == 1 && currentStreet % 2 != 0 && currentStreet != 1) {
            condition = true; //Check if it in left Avenue with odd streets
        } else if (maxAvenue != 1 && currentAvenue == maxAvenue && maxAvenue % 2 == 0 && currentStreet % 2 == 0) {
            condition = true; //Check if it in right Avenue with even streets if max avenue even
        } else if (maxAvenue != 1 && currentAvenue == maxAvenue && maxAvenue % 2 != 0 && currentStreet % 2 != 0) {
            condition = true;  //Check if it in right Avenue with odd streets if max avenue odd
        } else if (maxStreet != 1 && currentStreet == maxStreet && maxStreet % 2 == 0 && currentAvenue % 2 == 0) {
            condition = true; //Check if it in  above street  with even avenues if max street even
        } else if (maxStreet != 1 && currentStreet == maxStreet && maxStreet % 2 != 0 && currentAvenue % 2 != 0) {
            condition = true; //Check if it in  above street  with odd avenues if max street odd
        } else condition = false;

        return condition;
    }


    private void CheckEvenSpots() {
        if (action_level2.equals("putlevel2")) {
                if (currentStreet % 2 != 0 && currentAvenue % 2 == 0) { //put in even avenue and odd street
                    putBeeper();
                    putbeepersEvenspot++;
                } else if (currentStreet % 2 == 0 && currentAvenue % 2 != 0) { //or put in odd avenue and even street
                    putBeeper();
                    putbeepersEvenspot++;
                } else {// nothing
                }
        }
        else if (action_level2.equals("picklevel2")) {
                if (currentStreet % 2 != 0 && currentAvenue % 2 == 0) {
                    pickBeeper();
                } else if (currentStreet % 2 == 0 && currentAvenue % 2 != 0) {
                    pickBeeper();
                }else  {// nothing
                }
        }
    }

    private void PickOutsideSpots(){
        // I dont want to moves to all corners to pick the beepers I want to move on outside corners just
        for (int i = 0; i < maxAvenue - 1; i++) {
            if (beepersPresent()) pickBeeper();
            move();
        }
        turnLeft();
        for (int i = 0; i < maxStreet - 1; i++) {
            if (beepersPresent()) pickBeeper();
            move();
        }
        turnLeft();
        for (int i = 0; i < maxAvenue - 1; i++) {
            if (beepersPresent()) pickBeeper();
            move();
        }
        turnLeft();
        for (int i = 0; i < maxStreet - 1; i++) {
            if (beepersPresent()) pickBeeper();
            move();
        }
        turnLeft();
    }

    //Functions for Level3
    private void DivideMap() {
        if (maxAvenue % 2 == 0 && maxAvenue > 3) { // case1: maxAvenue even and greater or equal to 4
            for (int i = 0; i < maxAvenue / 2 - 1; i++) {
                move();
            }
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            move();
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            // we fill or pick first 2 Corners in 2 Avenues lets fill or pick the whole 2 Avenues
            FillOrPick2Avenue();
        } else if (maxAvenue % 2 != 0 && maxAvenue > 2) { // case2: maxAvenue odd and greater or equal to 3
            for (int i = 0; i < maxAvenue / 2; i++) {
                move();
            }
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            // we fill or pick first Corner in 1 Avenues lets fill or pick the whole  Avenue
            FillOrPick1Avenue();
        } else if (maxAvenue == 1 || maxAvenue == 2) { // case3: maxAvenue  equal to 1 or 2 we cant fill any avenue
            GotoFillOrPickRows();
        }
    }

    private void FillOrPick2Avenue() {
        if (maxStreet % 2 == 0) { //case1: maxstreet is even so i fill or pick in pattern + less then one pattern
            for (int i = 0; i < (maxStreet / 2) - 1; i++) {
                moveOnePatternColumns();
            }
            // fill or pick less than one pattern
            turnLeft();
            move();
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            turnLeft();
            move();
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            turnRight();
            turnRight();
            move();
            GoRighttoFillOrPickRows();
        } else {
            for (int i = 0; i < maxStreet / 2; i++) { //case1: maxstreet is even so i fill or pick in pattern
                moveOnePatternColumns();
            }
            GoRighttoFillOrPickRows();
        }
    }

    private void moveOnePatternColumns() {
        turnLeft();
        move();
        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
        else pickBeeper();
        turnLeft();
        move();
        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
        else pickBeeper();
        turnRight();
        move();
        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
        else pickBeeper();
        turnRight();
        move();
        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
        else pickBeeper();
    }

    private void FillOrPick1Avenue() {
        turnLeft();
        for (int i = 0; i < maxStreet - 1; i++) { // fill or pick all corners in avenue
            move();
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
        }
        turnRight();
        GoRighttoFillOrPickRows();
    }

    private void GoRighttoFillOrPickRows() {
        //Go to rows or row that must to pick or fill
        if (maxAvenue % 2 == 0) {
            for (int i = 0; i < (maxAvenue / 2) - 1; i++) {
                move();
            }
        } else {
            for (int i = 0; i < (maxAvenue / 2); i++) {
                move();
            }
        }
        turnRight();
        if (maxStreet % 2 == 0 && maxStreet > 3) {
            for (int i = 0; i < (maxStreet / 2) - 1; i++) {
                move();
            }
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            move();
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            FillOrPick2Street();
        } else if (maxStreet % 2 != 0 && maxStreet > 2) {
            for (int i = 0; i < maxStreet / 2; i++) {
                move();
            }
            if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
            else pickBeeper();
            FillOrPick1Street();
        } else if (maxStreet == 1 || maxStreet == 2) {
            //nothing to fill
            //so gohome
            if (!frontIsBlocked()) {
                move();
            }
            turnRight();
            for (int i = 0; i < maxAvenue - 1; i++) {
                move();
            }
            turnRight();
            turnRight();
        }
    }

    private void FillOrPick2Street() {
        if (maxAvenue % 2 == 0) {
            for (int i = 0; i < maxAvenue / 2 - 1; i++) {
                moveOnePatternRaw();
            }
            turnRight();
            move();
            if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
                putBeeper();putbeepersDivide++;
            } else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
                pickBeeper();
            } else {
            }
            turnRight();
            move();
            if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
                putBeeper();putbeepersDivide++;
            } else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
                pickBeeper();
            } else {
            }
            turnLeft();
            turnLeft();
            //GoHome
            for (int i = 0; i < maxStreet / 2; i++) {
                move();
            }
            turnLeft();

        } else {
            for (int i = 0; i < maxStreet / 2; i++) {
                moveOnePatternRaw();
            }
            //GoHome
            for (int i = 0; i < maxStreet / 2; i++) {
                move();
            }
            turnLeft();
        }

    }

    private void moveOnePatternRaw() {
        turnRight();
        move();
        if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
            putBeeper();putbeepersDivide++;
        }
        else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
            pickBeeper();
        } else {
        }
        turnRight();
        move();
        if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
            putBeeper();putbeepersDivide++;
        }
        else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
            pickBeeper();
        }
        else {}
        turnLeft();
        move();
        if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
            putBeeper();putbeepersDivide++;
        }
        else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
            pickBeeper();
        } else {}

        turnLeft();
        move();
        if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
            putBeeper();putbeepersDivide++;
        }
        else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
            pickBeeper();
        } else {}
    }

    private void FillOrPick1Street() {
        turnRight();
        for (int i = 0; i < maxAvenue - 1; i++) {
            move();
            if (acttion_level3.equals("putlevel3") && !beepersPresent()) {
                putBeeper();putbeepersDivide++;
            } else if (acttion_level3.equals("picklevel3") && beepersPresent()) {
                pickBeeper();
            } else {
            }
        }
        turnLeft();
        //go home
        for (int i = 0; i < maxStreet / 2; i++) {
            move();
        }
        turnLeft();
    }

    private void GotoFillOrPickRows() {
        if (maxStreet == 1 || maxStreet == 2) { //Nothong to do
        }
        else if (maxStreet % 2 == 0) {
                    turnLeft();
                    for (int i = 0; i < maxStreet / 2 - 1; i++) {
                        move();
                    }
                    if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
                    else pickBeeper();
                    move();
                    if (acttion_level3.equals("putlevel3")){putBeeper();putbeepersDivide++;}
                    else pickBeeper();
                    turnRight();
                    if (frontIsBlocked()) {
                        //goto home position(1,1)
                        turnRight();
                        for (int i = 0; i < maxStreet / 2; i++) {
                            move();
                        }
                        turnLeft();
                    }
                    else {
                        move();
                        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
                        else pickBeeper();
                        turnRight();
                        move();
                        if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
                        else pickBeeper();
                        //goto home position(1,1)
                        turnRight();
                        move();
                        move();
                        turnLeft();
                        for (int i = 0; i < maxStreet / 2 - 1; i++) {
                            move();
                        }
                        turnLeft();
                    }
        }
        else if (maxStreet % 2 != 0) {
                    turnLeft();
                    for (int i = 0; i < maxStreet / 2; i++) {
                        move();
                    }
                    if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
                    else pickBeeper();
                    turnRight();
                    if (frontIsBlocked()) {
                        //go home
                        turnRight();
                        for (int i = 0; i < maxStreet / 2; i++) {
                            move();
                        }
                        turnLeft();
            }
            else {
                move();
                if (acttion_level3.equals("putlevel3")) {putBeeper();putbeepersDivide++;}
                else pickBeeper();
                turnRight();
                turnRight();
                move();
                move();
                turnLeft();
                //go home position(1,1)
                turnRight();
                for (int i = 0; i < maxStreet / 2; i++) {
                    move();
                }
                turnLeft();
            }
        }
    }
}


