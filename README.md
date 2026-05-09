# Parallel Datalog Engine

A multithreaded query engine for a simplified dialect of **Datalog**, built in Java.

> Project for **Concurrent Programming** (Programowanie Współbieżne), winter semester 2025/26, University of Warsaw.

---

## What is Datalog?

Datalog is a declarative logic language used in database and program analysis applications. A Datalog program defines:

- A finite set of **constants**,
- A set of **rules** of the form `head :- premise₁, …, premiseₖ.` — *if all premises are derivable, so is the head* (under every variable substitution),
- A set of **queries** — ground atoms (no variables) whose derivability is to be determined.

A statement is **derivable** if there exists a finite derivation tree rooted at it, where every leaf follows from a rule with no premises, and every internal node follows from its children via some rule and substitution.

---

## Project Structure

```
src/main/java/cp2025/
├── datalog/                  # Auto-generated parser (BNFC)
└── engine/
    ├── Datalog.java          # Core structures: atoms, rules, programs
    ├── Parser.java           # Parser wrapper
    ├── AbstractDeriver.java  # Deriver interface
    ├── AbstractOracle.java   # Oracle interface
    ├── NullOracle.java       # Trivial oracle (no calculable predicates)
    ├── SimpleDeriver.java    # Provided single-threaded reference implementation
    ├── ParallelDeriver.java  # ← implemented here
    ├── FunctionGenerator.java # Iterates over all variable substitutions
    └── Unifier.java          # Unification helpers
examples/                     # Sample Datalog programs with expected outputs
```

---

## The Oracle Extension

The engine supports an **oracle** — an external source of truth for designated *calculable* predicates. For a statement with a calculable predicate, instead of applying Datalog rules the engine calls `oracle.calculate(statement)` and treats the result as authoritative. This allows encoding arbitrary external relations (e.g. graph edges, database lookups) without listing them as Datalog facts.

```java
public interface AbstractOracle {
    boolean isCalculatable(Predicate predicate);
    boolean calculate(Atom statement) throws InterruptedException;
}
```

`calculate()` may block for an extended time but will return promptly on `Thread.interrupt()`.

---

## Algorithm

The engine uses **top-down recursive derivation with cycle detection**, matching the single-threaded `SimpleDeriver` reference implementation:

1. To derive a statement `s`, try every rule whose head unifies with `s` and recursively derive the body under that substitution.
2. Maintain a per-thread `inProgressStatements` set. If `s` is already in progress (cycle detected), return `false` immediately — *not* unconditional non-derivability, but non-derivability *conditional on all in-progress statements being non-derivable*.
3. When `deriveStatement(s)` returns `false`, it also returns the set `{s} ∪ (all false-sets from recursive calls)`. Once the root call returns `false`, every statement in that set is conclusively non-derivable.

---

## Parallel Implementation

`ParallelDeriver` extends the above algorithm across multiple threads while maintaining correctness. Key design decisions:

### Shared knowledge base
A single shared map of derivability results (derivable / non-derivable / unknown) is maintained across all worker threads for one `derive()` call. Before starting work on a statement, a thread checks the map; if the result is already known, it uses it immediately.

### Cross-thread notification
If thread A is in the process of deriving statement `s` (i.e. `s` is in A's `inProgressStatements`) and thread B independently determines the derivability of `s`, thread A is **notified immediately** and uses that result as if it had computed it itself — including propagating it to determine the derivability of everything that depended on `s` in A's call stack.

If A is inside `oracle.calculate()` at the time, it is interrupted via `Thread.interrupt()`.

### Granularity
Parallelism operates at the **query level**: each worker thread handles one top-level query at a time. A single query is never split across threads. If there is only one query, no parallel computation takes place.

### Lifecycle
Worker threads are created at the start of `derive()` and are guaranteed to terminate before `derive()` returns — no thread leaks. If the calling thread is interrupted, all workers are stopped and `InterruptedException` is thrown promptly.

### Interruption handling
Worker threads distinguish between two interrupt causes:
- **Statement resolved externally** — stop deriving the current statement, but continue processing the current query using the received result.
- **Global shutdown** (calling thread interrupted) — stop all work immediately and exit.

---

## Input Format

```
Constants: a, b, c

Rules:
    arc(a, b) :- .
    reach(X, Y) :- arc(X, Y).
    reach(X, Z) :- reach(X, Y), reach(Y, Z).

Queries: reach(a, c)
```

- Constant names start with a lowercase letter; variable names start with an uppercase letter.
- Rules with no premises use `:-` with an empty body (e.g. `fact(a) :- .`).
- Queries are ground atoms (no variables).

---

## Building and Running

The project uses **Maven**. A `Makefile` is provided as a convenience wrapper.

```bash
# Build and run all tests
make test

# Run the engine on a specific file
make run FILE=examples/some_program.dl
```

Or use Maven directly:

```bash
./mvnw test
./mvnw compile exec:java -Dexec.mainClass=cp2025.engine.Main
```

> Requires Java 17+. Tested on the `students` server environment.

---

## Usage

```java
int numThreads = 4;
AbstractDeriver deriver = new ParallelDeriver(numThreads);

Datalog.Program program = Parser.parse("path/to/program.dl");
AbstractOracle oracle = new NullOracle();

Map<Atom, Boolean> results = deriver.derive(program, oracle);
results.forEach((query, derivable) ->
    System.out.println(query + " -> " + derivable));
```
