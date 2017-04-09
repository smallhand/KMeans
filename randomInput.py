# -*- coding: utf-8 -*-
import sys
import random

''' ----complie format----
outputfile min max 

'''
if len(sys.argv) < 4: #if <1 : no arguments
    print('enter the output, min, max axis range')
    sys.exit()

fileName = sys.argv[1]
m = float(sys.argv[2])
M = float(sys.argv[3])

with open(fileName,'w') as fw:
    for i in range(8):
        x = round(random.uniform(m,M),2)
        y = round(random.uniform(m,M),2)
        fw.write(str(x) + ' ' + str(y) +'\n')

