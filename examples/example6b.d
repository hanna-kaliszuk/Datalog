Constants: a, b
Rules:

blue(a) :- .
red(X,Y) :- blue(X), blue(Y).

Queries: red(a,b)

