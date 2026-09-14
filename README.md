# Parallel Datalog Engine

A multithreaded query engine for a simplified version of the Datalog language, implemented in Java.

> Project for **Programowanie Współbieżne** (Concurrent Programming), winter semester 2025/26, University of Warsaw.

## What is Datalog?

Datalog is a declarative logic programming language used for expressing rules and querying relations between constants.

A program consists of:

- a finite set of constants,
- rules defining how statements can be derived,
- queries asking whether particular statements are derivable.

For example:

```text
Constants: a, b, c

Rules:
    edge(a, b) :- .
    edge(b, c) :- .
    path(X, Y) :- edge(X, Y).
    path(X, Z) :- path(X, Y), edge(Y, Z).

Queries: path(a, c)
```

The query is derivable because `a → b → c` forms a path according to the given rules.

The project implements a parallel engine for determining the derivability of queries.

## Features

- Top-down recursive Datalog evaluation
- Rule unification and variable substitution
- Recursive derivation with cycle detection
- Multiple queries evaluated concurrently
- Shared derivability cache between worker threads
- Cross-thread notification when a statement is resolved
- Cooperative interruption of long-running computations
- Support for external computation through an oracle
- Proper worker-thread lifecycle management
- Maven-based build and testing

## Technologies

### Language and Build Tools

- Java
- Maven
- Maven Wrapper
- Make

### Concurrency

- `Thread`
- Synchronization and shared state
- Thread interruption
- Worker threads
- Concurrent query evaluation

### Algorithms

- Top-down depth-first derivation
- Unification
- Recursive backtracking
- Cycle detection
- Memoization of derivability results

## Datalog Evaluation

The engine uses a top-down evaluation strategy.

To determine whether a statement is derivable, the engine:

1. Finds rules whose heads can be unified with the statement.
2. Generates the required variable substitutions.
3. Recursively derives every premise in the rule body.
4. Returns `true` as soon as one valid derivation is found.
5. Returns `false` after all possible derivations have been exhausted.

Conceptually:

```text
Query
  │
  ▼
Find matching rules
  │
  ▼
Unify rule head with query
  │
  ▼
Derive rule premises
  │
  ├── success ──► query is derivable
  │
  └── failure ──► try another rule/substitution
```

## Cycle Detection

Datalog rules may be recursive.

For example:

```text
path(X, Y) :- edge(X, Y).
path(X, Z) :- path(X, Y), edge(Y, Z).
```

A recursive derivation can also contain cycles that do not lead to a valid derivation.

To prevent infinite recursion, each worker maintains a set of statements currently being derived.

If a statement already present in this set is encountered again, the current derivation branch is stopped.

Importantly, encountering a cycle does **not** by itself prove that the statement is globally non-derivable. Other derivation paths may still succeed.

## Parallel Evaluation

The main extension over the provided single-threaded implementation is parallel query evaluation.

Parallelism is applied at the **query level**:

```text
                 ┌── Query 1 ──► Worker 1
                 │
Program ─────────┼── Query 2 ──► Worker 2
                 │
                 ├── Query 3 ──► Worker 3
                 │
                 └── Query 4 ──► Worker 4
```

A single query is never split between multiple workers.

Therefore, if a program contains only one query, the engine does not parallelize its internal derivation.

## Shared Derivability Cache

All workers operating during one `derive()` call share a common database of known results.

A statement can have one of three states:

```text
UNKNOWN
  │
  ├──► DERIVABLE
  │
  └──► NON-DERIVABLE
```

Before starting an expensive derivation, a worker checks whether another worker has already determined the result.

This avoids repeating the same computation in different threads.

For example:

```text
Worker 1                    Worker 2

derive(s1)                  derive(s2)
    │                           │
    └── derive(s)               └── derive(s)
             │
             ▼
       compute result
             │
             ▼
       shared knowledge
             │
             ├──────────────► Worker 1 uses result
             │
             └──────────────► Worker 2 uses result
```

## Cross-Thread Notifications

Workers can also depend on statements currently being derived by another worker.

If one worker determines the result of such a statement, the other worker is notified immediately.

The waiting derivation can then:

