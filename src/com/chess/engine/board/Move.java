package com.chess.engine.board;

import com.chess.engine.pieces.Piece;


/**
 * 
 * @author Yuri Sokolenko
 *
 *An Abstract class Move and to extends classes to describe attacking move and regular (Major) move.
 */
public abstract class Move {

	final Board board;
	final Piece piece;
	final int destinationCoordinate;
	
	
	private Move(final Board board, final Piece piece, final int destinationCoordinate) {
		super();
		this.board = board;
		this.piece = piece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	
	public static final class MajorMove extends Move{

		public MajorMove(final Board board, final Piece piece, final int destinationCoordinate) {
			super(board, piece, destinationCoordinate);
		}
	}
	
	public static final class AttackMove extends Move{

		final Piece attackedPiece;
		
		public AttackMove(Board board, Piece piece, int destinationCoordinate, final Piece attackedPiece) {
			super(board, piece, destinationCoordinate);
			this.attackedPiece=attackedPiece;
		}
		
		
	}
	
	
}
