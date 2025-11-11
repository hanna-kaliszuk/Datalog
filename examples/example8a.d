Constants: a, b
Rules:

blue(b) :- .
green(b) :- .
yellow(b) :- .
red(U,a,a) :- blue(X), green(Y), yellow(Z).

Queries: red(b,a,a)

