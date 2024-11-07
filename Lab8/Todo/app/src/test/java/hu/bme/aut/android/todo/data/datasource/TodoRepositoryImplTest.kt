package hu.bme.aut.android.todo.data.datasource

import hu.bme.aut.android.todo.data.dao.TodoDao
import hu.bme.aut.android.todo.data.entities.TodoEntity
import hu.bme.aut.android.todo.domain.model.Priority
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.datetime.LocalDate
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.mockito.kotlin.doReturn
import org.mockito.kotlin.mock
import org.mockito.kotlin.times
import org.mockito.kotlin.verify

@RunWith(MockitoJUnitRunner::class)
class TodoRepositoryImplTest {

    @Mock
    lateinit var todoDao: TodoDao

    @Test
    fun testGetAllTodos() {
        val sampleTodo = TodoEntity(
            1,
            "Test app",
            Priority.HIGH,
            LocalDate(2024, 4, 30),
            "Write unit tests for our Todo app"
        )
        val mockDao = mock<TodoDao> {
            on { getAllTodos() } doReturn (flowOf(listOf(sampleTodo)))
        }
        val todoRepositoryImpl = TodoRepositoryImpl(mockDao)
        val result = todoRepositoryImpl.getAllTodos()
        runBlocking {
            Assert.assertTrue(result.first().contains(sampleTodo))
            verify(mockDao, times(1)).getAllTodos()
        }
    }
}