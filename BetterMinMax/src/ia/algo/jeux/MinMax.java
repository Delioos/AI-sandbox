package ia.algo.jeux;

import ia.framework.common.Action;
import ia.framework.common.BaseProblem;
import ia.framework.common.State;
import ia.framework.jeux.Game;
import ia.framework.jeux.GameState;
import ia.framework.jeux.Player;


import java.util.ArrayList;


public class MinMax extends Player {

    public MinMax(Game g, boolean player_one) {
        super(g, player_one);
    }

    /**
     * {@inheritDoc}
     * <p>Demande un coup au joueur humain</p>
     */
    public Action getMove(GameState state) {
        return game.getMinMaxMove(state, this);
    }

}
