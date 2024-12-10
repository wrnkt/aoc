# Notes

example of loading new instances of class with a Map:

```java
Map<OutputType, Supplier<DayOutput> outputTypeToOutputMap = Map.of(
    DayOutput.FILE, () -> new FileOutput(),
);

outputTypeToOutputMap.get(DayOutput.FILE).get()
```
