#
# map_coloring.py
#
import constraint
from constraint import Problem, AllDifferentConstraint, MinConflictsSolver


STATES = [
    'Baden-Württemberg',
    'Bayern',
    'Berlin',
    'Brandenburg',
    'Bremen',
    'Hamburg',
    'Hessen',
    'Mecklenburg-Vorpommern',
    'Niedersachsen',
    'Nordrhein-Westfalen',
    'Rheinland-Pfalz',
    'Saarland',
    'Sachsen',
    'Sachsen-Anhalt',
    'Schleswig-Holstein',
    'Thüringen',
]
CLUSTERS = [
    ['Rheinland-Pfalz', 'Saarland'],
    ['Niedersachsen', 'Bremen'],
    ['Brandenburg', 'Berlin'],
    ['Baden-Württemberg', 'Hessen', 'Bayern'],
    ['Baden-Württemberg', 'Hessen', 'Rheinland-Pfalz'],
    ['Hessen', 'Bayern', 'Thüringen'],
    ['Hessen', 'Niedersachsen', 'Thüringen'],
    ['Hessen', 'Niedersachsen', 'Nordrhein-Westfalen'],
    ['Hessen', 'Nordrhein-Westfalen', 'Rheinland-Pfalz'],
    ['Thüringen', 'Niedersachsen', 'Sachsen-Anhalt'],
    ['Thüringen', 'Sachsen', 'Bayern'],
    ['Thüringen', 'Sachsen', 'Sachsen-Anhalt'],
    ['Brandenburg', 'Sachsen', 'Sachsen-Anhalt'],
    ['Niedersachsen', 'Hamburg', 'Schleswig-Holstein'],
    ['Niedersachsen', 'Mecklenburg-Vorpommern', 'Schleswig-Holstein'],
    ['Niedersachsen', 'Brandenburg', 'Mecklenburg-Vorpommern'],
    ['Niedersachsen', 'Brandenburg', 'Sachsen-Anhalt'],
]
# COLORS = ['rot', 'grün', 'blau']  # no solutions
COLORS = ['rot', 'grün', 'blau', 'gelb']  # 191808 solutions


def solve():
    problem = Problem()

    # variables
    problem.addVariables(STATES, COLORS)

    # constraints
    for cluster in CLUSTERS:
        problem.addConstraint(AllDifferentConstraint(), cluster)

    return problem.getSolutions()


def solve_min_conflicts():
    problem = Problem(MinConflictsSolver())

    # variables
    problem.addVariables(STATES, COLORS)

    # constraints
    for cluster in CLUSTERS:
        problem.addConstraint(AllDifferentConstraint(), cluster)

    return problem.getSolution()


def print_solutions():
    solutions = solve()

    if not solutions:
        print('No solutions found.')
        return

    # print raw solutions
    for solution in solutions:
        for k, v in solution.items():
            print(f'{k}: {v}')
        print()

    # print how many solutions were found
    print(f'Found {len(solutions)} solutions')


def print_solutions_min_conflicts():
    solution = solve_min_conflicts()

    if not solution:
        print('No solutions found for min conflicts.')
        return

    # print raw solution
    for k, v in solution.items():
        print(f'{k}: {v}')


def main():
    # print_solutions()
    print_solutions_min_conflicts()


if __name__ == '__main__':
    main()