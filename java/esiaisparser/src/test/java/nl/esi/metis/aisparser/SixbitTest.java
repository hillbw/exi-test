package nl.esi.metis.aisparser;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SixbitTest {
	/** Proof that one can ask for a string of length zero!
	 * length == to - from + 1
	 */
	@Test
	public void getString0()
	{
		Sixbit sb = new Sixbit("A", 0);
		String s = sb.getStringFromTo(1, 0);
		assertEquals( "" ,s);
	}
	
	private long orderChar(char character)
	{
		Sixbit sb = new Sixbit(Character.toString(character), 0);
		return sb.getIntFromTo(1, 6);
	}
	@Test
	public void checkOrder()
	{
		StringBuilder failureMessage = new StringBuilder(); 
		//                        0         1         2         3         4         5         6
		// 0-W`-w				  0123456789012345678901234567890123456789012345678901234567890123
		String legalCharacters = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVW`abcdefghijklmnopqrstuvw";
		for(int i =0; i < legalCharacters.length(); i++)
		{
			long result = orderChar(legalCharacters.charAt(i));
			if (i != result)
			{
				failureMessage.append("failure at " + i + " chararchter " + legalCharacters.charAt(i) + " yields " + result +". ");
			}
		}
		assertEquals ("", failureMessage.toString());
	}
	@Test
	public void get_string()
	{
		//                        0         1         2         3         4         5         6
		// 0-W`-w				  0123456789012345678901234567890123456789012345678901234567890123
		String inputCharacters = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVW`abcdefghijklmnopqrstuvw";
		Sixbit sb = new Sixbit(inputCharacters,0);
		
		String expectCharacters = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_ !\"#$%&'()*+,-./0123456789:;<=>?";
		assertEquals (expectCharacters, sb.getStringFromTo(1, inputCharacters.length()*6));
	}
	@Test
	public void get_28_after3()
	{
		Sixbit sb = new Sixbit("PQRghi",0);
		long first = sb.getIntFromTo(1, 3);
		assertEquals ("Precondition check", 4,first);
		
		assertEquals (17586145, sb.getIntFromTo(4, 31));
	}
	@Test
	public void get_36()
	{
		Sixbit sb = new Sixbit("PQRghi",0);
		assertEquals (34922495025L, sb.getBitVectorFromTo(1, 36).getLongFromTo(0, 35));
	}
	
	@Test
	public void get_increasing()
	{
		Sixbit sb = new Sixbit("PQRghi",0);
		assertEquals (2L, sb.getIntFromTo(1, 2));
		assertEquals (0L, sb.getIntFromTo(3, 4));
		assertEquals (1L, sb.getIntFromTo(5, 7));
		assertEquals (0L, sb.getIntFromTo(8, 11));
		assertEquals (24L, sb.getIntFromTo(12, 16));
		assertEquals (43L, sb.getIntFromTo(17, 22));
		assertEquals (120L, sb.getIntFromTo(23, 29));
		assertEquals (49L, sb.getIntFromTo(30, 36));
	}

	@Test
	public void decodeBinaryToContentCharacter()
	{
		for (int input = 0; input < 32; input ++)
		{
			int expected = input + 64;
			assertEquals ("Executing input = " + input, expected, Sixbit.decodeBinaryToContentCharacter(input));
		}
		for (int input = 32; input < 64; input ++)
		{
			int expected = input;
			assertEquals ("Executing input = " + input, expected, Sixbit.decodeBinaryToContentCharacter(input));
		}
	}
	
	@Test
	public void decodeEncodeBinaryToTransportCharacter()
	{
			//      				  0         1         2         3         4         5         6
			// 0-W`-w				  0123456789012345678901234567890123456789012345678901234567890123
			String inputCharacters = "0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVW`abcdefghijklmnopqrstuvw";
			for (int i = 0; i < inputCharacters.length(); i++)
			{
				char c = inputCharacters.charAt(i);
				assertEquals ("Executing charachter " + c, c, Sixbit.encodeBinaryToTransportCharacter(Sixbit.decodeTransportCharacterToBinary(c)));
			}

	}
	
	@Test
	public void encodeDecodeBinaryToTransportCharacter()
	{
		for (int input = 0; input < 64; input ++)
		{
			assertEquals ("Executing input = " + input, input, Sixbit.decodeTransportCharacterToBinary(Sixbit.encodeBinaryToTransportCharacter(input)));
		}
	}
	
	@Test
	public void decodeEncodeBinaryToContentCharacter()
	{
			//      				  0         1         2          3          4         5         6
			// 0-W`-w				  01234567890123456789012345678 901234 56789012345678901234567890123
			String inputCharacters = "@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_ !\"#$%&'()*+,-./0123456789:;<=>?";
			for (int i = 0; i < inputCharacters.length(); i++)
			{
				char c = inputCharacters.charAt(i);
				assertEquals (c, Sixbit.decodeBinaryToContentCharacter(Sixbit.encodeContentCharacterToBinary(c)));
			}

	}
	
	@Test
	public void encodeDecodeBinaryToContentCharacter()
	{
		for (int i = 0; i < 64; i++)
		{
			assertEquals (i, Sixbit.encodeContentCharacterToBinary(Sixbit.decodeBinaryToContentCharacter(i)));
		}

	}
}