1. stop the unnecessary computation,
2. use the newly known result,
3. continue deriving the surrounding statement.

This is particularly important when the other worker is performing a potentially long-running oracle computation.

## Interruption

The engine distinguishes between two types of interruption.

### Statement-level interruption

A worker may stop deriving a statement because another worker has already determined its result.

Only the unnecessary derivation is cancelled; the worker can continue processing the current query.

### Global interruption

If the thread executing `derive()` is interrupted, the entire computation is cancelled.

All worker threads are stopped and the method terminates by throwing `InterruptedException`.

Worker threads are always joined before `derive()` returns, preventing leaked background threads.

## Oracle Support

The engine supports an external `AbstractOracle` that can provide answers for selected predicates.

```java
public interface AbstractOracle {
    boolean isCalculatable(Predicate predicate);
    boolean calculate(Atom statement) throws InterruptedException;
}
```

For a predicate handled by the oracle, `calculate()` provides the authoritative answer instead of applying Datalog rules.

This allows external computations or data sources to be integrated into the derivation process.

The oracle may perform expensive computations, which makes correct thread interruption especially important.

## Project Structure

```text
.
├── .mvn/
│   └── wrapper/                 # Maven Wrapper
├── examples/                    # Example Datalog programs
├── lib/                         # Provided dependencies
├── src/
│   ├── main/
│   │   └── java/
│   │       └── cp2025/
│   │           ├── datalog/     # Generated parser
│   │           └── engine/
│   │               ├── Datalog.java
│   │               ├── Parser.java
│   │               ├── AbstractDeriver.java
│   │               ├── AbstractOracle.java
│   │               ├── NullOracle.java
│   │               ├── SimpleDeriver.java
│   │               ├── ParallelDeriver.java
│   │               ├── FunctionGenerator.java
│   │               └── Unifier.java
│   └── test/
│       └── java/                # Tests
├── .vscode/
│   └── java-formatter.xml
├── Datalog.cf                   # Parser grammar
├── Makefile
├── README.md
├── bnfc                          # Parser generator
├── mvnw
├── mvnw.cmd
└── pom.xml
```

### Implementation

The most important classes are:

- `ParallelDeriver.java` — multithreaded query evaluation
- `SimpleDeriver.java` — provided single-threaded reference implementation
- `Datalog.java` — core representations of programs, rules, atoms and predicates
- `Parser.java` — parser interface
- `Unifier.java` — rule/query unification
- `FunctionGenerator.java` — generation of variable substitutions
- `AbstractOracle.java` — interface for external computations

The parser and most supporting infrastructure were provided as part of the assignment.

## Building

The project uses Maven, with a Makefile provided as a convenience wrapper.

Run the test suite:

```bash
make test
```

Or use the Maven Wrapper directly:

```bash
./mvnw test
```

Compile the project:

```bash
./mvnw compile
```

## Running

The project includes a `Main` class demonstrating the engine.

Using Maven:

```bash
./mvnw compile exec:java \
    -Dexec.mainClass=cp2025.engine.Main
```

The Makefile can also be used for common operations.

## Useful Commands

### Run tests

```bash
make test
```

### Compile with Maven

```bash
./mvnw compile
```

### Run the example program

```bash
./mvnw compile exec:java \
    -Dexec.mainClass=cp2025.engine.Main
```

### Generate the parser

The parser can be regenerated from `Datalog.cf` using:

```bash
make datalog
```

## Notes

- Parallelism is applied only between top-level queries.
- A single query is always evaluated by one worker.
- Workers share derivability results within one `derive()` call.
- Recursive derivations use cycle detection to avoid infinite recursion.
- A detected cycle does not automatically imply global non-derivability.
- Oracle computations may be long-running and must respond to thread interruption.
- All worker threads are terminated before `derive()` returns.
- The repository contains both the implemented `ParallelDeriver` and the provided single-threaded reference implementation.

## Academic Context

This project was developed as a course assignment for **Concurrent Programming** at the University of Warsaw during the Winter Semester 2025/26.

The course provided the Datalog parser, single-threaded reference implementation and supporting infrastructure. The main task was to implement the multithreaded query evaluation in `ParallelDeriver`. 
