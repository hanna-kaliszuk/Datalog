Constants: a, b, c

Rules:
    p(a) :- p(b).
    p(b) :- p(a).
    p(a) :- p(c).
    p(c) :- .

Queries: p(a), p(b)