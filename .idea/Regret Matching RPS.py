import numpy as np

class RPSTrainer:
    ROCK, PAPER, SCISSORS, NUM_ACTIONS = 0, 1, 2, 3

    def __init__(self):
        self.regret_sum_1 = np.zeros(self.NUM_ACTIONS)
        self.strategy_sum_1 = np.zeros(self.NUM_ACTIONS)
        self.regret_sum_2 = np.zeros(self.NUM_ACTIONS)
        self.strategy_sum_2 = np.zeros(self.NUM_ACTIONS)

    def get_strategy(self, regret_sum):
        strategy = np.maximum(regret_sum, 0)
        normalizing_sum = np.sum(strategy)
        if normalizing_sum > 0:
            strategy /= normalizing_sum
        else:
            strategy = np.ones(self.NUM_ACTIONS) / self.NUM_ACTIONS
        return strategy

    def get_action(self, strategy):
        return np.searchsorted(np.cumsum(strategy), np.random.rand())

    def train(self, iterations):
        action_utility = np.zeros(self.NUM_ACTIONS)
        for _ in range(iterations):
            strategy_1 = self.get_strategy(self.regret_sum_1)
            strategy_2 = self.get_strategy(self.regret_sum_2)
            action_1 = self.get_action(strategy_1)
            action_2 = self.get_action(strategy_2)

            action_utility[action_2] = 0
            action_utility[(action_2 + 1) % self.NUM_ACTIONS] = 1
            action_utility[(action_2 - 1) % self.NUM_ACTIONS] = -1

            self.regret_sum_1 += action_utility - action_utility[action_1]
            self.strategy_sum_1 += strategy_1

            action_utility[action_1] = 0
            action_utility[(action_1 + 1) % self.NUM_ACTIONS] = 1
            action_utility[(action_1 - 1) % self.NUM_ACTIONS] = -1

            self.regret_sum_2 += action_utility - action_utility[action_2]
            self.strategy_sum_2 += strategy_2

    def get_average_strategy(self, strategy_sum):
        normalizing_sum = np.sum(strategy_sum)
        if normalizing_sum > 0:
            return strategy_sum / normalizing_sum
        else:
            return np.ones(self.NUM_ACTIONS) / self.NUM_ACTIONS


if __name__ == '__main__':
    trainer = RPSTrainer()
    trainer.train(1000000)
    print("Player 1's average strategy:", trainer.get_average_strategy(trainer.strategy_sum_1))
    print("Player 2's average strategy:", trainer.get_average_strategy(trainer.strategy_sum_2))

