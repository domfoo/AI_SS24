from constraint import Problem

rectangles = {"1": (6, 4), "2": (5, 2), "3": (2, 2), "4": (3, 2), "5": (1, 8), "6": (1, 4)}
board = (7, 8)

# true if no overlap
def no_overlap(x1, y1, w1, h1, x2, y2, w2, h2):
    return x1 + w1 <= x2 or x2 + w2 <= x1 or y1 + h1 <= y2 or y2 + h2 <= y1


def solve():
    problem = Problem()

    positions = {}
    for rectangle, (width, height) in rectangles.items():
        pos_list = []

        # Für horizontale Platzierung
        for x in range(board[0] - width + 1):
            for y in range(board[1] - height + 1):
                pos_list.append((x, y, width, height))

        # Für vertikale Platzierung (gedreht)
        for x in range(board[0] - height + 1):
            for y in range(board[1] - width + 1):
                pos_list.append((x, y, height, width))

        positions[rectangle] = pos_list
        problem.addVariable(rectangle, pos_list)

    # Constraints hinzufügen, dass sich keine Rechtecke überschneiden
    keys = list(positions.keys())
    for i in range(len(keys)):
        for j in range(i + 1, len(keys)):
            problem.addConstraint(
                lambda pos1, pos2: no_overlap(pos1[0], pos1[1], pos1[2], pos1[3], pos2[0], pos2[1], pos2[2], pos2[3]),
                (keys[i], keys[j])
            )
            print()

    return problem.getSolutions()


def main():
    solutions = solve()

    print("Number of solutions:", len(solutions))
    print(solutions[0])


if __name__ == "__main__":
    main()