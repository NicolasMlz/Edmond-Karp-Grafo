package algoritmo;

import java.util.ArrayList;
import java.util.Scanner;

public class EdmondKarp {
	public static void main(String[] args) {
		
		//Variaveis
		Scanner scan = new Scanner(System.in);
		System.out.println("Digite a quantidade de vértices do grafo: ");
		int vertices = scan.nextInt();
//		System.out.print("Digite a quantidade de arestas do grafo: ");
		int arestas = scan.nextInt();
//		System.out.print("\n");
		
		int primeiroVertice = 0;
		int ultimoVertice = vertices - 1;

		//Grafo como vetor de vertices
		Vertice[] grafo = new Vertice[vertices];

		// Inicializar cada vertice isolado
		for (int i = 0; i < vertices; i++)
			grafo[i] = new Vertice();

		// Inicializar cada aresta com suas informações
		for (int i = 1; i <= arestas; i++) {
			
			//Scanear variaveis da aresta
//			System.out.print("Digite o vertice de partida da aresta " + i + ": ");
			int u = scan.nextInt(); u--;
//			System.out.print("Digite o vertice de chegada da aresta " + i + ": ");
			int v = scan.nextInt(); v--;
//			System.out.print("Digite a capacidade da aresta " + i + ": ");
			int c = scan.nextInt(); 
//			System.out.print("\n");

			// Note que a aresta b não está inicialmente no grafo, mas ajudará na execução
			// Aresta de ida do fluxo
			Aresta a = new Aresta(u , v , 0 , c);
			// Aresta de volta (retirada de fluxo)
			Aresta b = new Aresta(v , u , 0 , 0);
			
			// Adicionar arestas no grafo ligada aos vértices corretos
			grafo[u].arestas.add(a);
			grafo[v].arestas.add(b);
		}
		
		//Inicializar fluxo maximo como 0
		int fluxoMaximo = 0;
		
		while (true) {

			// Vetor que armazena as arestas utilizadas para chegar até o vértice final
			Aresta[] caminhoAtual = new Aresta[vertices];
			
			// Lista de vértices que forem sendo acessados pelo fluxo
			ArrayList<Vertice> q = new ArrayList<>();
			q.add(grafo[primeiroVertice]);
			
			// Busca em largura até achar um caminho qualquer que leve até o fim
			while (!q.isEmpty()) {
				
				// Desenfileirar a o elemento pois foi visitado
				Vertice verticeAtual = q.remove(0);
				
				// Verifica se a aresta ainda não foi visitada e caso não,
				// aponta para o próximo vértice, e é possível enviar o fluxo através dele
				for (Aresta e : verticeAtual.arestas) {
					if (caminhoAtual[e.v] == null && e.v != primeiroVertice && e.capacidade > e.fluxo) {
						
						// Adicionar no caminho atual as arestas, até que o vértice final seja encontrado
						caminhoAtual[e.v] = e;
						
						// Acessar próximo vértice (em largura)
						q.add(grafo[e.v]);
					}
				}
				
			}
			
			// Se o fim não foi alcançado, nenhum caminho de aumento será encontrado
			// O algoritmo termina e printa o fluxo máximo
			if (caminhoAtual[ultimoVertice] == null) 
				break;
			
			// Se o fim foi alcançado, nós vamos aumentar o fluxo ao longo do caminho até 
			// sua capacidade máxima
			int fluxoCorrente = Integer.MAX_VALUE;
			
			// Encontra o fluxo máximo que pode ser transportado para um determinado caminho 
			// encontrando o gargalo mínimo desse caminho
			for (Aresta e = caminhoAtual[ultimoVertice]; e != null; e = caminhoAtual[e.u])
				fluxoCorrente = Math.min(fluxoCorrente , e.capacidade - e.fluxo);
			
			// Adiciona valores de fluxo e subtrai valores de fluxo reverso no caminho
			for (Aresta e = caminhoAtual[ultimoVertice]; e != null; e = caminhoAtual[e.u]) {
				e.fluxo += fluxoCorrente;
			}
			
			
			fluxoMaximo += fluxoCorrente;
		}
		
		// Saida do algoritmo
		System.out.println("------------------");
		System.out.println("Fluxo máximo: " + fluxoMaximo);
		scan.close();
	}
}