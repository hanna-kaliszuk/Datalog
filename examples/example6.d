Constants: a, b
Rules:

blue(a) :-  .
red(X,Y) :- blue(X), blue(Y).

Queries: red(a,b), red(a,a), red(b,b), red(b,a)

