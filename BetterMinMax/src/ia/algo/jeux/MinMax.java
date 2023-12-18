package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.ActionValuePair;
import ia.framework.common.State;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;

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
            return MaxValue(state).getAction();
        } else {
            return MinValue(state).getAction();
        }
    }

    public ActionValuePair MaxValue(GameState state) {
        // si c'est la fin de la partie on retourne une action null
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vmax = Integer.MIN_VALUE;
        // on initialise vmax à -infini
        // on initialise cmax à null
        // cmax correspond au coup qui mène à vmax
        Action cmax = null;
        // pour chaque action possible
        for (Action action : this.game.getActions(state)) {
            GameState ssuivant = (GameState) this.game.doAction(state, action);
            // on récupère l'état suivant
            ActionValuePair v = MinValue(ssuivant);
            if (v.getValue() > vmax) {
                vmax = (int) v.getValue();
                cmax = action;
            }
        }
        // on récupère l'état suivant
        return new ActionValuePair(cmax, vmax);
    }

    private ActionValuePair MinValue(GameState state) {
        if (this.game.endOfGame(state)) {
            return new ActionValuePair(null, state.getGameValue());
        }

        double vmin = Integer.MAX_VALUE;
        // on initialise vmin à +infini
        // on initialise cmin à null
        Action cmin = null;
        for (Action action : this.game.getActions(state)) {
            GameState ssuivant = (GameState) this.game.doAction(state, action);
            // on récupère l'état suivant
            ActionValuePair v = MaxValue(ssuivant);
            if (v.getValue() < vmin) {
                vmin = (int) v.getValue();
                cmin = action;
            }
        }

        return new ActionValuePair(cmin, vmin);
    }
}