# ----------
# User Instructions:
# 
# Create a function compute_value() which returns
# a grid of values. Value is defined as the minimum
# number of moves required to get from a cell to the
# goal. 
#
# If it is impossible to reach the goal from a cell
# you should assign that cell a value of 99.

# ----------
def show(m):
    for i in range(len(m)):
        print m[i]
    print '\n'

grid = [[0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 1, 0, 0, 0, 0],
        [0, 0, 0, 0, 1, 0]]

init = [0, 0]
goal = [len(grid)-1, len(grid[0])-1]

delta = [[-1, 0 ], # go up
         [ 0, -1], # go left
         [ 1, 0 ], # go down
         [ 0, 1 ]] # go right

delta_name = ['^', '<', 'v', '>']

cost_step = 1 # the cost associated with moving from a cell to an adjacent one.

# ----------------------------------------
# insert code below
# ----------------------------------------

def compute_value():
    cols = len(grid[0])
    rows = len(grid)
    value = [[99 for j in range(cols)] for i in range(rows)]
    change = True
    while change:
        change = False
        for x in range(rows):
            for y in range(cols):
                print '(%d, %d):' %(x, y)
                if goal[0] == x and goal[1] == y:
                    print 'goal found'
                    if value[x][y] > 0:
                        value[x][y] = 0
                        change = True
                elif grid[x][y] == 0:
                    for a in range(len(delta)):
                        x2 = x + delta[a][0]
                        y2 = y + delta[a][1]
                        if x2 >= 0 and x2 < rows and y2 >= 0 and y2 < cols:
                            if grid[x2][y2] == 0:
                                v2 = value[x2][y2] + cost_step
                                if v2 < value[x][y]:
                                    change = True
                                    value[x][y] = v2
                    show(value)
    return value 
    #make sure your function returns a grid of values as
    #demonstrated in the previous video.

m = compute_value()


