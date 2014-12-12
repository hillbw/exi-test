package nl.esi.metis.testgenerator;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cern.colt.bitvector.BitVector;

public class PayLoadTest {
	private static final String ASCII_6BIT_CHARACTERS = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_ !\"#$%&'()*+,-./0123456789:;<=>?";
	@Test
	public void testVector_NULL() {
		//Setup
		BitVector input = new BitVector(0);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		assertEquals ("", actual.getAsciiString());
		assertEquals(0, actual.getFillBits());
		//Teardown
	}

	@Test
	public void testVector_0() {
		//Setup
		BitVector input = new BitVector(1);
		input.put(0, false);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		assertEquals ("0", actual.getAsciiString());
		assertEquals(5, actual.getFillBits());
		//Teardown
	}

	@Test
	public void testVector_1() {
		//Setup
		BitVector input = new BitVector(1);
		input.put(0, true);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		String expected = Character.toString(ASCII_6BIT_CHARACTERS.charAt((int)Math.pow(2, 5-1)));
		assertEquals (expected , actual.getAsciiString());
		assertEquals(5, actual.getFillBits());
		//Teardown
	}

	@Test
	public void testVector_100000() {
		//Setup
		BitVector input = new BitVector(6);
		input.clear();
		input.put(5, true);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		assertEquals ("P", actual.getAsciiString());
		assertEquals(0, actual.getFillBits());
		//Teardown
	}
	
	@Test
	public void testVector_1plus_character() {
		//Setup
		BitVector input = new BitVector(7);
		input.clear();
		input.put(6, true);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		assertEquals (ASCII_6BIT_CHARACTERS.charAt((int)Math.pow(2, 5-1))+"0", actual.getAsciiString());
		assertEquals(5, actual.getFillBits());
		//Teardown
	}
	
	@Test
	public void testVector_2characters() {
		//Setup
		BitVector input = new BitVector(12);
		input.clear();
		input.put(11, true);
		//Execute
		PayLoad actual = new PayLoad(input);
		//Verify
		assertEquals ("P0", actual.getAsciiString());
		assertEquals(0, actual.getFillBits());
		//Teardown
	}
	
	@Test
	public void testIdentity() {
		//Setup
		BitVector bv = new BitVector(24);	
		bv.clear();
		bv.put(0, true);
		PayLoad pl = new PayLoad(bv);
		//Execute
		nl.esi.metis.aisparser.Sixbit sb = new nl.esi.metis.aisparser.Sixbit(pl.getAsciiString(), pl.getFillBits());
		
		//Verify
		assertEquals(bv, sb.getBitVector());
		
		//TearDown
	}
}
