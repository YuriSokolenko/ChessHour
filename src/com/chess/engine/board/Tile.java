package com.chess.engine.board;

import java.util.HashMap;
import java.util.Map;

import com.chess.engine.pieces.Piece;
import com.google.common.collect.ImmutableMap;

public abstract class Tile {

	protected final int tileCoordintare;

	/**
	 * Creates immutable HashMap of all Empty Tiles by using guava library
	 * ImmutableMap.copyOf.
	 */
	private static final Map<Integer, EmptyTile> EMPTY_TILES_CASH = createAllPossibleEmptyTiles();

	private static Map<Integer, EmptyTile> createAllPossibleEmptyTiles() {

		final Map<Integer, EmptyTile> emptyTileMap = new HashMap<>();

		for (int i = 0; i < BoardUtils.NUM_TILES; i++) {
			emptyTileMap.put(i, new EmptyTile(i));
		}

		return ImmutableMap.copyOf(emptyTileMap);
	}

	/**
	 * 
	 * @param tileCoordinate
	 * @param piece
	 * @return if tile piece not supported - creates gets a emptyTile from EmptyTile
	 *         HashMap, if supported - creates new occupiedTile
	 */
	public static Tile createTile(final int tileCoordinate, final Piece piece) {

		return piece != null ? new OccupiedTile(tileCoordinate, piece) : EMPTY_TILES_CASH.get(tileCoordinate);

	}

	private Tile(final int tileCoordinate) {

		this.tileCoordintare = tileCoordinate;
	}

	public abstract boolean isTileOccupied();

	public abstract Piece getPiece();

	public static final class EmptyTile extends Tile {

		EmptyTile(int coordinate) {

			super(coordinate);
		}

		@Override
		public boolean isTileOccupied() {
			return false;
		}

		@Override
		public Piece getPiece() {
			return null;
		}
	}

	public static final class OccupiedTile extends Tile {

		private final Piece pieceOnTile;

		public OccupiedTile(int tileCoordinate, final Piece pieceOnTile) {
			super(tileCoordinate);
			this.pieceOnTile = pieceOnTile;
		}

		@Override
		public boolean isTileOccupied() {
			return true;
		}

		@Override
		public Piece getPiece() {
			return pieceOnTile;
		}

	}

}
