package cruciverba.model.test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.OptionalInt;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import cruciverba.model.Schema;


class SchemaTest {

	static Schema schema;
	
	@BeforeAll
	static void setup() {
		schema = new Schema(11,17);
		assertEquals(11, schema.getNumRighe());
		assertEquals(17, schema.getNumColonne());
	}
	
	@Test
	public void testOK_SetGetCella() {
		schema.setCella(0,  0,  1);
		schema.setCella(0,  1,  2);
		schema.setCella(0,  3,  3);
		schema.setCella(0,  4,  4);
		schema.setCella(0, 12,  4);
		schema.setCella(0, 15,  7);
		schema.setCella(1, 16, 12);
		
		assertEquals(  1, schema.getCella(0, 0) );
		assertEquals(  2, schema.getCella(0, 1) );
		assertEquals(  3, schema.getCella(0, 3) );
		assertEquals(  4, schema.getCella(0, 4) );
		assertEquals(  4, schema.getCella(0,12) );
		assertEquals(  7, schema.getCella(0,15) );
		assertEquals( 12, schema.getCella(1,16) );
		
		schema.setCella(2,  0, 12);
		schema.setCella(2,  4, 13);
		schema.setCella(2, 10,  4);
		schema.setCella(2, 13, 14);
		schema.setCella(2,  1,  3);
		schema.setCella(2,  2,  2);
		schema.setCella(2, 16,  6);
		
		assertEquals( 12, schema.getCella(2, 0) );
		assertEquals( 13, schema.getCella(2, 4) );
		assertEquals(  4, schema.getCella(2,10) );
		assertEquals( 14, schema.getCella(2,13) );
		assertEquals(  3, schema.getCella(2, 1) );
		assertEquals(  2, schema.getCella(2, 2) );
		assertEquals(  6, schema.getCella(2,16));
		
		schema.setCella(3,  0,  0);
		schema.setCella(3,  1,  3);
		schema.setCella(3,  3, 15);
		schema.setCella(3,  9, 10);
		schema.setCella(3, 15,  6);
		
		assertEquals(  0, schema.getCella(3, 0) );
		assertEquals(  3, schema.getCella(3, 1) );
		assertEquals( 15, schema.getCella(3, 3) );
		assertEquals( 10, schema.getCella(3, 9) );
		assertEquals(  6, schema.getCella(3,15) );
		
		schema.setCella(6,  0,  0);
		schema.setCella(6,  1,  2);
		schema.setCella(6,  5, 12);
		schema.setCella(6, 14,  1);
		schema.setCella(7, 16,  3);
		
		assertEquals(  0, schema.getCella(6, 0) );
		assertEquals(  2, schema.getCella(6, 1) );
		assertEquals( 12, schema.getCella(6, 5) );
		assertEquals(  1, schema.getCella(6,14) );
		assertEquals(  3, schema.getCella(7,16) );
		
		schema.setCella(8,  0,  6);
		schema.setCella(8,  4,  2);
		schema.setCella(8,  6,  2);
		schema.setCella(8, 12,  9);
		
		assertEquals(  6, schema.getCella(8, 0) );
		assertEquals(  2, schema.getCella(8, 4) );
		assertEquals(  2, schema.getCella(8, 6) );
		assertEquals(  9, schema.getCella(8,12) );
	}
	
	
	@Test
	public void testOK_SetGetMapping() {
		
		assertTrue(schema.setMapping('L', 1));
		assertTrue(schema.setMapping('I', 2));
		assertTrue(schema.setMapping('A', 3));
		assertTrue(schema.setMapping('V', 4));
		assertTrue(schema.setMapping('N', 5));
		assertTrue(schema.setMapping('O', 6));
		assertTrue(schema.setMapping('D', 7));
		assertTrue(schema.setMapping('E', 8));
		assertTrue(schema.setMapping('S', 9));
		assertTrue(schema.setMapping('T', 10));
		
		assertEquals( OptionalInt.of(1),  schema.getMapping('L'));
		assertEquals( OptionalInt.of(2),  schema.getMapping('I'));
		assertEquals( OptionalInt.of(3),  schema.getMapping('A'));
		assertEquals( OptionalInt.of(4),  schema.getMapping('V'));
		assertEquals( OptionalInt.of(5),  schema.getMapping('N'));
		assertEquals( OptionalInt.of(6),  schema.getMapping('O'));
		assertEquals( OptionalInt.of(7),  schema.getMapping('D'));
		assertEquals( OptionalInt.of(8),  schema.getMapping('E'));
		assertEquals( OptionalInt.of(9),  schema.getMapping('S'));
		assertEquals( OptionalInt.of(10),  schema.getMapping('T'));
		
		assertTrue(schema.setMapping('R', 11));
		assertTrue(schema.setMapping('M', 12));
		assertTrue(schema.setMapping('J', 13));
		assertTrue(schema.setMapping('B', 14));
		assertTrue(schema.setMapping('C', 15));
		assertTrue(schema.setMapping('U', 16));
		assertTrue(schema.setMapping('G', 17));
		
		assertEquals( OptionalInt.of(11),  schema.getMapping('R'));
		assertEquals( OptionalInt.of(12),  schema.getMapping('M'));
		assertEquals( OptionalInt.of(13),  schema.getMapping('J'));
		assertEquals( OptionalInt.of(14),  schema.getMapping('B'));
		assertEquals( OptionalInt.of(15),  schema.getMapping('C'));
		assertEquals( OptionalInt.of(16),  schema.getMapping('U'));
		assertEquals( OptionalInt.of(17),  schema.getMapping('G'));
		
		// --- elementi non presenti 
		assertEquals( OptionalInt.empty(), schema.getMapping('F'));
		assertEquals( OptionalInt.empty(), schema.getMapping('H'));
		assertEquals( OptionalInt.empty(), schema.getMapping('K'));
		assertEquals( OptionalInt.empty(), schema.getMapping('P'));
		assertEquals( OptionalInt.empty(), schema.getMapping('Q'));
		assertEquals( OptionalInt.empty(), schema.getMapping('W'));
		assertEquals( OptionalInt.empty(), schema.getMapping('X'));
		assertEquals( OptionalInt.empty(), schema.getMapping('Y'));
		assertEquals( OptionalInt.empty(), schema.getMapping('Z'));
		
		// --- togli / metti  
		assertTrue(schema.unsetMapping('J', 13));
		assertEquals( OptionalInt.empty(), schema.getMapping('J'));
		assertTrue(schema.setMapping('K', 13));
		assertEquals( OptionalInt.of(13),  schema.getMapping('K'));
		
		// --- mapping inesistenti o errati  
		assertFalse(schema.unsetMapping('J', 13));
		assertFalse(schema.unsetMapping('K', 14));
	}
	
