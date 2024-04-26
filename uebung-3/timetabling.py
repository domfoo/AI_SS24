#
# timetabling.py
#

from constraint import Problem, AllDifferentConstraint


def solve():
    problem = Problem()

    # variables
    teachers = ['Maier', 'Huber', 'Müller', 'Schmid']
    subjects = ['Deutsch', 'Englisch', 'Mathe', 'Physik']
    rooms = [1, 2, 3, 4]

    problem.addVariables(teachers, rooms)
    problem.addVariables(subjects, rooms)

    # constraints
    problem.addConstraint(AllDifferentConstraint(), teachers)
    problem.addConstraint(AllDifferentConstraint(), subjects)

    problem.addConstraint(lambda a: a != 4, ['Maier'])
    problem.addConstraint(lambda a, b: a == b, ('Müller', 'Deutsch'))
    problem.addConstraint(lambda a, b: (a - 1) != b, ('Schmid', 'Müller'))
    problem.addConstraint(lambda a, b: (a + 1) != b, ('Schmid', 'Müller'))
    problem.addConstraint(lambda a, b: a == b, ('Huber', 'Mathe'))
    problem.addConstraint(lambda a: a == 4, ['Physik'])
    problem.addConstraint(lambda a: a != 1, ['Deutsch'])
    problem.addConstraint(lambda a: a != 1, ['Englisch'])

    return problem.getSolutions()


def sort_by_room(solution):
    return {v: [k for k, v2 in solution.items() if v2 == v] for v in set(solution.values())}


def main():
    solutions = solve()

    if not solutions:
        print('No solutions found.')
        return

    # print raw solutions
    print(*solutions, sep='\n', end='\n\n')

    # print solutions sorted by room
    print("Solutions sorted by room:")
    print(*[sort_by_room(solution) for solution in solutions], sep='\n', end='\n\n')

    # print how many solutions were found
    print(f'Found {len(solutions)} solutions')


if __name__ == '__main__':
    main()