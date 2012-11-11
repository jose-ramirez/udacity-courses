# --------------
# USER INSTRUCTIONS
#
# Now you will put everything together.
#
# First make sure that your sense and move functions
# work as expected for the test cases provided at the
# bottom of the previous two programming assignments.
# Once you are satisfied, copy your sense and move
# definitions into the robot class on this page, BUT
# now include noise.
#
# A good way to include noise in the sense step is to
# add Gaussian noise, centered at zero with variance
# of self.bearing_noise to each bearing. You can do this
# with the command random.gauss(0, self.bearing_noise)
#
# In the move step, you should make sure that your
# actual steering angle is chosen from a Gaussian
# distribution of steering angles. This distribution
# should be centered at the intended steering angle
# with variance of self.steering_noise.
#
# Feel free to use the included set_noise function.
#
# Please do not modify anything except where indicated
# below.

from math import *
import random

# --------
# 
# some top level parameters
#

max_steering_angle = pi / 4.0 # You do not need to use this value, but keep in mind the limitations of a real car.
bearing_noise = 0.1 # Noise parameter: should be included in sense function.
steering_noise = 0.1 # Noise parameter: should be included in move function.
distance_noise = 5.0 # Noise parameter: should be included in move function.

tolerance_xy = 15.0 # Tolerance for localization in the x and y directions.
tolerance_orientation = 0.25 # Tolerance for orientation.


# --------
# 
# the "world" has 4 landmarks.
# the robot's initial coordinates are somewhere in the square
# represented by the landmarks.
#
# NOTE: Landmark coordinates are given in (y, x) form and NOT
# in the traditional (x, y) format!

landmarks  = [[0.0, 100.0], [0.0, 0.0], [100.0, 0.0], [100.0, 100.0]] # position of 4 landmarks in (y, x) format.
world_size = 100.0 # world is NOT cyclic. Robot is allowed to travel "out of bounds"

# ------------------------------------------------
# 
# this is the robot class
#

class robot:

    # --------
    # init: 
    #    creates robot and initializes location/orientation 
    #

    def __init__(self, length = 20.0):
        self.x = random.random() * world_size # initial x position
        self.y = random.random() * world_size # initial y position
        self.orientation = random.random() * 2.0 * pi # initial orientation
        self.length = length # length of robot
        self.bearing_noise  = 0.0 # initialize bearing noise to zero
        self.steering_noise = 0.0 # initialize steering noise to zero
        self.distance_noise = 0.0 # initialize distance noise to zero

    # --------
    # set: 
    #    sets a robot coordinate
    #

    def set(self, new_x, new_y, new_orientation):

        if new_orientation < 0 or new_orientation >= 2 * pi:
            raise ValueError, 'Orientation must be in [0..2pi]'
        self.x = float(new_x)
        self.y = float(new_y)
        self.orientation = float(new_orientation)

    # --------
    # set_noise: 
    #    sets the noise parameters
    #
    def set_noise(self, new_b_noise, new_s_noise, new_d_noise):
        # makes it possible to change the noise parameters
        # this is often useful in particle filters
        self.bearing_noise  = float(new_b_noise)
        self.steering_noise = float(new_s_noise)
        self.distance_noise = float(new_d_noise)

    # --------
    # measurement_prob
    #    computes the probability of a measurement
    #  

    def measurement_prob(self, measurements):

        # calculate the correct measurement
        predicted_measurements = self.sense(0) # Our sense function took 0 as an argument to switch off noise.


        # compute errors
        error = 1.0
        for i in range(len(measurements)):
            error_bearing = abs(measurements[i] - predicted_measurements[i])
            error_bearing = (error_bearing + pi) % (2.0 * pi) - pi # truncate
            

            # update Gaussian
            error *= (exp(- (error_bearing ** 2) / (self.bearing_noise ** 2) / 2.0) /  
                      sqrt(2.0 * pi * (self.bearing_noise ** 2)))

        return error
    
    def __repr__(self): #allows us to print robot attributes.
        return '[x=%.6s y=%.6s orient=%.6s]' % (str(self.x), str(self.y), 
                                                str(self.orientation))
    
    ############# ONLY ADD/MODIFY CODE BELOW HERE ###################
       
    # --------
    # move: 
    #   
    def move(self, motion): # Do not change the name of this function
        result = robot()
        result.set_noise(bearing_noise, steering_noise, distance_noise)
        theta = self.orientation
        alpha = random.gauss(motion[0], self.steering_noise)
        d = random.gauss(motion[1], self.distance_noise)
        beta = (d / self.length) * tan(alpha)
        if fabs(beta) < 0.001:
            x_new = self.x + d * cos(theta)
            y_new = self.y - d * sin(theta)
            orientation_new = (theta + beta) % (2 * pi)
            result.set(x_new, y_new, orientation_new)
        else:
            R = d / beta
            x_new = self.x - R * sin(theta) + R * sin(theta + beta)
            y_new = self.y + R * cos(theta) - R * cos(theta + beta)
            orientation_new = (theta + beta) % (2 * pi)
            result.set(x_new, y_new, orientation_new)
        return result
    # copy your code from the previous exercise
    # and modify it so that it simulates motion noise
    # according to the noise parameters
    #           self.steering_noise
    #           self.distance_noise

    # --------
    # sense: 
    #    
    def sense(self, noise = 0):
        Z = []
        theta = self.orientation
        x = self.x
        y = self.y
        for i in range(len(landmarks)):
            bearing = (atan2(landmarks[i][0] - y, landmarks[i][1] - x) - theta + (2 * pi)) % (2 * pi)
            if noise:
                bearing += random.gauss(0, self.bearing_noise)
            Z.append(bearing)
        return Z
    # copy your code from the previous exercise
    # and modify it so that it simulates bearing noise
    # according to
    #           self.bearing_noise

    ############## ONLY ADD/MODIFY CODE ABOVE HERE ####################

