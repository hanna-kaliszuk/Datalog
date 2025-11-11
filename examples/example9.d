Constants: a, b
Rules:

blue(X) :- .
green(b,b) :- .
yellow(b,b,b,b) :- .
red(a,a,a) :- blue(X), green(Y,Z), yellow(X,Y,Z,U).

Queries: red(a,a,a)

