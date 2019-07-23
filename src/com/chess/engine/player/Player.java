package com.chess.engine.player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.Move;
import com.chess.engine.pieces.Alliance;
import com.chess.engine.pieces.King;
import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableList;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	protected final Collection<Move> opponentMoves;
	private final boolean isInCheck;
	
	
	public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = legalMoves;
		this.opponentMoves=opponentMoves;
		//if the opponents moves attacks a king`s position is not empty then the player is in Check!
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}
	
	
	
	
	private static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> opponentMoves){
		 final List<Move> attackMoves = new ArrayList<>();
		 
		 for(final Move move: attackMoves) {
			 if(piecePosition == move.getDestinationCoordinate()) {
				 attackMoves.add(move);
			 }
		 }
		 
		 return ImmutableList.copyOf(attackMoves);
	}

	/**
	 * checks if there is a king on the board
	 * @return King piece
	 */
	private King establishKing() {
		for(final Piece piece: getActivePieces()) {
			if(piece.getPieceType().isKing()) {
				return (King)piece;
			}
		}
		throw new RuntimeException("Should not reach here! Not a valid board!!!");
		
	}

	public boolean isMoveLegal(final Move move) {
		return this.legalMoves.contains(move);
	}
	
	public boolean isInCheck() {
		return this.isInCheck;
	}
	
	public boolean isInCheckMate() {
		return this.isInCheck && !hasEscapeMoves();
	}
	
	protected boolean hasEscapeMoves() {

		for(final Move move: this.legalMoves) {
			final MoveTransition transition = makeMove(move);
			if(transition.getMoveStatus().isDone()) {
				return true;
			}
		}
		return false;
	}




	public boolean isInStaleMate() {
		return !this.isInCheck && !hasEscapeMoves();
	}
	
	public boolean isCastled() {
		return false;
	}
	
	public MoveTransition makeMove(final Move move) {
		return null;
	}
	
	
	public abstract Collection<Piece> getActivePieces();	
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	
}
