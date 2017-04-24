#!/bin/bash

javac -cp graph/dist/JavaPlot.jar KMeans_nofile.java Cluster.java Point.java
java -cp graph/dist/JavaPlot.jar:. KMeans_nofile input2.data cluster.data
