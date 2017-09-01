package main;

import model.player.*;

public class Main {

	private static final int TOTAL_GAMES = 300;
	private static final String WIN_COUNT_TEXT = "Total vitórias comportamento %s: %o vitórias ou %.2f porcento das partidas.";
	
	private static int roundsSum;
	private static int matchsEndedByTimeout = 0;
	private static int playerAleatorioWinCount = 0;
	private static int playerCautelosoWinCount = 0;
	private static int playerExigenteWinCount = 0;
	private static int playerImpulsivoWinCount = 0;
	
	public static void main(String[] args) {
		
		Player playerWithMostWins = null;
		int winsHighestCount = 0;
		
		Game game;
		try {
			game = new Game();
		} catch(Exception e) {
			e.printStackTrace();
			return;
		}
		
		for(int i = 0; i < 300; i++) {
			Player winner = game.startNewMatch();
			
			//Contagem de vitórias
			if(winner instanceof Aleatorio) {
				playerAleatorioWinCount++;
				
				if(winsHighestCount < playerAleatorioWinCount) {
					winsHighestCount = playerAleatorioWinCount;
					playerWithMostWins = winner;
				}
			} else if(winner instanceof Cauteloso) {
				playerCautelosoWinCount++;
				
				if(winsHighestCount < playerCautelosoWinCount) {
					winsHighestCount = playerCautelosoWinCount;
					playerWithMostWins = winner;
				}
			} else if(winner instanceof Exigente) {
				playerExigenteWinCount++;
				
				if(winsHighestCount < playerExigenteWinCount) {
					winsHighestCount = playerExigenteWinCount;
					playerWithMostWins = winner;
				}
			} else {
				playerImpulsivoWinCount++;
				
				if(winsHighestCount < playerImpulsivoWinCount) {
					winsHighestCount = playerImpulsivoWinCount;
					playerWithMostWins = winner;
				}
			}
			
			//Contagem de partidas que terminaram por timeout
			if(game.getRoundCount() == Game.ROUND_MAX_COUNT) {
				matchsEndedByTimeout++;
			}
			
			roundsSum += game.getRoundCount();
		}
		

		String aleatorioWinCountText = String.format(WIN_COUNT_TEXT,
				Aleatorio.NAME,
				playerAleatorioWinCount,
				(float)playerAleatorioWinCount/300*100);
		String cautelosoWinCountText = String.format(WIN_COUNT_TEXT,
				Cauteloso.NAME,
				playerCautelosoWinCount,
				(float)playerCautelosoWinCount/300*100);
		String exigenteWinCountText = String.format(WIN_COUNT_TEXT,
				Exigente.NAME,
				playerExigenteWinCount,
				(float)playerExigenteWinCount/300*100);
		String impulsivoWinCountText = String.format(WIN_COUNT_TEXT,
				Impulsivo.NAME,
				playerImpulsivoWinCount,
				(float)playerImpulsivoWinCount/300*100);
		System.out.println(aleatorioWinCountText);
		System.out.println(cautelosoWinCountText);
		System.out.println(exigenteWinCountText);
		System.out.println(impulsivoWinCountText);
		System.out.println("Média de rodadas em 300 partidas: " + roundsSum/TOTAL_GAMES);
		System.out.println("Quantidade de partidas que terminaram por timeout: " + matchsEndedByTimeout);
		System.out.println("Comportamento com maior número de vitórias " + playerWithMostWins.getName());
	}

}
