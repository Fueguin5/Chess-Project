# Java Chess Game

**Desktop chess application implemented in Java using Swing**

---

## Overview

This project is a fully playable two-player chess game implemented in Java with a graphical user interface built using Swing. The application enforces standard chess rules, including legal move validation, check detection, checkmate detection, castling, and pawn promotion.

The project was originally developed as a collaborative assignment for a Java programming course. There was a bug in the castling function that was left for years after the course concluded, but has now been fixed to make this project suitable as a **portfolio project**.

---

## Features

- Complete 8×8 chess board with graphical interface
- Turn-based two-player gameplay (local)
- Legal move validation for all piece types
- Check and checkmate detection
- King-side and queen-side castling
- Pawn promotion with user selection
- Move logging with algebraic-style notation
- Visual highlighting of selected pieces and valid moves
- Board color settings panel

---

## My Role

- Co-developer on a two-person team for the original course project
- Implemented the core `ChessPanel` class, including:
  - Construction of the 8×8 board using a grid of interactive buttons
  - Initial placement and rendering of chess piece sprites
  - Event-driven piece selection and movement handling
- Implemented and refined move validation logic, including:
  - Preventing pieces from jumping over others where disallowed
  - Preventing captures of same-color pieces
  - Enforcing turn-based play between white and black
  - Enhancing opponent-detection logic to safely handle empty board positions
- Implemented visual and interaction improvements:
  - Chessboard-style tile coloring
  - Highlighting all valid moves when a piece is selected
  - Improving selection logic to prevent invalid interaction states
- Implemented checkmate detection by evaluating all possible opponent moves
  after each turn and terminating the game when no legal escape exists
- Designed and implemented UI features beyond basic gameplay:
  - Main menu and game startup flow
  - Color settings panel with live-updating board colors
  - Pawn promotion sidebar allowing user selection of promotion pieces
- Assisted in debugging and integrating rule-enforcement logic, including
  validation for moves that would place a king in check
- Post-course refinements for portfolio use:
  - Corrected castling validation logic and interaction flow
  - Fixed edge cases in move handling
  - Refactored resource loading to support classpath-based assets
  - Packaged the project as a standalone, runnable JAR

---

## Technical Details

| Area | Technology |
|------|------------|
| Language | Java |
| UI Framework | Swing (JFrame, JPanel, JButton) |
| Architecture | Event-driven GUI with centralized game-state management |
| Assets | PNG-based piece sprites loaded via classpath resources |
| Platform | Desktop (cross-platform via JVM) |

---

## Controls & Gameplay

- Click a piece to select it
- Valid moves are highlighted on the board
- Click a highlighted square to make a move
- Pawn promotion prompts the player to select a new piece
- The game automatically detects check and checkmate conditions

---

## Demo

- to be added

---

## Running the Game

```bash
java -jar ChessGame.jar
