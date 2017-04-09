#!/bin/bash

javac -cp graph/dist/JavaPlot.jar KMeans_tri.java Cluster.java Point.java
java -cp graph/dist/JavaPlot.jar:. KMeans_tri input2.data cluster.data
