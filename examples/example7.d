Constants: a, b
Rules:

blue(b) :- .
green(b) :- .
yellow(b) :- .
red(X,Y,Z) :- blue(X), green(Y), yellow(Z).

Queries: red(a,a,a)

