import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import javax.swing.border.*;
// 2023-11398 윤석민

//======================================================Don't modify below===============================================================//
enum PieceType {king, queen, bishop, knight, rook, pawn, none}
enum PlayerColor {black, white, none}
	
public class ChessBoard {
	private final JPanel gui = new JPanel(new BorderLayout(3, 3));
	private JPanel chessBoard;
	private JButton[][] chessBoardSquares = new JButton[8][8];
	private Piece[][] chessBoardStatus = new Piece[8][8];
	private ImageIcon[] pieceImage_b = new ImageIcon[7];
	private ImageIcon[] pieceImage_w = new ImageIcon[7];
	private JLabel message = new JLabel("Enter Reset to Start");

	ChessBoard(){
		initPieceImages();
		initBoardStatus();
		initializeGui();
	}
	
	public final void initBoardStatus(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) chessBoardStatus[j][i] = new Piece();
		}
	}
	
	public final void initPieceImages(){
		pieceImage_b[0] = new ImageIcon(new ImageIcon("./img/king_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[1] = new ImageIcon(new ImageIcon("./img/queen_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[2] = new ImageIcon(new ImageIcon("./img/bishop_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[3] = new ImageIcon(new ImageIcon("./img/knight_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[4] = new ImageIcon(new ImageIcon("./img/rook_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[5] = new ImageIcon(new ImageIcon("./img/pawn_b.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_b[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
		
		pieceImage_w[0] = new ImageIcon(new ImageIcon("./img/king_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[1] = new ImageIcon(new ImageIcon("./img/queen_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[2] = new ImageIcon(new ImageIcon("./img/bishop_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[3] = new ImageIcon(new ImageIcon("./img/knight_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[4] = new ImageIcon(new ImageIcon("./img/rook_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[5] = new ImageIcon(new ImageIcon("./img/pawn_w.png").getImage().getScaledInstance(64, 64, java.awt.Image.SCALE_SMOOTH));
		pieceImage_w[6] = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	}
	
	public ImageIcon getImageIcon(Piece piece){
		if(piece.color.equals(PlayerColor.black)){
			if(piece.type.equals(PieceType.king)) return pieceImage_b[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_b[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_b[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_b[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_b[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_b[5];
			else return pieceImage_b[6];
		}
		else if(piece.color.equals(PlayerColor.white)){
			if(piece.type.equals(PieceType.king)) return pieceImage_w[0];
			else if(piece.type.equals(PieceType.queen)) return pieceImage_w[1];
			else if(piece.type.equals(PieceType.bishop)) return pieceImage_w[2];
			else if(piece.type.equals(PieceType.knight)) return pieceImage_w[3];
			else if(piece.type.equals(PieceType.rook)) return pieceImage_w[4];
			else if(piece.type.equals(PieceType.pawn)) return pieceImage_w[5];
			else return pieceImage_w[6];
		}
		else return pieceImage_w[6];
	}

	public final void initializeGui(){
		gui.setBorder(new EmptyBorder(5, 5, 5, 5));
	    JToolBar tools = new JToolBar();
	    tools.setFloatable(false);
	    gui.add(tools, BorderLayout.PAGE_START);
	    JButton startButton = new JButton("Reset");
	    startButton.addActionListener(new ActionListener(){
	    	public void actionPerformed(ActionEvent e){
	    		initiateBoard();
	    	}
	    });
	    
	    tools.add(startButton);
	    tools.addSeparator();
	    tools.add(message);

	    chessBoard = new JPanel(new GridLayout(0, 8));
	    chessBoard.setBorder(new LineBorder(Color.BLACK));
	    gui.add(chessBoard);
	    ImageIcon defaultIcon = new ImageIcon(new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB));
	    Insets buttonMargin = new Insets(0,0,0,0);
	    for(int i=0; i<chessBoardSquares.length; i++) {
	        for (int j = 0; j < chessBoardSquares[i].length; j++) {
	        	JButton b = new JButton();
	        	b.addActionListener(new ButtonListener(i, j));
	            b.setMargin(buttonMargin);
	            b.setIcon(defaultIcon);
	            if((j % 2 == 1 && i % 2 == 1)|| (j % 2 == 0 && i % 2 == 0)) b.setBackground(Color.WHITE);
	            else b.setBackground(Color.gray);
	            b.setOpaque(true);
	            b.setBorderPainted(false);
	            chessBoardSquares[j][i] = b;
	        }
	    }

	    for (int i=0; i < 8; i++) {
	        for (int j=0; j < 8; j++) chessBoard.add(chessBoardSquares[j][i]);
	        
	    }
	}

	public final JComponent getGui() {
	    return gui;
	}
	
	public static void main(String[] args) {
	    Runnable r = new Runnable() {
	        @Override
	        public void run() {
	        	ChessBoard cb = new ChessBoard();
                JFrame f = new JFrame("Chess");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);
                f.setResizable(false);
                f.pack();
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
            }
        };
        SwingUtilities.invokeLater(r);
	}
		
			//================================Utilize these functions========================================//	
	
	class Piece{
		PlayerColor color;
		PieceType type;
		
		Piece(){
			color = PlayerColor.none;
			type = PieceType.none;
		}
		Piece(PlayerColor color, PieceType type){
			this.color = color;
			this.type = type;
		}
	}
	
	public void setIcon(int x, int y, Piece piece){
		chessBoardSquares[y][x].setIcon(getImageIcon(piece));
		chessBoardStatus[y][x] = piece;
	}
	
	public Piece getIcon(int x, int y){
		return chessBoardStatus[y][x];
	}
	
	public void markPosition(int x, int y){
		chessBoardSquares[y][x].setBackground(Color.pink);
	}
	
	public void unmarkPosition(int x, int y){
		if((y % 2 == 1 && x % 2 == 1)|| (y % 2 == 0 && x % 2 == 0)) chessBoardSquares[y][x].setBackground(Color.WHITE);
		else chessBoardSquares[y][x].setBackground(Color.gray);
	}
	
	public void setStatus(String inpt){
		message.setText(inpt);
	}
	
	public void initiateBoard(){
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) setIcon(i, j, new Piece());
		}
		setIcon(0, 0, new Piece(PlayerColor.black, PieceType.rook));
		setIcon(0, 1, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 2, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 3, new Piece(PlayerColor.black, PieceType.queen));
		setIcon(0, 4, new Piece(PlayerColor.black, PieceType.king));
		setIcon(0, 5, new Piece(PlayerColor.black, PieceType.bishop));
		setIcon(0, 6, new Piece(PlayerColor.black, PieceType.knight));
		setIcon(0, 7, new Piece(PlayerColor.black, PieceType.rook));
		for(int i=0;i<8;i++){
			setIcon(1, i, new Piece(PlayerColor.black, PieceType.pawn));
			setIcon(6, i, new Piece(PlayerColor.white, PieceType.pawn));
		}
		setIcon(7, 0, new Piece(PlayerColor.white, PieceType.rook));
		setIcon(7, 1, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 2, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 3, new Piece(PlayerColor.white, PieceType.queen));
		setIcon(7, 4, new Piece(PlayerColor.white, PieceType.king));
		setIcon(7, 5, new Piece(PlayerColor.white, PieceType.bishop));
		setIcon(7, 6, new Piece(PlayerColor.white, PieceType.knight));
		setIcon(7, 7, new Piece(PlayerColor.white, PieceType.rook));
		for(int i=0;i<8;i++){
			for(int j=0;j<8;j++) unmarkPosition(i, j);
		}
		onInitiateBoard();
	}
//======================================================Don't modify above==============================================================//	




//======================================================Implement below=================================================================//		
	enum MagicType {MARK, CHECK, CHECKMATE};
	private int selX, selY;
	private boolean check, checkmate, end;
	private int enPassant;
	private boolean enCastleblack, enCastlewhite;

	PlayerColor turn = PlayerColor.black;
	String checkmessage = "";
	
	class ButtonListener implements ActionListener {
		int x;
		int y;

		ButtonListener(int x, int y) {
			this.x = x;
			this.y = y;
		}

		public void actionPerformed(ActionEvent e) {    // Only modify here
			// (x, y) is where the click event occured
			if (!end) {
				if (getIcon(x, y).color == turn) {
					if (getIcon(selX,selY).type == PieceType.king && getIcon(x,y).type==PieceType.rook) {
						// left castling possible
						if (enableCastlingleft(selX,selY,getIcon(selX,selY)) && y==selY-4
								&&((enCastleblack && getIcon(selX,selY).color==PlayerColor.black)
								||(enCastlewhite && getIcon(selX,selY).color==PlayerColor.white))){
							setIcon(selX,selY-1, getIcon(selX,selY-4));
							setIcon(selX,selY-2, getIcon(selX,selY));

							setIcon(selX,selY-4, new Piece());
							setIcon(selX,selY,new Piece());

							if (getIcon(selX,selY).color == PlayerColor.white){
								enCastlewhite = false;
							}
							else {
								enCastleblack = false;
							}

							if (turn == PlayerColor.black){
								turn = PlayerColor.white;
							} else {
								turn = PlayerColor.black;
							}
							checkmessage = "";

							if (turn == PlayerColor.black){
								setStatus("BLACK's TURN" + checkmessage);
							} else {
								setStatus("WHITE's TURN" + checkmessage);
							}
						}

						// right castling possible
						else if (enableCastlingright(selX,selY,getIcon(selX,selY)) && y==selY+3
								&& ((enCastleblack && getIcon(selX,selY).color==PlayerColor.black)
								||(enCastlewhite && getIcon(selX,selY).color==PlayerColor.white))) {
							setIcon(selX,selY+1, getIcon(selX, selY+3));
							setIcon(selX,selY+2, getIcon(selX,selY));

							setIcon(selX,selY+3, new Piece());
							setIcon(selX,selY,new Piece());

							if (getIcon(selX,selY).color == PlayerColor.white){
								enCastlewhite = false;
							}
							else {
								enCastleblack = false;
							}

							if (turn == PlayerColor.black){
								turn = PlayerColor.white;
							} else {
								turn = PlayerColor.black;
							}
							checkmessage = "";

							if (turn == PlayerColor.black){
								setStatus("BLACK's TURN" + checkmessage);
							} else {
								setStatus("WHITE's TURN" + checkmessage);
							}
						}

						else {
							unmark();
							possibleMovement(x,y,getIcon(x,y));
						}

					}
					else {
						unmark();
						possibleMovement(x,y,getIcon(x,y));
					}
				}

				// click the possible movement block
				// selX, selY is the piece's location that I clicked
				else if (chessBoardSquares[y][x].getBackground() == Color.pink) {
					if (getIcon(x,y).type == PieceType.king){
						end = true;
					}

					// check the promotion
					isPromotion(x, y, getIcon(selX,selY));

					// move the piece
					setIcon(x,y,getIcon(selX,selY));

					// check the king or rook move
					moveKingRook(getIcon(selX,selY));

					// check the en passant
					isEnpassant(y,selX,selY);

					// check that pawn moves 2 blocks
					isPawnAd(x,y,selX,selY);

					// remove the piece
					setIcon(selX,selY,new Piece());
					unmark();

					if(end){
						if (turn == PlayerColor.black){
							setStatus("BLACK WON!");
						}
						else {
							setStatus("WHITE WON!");
						}
						return;
					}

					if (turn == PlayerColor.black){
						turn = PlayerColor.white;
					} else {
						turn = PlayerColor.black;
					}
					checkmessage = "";

					// in the checkmate statement
					if (checkStatus(turn)){
						checkmessage = " / CHECK";
						if (checkmateStatus(turn)) {
							checkmessage = " / CHECKMATE";
							end = true;
						}
					}

					if (turn == PlayerColor.black){
						setStatus("BLACK's TURN" + checkmessage);
					} else {
						setStatus("WHITE's TURN" + checkmessage);
					}
				}

				else {
					unmark();
				}

				selX = x;
				selY = y;
			}


		}

		public void possibleMovement(int x, int y, Piece piece){
			unmark();
			// pawn move
			if(piece.type == PieceType.pawn){
				pawnMovement(x, y, piece);
			}
			// knight move
			else if(piece.type == PieceType.knight){
				knightMovement(x, y, piece);
			}
			// bishop move
			else if(piece.type == PieceType.bishop){
				bishopMovement(x, y, piece);
			}
			// rook move
			else if(piece.type == PieceType.rook){
				rookMovement(x, y, piece);
			}
			// queen move
			else if(piece.type == PieceType.queen){
				bishopMovement(x, y, piece);
				rookMovement(x, y, piece);
			}
			// king move
			else if(piece.type == PieceType.king){
				kingMovement(x, y, piece);
			}

		}

		public void pawnMovement(int x, int y, Piece piece){
			if(piece.color== PlayerColor.white){
				if(legalMovement(x-1, 1)){
					if(getIcon(x-1, y).type == PieceType.none) {
						markPosition(x-1, y);
						if(x == 6 && getIcon(x-2, y).type == PieceType.none){
							markPosition(x-2, y);
						}
					}
					// capture left & enPassant left
					if(legalMovement(x-1, y-1)){
						if(getIcon(x-1, y-1).color == PlayerColor.black){
							markPosition(x-1, y-1);
						}

						if(x==3 && y==enPassant + 1){
							markPosition(x-1, y-1);
						}
					}
					// capture right & enPassant right
					if(legalMovement(x-1, y+1)){
						if(getIcon(x-1, y+1).color == PlayerColor.black){
							markPosition(x-1, y+1);
						}

						if(x==3 && y==enPassant - 1){
							markPosition(x-1, y+1);
						}
					}
				}
			}

			else {
				if(legalMovement(x+1, 1)){
					if(getIcon(x+1, y).type == PieceType.none){
						markPosition(x+1, y);
						if(x == 1 && getIcon(x+2, y).type == PieceType.none){
							markPosition(x+2, y);
						}
					}
				}

				// capture left & enPassant left
				if(legalMovement(x+1, y-1)){
					if(getIcon(x+1, y-1).color == PlayerColor.white){
						markPosition(x+1, y-1);
					}

					if(x==4 && y==enPassant + 1) {
						markPosition(x+1, y-1);
					}
				}

				// capture right & enPassant right
				if(legalMovement(x+1, y+1)){
					if(getIcon(x+1, y+1).color == PlayerColor.white){
						markPosition(x+1, y+1);
					}

					if(x == 4 && y == enPassant - 1) {
						markPosition(x+1, y+1);
					}
				}

			}
		}

		public void knightMovement(int x, int y, Piece piece){
			int[] fst = {1, -1};
			int[] snd = {2, -2};
			for (int i = 0; i < 2; i++) {
				for (int j = 0; j < 2; j++) {
					if(legalMovement(x+fst[i], y+snd[j])){
						if (getIcon(x + fst[i], y + snd[j]).color != piece.color)
							markPosition(x + fst[i], y + snd[j]);
					}

					if(legalMovement(x+snd[i], y+fst[j])){
						if (getIcon(x + snd[i], y + fst[j]).color != piece.color)
							markPosition(x + snd[i], y + fst[j]);
					}
				}
			}
		}

		public void kingMovement(int x, int y, Piece piece){
			if	((enCastleblack && getIcon(x,y).color==PlayerColor.black)
				||(enCastlewhite && getIcon(x,y).color==PlayerColor.white)){
				enableCastlingleft(x,y,piece);
				enableCastlingright(x,y,piece);
			}

			for (int i = 0; i < 3; i++) {
				for (int j = 0; j < 3; j++) {
					int new_x = x+i-1;
					int new_y = y+j-1;
					if (legalMovement(new_x, new_y)){
						if (getIcon(new_x, new_y).type != PieceType.king && getIcon(new_x, new_y).color != piece.color)
							markPosition(new_x, new_y);
					}
				}
			}
		}

		public void bishopMovement(int x, int y, Piece piece){
			ObstacleCheck(x, y, 1, 1, piece.color);
			ObstacleCheck(x, y, 1, -1, piece.color);
			ObstacleCheck(x, y, -1, 1, piece.color);
			ObstacleCheck(x, y, -1, -1, piece.color);
		}

		public void rookMovement(int x, int y, Piece piece){
			ObstacleCheck(x, y, 1, 0, piece.color);
			ObstacleCheck(x, y, -1, 0, piece.color);
			ObstacleCheck(x, y, 0, 1, piece.color);
			ObstacleCheck(x, y, 0, -1, piece.color);
		}

		public void ObstacleCheck(int x, int y, int dirX, int dirY, PlayerColor c){
			int unitX = dirX;
			int unitY = dirY;
			PlayerColor opponent;
			opponent = turnChange(c);

			while (legalMovement(x+dirX, y+dirY)){
				int newX = x+dirX;
				int newY = y+dirY;

				if(getIcon(newX, newY).color == opponent) {
					markPosition(newX, newY);
					break;
				}

				else if(getIcon(newX, newY).color == c){
					break;
				}

				markPosition(newX, newY);
				dirX += unitX;
				dirY += unitY;
			}
		}

		public PlayerColor turnChange(PlayerColor p1){
			if(p1 == PlayerColor.white){
				return PlayerColor.black;
			}

			else if(p1 == PlayerColor.black){
				return PlayerColor.white;
			}

			return null;
		}

		public boolean checkStatus(PlayerColor color){
			unmark();
			int x = 0;
			int y = 0;

			// find the king's position
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					if (chessBoardStatus[j][i].type == PieceType.king && chessBoardStatus[j][i].color == color) {
						x = i;
						y = j;
					}
				}
			}

			PlayerColor opponent;
			if(turn == PlayerColor.black){
				opponent = PlayerColor.white;
			}
			else{
				opponent = PlayerColor.black;
			}

			// find the attackable block of opponent piece
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					if (chessBoardStatus[j][i].color == opponent) {
						possibleMovement(i, j, getIcon(i, j));

						if(chessBoardSquares[y][x].getBackground()==Color.pink){
							unmark();
							check = true;
							return true;
						}

					}
				}
			}

			unmark();
			check = false;
			return false;
		}

		public boolean checkmateStatus(PlayerColor color){
			for(int i = 0; i <= 7; i++){
				for(int j = 0; j <= 7; j++){
					if(chessBoardStatus[j][i].color == color){
						Piece old = getIcon(i, j);
						possibleMovement(i, j, old);

						for(int k = 0; k<= 7; k++){
							for(int l = 0; l <=7; l++){
								if(chessBoardSquares[l][k].getBackground() == Color.pink){
									Piece opponent = getIcon(k, l);
									setIcon(k, l, old);
									setIcon(i, j, new Piece());

									if(!checkStatus(color)){
										setIcon(i, j, old);
										setIcon(k, l, opponent);
										checkmate = false;
										return false;
									}

									setIcon(i, j, old);
									setIcon(k, l, opponent);
									possibleMovement(i, j, old);
								}
							}
						}

						unmark();
					}
				}
			}

			checkmate = true;
			return true;
		}

		public void isPawnAd(int x, int y, int selX, int selY){
			if (getIcon(selX, selY).type == PieceType.pawn){
				if (selX==6 && x==4){
					enPassant = y;
				}
				else if (selX==1 && x==3) {
					enPassant = y;
				}
				else {
					enPassant = -1;
				}
			}
		}

		public void isEnpassant(int y, int selX, int selY){
			if (getIcon(selX, selY).type == PieceType.pawn){
				if (selX==3 && y==enPassant && getIcon(selX,selY).color == PlayerColor.white){
					setIcon(selX,enPassant,new Piece());
				}

				else if (selX==4 && y==enPassant && getIcon(selX,selY).color == PlayerColor.black) {
					setIcon(selX,enPassant,new Piece());
				}

				else {
					return;
				}
			}
		}

		public void isPromotion(int x, int y, Piece piece){
			if (piece.type == PieceType.pawn){
				if (x==0 && piece.color == PlayerColor.white){
					piece.type = PieceType.queen;
				}

				else if (x==7 && piece.color == PlayerColor.black){
					piece.type = PieceType.queen;

				}

				else {
					return;
				}
			}
		}

		public void moveKingRook(Piece piece){
			if (piece.type == PieceType.king || piece.type == PieceType.rook) {
				if (piece.color == PlayerColor.black) {
					enCastleblack = false;
				} else if (piece.color == PlayerColor.white) {
					enCastlewhite = false;
				}
			}
		}

		public boolean enableCastlingleft(int x, int y, Piece piece){
			if (!check){
				for (int i = 1; i < 4; i++) {
					if (getIcon(x, y - i).type != PieceType.none) {
						break;
					}
					if (i == 3 && enLanding(x, y - 2)) {
						if ((enCastlewhite && piece.color == PlayerColor.white)||(enCastleblack && piece.color == PlayerColor.black)){
							return true;
						}
					}
				}
			}
			return false;
		}

		public boolean enableCastlingright(int x, int y, Piece piece){
			if (!check && y==4){
				for (int i = 1; i < 3; i++) {
					if (getIcon(x, y + i).type != PieceType.none) {
						break;
					}
					if (i == 2 && enLanding(x, y + 2)){
						if ((enCastlewhite && piece.color == PlayerColor.white)||(enCastleblack && piece.color == PlayerColor.black)){
							return true;
						}
					}
				}
			}

			return false;
		}

		public boolean enLanding(int x, int y){
			unmark();
			PlayerColor opponent;
			if(turn == PlayerColor.black){
				opponent = PlayerColor.white;
			}
			else{
				opponent = PlayerColor.black;
			}

			// check that the king can land on (x, y)
			for (int i = 0; i <= 7; i++) {
				for (int j = 0; j <= 7; j++) {
					if (chessBoardStatus[j][i].color == opponent && j!=y && i!=x) {
						possibleMovement(i, j, getIcon(i, j));
						if(chessBoardSquares[y][x].getBackground()==Color.pink){
							unmark();
							return false;
						}
					}
				}
			}
			unmark();
			return true;
		}

		public void unmark(){
			for(int i=0;i<8;i++){
				for(int j=0;j<8;j++) {
					unmarkPosition(i, j);
				}
			}
		}

		public boolean legalMovement(int x, int y) {
			if (x > 7 || x < 0 || y > 7 || y < 0) {
				return false;
			}
			return true;
		}
	}
	void onInitiateBoard() {
		end = false;
		check = false;
		checkmate = false;

		enCastleblack = true;
		enCastlewhite = true;

		setStatus("BLACK's TURN");
		turn = PlayerColor.black;
		enPassant = -1;
	}
}