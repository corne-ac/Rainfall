import com.corne.rainfall.data.model.FireLocationItemModel
import com.corne.rainfall.utils.CsvParser
import com.corne.rainfall.utils.NetworkResult
import org.junit.Assert.assertEquals
import org.junit.Test

class CsvParserTest {

    @Test
    fun `parseCsvData returns list of NetworkResult with FireLocationItemModel when csvData is valid`() {
        val csvData =
            "id,latitude,longitude,fire_name,fire_size,fire_intensity\n1,10.0,20.0,Fire1,100,1.0"
        val expected =
            listOf(NetworkResult.success(FireLocationItemModel(10.0, 20.0, "Fire1", 100, 1.0)))

        val result = CsvParser.parseCsvData(csvData)

        assertEquals(expected, result)
    }

    /*@Test
    fun `parseCsvData returns list of NetworkResult with error when csvData is invalid`() {
        val csvData = "id,latitude,longitude,fire_name,fire_size\n1,10.0,20.0,Fire1,100"
        val expected = listOf(NetworkResult.error(R.string.error_fire_parse))

        val result = CsvParser.parseCsvData(csvData)

        assertEquals(expected, result)
    }*/

    @Test
    fun `parseCsvData returns empty list when csvData is empty`() {
        val csvData = ""
        val expected = emptyList<NetworkResult<FireLocationItemModel>>()

        val result = CsvParser.parseCsvData(csvData)

        assertEquals(expected, result)
    }
}