package com.chess.engine.pieces;

import java.util.Collection;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;

public abstract class Piece {

	protected final int piecePosition;
	protected final Alliance pieceAlliance;
	protected final boolean isFirstMove;
	protected final PieceType pieceType;
	private final int cashedHashCode;

	Piece(final PieceType pieceType, final Alliance alliance, final int piecePosition) {
		this.piecePosition = piecePosition;
		this.pieceAlliance = alliance;
		this.pieceType = pieceType;
		this.isFirstMove = false;
		this.cashedHashCode = computeHashCode();
	}
	
	
	
	public int computeHashCode() {
 		int result = pieceType.hashCode();
		result = 31 * result + pieceAlliance.hashCode();
		result = 31 * result + piecePosition;
		result = 31 * result + (isFirstMove ? 1: 0);
		return result;
	}

	@Override
	public int hashCode() {
		return this.cashedHashCode;
	}




	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Piece)) {
			return false;
		}
		
		Piece otherPiece = (Piece) obj;	
		return piecePosition == otherPiece.getPiecePosition() &&
					   pieceType == otherPiece.getPieceType() &&
					   pieceAlliance == otherPiece.getPieceAlliance();
	}




	public Alliance getPieceAlliance() {
		return pieceAlliance;
	}

	public boolean isFirstMove() {
		return this.isFirstMove;

	}
	
	public PieceType getPieceType() {
		return this.pieceType;
	}

	public int getPiecePosition() {
		return this.piecePosition;
	}

	public abstract Piece movePiece(Move move);
	
	public abstract Collection<Move> calculateLegalMoves(final Board board);

	public enum PieceType {

		PAWN("P") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, KNIGHT("N") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, BISHOP("B") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, ROOK("R") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return true;
			}
		}, QUEEN("Q") {
			@Override
			public boolean isKing() {
				return false;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		}, KING("K") {
			@Override
			public boolean isKing() {
				return true;
			}

			@Override
			public boolean isRook() {
				return false;
			}
		};

		private String pieceName;

		PieceType(final String pieceName) {
			this.pieceName = pieceName;
		}

		@Override
		public String toString() {
			return this.pieceName;
		}

		public abstract boolean isKing();
		public abstract boolean isRook();
		

	}


}
