package utils;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.List;

public class FileReader {
	
	/**
	 * Lê um arquivo de gameConfig para o jogo Bankrupt
	 * Esse arquivo consiste em duplas de valor separadas por " ", podendo conter mais de 
	 * uma linha.
	 * @return list de array de strings contendo a dupla de valores por linha.
	 * @throws Exception
	 */
	public static List<String[]> readGameConfig() throws Exception {
		List<String[]> gameConfig = new ArrayList<>();
		
		try {
			BufferedReader buffer = new BufferedReader(new java.io.FileReader("gameConfig.txt"));
			String line = buffer.readLine();

			while(line != null) {
				String[] values = line.split(" ");
				gameConfig.add(values);
				line = buffer.readLine();
			}
			buffer.close();
		} catch(Exception e) {
			throw new Exception("Erro ao ler o arquivo de configurações do jogo, por favor, verificar"
					+ "se o mesmo existe e se está no formato adequado");
		}
		return gameConfig;
	}
}
