package nl.esi.metis.aisparser.impl;

import java.util.ArrayList;
import java.util.List;

import nl.esi.metis.aisparser.AISMessage;
import nl.esi.metis.aisparser.AISMessage24PartA;
import nl.esi.metis.aisparser.AISMessage24PartB;
import nl.esi.metis.aisparser.VDMMessage;
import nl.esi.metis.aisparser.annotations.AISHypothesisAnnotation;
import nl.esi.metis.aisparser.annotations.AISInconsistentLengthForTypeAnnotation;
import nl.esi.metis.aisparser.annotations.Annotation;
import nl.esi.metis.aisparser.provenance.AISMessageProvenance;
import nl.esi.metis.aisparser.provenance.VDMMessageProvenance;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static org.mockito.Mockito.*;	

public class AISMessageFactoryTest {
	private static final String TYPEA_WRONGTYPEBINPUT = "HljRgloLGa;ptFGUui5NRGNKOGH";
	private static final String TYPEB_WRONGTYPEAINPUT = "HB2smvCg7ukjDjpqRQF?dWaH@TH0";

	
	private static boolean hasAnnotation(AISMessage message, Annotation expected)
	{
		boolean retval = false;
		final AISMessageProvenance provenance = message.getProvenance();
		final List<Annotation> annotations = provenance.getAnnotations(); 
		for(Annotation annotation : annotations)
		{
			retval |= annotation.equals(expected);
		}
		return retval;
	}
	
	@Test
	public void wrongTypeTypeA()
	{
		assertEquals(27, TYPEA_WRONGTYPEBINPUT.length());
		
		VDMMessage vdmmsg = mock (VDMMessage.class);
		when (vdmmsg.getMessage()).thenReturn(TYPEA_WRONGTYPEBINPUT);
		when (vdmmsg.getNrOfFillBits()).thenReturn(2);
		
		VDMMessageProvenance prov = mock (VDMMessageProvenance.class);
		when (prov.getAnnotations()).thenReturn(new ArrayList<Annotation>());
		when (vdmmsg.getProvenance()).thenReturn(prov);
		
		AISMessage msg = AISMessageFactory.getAISMessage(vdmmsg);
		assertEquals( 24, msg.getMessageID());
		assertTrue (msg instanceof AISMessage24PartA);
		assertEquals(2, msg.getProvenance().getAnnotations().size());
		assertTrue( hasAnnotation(msg, new AISInconsistentLengthForTypeAnnotation(24,160) ) );
		assertTrue( hasAnnotation(msg, new AISHypothesisAnnotation(24, 160) ));
	}

	@Test
	public void wrongTypeTypeB()
	{
		assertEquals(28, TYPEB_WRONGTYPEAINPUT.length());
		VDMMessage vdmmsg = mock (VDMMessage.class);
		when (vdmmsg.getMessage()).thenReturn(TYPEB_WRONGTYPEAINPUT);
		when (vdmmsg.getNrOfFillBits()).thenReturn(0);
		
		VDMMessageProvenance prov = mock (VDMMessageProvenance.class);
		when (prov.getAnnotations()).thenReturn(new ArrayList<Annotation>());
		when (vdmmsg.getProvenance()).thenReturn(prov);
		
		AISMessage msg = AISMessageFactory.getAISMessage(vdmmsg);
		assertEquals( 24, msg.getMessageID());
		assertTrue (msg instanceof AISMessage24PartB);
		assertEquals(2, msg.getProvenance().getAnnotations().size());
		assertTrue( hasAnnotation(msg, new AISInconsistentLengthForTypeAnnotation(24,168) ) );
		assertTrue( hasAnnotation(msg, new AISHypothesisAnnotation(24, 168) ));
	}

}