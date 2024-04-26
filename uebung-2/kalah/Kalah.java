package kalah;

import java.util.List;

/**
 * Hauptprogramm für KalahMuster.
 *
 * @since 29.3.2021
 * @author oliverbittel
 */
public class Kalah {

    // Farben ausstellen, weil ich keine Lust habe, mein Terminal-Theme zu ändern
    private static final String ANSI_BLUE = "";
    // private static final String ANSI_BLUE = "\u001B[34m";
    private static int minCalls = 0;
    private static int maxCalls = 0;
    private static int turns = 0;
    private static final boolean USE_HEURISTIC = true;

    /**
     *
     * @param args wird nicht verwendet.
     */
    public static void main(String[] args) {
        // testExample();
        // testHHGame();
        testMiniMaxAndAlphaBetaWithGivenBoard();
        // testHumanMiniMax();
        // testHumanMiniMaxAndAlphaBeta();
    }

    /**
     * Beispiel von https://de.wikipedia.org/wiki/Kalaha
     */
    public static void testExample() {
        KalahBoard kalahBd = new KalahBoard(new int[]{5, 3, 2, 1, 2, 0, 0, 4, 3, 0, 1, 2, 2, 0}, 'B');
        kalahBd.print();

        System.out.println("B spielt Mulde 11");
        kalahBd.move(11);
        kalahBd.print();

        System.out.println("B darf nochmals ziehen und spielt Mulde 7");
        kalahBd.move(7);
        kalahBd.print();
    }

    /**
     * Mensch gegen Mensch
     */
    public static void testHHGame() {
        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action = kalahBd.readAction();
            kalahBd.move(action);
            kalahBd.print();
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
    }

