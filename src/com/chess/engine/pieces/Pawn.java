package com.chess.engine.pieces;

import java.util.ArrayList;

import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Move.MajorMove;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Pawn extends Piece {

	private final static int[] CANDIDATE_MOVE_COORDINATE = { 8, 16, 7, 9 };

	public Pawn(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.PAWN, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATE) {

			final int candidateDestinationCoordinate = this.piecePosition
					+ (this.pieceAlliance.getDirection() * currentCandidateOffset);

			if (!BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {
				continue;
			}

			if (currentCandidateOffset == 8 && !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {

				// TODO more work here!!!! (deal with promotions)
				legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
			} // Pawn Jump:
			else if (currentCandidateOffset == 16 && this.isFirstMove()
					&& (BoardUtils.SECOND_ROW[this.piecePosition] && this.pieceAlliance.isBlack())
					|| (BoardUtils.SEVENTH_ROW[this.piecePosition] && this.pieceAlliance.isWhite())) {
				final int behindCandidateDestinationCoordinate = this.piecePosition
						+ (this.pieceAlliance.getDirection() * 8);
				if (!board.getTile(behindCandidateDestinationCoordinate).isTileOccupied()
						&& !board.getTile(candidateDestinationCoordinate).isTileOccupied()) {

					legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
				}

				// checks if a pawn can make an attacking movement without falling from the board
				
				// currentCandidateOffset in case the pawn is attacking to the right (for white)&&to the left (for black)
			} else if (currentCandidateOffset == 7
					&& !((BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()
							|| (BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack())))) {

				if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
					final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
					if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
						// TODO to do more here!
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					}
				}
				// currentCandidateOffset in case the pawn is attacking to the left (for white)&&to the right (for black)
			} else if (currentCandidateOffset == 9
					&& !((BoardUtils.FIRST_COLUMN[this.piecePosition] && this.pieceAlliance.isWhite()
							|| BoardUtils.EIGHTH_COLUMN[this.piecePosition] && this.pieceAlliance.isBlack()))) {
				{
					if (board.getTile(candidateDestinationCoordinate).isTileOccupied()) {
						final Piece pieceOnCandidate = board.getTile(candidateDestinationCoordinate).getPiece();
						if (this.pieceAlliance != pieceOnCandidate.getPieceAlliance()) {
							// TODO to do more here!
							legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
						}
					}
				}

			}
		}

		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return Piece.PieceType.PAWN.toString();
	}

	@Override
	public Pawn movePiece(Move move) {
		return new Pawn(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

}
