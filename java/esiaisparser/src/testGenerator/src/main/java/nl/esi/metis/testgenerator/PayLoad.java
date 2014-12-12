package nl.esi.metis.testgenerator;

import nl.esi.metis.aisparser.Sixbit;
import cern.colt.bitvector.BitVector;

public class PayLoad {
	private StringBuilder asciiString;
	public String getAsciiString() {
		return asciiString.toString();
	}

	private int fillBits;
	public int getFillBits() {
		return fillBits;
	}

	public PayLoad(BitVector bits)
	{
		BitVector bits2encode;
		
		int rest = bits.size() % Sixbit.BITSPERCHAR;
		if (rest != 0)
		{
			fillBits = Sixbit.BITSPERCHAR - rest;
			bits2encode = new BitVector(bits.size() + fillBits);
			bits2encode.replaceFromToWith(fillBits, fillBits + bits.size() -1, bits, 0);
		}
		else
		{
			fillBits = 0;
			bits2encode = bits;
		}
		asciiString = new StringBuilder();
		
		final int nrofChar = bits2encode.size() / Sixbit.BITSPERCHAR;
		for(int i = 0; i < nrofChar; i++)
    	{
			int mirror = nrofChar-1 -i;
			asciiString.append(Sixbit.encodeBinaryToTransportCharacter((int) bits2encode.getLongFromTo( mirror * Sixbit.BITSPERCHAR, (mirror+1) * Sixbit.BITSPERCHAR -1)));
    	}
	}
}
