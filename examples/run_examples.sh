#!/bin/bash

# export CLASSPATH="../lib/antlr-4.13.2-complete.jar:../target/zadanie-java-1.0-SNAPSHOT-jar-with-dependencies.jar"
# export CLASSPATH="../lib/antlr-4.13.2-complete.jar:../target/zadanie-java-1.0-SNAPSHOT.jar"
export CLASSPATH="../lib/antlr-4.13.2-complete.jar:../target/classes/"

# export JAVA_OPTS="-javaagent:$HOME/.m2/repository/org/jacoco/org.jacoco.agent/0.8.12/org.jacoco.agent-0.8.12-runtime.jar=destfile=jacoco-it.exec,append=true"

# Go to the script directory.
pushd `dirname $0`
# Ensure we return, regardless of how we exit.
trap 'popd' EXIT

for test_file in *.d; do
	name=`basename "$test_file" .d`
	echo "$name"
	cat "$name.d" | java $JAVA_OPTS cp2025.engine.Main >res.$$ 2>/dev/null
	if diff <(grep -e "^Query" res.$$ | sort) <(sort "$name.res"); then
		echo "  OK"
	fi
	rm res.$$
done

