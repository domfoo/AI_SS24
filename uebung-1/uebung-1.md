# Übung 1

## Aufgabe 1

a) Welche Parität hat der Zustand S in Abb. 1?
	-> Zahlenfolge: 7, 2, 4, 5, 0, 6, 8, 3, 1
	-> Falsche Zahlenpaare: (1, 7), (1, 2), (1, 4), (1, 5), (1, 6), (1, 8), (1, 3), (2, 7), (3, 7), (3, 4), (3, 5), (3, 6), (3, 8), (4, 7), (5, 7), (6, 7)
	-> Parität: 16

## Aufgabe 2

a) Wieso ist h1 eine monotone Heuristik?
	-> Weil die Wer
b) Wieso ist h2 eine monotone Heuristik?
	-> also der Abstand zwischen zwei Zuständen n und n' ist nicht größer als der über einen Umweg. Also ist wird der Abstand durch die Schätzung immer kleiner oder gleich bleiben
c) Wieso ist h1(n) <= h2(n)? Welche Heuristik ist also besser?
	-> h1(n) <= h2(n) gilt, weil
	-> h1 ist besser, da ihr Wert 8 näher an der 1 ist als die 18 von h2. Ein Wert nahe der 1 ist ideal. h1 ist eine optimistische Schätzung, h2 eine pessimistische Schätzung.

## Aufgabe 3

d) Sind ihre Zugfolgen optimal? Wenn ja, warum?
	-> IDS: ja, wenn gleiche positive Kosten für jede Aktion
	-> A*: ja, jeder expandierte Knoten ist optimal
e) Speicherprobleme, zu viele Möglichkeiten