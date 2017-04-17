#!/bin/bash

for (( i=1 ; i<=30 ; i=i+1 ))
do
	python randomInput.py input3.data 0 1000000 500000

	java KMeans input3.data cluster.data
	mv SSE.txt KM${i}/
	mv file.txt KM${i}/

	java KMeans_tri input3.data cluster.data
	mv SSE_tri.txt KM${i}/
	mv file_tri.txt KM${i}/
done
