## A. Requirment
    Before compiling, you need to install gnuplot, java and python3 at any operating system.
	
## B. compile and execute
#### the parameter of shell file are `inputdata`, `centroid`, `the numbers of cluster (k value)`
	$ chmod +x compile_tri.sh
	$ ./compile_tri.sh input.data cluster.data 8
	
## C. input and initial centroid data
#### you can produce a new input data or centroid data through 'randomInput.py'
	the parameters of randomInput are "filename, min coordiante, max coordinate, the number of input data"

	eg.
		$ python3 randomInput.py input.data 0 100000 50000
		$ python3 randomInput.py cluster.data 0 100000 8 
