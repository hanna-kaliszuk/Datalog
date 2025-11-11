Constants: a, b
Rules:

blue(b) :- .
red(X) :- blue(X), blue(b).

Queries: red(b), blue(b)

