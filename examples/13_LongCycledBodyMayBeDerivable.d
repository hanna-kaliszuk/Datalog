Constants: a, b, c, d

Rules:
    p(a) :- p(b).
    p(b) :- p(c).
    p(c) :- p(a).
    p(a) :- p(d).
    p(d) :- .

Queries: p(a), p(b), p(c)