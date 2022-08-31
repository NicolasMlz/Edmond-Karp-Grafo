package algoritmo;

public class Aresta {
	
	//Variaveis
	int u , v , fluxo , capacidade;
	Aresta reverse;

	//Construtor
	public Aresta(int u , int v , int fluxo , int capacidade) {
		this.u = u;
		this.v = v;
		this.fluxo = fluxo;
		this.capacidade = capacidade;
	}

	//Definir fluxos reversos
	public void setReverso(Aresta e) {
		reverse = e;
	}
}
