package org.unibl.etf.mdp.library.services;

import java.util.Random;

import org.unibl.etf.mdp.library.services.interfaces.IRandomNumberGeneratorService;

public class RandomNumberGeneratorService implements IRandomNumberGeneratorService{

	private Random random = new Random();
	
	@Override
	public int getRandomInt(int min, int max) {
		int rand = random.nextInt(min, max);
		return rand;
	}

	@Override
	public int getRandomInt() {
		int rand = random.nextInt();
		return rand;
	}

}
