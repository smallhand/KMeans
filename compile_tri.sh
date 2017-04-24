#!/bin/bash

inputfile=$1
cluster=$2
num_cluster=$3

javac -cp graph/dist/JavaPlot.jar KMeans_tri_nofile.java Cluster.java Point.java
java -cp graph/dist/JavaPlot.jar:. KMeans_tri_nofile $inputfile $cluster $num_cluster
