package com.chess.engine.pieces;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.chess.engine.board.Board;
import com.chess.engine.board.BoardUtils;
import com.chess.engine.board.Move;
import com.chess.engine.board.Tile;
import com.chess.engine.board.Move.AttackMove;
import com.chess.engine.board.Move.MajorMove;
import com.google.common.collect.ImmutableList;

public class Rook extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = { -8, -1, 1, 8 };

	public Rook(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.ROOK, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition;

			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

				// checks if the Rook is on first o eighth column. If yes - break;
				if (isFirstColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset)
						|| (isEighthColumnExclusion(candidateDestinationCoordinate, candidateCoordinateOffset))) {
					break;
				}
				candidateDestinationCoordinate += candidateCoordinateOffset;

				if (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

					final Tile candidateDestinationTile = board.getTile(candidateDestinationCoordinate);

					if (!candidateDestinationTile.isTileOccupied()) {
						legalMoves.add(new MajorMove(board, this, candidateDestinationCoordinate));
					} else {
						final Piece pieceAtDestination = candidateDestinationTile.getPiece();
						final Alliance pieceAlliance = pieceAtDestination.getPieceAlliance();

						if (this.pieceAlliance != pieceAlliance) {
							legalMoves.add(
									new AttackMove(board, this, candidateDestinationCoordinate, pieceAtDestination));
						}
						break;
					}
				}
			}
		}

		return ImmutableList.copyOf(legalMoves);
	}
	
	@Override
	public String toString() {
		return PieceType.ROOK.toString();
	}

	// if the Rook is in first column.
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {

		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {

		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1);
	}

	@Override
	public Rook movePiece(Move move) {
		return new Rook(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

}
