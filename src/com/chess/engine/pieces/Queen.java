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
import com.chess.engine.pieces.Piece.PieceType;
import com.google.common.collect.ImmutableList;

public class Queen extends Piece {

	private final static int[] CANDIDATE_MOVE_VECTOR_COORDINATE = { -9, -8, -7, -1, 1, 7, 8, 9 };

	public Queen(final Alliance pieceAlliance, final int piecePosition) {
		super(PieceType.QUEEN, pieceAlliance, piecePosition);
	}

	@Override
	public Collection<Move> calculateLegalMoves(final Board board) {

		final List<Move> legalMoves = new ArrayList<>();

		for (final int candidateCoordinateOffset : CANDIDATE_MOVE_VECTOR_COORDINATE) {
			int candidateDestinationCoordinate = this.piecePosition;

			while (BoardUtils.isValidTileCoordinate(candidateDestinationCoordinate)) {

				// checks if the Queen is on first o eighth column. If yes - break;
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
		return Piece.PieceType.QUEEN.toString();
	}

	// if the Queen is in first column.
	private static boolean isFirstColumnExclusion(final int currentPosition, final int candidateOffset) {

		return BoardUtils.FIRST_COLUMN[currentPosition] && (candidateOffset == -1||candidateOffset == -9 || candidateOffset == 7);
	}

	private static boolean isEighthColumnExclusion(final int currentPosition, final int candidateOffset) {

		return BoardUtils.EIGHTH_COLUMN[currentPosition] && (candidateOffset == 1||candidateOffset == 9 || candidateOffset == -7);
	}

	@Override
	public Queen movePiece(Move move) {
		return new Queen(move.getMovedPiece().getPieceAlliance(), move.getDestinationCoordinate());
	}

}