	@Test
	public void testKO_lowercaseChar() {
		assertThrows(IllegalArgumentException.class, () -> schema.unsetMapping('j', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.setMapping('j', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.getMapping('j'));
	}
	
	@Test
	public void testKO_mapping_nonLetterChar() {
		assertThrows(IllegalArgumentException.class, () -> schema.unsetMapping('3', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.unsetMapping(':', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.setMapping('3', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.setMapping(':', 13));
		assertThrows(IllegalArgumentException.class, () -> schema.getMapping('3'));
		assertThrows(IllegalArgumentException.class, () -> schema.getMapping(':'));
	}
	
	@Test
	public void testKO_mapping_IllegalNumber() {
		assertThrows(IllegalArgumentException.class, () -> schema.unsetMapping('A', -1));
		assertThrows(IllegalArgumentException.class, () -> schema.unsetMapping('A', 27));
		assertThrows(IllegalArgumentException.class, () -> schema.setMapping('A', -1));
		assertThrows(IllegalArgumentException.class, () -> schema.setMapping('A', 27));
	}
	
	@Test
	public void testKO_getCella_indicesOutOfRange() {
		// schema size 11 x 17 --> index ranges = [0..10] x [0..16] 
		assertThrows(IllegalArgumentException.class, () -> schema.getCella(11, 0));
		assertThrows(IllegalArgumentException.class, () -> schema.getCella(10, 17));
		assertThrows(IllegalArgumentException.class, () -> schema.getCella(-1, 0));
		assertThrows(IllegalArgumentException.class, () -> schema.getCella(10, -1));
	}
	
	@Test
	public void testKO_ctor_indicesOutOfRange() {
		assertThrows(IllegalArgumentException.class, () -> new Schema(11, 0));
		assertThrows(IllegalArgumentException.class, () -> new Schema(0, 1));
		assertThrows(IllegalArgumentException.class, () -> new Schema(-1, 10));
		assertThrows(IllegalArgumentException.class, () -> new Schema(10, -1));
	}

}
