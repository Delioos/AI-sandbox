package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;


public class MiniMaxAlphaBeta extends Player {

    private static final int INFINITY = Integer.MAX_VALUE;
    private static final int MINUS_INFINITY = Integer.MIN_VALUE;

    public static final int MAX_DEPTH = 5;

    public static int nb_noeuds = 0;

    /**
     * Represente un joueur
     *
     * @param g          l'instance des jeux
     * @param player_one si joueur 1
     */
    public MiniMaxAlphaBeta(Game g, boolean player_one) {
        super(g, player_one);
    }

    @Override
    public Action getMove(GameState state) {
        this.nb_noeuds = 0;
        Action action = null;
        if (player == PLAYER1) {
            action = (Action) MaxValeur(state, MINUS_INFINITY, INFINITY, MAX_DEPTH).getAction();
        } else {
            action = (Action) MinValeur(state, MINUS_INFINITY, INFINITY, MAX_DEPTH).getAction();
        }
        System.out.println("Nombre de noeuds : " + this.nb_noeuds);
        return action;
    }

    private ActionValuePair MaxValeur(GameState state, int alpha, int beta, int depth) {
        this.nb_noeuds++;
        double points = state.getHeuristicValue();
        if (this.game.endOfGame(state) || depth == 0) {
            return new ActionValuePair(null, points);
        }
        int vmax = Integer.MIN_VALUE;
        Action cmax = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MinValeur(S_suivant, alpha, beta, depth - 1);
            if (v.getValue() > vmax) {
                vmax = (int) v.getValue();
                cmax = action;
            }

            // *16      si vmax > alpha
            if (vmax > alpha) {
                // *17        alpha = vmax
                alpha = vmax;
            }

            // *18    si vmax >= beta
            if (vmax >= beta) {
                // *19      retourner (vmax, cmax)
                return new ActionValuePair(cmax, vmax);
            }
        }
        // 20  retourner (vmax, cmax)
        return new ActionValuePair(cmax, vmax);
    }

    private ActionValuePair MinValeur(GameState state, int alpha, int beta, int depth) {
        this.nb_noeuds++;
        double points = state.getHeuristicValue();
        if (this.game.endOfGame(state) || depth == 0) {
            return new ActionValuePair(null, points);
        }
        int vmin = Integer.MAX_VALUE;
        Action cmin = null;

        for (Action action : this.game.getActions(state)) {
            GameState S_suivant = (GameState) this.game.doAction(state, action);
            ActionValuePair v = MaxValeur(S_suivant, alpha, beta, depth - 1);
            if (v.getValue() < vmin) {
                vmin = (int) v.getValue();
                cmin = action;
            }

            // *31      si vmin < beta
            if (vmin < beta) {
                // *32         beta = vmin
                beta = vmin;
            }

            // *33    si vmin <= alpha
            if (vmin <= alpha) {
                // *34      retourner (vmin, cmin)
                return new ActionValuePair(cmin, vmin);
            }
        }
        // 26  retourner (vmin, cmin)
        return new ActionValuePair(cmin, vmin);
    }
}
