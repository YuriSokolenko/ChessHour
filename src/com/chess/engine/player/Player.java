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
import com.google.common.collect.Iterables;

public abstract class Player {

	protected final Board board;
	protected final King playerKing;
	protected final Collection<Move> legalMoves;
	protected final Collection<Move> opponentMoves;
	private final boolean isInCheck;
	
	
	public abstract Collection<Piece> getActivePieces();	
	public abstract Alliance getAlliance();
	public abstract Player getOpponent();
	
	protected abstract Collection<Move> calculateKingCastles (Collection<Move>playerLegals, Collection<Move> opponentsLegals);
	
	public Player(Board board, Collection<Move> legalMoves, Collection<Move> opponentMoves) {
		this.board = board;
		this.playerKing = establishKing();
		this.legalMoves = ImmutableList.copyOf(Iterables.concat(legalMoves, calculateKingCastles(legalMoves, opponentMoves)));
		this.opponentMoves=opponentMoves;
		//if the opponents moves attacks a king`s position is not empty then the player is in Check!
		this.isInCheck = !Player.calculateAttacksOnTile(this.playerKing.getPiecePosition(), opponentMoves).isEmpty();
	}
	

	
	protected static Collection<Move> calculateAttacksOnTile(int piecePosition, Collection<Move> moves){
		 final List<Move> attackMoves = new ArrayList<>();
		 
		 for(final Move move: moves) {
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
	public Collection<Move> getLegalMoves() {
		return this.legalMoves;
	}
	private Piece getPlayerKing() {
		return this.playerKing;
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
		//if the move is illigal:
		if(!isMoveLegal(move)) {
			return new MoveTransition(this.board, move, MoveStatus.ILLEGAL_MOVE);
		}
		
		//if the move is legal - excute it on abstract transition board
		final Board transitionBoard = move.execute();
		// calculates if the new position of the king is under check.
		final Collection<Move> kingAttacks = Player.calculateAttacksOnTile(transitionBoard.currentPlayer()
				.getOpponent().getPlayerKing().getPiecePosition(),
				transitionBoard.currentPlayer().getLegalMoves());
		
		//if the king is still under check - returns old board with the MoveStatus.LEAVES_PLAYER_IN_CHECK
		if(!kingAttacks.isEmpty()) {
			return new MoveTransition(this.board, move, MoveStatus.LEAVES_PLAYER_IN_CHECK);
		}
				
		//if the king no more under check - returns abstract board with king`s move executed - and it becomes real
		return new MoveTransition(transitionBoard, move, MoveStatus.DONE);
	}
	
}
