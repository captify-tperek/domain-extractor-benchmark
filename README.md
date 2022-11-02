to run the benchmark:
```
cd domain-extractor-benchmark
sbt
jmh:run -i 5 -wi 1 -f1 -t1
```

results on my m1 macbook pro (mileage may vary):
```
[info] DEBenchmark.runNewExtractor  thrpt    5  504278.988 ±  936.624  ops/s
[info] DEBenchmark.runOldExtractor  thrpt    5   56237.333 ± 1325.717  ops/s
```
