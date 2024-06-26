package ar.edu.unlam.mobile.scaffolding

import ar.edu.unlam.mobile.scaffolding.domain.models.Species
import org.junit.Assert.assertEquals
import org.junit.Test

class SpeciesTest {
    @Test
    fun testGetEmoji() {
        assertEquals("🦜", Species.LORO.getEmoji())
        assertEquals("🐱", Species.GATO.getEmoji())
        assertEquals("🐶", Species.PERRO.getEmoji())
        assertEquals("🐰", Species.CONEJO.getEmoji())
        assertEquals("❓", Species.UNKNOWN.getEmoji())
    }

    @Test
    fun testFromString() {
        assertEquals(Species.LORO, Species.fromString("loro"))
        assertEquals(Species.GATO, Species.fromString("GATO"))
        assertEquals(Species.PERRO, Species.fromString("Perro"))
        assertEquals(Species.CONEJO, Species.fromString("conejo"))
        assertEquals(Species.UNKNOWN, Species.fromString("unknown"))
        assertEquals(Species.UNKNOWN, Species.fromString("unrecognized_value"))
    }
}
