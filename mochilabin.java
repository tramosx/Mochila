import java.util.Random;

//import java.util.*;

public class mochilabin {

		/**
	 * @param args
	 */
	static Random Rnd = new Random();
	static double[] Pesos;
	static double[] Valores;
	static String[] populacao;
	static double[] fitness;
	static Boolean run = false;

	public static void main(String[] args) {
		int n = 10 + Rnd.nextInt(15);
		Pesos = geraPesos(n);
		double max = maximoP(Pesos) + Rnd.nextDouble() * somaP(Pesos);
		//System.out.println("somaP: " + (int)somaP(Pesos));
		System.out.println("max: " + (int)max);
		Valores = geraValores(n);
		
		int p = 20;
		populacao = geraPop(p, n);
		int gen = 1;
		do {
			fitness = evalPop(populacao, Pesos, max, p, n);
			exibir(Pesos,Valores,populacao,fitness);
			printPopulationStats(gen, fitness);
			gen++;
			breed(populacao, fitness, p, n);
		} while (run);

	}

	private static double[] geraPesos(int n) {
		// generate a knapsack problem
		// at random
		double[] Pesos = new double[n];
		for (int i = 0; i < n; i++) {
			Pesos[i] = 1 + Rnd.nextDouble() * 50;
		}
		return Pesos;
	}
	
	
	private static double[] geraValores(int n){
		double[] valores = new double[n];
		for (int i = 0; i < n; i++) {
			valores[i] = 1 + Rnd.nextDouble() * 50;
		}
		return valores;
		
	}
	
	
	private static String[] geraPop(int p, int n) {
		// gera uma populacao randomicamente
		// n bits de strings
		// tamanho total da populacao
		// é p
		String[] pop = new String[p];
		for (int i = 0; i < p; i++) {
			pop[i] = geraGene(n);
		}
		return pop;
	}

	private static String geraGene(int n) {
		// make a random n bit
		StringBuilder g = new StringBuilder("");
		char c;
		for (int i = 0; i < n; i++) {
			c = '0';
			if (Rnd.nextDouble() > .5) {
				c = '1';
			}
			g.append(c);
		}
	return g.toString();
	}

	private static double eval(String g, double[] w, double max, int n) {
		// return fitness of g
		double tw = 0;
		for (int i = 0; i < n; i++) {
			if (g.charAt(i) == '1') {
				tw = tw + w[i];
			}
		}
		double e = 0;
		double d = max - tw;
		if (d >= 0) {
			e = tw / max * 100;
		
		}
		return e;
	}

	private static double[] evalPop(String[] pop, double[] w, double max,
			int p, int n) {
		// evaluate the fitness of all
		// members of the population
		double[] fit = new double[p];
		for (int i = 0; i < p; i++) {
			fit[i] = eval(pop[i], w, max, n);
		}
		return fit;
	}

	private static int select(double[] fitness) {
		// double totalFit = Soma(fitness);
		double totalFit = Soma(fitness);
		double r = Rnd.nextDouble() * totalFit;
	//	System.out.println("r: "  +r);

		int i = 0;
		double sum = 0;
		do {
			sum += fitness[i];
			i++;
		} while (sum < r);
		return --i;
	}

	private static String cross(String n1, String n2, int n) {
		int c = Rnd.nextInt(n);
		System.out.println("c " +c);
		return n1.substring(0, c) + n2.substring(c);
	}

	private static void breed(String[] population, double[] fitness, int p,int n){
		String[] breedPop = new String[p];
		for (int i = 0; i < p; i++) {
			int j = select(fitness);
			System.out.println("j: " +j);
			breedPop[i] = population[j];
		}
		for (int i = 0; i < p; i++) {
			System.out.println("breedpop: " +breedPop[i].toString());
		}
		
		
		for (int i = 0; i < p - 1; i++) {
			population[i] = cross(breedPop[i].toString(),breedPop[i + 1].toString(),n);
			System.out.println("population: " +population[i]);
		}
		population[p - 1] = cross(breedPop[0].toString(),
				breedPop[p - 1].toString(), n);
	}

	static void printPopulationStats(int gen, double[] fitness) {
		
		// double max = fitness.Max();
		double max = Maximo(fitness);
		// double totalfit = Suma(fitness);
		double totalfit = Soma(fitness);
		System.out.println("\n");
		System.out.println("Generation: " + gen);
		System.out.println(" Max Fit: " + (int)max + " Total Fitness:" + (int)totalfit);
	}

	private static double Maximo(double[] fitness) {
		double Maior;
		Maior = fitness[0];
		for (int i = 0; i < fitness.length; i++) {
			if (fitness[i] > Maior)
				Maior = fitness[i];
		}
		return Maior;
	}

	private static double maximoP(double[] Pesos) {
		double Maior;
		Maior = Pesos[0];
		for (int i = 0; i < Pesos.length; i++) {
			if (Pesos[i] > Maior)
				Maior = Pesos[i];
		}
		return Maior;
	}
	
	private static double somaP(double[] Pesos){
		double soma = 0;
		for(int i=0; i<Pesos.length; i++){
			soma = soma + Pesos[i];
		}
		return soma;	
			
	}
	
	private static double Soma(double[] fitness){
		double soma = 0;
		for(int i=0; i<fitness.length; i++){
			soma = soma + fitness[i];
		}
		return soma;	
			
	}
	
	
	
	static void exibir(double[] Pesos,double[] Valores,String[] population,double[] fitness){
		System.out.print("Pesos:  ");
		for(int i=0; i<Pesos.length; i++){
			System.out.print("  " +(int)Pesos[i]);
		}
				
		System.out.print(" Tamanho dos Pesos:" + Pesos.length);
		System.out.println("\n");
		
		System.out.print("Valores:");
		for(int i=0; i<Pesos.length; i++){
			System.out.print("  " +(int)Valores[i]);
		}
		System.out.print(" Tamanho dos Valores:" + Valores.length);
		System.out.println("\n");
		
		System.out.print("Fitness:");
		for(int i=0; i<fitness.length; i++){
			System.out.print("   " +(int)fitness[i]);
			
		}
		
		System.out.print(" tamanho do fitness:" + fitness.length);
	    System.out.println("\n");
		System.out.print("Populacao:");
		for(int i=0; i<population.length; i++){
			System.out.print(" " +population[i]);
		
		}
		System.out.print("tamanho da populacao:" + population.length);
	}
}