    public static void testMiniMaxAndAlphaBetaWithGivenBoard() {
        minCalls = 0;
        maxCalls = 0;
        turns = 0;

        KalahBoard kalahBd = new KalahBoard(new int[]{2, 0, 4, 3, 2, 0, 0, 1, 0, 1, 3, 2, 1, 0}, 'A');
        // A ist am Zug und kann aufgrund von Bonuszügen 8-aml hintereinander ziehen!
        // A muss deutlich gewinnen!
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                // Berechnen Sie für A eine Aktion mit Ihrem Verfahren und geben Sie die Aktion auf der Konsole aus.
                action = maxActionAlphaBeta(kalahBd).getLastPlay();
                // action = maxAction(kalahBd).getLastPlay();
                System.out.println(ANSI_BLUE + "A spielt Mulde: " + action);
            }
            else {
                action = kalahBd.readAction();
            }
            kalahBd.move(action);
            kalahBd.print();
            turns++;
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
        System.out.println("avg Min-Aufrufe: " + minCalls + " / " + turns + " = " + minCalls / turns);
        System.out.println("avg Min-Aufrufe: " + maxCalls + " / " + turns + " = " + maxCalls / turns);
    }

    private static void testHumanMiniMax() {
        minCalls = 0;
        maxCalls = 0;
        turns = 0;

        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                action = maxAction(kalahBd).getLastPlay();
                System.out.println(ANSI_BLUE + "A spielt Mulde: " + action);
            }
            else {
                action = kalahBd.readAction();
            }
            kalahBd.move(action);
            kalahBd.print();
            turns++;
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
        System.out.println("avg Min-Aufrufe: " + minCalls + " / " + turns + " = " + minCalls / turns);
        System.out.println("avg Min-Aufrufe: " + maxCalls + " / " + turns + " = " + maxCalls / turns);
    }

    private static void testHumanMiniMaxAndAlphaBeta() {
        minCalls = 0;
        maxCalls = 0;
        turns = 0;

        KalahBoard kalahBd = new KalahBoard();
        kalahBd.print();

        while (!kalahBd.isFinished()) {
            int action;
            if (kalahBd.getCurPlayer() == 'A') {
                action = maxActionAlphaBeta(kalahBd).getLastPlay();
                System.out.println(ANSI_BLUE + "A spielt Mulde: " + action);
            }
            else {
                action = kalahBd.readAction();
            }
            kalahBd.move(action);
            kalahBd.print();
            turns++;
        }

        System.out.println("\n" + ANSI_BLUE + "GAME OVER");
        System.out.println("avg Min-Aufrufe:" + minCalls + " / " + turns + " = " + minCalls / turns);
        System.out.println("avg Min-Aufrufe:" + maxCalls + " / " + turns + " = " + maxCalls / turns);
    }

    /**
     * Min-Max-Algorithmus
     * liefert die beste Aktion zurück
     * @param state
     * @return
     */
    private static KalahBoard maxAction(KalahBoard state) {
        if (state.isFinished()) {
            return state;
        }

        int v = Integer.MIN_VALUE;
        KalahBoard action = state;

        for (KalahBoard currBoard : state.possibleActions()) {
            if (currBoard.isBonus()) {
                int v1 = maxValue(currBoard, 20);
                if (v1 > v)
                    v = v1;
                action = currBoard;
            } else {
                int v1 = minValue(currBoard, 20);
                if (v1 > v) {
                    v = v1;
                    action = currBoard;
                }
            }
        }
        return action;
    }

    private static int minValue(KalahBoard kalahBd, int depth) {
        minCalls++;

        if (kalahBd.isFinished() || depth <= 0) {
            return comp(kalahBd);
        }

        int v = Integer.MAX_VALUE;

        for (KalahBoard currBoard : kalahBd.possibleActions()) {
            if (currBoard.isBonus()) {
                v = Math.min(v, minValue(currBoard, depth));
            } else {
                v = Math.min(v, maxValue(currBoard, --depth));
            }
        }
        return v;
    }

    private static int maxValue(KalahBoard kalahBd, int depth) {
        maxCalls++;

        if (kalahBd.isFinished() || depth <= 0) {
            return comp(kalahBd);
        }

        int v = Integer.MIN_VALUE;

        for (KalahBoard currBoard : kalahBd.possibleActions()) {
            if (currBoard.isBonus()) {
                v = Math.max(v, maxValue(currBoard, depth));
            } else {
                v = Math.max(v, minValue(currBoard, --depth));
            }
        }
        return v;
    }

    /**
     * Min-Max-Algorithmus mit alpha-beta-Pruning
     * benutzt die Reihenfolge der möglichen Züge als Heuristik
     * liefert die beste Aktion zurück
     * @param state
     * @return
     */
    public static KalahBoard maxActionAlphaBeta(KalahBoard kalahBd) {
        if (kalahBd.isFinished()) {
            return kalahBd;
        }

        int v = Integer.MIN_VALUE;
        KalahBoard action = kalahBd;

        List<KalahBoard> possibleActions = kalahBd.possibleActions();
        if (USE_HEURISTIC) {
            // use heuristic
            possibleActions.sort((a, b) -> comp(b) - comp(a));
        }

        for (KalahBoard currBoard : possibleActions) {
            int v1;
            if (currBoard.isBonus()) {
                v1 = maxValueAlphaBeta(currBoard, 20, Integer.MIN_VALUE, Integer.MAX_VALUE);
            } else {
                v1 = minValueAlphaBeta(currBoard, 20, Integer.MIN_VALUE, Integer.MAX_VALUE);
            }
            if (v1 > v) {
                v = v1;
                action = currBoard;
            }
        }
        return action;
    }

    private static int minValueAlphaBeta(KalahBoard kalahBd, int depth, int alpha, int beta) {
        minCalls++;

        if (kalahBd.isFinished() || depth <= 0) {
            return comp(kalahBd);
        }

        int v = Integer.MAX_VALUE;

        List<KalahBoard> possibleActions = kalahBd.possibleActions();
        if (USE_HEURISTIC) {
            // use heuristic
            possibleActions.sort((a, b) -> comp(a) - comp(b));
        }

        for (KalahBoard currBoard : possibleActions) {
            if (currBoard.isBonus()) {
                v = Math.min(v, minValueAlphaBeta(currBoard, depth, alpha, beta));
            } else {
                v = Math.min(v, maxValueAlphaBeta(currBoard, --depth, alpha, beta));
            }
            if (v <= alpha)
                return v;
            beta = Math.min(beta, v);
        }

        return v;
    }

    private static int maxValueAlphaBeta(KalahBoard kalahBd, int depth, int alpha, int beta) {
        maxCalls++;

        if (kalahBd.isFinished() || depth <= 0) {
            return comp(kalahBd);
        }

        int v = Integer.MIN_VALUE;

        List<KalahBoard> possibleActions = kalahBd.possibleActions();
        if (USE_HEURISTIC) {
            // use heuristic
            possibleActions.sort((a, b) -> comp(b) - comp(a));
        }

        for (KalahBoard currBoard : possibleActions) {
            if (currBoard.isBonus()) {
                v = Math.max(v, maxValueAlphaBeta(currBoard, depth, alpha, beta));
            } else {
                v = Math.max(v, minValueAlphaBeta(currBoard, --depth, alpha, beta));
            }
            if (v >= beta)
                return v;
            alpha = Math.max(alpha, v);
        }

        return v;
    }

    private static Integer comp(KalahBoard kalahBd) {
        if (kalahBd.getCurPlayer() == 'A') {
            return kalahBd.getAKalah() - kalahBd.getBKalah();
        }
        else {
            return kalahBd.getBKalah() - kalahBd.getAKalah();
        }
    }
}
