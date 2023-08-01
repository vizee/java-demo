NATIVE_ARGS=

all: jvm native iprof pgo

jvm:
	javac Streams.java
	java Streams 100000 200

native:
	native-image $(NATIVE_ARGS) Streams
	./streams 100000 200

iprof:
	native-image $(NATIVE_ARGS) --pgo-instrument Streams
	./streams 100000 200

pgo:
	native-image $(NATIVE_ARGS) --pgo=default.iprof Streams
	./streams 100000 200

