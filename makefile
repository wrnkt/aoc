all:

test-class:
	./gradlew test --tests "wrnkt.aoc.year.y$(year).$(day)Test"

.PHONY: test-class
