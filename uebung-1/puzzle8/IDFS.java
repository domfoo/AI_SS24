package puzzle8;

import java.util.Deque;
import java.util.LinkedList;

/**
 * Klasse IDFS für iterative deepening depth-first search
 * @author domfoo
 */
public class IDFS {

	private static Deque<Board> dfs(Board curBoard, Deque<Board> path, int limit) {
		if (curBoard.isSolved()) { // Base case: board is solved
            return path;
		}
        else if (limit == 0) { // max limit reached
			return null;
		}
        else {
            for (Board child : curBoard.possibleActions()) {
                if (path.contains(child)) continue;

                path.add(child);
                Deque<Board> result = dfs(child, path, limit - 1);

                if (result != null) return result;

                path.removeLast();
            }
        }

        return null;
	}
	
	private static Deque<Board> idfs(Board curBoard, Deque<Board> path) {
		for (int limit = 5; limit < Integer.MAX_VALUE; limit++) {
			Deque<Board> result = dfs(curBoard,path,limit);
			if (result != null)
				return result;
		}
		return null;
	}
	
	public static Deque<Board> idfs(Board curBoard) {
		Deque<Board> path = new LinkedList<>();
		path.addLast(curBoard);
		Deque<Board> res =  idfs(curBoard, path); 
		return res;
	}
}
