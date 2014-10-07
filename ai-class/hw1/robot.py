#coding: UTF-8
#Uma das primeiras classes feitas na mão por mim :)
class Robot:
    """
        O robô deve ter um mapa, mas mesmo assim ele não
        sabe onde que ele está:
    """
    def __init__(self, map, verbose = False):
        self.map = map
        self.prob_map = self.init_prob_map()
        self.verbose = verbose
        #esses valores aqui serão setados como parâmetros:
        self.sensor_right = 0.7
        self.sensor_wrong = 1 - self.sensor_right
        self.p_move = 0.8;
        self.p_stay = 1 - self.p_move

    """
        Atualiza a matriz de probabilidades depois do
        robô ter-se movido.
    """
    def move(self, motion):
        rows = len(self.prob_map)
        cols = len(self.prob_map[0])
        aux = [[0.0 for row in range(cols)]
            for col in range(rows)]
        for i in range(rows):
            for j in range(cols):
                #Estamos assumindo um mundo circular:
                i_from = (i - motion[0]) % rows
                j_from = (j - motion[1]) % cols
                p_from = self.prob_map[i_from][j_from]

                aux[i][j] = \
                    (self.p_move * p_from) +
                    (self.p_stay * self.prob_map[i][j])
        self.prob_map = aux

    """
        Atualiza a matriz de probabilidades depois do
        robô ter sensado a cor da sua posição.
    """
    def sense(self, measurement):
        s = 0.
        rows = len(self.prob_map)
        cols = len(self.prob_map[0])
        aux = [[0.0 for row in range(cols)]
            for col in range(rows)]
        for i in range(rows):
            for j in range(cols):
                hit = (measurement == self.map[i][j])
                aux[i][j] = \
                self.prob_map[i][j] * (\
                    hit * self.sensor_right + \
                    (1 - hit) * self.sensor_wrong)
                s += aux[i][j]
        for i in range(rows):
            for j in range(cols):
                aux[i][j] /= s
        self.prob_map = aux

    """
        Se eu quiser saber como que é o mapa do robô,
        criamos uma funcão para imprimi-lo:
    """
    def show_map(self):
        for row in range(len(self.map)):
            print self.map[row]
        print

    """
        O robô criará uma matriz de probabilidades onde
        cada elemento m_{ij} é a probabilidade do robô
        estar nessa posição do mapa. Então,
        \sum_{i,j}m_{ij} = 1 (dentro das tolerãncias).

        Então, si quiser saber como va a matriz de
        probabilidades, aqui está a função para mostrá-la:
    """
    def show_prob_map(self):
        for row in range(len(self.prob_map)):
            print [float('{:1.3}'.format(val)) for val in
                self.prob_map[row]]
        print

    """
        Criar uma matriz de probabilidades inicial, ou seja,
        com todos seus elementos iguais. As dimensões dessa
        matriz dependem das dimensões do mapa do mundo
        mesmo:
    """
    def init_prob_map(self):
        rows = len(self.map)
        cols = len(self.map[0])
        p = 1. / (rows * cols)
        return [[p for j in range(cols)] for i in
            range(rows)]

    """
        Baseado na informação arrecadada pelos sensores do
        robô e a informação dos movimentos do robô, essa
        função atualizará a matriz de probabilidades para
        fazermos uma idea da posição dele dentro do mapa:
    """
    def localize(self, measurements, moves):
        for k in range(len(measurements)):
            self.move(moves[k])
            if self.verbose:
                print 'Move ', k + 1, ':'
                self.show_prob_map()
            self.sense(measurements[k])
            if self.verbose:
                print 'Sense ', k + 1, ':'
                self.show_prob_map()