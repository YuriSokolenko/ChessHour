package com.chess.engine.board;

import com.chess.engine.board.Board.Builder;
import com.chess.engine.pieces.Pawn;
import com.chess.engine.pieces.Piece;
import com.chess.engine.pieces.Rook;


/**
 * 
 * @author Yuri Sokolenko
 *
 *An Abstract class Move and to extends classes to describe attacking move and regular (Major) move.
 */
public abstract class Move {

	
	final Board board;
	final Piece movedPiece;
	final int destinationCoordinate;
	
	public static final Move NULL_MOVE = new NullMove();
	
	private Move(final Board board, final Piece movedPiece, final int destinationCoordinate) {
		super();
		this.board = board;
		this.movedPiece = movedPiece;
		this.destinationCoordinate = destinationCoordinate;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + this.destinationCoordinate;
		result = prime * result + movedPiece.hashCode();
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Move)) {
			return false;
		}
		final Move other = (Move) obj;
		return getDestinationCoordinate() == other.getDestinationCoordinate() &&
			   getMovedPiece().equals(other.getMovedPiece());
			}



	public int getDestinationCoordinate() {
		return this.destinationCoordinate;
	}
	
	public int getCurrentCoordinate() {
		return this.movedPiece.getPiecePosition();
	}
	
	public Piece getMovedPiece() {
		return this.movedPiece;
	}
	
	public boolean isAttack() {
		return false;
	}
	
	public boolean isCastlingMove() {
		return false;
	}
	
	public Piece getAttackedPiece() {
		return null;
	}
	
	public Board execute() {
		final Builder builder = new Builder();
		
		//moves all pieces to new board
		for(final Piece piece : this.board.currentPlayer().getActivePieces()) {
			//TODO hash code and equals for pieces
			if(!this.movedPiece.equals(piece)) {
				builder.setPiece(piece);
			}
		}
		
		for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
			builder.setPiece(piece);
		}
		//move the moved piece!
		builder.setPiece(this.movedPiece.movePiece(this));
		builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
		return builder.build();
	}

	public static final class MajorMove extends Move{

		public MajorMove(final Board board, 
						 final Piece movedPiece, 
						 final int destinationCoordinate) {
			
			super(board, movedPiece, destinationCoordinate);
		}

	}
	
	public static class AttackMove extends Move{

		final Piece attackedPiece;
		
		public AttackMove(final Board board,
						  final Piece movedPiece,
						  final int destinationCoordinate,
						  final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate);
			this.attackedPiece=attackedPiece;
		}
		
		@Override
		public int hashCode() {
			return this.attackedPiece.hashCode() + super.hashCode();
		}
		
		@Override
		public boolean equals(final Object obj) {
			if(this == obj) {
				return true;
			}
			if(!(obj instanceof AttackMove)) {
				return false;
			}
			final AttackMove otherAttackMove = (AttackMove) obj;
			return super.equals(otherAttackMove)&& getAttackedPiece().equals(otherAttackMove.getAttackedPiece());
			
			
		}
		@Override
		public boolean isAttack() {
			return true;
		}
		
		@Override
		public Piece getAttackedPiece() {
			return this.attackedPiece;
		}
		
		
	}
	
	public static final class PawnMove extends Move{

		public PawnMove(final Board board, 
						 final Piece movedPiece, 
						 final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
	}
	
	public static class PawnAttackMove extends AttackMove{

		public PawnAttackMove(final Board board, 
						 final Piece movedPiece, 
						 final int destinationCoordinate,
						 final Piece attackPiece) {
			super(board, movedPiece, destinationCoordinate, attackPiece);
		}

	}
	
	public static class PawnOnPassantAttackMove extends PawnAttackMove{

		public PawnOnPassantAttackMove(final Board board, 
									   final Piece movedPiece, 
									   final int destinationCoordinate,
									   final Piece attackedPiece) {
			super(board, movedPiece, destinationCoordinate, attackedPiece);
		}

	}
	
	public static final class PawnJump extends Move{

		public PawnJump (final Board board, 
						 final Piece movedPiece, 
						 final int destinationCoordinate) {
			super(board, movedPiece, destinationCoordinate);
		}
		
		@Override
		public Board execute() {
			final Builder builder = new Builder();
			for(final Piece piece: this.board.currentPlayer().getActivePieces()) {
				if(!this.movedPiece.equals(piece)) {
					builder.setPiece(piece);
				}
			}
			for(final Piece piece: this.board.currentPlayer().getOpponent().getActivePieces()) {
				builder.setPiece(piece);
				}
			final Pawn movedPawn = (Pawn)this.movedPiece.movePiece(this);
			builder.setPiece(movedPawn);
			builder.setEnPassantPawn(movedPawn);
			builder.setMoveMaker(this.board.currentPlayer().getOpponent().getAlliance());
			return builder.build();
		}

	}
	
	public static abstract class CastleMove extends Move{

		public CastleMove(final Board board, 
						 final Piece movedPiece, 
						 final int destinationCoordinate, Rook castleRook, int caslteRookStart, int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate);
		}

	}
	
	public static final class KingSideCastleMove extends CastleMove{

		public KingSideCastleMove (final Board board, 
								   final Piece movedPiece, 
								   final int destinationCoordinate,
								   final Rook castleRook,
								   final int caslteRookStart,
								   final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, caslteRookStart, castleRookDestination );
		}

	}
	
	public static final class QueenSideCastleMove extends CastleMove{

		public QueenSideCastleMove (final Board board, 
									final Piece movedPiece, 
									final int destinationCoordinate,
									final Rook castleRook,
									final int caslteRookStart,
									final int castleRookDestination) {
			super(board, movedPiece, destinationCoordinate, castleRook, castleRookDestination, castleRookDestination);
		}

	}
	
	public static final class NullMove extends Move{

		public NullMove	() {
			super(null, null, -1);
		}

		@Override
		public Board execute() {
			throw new RuntimeException("cannot execute the null move!");
		}
	}
	
	public static class MoveFactory {
		
		private MoveFactory() {
			throw new RuntimeException("Not instatiable!");
		}
		
		public static Move createMove(final Board board,
									  final int currentCoordinate,
									  final int destinationCoordinate) {
			
			for(final Move move: board.getAllLegalMoves()) {
				if(move.getCurrentCoordinate() == currentCoordinate &&
				   move.getDestinationCoordinate() == destinationCoordinate) {
					return move;
				}
			}
			
			return NULL_MOVE;
		}
		
	}
	
	
	
}
