#!/bin/bash

javac -cp graph/dist/JavaPlot.jar KMeans.java Cluster.java Point.java
java -cp graph/dist/JavaPlot.jar:. KMeans input2.data cluster.data
