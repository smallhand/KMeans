#Read result SSE from SSE.txt of 30 time runing result.
#Get the avg of 1st, 2nd... time SSE

import re

regex = r"(([0-9])(.)([0-9]*)E([0-9]+))"
re_time = r"[0-9]+(.)[0-9]*"
sse = {}
time = 0.0

for i in range(1, 30):
	cnt = 1
	f = open("KM"+str(i)+"/SSE_tri.txt", 'r')
	for line in f.readlines():
		if re.search(regex, line):
			match = re.search(regex, line)
			if not sse.has_key(cnt):
				sse.setdefault(cnt, [])
			sse[cnt].append(float(match.group(0)))
			cnt += 1
		elif re.search(r"Executing Time:", line):
			match = re.search(re_time, line)
			time += float(match.group(0))

	f.close()
print(time)
f= open("sum_tri.txt", 'w')
#comput avg of sse
for key, se in sse.items():
	avg = 0.0
	for num in se:
		avg += float(num)
	if len(se) != 0:
		avg = avg/len(se)
	else:
		avg = avg/1
	f.write("SSE"+str(key)+" : "+str(avg)+"\n")
f.write("Excuting Time:" + str(float(time/29)) + "ms")
f.close()


