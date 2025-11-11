Constants: a, b, c, d, e

Rules:
    arc(a, b) :- .
    arc(b, c) :- .
    arc(d, e) :- .
    arc(e, d) :- .
    reach(X, Y) :- arc(X, Y).
    reach(X, Y) :- arc(X, Z), reach(Z, Y).

Queries: reach(a,c), reach(c,a), reach(a,d), reach(e,b), reach(d,e), reach(e,d)