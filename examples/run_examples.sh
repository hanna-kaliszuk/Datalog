#!/bin/bash

export CLASSPATH="../lib/antlr-4.13.2-complete.jar:../target/classes/"

pushd `dirname $0` >/dev/null
trap 'popd >/dev/null' EXIT

ITERATIONS=100

for ((i=1; i<=ITERATIONS; i++)); do
    echo "===== RUN $i ====="

    for test_file in *.d; do
        name=`basename "$test_file" .d`
        echo "  $name"
        cat "$name.d" | java $JAVA_OPTS cp2025.engine.Main >res.$$ 2>/dev/null

        if diff <(grep -e "^Query" res.$$ | sort) <(sort "$name.res") >/dev/null; then
            echo "    OK"
        else
            echo "    FAIL on iteration $i"
            echo "    Test: $name"
            echo "    Keeping res.$$ for inspection."
            exit 1
        fi

        rm res.$$
    done
done

echo "======================================="
echo "ALL $ITERATIONS RUNS PASSED SUCCESSFULLY"
