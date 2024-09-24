package mk.ukim.finki.diansvinarii;

import mk.ukim.finki.diansvinarii.model.Winery;
import mk.ukim.finki.diansvinarii.repository.WineryRepository;
import mk.ukim.finki.diansvinarii.service.impl.WineryServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WineryServiceTest {

    @Mock
    private WineryRepository wineryRepository;

    @InjectMocks
    private WineryServiceImpl wineryService;

    private Winery winery;
    private Winery winery2;
    private Winery winery3;

    @BeforeEach
    public void initWinery(){
        winery = new Winery(1L, "wineryOne", "072223305", "filip.com", 0.2, 0.3, "08:00", "18:00");
        winery2 = new Winery(1L, "wineryTwo", "072223305", "vino.mk", 0.2, 0.3, "07:00", "17:00");
        winery3 = new Winery(1L, "Vinoteka", "072223305", "example.com", 0.2, 0.3, "09:00", "15:00");
    }

    /**
     * Test group for WineryService.searchWineries
     * or this test group Input space partitioning was used, resulting in 3 partitions and 4 tests
     * <ul>
     *     <li>name is null</li>
     *     <li>name is an empty string</li>
     *     <li>name is present in the names of available wineries</li>
     * </ul>
     */
    @Test
    public void testSearchWineries_NullName() {
        when(wineryRepository.findAllByNameContainingIgnoreCase(null)).thenReturn(List.of());
        List<Winery> result = wineryService.searchWineries(null);
        assertTrue(result.isEmpty());

        verify(wineryRepository).findAllByNameContainingIgnoreCase(null);
    }

    @Test
    public void testSearchWineries_EmptyString() {
        when(wineryRepository.findAllByNameContainingIgnoreCase("")).thenReturn(List.of(winery, winery2, winery3));
        List<Winery> result = wineryService.searchWineries("");

        assertEquals(result.size(), 3);
        verify(wineryRepository).findAllByNameContainingIgnoreCase("");
    }

    @Test
    public void testSearchWineries_ValidName() {
        when(wineryRepository.findAllByNameContainingIgnoreCase("Winery"))
                .thenReturn(List.of(winery, winery2));

        List<Winery> result = wineryService.searchWineries("Winery");

        assertEquals(List.of(winery, winery2), result);
        assertEquals(2, result.size());
        assertFalse(result.isEmpty());
        verify(wineryRepository).findAllByNameContainingIgnoreCase("Winery");
    }

    @Test
    public void testSearchWineries_InvalidName() {
        when(wineryRepository.findAllByNameContainingIgnoreCase("InvalidWinery"))
                .thenReturn(List.of());

        List<Winery> result = wineryService.searchWineries("InvalidWinery");
        assertTrue(result.isEmpty());
    }



    /**
     * Test group for WineryService.searchWineries
     * or this test group Input space partitioning was used, resulting in 3 partitions and 3 tests
     * <ul>
     *     <li>id is null</li>
     *     <li>id is not present in the repository</li>
     *     <li>is is present in the repository</li>
     * </ul>
     */
    @Test
    public void testFindById_ValidId() throws Exception {
        Winery winery = new Winery();
        when(wineryRepository.findById(3L)).thenReturn(Optional.of(winery));

        Optional<Winery> result = wineryService.findById(3L);
        assertTrue(result.isPresent());
    }

    @Test
    public void testFindById_InvalidId() {
        when(wineryRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> wineryService.findById(999L));
    }

    @Test
    public void testFindById_NullId() {
        assertThrows(NullPointerException.class, () -> wineryService.findById(null));
    }

    // Test for save
    @Test
    public void testSave_ValidFields() throws Exception {
        Winery winery = new Winery();
        when(wineryRepository.findById(1L)).thenReturn(Optional.of(winery));

        wineryService.save(1L, "Chateau", "1234567890", "http://example.com",
                21.45, 41.23, "09:00", "17:00");

        verify(wineryRepository, times(1)).save(winery);
        assertEquals("Chateau", winery.getName());
        assertEquals("1234567890", winery.getPhone());
    }

    @Test
    public void testSave_InvalidCoordinates() throws Exception {
        Winery winery = new Winery();
        when(wineryRepository.findById(1L)).thenReturn(Optional.of(winery));

        Exception exception = assertThrows(IllegalArgumentException.class, () ->
                wineryService.save(1L, "Chateau", "1234567890", "http://example.com",
                        200.0, 90.0, "09:00", "17:00")); // Invalid longitude

        assertEquals("Invalid coordinates", exception.getMessage());
    }

    @Test
    public void testSave_MissingRequiredFields() throws Exception {
        Winery winery = new Winery();
        when(wineryRepository.findById(1L)).thenReturn(Optional.of(winery));

        Exception exception = assertThrows(Exception.class, () ->
                wineryService.save(1L, null, null, "http://example.com", 21.45, 41.23, "09:00", "17:00"));

        assertEquals("Required fields missing", exception.getMessage());
    }


}

