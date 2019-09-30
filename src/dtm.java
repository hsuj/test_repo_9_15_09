import java.io.File;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class dtm {
    enum State {
        q0, q1, q2, q3, qY, qN;
    }

    public static void main(String[] args) throws Exception {
        URL url = dtm.class.getResource("tape.txt");
        File file = new File(url.getPath());
        Scanner inputScanner = new Scanner(file);
        String tapeOneInput = inputScanner.nextLine().toString();
        String tapeOne = "b" + tapeOneInput + "bb";
        String tapeTwo = "";
        if(inputScanner.hasNextLine()) {
            tapeTwo = inputScanner.nextLine().toString();
        }

        statesMethod(tapeOne, tapeTwo);
    }

    //state transitions
    public static void statesMethod(String tapeOne, String tapeTwo) {
        List<Object> tuple = new ArrayList<Object>(
                Arrays.asList(State.q0, Character.toString(tapeOne.charAt(1)), null, null, null)); //q,x,q',x',s
        State currentState = (State) tuple.get(0);
        String currentSymbol = (String) tuple.get(1);
        int currentReadWriteHeadIndex = 1;

        while(currentState != State.qY && currentState != State.qN) {
            switch (currentState) {
                case q0:
                    if(currentSymbol.equals("0")) {
                        tuple.set(2, State.q0);
                        tuple.set(3, "0");
                        tuple.set(4, "+1");
                    }
                    else if(currentSymbol.equals("1")) {
                        tuple.set(2, State.q0);
                        tuple.set(3, "1");
                        tuple.set(4, "+1");
                    }
                    else if(currentSymbol.equals("b")) {
                        tuple.set(2, State.q1);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    break;
                case q1:
                    if(currentSymbol.equals("0")) {
                        tuple.set(2, State.q2);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("1")) {
                        tuple.set(2, State.q3);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("b")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    break;
                case q2:
                    if(currentSymbol.equals("0")) {
                        tuple.set(2, State.qY);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("1")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("b")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    break;
                case q3:
                    if(currentSymbol.equals("0")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("1")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    else if(currentSymbol.equals("b")) {
                        tuple.set(2, State.qN);
                        tuple.set(3, "b");
                        tuple.set(4, "-1");
                    }
                    break;
            }

            //overwrites tape with current symbol at read write head
            tapeOne = tapeOne.substring(0, currentReadWriteHeadIndex)
                    + tuple.get(3) + tapeOne.substring(currentReadWriteHeadIndex + 1);

            printMethod(tapeOne, tapeTwo, tuple);

            if(tuple.get(4).equals("+1")) {
                currentReadWriteHeadIndex++;
            }
            else if(tuple.get(4).equals("-1")) {
                currentReadWriteHeadIndex--;
            }

            currentState = (State) tuple.get(2);
            currentSymbol = Character.toString(tapeOne.charAt(currentReadWriteHeadIndex));
            tuple.set(0, tuple.get(2)); //next state becomes current state
            tuple.set(1, tuple.get(3)); //next symbol becomes current symbol
        }

        switch (currentState) {
            case qY:
                System.out.println("DTM decided yes.");
                break;
            case qN:
                System.out.println("DTM decided no.");
                break;
        }
        return;
    }

    public static void printMethod(String tapeOne, String tapeTwo, List<Object> tuple) {
        System.out.print("Current State: " + tuple.get(0) + ", ");
        System.out.print("Current Symbol: " + tuple.get(1) + ", ");
        System.out.print("Next State: " + tuple.get(2) + ", ");
        System.out.print("Next Symbol: " + tuple.get(3) + ", ");
        System.out.println("Direction: " + tuple.get(4));
        System.out.println("TapeOne: " + tapeOne);
        if(!tapeTwo.isEmpty()) {
            System.out.println("TapeTwo: " + tapeTwo);
        }
    }
}
