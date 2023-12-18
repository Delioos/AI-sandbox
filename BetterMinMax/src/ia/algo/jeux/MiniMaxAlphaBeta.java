package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

import static ia.framework.jeux.Player.PLAYER1;

public class MiniMaxAlphaBeta extends Player {

    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int MINUS_INFINITY = Integer.MIN_VALUE;

    /**
     * Represente un joueur
     *
     * @param g          l'instance du jeux
     * @param player_one si joueur 1
     */
    public MiniMaxAlphaBeta(Game g, boolean player_one) {
        super(g, player_one);
    }

    @Override
    public Action getMove(GameState state) {
        if (player == PLAYER1) {
            return MaxValeur(state, MINUS_INFINITY, INFINITY).getAction();
        } else {
            return MinValeur(state, MINUS_INFINITY, INFINITY).getAction();
        }
    }

    private ActionValuePair MaxValeur(GameState state, int alpha, int beta) {
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        int V_max = Integer.MIN_VALUE;
        Action C_max = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MinValeur(S_suivant, alpha, beta);
            if (v.getValue() > V_max) {
                V_max = (int) v.getValue();
                C_max = action;
            }

            // *16      si V_max > alpha
            if (V_max > alpha) {
                // *17        alpha = V_max
                alpha = V_max;
            }

            // *18    si V_max >= beta
            if (V_max >= beta) {
                // *19      retourner (V_max, C_max)
                return new ActionValuePair(C_max, V_max);
            }
        }
        // 20  retourner (V_max, C_max)
        return new ActionValuePair(C_max, V_max);
    }

    private ActionValuePair MinValeur(GameState state, int alpha, int beta) {
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }
        int V_min = Integer.MAX_VALUE;
        Action C_min = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MaxValeur(S_suivant, alpha, beta);
            if (v.getValue() < V_min) {
                V_min = (int) v.getValue();
                C_min = action;
            }

            // *31      si V_min < beta
            if (V_min < beta) {
                // *32         beta = V_min
                beta = V_min;
            }

            // *33    si V_min <= alpha
            if (V_min <= alpha) {
                // *34      retourner (V_min, C_min)
                return new ActionValuePair(C_min, V_min);
            }
        }
        // 26  retourner (V_min, C_min)
        return new ActionValuePair(C_min, V_min);
    }
}
