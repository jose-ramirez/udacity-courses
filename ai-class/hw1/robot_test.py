#testing the robot. :)
from robot import Robot

world_as_we_know_it = [['r', 'g', 'g', 'r', 'r'],
                       ['r', 'r', 'g', 'r', 'r'],
                       ['r', 'r', 'g', 'g', 'r'],
                       ['r', 'r', 'r', 'r', 'r']]



moves_dict = {'sit': [0, 0],
              'up': [-1, 0],
              'down': [1, 0],
              'right': [0, 1],
              'left': [0, -1]}

measurements_1 = ['g', 'g', 'g', 'g', 'g']
moves_1 = [[0, 0], [0, 1], [1, 0], [1, 0], [0, 1]]

measurements_2 = ['r', 'r', 'r', 'r', 'g']
moves_2 = [[0, 0], [1, 0], [1, 0], [0, 1], [0, 1]]

r = Robot(world_as_we_know_it, True)
r.localize(measurements_1, moves_1)