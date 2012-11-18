# ----------
# User Instructions:
# 
# Define a function, search() that takes no input
# and returns a list
# in the form of [optimal path length, x, y]. For
# the grid shown below, your function should output
# [11, 4, 5].
#
# If there is no valid path from the start point
# to the goal, your function should return the string
# 'fail'
# ----------

# Grid format:
#   0 = Navigable space
#   1 = Occupied space

grid = [[0, 0, 1, 0, 0, 0],
        [0, 0, 1, 0, 0, 0],
        [0, 0, 0, 0, 1, 0],
        [0, 0, 1, 1, 1, 0],
        [0, 0, 0, 0, 1, 0]]

init = [0, 0]
goal = [len(grid)-1, len(grid[0])-1] # Make sure that the goal definition stays in the function.

delta = [[-1, 0 ], # go up
        [ 0, -1], # go left
        [ 1, 0 ], # go down
        [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>']

cost = 1

def search():
    g = 0
    untested = []
    expanded = []
    start = [g, init]
    untested.append(start)
    # ----------------------------------------
    # insert code here and make sure it returns the appropriate result
    # ----------------------------------------
    while len(untested) > 0:
        untested.sort()
    	#print "--------------"
    	#print "nodes to test:", untested
    	#print "expanded nodes till now:", len(expanded)
    	test = untested[0]
        if success(test, goal):
        	#print "Goal reaached"
        	return [test[0], test[1][0], test[1][1]]
        else:
        	untested.remove(test)
        	expanded.append(test)
        	nodes = expand(test)
        	g = nodes[0]
        	#print "current cost:", nodes[0]
        	for node in nodes[1]:
				if valid(node) and yet(node, untested, expanded):
					untested.append(node)
    return 'fail'

def yet(n, un, exp):
	b1 = True
	for p in un:
		if p[1] == n[1]:
			b1 = False
			break
	b2 = True
	for q in exp:
		if q[1] == n[1]:
			b2 = False
			break
	return b1 and b2

def expand(node):
	#print "Expanding node", node, ":"
	q = []
	a = node[0] + cost
	for move in delta:
		p = [node[1][0] + move[0], node[1][1] + move[1]]
		n = [a, p]
		q.append(n)
	#print "Possible children for node", node,":", q
	return [a, q]

def valid(node):
    b1 = node[1][0]>= 0
    b2 = node[1][0] < len(grid)
    b3 = node[1][1] >= 0
    b4 = node[1][1] < len(grid[0])
    if b1 and b2 and b3 and b4:
    	b5 = (grid[node[1][0]][node[1][1]] == 0)
    	if b5:
    		#print "Testing node", node, ": valid"
    		return True
    	else:
    		#print "Testing node", node, ": invalid"
    		return False
    else:
    	return False

def success(test, goal):
    b1 = test[1][0] == goal[0]
    b2 = test[1][1] == goal[1]
    return b1 and b2

print search()
