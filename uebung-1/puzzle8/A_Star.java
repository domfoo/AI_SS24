package puzzle8;

import java.util.Deque;
import java.util.HashMap;
import java.util.LinkedList;

/**
 *
 * @author domfoo
 */
public class A_Star {
	// cost ordnet jedem Board die Aktuellen Pfadkosten (g-Wert) zu.
	// pred ordnet jedem Board den Elternknoten zu. (siehe Skript S. 2-25). 
	// In cost und pred sind genau alle Knoten der closedList und openList enthalten!
	// Nachdem der Zielknoten erreicht wurde, lässt sich aus cost und pred der Ergebnispfad ermitteln.
	private static HashMap<Board,Integer> cost = new HashMap<>();
	private static HashMap<Board,Board> pred = new HashMap<>();

	// openList als Prioritätsliste.
	// Die Prioritätswerte sind die geschätzen Kosten f = g + h (s. Skript S. 2-66)
	private static IndexMinPQ<Board, Integer> openList = new IndexMinPQ<>();

	public static Deque<Board> aStar(Board startBoard) {
		if (startBoard.isSolved())
        	return new LinkedList<>();

		// start Board einfügen mit Kosten = 0
		openList.add(startBoard, startBoard.h1());
		cost.put(startBoard, 0);

		// Liste für bereits besuchte Fälle
		IndexMinPQ<Board, Integer> closedList = new IndexMinPQ<>();

		// Main A* loop
		while (!openList.isEmpty()) {
			// Das Element mit dem geringsten f-Wert wird betrachted
			Board currentBoard = openList.removeMin();

			// wenn das Board gelöst ist, wird er Pfad in eine Deque
			// gespeichert und zurückgegeben
			if (currentBoard.isSolved()) {
				Deque<Board> path = new LinkedList<>();
				while (currentBoard != startBoard) {
					path.addFirst(currentBoard);
					currentBoard = pred.get(currentBoard);
				}
				path.addFirst(startBoard);
				return path;
			}

			// Besuchte Knoten speichern
			closedList.add(currentBoard, cost.get(currentBoard));

			// Mögliche Züge werden betrachtet
			for (Board action : currentBoard.possibleActions()) {
				// neue Züge werden in die openList hinzugefügt und die Kosten aktualisiert 
				if (openList.get(action) == null && closedList.get(action) == null) {
					pred.put(action, currentBoard);
					cost.put(action, closedList.get(currentBoard) + 1);
					openList.add(action, cost.get(currentBoard) + action.h1());
				} else if (openList.get(action) != null) {
					// die Kosten für nichtbesuchte Züge werden angepasst,
					// wenn der neue Pfad besser ist
					if (openList.get(action) > cost.get(currentBoard) + action.h1()) {
						pred.put(action, currentBoard);
						cost.put(action, cost.get(currentBoard) + 1);
						openList.change(action, cost.get(currentBoard) + action.h1());
					}
				}
			}
		}

		return null; // Keine Lösung
	}
}
