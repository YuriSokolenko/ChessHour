package com.chess.gui;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Table {

	
	private final JFrame gameFrame;
	private static Dimension OUTER_FRAME_DIMENSION = new Dimension(600,	600);

	
	public Table() {
		this.gameFrame = new JFrame("Yuri Sokolenko CheckMate Chess");
		final JMenuBar tableMenuBar = new JMenuBar();
		populateMenuBar(tableMenuBar);
		this.gameFrame.setJMenuBar(tableMenuBar);
		this.gameFrame.setSize(OUTER_FRAME_DIMENSION);
		this.gameFrame.setVisible(true);
	}


	private void populateMenuBar(JMenuBar tableMenuBar) {
		tableMenuBar.add(createFileMenu());
	}


	private JMenu createFileMenu() {
		final JMenu fileMenu = new JMenu("File");
		final JMenuItem openPGN = new JMenuItem("Load PGN File");
		openPGN.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("open up that pgn file!");
			}
		});
		fileMenu.add(openPGN);
		return fileMenu;
	}
	
	
	
	
	
}
