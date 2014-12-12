package nl.esi.metis.testgenerator;

import java.util.Random;


public class NameSetterImpl implements NameSetter {
	private static final String ASCII_6BIT_CHARACTERS = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_ !\"#$%&'()*+,-./0123456789:;<=>?";
	
	private Random rn = new Random();
	
	public void setName (TestBitVectorConstructor tbv , int from, int to, int nrofChar){
		assert ( (to-from + 1) == (nrofChar * 6) );
		
		final int range = nrofChar + 1;
		final int randomLength = rn.nextInt(range);
		
		int current = to-5;
		
		StringBuilder expectedName = new StringBuilder();
		if (randomLength > 0)
		{
			for (int i = 0 ; i < randomLength -1; i ++)
			{
				final int randomChar = rn.nextInt(ASCII_6BIT_CHARACTERS.length());
				tbv.setBitVector(randomChar, current, current + 6 -1 );
				current -= 6;
				expectedName.append(ASCII_6BIT_CHARACTERS.charAt(randomChar));
			}
			final int randomChar = rn.nextInt(ASCII_6BIT_CHARACTERS.length()-1)+1;
			tbv.setBitVector(randomChar, current, current + 6 -1 );
			current -= 6;
			expectedName.append (ASCII_6BIT_CHARACTERS.charAt(randomChar));		//exclude @ character
		}
		for (int i = randomLength ; i < nrofChar; i ++)
		{
			tbv.setBitVector(0, current, current + 6 -1 );
			current -= 6;
			//no trailing @ characters expected
		}
		assert (current == from -6);
				
		tbv.addExpectedOutput("getName", expectedName.toString());
	}
}	