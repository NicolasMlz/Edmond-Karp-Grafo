package algoritmo;

import java.util.ArrayList;
import java.util.Scanner;

public class EdmondKarp {
	public static void main(String[] args) {
		
		//Variaveis
		Scanner scan = new Scanner(System.in);
		int vertices = scan.nextInt();
		int arestas = scan.nextInt();
		int fonte = 0;
		int fim = vertices - 1;

		//Grafo como vetor de vertices
		Vertice[] grafo = new Vertice[vertices];

		// Inicializar cada vertice
		for (int i = 0; i < vertices; i++)
			grafo[i] = new Vertice();

		// Initializar cada aresta
		for (int i = 0; i < arestas; i++) {
			
			//Scanear variaveis
			int u = scan.nextInt();
			int v = scan.nextInt();
			int c = scan.nextInt();

			// NOTE QUE ARESTA B, NÃO EST INICIALMENTE NO GRAFO, MAS AJUDAM A RESOLVER O PROBLEMA
			// Aresta de ida do fluxo
			Aresta a = new Aresta(u , v , 0 , c);
			// Aresta de volta (retirada de fluxo)
			Aresta b = new Aresta(v , u , 0 , 0);
			
			// Definir que a aresta de volta de "a" é "b" e vice-versa 
			a.setReverso(b);
			b.setReverso(a);
			
			// Adicionar arestas no grafo
			grafo[u].arestas.add(a);
			grafo[v].arestas.add(b);
		}
		
		//Inicializar fluxo maximo
		int fluxoMaximo = 0;
		
		while (true) {

			// Array pai usado para armazenar o caminho
			// (caminhoAtual[i] armazena a aresta usada para chegar no vertice i)
			Aresta[] caminhoAtual = new Aresta[vertices];
			ArrayList<Vertice> q = new ArrayList<>();
			q.add(grafo[fonte]);
			
			// BFS encontrando o menor fluxo de aumento
			while (!q.isEmpty()) {
				Vertice curr = q.remove(0);
				
				// Verifica se a aresta ainda não foi visitada e caso não,
				// aponta para a fonte, e é possível enviar o fluxo através dela
				for (Aresta e : curr.arestas)
					if (caminhoAtual[e.v] == null && e.v != fonte && e.capacidade > e.fluxo) {
						caminhoAtual[e.v] = e;
						q.add(grafo[e.v]);
					}
			}
				
			// Se fim não foi alcançado, nenhum caminho de aumento será encontrado
			// O algoritmo termina e printa o fluxo máximo
			if (caminhoAtual[fim] == null)
				break;
			
			// Se o fim foi alcançado, nós vamos aumentar o fluxo ao longo do caminho
			int fluxoCorrente = Integer.MAX_VALUE;
			
			// Encontra o fluxo máximo que pode ser transportado para um determinado caminho 
			// encontrando o fluxo residual mínimo de cada aresta no caminho
			for (Aresta e = caminhoAtual[fim]; e != null; e = caminhoAtual[e.u])
				fluxoCorrente = Math.min(fluxoCorrente , e.capacidade - e.fluxo);
			
			// Adiciona valores de fluxo e subtrai valores de fluxo reverso no caminho
			for (Aresta e = caminhoAtual[fim]; e != null; e = caminhoAtual[e.u]) {
				e.fluxo += fluxoCorrente;
				e.reverse.fluxo -= fluxoCorrente;
			}
			
			fluxoMaximo += fluxoCorrente;
		}

		// Saida do algoritmo
		System.out.println("Fluxo máximo: " + fluxoMaximo);
		scan.close();
	}
}