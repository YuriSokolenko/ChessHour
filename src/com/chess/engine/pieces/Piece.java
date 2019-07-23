package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {

	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;

	Piece(final Alliance pieceAlliance, final int piecePosition ) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = pieceAlliance;
		// TODO more Work here!!!
		this.isFirstMove = false;
	}

	public abstract Collection<Move> calculateLegalMoves(final Board board);

	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;

	}

	public int getPiecePosition() {
		return this.piecePosition;
	}
}
