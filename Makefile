DATALOG=src/main/java/cp2025/datalog
ANTLER=java -cp .:lib/antlr-4.13.2-complete.jar org.antlr.v4.Tool
ANTLER_FLAGS=-lib ${DATALOG} -package cp2025.datalog

all: compile

.PHONY: datalog compile test examples clean distclean vclean

datalog:
	./bnfc --antlr --java -o src/main/java/ -p cp2025 Datalog.cf
	${ANTLER} ${ANTLER_FLAGS} ${DATALOG}/DatalogLexer.g4
	${ANTLER} ${ANTLER_FLAGS} ${DATALOG}/DatalogParser.g4

compile:
	./mvnw compile assembly:single -f pom.xml || \
		(echo "Sometimes the first build fails with 'release version 21 not supported', so compile again:";\
		 ./mvnw compile assembly:single -f pom.xml)

test:
	./mvnw test -f pom.xml

examples: compile
	./examples/run_examples.sh

clean:
	rm -rf target/ ${DATALOG}/*.class ${DATALOG}/Absyn/*.class

distclean: vclean

vclean:
	rm -rf target/ ${DATALOG}
