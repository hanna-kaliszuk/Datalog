Constants: a, b
Rules:

yellow(X,Y,Z,U) :- .
green(X,Y,Z) :- yellow(X,Y,Z,a), yellow(X,Y,Z,b).
blue(X,Y) :- green(X,Y,a), green(X,Y,b).
red(X) :- blue(X, a), blue(X, b).

Queries: red(a), red(b)

