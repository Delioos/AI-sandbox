package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

import static ia.framework.jeux.Player.PLAYER1;

public class MinMax extends Player {

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MinMax(Game g, boolean player_one) {
        super(g, player_one);
    }

    @Override
    public Action getMove(GameState state) {
        if (player == PLAYER1) {
            return MaxValeur(state).getAction();
        } else {
            return MinValeur(state).getAction();
        }
    }

    private ActionValuePair MaxValeur(GameState state) {
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        int V_max = Integer.MIN_VALUE;
        Action C_max = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MinValeur(S_suivant);
            if (v.getValue() > V_max) {
                V_max = (int) v.getValue();
                C_max = action;
            }
        }
        return new ActionValuePair(C_max, V_max);
    }

    private ActionValuePair MinValeur(GameState state) {
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        int V_min = Integer.MAX_VALUE;
        Action C_min = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MaxValeur(S_suivant);
            if (v.getValue() < V_min) {
                V_min = (int) v.getValue();
                C_min = action;
            }
        }
        return new ActionValuePair(C_min, V_min);
    }

}