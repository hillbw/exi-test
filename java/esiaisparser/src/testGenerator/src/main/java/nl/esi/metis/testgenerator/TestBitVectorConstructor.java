package nl.esi.metis.testgenerator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import nl.esi.metis.aisparser.annotations.Annotation;

import cern.colt.bitvector.BitVector;

public class TestBitVectorConstructor {
		private BitVector bv;
		private Map<String,Object> eo;
		private List<Annotation> an;
		
		public TestBitVectorConstructor (int length)
		{
			bv = new BitVector(length);
			eo = new HashMap<String, Object>();
			an = new ArrayList<Annotation>();
		}


		public void setBitVector(long value, int from, int to)
		{
			bv.putLongFromTo(value, from, to);
		}
		
		public BitVector getBitVector()
		{
			return bv;
		}
		
		public Map<String, Object> getExpectedOutputs()
		{
			return eo;
		}
		
		public void addExpectedOutput(String key, Object value)
		{
			eo.put(key, value);
		}

		public List<Annotation> getExpectedAnnotations() {
			return an;
		}
		
		public void addExpectedAnnotation(Annotation a)
		{
			an.add(a);
		}


		public void addBits(int addBits) {
			final int oldSize = bv.size();
			final int newSize = oldSize + addBits;
			BitVector newBV = new BitVector(newSize);
			newBV.replaceFromToWith(addBits, newSize-1, bv, 0);
	
			Random rn = new Random();
			final int BITS_PER_LONG = 64;
			for (int from = 0; from < addBits; from += BITS_PER_LONG)
			{
				newBV.putLongFromTo(rn.nextLong(), from, Math.min(addBits,  from + BITS_PER_LONG) -1 );
			}
			
			bv = newBV;
	
		
		}

}
