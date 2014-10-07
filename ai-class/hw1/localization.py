sensor_right = 0.7
sensor_wrong = 1 - sensor_right
p_move = 0.8;
p_stay = 1 - p_move

colors = [['r', 'g', 'g', 'r', 'r'],
          ['r', 'r', 'g', 'r', 'r'],
          ['r', 'r', 'g', 'g', 'r'],
          ['r', 'r', 'r', 'r', 'r']]

measurements_1 = ['g', 'g', 'g', 'g', 'g']
moves_1 = [[0, 0], [0, 1], [1, 0], [1, 0], [0, 1]]

measurements_2 = ['r', 'r', 'r', 'r', 'g']
moves_2 = [[0, 0], [1, 0], [1, 0], [0, 1], [0, 1]]

def sense(p, colors, measurement):
    aux = [[0.0 for row in range(len(p[0]))] for col in range(len(p))]
    s = 0.
    for i in range(len(p)):
        for j in range(len(p[i])):
            hit = (measurement == colors[i][j])
            aux[i][j] = p[i][j] * (hit * sensor_right + (1 - hit) * sensor_wrong)
            s += aux[i][j]
    for i in range(len(aux)):
        for j in range(len(p[i])):
            aux[i][j] /= s
    return aux

def move(p, motion):
    aux = [[0.0 for row in range(len(p[0]))] for col in range(len(p))]
    for i in range(len(p)):
        for j in range(len(p[i])):
            aux[i][j] = (p_move * p[(i - motion[0]) % len(p)] \
                [(j - motion[1]) % len(p[i])]) + (p_stay * p[i][j])
    return aux

def show(p):
    for i in range(len(p)):
        print [float('{:1.3}'.format(x)) for x in p[i]]

#main routine:
if len(measurements_2) != len(moves_2):
    raise ValueError, "error in measurement/motion size."

pInit = 1./(float(len(colors)))/float(len(colors[0]))
p = [[pInit for row in range(len(colors[0]))] for col in range(len(colors))]

for k in range(len(measurements_1)):
    print 'Move ', k + 1, ':'
    p = move(p, moves_1[k])
    show(p)
    print
    print 'Sense ', k + 1, ':'
    p = sense(p, colors, measurements_1[k])
    show(p)
    print