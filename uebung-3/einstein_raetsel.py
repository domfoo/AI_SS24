# Einstein-Rätsel.
# O. Bittel; 18.4.2023
#
# Modellierung ist einfacher als bei
# https://github.com/python-constraint/python-constraint/blob/master/examples/einstein/einstein.py
#

# In a street there are five houses, painted five different colours.
# In each house lives a person of different nationality
# hese five homeowners each drink a different kind of beverage, smoke
# different brand of cigar and keep a different pet.

# The Brit lives in a red house.
# The Swede keeps dogs as pets.
# The Dane drinks tea.
# The Green house is on the left of the White house.
# The owner of the Green house drinks coffee.
# The person who smokes Pall Mall rears birds.
# The owner of the Yellow house smokes Dunhill.
# The man living in the centre house drinks milk.
# The Norwegian lives in the first house.
# The man who smokes Blends lives next to the one who keeps cats.
# The man who keeps horses lives next to the man who smokes Dunhill.
# The man who smokes Blue Master drinks beer.
# The German smokes Prince.
# The Norwegian lives next to the blue house.
# The man who smokes Blends has a neighbour who drinks water.

import constraint

problem = constraint.Problem()

# Werte:
house = [1, 2, 3, 4, 5]

# Variablen:
nationality = ["Brite", "Schwede", "Daene", "Norweger", "Deutscher"]
drink = ["Tee", "Kaffee", "Bier", "Milch", "Wasser"]
color = ["rot", "gruen", "gelb", "blau", "weiss"]
animal = ["Hund", "Vogel", "Katze", "Pferd", "Fisch"]
cigarette = ["Pall Mall", "Dunhill", "Marlboro", "Winfield", "Rothmann"]

problem.addVariables(nationality , house)
problem.addVariables(drink, house)
problem.addVariables(color, house)
problem.addVariables(animal, house)
problem.addVariables(cigarette, house)


# Constraints:
problem.addConstraint(constraint.AllDifferentConstraint(), nationality)
problem.addConstraint(constraint.AllDifferentConstraint(), drink)
problem.addConstraint(constraint.AllDifferentConstraint(), color)
problem.addConstraint(constraint.AllDifferentConstraint(), animal)
problem.addConstraint(constraint.AllDifferentConstraint(), cigarette)

problem.addConstraint(lambda a, b: a == b, ("Brite", "rot"))
problem.addConstraint(lambda a, b: a == b, ("Schwede", "Hund"))
problem.addConstraint(lambda a, b: a == b, ("Daene", "Tee"))
problem.addConstraint(lambda a, b: (a + 1) == b, ("gruen", "weiss"))
problem.addConstraint(lambda a, b: a == b, ("gruen", "Kaffee"))
problem.addConstraint(lambda a, b: a == b, ("Pall Mall", "Vogel"))
problem.addConstraint(lambda a: a == 3, ["Milch"])
problem.addConstraint(lambda a, b: a == b, ("gelb", "Dunhill"))
problem.addConstraint(lambda a: a == 1, ["Norweger"])
problem.addConstraint(lambda a, b: ((a - 1) == b) or ((a + 1) == b), ("Marlboro", "Katze"))
problem.addConstraint(lambda a, b: ((a - 1) == b) or ((a + 1) == b), ("Pferd", "Dunhill"))
problem.addConstraint(lambda a, b: a == b, ("Winfield", "Bier"))
problem.addConstraint(lambda a, b: ((a - 1) == b) or ((a + 1) == b), ("Norweger", "blau"))
problem.addConstraint(lambda a, b: a == b, ("Deutscher", "Rothmann"))
problem.addConstraint(lambda a, b: ((a - 1) == b) or ((a + 1) == b), ("Marlboro", "Wasser"))

# Lösungssuche:
solutions = problem.getSolutions()

# Ausgaben:
print("Anzahl Lösungen: {}".format(len(solutions)))

print("\nLösung für jede Variable:")
print(solutions[0])

print("\nLösung nach Häuser geordnet:")
sol = {}
for k,v in solutions[0].items():
    if v in sol:
        sol[v].append(k)
    else:
        sol[v] = [k]

print(sol)
