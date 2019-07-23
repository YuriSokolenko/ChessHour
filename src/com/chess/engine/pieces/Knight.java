package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Knight extends Piece {

	// All board as array, each tile has a coordinate - int num.
	// CANDIDATE_MOVE_COORDINATES - is relative to Knight position.
	private final static int[] CANDIDATE_MOVE_COORDINATES = { -17, -15, -10, -6, 6, 10, 15, 17 };

	public Knight(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.KNIGHT, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		List<Move> legalMoves = new ArrayList<>();

		for (final int currentCandidateOffset : CANDIDATE_MOVE_COORDINATES) {

			int candidateDestinationCoordinate = this.piecePosition - currentCandidateOffset;

			if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

				if (isFirstColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isSecondColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isSeventhColumnExclusion(this.piecePosition, currentCandidateOffset)
						|| isEighthColumnExclusion(this.piecePosition, currentCandidateOffset)) {
					continue;
				}

				final Tile candidateDestinaionTile = board.getTile(candidateDestinationCoordinate);

				if (!candidateDestinaionTile.isTileOccupied()) {
					legalMoves.add(new Move.MajorMove(board, this, candidateDestinationCoordinate));
				} else {
					final Piece pieceAtDestination = candidateDestinaionTile.getPiece();
					final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

					if (this.pieceAlliance != pieceAlliance) {
						legalMoves.add(new Move.AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
					}
				}
			}

		}
		return ImmutableList.copyOf(legalMoves);
	}
	
	
	@Override
	public String toString() {
		return Piece.PieceType.KNIGHT.toString();
	}

	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.FIRST_COLUMN[currentPosition] && ((candidateOffset == -17) || (candidateOffset == -10)
				|| (candidateOffset == 6) || (candidateOffset == 15));
	}

	private static boolean isSecondColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SECOND_COLUMN[currentPosition] && ((candidateOffset == -10) || candidateOffset == 6);
	}

	private static boolean isSeventhColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.SEVENTH_COLUMN[currentPosition] && ((candidateOffset == 10) || candidateOffset == -6);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {
		return BoardUtils.EIGHTH_COLUMN[currentPosition] && ((candidateOffset == 17) || (candidateOffset == 10)
				|| (candidateOffset == -6) || (candidateOffset == -15));
	}
}
