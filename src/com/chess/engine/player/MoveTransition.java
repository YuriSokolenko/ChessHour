package com.chess.engine.player;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public class MoveTransition {

	private final Board transitionBoard;
	private final Move move;
	private final MoveStatus movestatus;
	
	
	public MoveTransition(final Board transitionBoard,
				final Move move,
				final MoveStatus movestatus) {
		super();
		this.transitionBoard = transitionBoard;
		this.move = move;
		this.movestatus = movestatus;
	}
	
	public MoveStatus getMoveStatus() {
		return this.movestatus;
	}
	
	
	
}
