package algoritmo;

public class Aresta {
	
	// Variaveis
	int u; // Vértice de origem
	int v; // Vértice destino
	int fluxo; // Fluxo variável que passa pela aresta
	int capacidade; // Capacidade máxima da aresta

	//Construtor padrão
	public Aresta(int u , int v , int fluxo , int capacidade) {
		this.u = u;
		this.v = v;
		this.fluxo = fluxo;
		this.capacidade = capacidade;
	}
}
