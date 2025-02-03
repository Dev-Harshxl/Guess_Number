@GetMapping
public List<Map<String, Object>> getAllItems() {
    List<items> items = itemRepository.findAll();
    List<Map<String, Object>> response = new ArrayList<>();

    for (items item : items) {
        Map<String, Object> itemMap = new HashMap<>();
        itemMap.put("id", item.getId());
        itemMap.put("title", item.getTitle());
        itemMap.put("description", item.getDescription());

        if (item.getImage() != null) {
            // Default to JPEG if the MIME type is not explicitly known
            String base64Image = "data:image/jpeg;base64," + Base64.getEncoder().encodeToString(item.getImage());
            itemMap.put("image", base64Image);
        } else {
            itemMap.put("image", null);
        }

        response.add(itemMap);
    }

    return response;
}



import { takeEvery, put, call } from "redux-saga/effects";
import { setTodos } from "./todoActions";

// Worker saga that handles the API call
function* fetchTodosSaga() {
  try {
    // Dispatch to indicate loading has started
    yield put({ type: "FETCH_TODOS_REQUEST" });

    const response = yield call(fetch, "https://jsonplaceholder.typicode.com/todos?_limit=5");
    const todos = yield response.json();
    const todoTitles = todos.map((todo) => todo.title);

    // Dispatch to set todos and stop the loading state
    yield put(setTodos(todoTitles));
  } catch (error) {
    console.error("Error fetching todos:", error);
  }
}

// Watcher saga listens for FETCH_TODOS_REQUEST action
function* rootSaga() {
  yield takeEvery("FETCH_TODOS_REQUEST", fetchTodosSaga);
}

export default rootSaga;



            // Default to JPEG if the MIME type is not explicitly known



import { takeEvery, put, call } from "redux-saga/effects";
import { setTodos } from "./todoActions";

// Worker saga that handles the API call
function* fetchTodosSaga() {
  try {
    // Perform the API call to fetch the todos
    const response = yield call(fetch, "https://jsonplaceholder.typicode.com/todos?_limit=5");
    
    if (!response.ok) {
      throw new Error("Network response was not ok.");
    }

    const todos = yield response.json();
    const todoTitles = todos.map((todo) => todo.title);

    // Dispatch to set todos and stop the loading state
    yield put(setTodos(todoTitles));
  } catch (error) {
    console.error("Error fetching todos:", error);
    // You can dispatch an error action here to notify the UI of the error
    yield put({ type: "FETCH_TODOS_ERROR", error: error.message });
  }
}

// Watcher saga listens for FETCH_TODOS_REQUEST action
function* rootSaga() {
  // Watches for 'FETCH_TODOS_REQUEST' to call fetchTodosSaga
  yield takeEvery("FETCH_TODOS_REQUEST", fetchTodosSaga);
}

export default rootSaga;

