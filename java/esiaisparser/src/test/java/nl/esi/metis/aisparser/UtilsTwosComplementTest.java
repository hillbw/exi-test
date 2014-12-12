package nl.esi.metis.aisparser;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class UtilsTwosComplementTest {
	@Test
	public void Bits17ConversionTest_0()
	{
		int bits = 0;
		assertEquals( 0, UtilsTwosComplement.convertFrom17Bits(bits));
	}
	@Test
	public void Bits17ConversionTest_1FFFF()
	{
		int bits = 0x1FFFF;
		assertEquals( -1, UtilsTwosComplement.convertFrom17Bits(bits));
	}
	@Test
	public void Bits17ConversionTest_0FFFF()
	{
		int bits = 0x0FFFF;
		assertEquals( bits, UtilsTwosComplement.convertFrom17Bits(bits));
	}
	@Test
	public void Bits17ConversionTest_10000()
	{
		int bits = 0x10000;
		int expected = -bits;
		assertEquals( expected, UtilsTwosComplement.convertFrom17Bits(bits));
	}
	@Test
	public void Bits17ConversionTest_Default_NotAvailable()
	{
		int bits = 0xD548;
		int expected = 91*60*10;
		assertEquals( expected, UtilsTwosComplement.convertFrom17Bits(bits));
	}

	@Test
	public void Bits27ConversionTest_0()
	{
		int bits = 0;
		assertEquals( 0, UtilsTwosComplement.convertFrom27Bits(bits));
	}
	@Test
	public void Bits27ConversionTest_7FFFFFF()
	{
		int bits = 0x7FFFFFF;
		assertEquals( -1, UtilsTwosComplement.convertFrom27Bits(bits));
	}
	@Test
	public void Bits27ConversionTest_3FFFFFF()
	{
		int bits = 0x3FFFFFF;
		assertEquals( bits, UtilsTwosComplement.convertFrom27Bits(bits));
	}
	@Test
	public void Bits27ConversionTest_4000000()
	{
		int bits = 0x4000000;
		int expected = -bits;
		assertEquals( expected, UtilsTwosComplement.convertFrom27Bits(bits));
	}
	@Test
	public void Bits27ConversionTest_Default_NotAvailable()
	{
		int bits = 0x3412140;
		int expected = 91*60*10000;
		assertEquals( expected, UtilsTwosComplement.convertFrom27Bits(bits));
	}
	@Test
	public void Bits18ConversionTest_0()
	{
		int bits = 0;
		assertEquals( 0, UtilsTwosComplement.convertFrom18Bits(bits));
	}
	@Test
	public void Bits18ConversionTest_3FFFF()
	{
		int bits = 0x3FFFF;
		assertEquals( -1, UtilsTwosComplement.convertFrom18Bits(bits));
	}
	@Test
	public void Bits18ConversionTest_1FFFF()
	{
		int bits = 0x1FFFF;
		assertEquals( bits, UtilsTwosComplement.convertFrom18Bits(bits));
	}
	@Test
	public void Bits18ConversionTest_20000()
	{
		int bits = 0x20000;
		int expected = -bits;
		assertEquals( expected, UtilsTwosComplement.convertFrom18Bits(bits));
	}
	@Test
	public void Bits18ConversionTest_Default_NotAvailable()
	{
		int bits = 0x1A838;
		int expected = 181*60*10;
		assertEquals( expected, UtilsTwosComplement.convertFrom18Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_0()
	{
		int bits = 0;
		assertEquals( 0, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_FFFFFFF()
	{
		int bits = 0xFFFFFFF;
		assertEquals( -1, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_7FFFFFF()
	{
		int bits = 0x7FFFFFF;
		assertEquals( bits, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_8000000()
	{
		int bits = 0x8000000;
		int expected = -bits;
		assertEquals( expected, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_8888888()
	{
		int bits = 0x8888888;
		int expected = - 0x7777778;
		assertEquals( expected, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits28ConversionTest_Default_NotAvailable()
	{
		int bits = 0x6791AC0;
		int expected = 181*60*10000;
		assertEquals( expected, UtilsTwosComplement.convertFrom28Bits(bits));
	}
	@Test
	public void Bits8ConversionTest_0()
	{
		int bits = 0;
		assertEquals( 0, UtilsTwosComplement.convertFrom8Bits(bits));
	}
	@Test
	public void Bits8ConversionTest_FF()
	{
		int bits = 0xFF;
		assertEquals( -1, UtilsTwosComplement.convertFrom8Bits(bits));
	}
	@Test
	public void Bits8ConversionTest_7F()
	{
		int bits = 0x7F;
		assertEquals( bits, UtilsTwosComplement.convertFrom8Bits(bits));
	}
	@Test
	public void Bits8ConversionTest_80()
	{
		int bits = 0x80;
		int expected = -bits;
		assertEquals( expected, UtilsTwosComplement.convertFrom8Bits(bits));
	}

}
