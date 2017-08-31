import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import model.Propertie;
import player.Aleatorio;
import player.Cauteloso;
import player.Exigente;
import player.Impulsivo;
import player.Player;
import utils.Dice;
import utils.FileReader;

public class Game {

	private static final int BOARD_TOTAL_PIECES = 20;
	public static final int ROUND_MAX_COUNT = 1000;
	private static final int PAYMENT = 100;
	
	private List<String[]> mGameConfig;
	private Propertie[] mBoard = new Propertie[BOARD_TOTAL_PIECES];
	private List<Player> mPlayers;
	private int mNextPlayerIndex;
	private int mRoundCount;
	
	private Dice d6;
	
	/**
	 * Instancia o Game, podendo retornar erro caso haja algum problema na leitura do arquivo de 
	 * configuração.
	 * @throws Exception
	 */
	public Game() throws Exception {
		d6 = new Dice(6);
		mGameConfig = FileReader.readGameConfig();
		
		if(mGameConfig.isEmpty()) {
			throw new Exception("Arquivo gameConfig está vazio.");
		}
		if(mGameConfig.size() < BOARD_TOTAL_PIECES) {
			throw new Exception("Quantidade de propriedades no tabuleiro maior que quantidade "
					+ "de propriedades no gameConfig.txt");
		}
	}
	
	/**
	 * Inicia e executa uma nova partida de Bankrupt
	 * @return
	 */
	public Player startNewMatch() {
		repareForNewMatch();
		
		while(true) {
			Player player = getNextPlayer();
			int oldPos = player.getPosition();
			int newPos = player.move();
			
			//Se passou pelo começo recebe
			if(oldPos % BOARD_TOTAL_PIECES > newPos % BOARD_TOTAL_PIECES) {
				player.receivePayment(PAYMENT);
			}
			
			//Recupera a propriedade que o jogador está sobre
			Propertie overPropertie = mBoard[newPos % BOARD_TOTAL_PIECES];
			
			if(overPropertie.getOwner() == null) {
				//Se não não possui dono, oferecer compra
				boolean response = player.wantToBuy(overPropertie);
				
				if(response) {
					//Se o player realmente tem dinheiro pra comprar
					if(player.getCoins() >= overPropertie.getBuyCost()) {
						overPropertie.buy(player);
						player.deductValue(overPropertie.getBuyCost());
					}
				}
			} 
			else if(overPropertie.getOwner() != player) {
				//Se a propriedade possui dono e não é o jogador atual, cobrar o aluguel
				int rentValue = overPropertie.getRentCost();
				
				player.deductValue(rentValue);
				//Verifica se o jogador perdeu e calcula nesse caso quanto o outro
				//jogador deve receber (somente pagamos valores positivos)
				if(player.getCoins() < 0) {
					//Total a ser pago para o outro jogador
					rentValue += player.getCoins();
					playerLose(player);
				}
				
				//Paga o dono da propriedade
				overPropertie.getOwner().receivePayment(rentValue);
			}
			
			//Verifica condição de fim de jogo
			if(checkForEndGame()) {
				return getWinner();
			} else {
				//Próximo jogador
				endTurn(player);
			}
		}
	}
	
	/**
	 * Recupera o estado inicial de um jogo
	 */
	private void repareForNewMatch() {
		mRoundCount = 1;
		mNextPlayerIndex = 0;
		startBoard();
		initMatchOrder();
	}
	
	/**
	 * Preenche as propriedades com os valores lidos do gameConfig
	 */
	private void startBoard() {
		for(int index = 0; index < BOARD_TOTAL_PIECES; index++) {
			//TODO: alterar valores para os que foram lidos
			mBoard[index] = new Propertie(mGameConfig.get(index)[0], mGameConfig.get(index)[1]);
		}
	}
	
	/**
	 * Instancia e sorteia ordem dos jogadores
	 */
	private void initMatchOrder() {
		mPlayers = new ArrayList<Player>();
		mPlayers.add(new Impulsivo(500, d6));
		mPlayers.add(new Cauteloso(500, d6));
		mPlayers.add(new Exigente(500, d6));
		mPlayers.add(new Aleatorio(500, d6));
		
		//Ordena os players por iniciativa
		Collections.sort(mPlayers, new Comparator<Player>() {
			@Override
			public int compare(Player p1, Player p2) {
				if(p1.getInitiative() > p2.getInitiative()) {
					return 1;
				} else if(p1.getInitiative() < p2.getInitiative()) {
					return -1;
				} else {
					return 0;
				}
			}
		});
	}
	
	private Player getNextPlayer() {
		return mPlayers.get(mNextPlayerIndex);
	}
	
	/**
	 * Calcula o index do próximo jogador. A lista de players pode alterar de tamanho durante
	 * a partida, por esse motivo precisamos calcular esse index.
	 * @param player
	 */
	private void endTurn(Player player) {
		//Verifica se nesse turno o jogador perdeu
		if(player.getCoins() >= 0) {
			//Incrementa o turno
			mNextPlayerIndex++;	
		}
		if(mNextPlayerIndex == mPlayers.size()) {
			mNextPlayerIndex = 0;
			mRoundCount++;
		}
	}
	
	private boolean checkForEndGame() {
		if(mPlayers.size() == 1) {
			return true;
		} else if(mRoundCount == ROUND_MAX_COUNT) {
			return true;
		}
		return false;
	}
	
	/**
	 * Dada a condição de fim de jogo verifica quem venceu
	 * @return o vencedor
	 */
	private Player getWinner() {
		if(mPlayers.size() == 1) {
			return mPlayers.get(0);
		} else {
			Player winner = null;
			for(Player player : mPlayers) {
				if(winner != null) {
					if(player.getCoins() > winner.getCoins()) {
						winner = player;
					}
				} else {
					//Seta o primeiro como winner
					winner = player;
				}
			}
			return winner;
		}
	}
	
	/**
	 * Remove propriedades que o player possuia e remover o mesmo da lista de players jogando
	 */
	private void playerLose(Player player) {
		//Libera propriedades que o player era dono
		for(Propertie propertie : mBoard) {
			if(propertie.getOwner() == player) {
				propertie.removeOwner();
			}
		}
		mPlayers.remove(player);
	}
	
	public int getRoundCount() {
		return mRoundCount;
	}
}
